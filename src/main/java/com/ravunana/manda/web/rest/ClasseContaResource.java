package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.ClasseContaService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.ClasseContaDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.ClasseConta}.
 */
@RestController
@RequestMapping("/api")
public class ClasseContaResource {

    private final Logger log = LoggerFactory.getLogger(ClasseContaResource.class);

    private static final String ENTITY_NAME = "classeConta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClasseContaService classeContaService;

    public ClasseContaResource(ClasseContaService classeContaService) {
        this.classeContaService = classeContaService;
    }

    /**
     * {@code POST  /classe-contas} : Create a new classeConta.
     *
     * @param classeContaDTO the classeContaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classeContaDTO, or with status {@code 400 (Bad Request)} if the classeConta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/classe-contas")
    public ResponseEntity<ClasseContaDTO> createClasseConta(@Valid @RequestBody ClasseContaDTO classeContaDTO) throws URISyntaxException {
        log.debug("REST request to save ClasseConta : {}", classeContaDTO);
        if (classeContaDTO.getId() != null) {
            throw new BadRequestAlertException("A new classeConta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClasseContaDTO result = classeContaService.save(classeContaDTO);
        return ResponseEntity.created(new URI("/api/classe-contas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /classe-contas} : Updates an existing classeConta.
     *
     * @param classeContaDTO the classeContaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classeContaDTO,
     * or with status {@code 400 (Bad Request)} if the classeContaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classeContaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/classe-contas")
    public ResponseEntity<ClasseContaDTO> updateClasseConta(@Valid @RequestBody ClasseContaDTO classeContaDTO) throws URISyntaxException {
        log.debug("REST request to update ClasseConta : {}", classeContaDTO);
        if (classeContaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClasseContaDTO result = classeContaService.save(classeContaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classeContaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /classe-contas} : get all the classeContas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classeContas in body.
     */
    @GetMapping("/classe-contas")
    public ResponseEntity<List<ClasseContaDTO>> getAllClasseContas(Pageable pageable) {
        log.debug("REST request to get a page of ClasseContas");
        Page<ClasseContaDTO> page = classeContaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /classe-contas/:id} : get the "id" classeConta.
     *
     * @param id the id of the classeContaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classeContaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/classe-contas/{id}")
    public ResponseEntity<ClasseContaDTO> getClasseConta(@PathVariable Long id) {
        log.debug("REST request to get ClasseConta : {}", id);
        Optional<ClasseContaDTO> classeContaDTO = classeContaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classeContaDTO);
    }

    /**
     * {@code DELETE  /classe-contas/:id} : delete the "id" classeConta.
     *
     * @param id the id of the classeContaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/classe-contas/{id}")
    public ResponseEntity<Void> deleteClasseConta(@PathVariable Long id) {
        log.debug("REST request to delete ClasseConta : {}", id);
        classeContaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
