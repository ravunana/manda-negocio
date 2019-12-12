package com.ravunana.manda.service;

import com.ravunana.manda.domain.RelacionamentoPessoa;
import com.ravunana.manda.repository.RelacionamentoPessoaRepository;
import com.ravunana.manda.service.dto.RelacionamentoPessoaDTO;
import com.ravunana.manda.service.mapper.RelacionamentoPessoaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RelacionamentoPessoa}.
 */
@Service
@Transactional
public class RelacionamentoPessoaService {

    private final Logger log = LoggerFactory.getLogger(RelacionamentoPessoaService.class);

    private final RelacionamentoPessoaRepository relacionamentoPessoaRepository;

    private final RelacionamentoPessoaMapper relacionamentoPessoaMapper;

    public RelacionamentoPessoaService(RelacionamentoPessoaRepository relacionamentoPessoaRepository, RelacionamentoPessoaMapper relacionamentoPessoaMapper) {
        this.relacionamentoPessoaRepository = relacionamentoPessoaRepository;
        this.relacionamentoPessoaMapper = relacionamentoPessoaMapper;
    }

    /**
     * Save a relacionamentoPessoa.
     *
     * @param relacionamentoPessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public RelacionamentoPessoaDTO save(RelacionamentoPessoaDTO relacionamentoPessoaDTO) {
        log.debug("Request to save RelacionamentoPessoa : {}", relacionamentoPessoaDTO);
        RelacionamentoPessoa relacionamentoPessoa = relacionamentoPessoaMapper.toEntity(relacionamentoPessoaDTO);
        relacionamentoPessoa = relacionamentoPessoaRepository.save(relacionamentoPessoa);
        return relacionamentoPessoaMapper.toDto(relacionamentoPessoa);
    }

    /**
     * Get all the relacionamentoPessoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RelacionamentoPessoaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelacionamentoPessoas");
        return relacionamentoPessoaRepository.findAll(pageable)
            .map(relacionamentoPessoaMapper::toDto);
    }


    /**
     * Get one relacionamentoPessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RelacionamentoPessoaDTO> findOne(Long id) {
        log.debug("Request to get RelacionamentoPessoa : {}", id);
        return relacionamentoPessoaRepository.findById(id)
            .map(relacionamentoPessoaMapper::toDto);
    }

    /**
     * Delete the relacionamentoPessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RelacionamentoPessoa : {}", id);
        relacionamentoPessoaRepository.deleteById(id);
    }
}
