package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.LocalArmazenamentoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.LocalArmazenamentoDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.LocalArmazenamento}.
 */
@RestController
@RequestMapping("/api")
public class LocalArmazenamentoResource {

    private final Logger log = LoggerFactory.getLogger(LocalArmazenamentoResource.class);

    private static final String ENTITY_NAME = "localArmazenamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalArmazenamentoService localArmazenamentoService;

    public LocalArmazenamentoResource(LocalArmazenamentoService localArmazenamentoService) {
        this.localArmazenamentoService = localArmazenamentoService;
    }

    /**
     * {@code POST  /local-armazenamentos} : Create a new localArmazenamento.
     *
     * @param localArmazenamentoDTO the localArmazenamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localArmazenamentoDTO, or with status {@code 400 (Bad Request)} if the localArmazenamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/local-armazenamentos")
    public ResponseEntity<LocalArmazenamentoDTO> createLocalArmazenamento(@Valid @RequestBody LocalArmazenamentoDTO localArmazenamentoDTO) throws URISyntaxException {
        log.debug("REST request to save LocalArmazenamento : {}", localArmazenamentoDTO);
        if (localArmazenamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new localArmazenamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalArmazenamentoDTO result = localArmazenamentoService.save(localArmazenamentoDTO);
        return ResponseEntity.created(new URI("/api/local-armazenamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /local-armazenamentos} : Updates an existing localArmazenamento.
     *
     * @param localArmazenamentoDTO the localArmazenamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localArmazenamentoDTO,
     * or with status {@code 400 (Bad Request)} if the localArmazenamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localArmazenamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/local-armazenamentos")
    public ResponseEntity<LocalArmazenamentoDTO> updateLocalArmazenamento(@Valid @RequestBody LocalArmazenamentoDTO localArmazenamentoDTO) throws URISyntaxException {
        log.debug("REST request to update LocalArmazenamento : {}", localArmazenamentoDTO);
        if (localArmazenamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocalArmazenamentoDTO result = localArmazenamentoService.save(localArmazenamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localArmazenamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /local-armazenamentos} : get all the localArmazenamentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localArmazenamentos in body.
     */
    @GetMapping("/local-armazenamentos")
    public ResponseEntity<List<LocalArmazenamentoDTO>> getAllLocalArmazenamentos(Pageable pageable) {
        log.debug("REST request to get a page of LocalArmazenamentos");
        Page<LocalArmazenamentoDTO> page = localArmazenamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /local-armazenamentos/:id} : get the "id" localArmazenamento.
     *
     * @param id the id of the localArmazenamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localArmazenamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/local-armazenamentos/{id}")
    public ResponseEntity<LocalArmazenamentoDTO> getLocalArmazenamento(@PathVariable Long id) {
        log.debug("REST request to get LocalArmazenamento : {}", id);
        Optional<LocalArmazenamentoDTO> localArmazenamentoDTO = localArmazenamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(localArmazenamentoDTO);
    }

    /**
     * {@code DELETE  /local-armazenamentos/:id} : delete the "id" localArmazenamento.
     *
     * @param id the id of the localArmazenamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/local-armazenamentos/{id}")
    public ResponseEntity<Void> deleteLocalArmazenamento(@PathVariable Long id) {
        log.debug("REST request to delete LocalArmazenamento : {}", id);
        localArmazenamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
