package com.ravunana.manda.service;

import com.ravunana.manda.domain.ConversaoUnidade;
import com.ravunana.manda.repository.ConversaoUnidadeRepository;
import com.ravunana.manda.service.dto.ConversaoUnidadeDTO;
import com.ravunana.manda.service.mapper.ConversaoUnidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ConversaoUnidade}.
 */
@Service
@Transactional
public class ConversaoUnidadeService {

    private final Logger log = LoggerFactory.getLogger(ConversaoUnidadeService.class);

    private final ConversaoUnidadeRepository conversaoUnidadeRepository;

    private final ConversaoUnidadeMapper conversaoUnidadeMapper;

    public ConversaoUnidadeService(ConversaoUnidadeRepository conversaoUnidadeRepository, ConversaoUnidadeMapper conversaoUnidadeMapper) {
        this.conversaoUnidadeRepository = conversaoUnidadeRepository;
        this.conversaoUnidadeMapper = conversaoUnidadeMapper;
    }

    /**
     * Save a conversaoUnidade.
     *
     * @param conversaoUnidadeDTO the entity to save.
     * @return the persisted entity.
     */
    public ConversaoUnidadeDTO save(ConversaoUnidadeDTO conversaoUnidadeDTO) {
        log.debug("Request to save ConversaoUnidade : {}", conversaoUnidadeDTO);
        ConversaoUnidade conversaoUnidade = conversaoUnidadeMapper.toEntity(conversaoUnidadeDTO);
        conversaoUnidade = conversaoUnidadeRepository.save(conversaoUnidade);
        return conversaoUnidadeMapper.toDto(conversaoUnidade);
    }

    /**
     * Get all the conversaoUnidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConversaoUnidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConversaoUnidades");
        return conversaoUnidadeRepository.findAll(pageable)
            .map(conversaoUnidadeMapper::toDto);
    }


    /**
     * Get one conversaoUnidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConversaoUnidadeDTO> findOne(Long id) {
        log.debug("Request to get ConversaoUnidade : {}", id);
        return conversaoUnidadeRepository.findById(id)
            .map(conversaoUnidadeMapper::toDto);
    }

    /**
     * Delete the conversaoUnidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConversaoUnidade : {}", id);
        conversaoUnidadeRepository.deleteById(id);
    }
}
