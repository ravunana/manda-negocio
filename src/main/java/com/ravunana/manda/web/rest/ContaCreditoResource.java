package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.ContaCreditoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.ContaCreditoDTO;
import com.ravunana.manda.service.dto.ContaCreditoCriteria;
import com.ravunana.manda.service.ContaCreditoQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.ContaCredito}.
 */
@RestController
@RequestMapping("/api")
public class ContaCreditoResource {

    private final Logger log = LoggerFactory.getLogger(ContaCreditoResource.class);

    private static final String ENTITY_NAME = "contaCredito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContaCreditoService contaCreditoService;

    private final ContaCreditoQueryService contaCreditoQueryService;

    public ContaCreditoResource(ContaCreditoService contaCreditoService, ContaCreditoQueryService contaCreditoQueryService) {
        this.contaCreditoService = contaCreditoService;
        this.contaCreditoQueryService = contaCreditoQueryService;
    }

    /**
     * {@code POST  /conta-creditos} : Create a new contaCredito.
     *
     * @param contaCreditoDTO the contaCreditoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contaCreditoDTO, or with status {@code 400 (Bad Request)} if the contaCredito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conta-creditos")
    public ResponseEntity<ContaCreditoDTO> createContaCredito(@Valid @RequestBody ContaCreditoDTO contaCreditoDTO) throws URISyntaxException {
        log.debug("REST request to save ContaCredito : {}", contaCreditoDTO);
        if (contaCreditoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contaCredito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContaCreditoDTO result = contaCreditoService.save(contaCreditoDTO);
        return ResponseEntity.created(new URI("/api/conta-creditos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conta-creditos} : Updates an existing contaCredito.
     *
     * @param contaCreditoDTO the contaCreditoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contaCreditoDTO,
     * or with status {@code 400 (Bad Request)} if the contaCreditoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contaCreditoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conta-creditos")
    public ResponseEntity<ContaCreditoDTO> updateContaCredito(@Valid @RequestBody ContaCreditoDTO contaCreditoDTO) throws URISyntaxException {
        log.debug("REST request to update ContaCredito : {}", contaCreditoDTO);
        if (contaCreditoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContaCreditoDTO result = contaCreditoService.save(contaCreditoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contaCreditoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conta-creditos} : get all the contaCreditos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contaCreditos in body.
     */
    @GetMapping("/conta-creditos")
    public ResponseEntity<List<ContaCreditoDTO>> getAllContaCreditos(ContaCreditoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ContaCreditos by criteria: {}", criteria);
        Page<ContaCreditoDTO> page = contaCreditoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /conta-creditos/count} : count all the contaCreditos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/conta-creditos/count")
    public ResponseEntity<Long> countContaCreditos(ContaCreditoCriteria criteria) {
        log.debug("REST request to count ContaCreditos by criteria: {}", criteria);
        return ResponseEntity.ok().body(contaCreditoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /conta-creditos/:id} : get the "id" contaCredito.
     *
     * @param id the id of the contaCreditoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contaCreditoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conta-creditos/{id}")
    public ResponseEntity<ContaCreditoDTO> getContaCredito(@PathVariable Long id) {
        log.debug("REST request to get ContaCredito : {}", id);
        Optional<ContaCreditoDTO> contaCreditoDTO = contaCreditoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contaCreditoDTO);
    }

    /**
     * {@code DELETE  /conta-creditos/:id} : delete the "id" contaCredito.
     *
     * @param id the id of the contaCreditoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conta-creditos/{id}")
    public ResponseEntity<Void> deleteContaCredito(@PathVariable Long id) {
        log.debug("REST request to delete ContaCredito : {}", id);
        contaCreditoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
