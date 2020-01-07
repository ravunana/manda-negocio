package com.ravunana.manda.service;

import com.ravunana.manda.domain.DocumentoComercial;
import com.ravunana.manda.domain.LancamentoFinanceiro;
import com.ravunana.manda.domain.SerieDocumento;
import com.ravunana.manda.domain.enumeration.EntidadeSistema;
import com.ravunana.manda.repository.DocumentoComercialRepository;
import com.ravunana.manda.repository.LancamentoFinanceiroRepository;
import com.ravunana.manda.service.dto.ContaCreditoDTO;
import com.ravunana.manda.service.dto.ContaDebitoDTO;
import com.ravunana.manda.service.dto.DetalheLancamentoDTO;
import com.ravunana.manda.service.dto.EscrituracaoContabilDTO;
import com.ravunana.manda.service.dto.LancamentoFinanceiroDTO;
import com.ravunana.manda.service.mapper.LancamentoFinanceiroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
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

    @Autowired
    private EscrituracaoContabilService escrituracaoContabilService;

    @Autowired
    private ContaDebitoService contaDebitoService;

    @Autowired
    private ContaCreditoService contaCreditoService;

    @Autowired
    private CoordenadaBancariaService coordenadaBancariaService;


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
        DetalheLancamentoDTO detalhe = null;

        for ( DetalheLancamentoDTO detalheLancamentoDTO : detalhesLancamento ) {
            detalheLancamentoDTO.setLancamentoFinanceiroId(lancamentoFinanceiro.getId());
            detalheLancamentoDTO.setData( ZonedDateTime.now() );
            detalheLancamentoDTO.setUtilizadorId(userService.getCurrentUserLogged().getId());
            detalhe = detalheLancamentoService.save( detalheLancamentoDTO );
        }



        addEscrituracaoContabil(lancamentoFinanceiro.getEntidadeDocumento(), lancamentoFinanceiro.getNumeroDocumento(), lancamentoFinanceiro, detalhe);

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

    public LancamentoFinanceiroDTO getLancamentoByEntidadeAndNumero( EntidadeSistema entidade, String numero ) {
        log.debug("Request to get LancamentoFinanceiro by Entidade and Numero: {} {}", entidade, numero);
        return lancamentoFinanceiroMapper.toDto(lancamentoFinanceiroRepository.findByEntidadeDocumentoAndNumeroDocumento(entidade, numero));
    }

    public void addEscrituracaoContabil( EntidadeSistema entidadeSistema, String referenciaEntidade, LancamentoFinanceiro lancamento, DetalheLancamentoDTO detalhe ) {
        EscrituracaoContabilDTO escrituracaoContabilDTO = new EscrituracaoContabilDTO();
        escrituracaoContabilDTO.setHistorico(lancamento.getDescricao());
        escrituracaoContabilDTO.setTipo("N");
        escrituracaoContabilDTO.setValor( lancamento.getValor() );
        escrituracaoContabilDTO.setDataDocumento( LocalDate.now() );

        ContaCreditoDTO credito = null;
        ContaDebitoDTO debito = null;

        if ( lancamento.getTipoLancamento() == "ENTRADA" ) {

            credito = new ContaCreditoDTO();
            Long contaDebitarId = coordenadaBancariaService.findOne( detalhe.getCoordenadaId() ).get().getContaId();
            credito.setContaCreditarId( contaDebitarId ) ;
            credito.setValor( detalhe.getValor() );

            debito = new ContaDebitoDTO();
            Long contaCreditarId =  findOne( lancamento.getId() ).get().getContaId();
            debito.setContaDebitarId( contaCreditarId );
            debito.setValor( lancamento.getValor() );
        } else if ( lancamento.getTipoLancamento() == "SAIDA" ) {
            credito = new ContaCreditoDTO();
            Long contaCreditarId = coordenadaBancariaService.findOne( detalhe.getCoordenadaId() ).get().getContaId();
            credito.setContaCreditarId( contaCreditarId ) ;
            credito.setValor( detalhe.getValor() );

            debito = new ContaDebitoDTO();
            Long contaDebitarId =  findOne( lancamento.getId() ).get().getContaId();
            debito.setContaDebitarId( contaDebitarId );
            debito.setValor( lancamento.getValor() );
        }

        escrituracaoContabilService.addCredito(credito);
        escrituracaoContabilService.addDebito(debito);

        escrituracaoContabilService.setEntidadeSistema(entidadeSistema, referenciaEntidade);

        escrituracaoContabilService.save(escrituracaoContabilDTO);
    }
}
