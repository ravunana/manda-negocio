package com.ravunana.manda.service;

import com.ravunana.manda.domain.LocalArmazenamento;
import com.ravunana.manda.repository.LocalArmazenamentoRepository;
import com.ravunana.manda.service.dto.LocalArmazenamentoDTO;
import com.ravunana.manda.service.mapper.LocalArmazenamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LocalArmazenamento}.
 */
@Service
@Transactional
public class LocalArmazenamentoService {

    private final Logger log = LoggerFactory.getLogger(LocalArmazenamentoService.class);

    private final LocalArmazenamentoRepository localArmazenamentoRepository;

    private final LocalArmazenamentoMapper localArmazenamentoMapper;

    public LocalArmazenamentoService(LocalArmazenamentoRepository localArmazenamentoRepository, LocalArmazenamentoMapper localArmazenamentoMapper) {
        this.localArmazenamentoRepository = localArmazenamentoRepository;
        this.localArmazenamentoMapper = localArmazenamentoMapper;
    }

    /**
     * Save a localArmazenamento.
     *
     * @param localArmazenamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public LocalArmazenamentoDTO save(LocalArmazenamentoDTO localArmazenamentoDTO) {
        log.debug("Request to save LocalArmazenamento : {}", localArmazenamentoDTO);
        LocalArmazenamento localArmazenamento = localArmazenamentoMapper.toEntity(localArmazenamentoDTO);
        localArmazenamento = localArmazenamentoRepository.save(localArmazenamento);
        return localArmazenamentoMapper.toDto(localArmazenamento);
    }

    /**
     * Get all the localArmazenamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocalArmazenamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocalArmazenamentos");
        return localArmazenamentoRepository.findAll(pageable)
            .map(localArmazenamentoMapper::toDto);
    }


    /**
     * Get one localArmazenamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocalArmazenamentoDTO> findOne(Long id) {
        log.debug("Request to get LocalArmazenamento : {}", id);
        return localArmazenamentoRepository.findById(id)
            .map(localArmazenamentoMapper::toDto);
    }

    /**
     * Delete the localArmazenamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LocalArmazenamento : {}", id);
        localArmazenamentoRepository.deleteById(id);
    }
}
