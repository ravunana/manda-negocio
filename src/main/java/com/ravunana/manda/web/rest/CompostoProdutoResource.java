package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.CompostoProdutoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.CompostoProdutoDTO;
import com.ravunana.manda.service.dto.CompostoProdutoCriteria;
import com.ravunana.manda.service.CompostoProdutoQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.CompostoProduto}.
 */
@RestController
@RequestMapping("/api")
public class CompostoProdutoResource {

    private final Logger log = LoggerFactory.getLogger(CompostoProdutoResource.class);

    private static final String ENTITY_NAME = "compostoProduto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompostoProdutoService compostoProdutoService;

    private final CompostoProdutoQueryService compostoProdutoQueryService;

    public CompostoProdutoResource(CompostoProdutoService compostoProdutoService, CompostoProdutoQueryService compostoProdutoQueryService) {
        this.compostoProdutoService = compostoProdutoService;
        this.compostoProdutoQueryService = compostoProdutoQueryService;
    }

    /**
     * {@code POST  /composto-produtos} : Create a new compostoProduto.
     *
     * @param compostoProdutoDTO the compostoProdutoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compostoProdutoDTO, or with status {@code 400 (Bad Request)} if the compostoProduto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/composto-produtos")
    public ResponseEntity<CompostoProdutoDTO> createCompostoProduto(@Valid @RequestBody CompostoProdutoDTO compostoProdutoDTO) throws URISyntaxException {
        log.debug("REST request to save CompostoProduto : {}", compostoProdutoDTO);
        if (compostoProdutoDTO.getId() != null) {
            throw new BadRequestAlertException("A new compostoProduto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompostoProdutoDTO result = compostoProdutoService.save(compostoProdutoDTO);
        return ResponseEntity.created(new URI("/api/composto-produtos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /composto-produtos} : Updates an existing compostoProduto.
     *
     * @param compostoProdutoDTO the compostoProdutoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compostoProdutoDTO,
     * or with status {@code 400 (Bad Request)} if the compostoProdutoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compostoProdutoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/composto-produtos")
    public ResponseEntity<CompostoProdutoDTO> updateCompostoProduto(@Valid @RequestBody CompostoProdutoDTO compostoProdutoDTO) throws URISyntaxException {
        log.debug("REST request to update CompostoProduto : {}", compostoProdutoDTO);
        if (compostoProdutoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompostoProdutoDTO result = compostoProdutoService.save(compostoProdutoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compostoProdutoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /composto-produtos} : get all the compostoProdutos.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compostoProdutos in body.
     */
    @GetMapping("/composto-produtos")
    public ResponseEntity<List<CompostoProdutoDTO>> getAllCompostoProdutos(CompostoProdutoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CompostoProdutos by criteria: {}", criteria);
        Page<CompostoProdutoDTO> page = compostoProdutoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /composto-produtos/count} : count all the compostoProdutos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/composto-produtos/count")
    public ResponseEntity<Long> countCompostoProdutos(CompostoProdutoCriteria criteria) {
        log.debug("REST request to count CompostoProdutos by criteria: {}", criteria);
        return ResponseEntity.ok().body(compostoProdutoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /composto-produtos/:id} : get the "id" compostoProduto.
     *
     * @param id the id of the compostoProdutoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compostoProdutoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/composto-produtos/{id}")
    public ResponseEntity<CompostoProdutoDTO> getCompostoProduto(@PathVariable Long id) {
        log.debug("REST request to get CompostoProduto : {}", id);
        Optional<CompostoProdutoDTO> compostoProdutoDTO = compostoProdutoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compostoProdutoDTO);
    }

    /**
     * {@code DELETE  /composto-produtos/:id} : delete the "id" compostoProduto.
     *
     * @param id the id of the compostoProdutoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/composto-produtos/{id}")
    public ResponseEntity<Void> deleteCompostoProduto(@PathVariable Long id) {
        log.debug("REST request to delete CompostoProduto : {}", id);
        compostoProdutoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
