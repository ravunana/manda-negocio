package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.DetalheLancamentoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.DetalheLancamentoDTO;
import com.ravunana.manda.service.dto.DetalheLancamentoCriteria;
import com.ravunana.manda.service.DetalheLancamentoQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.DetalheLancamento}.
 */
@RestController
@RequestMapping("/api")
public class DetalheLancamentoResource {

    private final Logger log = LoggerFactory.getLogger(DetalheLancamentoResource.class);

    private static final String ENTITY_NAME = "detalheLancamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetalheLancamentoService detalheLancamentoService;

    private final DetalheLancamentoQueryService detalheLancamentoQueryService;

    public DetalheLancamentoResource(DetalheLancamentoService detalheLancamentoService, DetalheLancamentoQueryService detalheLancamentoQueryService) {
        this.detalheLancamentoService = detalheLancamentoService;
        this.detalheLancamentoQueryService = detalheLancamentoQueryService;
    }

    /**
     * {@code POST  /detalhe-lancamentos} : Create a new detalheLancamento.
     *
     * @param detalheLancamentoDTO the detalheLancamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalheLancamentoDTO, or with status {@code 400 (Bad Request)} if the detalheLancamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalhe-lancamentos")
    public ResponseEntity<DetalheLancamentoDTO> createDetalheLancamento(@Valid @RequestBody DetalheLancamentoDTO detalheLancamentoDTO) throws URISyntaxException {
        log.debug("REST request to save DetalheLancamento : {}", detalheLancamentoDTO);
        if (detalheLancamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalheLancamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalheLancamentoDTO result = detalheLancamentoService.save(detalheLancamentoDTO);
        return ResponseEntity.created(new URI("/api/detalhe-lancamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalhe-lancamentos} : Updates an existing detalheLancamento.
     *
     * @param detalheLancamentoDTO the detalheLancamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalheLancamentoDTO,
     * or with status {@code 400 (Bad Request)} if the detalheLancamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detalheLancamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalhe-lancamentos")
    public ResponseEntity<DetalheLancamentoDTO> updateDetalheLancamento(@Valid @RequestBody DetalheLancamentoDTO detalheLancamentoDTO) throws URISyntaxException {
        log.debug("REST request to update DetalheLancamento : {}", detalheLancamentoDTO);
        if (detalheLancamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetalheLancamentoDTO result = detalheLancamentoService.save(detalheLancamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalheLancamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /detalhe-lancamentos} : get all the detalheLancamentos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalheLancamentos in body.
     */
    @GetMapping("/detalhe-lancamentos")
    public ResponseEntity<List<DetalheLancamentoDTO>> getAllDetalheLancamentos(DetalheLancamentoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DetalheLancamentos by criteria: {}", criteria);
        Page<DetalheLancamentoDTO> page = detalheLancamentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /detalhe-lancamentos/count} : count all the detalheLancamentos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/detalhe-lancamentos/count")
    public ResponseEntity<Long> countDetalheLancamentos(DetalheLancamentoCriteria criteria) {
        log.debug("REST request to count DetalheLancamentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(detalheLancamentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /detalhe-lancamentos/:id} : get the "id" detalheLancamento.
     *
     * @param id the id of the detalheLancamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalheLancamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalhe-lancamentos/{id}")
    public ResponseEntity<DetalheLancamentoDTO> getDetalheLancamento(@PathVariable Long id) {
        log.debug("REST request to get DetalheLancamento : {}", id);
        Optional<DetalheLancamentoDTO> detalheLancamentoDTO = detalheLancamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalheLancamentoDTO);
    }

    /**
     * {@code DELETE  /detalhe-lancamentos/:id} : delete the "id" detalheLancamento.
     *
     * @param id the id of the detalheLancamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalhe-lancamentos/{id}")
    public ResponseEntity<Void> deleteDetalheLancamento(@PathVariable Long id) {
        log.debug("REST request to delete DetalheLancamento : {}", id);
        detalheLancamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
