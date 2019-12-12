package com.ravunana.manda.service;

import com.ravunana.manda.domain.FluxoDocumento;
import com.ravunana.manda.repository.FluxoDocumentoRepository;
import com.ravunana.manda.service.dto.FluxoDocumentoDTO;
import com.ravunana.manda.service.mapper.FluxoDocumentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FluxoDocumento}.
 */
@Service
@Transactional
public class FluxoDocumentoService {

    private final Logger log = LoggerFactory.getLogger(FluxoDocumentoService.class);

    private final FluxoDocumentoRepository fluxoDocumentoRepository;

    private final FluxoDocumentoMapper fluxoDocumentoMapper;

    public FluxoDocumentoService(FluxoDocumentoRepository fluxoDocumentoRepository, FluxoDocumentoMapper fluxoDocumentoMapper) {
        this.fluxoDocumentoRepository = fluxoDocumentoRepository;
        this.fluxoDocumentoMapper = fluxoDocumentoMapper;
    }

    /**
     * Save a fluxoDocumento.
     *
     * @param fluxoDocumentoDTO the entity to save.
     * @return the persisted entity.
     */
    public FluxoDocumentoDTO save(FluxoDocumentoDTO fluxoDocumentoDTO) {
        log.debug("Request to save FluxoDocumento : {}", fluxoDocumentoDTO);
        FluxoDocumento fluxoDocumento = fluxoDocumentoMapper.toEntity(fluxoDocumentoDTO);
        fluxoDocumento = fluxoDocumentoRepository.save(fluxoDocumento);
        return fluxoDocumentoMapper.toDto(fluxoDocumento);
    }

    /**
     * Get all the fluxoDocumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FluxoDocumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FluxoDocumentos");
        return fluxoDocumentoRepository.findAll(pageable)
            .map(fluxoDocumentoMapper::toDto);
    }


    /**
     * Get one fluxoDocumento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FluxoDocumentoDTO> findOne(Long id) {
        log.debug("Request to get FluxoDocumento : {}", id);
        return fluxoDocumentoRepository.findById(id)
            .map(fluxoDocumentoMapper::toDto);
    }

    /**
     * Delete the fluxoDocumento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FluxoDocumento : {}", id);
        fluxoDocumentoRepository.deleteById(id);
    }
}
