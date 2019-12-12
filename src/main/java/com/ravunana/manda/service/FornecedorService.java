package com.ravunana.manda.service;

import com.ravunana.manda.domain.Fornecedor;
import com.ravunana.manda.repository.FornecedorRepository;
import com.ravunana.manda.service.dto.FornecedorDTO;
import com.ravunana.manda.service.mapper.FornecedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Fornecedor}.
 */
@Service
@Transactional
public class FornecedorService {

    private final Logger log = LoggerFactory.getLogger(FornecedorService.class);

    private final FornecedorRepository fornecedorRepository;

    private final FornecedorMapper fornecedorMapper;

    public FornecedorService(FornecedorRepository fornecedorRepository, FornecedorMapper fornecedorMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    /**
     * Save a fornecedor.
     *
     * @param fornecedorDTO the entity to save.
     * @return the persisted entity.
     */
    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        log.debug("Request to save Fornecedor : {}", fornecedorDTO);
        Fornecedor fornecedor = fornecedorMapper.toEntity(fornecedorDTO);
        fornecedor = fornecedorRepository.save(fornecedor);
        return fornecedorMapper.toDto(fornecedor);
    }

    /**
     * Get all the fornecedors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fornecedors");
        return fornecedorRepository.findAll(pageable)
            .map(fornecedorMapper::toDto);
    }


    /**
     * Get one fornecedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FornecedorDTO> findOne(Long id) {
        log.debug("Request to get Fornecedor : {}", id);
        return fornecedorRepository.findById(id)
            .map(fornecedorMapper::toDto);
    }

    /**
     * Delete the fornecedor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Fornecedor : {}", id);
        fornecedorRepository.deleteById(id);
    }
}
