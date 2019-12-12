package com.ravunana.manda.service;

import com.ravunana.manda.domain.DevolucaoVenda;
import com.ravunana.manda.repository.DevolucaoVendaRepository;
import com.ravunana.manda.service.dto.DevolucaoVendaDTO;
import com.ravunana.manda.service.mapper.DevolucaoVendaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DevolucaoVenda}.
 */
@Service
@Transactional
public class DevolucaoVendaService {

    private final Logger log = LoggerFactory.getLogger(DevolucaoVendaService.class);

    private final DevolucaoVendaRepository devolucaoVendaRepository;

    private final DevolucaoVendaMapper devolucaoVendaMapper;

    public DevolucaoVendaService(DevolucaoVendaRepository devolucaoVendaRepository, DevolucaoVendaMapper devolucaoVendaMapper) {
        this.devolucaoVendaRepository = devolucaoVendaRepository;
        this.devolucaoVendaMapper = devolucaoVendaMapper;
    }

    /**
     * Save a devolucaoVenda.
     *
     * @param devolucaoVendaDTO the entity to save.
     * @return the persisted entity.
     */
    public DevolucaoVendaDTO save(DevolucaoVendaDTO devolucaoVendaDTO) {
        log.debug("Request to save DevolucaoVenda : {}", devolucaoVendaDTO);
        DevolucaoVenda devolucaoVenda = devolucaoVendaMapper.toEntity(devolucaoVendaDTO);
        devolucaoVenda = devolucaoVendaRepository.save(devolucaoVenda);
        return devolucaoVendaMapper.toDto(devolucaoVenda);
    }

    /**
     * Get all the devolucaoVendas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DevolucaoVendaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DevolucaoVendas");
        return devolucaoVendaRepository.findAll(pageable)
            .map(devolucaoVendaMapper::toDto);
    }


    /**
     * Get one devolucaoVenda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DevolucaoVendaDTO> findOne(Long id) {
        log.debug("Request to get DevolucaoVenda : {}", id);
        return devolucaoVendaRepository.findById(id)
            .map(devolucaoVendaMapper::toDto);
    }

    /**
     * Delete the devolucaoVenda by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DevolucaoVenda : {}", id);
        devolucaoVendaRepository.deleteById(id);
    }
}
