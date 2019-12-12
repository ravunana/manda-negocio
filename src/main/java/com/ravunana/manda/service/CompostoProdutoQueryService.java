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

import com.ravunana.manda.domain.CompostoProduto;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.CompostoProdutoRepository;
import com.ravunana.manda.service.dto.CompostoProdutoCriteria;
import com.ravunana.manda.service.dto.CompostoProdutoDTO;
import com.ravunana.manda.service.mapper.CompostoProdutoMapper;

/**
 * Service for executing complex queries for {@link CompostoProduto} entities in the database.
 * The main input is a {@link CompostoProdutoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompostoProdutoDTO} or a {@link Page} of {@link CompostoProdutoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompostoProdutoQueryService extends QueryService<CompostoProduto> {

    private final Logger log = LoggerFactory.getLogger(CompostoProdutoQueryService.class);

    private final CompostoProdutoRepository compostoProdutoRepository;

    private final CompostoProdutoMapper compostoProdutoMapper;

    public CompostoProdutoQueryService(CompostoProdutoRepository compostoProdutoRepository, CompostoProdutoMapper compostoProdutoMapper) {
        this.compostoProdutoRepository = compostoProdutoRepository;
        this.compostoProdutoMapper = compostoProdutoMapper;
    }

    /**
     * Return a {@link List} of {@link CompostoProdutoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompostoProdutoDTO> findByCriteria(CompostoProdutoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompostoProduto> specification = createSpecification(criteria);
        return compostoProdutoMapper.toDto(compostoProdutoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompostoProdutoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompostoProdutoDTO> findByCriteria(CompostoProdutoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompostoProduto> specification = createSpecification(criteria);
        return compostoProdutoRepository.findAll(specification, page)
            .map(compostoProdutoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompostoProdutoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompostoProduto> specification = createSpecification(criteria);
        return compostoProdutoRepository.count(specification);
    }

    /**
     * Function to convert {@link CompostoProdutoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompostoProduto> createSpecification(CompostoProdutoCriteria criteria) {
        Specification<CompostoProduto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompostoProduto_.id));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), CompostoProduto_.quantidade));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), CompostoProduto_.valor));
            }
            if (criteria.getPermanente() != null) {
                specification = specification.and(buildSpecification(criteria.getPermanente(), CompostoProduto_.permanente));
            }
            if (criteria.getProdutoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoId(),
                    root -> root.join(CompostoProduto_.produto, JoinType.LEFT).get(Produto_.id)));
            }
            if (criteria.getUnidadeId() != null) {
                specification = specification.and(buildSpecification(criteria.getUnidadeId(),
                    root -> root.join(CompostoProduto_.unidade, JoinType.LEFT).get(UnidadeMedida_.id)));
            }
            if (criteria.getCompostoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompostoId(),
                    root -> root.join(CompostoProduto_.composto, JoinType.LEFT).get(Produto_.id)));
            }
        }
        return specification;
    }
}
