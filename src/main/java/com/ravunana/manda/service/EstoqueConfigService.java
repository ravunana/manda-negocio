package com.ravunana.manda.service;

import com.ravunana.manda.domain.EstoqueConfig;
import com.ravunana.manda.repository.EstoqueConfigRepository;
import com.ravunana.manda.service.dto.EstoqueConfigDTO;
import com.ravunana.manda.service.mapper.EstoqueConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EstoqueConfig}.
 */
@Service
@Transactional
public class EstoqueConfigService {

    private final Logger log = LoggerFactory.getLogger(EstoqueConfigService.class);

    private final EstoqueConfigRepository estoqueConfigRepository;

    private final EstoqueConfigMapper estoqueConfigMapper;

    public EstoqueConfigService(EstoqueConfigRepository estoqueConfigRepository, EstoqueConfigMapper estoqueConfigMapper) {
        this.estoqueConfigRepository = estoqueConfigRepository;
        this.estoqueConfigMapper = estoqueConfigMapper;
    }

    /**
     * Save a estoqueConfig.
     *
     * @param estoqueConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public EstoqueConfigDTO save(EstoqueConfigDTO estoqueConfigDTO) {
        log.debug("Request to save EstoqueConfig : {}", estoqueConfigDTO);
        EstoqueConfig estoqueConfig = estoqueConfigMapper.toEntity(estoqueConfigDTO);
        estoqueConfig = estoqueConfigRepository.save(estoqueConfig);
        return estoqueConfigMapper.toDto(estoqueConfig);
    }

    /**
     * Get all the estoqueConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EstoqueConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstoqueConfigs");
        return estoqueConfigRepository.findAll(pageable)
            .map(estoqueConfigMapper::toDto);
    }


    /**
     * Get one estoqueConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EstoqueConfigDTO> findOne(Long id) {
        log.debug("Request to get EstoqueConfig : {}", id);
        return estoqueConfigRepository.findById(id)
            .map(estoqueConfigMapper::toDto);
    }

    /**
     * Delete the estoqueConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EstoqueConfig : {}", id);
        estoqueConfigRepository.deleteById(id);
    }
}
