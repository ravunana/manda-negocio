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

import com.ravunana.manda.domain.LookupItem;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.LookupItemRepository;
import com.ravunana.manda.service.dto.LookupItemCriteria;
import com.ravunana.manda.service.dto.LookupItemDTO;
import com.ravunana.manda.service.mapper.LookupItemMapper;

/**
 * Service for executing complex queries for {@link LookupItem} entities in the database.
 * The main input is a {@link LookupItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LookupItemDTO} or a {@link Page} of {@link LookupItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LookupItemQueryService extends QueryService<LookupItem> {

    private final Logger log = LoggerFactory.getLogger(LookupItemQueryService.class);

    private final LookupItemRepository lookupItemRepository;

    private final LookupItemMapper lookupItemMapper;

    public LookupItemQueryService(LookupItemRepository lookupItemRepository, LookupItemMapper lookupItemMapper) {
        this.lookupItemRepository = lookupItemRepository;
        this.lookupItemMapper = lookupItemMapper;
    }

    /**
     * Return a {@link List} of {@link LookupItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LookupItemDTO> findByCriteria(LookupItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LookupItem> specification = createSpecification(criteria);
        return lookupItemMapper.toDto(lookupItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LookupItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LookupItemDTO> findByCriteria(LookupItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LookupItem> specification = createSpecification(criteria);
        return lookupItemRepository.findAll(specification, page)
            .map(lookupItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LookupItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LookupItem> specification = createSpecification(criteria);
        return lookupItemRepository.count(specification);
    }

    /**
     * Function to convert {@link LookupItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LookupItem> createSpecification(LookupItemCriteria criteria) {
        Specification<LookupItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LookupItem_.id));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValor(), LookupItem_.valor));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), LookupItem_.nome));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), LookupItem_.type));
            }
            if (criteria.getOrdem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrdem(), LookupItem_.ordem));
            }
            if (criteria.getLookupId() != null) {
                specification = specification.and(buildSpecification(criteria.getLookupId(),
                    root -> root.join(LookupItem_.lookup, JoinType.LEFT).get(Lookup_.id)));
            }
        }
        return specification;
    }
}
