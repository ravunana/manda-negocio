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

import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.ContaRepository;
import com.ravunana.manda.service.dto.ContaCriteria;
import com.ravunana.manda.service.dto.ContaDTO;
import com.ravunana.manda.service.mapper.ContaMapper;

/**
 * Service for executing complex queries for {@link Conta} entities in the database.
 * The main input is a {@link ContaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContaDTO} or a {@link Page} of {@link ContaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContaQueryService extends QueryService<Conta> {

    private final Logger log = LoggerFactory.getLogger(ContaQueryService.class);

    private final ContaRepository contaRepository;

    private final ContaMapper contaMapper;

    public ContaQueryService(ContaRepository contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    /**
     * Return a {@link List} of {@link ContaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContaDTO> findByCriteria(ContaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Conta> specification = createSpecification(criteria);
        return contaMapper.toDto(contaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContaDTO> findByCriteria(ContaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Conta> specification = createSpecification(criteria);
        return contaRepository.findAll(specification, page)
            .map(contaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Conta> specification = createSpecification(criteria);
        return contaRepository.count(specification);
    }

    /**
     * Function to convert {@link ContaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Conta> createSpecification(ContaCriteria criteria) {
        Specification<Conta> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Conta_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Conta_.descricao));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), Conta_.codigo));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), Conta_.tipo));
            }
            if (criteria.getGrau() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrau(), Conta_.grau));
            }
            if (criteria.getNatureza() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNatureza(), Conta_.natureza));
            }
            if (criteria.getGrupo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrupo(), Conta_.grupo));
            }
            if (criteria.getContaId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaId(),
                    root -> root.join(Conta_.contas, JoinType.LEFT).get(Conta_.id)));
            }
            if (criteria.getContaDebitoId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaDebitoId(),
                    root -> root.join(Conta_.contaDebitos, JoinType.LEFT).get(ContaDebito_.id)));
            }
            if (criteria.getContaCreditoId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaCreditoId(),
                    root -> root.join(Conta_.contaCreditos, JoinType.LEFT).get(ContaCredito_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(Conta_.empresas, JoinType.LEFT).get(Empresa_.id)));
            }
            if (criteria.getContaAgregadoraId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaAgregadoraId(),
                    root -> root.join(Conta_.contaAgregadora, JoinType.LEFT).get(Conta_.id)));
            }
            if (criteria.getClasseContaId() != null) {
                specification = specification.and(buildSpecification(criteria.getClasseContaId(),
                    root -> root.join(Conta_.classeConta, JoinType.LEFT).get(ClasseConta_.id)));
            }
        }
        return specification;
    }
}
