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

import com.ravunana.manda.domain.EscrituracaoContabil;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.EscrituracaoContabilRepository;
import com.ravunana.manda.service.dto.EscrituracaoContabilCriteria;
import com.ravunana.manda.service.dto.EscrituracaoContabilDTO;
import com.ravunana.manda.service.mapper.EscrituracaoContabilMapper;

/**
 * Service for executing complex queries for {@link EscrituracaoContabil} entities in the database.
 * The main input is a {@link EscrituracaoContabilCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EscrituracaoContabilDTO} or a {@link Page} of {@link EscrituracaoContabilDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EscrituracaoContabilQueryService extends QueryService<EscrituracaoContabil> {

    private final Logger log = LoggerFactory.getLogger(EscrituracaoContabilQueryService.class);

    private final EscrituracaoContabilRepository escrituracaoContabilRepository;

    private final EscrituracaoContabilMapper escrituracaoContabilMapper;

    public EscrituracaoContabilQueryService(EscrituracaoContabilRepository escrituracaoContabilRepository, EscrituracaoContabilMapper escrituracaoContabilMapper) {
        this.escrituracaoContabilRepository = escrituracaoContabilRepository;
        this.escrituracaoContabilMapper = escrituracaoContabilMapper;
    }

    /**
     * Return a {@link List} of {@link EscrituracaoContabilDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EscrituracaoContabilDTO> findByCriteria(EscrituracaoContabilCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EscrituracaoContabil> specification = createSpecification(criteria);
        return escrituracaoContabilMapper.toDto(escrituracaoContabilRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EscrituracaoContabilDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EscrituracaoContabilDTO> findByCriteria(EscrituracaoContabilCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EscrituracaoContabil> specification = createSpecification(criteria);
        return escrituracaoContabilRepository.findAll(specification, page)
            .map(escrituracaoContabilMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EscrituracaoContabilCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EscrituracaoContabil> specification = createSpecification(criteria);
        return escrituracaoContabilRepository.count(specification);
    }

    /**
     * Function to convert {@link EscrituracaoContabilCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EscrituracaoContabil> createSpecification(EscrituracaoContabilCriteria criteria) {
        Specification<EscrituracaoContabil> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EscrituracaoContabil_.id));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), EscrituracaoContabil_.numero));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), EscrituracaoContabil_.valor));
            }
            if (criteria.getReferencia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferencia(), EscrituracaoContabil_.referencia));
            }
            if (criteria.getEntidadeReferencia() != null) {
                specification = specification.and(buildSpecification(criteria.getEntidadeReferencia(), EscrituracaoContabil_.entidadeReferencia));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), EscrituracaoContabil_.tipo));
            }
            if (criteria.getDataDocumento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataDocumento(), EscrituracaoContabil_.dataDocumento));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), EscrituracaoContabil_.data));
            }
            if (criteria.getContaDebitoId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaDebitoId(),
                    root -> root.join(EscrituracaoContabil_.contaDebitos, JoinType.LEFT).get(ContaDebito_.id)));
            }
            if (criteria.getContaCreditoId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaCreditoId(),
                    root -> root.join(EscrituracaoContabil_.contaCreditos, JoinType.LEFT).get(ContaCredito_.id)));
            }
            if (criteria.getUtilizadorId() != null) {
                specification = specification.and(buildSpecification(criteria.getUtilizadorId(),
                    root -> root.join(EscrituracaoContabil_.utilizador, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(EscrituracaoContabil_.empresa, JoinType.LEFT).get(Empresa_.id)));
            }
        }
        return specification;
    }
}
