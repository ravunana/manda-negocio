package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.VariacaoProdutoService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.VariacaoProdutoDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.VariacaoProduto}.
 */
@RestController
@RequestMapping("/api")
public class VariacaoProdutoResource {

    private final Logger log = LoggerFactory.getLogger(VariacaoProdutoResource.class);

    private static final String ENTITY_NAME = "variacaoProduto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VariacaoProdutoService variacaoProdutoService;

    public VariacaoProdutoResource(VariacaoProdutoService variacaoProdutoService) {
        this.variacaoProdutoService = variacaoProdutoService;
    }

    /**
     * {@code POST  /variacao-produtos} : Create a new variacaoProduto.
     *
     * @param variacaoProdutoDTO the variacaoProdutoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new variacaoProdutoDTO, or with status {@code 400 (Bad Request)} if the variacaoProduto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/variacao-produtos")
    public ResponseEntity<VariacaoProdutoDTO> createVariacaoProduto(@Valid @RequestBody VariacaoProdutoDTO variacaoProdutoDTO) throws URISyntaxException {
        log.debug("REST request to save VariacaoProduto : {}", variacaoProdutoDTO);
        if (variacaoProdutoDTO.getId() != null) {
            throw new BadRequestAlertException("A new variacaoProduto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VariacaoProdutoDTO result = variacaoProdutoService.save(variacaoProdutoDTO);
        return ResponseEntity.created(new URI("/api/variacao-produtos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /variacao-produtos} : Updates an existing variacaoProduto.
     *
     * @param variacaoProdutoDTO the variacaoProdutoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated variacaoProdutoDTO,
     * or with status {@code 400 (Bad Request)} if the variacaoProdutoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the variacaoProdutoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/variacao-produtos")
    public ResponseEntity<VariacaoProdutoDTO> updateVariacaoProduto(@Valid @RequestBody VariacaoProdutoDTO variacaoProdutoDTO) throws URISyntaxException {
        log.debug("REST request to update VariacaoProduto : {}", variacaoProdutoDTO);
        if (variacaoProdutoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VariacaoProdutoDTO result = variacaoProdutoService.save(variacaoProdutoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, variacaoProdutoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /variacao-produtos} : get all the variacaoProdutos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of variacaoProdutos in body.
     */
    @GetMapping("/variacao-produtos")
    public ResponseEntity<List<VariacaoProdutoDTO>> getAllVariacaoProdutos(Pageable pageable) {
        log.debug("REST request to get a page of VariacaoProdutos");
        Page<VariacaoProdutoDTO> page = variacaoProdutoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /variacao-produtos/:id} : get the "id" variacaoProduto.
     *
     * @param id the id of the variacaoProdutoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the variacaoProdutoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/variacao-produtos/{id}")
    public ResponseEntity<VariacaoProdutoDTO> getVariacaoProduto(@PathVariable Long id) {
        log.debug("REST request to get VariacaoProduto : {}", id);
        Optional<VariacaoProdutoDTO> variacaoProdutoDTO = variacaoProdutoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(variacaoProdutoDTO);
    }

    /**
     * {@code DELETE  /variacao-produtos/:id} : delete the "id" variacaoProduto.
     *
     * @param id the id of the variacaoProdutoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/variacao-produtos/{id}")
    public ResponseEntity<Void> deleteVariacaoProduto(@PathVariable Long id) {
        log.debug("REST request to delete VariacaoProduto : {}", id);
        variacaoProdutoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
