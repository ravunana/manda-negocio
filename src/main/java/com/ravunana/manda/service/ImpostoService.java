package com.ravunana.manda.service;

import com.ravunana.manda.domain.Imposto;
import com.ravunana.manda.repository.ImpostoRepository;
import com.ravunana.manda.service.dto.ImpostoDTO;
import com.ravunana.manda.service.mapper.ImpostoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Imposto}.
 */
@Service
@Transactional
public class ImpostoService {

    private final Logger log = LoggerFactory.getLogger(ImpostoService.class);

    private final ImpostoRepository impostoRepository;

    private final ImpostoMapper impostoMapper;

    public ImpostoService(ImpostoRepository impostoRepository, ImpostoMapper impostoMapper) {
        this.impostoRepository = impostoRepository;
        this.impostoMapper = impostoMapper;
    }

    /**
     * Save a imposto.
     *
     * @param impostoDTO the entity to save.
     * @return the persisted entity.
     */
    public ImpostoDTO save(ImpostoDTO impostoDTO) {
        log.debug("Request to save Imposto : {}", impostoDTO);
        Imposto imposto = impostoMapper.toEntity(impostoDTO);
        imposto = impostoRepository.save(imposto);
        return impostoMapper.toDto(imposto);
    }

    /**
     * Get all the impostos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ImpostoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Impostos");
        return impostoRepository.findAll(pageable)
            .map(impostoMapper::toDto);
    }


    /**
     * Get one imposto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ImpostoDTO> findOne(Long id) {
        log.debug("Request to get Imposto : {}", id);
        return impostoRepository.findById(id)
            .map(impostoMapper::toDto);
    }

    /**
     * Delete the imposto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Imposto : {}", id);
        impostoRepository.deleteById(id);
    }
}
