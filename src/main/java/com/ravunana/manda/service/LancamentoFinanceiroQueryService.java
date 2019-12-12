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

import com.ravunana.manda.domain.LancamentoFinanceiro;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.LancamentoFinanceiroRepository;
import com.ravunana.manda.service.dto.LancamentoFinanceiroCriteria;
import com.ravunana.manda.service.dto.LancamentoFinanceiroDTO;
import com.ravunana.manda.service.mapper.LancamentoFinanceiroMapper;

/**
 * Service for executing complex queries for {@link LancamentoFinanceiro} entities in the database.
 * The main input is a {@link LancamentoFinanceiroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LancamentoFinanceiroDTO} or a {@link Page} of {@link LancamentoFinanceiroDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LancamentoFinanceiroQueryService extends QueryService<LancamentoFinanceiro> {

    private final Logger log = LoggerFactory.getLogger(LancamentoFinanceiroQueryService.class);

    private final LancamentoFinanceiroRepository lancamentoFinanceiroRepository;

    private final LancamentoFinanceiroMapper lancamentoFinanceiroMapper;

    public LancamentoFinanceiroQueryService(LancamentoFinanceiroRepository lancamentoFinanceiroRepository, LancamentoFinanceiroMapper lancamentoFinanceiroMapper) {
        this.lancamentoFinanceiroRepository = lancamentoFinanceiroRepository;
        this.lancamentoFinanceiroMapper = lancamentoFinanceiroMapper;
    }

    /**
     * Return a {@link List} of {@link LancamentoFinanceiroDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LancamentoFinanceiroDTO> findByCriteria(LancamentoFinanceiroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LancamentoFinanceiro> specification = createSpecification(criteria);
        return lancamentoFinanceiroMapper.toDto(lancamentoFinanceiroRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LancamentoFinanceiroDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LancamentoFinanceiroDTO> findByCriteria(LancamentoFinanceiroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LancamentoFinanceiro> specification = createSpecification(criteria);
        return lancamentoFinanceiroRepository.findAll(specification, page)
            .map(lancamentoFinanceiroMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LancamentoFinanceiroCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LancamentoFinanceiro> specification = createSpecification(criteria);
        return lancamentoFinanceiroRepository.count(specification);
    }

    /**
     * Function to convert {@link LancamentoFinanceiroCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LancamentoFinanceiro> createSpecification(LancamentoFinanceiroCriteria criteria) {
        Specification<LancamentoFinanceiro> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LancamentoFinanceiro_.id));
            }
            if (criteria.getTipoLancamento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoLancamento(), LancamentoFinanceiro_.tipoLancamento));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), LancamentoFinanceiro_.valor));
            }
            if (criteria.getExterno() != null) {
                specification = specification.and(buildSpecification(criteria.getExterno(), LancamentoFinanceiro_.externo));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), LancamentoFinanceiro_.numero));
            }
            if (criteria.getDetalheLancamentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetalheLancamentoId(),
                    root -> root.join(LancamentoFinanceiro_.detalheLancamentos, JoinType.LEFT).get(DetalheLancamento_.id)));
            }
            if (criteria.getUtilizadorId() != null) {
                specification = specification.and(buildSpecification(criteria.getUtilizadorId(),
                    root -> root.join(LancamentoFinanceiro_.utilizador, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getContaId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaId(),
                    root -> root.join(LancamentoFinanceiro_.conta, JoinType.LEFT).get(Conta_.id)));
            }
            if (criteria.getImpostoId() != null) {
                specification = specification.and(buildSpecification(criteria.getImpostoId(),
                    root -> root.join(LancamentoFinanceiro_.impostos, JoinType.LEFT).get(Imposto_.id)));
            }
            if (criteria.getFormaLiquidacaoId() != null) {
                specification = specification.and(buildSpecification(criteria.getFormaLiquidacaoId(),
                    root -> root.join(LancamentoFinanceiro_.formaLiquidacao, JoinType.LEFT).get(FormaLiquidacao_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(LancamentoFinanceiro_.empresa, JoinType.LEFT).get(Empresa_.id)));
            }
            if (criteria.getTipoReciboId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoReciboId(),
                    root -> root.join(LancamentoFinanceiro_.tipoRecibo, JoinType.LEFT).get(DocumentoComercial_.id)));
            }
        }
        return specification;
    }
}
