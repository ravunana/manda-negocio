package com.ravunana.manda.service;

import com.ravunana.manda.domain.SerieDocumento;
import com.ravunana.manda.repository.SerieDocumentoRepository;
import com.ravunana.manda.service.dto.SerieDocumentoDTO;
import com.ravunana.manda.service.mapper.SerieDocumentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SerieDocumento}.
 */
@Service
@Transactional
public class SerieDocumentoService {

    private final Logger log = LoggerFactory.getLogger(SerieDocumentoService.class);

    private final SerieDocumentoRepository serieDocumentoRepository;

    private final SerieDocumentoMapper serieDocumentoMapper;

    public SerieDocumentoService(SerieDocumentoRepository serieDocumentoRepository, SerieDocumentoMapper serieDocumentoMapper) {
        this.serieDocumentoRepository = serieDocumentoRepository;
        this.serieDocumentoMapper = serieDocumentoMapper;
    }

    /**
     * Save a serieDocumento.
     *
     * @param serieDocumentoDTO the entity to save.
     * @return the persisted entity.
     */
    public SerieDocumentoDTO save(SerieDocumentoDTO serieDocumentoDTO) {
        log.debug("Request to save SerieDocumento : {}", serieDocumentoDTO);
        SerieDocumento serieDocumento = serieDocumentoMapper.toEntity(serieDocumentoDTO);
        serieDocumento = serieDocumentoRepository.save(serieDocumento);
        return serieDocumentoMapper.toDto(serieDocumento);
    }

    /**
     * Get all the serieDocumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SerieDocumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SerieDocumentos");
        return serieDocumentoRepository.findAll(pageable)
            .map(serieDocumentoMapper::toDto);
    }


    /**
     * Get one serieDocumento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SerieDocumentoDTO> findOne(Long id) {
        log.debug("Request to get SerieDocumento : {}", id);
        return serieDocumentoRepository.findById(id)
            .map(serieDocumentoMapper::toDto);
    }

    /**
     * Delete the serieDocumento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SerieDocumento : {}", id);
        serieDocumentoRepository.deleteById(id);
    }

    public SerieDocumento getSerieDocumentoAnoActual() {
        return serieDocumentoRepository.findAll().stream().findFirst().get();
    }

    public void atualizarSerieDocumento( SerieDocumento serie ) {
        serieDocumentoRepository.save(serie);
    }
}
