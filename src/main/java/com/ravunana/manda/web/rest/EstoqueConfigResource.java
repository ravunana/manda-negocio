package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.EstoqueConfigService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.EstoqueConfigDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.EstoqueConfig}.
 */
@RestController
@RequestMapping("/api")
public class EstoqueConfigResource {

    private final Logger log = LoggerFactory.getLogger(EstoqueConfigResource.class);

    private static final String ENTITY_NAME = "estoqueConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstoqueConfigService estoqueConfigService;

    public EstoqueConfigResource(EstoqueConfigService estoqueConfigService) {
        this.estoqueConfigService = estoqueConfigService;
    }

    /**
     * {@code POST  /estoque-configs} : Create a new estoqueConfig.
     *
     * @param estoqueConfigDTO the estoqueConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estoqueConfigDTO, or with status {@code 400 (Bad Request)} if the estoqueConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estoque-configs")
    public ResponseEntity<EstoqueConfigDTO> createEstoqueConfig(@Valid @RequestBody EstoqueConfigDTO estoqueConfigDTO) throws URISyntaxException {
        log.debug("REST request to save EstoqueConfig : {}", estoqueConfigDTO);
        if (estoqueConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new estoqueConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstoqueConfigDTO result = estoqueConfigService.save(estoqueConfigDTO);
        return ResponseEntity.created(new URI("/api/estoque-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estoque-configs} : Updates an existing estoqueConfig.
     *
     * @param estoqueConfigDTO the estoqueConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estoqueConfigDTO,
     * or with status {@code 400 (Bad Request)} if the estoqueConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estoqueConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estoque-configs")
    public ResponseEntity<EstoqueConfigDTO> updateEstoqueConfig(@Valid @RequestBody EstoqueConfigDTO estoqueConfigDTO) throws URISyntaxException {
        log.debug("REST request to update EstoqueConfig : {}", estoqueConfigDTO);
        if (estoqueConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstoqueConfigDTO result = estoqueConfigService.save(estoqueConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estoqueConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estoque-configs} : get all the estoqueConfigs.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estoqueConfigs in body.
     */
    @GetMapping("/estoque-configs")
    public ResponseEntity<List<EstoqueConfigDTO>> getAllEstoqueConfigs(Pageable pageable) {
        log.debug("REST request to get a page of EstoqueConfigs");
        Page<EstoqueConfigDTO> page = estoqueConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estoque-configs/:id} : get the "id" estoqueConfig.
     *
     * @param id the id of the estoqueConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estoqueConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estoque-configs/{id}")
    public ResponseEntity<EstoqueConfigDTO> getEstoqueConfig(@PathVariable Long id) {
        log.debug("REST request to get EstoqueConfig : {}", id);
        Optional<EstoqueConfigDTO> estoqueConfigDTO = estoqueConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estoqueConfigDTO);
    }

    /**
     * {@code DELETE  /estoque-configs/:id} : delete the "id" estoqueConfig.
     *
     * @param id the id of the estoqueConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estoque-configs/{id}")
    public ResponseEntity<Void> deleteEstoqueConfig(@PathVariable Long id) {
        log.debug("REST request to delete EstoqueConfig : {}", id);
        estoqueConfigService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
