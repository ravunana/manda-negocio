package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.EstruturaCalculoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.EstruturaCalculoDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.EstruturaCalculo}.
 */
@RestController
@RequestMapping("/api")
public class EstruturaCalculoResource {

    private final Logger log = LoggerFactory.getLogger(EstruturaCalculoResource.class);

    private static final String ENTITY_NAME = "estruturaCalculo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstruturaCalculoService estruturaCalculoService;

    public EstruturaCalculoResource(EstruturaCalculoService estruturaCalculoService) {
        this.estruturaCalculoService = estruturaCalculoService;
    }

    /**
     * {@code POST  /estrutura-calculos} : Create a new estruturaCalculo.
     *
     * @param estruturaCalculoDTO the estruturaCalculoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estruturaCalculoDTO, or with status {@code 400 (Bad Request)} if the estruturaCalculo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estrutura-calculos")
    public ResponseEntity<EstruturaCalculoDTO> createEstruturaCalculo(@Valid @RequestBody EstruturaCalculoDTO estruturaCalculoDTO) throws URISyntaxException {
        log.debug("REST request to save EstruturaCalculo : {}", estruturaCalculoDTO);
        if (estruturaCalculoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estruturaCalculo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstruturaCalculoDTO result = estruturaCalculoService.save(estruturaCalculoDTO);
        return ResponseEntity.created(new URI("/api/estrutura-calculos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estrutura-calculos} : Updates an existing estruturaCalculo.
     *
     * @param estruturaCalculoDTO the estruturaCalculoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estruturaCalculoDTO,
     * or with status {@code 400 (Bad Request)} if the estruturaCalculoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estruturaCalculoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estrutura-calculos")
    public ResponseEntity<EstruturaCalculoDTO> updateEstruturaCalculo(@Valid @RequestBody EstruturaCalculoDTO estruturaCalculoDTO) throws URISyntaxException {
        log.debug("REST request to update EstruturaCalculo : {}", estruturaCalculoDTO);
        if (estruturaCalculoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstruturaCalculoDTO result = estruturaCalculoService.save(estruturaCalculoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estruturaCalculoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estrutura-calculos} : get all the estruturaCalculos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estruturaCalculos in body.
     */
    @GetMapping("/estrutura-calculos")
    public ResponseEntity<List<EstruturaCalculoDTO>> getAllEstruturaCalculos(Pageable pageable) {
        log.debug("REST request to get a page of EstruturaCalculos");
        Page<EstruturaCalculoDTO> page = estruturaCalculoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estrutura-calculos/:id} : get the "id" estruturaCalculo.
     *
     * @param id the id of the estruturaCalculoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estruturaCalculoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estrutura-calculos/{id}")
    public ResponseEntity<EstruturaCalculoDTO> getEstruturaCalculo(@PathVariable Long id) {
        log.debug("REST request to get EstruturaCalculo : {}", id);
        Optional<EstruturaCalculoDTO> estruturaCalculoDTO = estruturaCalculoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estruturaCalculoDTO);
    }

    /**
     * {@code DELETE  /estrutura-calculos/:id} : delete the "id" estruturaCalculo.
     *
     * @param id the id of the estruturaCalculoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estrutura-calculos/{id}")
    public ResponseEntity<Void> deleteEstruturaCalculo(@PathVariable Long id) {
        log.debug("REST request to delete EstruturaCalculo : {}", id);
        estruturaCalculoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
