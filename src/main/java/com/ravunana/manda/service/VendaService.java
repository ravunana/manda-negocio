package com.ravunana.manda.service;

import com.ravunana.manda.domain.DocumentoComercial;
import com.ravunana.manda.domain.FormaLiquidacao;
import com.ravunana.manda.domain.SerieDocumento;
import com.ravunana.manda.domain.Venda;
import com.ravunana.manda.domain.enumeration.EntidadeSistema;
import com.ravunana.manda.repository.DocumentoComercialRepository;
import com.ravunana.manda.repository.FormaLiquidacaoRepository;
import com.ravunana.manda.repository.VendaRepository;
import com.ravunana.manda.service.dto.ItemVendaDTO;
import com.ravunana.manda.service.dto.LancamentoFinanceiroDTO;
import com.ravunana.manda.service.dto.VendaDTO;
import com.ravunana.manda.service.mapper.VendaMapper;
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
 * Service Implementation for managing {@link Venda}.
 */
@Service
@Transactional
public class VendaService {

    private final Logger log = LoggerFactory.getLogger(VendaService.class);

    private final VendaRepository vendaRepository;

    private final VendaMapper vendaMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private SerieDocumentoService serieDocumentoService;

    @Autowired
    private DocumentoComercialRepository documentoComercialRepository;

    @Autowired
    private LancamentoFinanceiroService lancamentoFinanceiroService;

    @Autowired
    private ItemVendaService itemVendaService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FormaLiquidacaoRepository formaLiquidacaoRepository;

    private LancamentoFinanceiroDTO lancamentoFinanceiroDTO = new LancamentoFinanceiroDTO();

    private BigDecimal TOTAL_FACTURA = new BigDecimal(0);

    public VendaService(VendaRepository vendaRepository, VendaMapper vendaMapper) {
        this.vendaRepository = vendaRepository;
        this.vendaMapper = vendaMapper;
    }

    /**
     * Save a venda.
     *
     * @param vendaDTO the entity to save.
     * @return the persisted entity.
     */
    public VendaDTO save(VendaDTO vendaDTO) {
        log.debug("Request to save Venda : {}", vendaDTO);
        Venda venda = vendaMapper.toEntity(vendaDTO);
        venda.setEmpresa( empresaService.getFirstEmpresa() );
        venda.setData(ZonedDateTime.now());


        if (vendaDTO.getId() != null ) {
            String numeroVendaSalvo = vendaRepository.findById( venda.getId() ).get().getNumero();
            venda.setNumero( numeroVendaSalvo );
            venda = vendaRepository.save(venda);
        } else {

            String numeroVenda = getNumeroVenda( vendaDTO.getTipoDocumentoId() );
            venda.setNumero(numeroVenda);

        log.info("Venda ------ {}", venda);
            venda = vendaRepository.save(venda);

            salvarItemVenda(venda.getId());

            vendaDTO.setNumero( numeroVenda );

            getPagamento(vendaDTO);

            itemVendaService.cleanItems();
        }
        TOTAL_FACTURA = new BigDecimal(0);

        return vendaMapper.toDto(venda);
    }

    /**
     * Get all the vendas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VendaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vendas");
        return vendaRepository.findAll(pageable)
            .map(vendaMapper::toDto);
    }


    /**
     * Get one venda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VendaDTO> findOne(Long id) {
        log.debug("Request to get Venda : {}", id);
        return vendaRepository.findById(id)
            .map(vendaMapper::toDto);
    }

    /**
     * Delete the venda by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Venda : {}", id);
        vendaRepository.deleteById(id);
    }


    private String getNumeroVenda( Long tipoDocumentoId ) {
        DocumentoComercial tipoDocumentoComercial = documentoComercialRepository.findById( tipoDocumentoId ).get();
        SerieDocumento serieDocumento = serieDocumentoService.getSerieDocumentoAnoActual();

        int sequencia = serieDocumento.getCodigoVenda();
        String numero = tipoDocumentoComercial.getNome() + " " + serieDocumento.getSerie() + "/" + sequencia; // <TIPO_DOCUMENTO> <SEQUENCIA_FORNECEDOR>
        // atualizar serie do documento
        serieDocumento.setCodigoVenda( sequencia + 1 );
        serieDocumentoService.atualizarSerieDocumento(serieDocumento);

        return numero;
    }

    private void salvarItemVenda(Long vendaId) {

            for ( ItemVendaDTO item : itemVendaService.getItems() ) {
                item.setVendaId( vendaId );
                BigDecimal totalUnitario = produtoService.getTotalUnitario(item.getQuantidade(), item.getDesconto(), item.getValor());
                TOTAL_FACTURA = TOTAL_FACTURA.add( totalUnitario );
                itemVendaService.save( item );
            }
    }

    private LancamentoFinanceiroDTO getPagamento( VendaDTO venda ) {
        FormaLiquidacao formaLiquidacao = formaLiquidacaoRepository.findById( venda.getFormaLiquidacaoId() ).get();
        lancamentoFinanceiroDTO.setExterno( false );
        lancamentoFinanceiroDTO.setDescricao( "Venda de mercadoria na modalidade "  + formaLiquidacao.getNome() + " no valor de " + TOTAL_FACTURA + " referente a factura nº " + venda.getNumero() + " data de liquidação " + LocalDate.now().plusDays( formaLiquidacao.getPrazoLiquidacao() ) );
        lancamentoFinanceiroDTO.setFormaLiquidacaoId( venda.getFormaLiquidacaoId() );
        lancamentoFinanceiroDTO.setValor( TOTAL_FACTURA );
        lancamentoFinanceiroDTO.setImpostos( venda.getImpostos() );
        lancamentoFinanceiroDTO.setNumeroDocumento( venda.getNumero() );
        lancamentoFinanceiroDTO.setEntidadeDocumento( EntidadeSistema.VENDA );

        lancamentoFinanceiroDTO.setTipoLancamento( "ENTRADA" );
        lancamentoFinanceiroDTO.setTipoReciboId( venda.getTipoDocumentoId() );
        return lancamentoFinanceiroService.save( lancamentoFinanceiroDTO );
    }
}
