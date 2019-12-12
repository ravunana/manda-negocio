package com.ravunana.manda.service;

import com.ravunana.manda.domain.DetalheAduaneiro;
import com.ravunana.manda.repository.DetalheAduaneiroRepository;
import com.ravunana.manda.service.dto.DetalheAduaneiroDTO;
import com.ravunana.manda.service.mapper.DetalheAduaneiroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DetalheAduaneiro}.
 */
@Service
@Transactional
public class DetalheAduaneiroService {

    private final Logger log = LoggerFactory.getLogger(DetalheAduaneiroService.class);

    private final DetalheAduaneiroRepository detalheAduaneiroRepository;

    private final DetalheAduaneiroMapper detalheAduaneiroMapper;

    public DetalheAduaneiroService(DetalheAduaneiroRepository detalheAduaneiroRepository, DetalheAduaneiroMapper detalheAduaneiroMapper) {
        this.detalheAduaneiroRepository = detalheAduaneiroRepository;
        this.detalheAduaneiroMapper = detalheAduaneiroMapper;
    }

    /**
     * Save a detalheAduaneiro.
     *
     * @param detalheAduaneiroDTO the entity to save.
     * @return the persisted entity.
     */
    public DetalheAduaneiroDTO save(DetalheAduaneiroDTO detalheAduaneiroDTO) {
        log.debug("Request to save DetalheAduaneiro : {}", detalheAduaneiroDTO);
        DetalheAduaneiro detalheAduaneiro = detalheAduaneiroMapper.toEntity(detalheAduaneiroDTO);
        detalheAduaneiro = detalheAduaneiroRepository.save(detalheAduaneiro);
        return detalheAduaneiroMapper.toDto(detalheAduaneiro);
    }

    /**
     * Get all the detalheAduaneiros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetalheAduaneiroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetalheAduaneiros");
        return detalheAduaneiroRepository.findAll(pageable)
            .map(detalheAduaneiroMapper::toDto);
    }


    /**
     * Get one detalheAduaneiro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetalheAduaneiroDTO> findOne(Long id) {
        log.debug("Request to get DetalheAduaneiro : {}", id);
        return detalheAduaneiroRepository.findById(id)
            .map(detalheAduaneiroMapper::toDto);
    }

    /**
     * Delete the detalheAduaneiro by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetalheAduaneiro : {}", id);
        detalheAduaneiroRepository.deleteById(id);
    }
}
