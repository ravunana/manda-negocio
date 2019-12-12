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

import com.ravunana.manda.domain.FluxoDocumento;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.FluxoDocumentoRepository;
import com.ravunana.manda.service.dto.FluxoDocumentoCriteria;
import com.ravunana.manda.service.dto.FluxoDocumentoDTO;
import com.ravunana.manda.service.mapper.FluxoDocumentoMapper;

/**
 * Service for executing complex queries for {@link FluxoDocumento} entities in the database.
 * The main input is a {@link FluxoDocumentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FluxoDocumentoDTO} or a {@link Page} of {@link FluxoDocumentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FluxoDocumentoQueryService extends QueryService<FluxoDocumento> {

    private final Logger log = LoggerFactory.getLogger(FluxoDocumentoQueryService.class);

    private final FluxoDocumentoRepository fluxoDocumentoRepository;

    private final FluxoDocumentoMapper fluxoDocumentoMapper;

    public FluxoDocumentoQueryService(FluxoDocumentoRepository fluxoDocumentoRepository, FluxoDocumentoMapper fluxoDocumentoMapper) {
        this.fluxoDocumentoRepository = fluxoDocumentoRepository;
        this.fluxoDocumentoMapper = fluxoDocumentoMapper;
    }

    /**
     * Return a {@link List} of {@link FluxoDocumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FluxoDocumentoDTO> findByCriteria(FluxoDocumentoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FluxoDocumento> specification = createSpecification(criteria);
        return fluxoDocumentoMapper.toDto(fluxoDocumentoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FluxoDocumentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FluxoDocumentoDTO> findByCriteria(FluxoDocumentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FluxoDocumento> specification = createSpecification(criteria);
        return fluxoDocumentoRepository.findAll(specification, page)
            .map(fluxoDocumentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FluxoDocumentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FluxoDocumento> specification = createSpecification(criteria);
        return fluxoDocumentoRepository.count(specification);
    }

    /**
     * Function to convert {@link FluxoDocumentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FluxoDocumento> createSpecification(FluxoDocumentoCriteria criteria) {
        Specification<FluxoDocumento> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FluxoDocumento_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), FluxoDocumento_.nome));
            }
            if (criteria.getAumentaEstoque() != null) {
                specification = specification.and(buildSpecification(criteria.getAumentaEstoque(), FluxoDocumento_.aumentaEstoque));
            }
            if (criteria.getAumentaValorCaixa() != null) {
                specification = specification.and(buildSpecification(criteria.getAumentaValorCaixa(), FluxoDocumento_.aumentaValorCaixa));
            }
            if (criteria.getCor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCor(), FluxoDocumento_.cor));
            }
            if (criteria.getPadrao() != null) {
                specification = specification.and(buildSpecification(criteria.getPadrao(), FluxoDocumento_.padrao));
            }
            if (criteria.getEntidade() != null) {
                specification = specification.and(buildSpecification(criteria.getEntidade(), FluxoDocumento_.entidade));
            }
            if (criteria.getProdutoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoId(),
                    root -> root.join(FluxoDocumento_.produtos, JoinType.LEFT).get(Produto_.id)));
            }
            if (criteria.getItemCompraId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemCompraId(),
                    root -> root.join(FluxoDocumento_.itemCompras, JoinType.LEFT).get(ItemCompra_.id)));
            }
            if (criteria.getItemVendaId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemVendaId(),
                    root -> root.join(FluxoDocumento_.itemVendas, JoinType.LEFT).get(ItemVenda_.id)));
            }
        }
        return specification;
    }
}
