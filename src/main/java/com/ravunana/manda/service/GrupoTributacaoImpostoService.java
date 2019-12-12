package com.ravunana.manda.service;

import com.ravunana.manda.domain.GrupoTributacaoImposto;
import com.ravunana.manda.repository.GrupoTributacaoImpostoRepository;
import com.ravunana.manda.service.dto.GrupoTributacaoImpostoDTO;
import com.ravunana.manda.service.mapper.GrupoTributacaoImpostoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GrupoTributacaoImposto}.
 */
@Service
@Transactional
public class GrupoTributacaoImpostoService {

    private final Logger log = LoggerFactory.getLogger(GrupoTributacaoImpostoService.class);

    private final GrupoTributacaoImpostoRepository grupoTributacaoImpostoRepository;

    private final GrupoTributacaoImpostoMapper grupoTributacaoImpostoMapper;

    public GrupoTributacaoImpostoService(GrupoTributacaoImpostoRepository grupoTributacaoImpostoRepository, GrupoTributacaoImpostoMapper grupoTributacaoImpostoMapper) {
        this.grupoTributacaoImpostoRepository = grupoTributacaoImpostoRepository;
        this.grupoTributacaoImpostoMapper = grupoTributacaoImpostoMapper;
    }

    /**
     * Save a grupoTributacaoImposto.
     *
     * @param grupoTributacaoImpostoDTO the entity to save.
     * @return the persisted entity.
     */
    public GrupoTributacaoImpostoDTO save(GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO) {
        log.debug("Request to save GrupoTributacaoImposto : {}", grupoTributacaoImpostoDTO);
        GrupoTributacaoImposto grupoTributacaoImposto = grupoTributacaoImpostoMapper.toEntity(grupoTributacaoImpostoDTO);
        grupoTributacaoImposto = grupoTributacaoImpostoRepository.save(grupoTributacaoImposto);
        return grupoTributacaoImpostoMapper.toDto(grupoTributacaoImposto);
    }

    /**
     * Get all the grupoTributacaoImpostos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GrupoTributacaoImpostoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GrupoTributacaoImpostos");
        return grupoTributacaoImpostoRepository.findAll(pageable)
            .map(grupoTributacaoImpostoMapper::toDto);
    }


    /**
     * Get one grupoTributacaoImposto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoTributacaoImpostoDTO> findOne(Long id) {
        log.debug("Request to get GrupoTributacaoImposto : {}", id);
        return grupoTributacaoImpostoRepository.findById(id)
            .map(grupoTributacaoImpostoMapper::toDto);
    }

    /**
     * Delete the grupoTributacaoImposto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupoTributacaoImposto : {}", id);
        grupoTributacaoImpostoRepository.deleteById(id);
    }
}
