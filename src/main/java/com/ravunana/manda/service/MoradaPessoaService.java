package com.ravunana.manda.service;

import com.ravunana.manda.domain.MoradaPessoa;
import com.ravunana.manda.repository.MoradaPessoaRepository;
import com.ravunana.manda.service.dto.MoradaPessoaDTO;
import com.ravunana.manda.service.mapper.MoradaPessoaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MoradaPessoa}.
 */
@Service
@Transactional
public class MoradaPessoaService {

    private final Logger log = LoggerFactory.getLogger(MoradaPessoaService.class);

    private final MoradaPessoaRepository moradaPessoaRepository;

    private final MoradaPessoaMapper moradaPessoaMapper;

    private List<MoradaPessoaDTO> moradas = new ArrayList<>();

    public MoradaPessoaService(MoradaPessoaRepository moradaPessoaRepository, MoradaPessoaMapper moradaPessoaMapper) {
        this.moradaPessoaRepository = moradaPessoaRepository;
        this.moradaPessoaMapper = moradaPessoaMapper;
    }

    /**
     * Save a moradaPessoa.
     *
     * @param moradaPessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public MoradaPessoaDTO save(MoradaPessoaDTO moradaPessoaDTO) {
        log.debug("Request to save MoradaPessoa : {}", moradaPessoaDTO);
        MoradaPessoa moradaPessoa = moradaPessoaMapper.toEntity(moradaPessoaDTO);
        moradaPessoa = moradaPessoaRepository.save(moradaPessoa);
        return moradaPessoaMapper.toDto(moradaPessoa);
    }

    /**
     * Get all the moradaPessoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MoradaPessoaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MoradaPessoas");
        return moradaPessoaRepository.findAll(pageable)
            .map(moradaPessoaMapper::toDto);
    }


    /**
     * Get one moradaPessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MoradaPessoaDTO> findOne(Long id) {
        log.debug("Request to get MoradaPessoa : {}", id);
        return moradaPessoaRepository.findById(id)
            .map(moradaPessoaMapper::toDto);
    }

    /**
     * Delete the moradaPessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MoradaPessoa : {}", id);
        moradaPessoaRepository.deleteById(id);
    }


    public MoradaPessoaDTO addMorada(MoradaPessoaDTO morada) {
        Boolean result = moradas.add(morada);
        if ( result )
            return morada;
        else
            return new MoradaPessoaDTO();
    }

    public MoradaPessoaDTO deleteMorada(int index) {
        return moradas.remove(index);
    }

    public List<MoradaPessoaDTO> listMoradas() {
        return moradas;
    }

    public void limparMoradas() {
        moradas.clear();
    }
}
