package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.RegraMovimentacaoCreditoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.RegraMovimentacaoCreditoDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.RegraMovimentacaoCredito}.
 */
@RestController
@RequestMapping("/api")
public class RegraMovimentacaoCreditoResource {

    private final Logger log = LoggerFactory.getLogger(RegraMovimentacaoCreditoResource.class);

    private static final String ENTITY_NAME = "regraMovimentacaoCredito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegraMovimentacaoCreditoService regraMovimentacaoCreditoService;

    public RegraMovimentacaoCreditoResource(RegraMovimentacaoCreditoService regraMovimentacaoCreditoService) {
        this.regraMovimentacaoCreditoService = regraMovimentacaoCreditoService;
    }

    /**
     * {@code POST  /regra-movimentacao-creditos} : Create a new regraMovimentacaoCredito.
     *
     * @param regraMovimentacaoCreditoDTO the regraMovimentacaoCreditoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regraMovimentacaoCreditoDTO, or with status {@code 400 (Bad Request)} if the regraMovimentacaoCredito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regra-movimentacao-creditos")
    public ResponseEntity<RegraMovimentacaoCreditoDTO> createRegraMovimentacaoCredito(@RequestBody RegraMovimentacaoCreditoDTO regraMovimentacaoCreditoDTO) throws URISyntaxException {
        log.debug("REST request to save RegraMovimentacaoCredito : {}", regraMovimentacaoCreditoDTO);
        if (regraMovimentacaoCreditoDTO.getId() != null) {
            throw new BadRequestAlertException("A new regraMovimentacaoCredito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegraMovimentacaoCreditoDTO result = regraMovimentacaoCreditoService.save(regraMovimentacaoCreditoDTO);
        return ResponseEntity.created(new URI("/api/regra-movimentacao-creditos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regra-movimentacao-creditos} : Updates an existing regraMovimentacaoCredito.
     *
     * @param regraMovimentacaoCreditoDTO the regraMovimentacaoCreditoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regraMovimentacaoCreditoDTO,
     * or with status {@code 400 (Bad Request)} if the regraMovimentacaoCreditoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regraMovimentacaoCreditoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regra-movimentacao-creditos")
    public ResponseEntity<RegraMovimentacaoCreditoDTO> updateRegraMovimentacaoCredito(@RequestBody RegraMovimentacaoCreditoDTO regraMovimentacaoCreditoDTO) throws URISyntaxException {
        log.debug("REST request to update RegraMovimentacaoCredito : {}", regraMovimentacaoCreditoDTO);
        if (regraMovimentacaoCreditoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegraMovimentacaoCreditoDTO result = regraMovimentacaoCreditoService.save(regraMovimentacaoCreditoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regraMovimentacaoCreditoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regra-movimentacao-creditos} : get all the regraMovimentacaoCreditos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regraMovimentacaoCreditos in body.
     */
    @GetMapping("/regra-movimentacao-creditos")
    public ResponseEntity<List<RegraMovimentacaoCreditoDTO>> getAllRegraMovimentacaoCreditos(Pageable pageable) {
        log.debug("REST request to get a page of RegraMovimentacaoCreditos");
        Page<RegraMovimentacaoCreditoDTO> page = regraMovimentacaoCreditoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /regra-movimentacao-creditos/:id} : get the "id" regraMovimentacaoCredito.
     *
     * @param id the id of the regraMovimentacaoCreditoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regraMovimentacaoCreditoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regra-movimentacao-creditos/{id}")
    public ResponseEntity<RegraMovimentacaoCreditoDTO> getRegraMovimentacaoCredito(@PathVariable Long id) {
        log.debug("REST request to get RegraMovimentacaoCredito : {}", id);
        Optional<RegraMovimentacaoCreditoDTO> regraMovimentacaoCreditoDTO = regraMovimentacaoCreditoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regraMovimentacaoCreditoDTO);
    }

    /**
     * {@code DELETE  /regra-movimentacao-creditos/:id} : delete the "id" regraMovimentacaoCredito.
     *
     * @param id the id of the regraMovimentacaoCreditoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regra-movimentacao-creditos/{id}")
    public ResponseEntity<Void> deleteRegraMovimentacaoCredito(@PathVariable Long id) {
        log.debug("REST request to delete RegraMovimentacaoCredito : {}", id);
        regraMovimentacaoCreditoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
