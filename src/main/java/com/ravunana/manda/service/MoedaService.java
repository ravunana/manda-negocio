package com.ravunana.manda.service;

import com.ravunana.manda.domain.Moeda;
import com.ravunana.manda.repository.MoedaRepository;
import com.ravunana.manda.service.dto.MoedaDTO;
import com.ravunana.manda.service.mapper.MoedaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Moeda}.
 */
@Service
@Transactional
public class MoedaService {

    private final Logger log = LoggerFactory.getLogger(MoedaService.class);

    private final MoedaRepository moedaRepository;

    private final MoedaMapper moedaMapper;

    public MoedaService(MoedaRepository moedaRepository, MoedaMapper moedaMapper) {
        this.moedaRepository = moedaRepository;
        this.moedaMapper = moedaMapper;
    }

    /**
     * Save a moeda.
     *
     * @param moedaDTO the entity to save.
     * @return the persisted entity.
     */
    public MoedaDTO save(MoedaDTO moedaDTO) {
        log.debug("Request to save Moeda : {}", moedaDTO);
        Moeda moeda = moedaMapper.toEntity(moedaDTO);
        moeda = moedaRepository.save(moeda);
        return moedaMapper.toDto(moeda);
    }

    /**
     * Get all the moedas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MoedaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Moedas");
        return moedaRepository.findAll(pageable)
            .map(moedaMapper::toDto);
    }


    /**
     * Get one moeda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MoedaDTO> findOne(Long id) {
        log.debug("Request to get Moeda : {}", id);
        return moedaRepository.findById(id)
            .map(moedaMapper::toDto);
    }

    /**
     * Delete the moeda by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Moeda : {}", id);
        moedaRepository.deleteById(id);
    }

    public String getMoedaNacional() {
        return this.moedaRepository.findAll().stream().filter( m -> m.isNacional() == true ).findFirst().get().getCodigo();
    }
}
