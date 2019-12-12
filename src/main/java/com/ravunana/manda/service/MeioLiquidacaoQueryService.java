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

import com.ravunana.manda.domain.MeioLiquidacao;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.MeioLiquidacaoRepository;
import com.ravunana.manda.service.dto.MeioLiquidacaoCriteria;
import com.ravunana.manda.service.dto.MeioLiquidacaoDTO;
import com.ravunana.manda.service.mapper.MeioLiquidacaoMapper;

/**
 * Service for executing complex queries for {@link MeioLiquidacao} entities in the database.
 * The main input is a {@link MeioLiquidacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MeioLiquidacaoDTO} or a {@link Page} of {@link MeioLiquidacaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MeioLiquidacaoQueryService extends QueryService<MeioLiquidacao> {

    private final Logger log = LoggerFactory.getLogger(MeioLiquidacaoQueryService.class);

    private final MeioLiquidacaoRepository meioLiquidacaoRepository;

    private final MeioLiquidacaoMapper meioLiquidacaoMapper;

    public MeioLiquidacaoQueryService(MeioLiquidacaoRepository meioLiquidacaoRepository, MeioLiquidacaoMapper meioLiquidacaoMapper) {
        this.meioLiquidacaoRepository = meioLiquidacaoRepository;
        this.meioLiquidacaoMapper = meioLiquidacaoMapper;
    }

    /**
     * Return a {@link List} of {@link MeioLiquidacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MeioLiquidacaoDTO> findByCriteria(MeioLiquidacaoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MeioLiquidacao> specification = createSpecification(criteria);
        return meioLiquidacaoMapper.toDto(meioLiquidacaoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MeioLiquidacaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MeioLiquidacaoDTO> findByCriteria(MeioLiquidacaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MeioLiquidacao> specification = createSpecification(criteria);
        return meioLiquidacaoRepository.findAll(specification, page)
            .map(meioLiquidacaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MeioLiquidacaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MeioLiquidacao> specification = createSpecification(criteria);
        return meioLiquidacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link MeioLiquidacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MeioLiquidacao> createSpecification(MeioLiquidacaoCriteria criteria) {
        Specification<MeioLiquidacao> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MeioLiquidacao_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), MeioLiquidacao_.nome));
            }
            if (criteria.getSigla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSigla(), MeioLiquidacao_.sigla));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), MeioLiquidacao_.icon));
            }
            if (criteria.getMostrarPontoVenda() != null) {
                specification = specification.and(buildSpecification(criteria.getMostrarPontoVenda(), MeioLiquidacao_.mostrarPontoVenda));
            }
            if (criteria.getDetalheLancamentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetalheLancamentoId(),
                    root -> root.join(MeioLiquidacao_.detalheLancamentos, JoinType.LEFT).get(DetalheLancamento_.id)));
            }
        }
        return specification;
    }
}
