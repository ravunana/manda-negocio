package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.EscrituracaoContabilService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.EscrituracaoContabilDTO;
import com.ravunana.manda.service.dto.ContaCreditoDTO;
import com.ravunana.manda.service.dto.ContaDebitoDTO;
import com.ravunana.manda.service.dto.EscrituracaoContabilCriteria;
import com.ravunana.manda.service.ContaService;
import com.ravunana.manda.service.EscrituracaoContabilQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.StreamSupport;


/**
 * REST controller for managing {@link com.ravunana.manda.domain.EscrituracaoContabil}.
 */
@RestController
@RequestMapping("/api")
public class EscrituracaoContabilResource {

    private final Logger log = LoggerFactory.getLogger(EscrituracaoContabilResource.class);

    private static final String ENTITY_NAME = "escrituracaoContabil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EscrituracaoContabilService escrituracaoContabilService;

    private final EscrituracaoContabilQueryService escrituracaoContabilQueryService;

    @Autowired
    private ContaService contaService;

    public EscrituracaoContabilResource(EscrituracaoContabilService escrituracaoContabilService, EscrituracaoContabilQueryService escrituracaoContabilQueryService) {
        this.escrituracaoContabilService = escrituracaoContabilService;
        this.escrituracaoContabilQueryService = escrituracaoContabilQueryService;
    }

    /**
     * {@code POST  /escrituracao-contabils} : Create a new escrituracaoContabil.
     *
     * @param escrituracaoContabilDTO the escrituracaoContabilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new escrituracaoContabilDTO, or with status {@code 400 (Bad Request)} if the escrituracaoContabil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/escrituracao-contabils")
    public ResponseEntity<EscrituracaoContabilDTO> createEscrituracaoContabil(@Valid @RequestBody EscrituracaoContabilDTO escrituracaoContabilDTO) throws URISyntaxException {
        log.debug("REST request to save EscrituracaoContabil : {}", escrituracaoContabilDTO);
        if (escrituracaoContabilDTO.getId() != null) {
            throw new BadRequestAlertException("A new escrituracaoContabil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EscrituracaoContabilDTO result = escrituracaoContabilService.save(escrituracaoContabilDTO);
        return ResponseEntity.created(new URI("/api/escrituracao-contabils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /escrituracao-contabils} : Updates an existing escrituracaoContabil.
     *
     * @param escrituracaoContabilDTO the escrituracaoContabilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated escrituracaoContabilDTO,
     * or with status {@code 400 (Bad Request)} if the escrituracaoContabilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the escrituracaoContabilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/escrituracao-contabils")
    public ResponseEntity<EscrituracaoContabilDTO> updateEscrituracaoContabil(@Valid @RequestBody EscrituracaoContabilDTO escrituracaoContabilDTO) throws URISyntaxException {
        log.debug("REST request to update EscrituracaoContabil : {}", escrituracaoContabilDTO);
        if (escrituracaoContabilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EscrituracaoContabilDTO result = escrituracaoContabilService.save(escrituracaoContabilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, escrituracaoContabilDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /escrituracao-contabils} : get all the escrituracaoContabils.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of escrituracaoContabils in body.
     */
    @GetMapping("/escrituracao-contabils")
    public ResponseEntity<List<EscrituracaoContabilDTO>> getAllEscrituracaoContabils(EscrituracaoContabilCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EscrituracaoContabils by criteria: {}", criteria);
        Page<EscrituracaoContabilDTO> page = escrituracaoContabilQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /escrituracao-contabils/count} : count all the escrituracaoContabils.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/escrituracao-contabils/count")
    public ResponseEntity<Long> countEscrituracaoContabils(EscrituracaoContabilCriteria criteria) {
        log.debug("REST request to count EscrituracaoContabils by criteria: {}", criteria);
        return ResponseEntity.ok().body(escrituracaoContabilQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /escrituracao-contabils/:id} : get the "id" escrituracaoContabil.
     *
     * @param id the id of the escrituracaoContabilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the escrituracaoContabilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/escrituracao-contabils/{id}")
    public ResponseEntity<EscrituracaoContabilDTO> getEscrituracaoContabil(@PathVariable Long id) {
        log.debug("REST request to get EscrituracaoContabil : {}", id);
        Optional<EscrituracaoContabilDTO> escrituracaoContabilDTO = escrituracaoContabilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(escrituracaoContabilDTO);
    }

    /**
     * {@code DELETE  /escrituracao-contabils/:id} : delete the "id" escrituracaoContabil.
     *
     * @param id the id of the escrituracaoContabilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/escrituracao-contabils/{id}")
    public ResponseEntity<Void> deleteEscrituracaoContabil(@PathVariable Long id) {
        log.debug("REST request to delete EscrituracaoContabil : {}", id);
        escrituracaoContabilService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/escrituracao-contabils?query=:query} : search for the escrituracaoContabil corresponding
     * to the query.
     *
     * @param query the query of the escrituracaoContabil search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/escrituracao-contabils")
    public ResponseEntity<List<EscrituracaoContabilDTO>> searchEscrituracaoContabils(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EscrituracaoContabils for query {}", query);
        Page<EscrituracaoContabilDTO> page = escrituracaoContabilService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/escrituracao-contabils/add-credito")
    public ContaCreditoDTO addCredito(@RequestBody ContaCreditoDTO credito) {
        credito.setContaCreditarDescricao( contaService.findOne(credito.getContaCreditarId()).get().getDescricao() );
        return escrituracaoContabilService.addCredito(credito);
    }

    @DeleteMapping("/escrituracao-contabils/delete-credito/{index}")
    public ContaCreditoDTO deleteCredito(@PathVariable int index) {
        return escrituracaoContabilService.deleteCredito(index);
    }

    @GetMapping("/escrituracao-contabils/listar-creditos")
    public List<ContaCreditoDTO> listarCredito() {
        return escrituracaoContabilService.listarCreditos();
    }

    @PostMapping("/escrituracao-contabils/add-debito")
    public ContaDebitoDTO addDebito(@RequestBody ContaDebitoDTO debito) {
        debito.setContaDebitarDescricao( contaService.findOne(debito.getContaDebitarId()).get().getDescricao() );
        return escrituracaoContabilService.addDebito(debito);
    }

    @DeleteMapping("/escrituracao-contabils/delete-debito/{index}")
    public ContaDebitoDTO deleteDebito(@PathVariable int index) {
        return escrituracaoContabilService.deleteDebito(index);
    }

    @GetMapping("/escrituracao-contabils/listar-debitos")
    public List<ContaDebitoDTO> listarDebito() {
        return escrituracaoContabilService.listarDebitos();
    }
}
