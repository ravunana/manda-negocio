package com.ravunana.manda.service;

import com.ravunana.manda.domain.LicencaSoftware;
import com.ravunana.manda.repository.LicencaSoftwareRepository;
import com.ravunana.manda.service.dto.LicencaSoftwareDTO;
import com.ravunana.manda.service.mapper.LicencaSoftwareMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LicencaSoftware}.
 */
@Service
@Transactional
public class LicencaSoftwareService {

    private final Logger log = LoggerFactory.getLogger(LicencaSoftwareService.class);

    private final LicencaSoftwareRepository licencaSoftwareRepository;

    private final LicencaSoftwareMapper licencaSoftwareMapper;

    public LicencaSoftwareService(LicencaSoftwareRepository licencaSoftwareRepository, LicencaSoftwareMapper licencaSoftwareMapper) {
        this.licencaSoftwareRepository = licencaSoftwareRepository;
        this.licencaSoftwareMapper = licencaSoftwareMapper;
    }

    /**
     * Save a licencaSoftware.
     *
     * @param licencaSoftwareDTO the entity to save.
     * @return the persisted entity.
     */
    public LicencaSoftwareDTO save(LicencaSoftwareDTO licencaSoftwareDTO) {
        log.debug("Request to save LicencaSoftware : {}", licencaSoftwareDTO);
        LicencaSoftware licencaSoftware = licencaSoftwareMapper.toEntity(licencaSoftwareDTO);
        licencaSoftware = licencaSoftwareRepository.save(licencaSoftware);
        return licencaSoftwareMapper.toDto(licencaSoftware);
    }

    /**
     * Get all the licencaSoftwares.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LicencaSoftwareDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LicencaSoftwares");
        return licencaSoftwareRepository.findAll(pageable)
            .map(licencaSoftwareMapper::toDto);
    }


    /**
     * Get one licencaSoftware by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LicencaSoftwareDTO> findOne(Long id) {
        log.debug("Request to get LicencaSoftware : {}", id);
        return licencaSoftwareRepository.findById(id)
            .map(licencaSoftwareMapper::toDto);
    }

    /**
     * Delete the licencaSoftware by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LicencaSoftware : {}", id);
        licencaSoftwareRepository.deleteById(id);
    }
}
