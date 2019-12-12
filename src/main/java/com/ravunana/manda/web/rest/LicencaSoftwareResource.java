package com.ravunana.manda.web.rest;

import com.ravunana.manda.service.LicencaSoftwareService;
import com.ravunana.manda.web.rest.errors.BadRequestAlertException;
import com.ravunana.manda.service.dto.LicencaSoftwareDTO;

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
 * REST controller for managing {@link com.ravunana.manda.domain.LicencaSoftware}.
 */
@RestController
@RequestMapping("/api")
public class LicencaSoftwareResource {

    private final Logger log = LoggerFactory.getLogger(LicencaSoftwareResource.class);

    private static final String ENTITY_NAME = "licencaSoftware";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicencaSoftwareService licencaSoftwareService;

    public LicencaSoftwareResource(LicencaSoftwareService licencaSoftwareService) {
        this.licencaSoftwareService = licencaSoftwareService;
    }

    /**
     * {@code POST  /licenca-softwares} : Create a new licencaSoftware.
     *
     * @param licencaSoftwareDTO the licencaSoftwareDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new licencaSoftwareDTO, or with status {@code 400 (Bad Request)} if the licencaSoftware has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/licenca-softwares")
    public ResponseEntity<LicencaSoftwareDTO> createLicencaSoftware(@Valid @RequestBody LicencaSoftwareDTO licencaSoftwareDTO) throws URISyntaxException {
        log.debug("REST request to save LicencaSoftware : {}", licencaSoftwareDTO);
        if (licencaSoftwareDTO.getId() != null) {
            throw new BadRequestAlertException("A new licencaSoftware cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LicencaSoftwareDTO result = licencaSoftwareService.save(licencaSoftwareDTO);
        return ResponseEntity.created(new URI("/api/licenca-softwares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /licenca-softwares} : Updates an existing licencaSoftware.
     *
     * @param licencaSoftwareDTO the licencaSoftwareDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licencaSoftwareDTO,
     * or with status {@code 400 (Bad Request)} if the licencaSoftwareDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the licencaSoftwareDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/licenca-softwares")
    public ResponseEntity<LicencaSoftwareDTO> updateLicencaSoftware(@Valid @RequestBody LicencaSoftwareDTO licencaSoftwareDTO) throws URISyntaxException {
        log.debug("REST request to update LicencaSoftware : {}", licencaSoftwareDTO);
        if (licencaSoftwareDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LicencaSoftwareDTO result = licencaSoftwareService.save(licencaSoftwareDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, licencaSoftwareDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /licenca-softwares} : get all the licencaSoftwares.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of licencaSoftwares in body.
     */
    @GetMapping("/licenca-softwares")
    public ResponseEntity<List<LicencaSoftwareDTO>> getAllLicencaSoftwares(Pageable pageable) {
        log.debug("REST request to get a page of LicencaSoftwares");
        Page<LicencaSoftwareDTO> page = licencaSoftwareService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /licenca-softwares/:id} : get the "id" licencaSoftware.
     *
     * @param id the id of the licencaSoftwareDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the licencaSoftwareDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/licenca-softwares/{id}")
    public ResponseEntity<LicencaSoftwareDTO> getLicencaSoftware(@PathVariable Long id) {
        log.debug("REST request to get LicencaSoftware : {}", id);
        Optional<LicencaSoftwareDTO> licencaSoftwareDTO = licencaSoftwareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(licencaSoftwareDTO);
    }

    /**
     * {@code DELETE  /licenca-softwares/:id} : delete the "id" licencaSoftware.
     *
     * @param id the id of the licencaSoftwareDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/licenca-softwares/{id}")
    public ResponseEntity<Void> deleteLicencaSoftware(@PathVariable Long id) {
        log.debug("REST request to delete LicencaSoftware : {}", id);
        licencaSoftwareService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
