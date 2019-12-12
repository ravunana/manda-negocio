package com.ravunana.manda.service;

import com.ravunana.manda.domain.EstruturaCalculo;
import com.ravunana.manda.repository.EstruturaCalculoRepository;
import com.ravunana.manda.service.dto.EstruturaCalculoDTO;
import com.ravunana.manda.service.mapper.EstruturaCalculoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EstruturaCalculo}.
 */
@Service
@Transactional
public class EstruturaCalculoService {

    private final Logger log = LoggerFactory.getLogger(EstruturaCalculoService.class);

    private final EstruturaCalculoRepository estruturaCalculoRepository;

    private final EstruturaCalculoMapper estruturaCalculoMapper;

    public EstruturaCalculoService(EstruturaCalculoRepository estruturaCalculoRepository, EstruturaCalculoMapper estruturaCalculoMapper) {
        this.estruturaCalculoRepository = estruturaCalculoRepository;
        this.estruturaCalculoMapper = estruturaCalculoMapper;
    }

    /**
     * Save a estruturaCalculo.
     *
     * @param estruturaCalculoDTO the entity to save.
     * @return the persisted entity.
     */
    public EstruturaCalculoDTO save(EstruturaCalculoDTO estruturaCalculoDTO) {
        log.debug("Request to save EstruturaCalculo : {}", estruturaCalculoDTO);
        EstruturaCalculo estruturaCalculo = estruturaCalculoMapper.toEntity(estruturaCalculoDTO);
        estruturaCalculo = estruturaCalculoRepository.save(estruturaCalculo);
        return estruturaCalculoMapper.toDto(estruturaCalculo);
    }

    /**
     * Get all the estruturaCalculos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EstruturaCalculoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstruturaCalculos");
        return estruturaCalculoRepository.findAll(pageable)
            .map(estruturaCalculoMapper::toDto);
    }


    /**
     * Get one estruturaCalculo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EstruturaCalculoDTO> findOne(Long id) {
        log.debug("Request to get EstruturaCalculo : {}", id);
        return estruturaCalculoRepository.findById(id)
            .map(estruturaCalculoMapper::toDto);
    }

    /**
     * Delete the estruturaCalculo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EstruturaCalculo : {}", id);
        estruturaCalculoRepository.deleteById(id);
    }
}
