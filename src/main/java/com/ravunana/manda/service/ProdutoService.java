package com.ravunana.manda.service;

import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.domain.EstoqueConfig;
import com.ravunana.manda.domain.FamiliaProduto;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.domain.SerieDocumento;
import com.ravunana.manda.repository.ContaRepository;
import com.ravunana.manda.repository.FamiliaProdutoRepository;
import com.ravunana.manda.repository.ProdutoRepository;
import com.ravunana.manda.service.dto.ProdutoDTO;
import com.ravunana.manda.service.mapper.ProdutoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Produto}.
 */
@Service
@Transactional
public class ProdutoService {

    private final Logger log = LoggerFactory.getLogger(ProdutoService.class);

    private final ProdutoRepository produtoRepository;

    private final ProdutoMapper produtoMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private SerieDocumentoService serieDocumentoService;

    @Autowired
    private ContaService contaService;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private FamiliaProdutoRepository familiaProdutoRepository;

    @Autowired
    private EstoqueConfigService estoqueConfigService;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    /**
     * Save a produto.
     *
     * @param produtoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        log.debug("Request to save Produto : {}", produtoDTO);

        SerieDocumento serieDocumento = serieDocumentoService.getSerieDocumentoAnoActual();
        Produto produto = produtoMapper.toEntity(produtoDTO);

        contaService.addSubConta(getContaAgregadoraId( produtoDTO.getFamiliaId() ), produto.getNome(), "" );

        produto.setUtilizador( userService.getCurrentUserLogged() );
        int serie = serieDocumento.getCodigoProduto();
        produto.setNumero( produto.getTipo().substring(0, 1) + " " + serieDocumento.getSerie() + " " + serie ); // <Tipo> <Serie> <Sequencia>
        if ( produto.getEan() == null || produto.getEan().equals(null) || produto.getEan() == ""  || produto.getEan() == " " ) {
            produto.setEan( produto.getNumero() );
        }
        produto = produtoRepository.save(produto);
        if ( produto.getId() > 0 ) {
        // atualizar serie do documento
        serieDocumento.setCodigoProduto( serie + 1 );
        serieDocumentoService.atualizarSerieDocumento(serieDocumento);
        }
        return produtoMapper.toDto(produto);
    }

    private Long getContaAgregadoraId( Long familiaId ) {
        return familiaProdutoRepository.findById( familiaId ).get().getConta().getId();
    }
    /**
     * Get all the produtos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Produtos");
        return produtoRepository.findAll(pageable)
            .map(produtoMapper::toDto);
    }

    /**
     * Get all the produtos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProdutoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return produtoRepository.findAllWithEagerRelationships(pageable).map(produtoMapper::toDto);
    }


    /**
     * Get one produto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProdutoDTO> findOne(Long id) {
        log.debug("Request to get Produto : {}", id);
        return produtoRepository.findOneWithEagerRelationships(id)
            .map(produtoMapper::toDto);
    }

    /**
     * Delete the produto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Produto : {}", id);
        produtoRepository.deleteById(id);
    }

    public BigDecimal getTotalUnitario( Double quantidade, Double desconto, BigDecimal precoUnitario ) {
        desconto = ( precoUnitario.doubleValue()  * quantidade * desconto ) / 100;
        return new BigDecimal((quantidade * precoUnitario.doubleValue() ) - desconto);
    }

    public Produto aumentarQuantidadeEstoque( Long produtoId, Double quantidade ) {
        Produto produto = produtoRepository.findById( produtoId ).get();
        if ( produto.getTipo() != "S" || produto.getTipo() != "Serviço" || produto.getTipo() != "Servico" ) {
            Double estoqueAtual = produto.getEstoqueAtual();
            produto.setEstoqueAtual(estoqueAtual + quantidade);
            return produtoRepository.save( produto );
        } else {
            return produto;
        }
    }

    public Produto diminuirQuantidadeEstoque( Long produtoId, Double quantidade ) {
        Produto produto = produtoRepository.findById( produtoId ).get();
        if ( produto.getTipo() != "S" || produto.getTipo() != "Serviço" || produto.getTipo() != "Servico" ) {
        Double estoqueAtual = produto.getEstoqueAtual();
        produto.setEstoqueAtual(estoqueAtual - quantidade);
        return produtoRepository.save( produto );
        } else {
            return produto;
        }
    }
}
