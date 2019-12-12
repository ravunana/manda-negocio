package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.ImpostoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.ImpostoDTO;
import com.ravunana.manda.service.dto.ImpostoCriteria;
import com.ravunana.manda.service.ImpostoQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.Imposto}.
 */
@RestController
@RequestMapping("/api")
public class ImpostoResource {

    private final Logger log = LoggerFactory.getLogger(ImpostoResource.class);

    private static final String ENTITY_NAME = "imposto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImpostoService impostoService;

    private final ImpostoQueryService impostoQueryService;

    public ImpostoResource(ImpostoService impostoService, ImpostoQueryService impostoQueryService) {
        this.impostoService = impostoService;
        this.impostoQueryService = impostoQueryService;
    }

    /**
     * {@code POST  /impostos} : Create a new imposto.
     *
     * @param impostoDTO the impostoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new impostoDTO, or with status {@code 400 (Bad Request)} if the imposto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/impostos")
    public ResponseEntity<ImpostoDTO> createImposto(@Valid @RequestBody ImpostoDTO impostoDTO) throws URISyntaxException {
        log.debug("REST request to save Imposto : {}", impostoDTO);
        if (impostoDTO.getId() != null) {
            throw new BadRequestAlertException("A new imposto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImpostoDTO result = impostoService.save(impostoDTO);
        return ResponseEntity.created(new URI("/api/impostos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /impostos} : Updates an existing imposto.
     *
     * @param impostoDTO the impostoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impostoDTO,
     * or with status {@code 400 (Bad Request)} if the impostoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the impostoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/impostos")
    public ResponseEntity<ImpostoDTO> updateImposto(@Valid @RequestBody ImpostoDTO impostoDTO) throws URISyntaxException {
        log.debug("REST request to update Imposto : {}", impostoDTO);
        if (impostoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImpostoDTO result = impostoService.save(impostoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impostoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /impostos} : get all the impostos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of impostos in body.
     */
    @GetMapping("/impostos")
    public ResponseEntity<List<ImpostoDTO>> getAllImpostos(ImpostoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Impostos by criteria: {}", criteria);
        Page<ImpostoDTO> page = impostoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /impostos/count} : count all the impostos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/impostos/count")
    public ResponseEntity<Long> countImpostos(ImpostoCriteria criteria) {
        log.debug("REST request to count Impostos by criteria: {}", criteria);
        return ResponseEntity.ok().body(impostoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /impostos/:id} : get the "id" imposto.
     *
     * @param id the id of the impostoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the impostoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/impostos/{id}")
    public ResponseEntity<ImpostoDTO> getImposto(@PathVariable Long id) {
        log.debug("REST request to get Imposto : {}", id);
        Optional<ImpostoDTO> impostoDTO = impostoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(impostoDTO);
    }

    /**
     * {@code DELETE  /impostos/:id} : delete the "id" imposto.
     *
     * @param id the id of the impostoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/impostos/{id}")
    public ResponseEntity<Void> deleteImposto(@PathVariable Long id) {
        log.debug("REST request to delete Imposto : {}", id);
        impostoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
