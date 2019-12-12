package com.ravunana.manda.service;

import com.ravunana.manda.domain.CoordenadaBancaria;
import com.ravunana.manda.repository.CoordenadaBancariaRepository;
import com.ravunana.manda.service.dto.CoordenadaBancariaDTO;
import com.ravunana.manda.service.mapper.CoordenadaBancariaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CoordenadaBancaria}.
 */
@Service
@Transactional
public class CoordenadaBancariaService {

    private final Logger log = LoggerFactory.getLogger(CoordenadaBancariaService.class);

    private final CoordenadaBancariaRepository coordenadaBancariaRepository;

    private final CoordenadaBancariaMapper coordenadaBancariaMapper;

    public CoordenadaBancariaService(CoordenadaBancariaRepository coordenadaBancariaRepository, CoordenadaBancariaMapper coordenadaBancariaMapper) {
        this.coordenadaBancariaRepository = coordenadaBancariaRepository;
        this.coordenadaBancariaMapper = coordenadaBancariaMapper;
    }

    /**
     * Save a coordenadaBancaria.
     *
     * @param coordenadaBancariaDTO the entity to save.
     * @return the persisted entity.
     */
    public CoordenadaBancariaDTO save(CoordenadaBancariaDTO coordenadaBancariaDTO) {
        log.debug("Request to save CoordenadaBancaria : {}", coordenadaBancariaDTO);
        CoordenadaBancaria coordenadaBancaria = coordenadaBancariaMapper.toEntity(coordenadaBancariaDTO);
        coordenadaBancaria = coordenadaBancariaRepository.save(coordenadaBancaria);
        return coordenadaBancariaMapper.toDto(coordenadaBancaria);
    }

    /**
     * Get all the coordenadaBancarias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CoordenadaBancariaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CoordenadaBancarias");
        return coordenadaBancariaRepository.findAll(pageable)
            .map(coordenadaBancariaMapper::toDto);
    }

    /**
     * Get all the coordenadaBancarias with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CoordenadaBancariaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return coordenadaBancariaRepository.findAllWithEagerRelationships(pageable).map(coordenadaBancariaMapper::toDto);
    }
    

    /**
     * Get one coordenadaBancaria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CoordenadaBancariaDTO> findOne(Long id) {
        log.debug("Request to get CoordenadaBancaria : {}", id);
        return coordenadaBancariaRepository.findOneWithEagerRelationships(id)
            .map(coordenadaBancariaMapper::toDto);
    }

    /**
     * Delete the coordenadaBancaria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CoordenadaBancaria : {}", id);
        coordenadaBancariaRepository.deleteById(id);
    }
}
