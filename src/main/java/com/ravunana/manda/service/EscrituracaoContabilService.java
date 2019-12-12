package com.ravunana.manda.service;

import com.ravunana.manda.domain.EscrituracaoContabil;
import com.ravunana.manda.repository.EscrituracaoContabilRepository;
import com.ravunana.manda.service.dto.EscrituracaoContabilDTO;
import com.ravunana.manda.service.mapper.EscrituracaoContabilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EscrituracaoContabil}.
 */
@Service
@Transactional
public class EscrituracaoContabilService {

    private final Logger log = LoggerFactory.getLogger(EscrituracaoContabilService.class);

    private final EscrituracaoContabilRepository escrituracaoContabilRepository;

    private final EscrituracaoContabilMapper escrituracaoContabilMapper;

    public EscrituracaoContabilService(EscrituracaoContabilRepository escrituracaoContabilRepository, EscrituracaoContabilMapper escrituracaoContabilMapper) {
        this.escrituracaoContabilRepository = escrituracaoContabilRepository;
        this.escrituracaoContabilMapper = escrituracaoContabilMapper;
    }

    /**
     * Save a escrituracaoContabil.
     *
     * @param escrituracaoContabilDTO the entity to save.
     * @return the persisted entity.
     */
    public EscrituracaoContabilDTO save(EscrituracaoContabilDTO escrituracaoContabilDTO) {
        log.debug("Request to save EscrituracaoContabil : {}", escrituracaoContabilDTO);
        EscrituracaoContabil escrituracaoContabil = escrituracaoContabilMapper.toEntity(escrituracaoContabilDTO);
        escrituracaoContabil = escrituracaoContabilRepository.save(escrituracaoContabil);
        return escrituracaoContabilMapper.toDto(escrituracaoContabil);
    }

    /**
     * Get all the escrituracaoContabils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EscrituracaoContabilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EscrituracaoContabils");
        return escrituracaoContabilRepository.findAll(pageable)
            .map(escrituracaoContabilMapper::toDto);
    }


    /**
     * Get one escrituracaoContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EscrituracaoContabilDTO> findOne(Long id) {
        log.debug("Request to get EscrituracaoContabil : {}", id);
        return escrituracaoContabilRepository.findById(id)
            .map(escrituracaoContabilMapper::toDto);
    }

    /**
     * Delete the escrituracaoContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EscrituracaoContabil : {}", id);
        escrituracaoContabilRepository.deleteById(id);
    }
}
