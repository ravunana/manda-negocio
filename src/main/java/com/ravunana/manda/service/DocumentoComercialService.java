package com.ravunana.manda.service;

import com.ravunana.manda.domain.DocumentoComercial;
import com.ravunana.manda.repository.DocumentoComercialRepository;
import com.ravunana.manda.service.dto.DocumentoComercialDTO;
import com.ravunana.manda.service.mapper.DocumentoComercialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentoComercial}.
 */
@Service
@Transactional
public class DocumentoComercialService {

    private final Logger log = LoggerFactory.getLogger(DocumentoComercialService.class);

    private final DocumentoComercialRepository documentoComercialRepository;

    private final DocumentoComercialMapper documentoComercialMapper;

    public DocumentoComercialService(DocumentoComercialRepository documentoComercialRepository, DocumentoComercialMapper documentoComercialMapper) {
        this.documentoComercialRepository = documentoComercialRepository;
        this.documentoComercialMapper = documentoComercialMapper;
    }

    /**
     * Save a documentoComercial.
     *
     * @param documentoComercialDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentoComercialDTO save(DocumentoComercialDTO documentoComercialDTO) {
        log.debug("Request to save DocumentoComercial : {}", documentoComercialDTO);
        DocumentoComercial documentoComercial = documentoComercialMapper.toEntity(documentoComercialDTO);
        documentoComercial = documentoComercialRepository.save(documentoComercial);
        return documentoComercialMapper.toDto(documentoComercial);
    }

    /**
     * Get all the documentoComercials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentoComercialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentoComercials");
        return documentoComercialRepository.findAll(pageable)
            .map(documentoComercialMapper::toDto);
    }


    /**
     * Get one documentoComercial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentoComercialDTO> findOne(Long id) {
        log.debug("Request to get DocumentoComercial : {}", id);
        return documentoComercialRepository.findById(id)
            .map(documentoComercialMapper::toDto);
    }

    /**
     * Delete the documentoComercial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentoComercial : {}", id);
        documentoComercialRepository.deleteById(id);
    }

    public DocumentoComercial getDocumentoComercial(String tipo) {
        return this.documentoComercialRepository.findAll().stream().filter( f -> f.getNome().equals(tipo) ).findFirst().get();
    }
}
