package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.Lookup;
import com.ravunana.manda.domain.LookupItem;
import com.ravunana.manda.repository.LookupRepository;
import com.ravunana.manda.service.LookupService;
import com.ravunana.manda.service.dto.LookupDTO;
import com.ravunana.manda.service.mapper.LookupMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.LookupCriteria;
import com.ravunana.manda.service.LookupQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.manda.domain.enumeration.EntidadeSistema;
/**
 * Integration tests for the {@link LookupResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class LookupResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final EntidadeSistema DEFAULT_ENTIDADE = EntidadeSistema.SOFTWARE;
    private static final EntidadeSistema UPDATED_ENTIDADE = EntidadeSistema.LICENCA_SOFTWARE;

    private static final Boolean DEFAULT_MODIFICAVEL = false;
    private static final Boolean UPDATED_MODIFICAVEL = true;

    @Autowired
    private LookupRepository lookupRepository;

    @Autowired
    private LookupMapper lookupMapper;

    @Autowired
    private LookupService lookupService;

    @Autowired
    private LookupQueryService lookupQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restLookupMockMvc;

    private Lookup lookup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LookupResource lookupResource = new LookupResource(lookupService, lookupQueryService);
        this.restLookupMockMvc = MockMvcBuilders.standaloneSetup(lookupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lookup createEntity(EntityManager em) {
        Lookup lookup = new Lookup()
            .nome(DEFAULT_NOME)
            .entidade(DEFAULT_ENTIDADE)
            .modificavel(DEFAULT_MODIFICAVEL);
        return lookup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lookup createUpdatedEntity(EntityManager em) {
        Lookup lookup = new Lookup()
            .nome(UPDATED_NOME)
            .entidade(UPDATED_ENTIDADE)
            .modificavel(UPDATED_MODIFICAVEL);
        return lookup;
    }

    @BeforeEach
    public void initTest() {
        lookup = createEntity(em);
    }

    @Test
    @Transactional
    public void createLookup() throws Exception {
        int databaseSizeBeforeCreate = lookupRepository.findAll().size();

        // Create the Lookup
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);
        restLookupMockMvc.perform(post("/api/lookups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isCreated());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeCreate + 1);
        Lookup testLookup = lookupList.get(lookupList.size() - 1);
        assertThat(testLookup.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testLookup.getEntidade()).isEqualTo(DEFAULT_ENTIDADE);
        assertThat(testLookup.isModificavel()).isEqualTo(DEFAULT_MODIFICAVEL);
    }

    @Test
    @Transactional
    public void createLookupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lookupRepository.findAll().size();

        // Create the Lookup with an existing ID
        lookup.setId(1L);
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLookupMockMvc.perform(post("/api/lookups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lookupRepository.findAll().size();
        // set the field null
        lookup.setNome(null);

        // Create the Lookup, which fails.
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        restLookupMockMvc.perform(post("/api/lookups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isBadRequest());

        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLookups() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList
        restLookupMockMvc.perform(get("/api/lookups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].entidade").value(hasItem(DEFAULT_ENTIDADE.toString())))
            .andExpect(jsonPath("$.[*].modificavel").value(hasItem(DEFAULT_MODIFICAVEL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get the lookup
        restLookupMockMvc.perform(get("/api/lookups/{id}", lookup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lookup.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.entidade").value(DEFAULT_ENTIDADE.toString()))
            .andExpect(jsonPath("$.modificavel").value(DEFAULT_MODIFICAVEL.booleanValue()));
    }


    @Test
    @Transactional
    public void getLookupsByIdFiltering() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        Long id = lookup.getId();

        defaultLookupShouldBeFound("id.equals=" + id);
        defaultLookupShouldNotBeFound("id.notEquals=" + id);

        defaultLookupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLookupShouldNotBeFound("id.greaterThan=" + id);

        defaultLookupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLookupShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLookupsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where nome equals to DEFAULT_NOME
        defaultLookupShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the lookupList where nome equals to UPDATED_NOME
        defaultLookupShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllLookupsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where nome not equals to DEFAULT_NOME
        defaultLookupShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the lookupList where nome not equals to UPDATED_NOME
        defaultLookupShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllLookupsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultLookupShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the lookupList where nome equals to UPDATED_NOME
        defaultLookupShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllLookupsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where nome is not null
        defaultLookupShouldBeFound("nome.specified=true");

        // Get all the lookupList where nome is null
        defaultLookupShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllLookupsByNomeContainsSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where nome contains DEFAULT_NOME
        defaultLookupShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the lookupList where nome contains UPDATED_NOME
        defaultLookupShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllLookupsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where nome does not contain DEFAULT_NOME
        defaultLookupShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the lookupList where nome does not contain UPDATED_NOME
        defaultLookupShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllLookupsByEntidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where entidade equals to DEFAULT_ENTIDADE
        defaultLookupShouldBeFound("entidade.equals=" + DEFAULT_ENTIDADE);

        // Get all the lookupList where entidade equals to UPDATED_ENTIDADE
        defaultLookupShouldNotBeFound("entidade.equals=" + UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllLookupsByEntidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where entidade not equals to DEFAULT_ENTIDADE
        defaultLookupShouldNotBeFound("entidade.notEquals=" + DEFAULT_ENTIDADE);

        // Get all the lookupList where entidade not equals to UPDATED_ENTIDADE
        defaultLookupShouldBeFound("entidade.notEquals=" + UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllLookupsByEntidadeIsInShouldWork() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where entidade in DEFAULT_ENTIDADE or UPDATED_ENTIDADE
        defaultLookupShouldBeFound("entidade.in=" + DEFAULT_ENTIDADE + "," + UPDATED_ENTIDADE);

        // Get all the lookupList where entidade equals to UPDATED_ENTIDADE
        defaultLookupShouldNotBeFound("entidade.in=" + UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllLookupsByEntidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where entidade is not null
        defaultLookupShouldBeFound("entidade.specified=true");

        // Get all the lookupList where entidade is null
        defaultLookupShouldNotBeFound("entidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllLookupsByModificavelIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where modificavel equals to DEFAULT_MODIFICAVEL
        defaultLookupShouldBeFound("modificavel.equals=" + DEFAULT_MODIFICAVEL);

        // Get all the lookupList where modificavel equals to UPDATED_MODIFICAVEL
        defaultLookupShouldNotBeFound("modificavel.equals=" + UPDATED_MODIFICAVEL);
    }

    @Test
    @Transactional
    public void getAllLookupsByModificavelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where modificavel not equals to DEFAULT_MODIFICAVEL
        defaultLookupShouldNotBeFound("modificavel.notEquals=" + DEFAULT_MODIFICAVEL);

        // Get all the lookupList where modificavel not equals to UPDATED_MODIFICAVEL
        defaultLookupShouldBeFound("modificavel.notEquals=" + UPDATED_MODIFICAVEL);
    }

    @Test
    @Transactional
    public void getAllLookupsByModificavelIsInShouldWork() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where modificavel in DEFAULT_MODIFICAVEL or UPDATED_MODIFICAVEL
        defaultLookupShouldBeFound("modificavel.in=" + DEFAULT_MODIFICAVEL + "," + UPDATED_MODIFICAVEL);

        // Get all the lookupList where modificavel equals to UPDATED_MODIFICAVEL
        defaultLookupShouldNotBeFound("modificavel.in=" + UPDATED_MODIFICAVEL);
    }

    @Test
    @Transactional
    public void getAllLookupsByModificavelIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        // Get all the lookupList where modificavel is not null
        defaultLookupShouldBeFound("modificavel.specified=true");

        // Get all the lookupList where modificavel is null
        defaultLookupShouldNotBeFound("modificavel.specified=false");
    }

    @Test
    @Transactional
    public void getAllLookupsByLookupItemIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);
        LookupItem lookupItem = LookupItemResourceIT.createEntity(em);
        em.persist(lookupItem);
        em.flush();
        lookup.addLookupItem(lookupItem);
        lookupRepository.saveAndFlush(lookup);
        Long lookupItemId = lookupItem.getId();

        // Get all the lookupList where lookupItem equals to lookupItemId
        defaultLookupShouldBeFound("lookupItemId.equals=" + lookupItemId);

        // Get all the lookupList where lookupItem equals to lookupItemId + 1
        defaultLookupShouldNotBeFound("lookupItemId.equals=" + (lookupItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLookupShouldBeFound(String filter) throws Exception {
        restLookupMockMvc.perform(get("/api/lookups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].entidade").value(hasItem(DEFAULT_ENTIDADE.toString())))
            .andExpect(jsonPath("$.[*].modificavel").value(hasItem(DEFAULT_MODIFICAVEL.booleanValue())));

        // Check, that the count call also returns 1
        restLookupMockMvc.perform(get("/api/lookups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLookupShouldNotBeFound(String filter) throws Exception {
        restLookupMockMvc.perform(get("/api/lookups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLookupMockMvc.perform(get("/api/lookups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLookup() throws Exception {
        // Get the lookup
        restLookupMockMvc.perform(get("/api/lookups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();

        // Update the lookup
        Lookup updatedLookup = lookupRepository.findById(lookup.getId()).get();
        // Disconnect from session so that the updates on updatedLookup are not directly saved in db
        em.detach(updatedLookup);
        updatedLookup
            .nome(UPDATED_NOME)
            .entidade(UPDATED_ENTIDADE)
            .modificavel(UPDATED_MODIFICAVEL);
        LookupDTO lookupDTO = lookupMapper.toDto(updatedLookup);

        restLookupMockMvc.perform(put("/api/lookups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isOk());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
        Lookup testLookup = lookupList.get(lookupList.size() - 1);
        assertThat(testLookup.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLookup.getEntidade()).isEqualTo(UPDATED_ENTIDADE);
        assertThat(testLookup.isModificavel()).isEqualTo(UPDATED_MODIFICAVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingLookup() throws Exception {
        int databaseSizeBeforeUpdate = lookupRepository.findAll().size();

        // Create the Lookup
        LookupDTO lookupDTO = lookupMapper.toDto(lookup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupMockMvc.perform(put("/api/lookups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lookup in the database
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLookup() throws Exception {
        // Initialize the database
        lookupRepository.saveAndFlush(lookup);

        int databaseSizeBeforeDelete = lookupRepository.findAll().size();

        // Delete the lookup
        restLookupMockMvc.perform(delete("/api/lookups/{id}", lookup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lookup> lookupList = lookupRepository.findAll();
        assertThat(lookupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
