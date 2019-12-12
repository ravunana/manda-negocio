package com.ravunana.manda.service;

import com.ravunana.manda.domain.AuditoriaVenda;
import com.ravunana.manda.repository.AuditoriaVendaRepository;
import com.ravunana.manda.service.dto.AuditoriaVendaDTO;
import com.ravunana.manda.service.mapper.AuditoriaVendaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AuditoriaVenda}.
 */
@Service
@Transactional
public class AuditoriaVendaService {

    private final Logger log = LoggerFactory.getLogger(AuditoriaVendaService.class);

    private final AuditoriaVendaRepository auditoriaVendaRepository;

    private final AuditoriaVendaMapper auditoriaVendaMapper;

    public AuditoriaVendaService(AuditoriaVendaRepository auditoriaVendaRepository, AuditoriaVendaMapper auditoriaVendaMapper) {
        this.auditoriaVendaRepository = auditoriaVendaRepository;
        this.auditoriaVendaMapper = auditoriaVendaMapper;
    }

    /**
     * Save a auditoriaVenda.
     *
     * @param auditoriaVendaDTO the entity to save.
     * @return the persisted entity.
     */
    public AuditoriaVendaDTO save(AuditoriaVendaDTO auditoriaVendaDTO) {
        log.debug("Request to save AuditoriaVenda : {}", auditoriaVendaDTO);
        AuditoriaVenda auditoriaVenda = auditoriaVendaMapper.toEntity(auditoriaVendaDTO);
        auditoriaVenda = auditoriaVendaRepository.save(auditoriaVenda);
        return auditoriaVendaMapper.toDto(auditoriaVenda);
    }

    /**
     * Get all the auditoriaVendas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditoriaVendaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuditoriaVendas");
        return auditoriaVendaRepository.findAll(pageable)
            .map(auditoriaVendaMapper::toDto);
    }


    /**
     * Get one auditoriaVenda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuditoriaVendaDTO> findOne(Long id) {
        log.debug("Request to get AuditoriaVenda : {}", id);
        return auditoriaVendaRepository.findById(id)
            .map(auditoriaVendaMapper::toDto);
    }

    /**
     * Delete the auditoriaVenda by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AuditoriaVenda : {}", id);
        auditoriaVendaRepository.deleteById(id);
    }
}
