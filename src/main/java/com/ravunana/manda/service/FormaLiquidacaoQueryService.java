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

import com.ravunana.manda.domain.FormaLiquidacao;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.FormaLiquidacaoRepository;
import com.ravunana.manda.service.dto.FormaLiquidacaoCriteria;
import com.ravunana.manda.service.dto.FormaLiquidacaoDTO;
import com.ravunana.manda.service.mapper.FormaLiquidacaoMapper;

/**
 * Service for executing complex queries for {@link FormaLiquidacao} entities in the database.
 * The main input is a {@link FormaLiquidacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FormaLiquidacaoDTO} or a {@link Page} of {@link FormaLiquidacaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FormaLiquidacaoQueryService extends QueryService<FormaLiquidacao> {

    private final Logger log = LoggerFactory.getLogger(FormaLiquidacaoQueryService.class);

    private final FormaLiquidacaoRepository formaLiquidacaoRepository;

    private final FormaLiquidacaoMapper formaLiquidacaoMapper;

    public FormaLiquidacaoQueryService(FormaLiquidacaoRepository formaLiquidacaoRepository, FormaLiquidacaoMapper formaLiquidacaoMapper) {
        this.formaLiquidacaoRepository = formaLiquidacaoRepository;
        this.formaLiquidacaoMapper = formaLiquidacaoMapper;
    }

    /**
     * Return a {@link List} of {@link FormaLiquidacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FormaLiquidacaoDTO> findByCriteria(FormaLiquidacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FormaLiquidacao> specification = createSpecification(criteria);
        return formaLiquidacaoMapper.toDto(formaLiquidacaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FormaLiquidacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FormaLiquidacaoDTO> findByCriteria(FormaLiquidacaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FormaLiquidacao> specification = createSpecification(criteria);
        return formaLiquidacaoRepository.findAll(specification, page)
            .map(formaLiquidacaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FormaLiquidacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FormaLiquidacao> specification = createSpecification(criteria);
        return formaLiquidacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link FormaLiquidacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FormaLiquidacao> createSpecification(FormaLiquidacaoCriteria criteria) {
        Specification<FormaLiquidacao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FormaLiquidacao_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), FormaLiquidacao_.nome));
            }
            if (criteria.getJuro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJuro(), FormaLiquidacao_.juro));
            }
            if (criteria.getPrazoLiquidacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrazoLiquidacao(), FormaLiquidacao_.prazoLiquidacao));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), FormaLiquidacao_.quantidade));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), FormaLiquidacao_.icon));
            }
            if (criteria.getLancamentoFinanceiroId() != null) {
                specification = specification.and(buildSpecification(criteria.getLancamentoFinanceiroId(),
                    root -> root.join(FormaLiquidacao_.lancamentoFinanceiros, JoinType.LEFT).get(LancamentoFinanceiro_.id)));
            }
        }
        return specification;
    }
}
