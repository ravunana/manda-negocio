package com.ravunana.manda.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.ProdutoRepository;
import com.ravunana.manda.service.dto.ProdutoCriteria;
import com.ravunana.manda.service.dto.ProdutoDTO;
import com.ravunana.manda.service.mapper.ProdutoMapper;

/**
 * Service for executing complex queries for {@link Produto} entities in the database.
 * The main input is a {@link ProdutoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProdutoDTO} or a {@link Page} of {@link ProdutoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProdutoQueryService extends QueryService<Produto> {

    private final Logger log = LoggerFactory.getLogger(ProdutoQueryService.class);

    private final ProdutoRepository produtoRepository;

    private final ProdutoMapper produtoMapper;

    public ProdutoQueryService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    /**
     * Return a {@link List} of {@link ProdutoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProdutoDTO> findByCriteria(ProdutoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Produto> specification = createSpecification(criteria);
        return produtoMapper.toDto(produtoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProdutoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findByCriteria(ProdutoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Produto> specification = createSpecification(criteria);
        return produtoRepository.findAll(specification, page)
            .map(produtoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProdutoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Produto> specification = createSpecification(criteria);
        return produtoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProdutoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Produto> createSpecification(ProdutoCriteria criteria) {
        Specification<Produto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Produto_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), Produto_.tipo));
            }
            if (criteria.getEan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEan(), Produto_.ean));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Produto_.numero));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Produto_.nome));
            }
            if (criteria.getComposto() != null) {
                specification = specification.and(buildSpecification(criteria.getComposto(), Produto_.composto));
            }
            if (criteria.getEstoqueMinimo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstoqueMinimo(), Produto_.estoqueMinimo));
            }
            if (criteria.getEstoqueMaximo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstoqueMaximo(), Produto_.estoqueMaximo));
            }
            if (criteria.getEstoqueAtual() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstoqueAtual(), Produto_.estoqueAtual));
            }
            if (criteria.getMostrarPDV() != null) {
                specification = specification.and(buildSpecification(criteria.getMostrarPDV(), Produto_.mostrarPDV));
            }
            if (criteria.getPrazoMedioEntrega() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrazoMedioEntrega(), Produto_.prazoMedioEntrega));
            }
            if (criteria.getCompostoProdutoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompostoProdutoId(),
                    root -> root.join(Produto_.compostoProdutos, JoinType.LEFT).get(CompostoProduto_.id)));
            }
            if (criteria.getConversaoUnidadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getConversaoUnidadeId(),
                    root -> root.join(Produto_.conversaoUnidades, JoinType.LEFT).get(ConversaoUnidade_.id)));
            }
            if (criteria.getEstruturaCalculoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstruturaCalculoId(),
                    root -> root.join(Produto_.estruturaCalculos, JoinType.LEFT).get(EstruturaCalculo_.id)));
            }
            if (criteria.getItemCompraId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemCompraId(),
                    root -> root.join(Produto_.itemCompras, JoinType.LEFT).get(ItemCompra_.id)));
            }
            if (criteria.getItemVendaId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemVendaId(),
                    root -> root.join(Produto_.itemVendas, JoinType.LEFT).get(ItemVenda_.id)));
            }
            if (criteria.getUtilizadorId() != null) {
                specification = specification.and(buildSpecification(criteria.getUtilizadorId(),
                    root -> root.join(Produto_.utilizador, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getImpostoId() != null) {
                specification = specification.and(buildSpecification(criteria.getImpostoId(),
                    root -> root.join(Produto_.impostos, JoinType.LEFT).get(Imposto_.id)));
            }
            if (criteria.getFornecedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getFornecedorId(),
                    root -> root.join(Produto_.fornecedors, JoinType.LEFT).get(Fornecedor_.id)));
            }
            if (criteria.getLocalArmazenamentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocalArmazenamentoId(),
                    root -> root.join(Produto_.localArmazenamento, JoinType.LEFT).get(LocalArmazenamento_.id)));
            }
            if (criteria.getFamiliaId() != null) {
                specification = specification.and(buildSpecification(criteria.getFamiliaId(),
                    root -> root.join(Produto_.familia, JoinType.LEFT).get(FamiliaProduto_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(Produto_.empresa, JoinType.LEFT).get(Empresa_.id)));
            }
            if (criteria.getEstadoId() != null) {
                specification = specification.and(buildSpecification(criteria.getEstadoId(),
                    root -> root.join(Produto_.estado, JoinType.LEFT).get(FluxoDocumento_.id)));
            }
        }
        return specification;
    }
}
