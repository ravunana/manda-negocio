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

import com.ravunana.manda.domain.DevolucaoCompra;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.DevolucaoCompraRepository;
import com.ravunana.manda.service.dto.DevolucaoCompraCriteria;
import com.ravunana.manda.service.dto.DevolucaoCompraDTO;
import com.ravunana.manda.service.mapper.DevolucaoCompraMapper;

/**
 * Service for executing complex queries for {@link DevolucaoCompra} entities in the database.
 * The main input is a {@link DevolucaoCompraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DevolucaoCompraDTO} or a {@link Page} of {@link DevolucaoCompraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DevolucaoCompraQueryService extends QueryService<DevolucaoCompra> {

    private final Logger log = LoggerFactory.getLogger(DevolucaoCompraQueryService.class);

    private final DevolucaoCompraRepository devolucaoCompraRepository;

    private final DevolucaoCompraMapper devolucaoCompraMapper;

    public DevolucaoCompraQueryService(DevolucaoCompraRepository devolucaoCompraRepository, DevolucaoCompraMapper devolucaoCompraMapper) {
        this.devolucaoCompraRepository = devolucaoCompraRepository;
        this.devolucaoCompraMapper = devolucaoCompraMapper;
    }

    /**
     * Return a {@link List} of {@link DevolucaoCompraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DevolucaoCompraDTO> findByCriteria(DevolucaoCompraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DevolucaoCompra> specification = createSpecification(criteria);
        return devolucaoCompraMapper.toDto(devolucaoCompraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DevolucaoCompraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DevolucaoCompraDTO> findByCriteria(DevolucaoCompraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DevolucaoCompra> specification = createSpecification(criteria);
        return devolucaoCompraRepository.findAll(specification, page)
            .map(devolucaoCompraMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DevolucaoCompraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DevolucaoCompra> specification = createSpecification(criteria);
        return devolucaoCompraRepository.count(specification);
    }

    /**
     * Function to convert {@link DevolucaoCompraCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DevolucaoCompra> createSpecification(DevolucaoCompraCriteria criteria) {
        Specification<DevolucaoCompra> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DevolucaoCompra_.id));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), DevolucaoCompra_.quantidade));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), DevolucaoCompra_.valor));
            }
            if (criteria.getDesconto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDesconto(), DevolucaoCompra_.desconto));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), DevolucaoCompra_.data));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(DevolucaoCompra_.item, JoinType.LEFT).get(ItemCompra_.id)));
            }
        }
        return specification;
    }
}
