package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.RegraMovimentacaoDebitoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.RegraMovimentacaoDebitoDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ravunana.manda.domain.RegraMovimentacaoDebito}.
 */
@RestController
@RequestMapping("/api")
public class RegraMovimentacaoDebitoResource {

    private final Logger log = LoggerFactory.getLogger(RegraMovimentacaoDebitoResource.class);

    private static final String ENTITY_NAME = "regraMovimentacaoDebito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegraMovimentacaoDebitoService regraMovimentacaoDebitoService;

    public RegraMovimentacaoDebitoResource(RegraMovimentacaoDebitoService regraMovimentacaoDebitoService) {
        this.regraMovimentacaoDebitoService = regraMovimentacaoDebitoService;
    }

    /**
     * {@code POST  /regra-movimentacao-debitos} : Create a new regraMovimentacaoDebito.
     *
     * @param regraMovimentacaoDebitoDTO the regraMovimentacaoDebitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regraMovimentacaoDebitoDTO, or with status {@code 400 (Bad Request)} if the regraMovimentacaoDebito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regra-movimentacao-debitos")
    public ResponseEntity<RegraMovimentacaoDebitoDTO> createRegraMovimentacaoDebito(@RequestBody RegraMovimentacaoDebitoDTO regraMovimentacaoDebitoDTO) throws URISyntaxException {
        log.debug("REST request to save RegraMovimentacaoDebito : {}", regraMovimentacaoDebitoDTO);
        if (regraMovimentacaoDebitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new regraMovimentacaoDebito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegraMovimentacaoDebitoDTO result = regraMovimentacaoDebitoService.save(regraMovimentacaoDebitoDTO);
        return ResponseEntity.created(new URI("/api/regra-movimentacao-debitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regra-movimentacao-debitos} : Updates an existing regraMovimentacaoDebito.
     *
     * @param regraMovimentacaoDebitoDTO the regraMovimentacaoDebitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regraMovimentacaoDebitoDTO,
     * or with status {@code 400 (Bad Request)} if the regraMovimentacaoDebitoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regraMovimentacaoDebitoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regra-movimentacao-debitos")
    public ResponseEntity<RegraMovimentacaoDebitoDTO> updateRegraMovimentacaoDebito(@RequestBody RegraMovimentacaoDebitoDTO regraMovimentacaoDebitoDTO) throws URISyntaxException {
        log.debug("REST request to update RegraMovimentacaoDebito : {}", regraMovimentacaoDebitoDTO);
        if (regraMovimentacaoDebitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegraMovimentacaoDebitoDTO result = regraMovimentacaoDebitoService.save(regraMovimentacaoDebitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regraMovimentacaoDebitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regra-movimentacao-debitos} : get all the regraMovimentacaoDebitos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regraMovimentacaoDebitos in body.
     */
    @GetMapping("/regra-movimentacao-debitos")
    public ResponseEntity<List<RegraMovimentacaoDebitoDTO>> getAllRegraMovimentacaoDebitos(Pageable pageable) {
        log.debug("REST request to get a page of RegraMovimentacaoDebitos");
        Page<RegraMovimentacaoDebitoDTO> page = regraMovimentacaoDebitoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /regra-movimentacao-debitos/:id} : get the "id" regraMovimentacaoDebito.
     *
     * @param id the id of the regraMovimentacaoDebitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regraMovimentacaoDebitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regra-movimentacao-debitos/{id}")
    public ResponseEntity<RegraMovimentacaoDebitoDTO> getRegraMovimentacaoDebito(@PathVariable Long id) {
        log.debug("REST request to get RegraMovimentacaoDebito : {}", id);
        Optional<RegraMovimentacaoDebitoDTO> regraMovimentacaoDebitoDTO = regraMovimentacaoDebitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regraMovimentacaoDebitoDTO);
    }

    /**
     * {@code DELETE  /regra-movimentacao-debitos/:id} : delete the "id" regraMovimentacaoDebito.
     *
     * @param id the id of the regraMovimentacaoDebitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regra-movimentacao-debitos/{id}")
    public ResponseEntity<Void> deleteRegraMovimentacaoDebito(@PathVariable Long id) {
        log.debug("REST request to delete RegraMovimentacaoDebito : {}", id);
        regraMovimentacaoDebitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
