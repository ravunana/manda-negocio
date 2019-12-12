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

import com.ravunana.manda.domain.Fornecedor;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.FornecedorRepository;
import com.ravunana.manda.service.dto.FornecedorCriteria;
import com.ravunana.manda.service.dto.FornecedorDTO;
import com.ravunana.manda.service.mapper.FornecedorMapper;

/**
 * Service for executing complex queries for {@link Fornecedor} entities in the database.
 * The main input is a {@link FornecedorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FornecedorDTO} or a {@link Page} of {@link FornecedorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FornecedorQueryService extends QueryService<Fornecedor> {

    private final Logger log = LoggerFactory.getLogger(FornecedorQueryService.class);

    private final FornecedorRepository fornecedorRepository;

    private final FornecedorMapper fornecedorMapper;

    public FornecedorQueryService(FornecedorRepository fornecedorRepository, FornecedorMapper fornecedorMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    /**
     * Return a {@link List} of {@link FornecedorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FornecedorDTO> findByCriteria(FornecedorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fornecedor> specification = createSpecification(criteria);
        return fornecedorMapper.toDto(fornecedorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FornecedorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorDTO> findByCriteria(FornecedorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fornecedor> specification = createSpecification(criteria);
        return fornecedorRepository.findAll(specification, page)
            .map(fornecedorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FornecedorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fornecedor> specification = createSpecification(criteria);
        return fornecedorRepository.count(specification);
    }

    /**
     * Function to convert {@link FornecedorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Fornecedor> createSpecification(FornecedorCriteria criteria) {
        Specification<Fornecedor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Fornecedor_.id));
            }
            if (criteria.getCertificadoISO9001() != null) {
                specification = specification.and(buildSpecification(criteria.getCertificadoISO9001(), Fornecedor_.certificadoISO9001));
            }
            if (criteria.getGarantiaDefeitoFabrica() != null) {
                specification = specification.and(buildSpecification(criteria.getGarantiaDefeitoFabrica(), Fornecedor_.garantiaDefeitoFabrica));
            }
            if (criteria.getTransporte() != null) {
                specification = specification.and(buildSpecification(criteria.getTransporte(), Fornecedor_.transporte));
            }
            if (criteria.getQualidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQualidade(), Fornecedor_.qualidade));
            }
            if (criteria.getPagamentoPrazo() != null) {
                specification = specification.and(buildSpecification(criteria.getPagamentoPrazo(), Fornecedor_.pagamentoPrazo));
            }
            if (criteria.getClassificacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getClassificacao(), Fornecedor_.classificacao));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), Fornecedor_.ativo));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Fornecedor_.numero));
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(buildSpecification(criteria.getPessoaId(),
                    root -> root.join(Fornecedor_.pessoa, JoinType.LEFT).get(Pessoa_.id)));
            }
            if (criteria.getItemCompraId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemCompraId(),
                    root -> root.join(Fornecedor_.itemCompras, JoinType.LEFT).get(ItemCompra_.id)));
            }
            if (criteria.getContaId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaId(),
                    root -> root.join(Fornecedor_.conta, JoinType.LEFT).get(Conta_.id)));
            }
            if (criteria.getProdutoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoId(),
                    root -> root.join(Fornecedor_.produtos, JoinType.LEFT).get(Produto_.id)));
            }
        }
        return specification;
    }
}
