package com.ravunana.manda.service;

import com.ravunana.manda.domain.RegraMovimentacaoCredito;
import com.ravunana.manda.repository.RegraMovimentacaoCreditoRepository;
import com.ravunana.manda.service.dto.RegraMovimentacaoCreditoDTO;
import com.ravunana.manda.service.mapper.RegraMovimentacaoCreditoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RegraMovimentacaoCredito}.
 */
@Service
@Transactional
public class RegraMovimentacaoCreditoService {

    private final Logger log = LoggerFactory.getLogger(RegraMovimentacaoCreditoService.class);

    private final RegraMovimentacaoCreditoRepository regraMovimentacaoCreditoRepository;

    private final RegraMovimentacaoCreditoMapper regraMovimentacaoCreditoMapper;

    public RegraMovimentacaoCreditoService(RegraMovimentacaoCreditoRepository regraMovimentacaoCreditoRepository, RegraMovimentacaoCreditoMapper regraMovimentacaoCreditoMapper) {
        this.regraMovimentacaoCreditoRepository = regraMovimentacaoCreditoRepository;
        this.regraMovimentacaoCreditoMapper = regraMovimentacaoCreditoMapper;
    }

    /**
     * Save a regraMovimentacaoCredito.
     *
     * @param regraMovimentacaoCreditoDTO the entity to save.
     * @return the persisted entity.
     */
    public RegraMovimentacaoCreditoDTO save(RegraMovimentacaoCreditoDTO regraMovimentacaoCreditoDTO) {
        log.debug("Request to save RegraMovimentacaoCredito : {}", regraMovimentacaoCreditoDTO);
        RegraMovimentacaoCredito regraMovimentacaoCredito = regraMovimentacaoCreditoMapper.toEntity(regraMovimentacaoCreditoDTO);
        regraMovimentacaoCredito = regraMovimentacaoCreditoRepository.save(regraMovimentacaoCredito);
        return regraMovimentacaoCreditoMapper.toDto(regraMovimentacaoCredito);
    }

    /**
     * Get all the regraMovimentacaoCreditos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RegraMovimentacaoCreditoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegraMovimentacaoCreditos");
        return regraMovimentacaoCreditoRepository.findAll(pageable)
            .map(regraMovimentacaoCreditoMapper::toDto);
    }


    /**
     * Get one regraMovimentacaoCredito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RegraMovimentacaoCreditoDTO> findOne(Long id) {
        log.debug("Request to get RegraMovimentacaoCredito : {}", id);
        return regraMovimentacaoCreditoRepository.findById(id)
            .map(regraMovimentacaoCreditoMapper::toDto);
    }

    /**
     * Delete the regraMovimentacaoCredito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RegraMovimentacaoCredito : {}", id);
        regraMovimentacaoCreditoRepository.deleteById(id);
    }
}
