package com.ravunana.manda.service;

import com.ravunana.manda.domain.DetalheLancamento;
import com.ravunana.manda.repository.DetalheLancamentoRepository;
import com.ravunana.manda.service.dto.DetalheLancamentoDTO;
import com.ravunana.manda.service.mapper.DetalheLancamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DetalheLancamento}.
 */
@Service
@Transactional
public class DetalheLancamentoService {

    private final Logger log = LoggerFactory.getLogger(DetalheLancamentoService.class);

    private final DetalheLancamentoRepository detalheLancamentoRepository;

    private final DetalheLancamentoMapper detalheLancamentoMapper;

    public DetalheLancamentoService(DetalheLancamentoRepository detalheLancamentoRepository, DetalheLancamentoMapper detalheLancamentoMapper) {
        this.detalheLancamentoRepository = detalheLancamentoRepository;
        this.detalheLancamentoMapper = detalheLancamentoMapper;
    }

    /**
     * Save a detalheLancamento.
     *
     * @param detalheLancamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalheLancamentoDTO save(DetalheLancamentoDTO detalheLancamentoDTO) {
        log.debug("Request to save DetalheLancamento : {}", detalheLancamentoDTO);
        DetalheLancamento detalheLancamento = detalheLancamentoMapper.toEntity(detalheLancamentoDTO);
        detalheLancamento = detalheLancamentoRepository.save(detalheLancamento);
        return detalheLancamentoMapper.toDto(detalheLancamento);
    }

    /**
     * Get all the detalheLancamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalheLancamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalheLancamentos");
        return detalheLancamentoRepository.findAll(pageable)
            .map(detalheLancamentoMapper::toDto);
    }


    /**
     * Get one detalheLancamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetalheLancamentoDTO> findOne(Long id) {
        log.debug("Request to get DetalheLancamento : {}", id);
        return detalheLancamentoRepository.findById(id)
            .map(detalheLancamentoMapper::toDto);
    }

    /**
     * Delete the detalheLancamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalheLancamento : {}", id);
        detalheLancamentoRepository.deleteById(id);
    }
}
