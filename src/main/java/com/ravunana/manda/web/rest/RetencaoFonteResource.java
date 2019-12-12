package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.RetencaoFonteService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.RetencaoFonteDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.RetencaoFonte}.
 */
@RestController
@RequestMapping("/api")
public class RetencaoFonteResource {

    private final Logger log = LoggerFactory.getLogger(RetencaoFonteResource.class);

    private static final String ENTITY_NAME = "retencaoFonte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetencaoFonteService retencaoFonteService;

    public RetencaoFonteResource(RetencaoFonteService retencaoFonteService) {
        this.retencaoFonteService = retencaoFonteService;
    }

    /**
     * {@code POST  /retencao-fontes} : Create a new retencaoFonte.
     *
     * @param retencaoFonteDTO the retencaoFonteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new retencaoFonteDTO, or with status {@code 400 (Bad Request)} if the retencaoFonte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/retencao-fontes")
    public ResponseEntity<RetencaoFonteDTO> createRetencaoFonte(@RequestBody RetencaoFonteDTO retencaoFonteDTO) throws URISyntaxException {
        log.debug("REST request to save RetencaoFonte : {}", retencaoFonteDTO);
        if (retencaoFonteDTO.getId() != null) {
            throw new BadRequestAlertException("A new retencaoFonte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RetencaoFonteDTO result = retencaoFonteService.save(retencaoFonteDTO);
        return ResponseEntity.created(new URI("/api/retencao-fontes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /retencao-fontes} : Updates an existing retencaoFonte.
     *
     * @param retencaoFonteDTO the retencaoFonteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated retencaoFonteDTO,
     * or with status {@code 400 (Bad Request)} if the retencaoFonteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the retencaoFonteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/retencao-fontes")
    public ResponseEntity<RetencaoFonteDTO> updateRetencaoFonte(@RequestBody RetencaoFonteDTO retencaoFonteDTO) throws URISyntaxException {
        log.debug("REST request to update RetencaoFonte : {}", retencaoFonteDTO);
        if (retencaoFonteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RetencaoFonteDTO result = retencaoFonteService.save(retencaoFonteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, retencaoFonteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /retencao-fontes} : get all the retencaoFontes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of retencaoFontes in body.
     */
    @GetMapping("/retencao-fontes")
    public ResponseEntity<List<RetencaoFonteDTO>> getAllRetencaoFontes(Pageable pageable) {
        log.debug("REST request to get a page of RetencaoFontes");
        Page<RetencaoFonteDTO> page = retencaoFonteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /retencao-fontes/:id} : get the "id" retencaoFonte.
     *
     * @param id the id of the retencaoFonteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the retencaoFonteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/retencao-fontes/{id}")
    public ResponseEntity<RetencaoFonteDTO> getRetencaoFonte(@PathVariable Long id) {
        log.debug("REST request to get RetencaoFonte : {}", id);
        Optional<RetencaoFonteDTO> retencaoFonteDTO = retencaoFonteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(retencaoFonteDTO);
    }

    /**
     * {@code DELETE  /retencao-fontes/:id} : delete the "id" retencaoFonte.
     *
     * @param id the id of the retencaoFonteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/retencao-fontes/{id}")
    public ResponseEntity<Void> deleteRetencaoFonte(@PathVariable Long id) {
        log.debug("REST request to delete RetencaoFonte : {}", id);
        retencaoFonteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
