package com.ravunana.manda.service;

import com.ravunana.manda.domain.VariacaoProduto;
import com.ravunana.manda.repository.VariacaoProdutoRepository;
import com.ravunana.manda.service.dto.VariacaoProdutoDTO;
import com.ravunana.manda.service.mapper.VariacaoProdutoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link VariacaoProduto}.
 */
@Service
@Transactional
public class VariacaoProdutoService {

    private final Logger log = LoggerFactory.getLogger(VariacaoProdutoService.class);

    private final VariacaoProdutoRepository variacaoProdutoRepository;

    private final VariacaoProdutoMapper variacaoProdutoMapper;

    public VariacaoProdutoService(VariacaoProdutoRepository variacaoProdutoRepository, VariacaoProdutoMapper variacaoProdutoMapper) {
        this.variacaoProdutoRepository = variacaoProdutoRepository;
        this.variacaoProdutoMapper = variacaoProdutoMapper;
    }

    /**
     * Save a variacaoProduto.
     *
     * @param variacaoProdutoDTO the entity to save.
     * @return the persisted entity.
     */
    public VariacaoProdutoDTO save(VariacaoProdutoDTO variacaoProdutoDTO) {
        log.debug("Request to save VariacaoProduto : {}", variacaoProdutoDTO);
        VariacaoProduto variacaoProduto = variacaoProdutoMapper.toEntity(variacaoProdutoDTO);
        variacaoProduto = variacaoProdutoRepository.save(variacaoProduto);
        return variacaoProdutoMapper.toDto(variacaoProduto);
    }

    /**
     * Get all the variacaoProdutos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VariacaoProdutoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VariacaoProdutos");
        return variacaoProdutoRepository.findAll(pageable)
            .map(variacaoProdutoMapper::toDto);
    }


    /**
     * Get one variacaoProduto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VariacaoProdutoDTO> findOne(Long id) {
        log.debug("Request to get VariacaoProduto : {}", id);
        return variacaoProdutoRepository.findById(id)
            .map(variacaoProdutoMapper::toDto);
    }

    /**
     * Delete the variacaoProduto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VariacaoProduto : {}", id);
        variacaoProdutoRepository.deleteById(id);
    }
}
