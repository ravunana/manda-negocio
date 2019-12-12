package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.ItemVendaService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.ItemVendaDTO;
import com.ravunana.manda.service.dto.ItemVendaCriteria;
import com.ravunana.manda.service.ItemVendaQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.ItemVenda}.
 */
@RestController
@RequestMapping("/api")
public class ItemVendaResource {

    private final Logger log = LoggerFactory.getLogger(ItemVendaResource.class);

    private static final String ENTITY_NAME = "itemVenda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemVendaService itemVendaService;

    private final ItemVendaQueryService itemVendaQueryService;

    public ItemVendaResource(ItemVendaService itemVendaService, ItemVendaQueryService itemVendaQueryService) {
        this.itemVendaService = itemVendaService;
        this.itemVendaQueryService = itemVendaQueryService;
    }

    /**
     * {@code POST  /item-vendas} : Create a new itemVenda.
     *
     * @param itemVendaDTO the itemVendaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemVendaDTO, or with status {@code 400 (Bad Request)} if the itemVenda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-vendas")
    public ResponseEntity<ItemVendaDTO> createItemVenda(@Valid @RequestBody ItemVendaDTO itemVendaDTO) throws URISyntaxException {
        log.debug("REST request to save ItemVenda : {}", itemVendaDTO);
        if (itemVendaDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemVenda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemVendaDTO result = itemVendaService.save(itemVendaDTO);
        return ResponseEntity.created(new URI("/api/item-vendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-vendas} : Updates an existing itemVenda.
     *
     * @param itemVendaDTO the itemVendaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemVendaDTO,
     * or with status {@code 400 (Bad Request)} if the itemVendaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemVendaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-vendas")
    public ResponseEntity<ItemVendaDTO> updateItemVenda(@Valid @RequestBody ItemVendaDTO itemVendaDTO) throws URISyntaxException {
        log.debug("REST request to update ItemVenda : {}", itemVendaDTO);
        if (itemVendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemVendaDTO result = itemVendaService.save(itemVendaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemVendaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-vendas} : get all the itemVendas.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemVendas in body.
     */
    @GetMapping("/item-vendas")
    public ResponseEntity<List<ItemVendaDTO>> getAllItemVendas(ItemVendaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemVendas by criteria: {}", criteria);
        Page<ItemVendaDTO> page = itemVendaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /item-vendas/count} : count all the itemVendas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/item-vendas/count")
    public ResponseEntity<Long> countItemVendas(ItemVendaCriteria criteria) {
        log.debug("REST request to count ItemVendas by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemVendaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /item-vendas/:id} : get the "id" itemVenda.
     *
     * @param id the id of the itemVendaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemVendaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-vendas/{id}")
    public ResponseEntity<ItemVendaDTO> getItemVenda(@PathVariable Long id) {
        log.debug("REST request to get ItemVenda : {}", id);
        Optional<ItemVendaDTO> itemVendaDTO = itemVendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemVendaDTO);
    }

    /**
     * {@code DELETE  /item-vendas/:id} : delete the "id" itemVenda.
     *
     * @param id the id of the itemVendaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-vendas/{id}")
    public ResponseEntity<Void> deleteItemVenda(@PathVariable Long id) {
        log.debug("REST request to delete ItemVenda : {}", id);
        itemVendaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
