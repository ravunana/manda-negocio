package com.ravunana.manda.service;

import com.ravunana.manda.domain.FamiliaProduto;
import com.ravunana.manda.repository.FamiliaProdutoRepository;
import com.ravunana.manda.service.dto.FamiliaProdutoDTO;
import com.ravunana.manda.service.mapper.FamiliaProdutoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FamiliaProduto}.
 */
@Service
@Transactional
public class FamiliaProdutoService {

    private final Logger log = LoggerFactory.getLogger(FamiliaProdutoService.class);

    private final FamiliaProdutoRepository familiaProdutoRepository;

    private final FamiliaProdutoMapper familiaProdutoMapper;

    public FamiliaProdutoService(FamiliaProdutoRepository familiaProdutoRepository, FamiliaProdutoMapper familiaProdutoMapper) {
        this.familiaProdutoRepository = familiaProdutoRepository;
        this.familiaProdutoMapper = familiaProdutoMapper;
    }

    /**
     * Save a familiaProduto.
     *
     * @param familiaProdutoDTO the entity to save.
     * @return the persisted entity.
     */
    public FamiliaProdutoDTO save(FamiliaProdutoDTO familiaProdutoDTO) {
        log.debug("Request to save FamiliaProduto : {}", familiaProdutoDTO);
        FamiliaProduto familiaProduto = familiaProdutoMapper.toEntity(familiaProdutoDTO);
        familiaProduto = familiaProdutoRepository.save(familiaProduto);
        return familiaProdutoMapper.toDto(familiaProduto);
    }

    /**
     * Get all the familiaProdutos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FamiliaProdutoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FamiliaProdutos");
        return familiaProdutoRepository.findAll(pageable)
            .map(familiaProdutoMapper::toDto);
    }


    /**
     * Get one familiaProduto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FamiliaProdutoDTO> findOne(Long id) {
        log.debug("Request to get FamiliaProduto : {}", id);
        return familiaProdutoRepository.findById(id)
            .map(familiaProdutoMapper::toDto);
    }

    /**
     * Delete the familiaProduto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FamiliaProduto : {}", id);
        familiaProdutoRepository.deleteById(id);
    }
}
