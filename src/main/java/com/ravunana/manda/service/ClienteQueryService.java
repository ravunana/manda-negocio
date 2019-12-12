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

import com.ravunana.manda.domain.Cliente;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.ClienteRepository;
import com.ravunana.manda.service.dto.ClienteCriteria;
import com.ravunana.manda.service.dto.ClienteDTO;
import com.ravunana.manda.service.mapper.ClienteMapper;

/**
 * Service for executing complex queries for {@link Cliente} entities in the database.
 * The main input is a {@link ClienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClienteDTO} or a {@link Page} of {@link ClienteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClienteQueryService extends QueryService<Cliente> {

    private final Logger log = LoggerFactory.getLogger(ClienteQueryService.class);

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper;

    public ClienteQueryService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    /**
     * Return a {@link List} of {@link ClienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> findByCriteria(ClienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cliente> specification = createSpecification(criteria);
        return clienteMapper.toDto(clienteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findByCriteria(ClienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cliente> specification = createSpecification(criteria);
        return clienteRepository.findAll(specification, page)
            .map(clienteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClienteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cliente> specification = createSpecification(criteria);
        return clienteRepository.count(specification);
    }

    /**
     * Function to convert {@link ClienteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cliente> createSpecification(ClienteCriteria criteria) {
        Specification<Cliente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cliente_.id));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), Cliente_.ativo));
            }
            if (criteria.getSatisfacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSatisfacao(), Cliente_.satisfacao));
            }
            if (criteria.getFrequencia() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFrequencia(), Cliente_.frequencia));
            }
            if (criteria.getCanalUsado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCanalUsado(), Cliente_.canalUsado));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Cliente_.numero));
            }
            if (criteria.getAutofacturacao() != null) {
                specification = specification.and(buildSpecification(criteria.getAutofacturacao(), Cliente_.autofacturacao));
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(buildSpecification(criteria.getPessoaId(),
                    root -> root.join(Cliente_.pessoa, JoinType.LEFT).get(Pessoa_.id)));
            }
            if (criteria.getVendaId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendaId(),
                    root -> root.join(Cliente_.vendas, JoinType.LEFT).get(Venda_.id)));
            }
            if (criteria.getContaId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaId(),
                    root -> root.join(Cliente_.conta, JoinType.LEFT).get(Conta_.id)));
            }
        }
        return specification;
    }
}
