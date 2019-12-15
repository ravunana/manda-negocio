package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.ContaService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.ContaDTO;
import com.ravunana.manda.service.dto.ContaCriteria;
import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.service.ContaQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.Conta}.
 */
@RestController
@RequestMapping("/api")
public class ContaResource {

    private final Logger log = LoggerFactory.getLogger(ContaResource.class);

    private static final String ENTITY_NAME = "conta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContaService contaService;

    private final ContaQueryService contaQueryService;

    public ContaResource(ContaService contaService, ContaQueryService contaQueryService) {
        this.contaService = contaService;
        this.contaQueryService = contaQueryService;
    }

    /**
     * {@code POST  /contas} : Create a new conta.
     *
     * @param contaDTO the contaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contaDTO, or with status {@code 400 (Bad Request)} if the conta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contas")
    public ResponseEntity<ContaDTO> createConta(@Valid @RequestBody ContaDTO contaDTO) throws URISyntaxException {
        log.debug("REST request to save Conta : {}", contaDTO);
        if (contaDTO.getId() != null) {
            throw new BadRequestAlertException("A new conta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContaDTO result = contaService.save(contaDTO);
        return ResponseEntity.created(new URI("/api/contas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contas} : Updates an existing conta.
     *
     * @param contaDTO the contaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contaDTO,
     * or with status {@code 400 (Bad Request)} if the contaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contas")
    public ResponseEntity<ContaDTO> updateConta(@Valid @RequestBody ContaDTO contaDTO) throws URISyntaxException {
        log.debug("REST request to update Conta : {}", contaDTO);
        if (contaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContaDTO result = contaService.save(contaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contas} : get all the contas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contas in body.
     */
    @GetMapping("/contas")
    public ResponseEntity<List<ContaDTO>> getAllContas(ContaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Contas by criteria: {}", criteria);
        Page<ContaDTO> page = contaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /contas/count} : count all the contas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/contas/count")
    public ResponseEntity<Long> countContas(ContaCriteria criteria) {
        log.debug("REST request to count Contas by criteria: {}", criteria);
        return ResponseEntity.ok().body(contaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contas/:id} : get the "id" conta.
     *
     * @param id the id of the contaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contas/{id}")
    public ResponseEntity<ContaDTO> getConta(@PathVariable Long id) {
        log.debug("REST request to get Conta : {}", id);
        Optional<ContaDTO> contaDTO = contaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contaDTO);
    }

    /**
     * {@code DELETE  /contas/:id} : delete the "id" conta.
     *
     * @param id the id of the contaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contas/{id}")
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        log.debug("REST request to delete Conta : {}", id);
        contaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/contas/add-subconta/{contaAgregadoraId}/{descricao}")
    public Conta addSubConta( @PathVariable Long contaAgregadoraId, @PathVariable String descricao ) {
        return contaService.addSubConta(contaAgregadoraId, descricao);
    }
}
