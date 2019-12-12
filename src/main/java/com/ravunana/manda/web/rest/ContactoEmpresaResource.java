package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.ContactoEmpresaService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.ContactoEmpresaDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.ContactoEmpresa}.
 */
@RestController
@RequestMapping("/api")
public class ContactoEmpresaResource {

    private final Logger log = LoggerFactory.getLogger(ContactoEmpresaResource.class);

    private static final String ENTITY_NAME = "contactoEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactoEmpresaService contactoEmpresaService;

    public ContactoEmpresaResource(ContactoEmpresaService contactoEmpresaService) {
        this.contactoEmpresaService = contactoEmpresaService;
    }

    /**
     * {@code POST  /contacto-empresas} : Create a new contactoEmpresa.
     *
     * @param contactoEmpresaDTO the contactoEmpresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactoEmpresaDTO, or with status {@code 400 (Bad Request)} if the contactoEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contacto-empresas")
    public ResponseEntity<ContactoEmpresaDTO> createContactoEmpresa(@Valid @RequestBody ContactoEmpresaDTO contactoEmpresaDTO) throws URISyntaxException {
        log.debug("REST request to save ContactoEmpresa : {}", contactoEmpresaDTO);
        if (contactoEmpresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactoEmpresaDTO result = contactoEmpresaService.save(contactoEmpresaDTO);
        return ResponseEntity.created(new URI("/api/contacto-empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contacto-empresas} : Updates an existing contactoEmpresa.
     *
     * @param contactoEmpresaDTO the contactoEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactoEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the contactoEmpresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactoEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contacto-empresas")
    public ResponseEntity<ContactoEmpresaDTO> updateContactoEmpresa(@Valid @RequestBody ContactoEmpresaDTO contactoEmpresaDTO) throws URISyntaxException {
        log.debug("REST request to update ContactoEmpresa : {}", contactoEmpresaDTO);
        if (contactoEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactoEmpresaDTO result = contactoEmpresaService.save(contactoEmpresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactoEmpresaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contacto-empresas} : get all the contactoEmpresas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactoEmpresas in body.
     */
    @GetMapping("/contacto-empresas")
    public ResponseEntity<List<ContactoEmpresaDTO>> getAllContactoEmpresas(Pageable pageable) {
        log.debug("REST request to get a page of ContactoEmpresas");
        Page<ContactoEmpresaDTO> page = contactoEmpresaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contacto-empresas/:id} : get the "id" contactoEmpresa.
     *
     * @param id the id of the contactoEmpresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactoEmpresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contacto-empresas/{id}")
    public ResponseEntity<ContactoEmpresaDTO> getContactoEmpresa(@PathVariable Long id) {
        log.debug("REST request to get ContactoEmpresa : {}", id);
        Optional<ContactoEmpresaDTO> contactoEmpresaDTO = contactoEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactoEmpresaDTO);
    }

    /**
     * {@code DELETE  /contacto-empresas/:id} : delete the "id" contactoEmpresa.
     *
     * @param id the id of the contactoEmpresaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contacto-empresas/{id}")
    public ResponseEntity<Void> deleteContactoEmpresa(@PathVariable Long id) {
        log.debug("REST request to delete ContactoEmpresa : {}", id);
        contactoEmpresaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
