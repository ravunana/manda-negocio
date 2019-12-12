package com.ravunana.manda.service;

import com.ravunana.manda.domain.CompostoProduto;
import com.ravunana.manda.repository.CompostoProdutoRepository;
import com.ravunana.manda.service.dto.CompostoProdutoDTO;
import com.ravunana.manda.service.mapper.CompostoProdutoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CompostoProduto}.
 */
@Service
@Transactional
public class CompostoProdutoService {

    private final Logger log = LoggerFactory.getLogger(CompostoProdutoService.class);

    private final CompostoProdutoRepository compostoProdutoRepository;

    private final CompostoProdutoMapper compostoProdutoMapper;

    public CompostoProdutoService(CompostoProdutoRepository compostoProdutoRepository, CompostoProdutoMapper compostoProdutoMapper) {
        this.compostoProdutoRepository = compostoProdutoRepository;
        this.compostoProdutoMapper = compostoProdutoMapper;
    }

    /**
     * Save a compostoProduto.
     *
     * @param compostoProdutoDTO the entity to save.
     * @return the persisted entity.
     */
    public CompostoProdutoDTO save(CompostoProdutoDTO compostoProdutoDTO) {
        log.debug("Request to save CompostoProduto : {}", compostoProdutoDTO);
        CompostoProduto compostoProduto = compostoProdutoMapper.toEntity(compostoProdutoDTO);
        compostoProduto = compostoProdutoRepository.save(compostoProduto);
        return compostoProdutoMapper.toDto(compostoProduto);
    }

    /**
     * Get all the compostoProdutos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompostoProdutoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompostoProdutos");
        return compostoProdutoRepository.findAll(pageable)
            .map(compostoProdutoMapper::toDto);
    }


    /**
     * Get one compostoProduto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompostoProdutoDTO> findOne(Long id) {
        log.debug("Request to get CompostoProduto : {}", id);
        return compostoProdutoRepository.findById(id)
            .map(compostoProdutoMapper::toDto);
    }

    /**
     * Delete the compostoProduto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompostoProduto : {}", id);
        compostoProdutoRepository.deleteById(id);
    }
}
