package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.FluxoDocumentoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.FluxoDocumentoDTO;
import com.ravunana.manda.service.dto.FluxoDocumentoCriteria;
import com.ravunana.manda.service.FluxoDocumentoQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.FluxoDocumento}.
 */
@RestController
@RequestMapping("/api")
public class FluxoDocumentoResource {

    private final Logger log = LoggerFactory.getLogger(FluxoDocumentoResource.class);

    private static final String ENTITY_NAME = "fluxoDocumento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FluxoDocumentoService fluxoDocumentoService;

    private final FluxoDocumentoQueryService fluxoDocumentoQueryService;

    public FluxoDocumentoResource(FluxoDocumentoService fluxoDocumentoService, FluxoDocumentoQueryService fluxoDocumentoQueryService) {
        this.fluxoDocumentoService = fluxoDocumentoService;
        this.fluxoDocumentoQueryService = fluxoDocumentoQueryService;
    }

    /**
     * {@code POST  /fluxo-documentos} : Create a new fluxoDocumento.
     *
     * @param fluxoDocumentoDTO the fluxoDocumentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fluxoDocumentoDTO, or with status {@code 400 (Bad Request)} if the fluxoDocumento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fluxo-documentos")
    public ResponseEntity<FluxoDocumentoDTO> createFluxoDocumento(@Valid @RequestBody FluxoDocumentoDTO fluxoDocumentoDTO) throws URISyntaxException {
        log.debug("REST request to save FluxoDocumento : {}", fluxoDocumentoDTO);
        if (fluxoDocumentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new fluxoDocumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FluxoDocumentoDTO result = fluxoDocumentoService.save(fluxoDocumentoDTO);
        return ResponseEntity.created(new URI("/api/fluxo-documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fluxo-documentos} : Updates an existing fluxoDocumento.
     *
     * @param fluxoDocumentoDTO the fluxoDocumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fluxoDocumentoDTO,
     * or with status {@code 400 (Bad Request)} if the fluxoDocumentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fluxoDocumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fluxo-documentos")
    public ResponseEntity<FluxoDocumentoDTO> updateFluxoDocumento(@Valid @RequestBody FluxoDocumentoDTO fluxoDocumentoDTO) throws URISyntaxException {
        log.debug("REST request to update FluxoDocumento : {}", fluxoDocumentoDTO);
        if (fluxoDocumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FluxoDocumentoDTO result = fluxoDocumentoService.save(fluxoDocumentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fluxoDocumentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fluxo-documentos} : get all the fluxoDocumentos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fluxoDocumentos in body.
     */
    @GetMapping("/fluxo-documentos")
    public ResponseEntity<List<FluxoDocumentoDTO>> getAllFluxoDocumentos(FluxoDocumentoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FluxoDocumentos by criteria: {}", criteria);
        Page<FluxoDocumentoDTO> page = fluxoDocumentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /fluxo-documentos/count} : count all the fluxoDocumentos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/fluxo-documentos/count")
    public ResponseEntity<Long> countFluxoDocumentos(FluxoDocumentoCriteria criteria) {
        log.debug("REST request to count FluxoDocumentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(fluxoDocumentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fluxo-documentos/:id} : get the "id" fluxoDocumento.
     *
     * @param id the id of the fluxoDocumentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fluxoDocumentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fluxo-documentos/{id}")
    public ResponseEntity<FluxoDocumentoDTO> getFluxoDocumento(@PathVariable Long id) {
        log.debug("REST request to get FluxoDocumento : {}", id);
        Optional<FluxoDocumentoDTO> fluxoDocumentoDTO = fluxoDocumentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fluxoDocumentoDTO);
    }

    /**
     * {@code DELETE  /fluxo-documentos/:id} : delete the "id" fluxoDocumento.
     *
     * @param id the id of the fluxoDocumentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fluxo-documentos/{id}")
    public ResponseEntity<Void> deleteFluxoDocumento(@PathVariable Long id) {
        log.debug("REST request to delete FluxoDocumento : {}", id);
        fluxoDocumentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
