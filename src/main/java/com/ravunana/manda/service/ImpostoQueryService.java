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

import com.ravunana.manda.domain.Imposto;
import com.ravunana.manda.domain.*; // for static metamodels
import com.ravunana.manda.repository.ImpostoRepository;
import com.ravunana.manda.service.dto.ImpostoCriteria;
import com.ravunana.manda.service.dto.ImpostoDTO;
import com.ravunana.manda.service.mapper.ImpostoMapper;

/**
 * Service for executing complex queries for {@link Imposto} entities in the database.
 * The main input is a {@link ImpostoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ImpostoDTO} or a {@link Page} of {@link ImpostoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImpostoQueryService extends QueryService<Imposto> {

    private final Logger log = LoggerFactory.getLogger(ImpostoQueryService.class);

    private final ImpostoRepository impostoRepository;

    private final ImpostoMapper impostoMapper;

    public ImpostoQueryService(ImpostoRepository impostoRepository, ImpostoMapper impostoMapper) {
        this.impostoRepository = impostoRepository;
        this.impostoMapper = impostoMapper;
    }

    /**
     * Return a {@link List} of {@link ImpostoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ImpostoDTO> findByCriteria(ImpostoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Imposto> specification = createSpecification(criteria);
        return impostoMapper.toDto(impostoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ImpostoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ImpostoDTO> findByCriteria(ImpostoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Imposto> specification = createSpecification(criteria);
        return impostoRepository.findAll(specification, page)
            .map(impostoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImpostoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Imposto> specification = createSpecification(criteria);
        return impostoRepository.count(specification);
    }

    /**
     * Function to convert {@link ImpostoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Imposto> createSpecification(ImpostoCriteria criteria) {
        Specification<Imposto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Imposto_.id));
            }
            if (criteria.getTipoImposto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoImposto(), Imposto_.tipoImposto));
            }
            if (criteria.getCodigoImposto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigoImposto(), Imposto_.codigoImposto));
            }
            if (criteria.getPorcentagem() != null) {
                specification = specification.and(buildSpecification(criteria.getPorcentagem(), Imposto_.porcentagem));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Imposto_.valor));
            }
            if (criteria.getPais() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPais(), Imposto_.pais));
            }
            if (criteria.getVigencia() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVigencia(), Imposto_.vigencia));
            }
            if (criteria.getGrupoTributacaoImpostoId() != null) {
                specification = specification.and(buildSpecification(criteria.getGrupoTributacaoImpostoId(),
                    root -> root.join(Imposto_.grupoTributacaoImpostos, JoinType.LEFT).get(GrupoTributacaoImposto_.id)));
            }
            if (criteria.getRetencaoFonteId() != null) {
                specification = specification.and(buildSpecification(criteria.getRetencaoFonteId(),
                    root -> root.join(Imposto_.retencaoFontes, JoinType.LEFT).get(RetencaoFonte_.id)));
            }
            if (criteria.getContaId() != null) {
                specification = specification.and(buildSpecification(criteria.getContaId(),
                    root -> root.join(Imposto_.conta, JoinType.LEFT).get(Conta_.id)));
            }
            if (criteria.getLancamentoId() != null) {
                specification = specification.and(buildSpecification(criteria.getLancamentoId(),
                    root -> root.join(Imposto_.lancamentos, JoinType.LEFT).get(LancamentoFinanceiro_.id)));
            }
            if (criteria.getProdutoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProdutoId(),
                    root -> root.join(Imposto_.produtos, JoinType.LEFT).get(Produto_.id)));
            }
        }
        return specification;
    }
}
