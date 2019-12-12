package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.DocumentoComercialService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.DocumentoComercialDTO;
import com.ravunana.manda.service.dto.DocumentoComercialCriteria;
import com.ravunana.manda.service.DocumentoComercialQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ravunana.manda.domain.DocumentoComercial}.
 */
@RestController
@RequestMapping("/api")
public class DocumentoComercialResource {

    private final Logger log = LoggerFactory.getLogger(DocumentoComercialResource.class);

    private static final String ENTITY_NAME = "documentoComercial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentoComercialService documentoComercialService;

    private final DocumentoComercialQueryService documentoComercialQueryService;

    public DocumentoComercialResource(DocumentoComercialService documentoComercialService, DocumentoComercialQueryService documentoComercialQueryService) {
        this.documentoComercialService = documentoComercialService;
        this.documentoComercialQueryService = documentoComercialQueryService;
    }

    /**
     * {@code POST  /documento-comercials} : Create a new documentoComercial.
     *
     * @param documentoComercialDTO the documentoComercialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentoComercialDTO, or with status {@code 400 (Bad Request)} if the documentoComercial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documento-comercials")
    public ResponseEntity<DocumentoComercialDTO> createDocumentoComercial(@Valid @RequestBody DocumentoComercialDTO documentoComercialDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentoComercial : {}", documentoComercialDTO);
        if (documentoComercialDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentoComercial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentoComercialDTO result = documentoComercialService.save(documentoComercialDTO);
        return ResponseEntity.created(new URI("/api/documento-comercials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documento-comercials} : Updates an existing documentoComercial.
     *
     * @param documentoComercialDTO the documentoComercialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentoComercialDTO,
     * or with status {@code 400 (Bad Request)} if the documentoComercialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentoComercialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documento-comercials")
    public ResponseEntity<DocumentoComercialDTO> updateDocumentoComercial(@Valid @RequestBody DocumentoComercialDTO documentoComercialDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentoComercial : {}", documentoComercialDTO);
        if (documentoComercialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentoComercialDTO result = documentoComercialService.save(documentoComercialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentoComercialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /documento-comercials} : get all the documentoComercials.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentoComercials in body.
     */
    @GetMapping("/documento-comercials")
    public ResponseEntity<List<DocumentoComercialDTO>> getAllDocumentoComercials(DocumentoComercialCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DocumentoComercials by criteria: {}", criteria);
        Page<DocumentoComercialDTO> page = documentoComercialQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /documento-comercials/count} : count all the documentoComercials.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/documento-comercials/count")
    public ResponseEntity<Long> countDocumentoComercials(DocumentoComercialCriteria criteria) {
        log.debug("REST request to count DocumentoComercials by criteria: {}", criteria);
        return ResponseEntity.ok().body(documentoComercialQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /documento-comercials/:id} : get the "id" documentoComercial.
     *
     * @param id the id of the documentoComercialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentoComercialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documento-comercials/{id}")
    public ResponseEntity<DocumentoComercialDTO> getDocumentoComercial(@PathVariable Long id) {
        log.debug("REST request to get DocumentoComercial : {}", id);
        Optional<DocumentoComercialDTO> documentoComercialDTO = documentoComercialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentoComercialDTO);
    }

    /**
     * {@code DELETE  /documento-comercials/:id} : delete the "id" documentoComercial.
     *
     * @param id the id of the documentoComercialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documento-comercials/{id}")
    public ResponseEntity<Void> deleteDocumentoComercial(@PathVariable Long id) {
        log.debug("REST request to delete DocumentoComercial : {}", id);
        documentoComercialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
