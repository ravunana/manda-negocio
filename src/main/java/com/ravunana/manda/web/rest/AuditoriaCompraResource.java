package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.AuditoriaCompraService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.AuditoriaCompraDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ravunana.manda.domain.AuditoriaCompra}.
 */
@RestController
@RequestMapping("/api")
public class AuditoriaCompraResource {

    private final Logger log = LoggerFactory.getLogger(AuditoriaCompraResource.class);

    private static final String ENTITY_NAME = "auditoriaCompra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditoriaCompraService auditoriaCompraService;

    public AuditoriaCompraResource(AuditoriaCompraService auditoriaCompraService) {
        this.auditoriaCompraService = auditoriaCompraService;
    }

    /**
     * {@code POST  /auditoria-compras} : Create a new auditoriaCompra.
     *
     * @param auditoriaCompraDTO the auditoriaCompraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditoriaCompraDTO, or with status {@code 400 (Bad Request)} if the auditoriaCompra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/auditoria-compras")
    public ResponseEntity<AuditoriaCompraDTO> createAuditoriaCompra(@RequestBody AuditoriaCompraDTO auditoriaCompraDTO) throws URISyntaxException {
        log.debug("REST request to save AuditoriaCompra : {}", auditoriaCompraDTO);
        if (auditoriaCompraDTO.getId() != null) {
            throw new BadRequestAlertException("A new auditoriaCompra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuditoriaCompraDTO result = auditoriaCompraService.save(auditoriaCompraDTO);
        return ResponseEntity.created(new URI("/api/auditoria-compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /auditoria-compras} : Updates an existing auditoriaCompra.
     *
     * @param auditoriaCompraDTO the auditoriaCompraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditoriaCompraDTO,
     * or with status {@code 400 (Bad Request)} if the auditoriaCompraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditoriaCompraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/auditoria-compras")
    public ResponseEntity<AuditoriaCompraDTO> updateAuditoriaCompra(@RequestBody AuditoriaCompraDTO auditoriaCompraDTO) throws URISyntaxException {
        log.debug("REST request to update AuditoriaCompra : {}", auditoriaCompraDTO);
        if (auditoriaCompraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AuditoriaCompraDTO result = auditoriaCompraService.save(auditoriaCompraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditoriaCompraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /auditoria-compras} : get all the auditoriaCompras.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditoriaCompras in body.
     */
    @GetMapping("/auditoria-compras")
    public ResponseEntity<List<AuditoriaCompraDTO>> getAllAuditoriaCompras(Pageable pageable) {
        log.debug("REST request to get a page of AuditoriaCompras");
        Page<AuditoriaCompraDTO> page = auditoriaCompraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /auditoria-compras/:id} : get the "id" auditoriaCompra.
     *
     * @param id the id of the auditoriaCompraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditoriaCompraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/auditoria-compras/{id}")
    public ResponseEntity<AuditoriaCompraDTO> getAuditoriaCompra(@PathVariable Long id) {
        log.debug("REST request to get AuditoriaCompra : {}", id);
        Optional<AuditoriaCompraDTO> auditoriaCompraDTO = auditoriaCompraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auditoriaCompraDTO);
    }

    /**
     * {@code DELETE  /auditoria-compras/:id} : delete the "id" auditoriaCompra.
     *
     * @param id the id of the auditoriaCompraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/auditoria-compras/{id}")
    public ResponseEntity<Void> deleteAuditoriaCompra(@PathVariable Long id) {
        log.debug("REST request to delete AuditoriaCompra : {}", id);
        auditoriaCompraService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
