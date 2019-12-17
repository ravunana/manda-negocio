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

import com.ravunana.manda.domain.ItemCompra;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.ItemCompraRepository;
import com.ravunana.manda.service.dto.ItemCompraCriteria;
import com.ravunana.manda.service.dto.ItemCompraDTO;
import com.ravunana.manda.service.mapper.ItemCompraMapper;

/**
 * Service for executing complex queries for {@link ItemCompra} entities in the database.
 * The main input is a {@link ItemCompraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemCompraDTO} or a {@link Page} of {@link ItemCompraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemCompraQueryService extends QueryService<ItemCompra> {

    private final Logger log = LoggerFactory.getLogger(ItemCompraQueryService.class);

    private final ItemCompraRepository itemCompraRepository;

    private final ItemCompraMapper itemCompraMapper;

    public ItemCompraQueryService(ItemCompraRepository itemCompraRepository, ItemCompraMapper itemCompraMapper) {
        this.itemCompraRepository = itemCompraRepository;
        this.itemCompraMapper = itemCompraMapper;
    }

    /**
     * Return a {@link List} of {@link ItemCompraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemCompraDTO> findByCriteria(ItemCompraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemCompra> specification = createSpecification(criteria);
        return itemCompraMapper.toDto(itemCompraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemCompraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemCompraDTO> findByCriteria(ItemCompraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemCompra> specification = createSpecification(criteria);
        return itemCompraRepository.findAll(specification, page)
            .map(itemCompraMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemCompraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemCompra> specification = createSpecification(criteria);
        return itemCompraRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemCompraCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemCompra> createSpecification(ItemCompraCriteria criteria) {
        Specification<ItemCompra> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemCompra_.id));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), ItemCompra_.quantidade));
            }
            if (criteria.getDesconto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDesconto(), ItemCompra_.desconto));
            }
            if (criteria.getDataSolicitacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataSolicitacao(), ItemCompra_.dataSolicitacao));
            }
            if (criteria.getDataEntrega() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataEntrega(), ItemCompra_.dataEntrega));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), ItemCompra_.valor));
            }
            if (criteria.getSolicitante() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSolicitante(), ItemCompra_.solicitante));
            }
            if (criteria.getCompraId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompraId(),
                    root -> root.join(ItemCompra_.compra, JoinType.LEFT).get(Compra_.id)));
            }
            if (criteria.getProdutoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoId(),
                    root -> root.join(ItemCompra_.produto, JoinType.LEFT).get(Produto_.id)));
            }
            if (criteria.getFornecedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getFornecedorId(),
                    root -> root.join(ItemCompra_.fornecedor, JoinType.LEFT).get(Fornecedor_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(ItemCompra_.status, JoinType.LEFT).get(FluxoDocumento_.id)));
            }
        }
        return specification;
    }
}
