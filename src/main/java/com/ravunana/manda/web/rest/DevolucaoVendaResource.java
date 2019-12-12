package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.DevolucaoVendaService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.DevolucaoVendaDTO;
import com.ravunana.manda.service.dto.DevolucaoVendaCriteria;
import com.ravunana.manda.service.DevolucaoVendaQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.DevolucaoVenda}.
 */
@RestController
@RequestMapping("/api")
public class DevolucaoVendaResource {

    private final Logger log = LoggerFactory.getLogger(DevolucaoVendaResource.class);

    private static final String ENTITY_NAME = "devolucaoVenda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DevolucaoVendaService devolucaoVendaService;

    private final DevolucaoVendaQueryService devolucaoVendaQueryService;

    public DevolucaoVendaResource(DevolucaoVendaService devolucaoVendaService, DevolucaoVendaQueryService devolucaoVendaQueryService) {
        this.devolucaoVendaService = devolucaoVendaService;
        this.devolucaoVendaQueryService = devolucaoVendaQueryService;
    }

    /**
     * {@code POST  /devolucao-vendas} : Create a new devolucaoVenda.
     *
     * @param devolucaoVendaDTO the devolucaoVendaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new devolucaoVendaDTO, or with status {@code 400 (Bad Request)} if the devolucaoVenda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/devolucao-vendas")
    public ResponseEntity<DevolucaoVendaDTO> createDevolucaoVenda(@Valid @RequestBody DevolucaoVendaDTO devolucaoVendaDTO) throws URISyntaxException {
        log.debug("REST request to save DevolucaoVenda : {}", devolucaoVendaDTO);
        if (devolucaoVendaDTO.getId() != null) {
            throw new BadRequestAlertException("A new devolucaoVenda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DevolucaoVendaDTO result = devolucaoVendaService.save(devolucaoVendaDTO);
        return ResponseEntity.created(new URI("/api/devolucao-vendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /devolucao-vendas} : Updates an existing devolucaoVenda.
     *
     * @param devolucaoVendaDTO the devolucaoVendaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated devolucaoVendaDTO,
     * or with status {@code 400 (Bad Request)} if the devolucaoVendaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the devolucaoVendaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/devolucao-vendas")
    public ResponseEntity<DevolucaoVendaDTO> updateDevolucaoVenda(@Valid @RequestBody DevolucaoVendaDTO devolucaoVendaDTO) throws URISyntaxException {
        log.debug("REST request to update DevolucaoVenda : {}", devolucaoVendaDTO);
        if (devolucaoVendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DevolucaoVendaDTO result = devolucaoVendaService.save(devolucaoVendaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, devolucaoVendaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /devolucao-vendas} : get all the devolucaoVendas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of devolucaoVendas in body.
     */
    @GetMapping("/devolucao-vendas")
    public ResponseEntity<List<DevolucaoVendaDTO>> getAllDevolucaoVendas(DevolucaoVendaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DevolucaoVendas by criteria: {}", criteria);
        Page<DevolucaoVendaDTO> page = devolucaoVendaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /devolucao-vendas/count} : count all the devolucaoVendas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/devolucao-vendas/count")
    public ResponseEntity<Long> countDevolucaoVendas(DevolucaoVendaCriteria criteria) {
        log.debug("REST request to count DevolucaoVendas by criteria: {}", criteria);
        return ResponseEntity.ok().body(devolucaoVendaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /devolucao-vendas/:id} : get the "id" devolucaoVenda.
     *
     * @param id the id of the devolucaoVendaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the devolucaoVendaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/devolucao-vendas/{id}")
    public ResponseEntity<DevolucaoVendaDTO> getDevolucaoVenda(@PathVariable Long id) {
        log.debug("REST request to get DevolucaoVenda : {}", id);
        Optional<DevolucaoVendaDTO> devolucaoVendaDTO = devolucaoVendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(devolucaoVendaDTO);
    }

    /**
     * {@code DELETE  /devolucao-vendas/:id} : delete the "id" devolucaoVenda.
     *
     * @param id the id of the devolucaoVendaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/devolucao-vendas/{id}")
    public ResponseEntity<Void> deleteDevolucaoVenda(@PathVariable Long id) {
        log.debug("REST request to delete DevolucaoVenda : {}", id);
        devolucaoVendaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
