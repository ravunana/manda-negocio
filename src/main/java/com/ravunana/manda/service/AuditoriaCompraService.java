package com.ravunana.manda.service;

import com.ravunana.manda.domain.AuditoriaCompra;
import com.ravunana.manda.repository.AuditoriaCompraRepository;
import com.ravunana.manda.service.dto.AuditoriaCompraDTO;
import com.ravunana.manda.service.mapper.AuditoriaCompraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AuditoriaCompra}.
 */
@Service
@Transactional
public class AuditoriaCompraService {

    private final Logger log = LoggerFactory.getLogger(AuditoriaCompraService.class);

    private final AuditoriaCompraRepository auditoriaCompraRepository;

    private final AuditoriaCompraMapper auditoriaCompraMapper;

    public AuditoriaCompraService(AuditoriaCompraRepository auditoriaCompraRepository, AuditoriaCompraMapper auditoriaCompraMapper) {
        this.auditoriaCompraRepository = auditoriaCompraRepository;
        this.auditoriaCompraMapper = auditoriaCompraMapper;
    }

    /**
     * Save a auditoriaCompra.
     *
     * @param auditoriaCompraDTO the entity to save.
     * @return the persisted entity.
     */
    public AuditoriaCompraDTO save(AuditoriaCompraDTO auditoriaCompraDTO) {
        log.debug("Request to save AuditoriaCompra : {}", auditoriaCompraDTO);
        AuditoriaCompra auditoriaCompra = auditoriaCompraMapper.toEntity(auditoriaCompraDTO);
        auditoriaCompra = auditoriaCompraRepository.save(auditoriaCompra);
        return auditoriaCompraMapper.toDto(auditoriaCompra);
    }

    /**
     * Get all the auditoriaCompras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditoriaCompraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuditoriaCompras");
        return auditoriaCompraRepository.findAll(pageable)
            .map(auditoriaCompraMapper::toDto);
    }


    /**
     * Get one auditoriaCompra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuditoriaCompraDTO> findOne(Long id) {
        log.debug("Request to get AuditoriaCompra : {}", id);
        return auditoriaCompraRepository.findById(id)
            .map(auditoriaCompraMapper::toDto);
    }

    /**
     * Delete the auditoriaCompra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AuditoriaCompra : {}", id);
        auditoriaCompraRepository.deleteById(id);
    }
}
