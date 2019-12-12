package com.ravunana.manda.service;

import com.ravunana.manda.domain.ContaCredito;
import com.ravunana.manda.repository.ContaCreditoRepository;
import com.ravunana.manda.service.dto.ContaCreditoDTO;
import com.ravunana.manda.service.mapper.ContaCreditoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContaCredito}.
 */
@Service
@Transactional
public class ContaCreditoService {

    private final Logger log = LoggerFactory.getLogger(ContaCreditoService.class);

    private final ContaCreditoRepository contaCreditoRepository;

    private final ContaCreditoMapper contaCreditoMapper;

    public ContaCreditoService(ContaCreditoRepository contaCreditoRepository, ContaCreditoMapper contaCreditoMapper) {
        this.contaCreditoRepository = contaCreditoRepository;
        this.contaCreditoMapper = contaCreditoMapper;
    }

    /**
     * Save a contaCredito.
     *
     * @param contaCreditoDTO the entity to save.
     * @return the persisted entity.
     */
    public ContaCreditoDTO save(ContaCreditoDTO contaCreditoDTO) {
        log.debug("Request to save ContaCredito : {}", contaCreditoDTO);
        ContaCredito contaCredito = contaCreditoMapper.toEntity(contaCreditoDTO);
        contaCredito = contaCreditoRepository.save(contaCredito);
        return contaCreditoMapper.toDto(contaCredito);
    }

    /**
     * Get all the contaCreditos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContaCreditoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContaCreditos");
        return contaCreditoRepository.findAll(pageable)
            .map(contaCreditoMapper::toDto);
    }


    /**
     * Get one contaCredito by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContaCreditoDTO> findOne(Long id) {
        log.debug("Request to get ContaCredito : {}", id);
        return contaCreditoRepository.findById(id)
            .map(contaCreditoMapper::toDto);
    }

    /**
     * Delete the contaCredito by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContaCredito : {}", id);
        contaCreditoRepository.deleteById(id);
    }
}
