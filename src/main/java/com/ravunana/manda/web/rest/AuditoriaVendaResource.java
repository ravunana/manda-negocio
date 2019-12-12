package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.AuditoriaVendaService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.AuditoriaVendaDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.AuditoriaVenda}.
 */
@RestController
@RequestMapping("/api")
public class AuditoriaVendaResource {

    private final Logger log = LoggerFactory.getLogger(AuditoriaVendaResource.class);

    private static final String ENTITY_NAME = "auditoriaVenda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditoriaVendaService auditoriaVendaService;

    public AuditoriaVendaResource(AuditoriaVendaService auditoriaVendaService) {
        this.auditoriaVendaService = auditoriaVendaService;
    }

    /**
     * {@code POST  /auditoria-vendas} : Create a new auditoriaVenda.
     *
     * @param auditoriaVendaDTO the auditoriaVendaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditoriaVendaDTO, or with status {@code 400 (Bad Request)} if the auditoriaVenda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/auditoria-vendas")
    public ResponseEntity<AuditoriaVendaDTO> createAuditoriaVenda(@Valid @RequestBody AuditoriaVendaDTO auditoriaVendaDTO) throws URISyntaxException {
        log.debug("REST request to save AuditoriaVenda : {}", auditoriaVendaDTO);
        if (auditoriaVendaDTO.getId() != null) {
            throw new BadRequestAlertException("A new auditoriaVenda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuditoriaVendaDTO result = auditoriaVendaService.save(auditoriaVendaDTO);
        return ResponseEntity.created(new URI("/api/auditoria-vendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /auditoria-vendas} : Updates an existing auditoriaVenda.
     *
     * @param auditoriaVendaDTO the auditoriaVendaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditoriaVendaDTO,
     * or with status {@code 400 (Bad Request)} if the auditoriaVendaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditoriaVendaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/auditoria-vendas")
    public ResponseEntity<AuditoriaVendaDTO> updateAuditoriaVenda(@Valid @RequestBody AuditoriaVendaDTO auditoriaVendaDTO) throws URISyntaxException {
        log.debug("REST request to update AuditoriaVenda : {}", auditoriaVendaDTO);
        if (auditoriaVendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AuditoriaVendaDTO result = auditoriaVendaService.save(auditoriaVendaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditoriaVendaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /auditoria-vendas} : get all the auditoriaVendas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditoriaVendas in body.
     */
    @GetMapping("/auditoria-vendas")
    public ResponseEntity<List<AuditoriaVendaDTO>> getAllAuditoriaVendas(Pageable pageable) {
        log.debug("REST request to get a page of AuditoriaVendas");
        Page<AuditoriaVendaDTO> page = auditoriaVendaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /auditoria-vendas/:id} : get the "id" auditoriaVenda.
     *
     * @param id the id of the auditoriaVendaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditoriaVendaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/auditoria-vendas/{id}")
    public ResponseEntity<AuditoriaVendaDTO> getAuditoriaVenda(@PathVariable Long id) {
        log.debug("REST request to get AuditoriaVenda : {}", id);
        Optional<AuditoriaVendaDTO> auditoriaVendaDTO = auditoriaVendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auditoriaVendaDTO);
    }

    /**
     * {@code DELETE  /auditoria-vendas/:id} : delete the "id" auditoriaVenda.
     *
     * @param id the id of the auditoriaVendaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/auditoria-vendas/{id}")
    public ResponseEntity<Void> deleteAuditoriaVenda(@PathVariable Long id) {
        log.debug("REST request to delete AuditoriaVenda : {}", id);
        auditoriaVendaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
