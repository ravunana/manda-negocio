package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.MoradaPessoaService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.MoradaPessoaDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.MoradaPessoa}.
 */
@RestController
@RequestMapping("/api")
public class MoradaPessoaResource {

    private final Logger log = LoggerFactory.getLogger(MoradaPessoaResource.class);

    private static final String ENTITY_NAME = "moradaPessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoradaPessoaService moradaPessoaService;

    public MoradaPessoaResource(MoradaPessoaService moradaPessoaService) {
        this.moradaPessoaService = moradaPessoaService;
    }

    /**
     * {@code POST  /morada-pessoas} : Create a new moradaPessoa.
     *
     * @param moradaPessoaDTO the moradaPessoaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moradaPessoaDTO, or with status {@code 400 (Bad Request)} if the moradaPessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/morada-pessoas")
    public ResponseEntity<MoradaPessoaDTO> createMoradaPessoa(@Valid @RequestBody MoradaPessoaDTO moradaPessoaDTO) throws URISyntaxException {
        log.debug("REST request to save MoradaPessoa : {}", moradaPessoaDTO);
        if (moradaPessoaDTO.getId() != null) {
            throw new BadRequestAlertException("A new moradaPessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoradaPessoaDTO result = moradaPessoaService.save(moradaPessoaDTO);
        return ResponseEntity.created(new URI("/api/morada-pessoas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /morada-pessoas} : Updates an existing moradaPessoa.
     *
     * @param moradaPessoaDTO the moradaPessoaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moradaPessoaDTO,
     * or with status {@code 400 (Bad Request)} if the moradaPessoaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moradaPessoaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/morada-pessoas")
    public ResponseEntity<MoradaPessoaDTO> updateMoradaPessoa(@Valid @RequestBody MoradaPessoaDTO moradaPessoaDTO) throws URISyntaxException {
        log.debug("REST request to update MoradaPessoa : {}", moradaPessoaDTO);
        if (moradaPessoaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MoradaPessoaDTO result = moradaPessoaService.save(moradaPessoaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moradaPessoaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /morada-pessoas} : get all the moradaPessoas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moradaPessoas in body.
     */
    @GetMapping("/morada-pessoas")
    public ResponseEntity<List<MoradaPessoaDTO>> getAllMoradaPessoas(Pageable pageable) {
        log.debug("REST request to get a page of MoradaPessoas");
        Page<MoradaPessoaDTO> page = moradaPessoaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /morada-pessoas/:id} : get the "id" moradaPessoa.
     *
     * @param id the id of the moradaPessoaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moradaPessoaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/morada-pessoas/{id}")
    public ResponseEntity<MoradaPessoaDTO> getMoradaPessoa(@PathVariable Long id) {
        log.debug("REST request to get MoradaPessoa : {}", id);
        Optional<MoradaPessoaDTO> moradaPessoaDTO = moradaPessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moradaPessoaDTO);
    }

    /**
     * {@code DELETE  /morada-pessoas/:id} : delete the "id" moradaPessoa.
     *
     * @param id the id of the moradaPessoaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/morada-pessoas/{id}")
    public ResponseEntity<Void> deleteMoradaPessoa(@PathVariable Long id) {
        log.debug("REST request to delete MoradaPessoa : {}", id);
        moradaPessoaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
