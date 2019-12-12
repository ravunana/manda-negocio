package com.ravunana.manda.service;

import com.ravunana.manda.domain.ContaDebito;
import com.ravunana.manda.repository.ContaDebitoRepository;
import com.ravunana.manda.service.dto.ContaDebitoDTO;
import com.ravunana.manda.service.mapper.ContaDebitoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContaDebito}.
 */
@Service
@Transactional
public class ContaDebitoService {

    private final Logger log = LoggerFactory.getLogger(ContaDebitoService.class);

    private final ContaDebitoRepository contaDebitoRepository;

    private final ContaDebitoMapper contaDebitoMapper;

    public ContaDebitoService(ContaDebitoRepository contaDebitoRepository, ContaDebitoMapper contaDebitoMapper) {
        this.contaDebitoRepository = contaDebitoRepository;
        this.contaDebitoMapper = contaDebitoMapper;
    }

    /**
     * Save a contaDebito.
     *
     * @param contaDebitoDTO the entity to save.
     * @return the persisted entity.
     */
    public ContaDebitoDTO save(ContaDebitoDTO contaDebitoDTO) {
        log.debug("Request to save ContaDebito : {}", contaDebitoDTO);
        ContaDebito contaDebito = contaDebitoMapper.toEntity(contaDebitoDTO);
        contaDebito = contaDebitoRepository.save(contaDebito);
        return contaDebitoMapper.toDto(contaDebito);
    }

    /**
     * Get all the contaDebitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContaDebitoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContaDebitos");
        return contaDebitoRepository.findAll(pageable)
            .map(contaDebitoMapper::toDto);
    }


    /**
     * Get one contaDebito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContaDebitoDTO> findOne(Long id) {
        log.debug("Request to get ContaDebito : {}", id);
        return contaDebitoRepository.findById(id)
            .map(contaDebitoMapper::toDto);
    }

    /**
     * Delete the contaDebito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContaDebito : {}", id);
        contaDebitoRepository.deleteById(id);
    }
}
