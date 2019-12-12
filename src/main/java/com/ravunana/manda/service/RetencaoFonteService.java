package com.ravunana.manda.service;

import com.ravunana.manda.domain.RetencaoFonte;
import com.ravunana.manda.repository.RetencaoFonteRepository;
import com.ravunana.manda.service.dto.RetencaoFonteDTO;
import com.ravunana.manda.service.mapper.RetencaoFonteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RetencaoFonte}.
 */
@Service
@Transactional
public class RetencaoFonteService {

    private final Logger log = LoggerFactory.getLogger(RetencaoFonteService.class);

    private final RetencaoFonteRepository retencaoFonteRepository;

    private final RetencaoFonteMapper retencaoFonteMapper;

    public RetencaoFonteService(RetencaoFonteRepository retencaoFonteRepository, RetencaoFonteMapper retencaoFonteMapper) {
        this.retencaoFonteRepository = retencaoFonteRepository;
        this.retencaoFonteMapper = retencaoFonteMapper;
    }

    /**
     * Save a retencaoFonte.
     *
     * @param retencaoFonteDTO the entity to save.
     * @return the persisted entity.
     */
    public RetencaoFonteDTO save(RetencaoFonteDTO retencaoFonteDTO) {
        log.debug("Request to save RetencaoFonte : {}", retencaoFonteDTO);
        RetencaoFonte retencaoFonte = retencaoFonteMapper.toEntity(retencaoFonteDTO);
        retencaoFonte = retencaoFonteRepository.save(retencaoFonte);
        return retencaoFonteMapper.toDto(retencaoFonte);
    }

    /**
     * Get all the retencaoFontes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RetencaoFonteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RetencaoFontes");
        return retencaoFonteRepository.findAll(pageable)
            .map(retencaoFonteMapper::toDto);
    }


    /**
     * Get one retencaoFonte by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RetencaoFonteDTO> findOne(Long id) {
        log.debug("Request to get RetencaoFonte : {}", id);
        return retencaoFonteRepository.findById(id)
            .map(retencaoFonteMapper::toDto);
    }

    /**
     * Delete the retencaoFonte by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RetencaoFonte : {}", id);
        retencaoFonteRepository.deleteById(id);
    }
}
