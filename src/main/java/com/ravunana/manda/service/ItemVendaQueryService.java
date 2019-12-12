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

import com.ravunana.manda.domain.ItemVenda;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.ItemVendaRepository;
import com.ravunana.manda.service.dto.ItemVendaCriteria;
import com.ravunana.manda.service.dto.ItemVendaDTO;
import com.ravunana.manda.service.mapper.ItemVendaMapper;

/**
 * Service for executing complex queries for {@link ItemVenda} entities in the database.
 * The main input is a {@link ItemVendaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemVendaDTO} or a {@link Page} of {@link ItemVendaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemVendaQueryService extends QueryService<ItemVenda> {

    private final Logger log = LoggerFactory.getLogger(ItemVendaQueryService.class);

    private final ItemVendaRepository itemVendaRepository;

    private final ItemVendaMapper itemVendaMapper;

    public ItemVendaQueryService(ItemVendaRepository itemVendaRepository, ItemVendaMapper itemVendaMapper) {
        this.itemVendaRepository = itemVendaRepository;
        this.itemVendaMapper = itemVendaMapper;
    }

    /**
     * Return a {@link List} of {@link ItemVendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemVendaDTO> findByCriteria(ItemVendaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ItemVenda> specification = createSpecification(criteria);
        return itemVendaMapper.toDto(itemVendaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemVendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemVendaDTO> findByCriteria(ItemVendaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ItemVenda> specification = createSpecification(criteria);
        return itemVendaRepository.findAll(specification, page)
            .map(itemVendaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemVendaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ItemVenda> specification = createSpecification(criteria);
        return itemVendaRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemVendaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ItemVenda> createSpecification(ItemVendaCriteria criteria) {
        Specification<ItemVenda> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ItemVenda_.id));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), ItemVenda_.quantidade));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), ItemVenda_.valor));
            }
            if (criteria.getDesconto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDesconto(), ItemVenda_.desconto));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), ItemVenda_.data));
            }
            if (criteria.getVendaId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendaId(),
                    root -> root.join(ItemVenda_.venda, JoinType.LEFT).get(Venda_.id)));
            }
            if (criteria.getProdutoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoId(),
                    root -> root.join(ItemVenda_.produto, JoinType.LEFT).get(Produto_.id)));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusId(),
                    root -> root.join(ItemVenda_.status, JoinType.LEFT).get(FluxoDocumento_.id)));
            }
        }
        return specification;
    }
}
