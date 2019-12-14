package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.ItemCompraService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.ItemCompraDTO;
import com.ravunana.manda.service.dto.ItemCompraCriteria;
import com.ravunana.manda.service.ItemCompraQueryService;

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
 * REST controller for managing {@link com.ravunana.manda.domain.ItemCompra}.
 */
@RestController
@RequestMapping("/api")
public class ItemCompraResource {

    private final Logger log = LoggerFactory.getLogger(ItemCompraResource.class);

    private static final String ENTITY_NAME = "itemCompra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemCompraService itemCompraService;

    private final ItemCompraQueryService itemCompraQueryService;

    public ItemCompraResource(ItemCompraService itemCompraService, ItemCompraQueryService itemCompraQueryService) {
        this.itemCompraService = itemCompraService;
        this.itemCompraQueryService = itemCompraQueryService;
    }

    /**
     * {@code POST  /item-compras} : Create a new itemCompra.
     *
     * @param itemCompraDTO the itemCompraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemCompraDTO, or with status {@code 400 (Bad Request)} if the itemCompra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-compras")
    public ResponseEntity<ItemCompraDTO> createItemCompra(@Valid @RequestBody ItemCompraDTO itemCompraDTO) throws URISyntaxException {
        log.debug("REST request to save ItemCompra : {}", itemCompraDTO);
        if (itemCompraDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemCompra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemCompraDTO result = itemCompraService.save(itemCompraDTO);
        return ResponseEntity.created(new URI("/api/item-compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-compras} : Updates an existing itemCompra.
     *
     * @param itemCompraDTO the itemCompraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemCompraDTO,
     * or with status {@code 400 (Bad Request)} if the itemCompraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemCompraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-compras")
    public ResponseEntity<ItemCompraDTO> updateItemCompra(@Valid @RequestBody ItemCompraDTO itemCompraDTO) throws URISyntaxException {
        log.debug("REST request to update ItemCompra : {}", itemCompraDTO);
        if (itemCompraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemCompraDTO result = itemCompraService.save(itemCompraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemCompraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-compras} : get all the itemCompras.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemCompras in body.
     */
    @GetMapping("/item-compras")
    public ResponseEntity<List<ItemCompraDTO>> getAllItemCompras(ItemCompraCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ItemCompras by criteria: {}", criteria);
        Page<ItemCompraDTO> page = itemCompraQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /item-compras/count} : count all the itemCompras.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/item-compras/count")
    public ResponseEntity<Long> countItemCompras(ItemCompraCriteria criteria) {
        log.debug("REST request to count ItemCompras by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemCompraQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /item-compras/:id} : get the "id" itemCompra.
     *
     * @param id the id of the itemCompraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemCompraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-compras/{id}")
    public ResponseEntity<ItemCompraDTO> getItemCompra(@PathVariable Long id) {
        log.debug("REST request to get ItemCompra : {}", id);
        Optional<ItemCompraDTO> itemCompraDTO = itemCompraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemCompraDTO);
    }

    /**
     * {@code DELETE  /item-compras/:id} : delete the "id" itemCompra.
     *
     * @param id the id of the itemCompraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-compras/{id}")
    public ResponseEntity<Void> deleteItemCompra(@PathVariable Long id) {
        log.debug("REST request to delete ItemCompra : {}", id);
        itemCompraService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/item-compras/add")
    public ItemCompraDTO addContacto(@RequestBody ItemCompraDTO item) {
        return itemCompraService.addItem(item);
    }

    @DeleteMapping("/item-compras/delete/{index}")
    public ItemCompraDTO deleteContacto(@PathVariable int index) {
        return itemCompraService.deleteItem(index);
    }

    @GetMapping("/item-compras/lista")
    public List<ItemCompraDTO> getItems() {
        return itemCompraService.getItems();
    }

    @GetMapping("/item-compras/lista/clean")
    public void cleanItem() {
        itemCompraService.cleanItems();
    }
}
