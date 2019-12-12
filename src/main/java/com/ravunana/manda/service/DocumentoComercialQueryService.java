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

import com.ravunana.manda.domain.DocumentoComercial;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.DocumentoComercialRepository;
import com.ravunana.manda.service.dto.DocumentoComercialCriteria;
import com.ravunana.manda.service.dto.DocumentoComercialDTO;
import com.ravunana.manda.service.mapper.DocumentoComercialMapper;

/**
 * Service for executing complex queries for {@link DocumentoComercial} entities in the database.
 * The main input is a {@link DocumentoComercialCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocumentoComercialDTO} or a {@link Page} of {@link DocumentoComercialDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentoComercialQueryService extends QueryService<DocumentoComercial> {

    private final Logger log = LoggerFactory.getLogger(DocumentoComercialQueryService.class);

    private final DocumentoComercialRepository documentoComercialRepository;

    private final DocumentoComercialMapper documentoComercialMapper;

    public DocumentoComercialQueryService(DocumentoComercialRepository documentoComercialRepository, DocumentoComercialMapper documentoComercialMapper) {
        this.documentoComercialRepository = documentoComercialRepository;
        this.documentoComercialMapper = documentoComercialMapper;
    }

    /**
     * Return a {@link List} of {@link DocumentoComercialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentoComercialDTO> findByCriteria(DocumentoComercialCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DocumentoComercial> specification = createSpecification(criteria);
        return documentoComercialMapper.toDto(documentoComercialRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DocumentoComercialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentoComercialDTO> findByCriteria(DocumentoComercialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DocumentoComercial> specification = createSpecification(criteria);
        return documentoComercialRepository.findAll(specification, page)
            .map(documentoComercialMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentoComercialCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DocumentoComercial> specification = createSpecification(criteria);
        return documentoComercialRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentoComercialCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DocumentoComercial> createSpecification(DocumentoComercialCriteria criteria) {
        Specification<DocumentoComercial> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DocumentoComercial_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), DocumentoComercial_.nome));
            }
            if (criteria.getSerie() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerie(), DocumentoComercial_.serie));
            }
            if (criteria.getPadrao() != null) {
                specification = specification.and(buildSpecification(criteria.getPadrao(), DocumentoComercial_.padrao));
            }
            if (criteria.getMovimentaEstoque() != null) {
                specification = specification.and(buildSpecification(criteria.getMovimentaEstoque(), DocumentoComercial_.movimentaEstoque));
            }
            if (criteria.getMovimentaCaixa() != null) {
                specification = specification.and(buildSpecification(criteria.getMovimentaCaixa(), DocumentoComercial_.movimentaCaixa));
            }
            if (criteria.getMovimentaContabilidade() != null) {
                specification = specification.and(buildSpecification(criteria.getMovimentaContabilidade(), DocumentoComercial_.movimentaContabilidade));
            }
            if (criteria.getCor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCor(), DocumentoComercial_.cor));
            }
            if (criteria.getMostraPontoVenda() != null) {
                specification = specification.and(buildSpecification(criteria.getMostraPontoVenda(), DocumentoComercial_.mostraPontoVenda));
            }
            if (criteria.getEntidade() != null) {
                specification = specification.and(buildSpecification(criteria.getEntidade(), DocumentoComercial_.entidade));
            }
            if (criteria.getLancamentoFinanceiroId() != null) {
                specification = specification.and(buildSpecification(criteria.getLancamentoFinanceiroId(),
                    root -> root.join(DocumentoComercial_.lancamentoFinanceiros, JoinType.LEFT).get(LancamentoFinanceiro_.id)));
            }
            if (criteria.getCompraId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompraId(),
                    root -> root.join(DocumentoComercial_.compras, JoinType.LEFT).get(Compra_.id)));
            }
            if (criteria.getVendaId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendaId(),
                    root -> root.join(DocumentoComercial_.vendas, JoinType.LEFT).get(Venda_.id)));
            }
        }
        return specification;
    }
}
