package com.ravunana.manda.service;

import com.ravunana.manda.domain.RegraMovimentacaoDebito;
import com.ravunana.manda.repository.RegraMovimentacaoDebitoRepository;
import com.ravunana.manda.service.dto.RegraMovimentacaoDebitoDTO;
import com.ravunana.manda.service.mapper.RegraMovimentacaoDebitoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RegraMovimentacaoDebito}.
 */
@Service
@Transactional
public class RegraMovimentacaoDebitoService {

    private final Logger log = LoggerFactory.getLogger(RegraMovimentacaoDebitoService.class);

    private final RegraMovimentacaoDebitoRepository regraMovimentacaoDebitoRepository;

    private final RegraMovimentacaoDebitoMapper regraMovimentacaoDebitoMapper;

    public RegraMovimentacaoDebitoService(RegraMovimentacaoDebitoRepository regraMovimentacaoDebitoRepository, RegraMovimentacaoDebitoMapper regraMovimentacaoDebitoMapper) {
        this.regraMovimentacaoDebitoRepository = regraMovimentacaoDebitoRepository;
        this.regraMovimentacaoDebitoMapper = regraMovimentacaoDebitoMapper;
    }

    /**
     * Save a regraMovimentacaoDebito.
     *
     * @param regraMovimentacaoDebitoDTO the entity to save.
     * @return the persisted entity.
     */
    public RegraMovimentacaoDebitoDTO save(RegraMovimentacaoDebitoDTO regraMovimentacaoDebitoDTO) {
        log.debug("Request to save RegraMovimentacaoDebito : {}", regraMovimentacaoDebitoDTO);
        RegraMovimentacaoDebito regraMovimentacaoDebito = regraMovimentacaoDebitoMapper.toEntity(regraMovimentacaoDebitoDTO);
        regraMovimentacaoDebito = regraMovimentacaoDebitoRepository.save(regraMovimentacaoDebito);
        return regraMovimentacaoDebitoMapper.toDto(regraMovimentacaoDebito);
    }

    /**
     * Get all the regraMovimentacaoDebitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RegraMovimentacaoDebitoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegraMovimentacaoDebitos");
        return regraMovimentacaoDebitoRepository.findAll(pageable)
            .map(regraMovimentacaoDebitoMapper::toDto);
    }


    /**
     * Get one regraMovimentacaoDebito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RegraMovimentacaoDebitoDTO> findOne(Long id) {
        log.debug("Request to get RegraMovimentacaoDebito : {}", id);
        return regraMovimentacaoDebitoRepository.findById(id)
            .map(regraMovimentacaoDebitoMapper::toDto);
    }

    /**
     * Delete the regraMovimentacaoDebito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RegraMovimentacaoDebito : {}", id);
        regraMovimentacaoDebitoRepository.deleteById(id);
    }
}
