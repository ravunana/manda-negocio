package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.GrupoTributacaoImpostoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.GrupoTributacaoImpostoDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.GrupoTributacaoImposto}.
 */
@RestController
@RequestMapping("/api")
public class GrupoTributacaoImpostoResource {

    private final Logger log = LoggerFactory.getLogger(GrupoTributacaoImpostoResource.class);

    private static final String ENTITY_NAME = "grupoTributacaoImposto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoTributacaoImpostoService grupoTributacaoImpostoService;

    public GrupoTributacaoImpostoResource(GrupoTributacaoImpostoService grupoTributacaoImpostoService) {
        this.grupoTributacaoImpostoService = grupoTributacaoImpostoService;
    }

    /**
     * {@code POST  /grupo-tributacao-impostos} : Create a new grupoTributacaoImposto.
     *
     * @param grupoTributacaoImpostoDTO the grupoTributacaoImpostoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoTributacaoImpostoDTO, or with status {@code 400 (Bad Request)} if the grupoTributacaoImposto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupo-tributacao-impostos")
    public ResponseEntity<GrupoTributacaoImpostoDTO> createGrupoTributacaoImposto(@Valid @RequestBody GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO) throws URISyntaxException {
        log.debug("REST request to save GrupoTributacaoImposto : {}", grupoTributacaoImpostoDTO);
        if (grupoTributacaoImpostoDTO.getId() != null) {
            throw new BadRequestAlertException("A new grupoTributacaoImposto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoTributacaoImpostoDTO result = grupoTributacaoImpostoService.save(grupoTributacaoImpostoDTO);
        return ResponseEntity.created(new URI("/api/grupo-tributacao-impostos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grupo-tributacao-impostos} : Updates an existing grupoTributacaoImposto.
     *
     * @param grupoTributacaoImpostoDTO the grupoTributacaoImpostoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoTributacaoImpostoDTO,
     * or with status {@code 400 (Bad Request)} if the grupoTributacaoImpostoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoTributacaoImpostoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupo-tributacao-impostos")
    public ResponseEntity<GrupoTributacaoImpostoDTO> updateGrupoTributacaoImposto(@Valid @RequestBody GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO) throws URISyntaxException {
        log.debug("REST request to update GrupoTributacaoImposto : {}", grupoTributacaoImpostoDTO);
        if (grupoTributacaoImpostoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupoTributacaoImpostoDTO result = grupoTributacaoImpostoService.save(grupoTributacaoImpostoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoTributacaoImpostoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grupo-tributacao-impostos} : get all the grupoTributacaoImpostos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoTributacaoImpostos in body.
     */
    @GetMapping("/grupo-tributacao-impostos")
    public ResponseEntity<List<GrupoTributacaoImpostoDTO>> getAllGrupoTributacaoImpostos(Pageable pageable) {
        log.debug("REST request to get a page of GrupoTributacaoImpostos");
        Page<GrupoTributacaoImpostoDTO> page = grupoTributacaoImpostoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grupo-tributacao-impostos/:id} : get the "id" grupoTributacaoImposto.
     *
     * @param id the id of the grupoTributacaoImpostoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoTributacaoImpostoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupo-tributacao-impostos/{id}")
    public ResponseEntity<GrupoTributacaoImpostoDTO> getGrupoTributacaoImposto(@PathVariable Long id) {
        log.debug("REST request to get GrupoTributacaoImposto : {}", id);
        Optional<GrupoTributacaoImpostoDTO> grupoTributacaoImpostoDTO = grupoTributacaoImpostoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoTributacaoImpostoDTO);
    }

    /**
     * {@code DELETE  /grupo-tributacao-impostos/:id} : delete the "id" grupoTributacaoImposto.
     *
     * @param id the id of the grupoTributacaoImpostoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupo-tributacao-impostos/{id}")
    public ResponseEntity<Void> deleteGrupoTributacaoImposto(@PathVariable Long id) {
        log.debug("REST request to delete GrupoTributacaoImposto : {}", id);
        grupoTributacaoImpostoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
