package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.LookupItem;
import com.ravunana.manda.domain.Lookup;
import com.ravunana.manda.repository.LookupItemRepository;
import com.ravunana.manda.service.LookupItemService;
import com.ravunana.manda.service.dto.LookupItemDTO;
import com.ravunana.manda.service.mapper.LookupItemMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.LookupItemCriteria;
import com.ravunana.manda.service.LookupItemQueryService;

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

import com.ravunana.manda.domain.enumeration.LookupType;
/**
 * Integration tests for the {@link LookupItemResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class LookupItemResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LookupType DEFAULT_TYPE = LookupType.USUARIO;
    private static final LookupType UPDATED_TYPE = LookupType.SISTEMA;

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;
    private static final Integer SMALLER_ORDEM = 1 - 1;

    @Autowired
    private LookupItemRepository lookupItemRepository;

    @Autowired
    private LookupItemMapper lookupItemMapper;

    @Autowired
    private LookupItemService lookupItemService;

    @Autowired
    private LookupItemQueryService lookupItemQueryService;

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

    private MockMvc restLookupItemMockMvc;

    private LookupItem lookupItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LookupItemResource lookupItemResource = new LookupItemResource(lookupItemService, lookupItemQueryService);
        this.restLookupItemMockMvc = MockMvcBuilders.standaloneSetup(lookupItemResource)
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
    public static LookupItem createEntity(EntityManager em) {
        LookupItem lookupItem = new LookupItem()
            .valor(DEFAULT_VALOR)
            .nome(DEFAULT_NOME)
            .type(DEFAULT_TYPE)
            .ordem(DEFAULT_ORDEM);
        return lookupItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LookupItem createUpdatedEntity(EntityManager em) {
        LookupItem lookupItem = new LookupItem()
            .valor(UPDATED_VALOR)
            .nome(UPDATED_NOME)
            .type(UPDATED_TYPE)
            .ordem(UPDATED_ORDEM);
        return lookupItem;
    }

    @BeforeEach
    public void initTest() {
        lookupItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createLookupItem() throws Exception {
        int databaseSizeBeforeCreate = lookupItemRepository.findAll().size();

        // Create the LookupItem
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);
        restLookupItemMockMvc.perform(post("/api/lookup-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeCreate + 1);
        LookupItem testLookupItem = lookupItemList.get(lookupItemList.size() - 1);
        assertThat(testLookupItem.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testLookupItem.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testLookupItem.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testLookupItem.getOrdem()).isEqualTo(DEFAULT_ORDEM);
    }

    @Test
    @Transactional
    public void createLookupItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lookupItemRepository.findAll().size();

        // Create the LookupItem with an existing ID
        lookupItem.setId(1L);
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLookupItemMockMvc.perform(post("/api/lookup-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLookupItems() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList
        restLookupItemMockMvc.perform(get("/api/lookup-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookupItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)));
    }
    
    @Test
    @Transactional
    public void getLookupItem() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get the lookupItem
        restLookupItemMockMvc.perform(get("/api/lookup-items/{id}", lookupItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lookupItem.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM));
    }


    @Test
    @Transactional
    public void getLookupItemsByIdFiltering() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        Long id = lookupItem.getId();

        defaultLookupItemShouldBeFound("id.equals=" + id);
        defaultLookupItemShouldNotBeFound("id.notEquals=" + id);

        defaultLookupItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLookupItemShouldNotBeFound("id.greaterThan=" + id);

        defaultLookupItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLookupItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLookupItemsByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where valor equals to DEFAULT_VALOR
        defaultLookupItemShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the lookupItemList where valor equals to UPDATED_VALOR
        defaultLookupItemShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where valor not equals to DEFAULT_VALOR
        defaultLookupItemShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the lookupItemList where valor not equals to UPDATED_VALOR
        defaultLookupItemShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByValorIsInShouldWork() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultLookupItemShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the lookupItemList where valor equals to UPDATED_VALOR
        defaultLookupItemShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where valor is not null
        defaultLookupItemShouldBeFound("valor.specified=true");

        // Get all the lookupItemList where valor is null
        defaultLookupItemShouldNotBeFound("valor.specified=false");
    }
                @Test
    @Transactional
    public void getAllLookupItemsByValorContainsSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where valor contains DEFAULT_VALOR
        defaultLookupItemShouldBeFound("valor.contains=" + DEFAULT_VALOR);

        // Get all the lookupItemList where valor contains UPDATED_VALOR
        defaultLookupItemShouldNotBeFound("valor.contains=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByValorNotContainsSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where valor does not contain DEFAULT_VALOR
        defaultLookupItemShouldNotBeFound("valor.doesNotContain=" + DEFAULT_VALOR);

        // Get all the lookupItemList where valor does not contain UPDATED_VALOR
        defaultLookupItemShouldBeFound("valor.doesNotContain=" + UPDATED_VALOR);
    }


    @Test
    @Transactional
    public void getAllLookupItemsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where nome equals to DEFAULT_NOME
        defaultLookupItemShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the lookupItemList where nome equals to UPDATED_NOME
        defaultLookupItemShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where nome not equals to DEFAULT_NOME
        defaultLookupItemShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the lookupItemList where nome not equals to UPDATED_NOME
        defaultLookupItemShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultLookupItemShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the lookupItemList where nome equals to UPDATED_NOME
        defaultLookupItemShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where nome is not null
        defaultLookupItemShouldBeFound("nome.specified=true");

        // Get all the lookupItemList where nome is null
        defaultLookupItemShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllLookupItemsByNomeContainsSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where nome contains DEFAULT_NOME
        defaultLookupItemShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the lookupItemList where nome contains UPDATED_NOME
        defaultLookupItemShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where nome does not contain DEFAULT_NOME
        defaultLookupItemShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the lookupItemList where nome does not contain UPDATED_NOME
        defaultLookupItemShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllLookupItemsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where type equals to DEFAULT_TYPE
        defaultLookupItemShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the lookupItemList where type equals to UPDATED_TYPE
        defaultLookupItemShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where type not equals to DEFAULT_TYPE
        defaultLookupItemShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the lookupItemList where type not equals to UPDATED_TYPE
        defaultLookupItemShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultLookupItemShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the lookupItemList where type equals to UPDATED_TYPE
        defaultLookupItemShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where type is not null
        defaultLookupItemShouldBeFound("type.specified=true");

        // Get all the lookupItemList where type is null
        defaultLookupItemShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllLookupItemsByOrdemIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem equals to DEFAULT_ORDEM
        defaultLookupItemShouldBeFound("ordem.equals=" + DEFAULT_ORDEM);

        // Get all the lookupItemList where ordem equals to UPDATED_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.equals=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByOrdemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem not equals to DEFAULT_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.notEquals=" + DEFAULT_ORDEM);

        // Get all the lookupItemList where ordem not equals to UPDATED_ORDEM
        defaultLookupItemShouldBeFound("ordem.notEquals=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByOrdemIsInShouldWork() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem in DEFAULT_ORDEM or UPDATED_ORDEM
        defaultLookupItemShouldBeFound("ordem.in=" + DEFAULT_ORDEM + "," + UPDATED_ORDEM);

        // Get all the lookupItemList where ordem equals to UPDATED_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.in=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByOrdemIsNullOrNotNull() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem is not null
        defaultLookupItemShouldBeFound("ordem.specified=true");

        // Get all the lookupItemList where ordem is null
        defaultLookupItemShouldNotBeFound("ordem.specified=false");
    }

    @Test
    @Transactional
    public void getAllLookupItemsByOrdemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem is greater than or equal to DEFAULT_ORDEM
        defaultLookupItemShouldBeFound("ordem.greaterThanOrEqual=" + DEFAULT_ORDEM);

        // Get all the lookupItemList where ordem is greater than or equal to UPDATED_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.greaterThanOrEqual=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByOrdemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem is less than or equal to DEFAULT_ORDEM
        defaultLookupItemShouldBeFound("ordem.lessThanOrEqual=" + DEFAULT_ORDEM);

        // Get all the lookupItemList where ordem is less than or equal to SMALLER_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.lessThanOrEqual=" + SMALLER_ORDEM);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByOrdemIsLessThanSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem is less than DEFAULT_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.lessThan=" + DEFAULT_ORDEM);

        // Get all the lookupItemList where ordem is less than UPDATED_ORDEM
        defaultLookupItemShouldBeFound("ordem.lessThan=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void getAllLookupItemsByOrdemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        // Get all the lookupItemList where ordem is greater than DEFAULT_ORDEM
        defaultLookupItemShouldNotBeFound("ordem.greaterThan=" + DEFAULT_ORDEM);

        // Get all the lookupItemList where ordem is greater than SMALLER_ORDEM
        defaultLookupItemShouldBeFound("ordem.greaterThan=" + SMALLER_ORDEM);
    }


    @Test
    @Transactional
    public void getAllLookupItemsByLookupIsEqualToSomething() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);
        Lookup lookup = LookupResourceIT.createEntity(em);
        em.persist(lookup);
        em.flush();
        lookupItem.setLookup(lookup);
        lookupItemRepository.saveAndFlush(lookupItem);
        Long lookupId = lookup.getId();

        // Get all the lookupItemList where lookup equals to lookupId
        defaultLookupItemShouldBeFound("lookupId.equals=" + lookupId);

        // Get all the lookupItemList where lookup equals to lookupId + 1
        defaultLookupItemShouldNotBeFound("lookupId.equals=" + (lookupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLookupItemShouldBeFound(String filter) throws Exception {
        restLookupItemMockMvc.perform(get("/api/lookup-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lookupItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)));

        // Check, that the count call also returns 1
        restLookupItemMockMvc.perform(get("/api/lookup-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLookupItemShouldNotBeFound(String filter) throws Exception {
        restLookupItemMockMvc.perform(get("/api/lookup-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLookupItemMockMvc.perform(get("/api/lookup-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLookupItem() throws Exception {
        // Get the lookupItem
        restLookupItemMockMvc.perform(get("/api/lookup-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookupItem() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();

        // Update the lookupItem
        LookupItem updatedLookupItem = lookupItemRepository.findById(lookupItem.getId()).get();
        // Disconnect from session so that the updates on updatedLookupItem are not directly saved in db
        em.detach(updatedLookupItem);
        updatedLookupItem
            .valor(UPDATED_VALOR)
            .nome(UPDATED_NOME)
            .type(UPDATED_TYPE)
            .ordem(UPDATED_ORDEM);
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(updatedLookupItem);

        restLookupItemMockMvc.perform(put("/api/lookup-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO)))
            .andExpect(status().isOk());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);
        LookupItem testLookupItem = lookupItemList.get(lookupItemList.size() - 1);
        assertThat(testLookupItem.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testLookupItem.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLookupItem.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLookupItem.getOrdem()).isEqualTo(UPDATED_ORDEM);
    }

    @Test
    @Transactional
    public void updateNonExistingLookupItem() throws Exception {
        int databaseSizeBeforeUpdate = lookupItemRepository.findAll().size();

        // Create the LookupItem
        LookupItemDTO lookupItemDTO = lookupItemMapper.toDto(lookupItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLookupItemMockMvc.perform(put("/api/lookup-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookupItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LookupItem in the database
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLookupItem() throws Exception {
        // Initialize the database
        lookupItemRepository.saveAndFlush(lookupItem);

        int databaseSizeBeforeDelete = lookupItemRepository.findAll().size();

        // Delete the lookupItem
        restLookupItemMockMvc.perform(delete("/api/lookup-items/{id}", lookupItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LookupItem> lookupItemList = lookupItemRepository.findAll();
        assertThat(lookupItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
