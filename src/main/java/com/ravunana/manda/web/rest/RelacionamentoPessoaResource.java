package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.RelacionamentoPessoaService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.RelacionamentoPessoaDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.RelacionamentoPessoa}.
 */
@RestController
@RequestMapping("/api")
public class RelacionamentoPessoaResource {

    private final Logger log = LoggerFactory.getLogger(RelacionamentoPessoaResource.class);

    private static final String ENTITY_NAME = "relacionamentoPessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelacionamentoPessoaService relacionamentoPessoaService;

    public RelacionamentoPessoaResource(RelacionamentoPessoaService relacionamentoPessoaService) {
        this.relacionamentoPessoaService = relacionamentoPessoaService;
    }

    /**
     * {@code POST  /relacionamento-pessoas} : Create a new relacionamentoPessoa.
     *
     * @param relacionamentoPessoaDTO the relacionamentoPessoaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relacionamentoPessoaDTO, or with status {@code 400 (Bad Request)} if the relacionamentoPessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relacionamento-pessoas")
    public ResponseEntity<RelacionamentoPessoaDTO> createRelacionamentoPessoa(@Valid @RequestBody RelacionamentoPessoaDTO relacionamentoPessoaDTO) throws URISyntaxException {
        log.debug("REST request to save RelacionamentoPessoa : {}", relacionamentoPessoaDTO);
        if (relacionamentoPessoaDTO.getId() != null) {
            throw new BadRequestAlertException("A new relacionamentoPessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RelacionamentoPessoaDTO result = relacionamentoPessoaService.save(relacionamentoPessoaDTO);
        return ResponseEntity.created(new URI("/api/relacionamento-pessoas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relacionamento-pessoas} : Updates an existing relacionamentoPessoa.
     *
     * @param relacionamentoPessoaDTO the relacionamentoPessoaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relacionamentoPessoaDTO,
     * or with status {@code 400 (Bad Request)} if the relacionamentoPessoaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relacionamentoPessoaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relacionamento-pessoas")
    public ResponseEntity<RelacionamentoPessoaDTO> updateRelacionamentoPessoa(@Valid @RequestBody RelacionamentoPessoaDTO relacionamentoPessoaDTO) throws URISyntaxException {
        log.debug("REST request to update RelacionamentoPessoa : {}", relacionamentoPessoaDTO);
        if (relacionamentoPessoaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RelacionamentoPessoaDTO result = relacionamentoPessoaService.save(relacionamentoPessoaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relacionamentoPessoaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /relacionamento-pessoas} : get all the relacionamentoPessoas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relacionamentoPessoas in body.
     */
    @GetMapping("/relacionamento-pessoas")
    public ResponseEntity<List<RelacionamentoPessoaDTO>> getAllRelacionamentoPessoas(Pageable pageable) {
        log.debug("REST request to get a page of RelacionamentoPessoas");
        Page<RelacionamentoPessoaDTO> page = relacionamentoPessoaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /relacionamento-pessoas/:id} : get the "id" relacionamentoPessoa.
     *
     * @param id the id of the relacionamentoPessoaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relacionamentoPessoaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relacionamento-pessoas/{id}")
    public ResponseEntity<RelacionamentoPessoaDTO> getRelacionamentoPessoa(@PathVariable Long id) {
        log.debug("REST request to get RelacionamentoPessoa : {}", id);
        Optional<RelacionamentoPessoaDTO> relacionamentoPessoaDTO = relacionamentoPessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(relacionamentoPessoaDTO);
    }

    /**
     * {@code DELETE  /relacionamento-pessoas/:id} : delete the "id" relacionamentoPessoa.
     *
     * @param id the id of the relacionamentoPessoaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relacionamento-pessoas/{id}")
    public ResponseEntity<Void> deleteRelacionamentoPessoa(@PathVariable Long id) {
        log.debug("REST request to delete RelacionamentoPessoa : {}", id);
        relacionamentoPessoaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
