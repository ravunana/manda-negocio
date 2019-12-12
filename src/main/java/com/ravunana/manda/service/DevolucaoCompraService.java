package com.ravunana.manda.service;

import com.ravunana.manda.domain.DevolucaoCompra;
import com.ravunana.manda.repository.DevolucaoCompraRepository;
import com.ravunana.manda.service.dto.DevolucaoCompraDTO;
import com.ravunana.manda.service.mapper.DevolucaoCompraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DevolucaoCompra}.
 */
@Service
@Transactional
public class DevolucaoCompraService {

    private final Logger log = LoggerFactory.getLogger(DevolucaoCompraService.class);

    private final DevolucaoCompraRepository devolucaoCompraRepository;

    private final DevolucaoCompraMapper devolucaoCompraMapper;

    public DevolucaoCompraService(DevolucaoCompraRepository devolucaoCompraRepository, DevolucaoCompraMapper devolucaoCompraMapper) {
        this.devolucaoCompraRepository = devolucaoCompraRepository;
        this.devolucaoCompraMapper = devolucaoCompraMapper;
    }

    /**
     * Save a devolucaoCompra.
     *
     * @param devolucaoCompraDTO the entity to save.
     * @return the persisted entity.
     */
    public DevolucaoCompraDTO save(DevolucaoCompraDTO devolucaoCompraDTO) {
        log.debug("Request to save DevolucaoCompra : {}", devolucaoCompraDTO);
        DevolucaoCompra devolucaoCompra = devolucaoCompraMapper.toEntity(devolucaoCompraDTO);
        devolucaoCompra = devolucaoCompraRepository.save(devolucaoCompra);
        return devolucaoCompraMapper.toDto(devolucaoCompra);
    }

    /**
     * Get all the devolucaoCompras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DevolucaoCompraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DevolucaoCompras");
        return devolucaoCompraRepository.findAll(pageable)
            .map(devolucaoCompraMapper::toDto);
    }


    /**
     * Get one devolucaoCompra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DevolucaoCompraDTO> findOne(Long id) {
        log.debug("Request to get DevolucaoCompra : {}", id);
        return devolucaoCompraRepository.findById(id)
            .map(devolucaoCompraMapper::toDto);
    }

    /**
     * Delete the devolucaoCompra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DevolucaoCompra : {}", id);
        devolucaoCompraRepository.deleteById(id);
    }
}
