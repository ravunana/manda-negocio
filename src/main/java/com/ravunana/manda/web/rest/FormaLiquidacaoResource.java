package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.FormaLiquidacaoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.FormaLiquidacaoDTO;
import com.ravunana.manda.service.dto.FormaLiquidacaoCriteria;
import com.ravunana.manda.service.FormaLiquidacaoQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.FormaLiquidacao}.
 */
@RestController
@RequestMapping("/api")
public class FormaLiquidacaoResource {

    private final Logger log = LoggerFactory.getLogger(FormaLiquidacaoResource.class);

    private static final String ENTITY_NAME = "formaLiquidacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormaLiquidacaoService formaLiquidacaoService;

    private final FormaLiquidacaoQueryService formaLiquidacaoQueryService;

    public FormaLiquidacaoResource(FormaLiquidacaoService formaLiquidacaoService, FormaLiquidacaoQueryService formaLiquidacaoQueryService) {
        this.formaLiquidacaoService = formaLiquidacaoService;
        this.formaLiquidacaoQueryService = formaLiquidacaoQueryService;
    }

    /**
     * {@code POST  /forma-liquidacaos} : Create a new formaLiquidacao.
     *
     * @param formaLiquidacaoDTO the formaLiquidacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formaLiquidacaoDTO, or with status {@code 400 (Bad Request)} if the formaLiquidacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forma-liquidacaos")
    public ResponseEntity<FormaLiquidacaoDTO> createFormaLiquidacao(@Valid @RequestBody FormaLiquidacaoDTO formaLiquidacaoDTO) throws URISyntaxException {
        log.debug("REST request to save FormaLiquidacao : {}", formaLiquidacaoDTO);
        if (formaLiquidacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new formaLiquidacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormaLiquidacaoDTO result = formaLiquidacaoService.save(formaLiquidacaoDTO);
        return ResponseEntity.created(new URI("/api/forma-liquidacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forma-liquidacaos} : Updates an existing formaLiquidacao.
     *
     * @param formaLiquidacaoDTO the formaLiquidacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formaLiquidacaoDTO,
     * or with status {@code 400 (Bad Request)} if the formaLiquidacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formaLiquidacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forma-liquidacaos")
    public ResponseEntity<FormaLiquidacaoDTO> updateFormaLiquidacao(@Valid @RequestBody FormaLiquidacaoDTO formaLiquidacaoDTO) throws URISyntaxException {
        log.debug("REST request to update FormaLiquidacao : {}", formaLiquidacaoDTO);
        if (formaLiquidacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormaLiquidacaoDTO result = formaLiquidacaoService.save(formaLiquidacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formaLiquidacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /forma-liquidacaos} : get all the formaLiquidacaos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formaLiquidacaos in body.
     */
    @GetMapping("/forma-liquidacaos")
    public ResponseEntity<List<FormaLiquidacaoDTO>> getAllFormaLiquidacaos(FormaLiquidacaoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FormaLiquidacaos by criteria: {}", criteria);
        Page<FormaLiquidacaoDTO> page = formaLiquidacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /forma-liquidacaos/count} : count all the formaLiquidacaos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/forma-liquidacaos/count")
    public ResponseEntity<Long> countFormaLiquidacaos(FormaLiquidacaoCriteria criteria) {
        log.debug("REST request to count FormaLiquidacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(formaLiquidacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /forma-liquidacaos/:id} : get the "id" formaLiquidacao.
     *
     * @param id the id of the formaLiquidacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formaLiquidacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forma-liquidacaos/{id}")
    public ResponseEntity<FormaLiquidacaoDTO> getFormaLiquidacao(@PathVariable Long id) {
        log.debug("REST request to get FormaLiquidacao : {}", id);
        Optional<FormaLiquidacaoDTO> formaLiquidacaoDTO = formaLiquidacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formaLiquidacaoDTO);
    }

    /**
     * {@code DELETE  /forma-liquidacaos/:id} : delete the "id" formaLiquidacao.
     *
     * @param id the id of the formaLiquidacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forma-liquidacaos/{id}")
    public ResponseEntity<Void> deleteFormaLiquidacao(@PathVariable Long id) {
        log.debug("REST request to delete FormaLiquidacao : {}", id);
        formaLiquidacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
