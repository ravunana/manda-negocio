package com.ravunana.manda.service;

import com.ravunana.manda.domain.ClasseConta;
import com.ravunana.manda.repository.ClasseContaRepository;
import com.ravunana.manda.service.dto.ClasseContaDTO;
import com.ravunana.manda.service.mapper.ClasseContaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClasseConta}.
 */
@Service
@Transactional
public class ClasseContaService {

    private final Logger log = LoggerFactory.getLogger(ClasseContaService.class);

    private final ClasseContaRepository classeContaRepository;

    private final ClasseContaMapper classeContaMapper;

    public ClasseContaService(ClasseContaRepository classeContaRepository, ClasseContaMapper classeContaMapper) {
        this.classeContaRepository = classeContaRepository;
        this.classeContaMapper = classeContaMapper;
    }

    /**
     * Save a classeConta.
     *
     * @param classeContaDTO the entity to save.
     * @return the persisted entity.
     */
    public ClasseContaDTO save(ClasseContaDTO classeContaDTO) {
        log.debug("Request to save ClasseConta : {}", classeContaDTO);
        ClasseConta classeConta = classeContaMapper.toEntity(classeContaDTO);
        classeConta = classeContaRepository.save(classeConta);
        return classeContaMapper.toDto(classeConta);
    }

    /**
     * Get all the classeContas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClasseContaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClasseContas");
        return classeContaRepository.findAll(pageable)
            .map(classeContaMapper::toDto);
    }


    /**
     * Get one classeConta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClasseContaDTO> findOne(Long id) {
        log.debug("Request to get ClasseConta : {}", id);
        return classeContaRepository.findById(id)
            .map(classeContaMapper::toDto);
    }

    /**
     * Delete the classeConta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClasseConta : {}", id);
        classeContaRepository.deleteById(id);
    }
}
