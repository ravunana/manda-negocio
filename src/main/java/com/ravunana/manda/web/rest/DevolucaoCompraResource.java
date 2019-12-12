package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.DevolucaoCompraService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.DevolucaoCompraDTO;
import com.ravunana.manda.service.dto.DevolucaoCompraCriteria;
import com.ravunana.manda.service.DevolucaoCompraQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.DevolucaoCompra}.
 */
@RestController
@RequestMapping("/api")
public class DevolucaoCompraResource {

    private final Logger log = LoggerFactory.getLogger(DevolucaoCompraResource.class);

    private static final String ENTITY_NAME = "devolucaoCompra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DevolucaoCompraService devolucaoCompraService;

    private final DevolucaoCompraQueryService devolucaoCompraQueryService;

    public DevolucaoCompraResource(DevolucaoCompraService devolucaoCompraService, DevolucaoCompraQueryService devolucaoCompraQueryService) {
        this.devolucaoCompraService = devolucaoCompraService;
        this.devolucaoCompraQueryService = devolucaoCompraQueryService;
    }

    /**
     * {@code POST  /devolucao-compras} : Create a new devolucaoCompra.
     *
     * @param devolucaoCompraDTO the devolucaoCompraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new devolucaoCompraDTO, or with status {@code 400 (Bad Request)} if the devolucaoCompra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/devolucao-compras")
    public ResponseEntity<DevolucaoCompraDTO> createDevolucaoCompra(@Valid @RequestBody DevolucaoCompraDTO devolucaoCompraDTO) throws URISyntaxException {
        log.debug("REST request to save DevolucaoCompra : {}", devolucaoCompraDTO);
        if (devolucaoCompraDTO.getId() != null) {
            throw new BadRequestAlertException("A new devolucaoCompra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DevolucaoCompraDTO result = devolucaoCompraService.save(devolucaoCompraDTO);
        return ResponseEntity.created(new URI("/api/devolucao-compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /devolucao-compras} : Updates an existing devolucaoCompra.
     *
     * @param devolucaoCompraDTO the devolucaoCompraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated devolucaoCompraDTO,
     * or with status {@code 400 (Bad Request)} if the devolucaoCompraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the devolucaoCompraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/devolucao-compras")
    public ResponseEntity<DevolucaoCompraDTO> updateDevolucaoCompra(@Valid @RequestBody DevolucaoCompraDTO devolucaoCompraDTO) throws URISyntaxException {
        log.debug("REST request to update DevolucaoCompra : {}", devolucaoCompraDTO);
        if (devolucaoCompraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DevolucaoCompraDTO result = devolucaoCompraService.save(devolucaoCompraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, devolucaoCompraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /devolucao-compras} : get all the devolucaoCompras.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of devolucaoCompras in body.
     */
    @GetMapping("/devolucao-compras")
    public ResponseEntity<List<DevolucaoCompraDTO>> getAllDevolucaoCompras(DevolucaoCompraCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DevolucaoCompras by criteria: {}", criteria);
        Page<DevolucaoCompraDTO> page = devolucaoCompraQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /devolucao-compras/count} : count all the devolucaoCompras.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/devolucao-compras/count")
    public ResponseEntity<Long> countDevolucaoCompras(DevolucaoCompraCriteria criteria) {
        log.debug("REST request to count DevolucaoCompras by criteria: {}", criteria);
        return ResponseEntity.ok().body(devolucaoCompraQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /devolucao-compras/:id} : get the "id" devolucaoCompra.
     *
     * @param id the id of the devolucaoCompraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the devolucaoCompraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/devolucao-compras/{id}")
    public ResponseEntity<DevolucaoCompraDTO> getDevolucaoCompra(@PathVariable Long id) {
        log.debug("REST request to get DevolucaoCompra : {}", id);
        Optional<DevolucaoCompraDTO> devolucaoCompraDTO = devolucaoCompraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(devolucaoCompraDTO);
    }

    /**
     * {@code DELETE  /devolucao-compras/:id} : delete the "id" devolucaoCompra.
     *
     * @param id the id of the devolucaoCompraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/devolucao-compras/{id}")
    public ResponseEntity<Void> deleteDevolucaoCompra(@PathVariable Long id) {
        log.debug("REST request to delete DevolucaoCompra : {}", id);
        devolucaoCompraService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
