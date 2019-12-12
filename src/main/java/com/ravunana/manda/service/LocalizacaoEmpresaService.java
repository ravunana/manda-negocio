package com.ravunana.manda.service;

import com.ravunana.manda.domain.LocalizacaoEmpresa;
import com.ravunana.manda.repository.LocalizacaoEmpresaRepository;
import com.ravunana.manda.service.dto.LocalizacaoEmpresaDTO;
import com.ravunana.manda.service.mapper.LocalizacaoEmpresaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LocalizacaoEmpresa}.
 */
@Service
@Transactional
public class LocalizacaoEmpresaService {

    private final Logger log = LoggerFactory.getLogger(LocalizacaoEmpresaService.class);

    private final LocalizacaoEmpresaRepository localizacaoEmpresaRepository;

    private final LocalizacaoEmpresaMapper localizacaoEmpresaMapper;

    public LocalizacaoEmpresaService(LocalizacaoEmpresaRepository localizacaoEmpresaRepository, LocalizacaoEmpresaMapper localizacaoEmpresaMapper) {
        this.localizacaoEmpresaRepository = localizacaoEmpresaRepository;
        this.localizacaoEmpresaMapper = localizacaoEmpresaMapper;
    }

    /**
     * Save a localizacaoEmpresa.
     *
     * @param localizacaoEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public LocalizacaoEmpresaDTO save(LocalizacaoEmpresaDTO localizacaoEmpresaDTO) {
        log.debug("Request to save LocalizacaoEmpresa : {}", localizacaoEmpresaDTO);
        LocalizacaoEmpresa localizacaoEmpresa = localizacaoEmpresaMapper.toEntity(localizacaoEmpresaDTO);
        localizacaoEmpresa = localizacaoEmpresaRepository.save(localizacaoEmpresa);
        return localizacaoEmpresaMapper.toDto(localizacaoEmpresa);
    }

    /**
     * Get all the localizacaoEmpresas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocalizacaoEmpresaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocalizacaoEmpresas");
        return localizacaoEmpresaRepository.findAll(pageable)
            .map(localizacaoEmpresaMapper::toDto);
    }


    /**
     * Get one localizacaoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocalizacaoEmpresaDTO> findOne(Long id) {
        log.debug("Request to get LocalizacaoEmpresa : {}", id);
        return localizacaoEmpresaRepository.findById(id)
            .map(localizacaoEmpresaMapper::toDto);
    }

    /**
     * Delete the localizacaoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LocalizacaoEmpresa : {}", id);
        localizacaoEmpresaRepository.deleteById(id);
    }
}
