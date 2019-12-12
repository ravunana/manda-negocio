package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.DetalheAduaneiroService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.DetalheAduaneiroDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.DetalheAduaneiro}.
 */
@RestController
@RequestMapping("/api")
public class DetalheAduaneiroResource {

    private final Logger log = LoggerFactory.getLogger(DetalheAduaneiroResource.class);

    private static final String ENTITY_NAME = "detalheAduaneiro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetalheAduaneiroService detalheAduaneiroService;

    public DetalheAduaneiroResource(DetalheAduaneiroService detalheAduaneiroService) {
        this.detalheAduaneiroService = detalheAduaneiroService;
    }

    /**
     * {@code POST  /detalhe-aduaneiros} : Create a new detalheAduaneiro.
     *
     * @param detalheAduaneiroDTO the detalheAduaneiroDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalheAduaneiroDTO, or with status {@code 400 (Bad Request)} if the detalheAduaneiro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalhe-aduaneiros")
    public ResponseEntity<DetalheAduaneiroDTO> createDetalheAduaneiro(@Valid @RequestBody DetalheAduaneiroDTO detalheAduaneiroDTO) throws URISyntaxException {
        log.debug("REST request to save DetalheAduaneiro : {}", detalheAduaneiroDTO);
        if (detalheAduaneiroDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalheAduaneiro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalheAduaneiroDTO result = detalheAduaneiroService.save(detalheAduaneiroDTO);
        return ResponseEntity.created(new URI("/api/detalhe-aduaneiros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalhe-aduaneiros} : Updates an existing detalheAduaneiro.
     *
     * @param detalheAduaneiroDTO the detalheAduaneiroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalheAduaneiroDTO,
     * or with status {@code 400 (Bad Request)} if the detalheAduaneiroDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detalheAduaneiroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalhe-aduaneiros")
    public ResponseEntity<DetalheAduaneiroDTO> updateDetalheAduaneiro(@Valid @RequestBody DetalheAduaneiroDTO detalheAduaneiroDTO) throws URISyntaxException {
        log.debug("REST request to update DetalheAduaneiro : {}", detalheAduaneiroDTO);
        if (detalheAduaneiroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetalheAduaneiroDTO result = detalheAduaneiroService.save(detalheAduaneiroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalheAduaneiroDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /detalhe-aduaneiros} : get all the detalheAduaneiros.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalheAduaneiros in body.
     */
    @GetMapping("/detalhe-aduaneiros")
    public ResponseEntity<List<DetalheAduaneiroDTO>> getAllDetalheAduaneiros(Pageable pageable) {
        log.debug("REST request to get a page of DetalheAduaneiros");
        Page<DetalheAduaneiroDTO> page = detalheAduaneiroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detalhe-aduaneiros/:id} : get the "id" detalheAduaneiro.
     *
     * @param id the id of the detalheAduaneiroDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalheAduaneiroDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalhe-aduaneiros/{id}")
    public ResponseEntity<DetalheAduaneiroDTO> getDetalheAduaneiro(@PathVariable Long id) {
        log.debug("REST request to get DetalheAduaneiro : {}", id);
        Optional<DetalheAduaneiroDTO> detalheAduaneiroDTO = detalheAduaneiroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalheAduaneiroDTO);
    }

    /**
     * {@code DELETE  /detalhe-aduaneiros/:id} : delete the "id" detalheAduaneiro.
     *
     * @param id the id of the detalheAduaneiroDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalhe-aduaneiros/{id}")
    public ResponseEntity<Void> deleteDetalheAduaneiro(@PathVariable Long id) {
        log.debug("REST request to delete DetalheAduaneiro : {}", id);
        detalheAduaneiroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
