package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.MoedaService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.MoedaDTO;
import com.ravunana.manda.service.dto.MoedaCriteria;
import com.ravunana.manda.service.MoedaQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.Moeda}.
 */
@RestController
@RequestMapping("/api")
public class MoedaResource {

    private final Logger log = LoggerFactory.getLogger(MoedaResource.class);

    private static final String ENTITY_NAME = "moeda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoedaService moedaService;

    private final MoedaQueryService moedaQueryService;

    public MoedaResource(MoedaService moedaService, MoedaQueryService moedaQueryService) {
        this.moedaService = moedaService;
        this.moedaQueryService = moedaQueryService;
    }

    /**
     * {@code POST  /moedas} : Create a new moeda.
     *
     * @param moedaDTO the moedaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moedaDTO, or with status {@code 400 (Bad Request)} if the moeda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/moedas")
    public ResponseEntity<MoedaDTO> createMoeda(@Valid @RequestBody MoedaDTO moedaDTO) throws URISyntaxException {
        log.debug("REST request to save Moeda : {}", moedaDTO);
        if (moedaDTO.getId() != null) {
            throw new BadRequestAlertException("A new moeda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoedaDTO result = moedaService.save(moedaDTO);
        return ResponseEntity.created(new URI("/api/moedas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /moedas} : Updates an existing moeda.
     *
     * @param moedaDTO the moedaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moedaDTO,
     * or with status {@code 400 (Bad Request)} if the moedaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moedaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/moedas")
    public ResponseEntity<MoedaDTO> updateMoeda(@Valid @RequestBody MoedaDTO moedaDTO) throws URISyntaxException {
        log.debug("REST request to update Moeda : {}", moedaDTO);
        if (moedaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MoedaDTO result = moedaService.save(moedaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moedaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /moedas} : get all the moedas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moedas in body.
     */
    @GetMapping("/moedas")
    public ResponseEntity<List<MoedaDTO>> getAllMoedas(MoedaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Moedas by criteria: {}", criteria);
        Page<MoedaDTO> page = moedaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /moedas/count} : count all the moedas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/moedas/count")
    public ResponseEntity<Long> countMoedas(MoedaCriteria criteria) {
        log.debug("REST request to count Moedas by criteria: {}", criteria);
        return ResponseEntity.ok().body(moedaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /moedas/:id} : get the "id" moeda.
     *
     * @param id the id of the moedaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moedaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/moedas/{id}")
    public ResponseEntity<MoedaDTO> getMoeda(@PathVariable Long id) {
        log.debug("REST request to get Moeda : {}", id);
        Optional<MoedaDTO> moedaDTO = moedaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moedaDTO);
    }

    /**
     * {@code DELETE  /moedas/:id} : delete the "id" moeda.
     *
     * @param id the id of the moedaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/moedas/{id}")
    public ResponseEntity<Void> deleteMoeda(@PathVariable Long id) {
        log.debug("REST request to delete Moeda : {}", id);
        moedaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping( "/moedas/nacional" )
    public String getMoedaNacional() {
        String moedaNacional = this.moedaService.getMoedaNacional();
        log.debug("REST request to get moeda nacional : {}", moedaNacional);
        return moedaNacional;
    }
}
