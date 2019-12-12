package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.FamiliaProdutoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.FamiliaProdutoDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.FamiliaProduto}.
 */
@RestController
@RequestMapping("/api")
public class FamiliaProdutoResource {

    private final Logger log = LoggerFactory.getLogger(FamiliaProdutoResource.class);

    private static final String ENTITY_NAME = "familiaProduto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamiliaProdutoService familiaProdutoService;

    public FamiliaProdutoResource(FamiliaProdutoService familiaProdutoService) {
        this.familiaProdutoService = familiaProdutoService;
    }

    /**
     * {@code POST  /familia-produtos} : Create a new familiaProduto.
     *
     * @param familiaProdutoDTO the familiaProdutoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familiaProdutoDTO, or with status {@code 400 (Bad Request)} if the familiaProduto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/familia-produtos")
    public ResponseEntity<FamiliaProdutoDTO> createFamiliaProduto(@Valid @RequestBody FamiliaProdutoDTO familiaProdutoDTO) throws URISyntaxException {
        log.debug("REST request to save FamiliaProduto : {}", familiaProdutoDTO);
        if (familiaProdutoDTO.getId() != null) {
            throw new BadRequestAlertException("A new familiaProduto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FamiliaProdutoDTO result = familiaProdutoService.save(familiaProdutoDTO);
        return ResponseEntity.created(new URI("/api/familia-produtos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /familia-produtos} : Updates an existing familiaProduto.
     *
     * @param familiaProdutoDTO the familiaProdutoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familiaProdutoDTO,
     * or with status {@code 400 (Bad Request)} if the familiaProdutoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familiaProdutoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/familia-produtos")
    public ResponseEntity<FamiliaProdutoDTO> updateFamiliaProduto(@Valid @RequestBody FamiliaProdutoDTO familiaProdutoDTO) throws URISyntaxException {
        log.debug("REST request to update FamiliaProduto : {}", familiaProdutoDTO);
        if (familiaProdutoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FamiliaProdutoDTO result = familiaProdutoService.save(familiaProdutoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familiaProdutoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /familia-produtos} : get all the familiaProdutos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of familiaProdutos in body.
     */
    @GetMapping("/familia-produtos")
    public ResponseEntity<List<FamiliaProdutoDTO>> getAllFamiliaProdutos(Pageable pageable) {
        log.debug("REST request to get a page of FamiliaProdutos");
        Page<FamiliaProdutoDTO> page = familiaProdutoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /familia-produtos/:id} : get the "id" familiaProduto.
     *
     * @param id the id of the familiaProdutoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familiaProdutoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/familia-produtos/{id}")
    public ResponseEntity<FamiliaProdutoDTO> getFamiliaProduto(@PathVariable Long id) {
        log.debug("REST request to get FamiliaProduto : {}", id);
        Optional<FamiliaProdutoDTO> familiaProdutoDTO = familiaProdutoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familiaProdutoDTO);
    }

    /**
     * {@code DELETE  /familia-produtos/:id} : delete the "id" familiaProduto.
     *
     * @param id the id of the familiaProdutoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/familia-produtos/{id}")
    public ResponseEntity<Void> deleteFamiliaProduto(@PathVariable Long id) {
        log.debug("REST request to delete FamiliaProduto : {}", id);
        familiaProdutoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
