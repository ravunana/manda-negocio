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

import com.ravunana.manda.domain.DetalheLancamento;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.DetalheLancamentoRepository;
import com.ravunana.manda.service.dto.DetalheLancamentoCriteria;
import com.ravunana.manda.service.dto.DetalheLancamentoDTO;
import com.ravunana.manda.service.mapper.DetalheLancamentoMapper;

/**
 * Service for executing complex queries for {@link DetalheLancamento} entities in the database.
 * The main input is a {@link DetalheLancamentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DetalheLancamentoDTO} or a {@link Page} of {@link DetalheLancamentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DetalheLancamentoQueryService extends QueryService<DetalheLancamento> {

    private final Logger log = LoggerFactory.getLogger(DetalheLancamentoQueryService.class);

    private final DetalheLancamentoRepository detalheLancamentoRepository;

    private final DetalheLancamentoMapper detalheLancamentoMapper;

    public DetalheLancamentoQueryService(DetalheLancamentoRepository detalheLancamentoRepository, DetalheLancamentoMapper detalheLancamentoMapper) {
        this.detalheLancamentoRepository = detalheLancamentoRepository;
        this.detalheLancamentoMapper = detalheLancamentoMapper;
    }

    /**
     * Return a {@link List} of {@link DetalheLancamentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DetalheLancamentoDTO> findByCriteria(DetalheLancamentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DetalheLancamento> specification = createSpecification(criteria);
        return detalheLancamentoMapper.toDto(detalheLancamentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DetalheLancamentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalheLancamentoDTO> findByCriteria(DetalheLancamentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DetalheLancamento> specification = createSpecification(criteria);
        return detalheLancamentoRepository.findAll(specification, page)
            .map(detalheLancamentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DetalheLancamentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DetalheLancamento> specification = createSpecification(criteria);
        return detalheLancamentoRepository.count(specification);
    }

    /**
     * Function to convert {@link DetalheLancamentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DetalheLancamento> createSpecification(DetalheLancamentoCriteria criteria) {
        Specification<DetalheLancamento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DetalheLancamento_.id));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), DetalheLancamento_.valor));
            }
            if (criteria.getDesconto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDesconto(), DetalheLancamento_.desconto));
            }
            if (criteria.getJuro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJuro(), DetalheLancamento_.juro));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), DetalheLancamento_.data));
            }
            if (criteria.getRetencaoFonte() != null) {
                specification = specification.and(buildSpecification(criteria.getRetencaoFonte(), DetalheLancamento_.retencaoFonte));
            }
            if (criteria.getDataVencimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataVencimento(), DetalheLancamento_.dataVencimento));
            }
            if (criteria.getLiquidado() != null) {
                specification = specification.and(buildSpecification(criteria.getLiquidado(), DetalheLancamento_.liquidado));
            }
            if (criteria.getRetencaoFonteId() != null) {
                specification = specification.and(buildSpecification(criteria.getRetencaoFonteId(),
                    root -> root.join(DetalheLancamento_.retencaoFontes, JoinType.LEFT).get(RetencaoFonte_.id)));
            }
            if (criteria.getUtilizadorId() != null) {
                specification = specification.and(buildSpecification(criteria.getUtilizadorId(),
                    root -> root.join(DetalheLancamento_.utilizador, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getLancamentoFinanceiroId() != null) {
                specification = specification.and(buildSpecification(criteria.getLancamentoFinanceiroId(),
                    root -> root.join(DetalheLancamento_.lancamentoFinanceiro, JoinType.LEFT).get(LancamentoFinanceiro_.id)));
            }
            if (criteria.getMetodoLiquidacaoId() != null) {
                specification = specification.and(buildSpecification(criteria.getMetodoLiquidacaoId(),
                    root -> root.join(DetalheLancamento_.metodoLiquidacao, JoinType.LEFT).get(MeioLiquidacao_.id)));
            }
            if (criteria.getMoedaId() != null) {
                specification = specification.and(buildSpecification(criteria.getMoedaId(),
                    root -> root.join(DetalheLancamento_.moeda, JoinType.LEFT).get(Moeda_.id)));
            }
            if (criteria.getCoordenadaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCoordenadaId(),
                    root -> root.join(DetalheLancamento_.coordenada, JoinType.LEFT).get(CoordenadaBancaria_.id)));
            }
        }
        return specification;
    }
}
