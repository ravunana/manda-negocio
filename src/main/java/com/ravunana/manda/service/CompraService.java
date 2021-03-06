package com.ravunana.manda.service;

import com.ravunana.manda.domain.Compra;
import com.ravunana.manda.domain.DocumentoComercial;
import com.ravunana.manda.domain.FormaLiquidacao;
import com.ravunana.manda.domain.SerieDocumento;
import com.ravunana.manda.domain.enumeration.EntidadeSistema;
import com.ravunana.manda.repository.CompraRepository;
import com.ravunana.manda.repository.DocumentoComercialRepository;
import com.ravunana.manda.repository.FormaLiquidacaoRepository;
import com.ravunana.manda.service.dto.CompraDTO;
import com.ravunana.manda.service.dto.ItemCompraDTO;
import com.ravunana.manda.service.dto.LancamentoFinanceiroDTO;
import com.ravunana.manda.service.mapper.CompraMapper;
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
import java.util.Optional;

/**
 * Service Implementation for managing {@link Compra}.
 */
@Service
@Transactional
public class CompraService {

    private final Logger log = LoggerFactory.getLogger(CompraService.class);

    private final CompraRepository compraRepository;

    private final CompraMapper compraMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private SerieDocumentoService serieDocumentoService;

    @Autowired
    private DocumentoComercialRepository documentoComercialRepository;

    @Autowired
    private ItemCompraService itemCompraService;

    @Autowired
    private LancamentoFinanceiroService lancamentoFinanceiroService;

    private LancamentoFinanceiroDTO lancamentoFinanceiroDTO = new LancamentoFinanceiroDTO();

    private BigDecimal TOTAL_FACTURA = new BigDecimal(0);

    @Autowired
    private FormaLiquidacaoRepository formaLiquidacaoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private DocumentoComercialService documentoComercialService;

    public CompraService(CompraRepository compraRepository, CompraMapper compraMapper) {
        this.compraRepository = compraRepository;
        this.compraMapper = compraMapper;
    }

    /**
     * Save a compra.
     *
     * @param compraDTO the entity to save.
     * @return the persisted entity.
     */
    public CompraDTO save(CompraDTO compraDTO) {
        log.debug("Request to save Compra : {}", compraDTO);
        Compra compra = compraMapper.toEntity(compraDTO);

        compra.setData(ZonedDateTime.now());
        compra.setUtilizador( userService.getCurrentUserLogged() );

        if (compra.getId() != null ) {
            String numeroCompraSalvo = compraRepository.findById( compra.getId() ).get().getNumero();
            compra.setNumero( numeroCompraSalvo );
            compra = compraRepository.save(compra);
        } else {

            String numeroCompra = getNumeroCompra( compraDTO.getTipoDocumentoId() );
            compra.setNumero(numeroCompra);
            compra = compraRepository.save(compra);

            salvarItemCompra(compra.getId());

            compraDTO.setNumero( numeroCompra );

            getRecebimento(compraDTO);

            itemCompraService.cleanItems();
            TOTAL_FACTURA = new BigDecimal(0);
        }
        return compraMapper.toDto(compra);
    }

    /**
     * Get all the compras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Compras");
        return compraRepository.findAll(pageable)
            .map(compraMapper::toDto);
    }


    /**
     * Get one compra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompraDTO> findOne(Long id) {
        log.debug("Request to get Compra : {}", id);
        return compraRepository.findById(id)
            .map(compraMapper::toDto);
    }

    /**
     * Delete the compra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Compra : {}", id);
        compraRepository.deleteById(id);
    }

    private String getNumeroCompra( Long tipoDocumentoId ) {
        DocumentoComercial tipoDocumentoComercial = documentoComercialRepository.findById( tipoDocumentoId ).get();
        SerieDocumento serieDocumento = serieDocumentoService.getSerieDocumentoAnoActual();

        int sequencia = serieDocumento.getCodigoCompra();
        String numero = tipoDocumentoComercial.getNome() + " " + serieDocumento.getSerie() + "/" + sequencia; // <TIPO_DOCUMENTO> <SEQUENCIA_FORNECEDOR>
        // atualizar serie do documento
        serieDocumento.setCodigoCompra( sequencia + 1 );
        serieDocumentoService.atualizarSerieDocumento(serieDocumento);

        return numero;
    }

    private void salvarItemCompra(Long compraId) {

            for ( ItemCompraDTO item : itemCompraService.getItems() ) {
                item.setCompraId( compraId );
                BigDecimal totalUnitario = produtoService.getTotalUnitario(item.getQuantidade(), item.getDesconto(), item.getValor());
                TOTAL_FACTURA = TOTAL_FACTURA.add( totalUnitario );
                itemCompraService.save( item );
            }
    }

    private LancamentoFinanceiroDTO getRecebimento( CompraDTO compra ) {
        FormaLiquidacao formaLiquidacao = formaLiquidacaoRepository.findById( compra.getFormaLiquidacaoId() ).get();
        lancamentoFinanceiroDTO.setExterno( false );
        lancamentoFinanceiroDTO.setDescricao( "Compra na modalidade "  + formaLiquidacao.getNome() + " no valor de " + TOTAL_FACTURA + " referente a factura nº " + compra.getNumero() + " data de liquidação " + LocalDate.now().plusDays( formaLiquidacao.getPrazoLiquidacao() ) );
        lancamentoFinanceiroDTO.setFormaLiquidacaoId( compra.getFormaLiquidacaoId() );
        lancamentoFinanceiroDTO.setValor( TOTAL_FACTURA );
        lancamentoFinanceiroDTO.setImpostos( compra.getImpostos() );
        lancamentoFinanceiroDTO.setNumeroDocumento( compra.getNumero() );
        lancamentoFinanceiroDTO.setEntidadeDocumento( EntidadeSistema.COMPRA );
        DocumentoComercial documentoComercial = documentoComercialService.getDocumentoComercial("R");
        lancamentoFinanceiroDTO.setTipoLancamento( "SAIDA" );
        lancamentoFinanceiroDTO.setTipoReciboId( documentoComercial.getId() );
        // Conta Compra 21
        lancamentoFinanceiroDTO.setContaId(1107L);
        return lancamentoFinanceiroService.save( lancamentoFinanceiroDTO );
    }
}
