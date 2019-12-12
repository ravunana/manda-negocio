package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.Moeda;
import com.ravunana.manda.domain.DetalheLancamento;
import com.ravunana.manda.repository.MoedaRepository;
import com.ravunana.manda.service.MoedaService;
import com.ravunana.manda.service.dto.MoedaDTO;
import com.ravunana.manda.service.mapper.MoedaMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.MoedaCriteria;
import com.ravunana.manda.service.MoedaQueryService;

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

/**
 * Integration tests for the {@link MoedaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class MoedaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NACIONAL = false;
    private static final Boolean UPDATED_NACIONAL = true;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    @Autowired
    private MoedaRepository moedaRepository;

    @Autowired
    private MoedaMapper moedaMapper;

    @Autowired
    private MoedaService moedaService;

    @Autowired
    private MoedaQueryService moedaQueryService;

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

    private MockMvc restMoedaMockMvc;

    private Moeda moeda;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MoedaResource moedaResource = new MoedaResource(moedaService, moedaQueryService);
        this.restMoedaMockMvc = MockMvcBuilders.standaloneSetup(moedaResource)
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
    public static Moeda createEntity(EntityManager em) {
        Moeda moeda = new Moeda()
            .nome(DEFAULT_NOME)
            .codigo(DEFAULT_CODIGO)
            .pais(DEFAULT_PAIS)
            .nacional(DEFAULT_NACIONAL)
            .icon(DEFAULT_ICON);
        return moeda;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Moeda createUpdatedEntity(EntityManager em) {
        Moeda moeda = new Moeda()
            .nome(UPDATED_NOME)
            .codigo(UPDATED_CODIGO)
            .pais(UPDATED_PAIS)
            .nacional(UPDATED_NACIONAL)
            .icon(UPDATED_ICON);
        return moeda;
    }

    @BeforeEach
    public void initTest() {
        moeda = createEntity(em);
    }

    @Test
    @Transactional
    public void createMoeda() throws Exception {
        int databaseSizeBeforeCreate = moedaRepository.findAll().size();

        // Create the Moeda
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);
        restMoedaMockMvc.perform(post("/api/moedas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moedaDTO)))
            .andExpect(status().isCreated());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeCreate + 1);
        Moeda testMoeda = moedaList.get(moedaList.size() - 1);
        assertThat(testMoeda.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMoeda.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testMoeda.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testMoeda.isNacional()).isEqualTo(DEFAULT_NACIONAL);
        assertThat(testMoeda.getIcon()).isEqualTo(DEFAULT_ICON);
    }

    @Test
    @Transactional
    public void createMoedaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moedaRepository.findAll().size();

        // Create the Moeda with an existing ID
        moeda.setId(1L);
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoedaMockMvc.perform(post("/api/moedas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moedaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = moedaRepository.findAll().size();
        // set the field null
        moeda.setNome(null);

        // Create the Moeda, which fails.
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);

        restMoedaMockMvc.perform(post("/api/moedas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moedaDTO)))
            .andExpect(status().isBadRequest());

        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = moedaRepository.findAll().size();
        // set the field null
        moeda.setCodigo(null);

        // Create the Moeda, which fails.
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);

        restMoedaMockMvc.perform(post("/api/moedas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moedaDTO)))
            .andExpect(status().isBadRequest());

        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMoedas() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList
        restMoedaMockMvc.perform(get("/api/moedas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moeda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].nacional").value(hasItem(DEFAULT_NACIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));
    }
    
    @Test
    @Transactional
    public void getMoeda() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get the moeda
        restMoedaMockMvc.perform(get("/api/moedas/{id}", moeda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moeda.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.nacional").value(DEFAULT_NACIONAL.booleanValue()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON));
    }


    @Test
    @Transactional
    public void getMoedasByIdFiltering() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        Long id = moeda.getId();

        defaultMoedaShouldBeFound("id.equals=" + id);
        defaultMoedaShouldNotBeFound("id.notEquals=" + id);

        defaultMoedaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMoedaShouldNotBeFound("id.greaterThan=" + id);

        defaultMoedaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMoedaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMoedasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where nome equals to DEFAULT_NOME
        defaultMoedaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the moedaList where nome equals to UPDATED_NOME
        defaultMoedaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllMoedasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where nome not equals to DEFAULT_NOME
        defaultMoedaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the moedaList where nome not equals to UPDATED_NOME
        defaultMoedaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllMoedasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultMoedaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the moedaList where nome equals to UPDATED_NOME
        defaultMoedaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllMoedasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where nome is not null
        defaultMoedaShouldBeFound("nome.specified=true");

        // Get all the moedaList where nome is null
        defaultMoedaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllMoedasByNomeContainsSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where nome contains DEFAULT_NOME
        defaultMoedaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the moedaList where nome contains UPDATED_NOME
        defaultMoedaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllMoedasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where nome does not contain DEFAULT_NOME
        defaultMoedaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the moedaList where nome does not contain UPDATED_NOME
        defaultMoedaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllMoedasByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where codigo equals to DEFAULT_CODIGO
        defaultMoedaShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the moedaList where codigo equals to UPDATED_CODIGO
        defaultMoedaShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllMoedasByCodigoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where codigo not equals to DEFAULT_CODIGO
        defaultMoedaShouldNotBeFound("codigo.notEquals=" + DEFAULT_CODIGO);

        // Get all the moedaList where codigo not equals to UPDATED_CODIGO
        defaultMoedaShouldBeFound("codigo.notEquals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllMoedasByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultMoedaShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the moedaList where codigo equals to UPDATED_CODIGO
        defaultMoedaShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllMoedasByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where codigo is not null
        defaultMoedaShouldBeFound("codigo.specified=true");

        // Get all the moedaList where codigo is null
        defaultMoedaShouldNotBeFound("codigo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMoedasByCodigoContainsSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where codigo contains DEFAULT_CODIGO
        defaultMoedaShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the moedaList where codigo contains UPDATED_CODIGO
        defaultMoedaShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllMoedasByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where codigo does not contain DEFAULT_CODIGO
        defaultMoedaShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the moedaList where codigo does not contain UPDATED_CODIGO
        defaultMoedaShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }


    @Test
    @Transactional
    public void getAllMoedasByPaisIsEqualToSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where pais equals to DEFAULT_PAIS
        defaultMoedaShouldBeFound("pais.equals=" + DEFAULT_PAIS);

        // Get all the moedaList where pais equals to UPDATED_PAIS
        defaultMoedaShouldNotBeFound("pais.equals=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllMoedasByPaisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where pais not equals to DEFAULT_PAIS
        defaultMoedaShouldNotBeFound("pais.notEquals=" + DEFAULT_PAIS);

        // Get all the moedaList where pais not equals to UPDATED_PAIS
        defaultMoedaShouldBeFound("pais.notEquals=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllMoedasByPaisIsInShouldWork() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where pais in DEFAULT_PAIS or UPDATED_PAIS
        defaultMoedaShouldBeFound("pais.in=" + DEFAULT_PAIS + "," + UPDATED_PAIS);

        // Get all the moedaList where pais equals to UPDATED_PAIS
        defaultMoedaShouldNotBeFound("pais.in=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllMoedasByPaisIsNullOrNotNull() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where pais is not null
        defaultMoedaShouldBeFound("pais.specified=true");

        // Get all the moedaList where pais is null
        defaultMoedaShouldNotBeFound("pais.specified=false");
    }
                @Test
    @Transactional
    public void getAllMoedasByPaisContainsSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where pais contains DEFAULT_PAIS
        defaultMoedaShouldBeFound("pais.contains=" + DEFAULT_PAIS);

        // Get all the moedaList where pais contains UPDATED_PAIS
        defaultMoedaShouldNotBeFound("pais.contains=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllMoedasByPaisNotContainsSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where pais does not contain DEFAULT_PAIS
        defaultMoedaShouldNotBeFound("pais.doesNotContain=" + DEFAULT_PAIS);

        // Get all the moedaList where pais does not contain UPDATED_PAIS
        defaultMoedaShouldBeFound("pais.doesNotContain=" + UPDATED_PAIS);
    }


    @Test
    @Transactional
    public void getAllMoedasByNacionalIsEqualToSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where nacional equals to DEFAULT_NACIONAL
        defaultMoedaShouldBeFound("nacional.equals=" + DEFAULT_NACIONAL);

        // Get all the moedaList where nacional equals to UPDATED_NACIONAL
        defaultMoedaShouldNotBeFound("nacional.equals=" + UPDATED_NACIONAL);
    }

    @Test
    @Transactional
    public void getAllMoedasByNacionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where nacional not equals to DEFAULT_NACIONAL
        defaultMoedaShouldNotBeFound("nacional.notEquals=" + DEFAULT_NACIONAL);

        // Get all the moedaList where nacional not equals to UPDATED_NACIONAL
        defaultMoedaShouldBeFound("nacional.notEquals=" + UPDATED_NACIONAL);
    }

    @Test
    @Transactional
    public void getAllMoedasByNacionalIsInShouldWork() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where nacional in DEFAULT_NACIONAL or UPDATED_NACIONAL
        defaultMoedaShouldBeFound("nacional.in=" + DEFAULT_NACIONAL + "," + UPDATED_NACIONAL);

        // Get all the moedaList where nacional equals to UPDATED_NACIONAL
        defaultMoedaShouldNotBeFound("nacional.in=" + UPDATED_NACIONAL);
    }

    @Test
    @Transactional
    public void getAllMoedasByNacionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where nacional is not null
        defaultMoedaShouldBeFound("nacional.specified=true");

        // Get all the moedaList where nacional is null
        defaultMoedaShouldNotBeFound("nacional.specified=false");
    }

    @Test
    @Transactional
    public void getAllMoedasByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where icon equals to DEFAULT_ICON
        defaultMoedaShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the moedaList where icon equals to UPDATED_ICON
        defaultMoedaShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllMoedasByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where icon not equals to DEFAULT_ICON
        defaultMoedaShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the moedaList where icon not equals to UPDATED_ICON
        defaultMoedaShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllMoedasByIconIsInShouldWork() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultMoedaShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the moedaList where icon equals to UPDATED_ICON
        defaultMoedaShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllMoedasByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where icon is not null
        defaultMoedaShouldBeFound("icon.specified=true");

        // Get all the moedaList where icon is null
        defaultMoedaShouldNotBeFound("icon.specified=false");
    }
                @Test
    @Transactional
    public void getAllMoedasByIconContainsSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where icon contains DEFAULT_ICON
        defaultMoedaShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the moedaList where icon contains UPDATED_ICON
        defaultMoedaShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllMoedasByIconNotContainsSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList where icon does not contain DEFAULT_ICON
        defaultMoedaShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the moedaList where icon does not contain UPDATED_ICON
        defaultMoedaShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }


    @Test
    @Transactional
    public void getAllMoedasByDetalheLancamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);
        DetalheLancamento detalheLancamento = DetalheLancamentoResourceIT.createEntity(em);
        em.persist(detalheLancamento);
        em.flush();
        moeda.addDetalheLancamento(detalheLancamento);
        moedaRepository.saveAndFlush(moeda);
        Long detalheLancamentoId = detalheLancamento.getId();

        // Get all the moedaList where detalheLancamento equals to detalheLancamentoId
        defaultMoedaShouldBeFound("detalheLancamentoId.equals=" + detalheLancamentoId);

        // Get all the moedaList where detalheLancamento equals to detalheLancamentoId + 1
        defaultMoedaShouldNotBeFound("detalheLancamentoId.equals=" + (detalheLancamentoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMoedaShouldBeFound(String filter) throws Exception {
        restMoedaMockMvc.perform(get("/api/moedas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moeda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].nacional").value(hasItem(DEFAULT_NACIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));

        // Check, that the count call also returns 1
        restMoedaMockMvc.perform(get("/api/moedas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMoedaShouldNotBeFound(String filter) throws Exception {
        restMoedaMockMvc.perform(get("/api/moedas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMoedaMockMvc.perform(get("/api/moedas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMoeda() throws Exception {
        // Get the moeda
        restMoedaMockMvc.perform(get("/api/moedas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMoeda() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        int databaseSizeBeforeUpdate = moedaRepository.findAll().size();

        // Update the moeda
        Moeda updatedMoeda = moedaRepository.findById(moeda.getId()).get();
        // Disconnect from session so that the updates on updatedMoeda are not directly saved in db
        em.detach(updatedMoeda);
        updatedMoeda
            .nome(UPDATED_NOME)
            .codigo(UPDATED_CODIGO)
            .pais(UPDATED_PAIS)
            .nacional(UPDATED_NACIONAL)
            .icon(UPDATED_ICON);
        MoedaDTO moedaDTO = moedaMapper.toDto(updatedMoeda);

        restMoedaMockMvc.perform(put("/api/moedas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moedaDTO)))
            .andExpect(status().isOk());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeUpdate);
        Moeda testMoeda = moedaList.get(moedaList.size() - 1);
        assertThat(testMoeda.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMoeda.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testMoeda.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testMoeda.isNacional()).isEqualTo(UPDATED_NACIONAL);
        assertThat(testMoeda.getIcon()).isEqualTo(UPDATED_ICON);
    }

    @Test
    @Transactional
    public void updateNonExistingMoeda() throws Exception {
        int databaseSizeBeforeUpdate = moedaRepository.findAll().size();

        // Create the Moeda
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoedaMockMvc.perform(put("/api/moedas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moedaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMoeda() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        int databaseSizeBeforeDelete = moedaRepository.findAll().size();

        // Delete the moeda
        restMoedaMockMvc.perform(delete("/api/moedas/{id}", moeda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
