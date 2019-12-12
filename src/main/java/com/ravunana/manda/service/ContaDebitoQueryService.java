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

import com.ravunana.manda.domain.ContaDebito;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.ContaDebitoRepository;
import com.ravunana.manda.service.dto.ContaDebitoCriteria;
import com.ravunana.manda.service.dto.ContaDebitoDTO;
import com.ravunana.manda.service.mapper.ContaDebitoMapper;

/**
 * Service for executing complex queries for {@link ContaDebito} entities in the database.
 * The main input is a {@link ContaDebitoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContaDebitoDTO} or a {@link Page} of {@link ContaDebitoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContaDebitoQueryService extends QueryService<ContaDebito> {

    private final Logger log = LoggerFactory.getLogger(ContaDebitoQueryService.class);

    private final ContaDebitoRepository contaDebitoRepository;

    private final ContaDebitoMapper contaDebitoMapper;

    public ContaDebitoQueryService(ContaDebitoRepository contaDebitoRepository, ContaDebitoMapper contaDebitoMapper) {
        this.contaDebitoRepository = contaDebitoRepository;
        this.contaDebitoMapper = contaDebitoMapper;
    }

    /**
     * Return a {@link List} of {@link ContaDebitoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContaDebitoDTO> findByCriteria(ContaDebitoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContaDebito> specification = createSpecification(criteria);
        return contaDebitoMapper.toDto(contaDebitoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContaDebitoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContaDebitoDTO> findByCriteria(ContaDebitoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContaDebito> specification = createSpecification(criteria);
        return contaDebitoRepository.findAll(specification, page)
            .map(contaDebitoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContaDebitoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContaDebito> specification = createSpecification(criteria);
        return contaDebitoRepository.count(specification);
    }

    /**
     * Function to convert {@link ContaDebitoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ContaDebito> createSpecification(ContaDebitoCriteria criteria) {
        Specification<ContaDebito> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ContaDebito_.id));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), ContaDebito_.valor));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), ContaDebito_.data));
            }
            if (criteria.getContaDebitarId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaDebitarId(),
                    root -> root.join(ContaDebito_.contaDebitar, JoinType.LEFT).get(Conta_.id)));
            }
            if (criteria.getLancamentoDebitoId() != null) {
                specification = specification.and(buildSpecification(criteria.getLancamentoDebitoId(),
                    root -> root.join(ContaDebito_.lancamentoDebito, JoinType.LEFT).get(EscrituracaoContabil_.id)));
            }
        }
        return specification;
    }
}
