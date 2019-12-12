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

import com.ravunana.manda.domain.Empresa;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.EmpresaRepository;
import com.ravunana.manda.service.dto.EmpresaCriteria;
import com.ravunana.manda.service.dto.EmpresaDTO;
import com.ravunana.manda.service.mapper.EmpresaMapper;

/**
 * Service for executing complex queries for {@link Empresa} entities in the database.
 * The main input is a {@link EmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmpresaDTO} or a {@link Page} of {@link EmpresaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmpresaQueryService extends QueryService<Empresa> {

    private final Logger log = LoggerFactory.getLogger(EmpresaQueryService.class);

    private final EmpresaRepository empresaRepository;

    private final EmpresaMapper empresaMapper;

    public EmpresaQueryService(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
    }

    /**
     * Return a {@link List} of {@link EmpresaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmpresaDTO> findByCriteria(EmpresaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Empresa> specification = createSpecification(criteria);
        return empresaMapper.toDto(empresaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmpresaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmpresaDTO> findByCriteria(EmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Empresa> specification = createSpecification(criteria);
        return empresaRepository.findAll(specification, page)
            .map(empresaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Empresa> specification = createSpecification(criteria);
        return empresaRepository.count(specification);
    }

    /**
     * Function to convert {@link EmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Empresa> createSpecification(EmpresaCriteria criteria) {
        Specification<Empresa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Empresa_.id));
            }
            if (criteria.getTipoSociedade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoSociedade(), Empresa_.tipoSociedade));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Empresa_.nome));
            }
            if (criteria.getCapitalSocial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapitalSocial(), Empresa_.capitalSocial));
            }
            if (criteria.getFundacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFundacao(), Empresa_.fundacao));
            }
            if (criteria.getNif() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNif(), Empresa_.nif));
            }
            if (criteria.getNumeroRegistroComercial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroRegistroComercial(), Empresa_.numeroRegistroComercial));
            }
            if (criteria.getDespesaFixa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDespesaFixa(), Empresa_.despesaFixa));
            }
            if (criteria.getDespesaVariavel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDespesaVariavel(), Empresa_.despesaVariavel));
            }
            if (criteria.getAberturaExercio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAberturaExercio(), Empresa_.aberturaExercio));
            }
            if (criteria.getSede() != null) {
                specification = specification.and(buildSpecification(criteria.getSede(), Empresa_.sede));
            }
            if (criteria.getEscrituracaoContabilId() != null) {
                specification = specification.and(buildSpecification(criteria.getEscrituracaoContabilId(),
                    root -> root.join(Empresa_.escrituracaoContabils, JoinType.LEFT).get(EscrituracaoContabil_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpresaId(),
                    root -> root.join(Empresa_.empresas, JoinType.LEFT).get(Empresa_.id)));
            }
            if (criteria.getLocalizacaoEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocalizacaoEmpresaId(),
                    root -> root.join(Empresa_.localizacaoEmpresas, JoinType.LEFT).get(LocalizacaoEmpresa_.id)));
            }
            if (criteria.getContactoEmpresaId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactoEmpresaId(),
                    root -> root.join(Empresa_.contactoEmpresas, JoinType.LEFT).get(ContactoEmpresa_.id)));
            }
            if (criteria.getLicencaSoftwareId() != null) {
                specification = specification.and(buildSpecification(criteria.getLicencaSoftwareId(),
                    root -> root.join(Empresa_.licencaSoftwares, JoinType.LEFT).get(LicencaSoftware_.id)));
            }
            if (criteria.getLancamentoFinanceiroId() != null) {
                specification = specification.and(buildSpecification(criteria.getLancamentoFinanceiroId(),
                    root -> root.join(Empresa_.lancamentoFinanceiros, JoinType.LEFT).get(LancamentoFinanceiro_.id)));
            }
            if (criteria.getProdutoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoId(),
                    root -> root.join(Empresa_.produtos, JoinType.LEFT).get(Produto_.id)));
            }
            if (criteria.getLocalArmazenamentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getLocalArmazenamentoId(),
                    root -> root.join(Empresa_.localArmazenamentos, JoinType.LEFT).get(LocalArmazenamento_.id)));
            }
            if (criteria.getCompraId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompraId(),
                    root -> root.join(Empresa_.compras, JoinType.LEFT).get(Compra_.id)));
            }
            if (criteria.getVendaId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendaId(),
                    root -> root.join(Empresa_.vendas, JoinType.LEFT).get(Venda_.id)));
            }
            if (criteria.getUtilizadorId() != null) {
                specification = specification.and(buildSpecification(criteria.getUtilizadorId(),
                    root -> root.join(Empresa_.utilizador, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getContaId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaId(),
                    root -> root.join(Empresa_.conta, JoinType.LEFT).get(Conta_.id)));
            }
            if (criteria.getHierarquiaId() != null) {
                specification = specification.and(buildSpecification(criteria.getHierarquiaId(),
                    root -> root.join(Empresa_.hierarquia, JoinType.LEFT).get(Empresa_.id)));
            }
            if (criteria.getContaId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaId(),
                    root -> root.join(Empresa_.contas, JoinType.LEFT).get(Conta_.id)));
            }
            if (criteria.getCoordenadaBancariaId() != null) {
                specification = specification.and(buildSpecification(criteria.getCoordenadaBancariaId(),
                    root -> root.join(Empresa_.coordenadaBancarias, JoinType.LEFT).get(CoordenadaBancaria_.id)));
            }
        }
        return specification;
    }
}
