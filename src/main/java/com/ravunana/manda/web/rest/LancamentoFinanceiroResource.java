package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.LancamentoFinanceiroService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.LancamentoFinanceiroDTO;
import com.ravunana.manda.service.dto.LancamentoFinanceiroCriteria;
import com.ravunana.manda.service.LancamentoFinanceiroQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.LancamentoFinanceiro}.
 */
@RestController
@RequestMapping("/api")
public class LancamentoFinanceiroResource {

    private final Logger log = LoggerFactory.getLogger(LancamentoFinanceiroResource.class);

    private static final String ENTITY_NAME = "lancamentoFinanceiro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LancamentoFinanceiroService lancamentoFinanceiroService;

    private final LancamentoFinanceiroQueryService lancamentoFinanceiroQueryService;

    public LancamentoFinanceiroResource(LancamentoFinanceiroService lancamentoFinanceiroService, LancamentoFinanceiroQueryService lancamentoFinanceiroQueryService) {
        this.lancamentoFinanceiroService = lancamentoFinanceiroService;
        this.lancamentoFinanceiroQueryService = lancamentoFinanceiroQueryService;
    }

    /**
     * {@code POST  /lancamento-financeiros} : Create a new lancamentoFinanceiro.
     *
     * @param lancamentoFinanceiroDTO the lancamentoFinanceiroDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lancamentoFinanceiroDTO, or with status {@code 400 (Bad Request)} if the lancamentoFinanceiro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lancamento-financeiros")
    public ResponseEntity<LancamentoFinanceiroDTO> createLancamentoFinanceiro(@Valid @RequestBody LancamentoFinanceiroDTO lancamentoFinanceiroDTO) throws URISyntaxException {
        log.debug("REST request to save LancamentoFinanceiro : {}", lancamentoFinanceiroDTO);
        if (lancamentoFinanceiroDTO.getId() != null) {
            throw new BadRequestAlertException("A new lancamentoFinanceiro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LancamentoFinanceiroDTO result = lancamentoFinanceiroService.save(lancamentoFinanceiroDTO);
        return ResponseEntity.created(new URI("/api/lancamento-financeiros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lancamento-financeiros} : Updates an existing lancamentoFinanceiro.
     *
     * @param lancamentoFinanceiroDTO the lancamentoFinanceiroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lancamentoFinanceiroDTO,
     * or with status {@code 400 (Bad Request)} if the lancamentoFinanceiroDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lancamentoFinanceiroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lancamento-financeiros")
    public ResponseEntity<LancamentoFinanceiroDTO> updateLancamentoFinanceiro(@Valid @RequestBody LancamentoFinanceiroDTO lancamentoFinanceiroDTO) throws URISyntaxException {
        log.debug("REST request to update LancamentoFinanceiro : {}", lancamentoFinanceiroDTO);
        if (lancamentoFinanceiroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LancamentoFinanceiroDTO result = lancamentoFinanceiroService.save(lancamentoFinanceiroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lancamentoFinanceiroDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lancamento-financeiros} : get all the lancamentoFinanceiros.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lancamentoFinanceiros in body.
     */
    @GetMapping("/lancamento-financeiros")
    public ResponseEntity<List<LancamentoFinanceiroDTO>> getAllLancamentoFinanceiros(LancamentoFinanceiroCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LancamentoFinanceiros by criteria: {}", criteria);
        Page<LancamentoFinanceiroDTO> page = lancamentoFinanceiroQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /lancamento-financeiros/count} : count all the lancamentoFinanceiros.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/lancamento-financeiros/count")
    public ResponseEntity<Long> countLancamentoFinanceiros(LancamentoFinanceiroCriteria criteria) {
        log.debug("REST request to count LancamentoFinanceiros by criteria: {}", criteria);
        return ResponseEntity.ok().body(lancamentoFinanceiroQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /lancamento-financeiros/:id} : get the "id" lancamentoFinanceiro.
     *
     * @param id the id of the lancamentoFinanceiroDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lancamentoFinanceiroDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lancamento-financeiros/{id}")
    public ResponseEntity<LancamentoFinanceiroDTO> getLancamentoFinanceiro(@PathVariable Long id) {
        log.debug("REST request to get LancamentoFinanceiro : {}", id);
        Optional<LancamentoFinanceiroDTO> lancamentoFinanceiroDTO = lancamentoFinanceiroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lancamentoFinanceiroDTO);
    }

    /**
     * {@code DELETE  /lancamento-financeiros/:id} : delete the "id" lancamentoFinanceiro.
     *
     * @param id the id of the lancamentoFinanceiroDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lancamento-financeiros/{id}")
    public ResponseEntity<Void> deleteLancamentoFinanceiro(@PathVariable Long id) {
        log.debug("REST request to delete LancamentoFinanceiro : {}", id);
        lancamentoFinanceiroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
