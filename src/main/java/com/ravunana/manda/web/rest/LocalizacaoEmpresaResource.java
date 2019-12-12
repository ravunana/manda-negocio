package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.LocalizacaoEmpresaService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.LocalizacaoEmpresaDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.LocalizacaoEmpresa}.
 */
@RestController
@RequestMapping("/api")
public class LocalizacaoEmpresaResource {

    private final Logger log = LoggerFactory.getLogger(LocalizacaoEmpresaResource.class);

    private static final String ENTITY_NAME = "localizacaoEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalizacaoEmpresaService localizacaoEmpresaService;

    public LocalizacaoEmpresaResource(LocalizacaoEmpresaService localizacaoEmpresaService) {
        this.localizacaoEmpresaService = localizacaoEmpresaService;
    }

    /**
     * {@code POST  /localizacao-empresas} : Create a new localizacaoEmpresa.
     *
     * @param localizacaoEmpresaDTO the localizacaoEmpresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localizacaoEmpresaDTO, or with status {@code 400 (Bad Request)} if the localizacaoEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/localizacao-empresas")
    public ResponseEntity<LocalizacaoEmpresaDTO> createLocalizacaoEmpresa(@Valid @RequestBody LocalizacaoEmpresaDTO localizacaoEmpresaDTO) throws URISyntaxException {
        log.debug("REST request to save LocalizacaoEmpresa : {}", localizacaoEmpresaDTO);
        if (localizacaoEmpresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new localizacaoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalizacaoEmpresaDTO result = localizacaoEmpresaService.save(localizacaoEmpresaDTO);
        return ResponseEntity.created(new URI("/api/localizacao-empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /localizacao-empresas} : Updates an existing localizacaoEmpresa.
     *
     * @param localizacaoEmpresaDTO the localizacaoEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localizacaoEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the localizacaoEmpresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localizacaoEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/localizacao-empresas")
    public ResponseEntity<LocalizacaoEmpresaDTO> updateLocalizacaoEmpresa(@Valid @RequestBody LocalizacaoEmpresaDTO localizacaoEmpresaDTO) throws URISyntaxException {
        log.debug("REST request to update LocalizacaoEmpresa : {}", localizacaoEmpresaDTO);
        if (localizacaoEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocalizacaoEmpresaDTO result = localizacaoEmpresaService.save(localizacaoEmpresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localizacaoEmpresaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /localizacao-empresas} : get all the localizacaoEmpresas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localizacaoEmpresas in body.
     */
    @GetMapping("/localizacao-empresas")
    public ResponseEntity<List<LocalizacaoEmpresaDTO>> getAllLocalizacaoEmpresas(Pageable pageable) {
        log.debug("REST request to get a page of LocalizacaoEmpresas");
        Page<LocalizacaoEmpresaDTO> page = localizacaoEmpresaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /localizacao-empresas/:id} : get the "id" localizacaoEmpresa.
     *
     * @param id the id of the localizacaoEmpresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localizacaoEmpresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/localizacao-empresas/{id}")
    public ResponseEntity<LocalizacaoEmpresaDTO> getLocalizacaoEmpresa(@PathVariable Long id) {
        log.debug("REST request to get LocalizacaoEmpresa : {}", id);
        Optional<LocalizacaoEmpresaDTO> localizacaoEmpresaDTO = localizacaoEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(localizacaoEmpresaDTO);
    }

    /**
     * {@code DELETE  /localizacao-empresas/:id} : delete the "id" localizacaoEmpresa.
     *
     * @param id the id of the localizacaoEmpresaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/localizacao-empresas/{id}")
    public ResponseEntity<Void> deleteLocalizacaoEmpresa(@PathVariable Long id) {
        log.debug("REST request to delete LocalizacaoEmpresa : {}", id);
        localizacaoEmpresaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
