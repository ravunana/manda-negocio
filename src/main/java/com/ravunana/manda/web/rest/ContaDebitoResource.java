package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.ContaDebitoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.ContaDebitoDTO;
import com.ravunana.manda.service.dto.ContaDebitoCriteria;
import com.ravunana.manda.service.ContaDebitoQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.ContaDebito}.
 */
@RestController
@RequestMapping("/api")
public class ContaDebitoResource {

    private final Logger log = LoggerFactory.getLogger(ContaDebitoResource.class);

    private static final String ENTITY_NAME = "contaDebito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContaDebitoService contaDebitoService;

    private final ContaDebitoQueryService contaDebitoQueryService;

    public ContaDebitoResource(ContaDebitoService contaDebitoService, ContaDebitoQueryService contaDebitoQueryService) {
        this.contaDebitoService = contaDebitoService;
        this.contaDebitoQueryService = contaDebitoQueryService;
    }

    /**
     * {@code POST  /conta-debitos} : Create a new contaDebito.
     *
     * @param contaDebitoDTO the contaDebitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contaDebitoDTO, or with status {@code 400 (Bad Request)} if the contaDebito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conta-debitos")
    public ResponseEntity<ContaDebitoDTO> createContaDebito(@Valid @RequestBody ContaDebitoDTO contaDebitoDTO) throws URISyntaxException {
        log.debug("REST request to save ContaDebito : {}", contaDebitoDTO);
        if (contaDebitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contaDebito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContaDebitoDTO result = contaDebitoService.save(contaDebitoDTO);
        return ResponseEntity.created(new URI("/api/conta-debitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conta-debitos} : Updates an existing contaDebito.
     *
     * @param contaDebitoDTO the contaDebitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contaDebitoDTO,
     * or with status {@code 400 (Bad Request)} if the contaDebitoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contaDebitoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conta-debitos")
    public ResponseEntity<ContaDebitoDTO> updateContaDebito(@Valid @RequestBody ContaDebitoDTO contaDebitoDTO) throws URISyntaxException {
        log.debug("REST request to update ContaDebito : {}", contaDebitoDTO);
        if (contaDebitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContaDebitoDTO result = contaDebitoService.save(contaDebitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contaDebitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conta-debitos} : get all the contaDebitos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contaDebitos in body.
     */
    @GetMapping("/conta-debitos")
    public ResponseEntity<List<ContaDebitoDTO>> getAllContaDebitos(ContaDebitoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ContaDebitos by criteria: {}", criteria);
        Page<ContaDebitoDTO> page = contaDebitoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /conta-debitos/count} : count all the contaDebitos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/conta-debitos/count")
    public ResponseEntity<Long> countContaDebitos(ContaDebitoCriteria criteria) {
        log.debug("REST request to count ContaDebitos by criteria: {}", criteria);
        return ResponseEntity.ok().body(contaDebitoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /conta-debitos/:id} : get the "id" contaDebito.
     *
     * @param id the id of the contaDebitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contaDebitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conta-debitos/{id}")
    public ResponseEntity<ContaDebitoDTO> getContaDebito(@PathVariable Long id) {
        log.debug("REST request to get ContaDebito : {}", id);
        Optional<ContaDebitoDTO> contaDebitoDTO = contaDebitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contaDebitoDTO);
    }

    /**
     * {@code DELETE  /conta-debitos/:id} : delete the "id" contaDebito.
     *
     * @param id the id of the contaDebitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conta-debitos/{id}")
    public ResponseEntity<Void> deleteContaDebito(@PathVariable Long id) {
        log.debug("REST request to delete ContaDebito : {}", id);
        contaDebitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
