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

import com.ravunana.manda.domain.CoordenadaBancaria;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.CoordenadaBancariaRepository;
import com.ravunana.manda.service.dto.CoordenadaBancariaCriteria;
import com.ravunana.manda.service.dto.CoordenadaBancariaDTO;
import com.ravunana.manda.service.mapper.CoordenadaBancariaMapper;

/**
 * Service for executing complex queries for {@link CoordenadaBancaria} entities in the database.
 * The main input is a {@link CoordenadaBancariaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CoordenadaBancariaDTO} or a {@link Page} of {@link CoordenadaBancariaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CoordenadaBancariaQueryService extends QueryService<CoordenadaBancaria> {

    private final Logger log = LoggerFactory.getLogger(CoordenadaBancariaQueryService.class);

    private final CoordenadaBancariaRepository coordenadaBancariaRepository;

    private final CoordenadaBancariaMapper coordenadaBancariaMapper;

    public CoordenadaBancariaQueryService(CoordenadaBancariaRepository coordenadaBancariaRepository, CoordenadaBancariaMapper coordenadaBancariaMapper) {
        this.coordenadaBancariaRepository = coordenadaBancariaRepository;
        this.coordenadaBancariaMapper = coordenadaBancariaMapper;
    }

    /**
     * Return a {@link List} of {@link CoordenadaBancariaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CoordenadaBancariaDTO> findByCriteria(CoordenadaBancariaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CoordenadaBancaria> specification = createSpecification(criteria);
        return coordenadaBancariaMapper.toDto(coordenadaBancariaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CoordenadaBancariaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CoordenadaBancariaDTO> findByCriteria(CoordenadaBancariaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CoordenadaBancaria> specification = createSpecification(criteria);
        return coordenadaBancariaRepository.findAll(specification, page)
            .map(coordenadaBancariaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CoordenadaBancariaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CoordenadaBancaria> specification = createSpecification(criteria);
        return coordenadaBancariaRepository.count(specification);
    }

    /**
     * Function to convert {@link CoordenadaBancariaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CoordenadaBancaria> createSpecification(CoordenadaBancariaCriteria criteria) {
        Specification<CoordenadaBancaria> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CoordenadaBancaria_.id));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), CoordenadaBancaria_.descricao));
            }
            if (criteria.getProprietario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProprietario(), CoordenadaBancaria_.proprietario));
            }
            if (criteria.getNumeroConta() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroConta(), CoordenadaBancaria_.numeroConta));
            }
            if (criteria.getIban() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIban(), CoordenadaBancaria_.iban));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), CoordenadaBancaria_.ativo));
            }
            if (criteria.getMostrarDocumento() != null) {
                specification = specification.and(buildSpecification(criteria.getMostrarDocumento(), CoordenadaBancaria_.mostrarDocumento));
            }
            if (criteria.getMostrarPontoVenda() != null) {
                specification = specification.and(buildSpecification(criteria.getMostrarPontoVenda(), CoordenadaBancaria_.mostrarPontoVenda));
            }
            if (criteria.getPadraoRecebimento() != null) {
                specification = specification.and(buildSpecification(criteria.getPadraoRecebimento(), CoordenadaBancaria_.padraoRecebimento));
            }
            if (criteria.getPadraoPagamento() != null) {
                specification = specification.and(buildSpecification(criteria.getPadraoPagamento(), CoordenadaBancaria_.padraoPagamento));
            }
            if (criteria.getDetalheLancamentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetalheLancamentoId(),
                    root -> root.join(CoordenadaBancaria_.detalheLancamentos, JoinType.LEFT).get(DetalheLancamento_.id)));
            }
            if (criteria.getContaId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaId(),
                    root -> root.join(CoordenadaBancaria_.conta, JoinType.LEFT).get(Conta_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(CoordenadaBancaria_.empresas, JoinType.LEFT).get(Empresa_.id)));
            }
        }
        return specification;
    }
}
