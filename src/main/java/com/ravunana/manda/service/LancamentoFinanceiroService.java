package com.ravunana.manda.service;

import com.ravunana.manda.domain.LancamentoFinanceiro;
import com.ravunana.manda.repository.DocumentoComercialRepository;
import com.ravunana.manda.repository.LancamentoFinanceiroRepository;
import com.ravunana.manda.service.dto.DetalheLancamentoDTO;
import com.ravunana.manda.service.dto.LancamentoFinanceiroDTO;
import com.ravunana.manda.service.mapper.LancamentoFinanceiroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link LancamentoFinanceiro}.
 */
@Service
@Transactional
public class LancamentoFinanceiroService {

    private final Logger log = LoggerFactory.getLogger(LancamentoFinanceiroService.class);

    private final LancamentoFinanceiroRepository lancamentoFinanceiroRepository;

    private final LancamentoFinanceiroMapper lancamentoFinanceiroMapper;

    private List<DetalheLancamentoDTO> detalhesLancamento = new ArrayList<>();

    private LancamentoFinanceiroDTO lancamentoFinanceiroDTO;

    @Autowired
    private UserService userService;

    @Autowired
    private DetalheLancamentoService detalheLancamentoService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private DocumentoComercialRepository documentoComercialRepository;

    @Autowired
    SerieDocumentoService serieDocumentoService;



    public LancamentoFinanceiroService(LancamentoFinanceiroRepository lancamentoFinanceiroRepository, LancamentoFinanceiroMapper lancamentoFinanceiroMapper) {
        this.lancamentoFinanceiroRepository = lancamentoFinanceiroRepository;
        this.lancamentoFinanceiroMapper = lancamentoFinanceiroMapper;
    }

    /**
     * Save a lancamentoFinanceiro.
     *
     * @param lancamentoFinanceiroDTO the entity to save.
     * @return the persisted entity.
     */
    public LancamentoFinanceiroDTO save(LancamentoFinanceiroDTO lancamentoFinanceiroDTO) {
        log.debug("Request to save LancamentoFinanceiro : {}", lancamentoFinanceiroDTO);
        LancamentoFinanceiro lancamentoFinanceiro = lancamentoFinanceiroMapper.toEntity(lancamentoFinanceiroDTO);

        lancamentoFinanceiro.setUtilizador(userService.getCurrentUserLogged());
        lancamentoFinanceiro.setEmpresa(empresaService.getFirstEmpresa());

        lancamentoFinanceiro.setNumero( getNumeroRecibo(lancamentoFinanceiroDTO.getTipoReciboId()) );

        lancamentoFinanceiro = lancamentoFinanceiroRepository.save(lancamentoFinanceiro);

        for ( DetalheLancamentoDTO detalheLancamentoDTO : detalhesLancamento ) {
            detalheLancamentoDTO.setLancamentoFinanceiroId(lancamentoFinanceiro.getId());
            detalheLancamentoDTO.setData( ZonedDateTime.now() );
            detalheLancamentoDTO.setUtilizadorId(userService.getCurrentUserLogged().getId());
            detalheLancamentoService.save( detalheLancamentoDTO );
        }

        clearDetahesLancamento();
        return lancamentoFinanceiroMapper.toDto(lancamentoFinanceiro);
    }

    /**
     * Get all the lancamentoFinanceiros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LancamentoFinanceiroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LancamentoFinanceiros");
        return lancamentoFinanceiroRepository.findAll(pageable)
            .map(lancamentoFinanceiroMapper::toDto);
    }

    /**
     * Get all the lancamentoFinanceiros with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<LancamentoFinanceiroDTO> findAllWithEagerRelationships(Pageable pageable) {
        return lancamentoFinanceiroRepository.findAllWithEagerRelationships(pageable).map(lancamentoFinanceiroMapper::toDto);
    }


    /**
     * Get one lancamentoFinanceiro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LancamentoFinanceiroDTO> findOne(Long id) {
        log.debug("Request to get LancamentoFinanceiro : {}", id);
        return lancamentoFinanceiroRepository.findOneWithEagerRelationships(id)
            .map(lancamentoFinanceiroMapper::toDto);
    }

    /**
     * Delete the lancamentoFinanceiro by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LancamentoFinanceiro : {}", id);
        lancamentoFinanceiroRepository.deleteById(id);
    }


    public DetalheLancamentoDTO addDetalheLancamento( DetalheLancamentoDTO detalheLancamentoDTO ) {
        Boolean result = detalhesLancamento.add(detalheLancamentoDTO);
        if ( result == true ) {
            return detalheLancamentoDTO;
        } else {
            return new DetalheLancamentoDTO();
        }
    }

    public List<DetalheLancamentoDTO> listarDetalhe() {
        return detalhesLancamento;
    }

    public DetalheLancamentoDTO deleteDetalhe(int index) {
        return detalhesLancamento.remove(index);
    }

    private String getNumeroRecibo( Long tipoDocumentoId ) {
        DocumentoComercial tipoDocumentoComercial = documentoComercialRepository.findById( tipoDocumentoId ).get();
        SerieDocumento serieDocumento = serieDocumentoService.getSerieDocumentoAnoActual();

        int sequencia = serieDocumento.getCodigoLancamentoFinanceiro();
        String numero = tipoDocumentoComercial.getNome() + " " + serieDocumento.getSerie() + "/" + sequencia; // <TIPO_DOCUMENTO> <SEQUENCIA_FORNECEDOR>
        // atualizar serie do documento
        serieDocumento.setCodigoLancamentoFinanceiro( sequencia + 1 );
        serieDocumentoService.atualizarSerieDocumento(serieDocumento);

        return numero;
    }

    public void clearDetahesLancamento() {
        detalhesLancamento.clear();
    }

}
