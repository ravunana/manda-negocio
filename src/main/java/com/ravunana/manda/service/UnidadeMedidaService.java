package com.ravunana.manda.service;

import com.ravunana.manda.domain.UnidadeMedida;
import com.ravunana.manda.repository.UnidadeMedidaRepository;
import com.ravunana.manda.service.dto.UnidadeMedidaDTO;
import com.ravunana.manda.service.mapper.UnidadeMedidaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UnidadeMedida}.
 */
@Service
@Transactional
public class UnidadeMedidaService {

    private final Logger log = LoggerFactory.getLogger(UnidadeMedidaService.class);

    private final UnidadeMedidaRepository unidadeMedidaRepository;

    private final UnidadeMedidaMapper unidadeMedidaMapper;

    public UnidadeMedidaService(UnidadeMedidaRepository unidadeMedidaRepository, UnidadeMedidaMapper unidadeMedidaMapper) {
        this.unidadeMedidaRepository = unidadeMedidaRepository;
        this.unidadeMedidaMapper = unidadeMedidaMapper;
    }

    /**
     * Save a unidadeMedida.
     *
     * @param unidadeMedidaDTO the entity to save.
     * @return the persisted entity.
     */
    public UnidadeMedidaDTO save(UnidadeMedidaDTO unidadeMedidaDTO) {
        log.debug("Request to save UnidadeMedida : {}", unidadeMedidaDTO);
        UnidadeMedida unidadeMedida = unidadeMedidaMapper.toEntity(unidadeMedidaDTO);
        unidadeMedida = unidadeMedidaRepository.save(unidadeMedida);
        return unidadeMedidaMapper.toDto(unidadeMedida);
    }

    /**
     * Get all the unidadeMedidas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UnidadeMedidaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UnidadeMedidas");
        return unidadeMedidaRepository.findAll(pageable)
            .map(unidadeMedidaMapper::toDto);
    }


    /**
     * Get one unidadeMedida by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UnidadeMedidaDTO> findOne(Long id) {
        log.debug("Request to get UnidadeMedida : {}", id);
        return unidadeMedidaRepository.findById(id)
            .map(unidadeMedidaMapper::toDto);
    }

    /**
     * Delete the unidadeMedida by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UnidadeMedida : {}", id);
        unidadeMedidaRepository.deleteById(id);
    }
}
