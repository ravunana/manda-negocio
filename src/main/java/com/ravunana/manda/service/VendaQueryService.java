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

import com.ravunana.manda.domain.Venda;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.VendaRepository;
import com.ravunana.manda.service.dto.VendaCriteria;
import com.ravunana.manda.service.dto.VendaDTO;
import com.ravunana.manda.service.mapper.VendaMapper;

/**
 * Service for executing complex queries for {@link Venda} entities in the database.
 * The main input is a {@link VendaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VendaDTO} or a {@link Page} of {@link VendaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VendaQueryService extends QueryService<Venda> {

    private final Logger log = LoggerFactory.getLogger(VendaQueryService.class);

    private final VendaRepository vendaRepository;

    private final VendaMapper vendaMapper;

    public VendaQueryService(VendaRepository vendaRepository, VendaMapper vendaMapper) {
        this.vendaRepository = vendaRepository;
        this.vendaMapper = vendaMapper;
    }

    /**
     * Return a {@link List} of {@link VendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VendaDTO> findByCriteria(VendaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Venda> specification = createSpecification(criteria);
        return vendaMapper.toDto(vendaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VendaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VendaDTO> findByCriteria(VendaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Venda> specification = createSpecification(criteria);
        return vendaRepository.findAll(specification, page)
            .map(vendaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VendaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Venda> specification = createSpecification(criteria);
        return vendaRepository.count(specification);
    }

    /**
     * Function to convert {@link VendaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Venda> createSpecification(VendaCriteria criteria) {
        Specification<Venda> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Venda_.id));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Venda_.numero));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), Venda_.data));
            }
            if (criteria.getItemVendaId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemVendaId(),
                    root -> root.join(Venda_.itemVendas, JoinType.LEFT).get(ItemVenda_.id)));
            }
            if (criteria.getVendedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendedorId(),
                    root -> root.join(Venda_.vendedor, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getClienteId() != null) {
                specification = specification.and(buildSpecification(criteria.getClienteId(),
                    root -> root.join(Venda_.cliente, JoinType.LEFT).get(Cliente_.id)));
            }
            if (criteria.getTipoDocumentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoDocumentoId(),
                    root -> root.join(Venda_.tipoDocumento, JoinType.LEFT).get(DocumentoComercial_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(Venda_.empresa, JoinType.LEFT).get(Empresa_.id)));
            }
        }
        return specification;
    }
}
