package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.ContactoPessoaService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.ContactoPessoaDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.ContactoPessoa}.
 */
@RestController
@RequestMapping("/api")
public class ContactoPessoaResource {

    private final Logger log = LoggerFactory.getLogger(ContactoPessoaResource.class);

    private static final String ENTITY_NAME = "contactoPessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactoPessoaService contactoPessoaService;

    public ContactoPessoaResource(ContactoPessoaService contactoPessoaService) {
        this.contactoPessoaService = contactoPessoaService;
    }

    /**
     * {@code POST  /contacto-pessoas} : Create a new contactoPessoa.
     *
     * @param contactoPessoaDTO the contactoPessoaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactoPessoaDTO, or with status {@code 400 (Bad Request)} if the contactoPessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contacto-pessoas")
    public ResponseEntity<ContactoPessoaDTO> createContactoPessoa(@Valid @RequestBody ContactoPessoaDTO contactoPessoaDTO) throws URISyntaxException {
        log.debug("REST request to save ContactoPessoa : {}", contactoPessoaDTO);
        if (contactoPessoaDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactoPessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactoPessoaDTO result = contactoPessoaService.save(contactoPessoaDTO);
        return ResponseEntity.created(new URI("/api/contacto-pessoas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contacto-pessoas} : Updates an existing contactoPessoa.
     *
     * @param contactoPessoaDTO the contactoPessoaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactoPessoaDTO,
     * or with status {@code 400 (Bad Request)} if the contactoPessoaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactoPessoaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contacto-pessoas")
    public ResponseEntity<ContactoPessoaDTO> updateContactoPessoa(@Valid @RequestBody ContactoPessoaDTO contactoPessoaDTO) throws URISyntaxException {
        log.debug("REST request to update ContactoPessoa : {}", contactoPessoaDTO);
        if (contactoPessoaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactoPessoaDTO result = contactoPessoaService.save(contactoPessoaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactoPessoaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contacto-pessoas} : get all the contactoPessoas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactoPessoas in body.
     */
    @GetMapping("/contacto-pessoas")
    public ResponseEntity<List<ContactoPessoaDTO>> getAllContactoPessoas(Pageable pageable) {
        log.debug("REST request to get a page of ContactoPessoas");
        Page<ContactoPessoaDTO> page = contactoPessoaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contacto-pessoas/:id} : get the "id" contactoPessoa.
     *
     * @param id the id of the contactoPessoaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactoPessoaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contacto-pessoas/{id}")
    public ResponseEntity<ContactoPessoaDTO> getContactoPessoa(@PathVariable Long id) {
        log.debug("REST request to get ContactoPessoa : {}", id);
        Optional<ContactoPessoaDTO> contactoPessoaDTO = contactoPessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactoPessoaDTO);
    }

    /**
     * {@code DELETE  /contacto-pessoas/:id} : delete the "id" contactoPessoa.
     *
     * @param id the id of the contactoPessoaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contacto-pessoas/{id}")
    public ResponseEntity<Void> deleteContactoPessoa(@PathVariable Long id) {
        log.debug("REST request to delete ContactoPessoa : {}", id);
        contactoPessoaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/contacto-pessoas/add")
    public ContactoPessoaDTO addContacto(@RequestBody ContactoPessoaDTO contacto) {
        return contactoPessoaService.addContacto(contacto);
    }

    @DeleteMapping("/contacto-pessoas/delete/{index}")
    public ContactoPessoaDTO deleteContacto(@PathVariable int index) {
        return contactoPessoaService.deleteContacto(index);
    }

    @GetMapping("/contacto-pessoas/lista")
    public List<ContactoPessoaDTO> listarContactos() {
        return contactoPessoaService.listContactos();
    }
}
