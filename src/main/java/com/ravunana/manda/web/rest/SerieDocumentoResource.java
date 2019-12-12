package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.SerieDocumentoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.SerieDocumentoDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.SerieDocumento}.
 */
@RestController
@RequestMapping("/api")
public class SerieDocumentoResource {

    private final Logger log = LoggerFactory.getLogger(SerieDocumentoResource.class);

    private static final String ENTITY_NAME = "serieDocumento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SerieDocumentoService serieDocumentoService;

    public SerieDocumentoResource(SerieDocumentoService serieDocumentoService) {
        this.serieDocumentoService = serieDocumentoService;
    }

    /**
     * {@code POST  /serie-documentos} : Create a new serieDocumento.
     *
     * @param serieDocumentoDTO the serieDocumentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serieDocumentoDTO, or with status {@code 400 (Bad Request)} if the serieDocumento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/serie-documentos")
    public ResponseEntity<SerieDocumentoDTO> createSerieDocumento(@Valid @RequestBody SerieDocumentoDTO serieDocumentoDTO) throws URISyntaxException {
        log.debug("REST request to save SerieDocumento : {}", serieDocumentoDTO);
        if (serieDocumentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new serieDocumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SerieDocumentoDTO result = serieDocumentoService.save(serieDocumentoDTO);
        return ResponseEntity.created(new URI("/api/serie-documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /serie-documentos} : Updates an existing serieDocumento.
     *
     * @param serieDocumentoDTO the serieDocumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serieDocumentoDTO,
     * or with status {@code 400 (Bad Request)} if the serieDocumentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serieDocumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/serie-documentos")
    public ResponseEntity<SerieDocumentoDTO> updateSerieDocumento(@Valid @RequestBody SerieDocumentoDTO serieDocumentoDTO) throws URISyntaxException {
        log.debug("REST request to update SerieDocumento : {}", serieDocumentoDTO);
        if (serieDocumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SerieDocumentoDTO result = serieDocumentoService.save(serieDocumentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serieDocumentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /serie-documentos} : get all the serieDocumentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serieDocumentos in body.
     */
    @GetMapping("/serie-documentos")
    public ResponseEntity<List<SerieDocumentoDTO>> getAllSerieDocumentos(Pageable pageable) {
        log.debug("REST request to get a page of SerieDocumentos");
        Page<SerieDocumentoDTO> page = serieDocumentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /serie-documentos/:id} : get the "id" serieDocumento.
     *
     * @param id the id of the serieDocumentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serieDocumentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/serie-documentos/{id}")
    public ResponseEntity<SerieDocumentoDTO> getSerieDocumento(@PathVariable Long id) {
        log.debug("REST request to get SerieDocumento : {}", id);
        Optional<SerieDocumentoDTO> serieDocumentoDTO = serieDocumentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serieDocumentoDTO);
    }

    /**
     * {@code DELETE  /serie-documentos/:id} : delete the "id" serieDocumento.
     *
     * @param id the id of the serieDocumentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/serie-documentos/{id}")
    public ResponseEntity<Void> deleteSerieDocumento(@PathVariable Long id) {
        log.debug("REST request to delete SerieDocumento : {}", id);
        serieDocumentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
