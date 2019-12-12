package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.ConversaoUnidadeService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.ConversaoUnidadeDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.ConversaoUnidade}.
 */
@RestController
@RequestMapping("/api")
public class ConversaoUnidadeResource {

    private final Logger log = LoggerFactory.getLogger(ConversaoUnidadeResource.class);

    private static final String ENTITY_NAME = "conversaoUnidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConversaoUnidadeService conversaoUnidadeService;

    public ConversaoUnidadeResource(ConversaoUnidadeService conversaoUnidadeService) {
        this.conversaoUnidadeService = conversaoUnidadeService;
    }

    /**
     * {@code POST  /conversao-unidades} : Create a new conversaoUnidade.
     *
     * @param conversaoUnidadeDTO the conversaoUnidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conversaoUnidadeDTO, or with status {@code 400 (Bad Request)} if the conversaoUnidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conversao-unidades")
    public ResponseEntity<ConversaoUnidadeDTO> createConversaoUnidade(@Valid @RequestBody ConversaoUnidadeDTO conversaoUnidadeDTO) throws URISyntaxException {
        log.debug("REST request to save ConversaoUnidade : {}", conversaoUnidadeDTO);
        if (conversaoUnidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new conversaoUnidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConversaoUnidadeDTO result = conversaoUnidadeService.save(conversaoUnidadeDTO);
        return ResponseEntity.created(new URI("/api/conversao-unidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conversao-unidades} : Updates an existing conversaoUnidade.
     *
     * @param conversaoUnidadeDTO the conversaoUnidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conversaoUnidadeDTO,
     * or with status {@code 400 (Bad Request)} if the conversaoUnidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conversaoUnidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conversao-unidades")
    public ResponseEntity<ConversaoUnidadeDTO> updateConversaoUnidade(@Valid @RequestBody ConversaoUnidadeDTO conversaoUnidadeDTO) throws URISyntaxException {
        log.debug("REST request to update ConversaoUnidade : {}", conversaoUnidadeDTO);
        if (conversaoUnidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConversaoUnidadeDTO result = conversaoUnidadeService.save(conversaoUnidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conversaoUnidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conversao-unidades} : get all the conversaoUnidades.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conversaoUnidades in body.
     */
    @GetMapping("/conversao-unidades")
    public ResponseEntity<List<ConversaoUnidadeDTO>> getAllConversaoUnidades(Pageable pageable) {
        log.debug("REST request to get a page of ConversaoUnidades");
        Page<ConversaoUnidadeDTO> page = conversaoUnidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conversao-unidades/:id} : get the "id" conversaoUnidade.
     *
     * @param id the id of the conversaoUnidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conversaoUnidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conversao-unidades/{id}")
    public ResponseEntity<ConversaoUnidadeDTO> getConversaoUnidade(@PathVariable Long id) {
        log.debug("REST request to get ConversaoUnidade : {}", id);
        Optional<ConversaoUnidadeDTO> conversaoUnidadeDTO = conversaoUnidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conversaoUnidadeDTO);
    }

    /**
     * {@code DELETE  /conversao-unidades/:id} : delete the "id" conversaoUnidade.
     *
     * @param id the id of the conversaoUnidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conversao-unidades/{id}")
    public ResponseEntity<Void> deleteConversaoUnidade(@PathVariable Long id) {
        log.debug("REST request to delete ConversaoUnidade : {}", id);
        conversaoUnidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
