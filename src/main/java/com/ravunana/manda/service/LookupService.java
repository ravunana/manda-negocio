package com.ravunana.manda.service;

import com.ravunana.manda.domain.Lookup;
import com.ravunana.manda.repository.LookupRepository;
import com.ravunana.manda.service.dto.LookupDTO;
import com.ravunana.manda.service.mapper.LookupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Lookup}.
 */
@Service
@Transactional
public class LookupService {

    private final Logger log = LoggerFactory.getLogger(LookupService.class);

    private final LookupRepository lookupRepository;

    private final LookupMapper lookupMapper;

    public LookupService(LookupRepository lookupRepository, LookupMapper lookupMapper) {
        this.lookupRepository = lookupRepository;
        this.lookupMapper = lookupMapper;
    }

    /**
     * Save a lookup.
     *
     * @param lookupDTO the entity to save.
     * @return the persisted entity.
     */
    public LookupDTO save(LookupDTO lookupDTO) {
        log.debug("Request to save Lookup : {}", lookupDTO);
        Lookup lookup = lookupMapper.toEntity(lookupDTO);
        lookup = lookupRepository.save(lookup);
        return lookupMapper.toDto(lookup);
    }

    /**
     * Get all the lookups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LookupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lookups");
        return lookupRepository.findAll(pageable)
            .map(lookupMapper::toDto);
    }


    /**
     * Get one lookup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LookupDTO> findOne(Long id) {
        log.debug("Request to get Lookup : {}", id);
        return lookupRepository.findById(id)
            .map(lookupMapper::toDto);
    }

    /**
     * Delete the lookup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Lookup : {}", id);
        lookupRepository.deleteById(id);
    }
}
