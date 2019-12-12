package com.ravunana.manda.service;

import com.ravunana.manda.domain.ItemVenda;
import com.ravunana.manda.repository.ItemVendaRepository;
import com.ravunana.manda.service.dto.ItemVendaDTO;
import com.ravunana.manda.service.mapper.ItemVendaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemVenda}.
 */
@Service
@Transactional
public class ItemVendaService {

    private final Logger log = LoggerFactory.getLogger(ItemVendaService.class);

    private final ItemVendaRepository itemVendaRepository;

    private final ItemVendaMapper itemVendaMapper;

    public ItemVendaService(ItemVendaRepository itemVendaRepository, ItemVendaMapper itemVendaMapper) {
        this.itemVendaRepository = itemVendaRepository;
        this.itemVendaMapper = itemVendaMapper;
    }

    /**
     * Save a itemVenda.
     *
     * @param itemVendaDTO the entity to save.
     * @return the persisted entity.
     */
    public ItemVendaDTO save(ItemVendaDTO itemVendaDTO) {
        log.debug("Request to save ItemVenda : {}", itemVendaDTO);
        ItemVenda itemVenda = itemVendaMapper.toEntity(itemVendaDTO);
        itemVenda = itemVendaRepository.save(itemVenda);
        return itemVendaMapper.toDto(itemVenda);
    }

    /**
     * Get all the itemVendas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemVendaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemVendas");
        return itemVendaRepository.findAll(pageable)
            .map(itemVendaMapper::toDto);
    }


    /**
     * Get one itemVenda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemVendaDTO> findOne(Long id) {
        log.debug("Request to get ItemVenda : {}", id);
        return itemVendaRepository.findById(id)
            .map(itemVendaMapper::toDto);
    }

    /**
     * Delete the itemVenda by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ItemVenda : {}", id);
        itemVendaRepository.deleteById(id);
    }
}
