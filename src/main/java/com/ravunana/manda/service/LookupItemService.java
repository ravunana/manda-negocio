package com.ravunana.manda.service;

import com.ravunana.manda.domain.LookupItem;
import com.ravunana.manda.repository.LookupItemRepository;
import com.ravunana.manda.service.dto.LookupItemDTO;
import com.ravunana.manda.service.mapper.LookupItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LookupItem}.
 */
@Service
@Transactional
public class LookupItemService {

    private final Logger log = LoggerFactory.getLogger(LookupItemService.class);

    private final LookupItemRepository lookupItemRepository;

    private final LookupItemMapper lookupItemMapper;

    public LookupItemService(LookupItemRepository lookupItemRepository, LookupItemMapper lookupItemMapper) {
        this.lookupItemRepository = lookupItemRepository;
        this.lookupItemMapper = lookupItemMapper;
    }

    /**
     * Save a lookupItem.
     *
     * @param lookupItemDTO the entity to save.
     * @return the persisted entity.
     */
    public LookupItemDTO save(LookupItemDTO lookupItemDTO) {
        log.debug("Request to save LookupItem : {}", lookupItemDTO);
        LookupItem lookupItem = lookupItemMapper.toEntity(lookupItemDTO);
        lookupItem = lookupItemRepository.save(lookupItem);
        return lookupItemMapper.toDto(lookupItem);
    }

    /**
     * Get all the lookupItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LookupItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LookupItems");
        return lookupItemRepository.findAll(pageable)
            .map(lookupItemMapper::toDto);
    }


    /**
     * Get one lookupItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LookupItemDTO> findOne(Long id) {
        log.debug("Request to get LookupItem : {}", id);
        return lookupItemRepository.findById(id)
            .map(lookupItemMapper::toDto);
    }

    /**
     * Delete the lookupItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LookupItem : {}", id);
        lookupItemRepository.deleteById(id);
    }
}
