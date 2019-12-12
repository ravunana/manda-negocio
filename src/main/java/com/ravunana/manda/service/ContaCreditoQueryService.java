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

import com.ravunana.manda.domain.ContaCredito;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.ContaCreditoRepository;
import com.ravunana.manda.service.dto.ContaCreditoCriteria;
import com.ravunana.manda.service.dto.ContaCreditoDTO;
import com.ravunana.manda.service.mapper.ContaCreditoMapper;

/**
 * Service for executing complex queries for {@link ContaCredito} entities in the database.
 * The main input is a {@link ContaCreditoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContaCreditoDTO} or a {@link Page} of {@link ContaCreditoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContaCreditoQueryService extends QueryService<ContaCredito> {

    private final Logger log = LoggerFactory.getLogger(ContaCreditoQueryService.class);

    private final ContaCreditoRepository contaCreditoRepository;

    private final ContaCreditoMapper contaCreditoMapper;

    public ContaCreditoQueryService(ContaCreditoRepository contaCreditoRepository, ContaCreditoMapper contaCreditoMapper) {
        this.contaCreditoRepository = contaCreditoRepository;
        this.contaCreditoMapper = contaCreditoMapper;
    }

    /**
     * Return a {@link List} of {@link ContaCreditoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContaCreditoDTO> findByCriteria(ContaCreditoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContaCredito> specification = createSpecification(criteria);
        return contaCreditoMapper.toDto(contaCreditoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContaCreditoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContaCreditoDTO> findByCriteria(ContaCreditoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContaCredito> specification = createSpecification(criteria);
        return contaCreditoRepository.findAll(specification, page)
            .map(contaCreditoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContaCreditoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContaCredito> specification = createSpecification(criteria);
        return contaCreditoRepository.count(specification);
    }

    /**
     * Function to convert {@link ContaCreditoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ContaCredito> createSpecification(ContaCreditoCriteria criteria) {
        Specification<ContaCredito> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ContaCredito_.id));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), ContaCredito_.valor));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), ContaCredito_.data));
            }
            if (criteria.getContaCreditarId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaCreditarId(),
                    root -> root.join(ContaCredito_.contaCreditar, JoinType.LEFT).get(Conta_.id)));
            }
            if (criteria.getLancamentoCreditoId() != null) {
                specification = specification.and(buildSpecification(criteria.getLancamentoCreditoId(),
                    root -> root.join(ContaCredito_.lancamentoCredito, JoinType.LEFT).get(EscrituracaoContabil_.id)));
            }
        }
        return specification;
    }
}
