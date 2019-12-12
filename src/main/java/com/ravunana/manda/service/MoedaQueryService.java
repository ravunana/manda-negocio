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

import com.ravunana.manda.domain.Moeda;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.MoedaRepository;
import com.ravunana.manda.service.dto.MoedaCriteria;
import com.ravunana.manda.service.dto.MoedaDTO;
import com.ravunana.manda.service.mapper.MoedaMapper;

/**
 * Service for executing complex queries for {@link Moeda} entities in the database.
 * The main input is a {@link MoedaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MoedaDTO} or a {@link Page} of {@link MoedaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MoedaQueryService extends QueryService<Moeda> {

    private final Logger log = LoggerFactory.getLogger(MoedaQueryService.class);

    private final MoedaRepository moedaRepository;

    private final MoedaMapper moedaMapper;

    public MoedaQueryService(MoedaRepository moedaRepository, MoedaMapper moedaMapper) {
        this.moedaRepository = moedaRepository;
        this.moedaMapper = moedaMapper;
    }

    /**
     * Return a {@link List} of {@link MoedaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MoedaDTO> findByCriteria(MoedaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Moeda> specification = createSpecification(criteria);
        return moedaMapper.toDto(moedaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MoedaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MoedaDTO> findByCriteria(MoedaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Moeda> specification = createSpecification(criteria);
        return moedaRepository.findAll(specification, page)
            .map(moedaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MoedaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Moeda> specification = createSpecification(criteria);
        return moedaRepository.count(specification);
    }

    /**
     * Function to convert {@link MoedaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Moeda> createSpecification(MoedaCriteria criteria) {
        Specification<Moeda> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Moeda_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Moeda_.nome));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), Moeda_.codigo));
            }
            if (criteria.getPais() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPais(), Moeda_.pais));
            }
            if (criteria.getNacional() != null) {
                specification = specification.and(buildSpecification(criteria.getNacional(), Moeda_.nacional));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), Moeda_.icon));
            }
            if (criteria.getDetalheLancamentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetalheLancamentoId(),
                    root -> root.join(Moeda_.detalheLancamentos, JoinType.LEFT).get(DetalheLancamento_.id)));
            }
        }
        return specification;
    }
}
