package com.ravunana.manda.service;

import com.ravunana.manda.domain.Arquivo;
import com.ravunana.manda.domain.DocumentoComercial;
import com.ravunana.manda.domain.EscrituracaoContabil;
import com.ravunana.manda.domain.SerieDocumento;
import com.ravunana.manda.domain.enumeration.EntidadeSistema;
import com.ravunana.manda.repository.ArquivoRepository;
import com.ravunana.manda.repository.DocumentoComercialRepository;
import com.ravunana.manda.repository.EscrituracaoContabilRepository;
import com.ravunana.manda.service.dto.ArquivoDTO;
import com.ravunana.manda.service.dto.ContaCreditoDTO;
import com.ravunana.manda.service.dto.ContaDebitoDTO;
import com.ravunana.manda.service.dto.EscrituracaoContabilDTO;
import com.ravunana.manda.service.mapper.EscrituracaoContabilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * Service Implementation for managing {@link EscrituracaoContabil}.
 */
@Service
@Transactional
public class EscrituracaoContabilService {

    private final Logger log = LoggerFactory.getLogger(EscrituracaoContabilService.class);

    private final EscrituracaoContabilRepository escrituracaoContabilRepository;

    private final EscrituracaoContabilMapper escrituracaoContabilMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private EmpresaService empresaService;

    private List<ContaCreditoDTO> creditos = new ArrayList<>();
    private List<ContaDebitoDTO> debitos = new ArrayList<>();

    @Autowired
    private ContaCreditoService contaCreditoService;
    @Autowired
    private ContaDebitoService contaDebitoService;

    @Autowired
    private SerieDocumentoService serieDocumentoService;

    private EntidadeSistema entidadeSistema = null;
    private String referenciaEntidade = null;

    public EscrituracaoContabilService(EscrituracaoContabilRepository escrituracaoContabilRepository, EscrituracaoContabilMapper escrituracaoContabilMapper) {
        this.escrituracaoContabilRepository = escrituracaoContabilRepository;
        this.escrituracaoContabilMapper = escrituracaoContabilMapper;
    }

    /**
     * Save a escrituracaoContabil.
     *
     * @param escrituracaoContabilDTO the entity to save.
     * @return the persisted entity.
     */
    public EscrituracaoContabilDTO save(EscrituracaoContabilDTO escrituracaoContabilDTO) {

        if ( entidadeSistema == null || referenciaEntidade == null ) {
            referenciaEntidade = escrituracaoContabilDTO.getNumero();
            entidadeSistema = EntidadeSistema.ARQUIVO;
        }

        String numero = getNumeroEscrituracaoCOntabil( referenciaEntidade , escrituracaoContabilDTO.getDataDocumento());

        escrituracaoContabilDTO.setReferencia( referenciaEntidade );
        escrituracaoContabilDTO.setNumero( numero );
        escrituracaoContabilDTO.setEntidadeReferencia( entidadeSistema );

        EscrituracaoContabil escrituracaoContabil = escrituracaoContabilMapper.toEntity(escrituracaoContabilDTO);

        escrituracaoContabil.setData(ZonedDateTime.now());
        escrituracaoContabil.setUtilizador(userService.getCurrentUserLogged());
        escrituracaoContabil.setEmpresa(empresaService.getFirstEmpresa());


        log.debug("Request to save EscrituracaoContabil : {}", escrituracaoContabil);


        escrituracaoContabil = escrituracaoContabilRepository.save(escrituracaoContabil);
        EscrituracaoContabilDTO result = escrituracaoContabilMapper.toDto(escrituracaoContabil);

        for ( ContaCreditoDTO credito : creditos ) {
            credito.setData(ZonedDateTime.now());
            credito.setLancamentoCreditoId(escrituracaoContabil.getId());
            credito.setLancamentoCreditoHistorico(escrituracaoContabil.getHistorico());
            contaCreditoService.save(credito);
        }

        for ( ContaDebitoDTO debito : debitos ) {
            debito.setData(ZonedDateTime.now());
            debito.setLancamentoDebitoId(escrituracaoContabil.getId());
            debito.setLancamentoDebitoHistorico(escrituracaoContabil.getHistorico());
            contaDebitoService.save(debito);
        }
        limparCreditos();
        limparDebitos();
        return result;
    }

    /**
     * Get all the escrituracaoContabils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EscrituracaoContabilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EscrituracaoContabils");
        return escrituracaoContabilRepository.findAll(pageable)
            .map(escrituracaoContabilMapper::toDto);
    }

    /**
     * Get one escrituracaoContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EscrituracaoContabilDTO> findOne(Long id) {
        log.debug("Request to get EscrituracaoContabil : {}", id);
        return escrituracaoContabilRepository.findById(id)
            .map(escrituracaoContabilMapper::toDto);
    }

    /**
     * Delete the escrituracaoContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EscrituracaoContabil : {}", id);
        escrituracaoContabilRepository.deleteById(id);
    }

    private String getNumeroEscrituracaoCOntabil( String numeroDocumento, LocalDate dataDocumento ) {
        SerieDocumento serieDocumento = serieDocumentoService.getSerieDocumentoAnoActual();

        int sequencia = serieDocumento.getCodigoEscrituracaoContabil();
        // <DATA_DOCUMENTO> <SEQUENCIA> <NUMERO_FACTURA> */
        String numero = dataDocumento + " " + sequencia + " " + numeroDocumento;
        // atualizar serie do documento
        serieDocumento.setCodigoEscrituracaoContabil( sequencia + 1 );
        serieDocumentoService.atualizarSerieDocumento(serieDocumento);

        return numero;
    }

    public ContaCreditoDTO addCredito(ContaCreditoDTO credito) {
        Boolean result = creditos.add(credito);
        if ( result == true )
            return credito;
        else
            return new ContaCreditoDTO();
    }

    public ContaDebitoDTO addDebito(ContaDebitoDTO debito) {
        Boolean result = debitos.add(debito);
        if ( result == true )
            return debito;
        else
            return new ContaDebitoDTO();
    }

    public ContaCreditoDTO deleteCredito(int index) {
        return creditos.remove(index);
    }

    public ContaDebitoDTO deleteDebito(int index) {
        return debitos.remove(index);
    }

    public List<ContaCreditoDTO> listarCreditos() {
        return creditos;
    }

    public List<ContaDebitoDTO> listarDebitos() {
        return debitos;
    }

    private void limparCreditos() {
        creditos.clear();
    }

    private void limparDebitos() {
        debitos.clear();
    }

    public void setEntidadeSistema(EntidadeSistema entidadeSistema, String referenciaEntidade) {
        this.entidadeSistema = entidadeSistema;
        this.referenciaEntidade = referenciaEntidade;
    }
}
