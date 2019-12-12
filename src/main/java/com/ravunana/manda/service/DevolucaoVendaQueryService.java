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

import com.ravunana.manda.domain.DevolucaoVenda;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.DevolucaoVendaRepository;
import com.ravunana.manda.service.dto.DevolucaoVendaCriteria;
import com.ravunana.manda.service.dto.DevolucaoVendaDTO;
import com.ravunana.manda.service.mapper.DevolucaoVendaMapper;

/**
 * Service for executing complex queries for {@link DevolucaoVenda} entities in the database.
 * The main input is a {@link DevolucaoVendaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DevolucaoVendaDTO} or a {@link Page} of {@link DevolucaoVendaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DevolucaoVendaQueryService extends QueryService<DevolucaoVenda> {

    private final Logger log = LoggerFactory.getLogger(DevolucaoVendaQueryService.class);

    private final DevolucaoVendaRepository devolucaoVendaRepository;

    private final DevolucaoVendaMapper devolucaoVendaMapper;

    public DevolucaoVendaQueryService(DevolucaoVendaRepository devolucaoVendaRepository, DevolucaoVendaMapper devolucaoVendaMapper) {
        this.devolucaoVendaRepository = devolucaoVendaRepository;
        this.devolucaoVendaMapper = devolucaoVendaMapper;
    }

    /**
     * Return a {@link List} of {@link DevolucaoVendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DevolucaoVendaDTO> findByCriteria(DevolucaoVendaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DevolucaoVenda> specification = createSpecification(criteria);
        return devolucaoVendaMapper.toDto(devolucaoVendaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DevolucaoVendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DevolucaoVendaDTO> findByCriteria(DevolucaoVendaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DevolucaoVenda> specification = createSpecification(criteria);
        return devolucaoVendaRepository.findAll(specification, page)
            .map(devolucaoVendaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DevolucaoVendaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DevolucaoVenda> specification = createSpecification(criteria);
        return devolucaoVendaRepository.count(specification);
    }

    /**
     * Function to convert {@link DevolucaoVendaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DevolucaoVenda> createSpecification(DevolucaoVendaCriteria criteria) {
        Specification<DevolucaoVenda> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DevolucaoVenda_.id));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), DevolucaoVenda_.quantidade));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), DevolucaoVenda_.valor));
            }
            if (criteria.getDesconto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDesconto(), DevolucaoVenda_.desconto));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), DevolucaoVenda_.data));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemId(),
                    root -> root.join(DevolucaoVenda_.item, JoinType.LEFT).get(ItemVenda_.id)));
            }
        }
        return specification;
    }
}
