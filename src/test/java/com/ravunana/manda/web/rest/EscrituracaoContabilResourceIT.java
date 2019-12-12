package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.EscrituracaoContabil;
import com.ravunana.manda.domain.ContaDebito;
import com.ravunana.manda.domain.ContaCredito;
import com.ravunana.manda.domain.User;
import com.ravunana.manda.domain.Empresa;
import com.ravunana.manda.repository.EscrituracaoContabilRepository;
import com.ravunana.manda.service.EscrituracaoContabilService;
import com.ravunana.manda.service.dto.EscrituracaoContabilDTO;
import com.ravunana.manda.service.mapper.EscrituracaoContabilMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.EscrituracaoContabilCriteria;
import com.ravunana.manda.service.EscrituracaoContabilQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.sameInstant;
import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.manda.domain.enumeration.EntidadeSistema;
/**
 * Integration tests for the {@link EscrituracaoContabilResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class EscrituracaoContabilResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_HISTORICO = "AAAAAAAAAA";
    private static final String UPDATED_HISTORICO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(1);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(0 - 1);

    private static final String DEFAULT_REFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCIA = "BBBBBBBBBB";

    private static final EntidadeSistema DEFAULT_ENTIDADE_REFERENCIA = EntidadeSistema.SOFTWARE;
    private static final EntidadeSistema UPDATED_ENTIDADE_REFERENCIA = EntidadeSistema.LICENCA_SOFTWARE;

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_DOCUMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DOCUMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_DOCUMENTO = LocalDate.ofEpochDay(-1L);

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private EscrituracaoContabilRepository escrituracaoContabilRepository;

    @Autowired
    private EscrituracaoContabilMapper escrituracaoContabilMapper;

    @Autowired
    private EscrituracaoContabilService escrituracaoContabilService;

    @Autowired
    private EscrituracaoContabilQueryService escrituracaoContabilQueryService;

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

    private MockMvc restEscrituracaoContabilMockMvc;

    private EscrituracaoContabil escrituracaoContabil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EscrituracaoContabilResource escrituracaoContabilResource = new EscrituracaoContabilResource(escrituracaoContabilService, escrituracaoContabilQueryService);
        this.restEscrituracaoContabilMockMvc = MockMvcBuilders.standaloneSetup(escrituracaoContabilResource)
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
    public static EscrituracaoContabil createEntity(EntityManager em) {
        EscrituracaoContabil escrituracaoContabil = new EscrituracaoContabil()
            .numero(DEFAULT_NUMERO)
            .historico(DEFAULT_HISTORICO)
            .valor(DEFAULT_VALOR)
            .referencia(DEFAULT_REFERENCIA)
            .entidadeReferencia(DEFAULT_ENTIDADE_REFERENCIA)
            .tipo(DEFAULT_TIPO)
            .dataDocumento(DEFAULT_DATA_DOCUMENTO)
            .data(DEFAULT_DATA);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        escrituracaoContabil.setEmpresa(empresa);
        return escrituracaoContabil;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EscrituracaoContabil createUpdatedEntity(EntityManager em) {
        EscrituracaoContabil escrituracaoContabil = new EscrituracaoContabil()
            .numero(UPDATED_NUMERO)
            .historico(UPDATED_HISTORICO)
            .valor(UPDATED_VALOR)
            .referencia(UPDATED_REFERENCIA)
            .entidadeReferencia(UPDATED_ENTIDADE_REFERENCIA)
            .tipo(UPDATED_TIPO)
            .dataDocumento(UPDATED_DATA_DOCUMENTO)
            .data(UPDATED_DATA);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        escrituracaoContabil.setEmpresa(empresa);
        return escrituracaoContabil;
    }

    @BeforeEach
    public void initTest() {
        escrituracaoContabil = createEntity(em);
    }

    @Test
    @Transactional
    public void createEscrituracaoContabil() throws Exception {
        int databaseSizeBeforeCreate = escrituracaoContabilRepository.findAll().size();

        // Create the EscrituracaoContabil
        EscrituracaoContabilDTO escrituracaoContabilDTO = escrituracaoContabilMapper.toDto(escrituracaoContabil);
        restEscrituracaoContabilMockMvc.perform(post("/api/escrituracao-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escrituracaoContabilDTO)))
            .andExpect(status().isCreated());

        // Validate the EscrituracaoContabil in the database
        List<EscrituracaoContabil> escrituracaoContabilList = escrituracaoContabilRepository.findAll();
        assertThat(escrituracaoContabilList).hasSize(databaseSizeBeforeCreate + 1);
        EscrituracaoContabil testEscrituracaoContabil = escrituracaoContabilList.get(escrituracaoContabilList.size() - 1);
        assertThat(testEscrituracaoContabil.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEscrituracaoContabil.getHistorico()).isEqualTo(DEFAULT_HISTORICO);
        assertThat(testEscrituracaoContabil.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testEscrituracaoContabil.getReferencia()).isEqualTo(DEFAULT_REFERENCIA);
        assertThat(testEscrituracaoContabil.getEntidadeReferencia()).isEqualTo(DEFAULT_ENTIDADE_REFERENCIA);
        assertThat(testEscrituracaoContabil.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testEscrituracaoContabil.getDataDocumento()).isEqualTo(DEFAULT_DATA_DOCUMENTO);
        assertThat(testEscrituracaoContabil.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createEscrituracaoContabilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = escrituracaoContabilRepository.findAll().size();

        // Create the EscrituracaoContabil with an existing ID
        escrituracaoContabil.setId(1L);
        EscrituracaoContabilDTO escrituracaoContabilDTO = escrituracaoContabilMapper.toDto(escrituracaoContabil);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEscrituracaoContabilMockMvc.perform(post("/api/escrituracao-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escrituracaoContabilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EscrituracaoContabil in the database
        List<EscrituracaoContabil> escrituracaoContabilList = escrituracaoContabilRepository.findAll();
        assertThat(escrituracaoContabilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = escrituracaoContabilRepository.findAll().size();
        // set the field null
        escrituracaoContabil.setNumero(null);

        // Create the EscrituracaoContabil, which fails.
        EscrituracaoContabilDTO escrituracaoContabilDTO = escrituracaoContabilMapper.toDto(escrituracaoContabil);

        restEscrituracaoContabilMockMvc.perform(post("/api/escrituracao-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escrituracaoContabilDTO)))
            .andExpect(status().isBadRequest());

        List<EscrituracaoContabil> escrituracaoContabilList = escrituracaoContabilRepository.findAll();
        assertThat(escrituracaoContabilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = escrituracaoContabilRepository.findAll().size();
        // set the field null
        escrituracaoContabil.setValor(null);

        // Create the EscrituracaoContabil, which fails.
        EscrituracaoContabilDTO escrituracaoContabilDTO = escrituracaoContabilMapper.toDto(escrituracaoContabil);

        restEscrituracaoContabilMockMvc.perform(post("/api/escrituracao-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escrituracaoContabilDTO)))
            .andExpect(status().isBadRequest());

        List<EscrituracaoContabil> escrituracaoContabilList = escrituracaoContabilRepository.findAll();
        assertThat(escrituracaoContabilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = escrituracaoContabilRepository.findAll().size();
        // set the field null
        escrituracaoContabil.setTipo(null);

        // Create the EscrituracaoContabil, which fails.
        EscrituracaoContabilDTO escrituracaoContabilDTO = escrituracaoContabilMapper.toDto(escrituracaoContabil);

        restEscrituracaoContabilMockMvc.perform(post("/api/escrituracao-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escrituracaoContabilDTO)))
            .andExpect(status().isBadRequest());

        List<EscrituracaoContabil> escrituracaoContabilList = escrituracaoContabilRepository.findAll();
        assertThat(escrituracaoContabilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = escrituracaoContabilRepository.findAll().size();
        // set the field null
        escrituracaoContabil.setDataDocumento(null);

        // Create the EscrituracaoContabil, which fails.
        EscrituracaoContabilDTO escrituracaoContabilDTO = escrituracaoContabilMapper.toDto(escrituracaoContabil);

        restEscrituracaoContabilMockMvc.perform(post("/api/escrituracao-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escrituracaoContabilDTO)))
            .andExpect(status().isBadRequest());

        List<EscrituracaoContabil> escrituracaoContabilList = escrituracaoContabilRepository.findAll();
        assertThat(escrituracaoContabilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = escrituracaoContabilRepository.findAll().size();
        // set the field null
        escrituracaoContabil.setData(null);

        // Create the EscrituracaoContabil, which fails.
        EscrituracaoContabilDTO escrituracaoContabilDTO = escrituracaoContabilMapper.toDto(escrituracaoContabil);

        restEscrituracaoContabilMockMvc.perform(post("/api/escrituracao-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escrituracaoContabilDTO)))
            .andExpect(status().isBadRequest());

        List<EscrituracaoContabil> escrituracaoContabilList = escrituracaoContabilRepository.findAll();
        assertThat(escrituracaoContabilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabils() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList
        restEscrituracaoContabilMockMvc.perform(get("/api/escrituracao-contabils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escrituracaoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].historico").value(hasItem(DEFAULT_HISTORICO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].referencia").value(hasItem(DEFAULT_REFERENCIA)))
            .andExpect(jsonPath("$.[*].entidadeReferencia").value(hasItem(DEFAULT_ENTIDADE_REFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].dataDocumento").value(hasItem(DEFAULT_DATA_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getEscrituracaoContabil() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get the escrituracaoContabil
        restEscrituracaoContabilMockMvc.perform(get("/api/escrituracao-contabils/{id}", escrituracaoContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(escrituracaoContabil.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.historico").value(DEFAULT_HISTORICO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.referencia").value(DEFAULT_REFERENCIA))
            .andExpect(jsonPath("$.entidadeReferencia").value(DEFAULT_ENTIDADE_REFERENCIA.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.dataDocumento").value(DEFAULT_DATA_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)));
    }


    @Test
    @Transactional
    public void getEscrituracaoContabilsByIdFiltering() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        Long id = escrituracaoContabil.getId();

        defaultEscrituracaoContabilShouldBeFound("id.equals=" + id);
        defaultEscrituracaoContabilShouldNotBeFound("id.notEquals=" + id);

        defaultEscrituracaoContabilShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEscrituracaoContabilShouldNotBeFound("id.greaterThan=" + id);

        defaultEscrituracaoContabilShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEscrituracaoContabilShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where numero equals to DEFAULT_NUMERO
        defaultEscrituracaoContabilShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the escrituracaoContabilList where numero equals to UPDATED_NUMERO
        defaultEscrituracaoContabilShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where numero not equals to DEFAULT_NUMERO
        defaultEscrituracaoContabilShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the escrituracaoContabilList where numero not equals to UPDATED_NUMERO
        defaultEscrituracaoContabilShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultEscrituracaoContabilShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the escrituracaoContabilList where numero equals to UPDATED_NUMERO
        defaultEscrituracaoContabilShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where numero is not null
        defaultEscrituracaoContabilShouldBeFound("numero.specified=true");

        // Get all the escrituracaoContabilList where numero is null
        defaultEscrituracaoContabilShouldNotBeFound("numero.specified=false");
    }
                @Test
    @Transactional
    public void getAllEscrituracaoContabilsByNumeroContainsSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where numero contains DEFAULT_NUMERO
        defaultEscrituracaoContabilShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the escrituracaoContabilList where numero contains UPDATED_NUMERO
        defaultEscrituracaoContabilShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where numero does not contain DEFAULT_NUMERO
        defaultEscrituracaoContabilShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the escrituracaoContabilList where numero does not contain UPDATED_NUMERO
        defaultEscrituracaoContabilShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }


    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where valor equals to DEFAULT_VALOR
        defaultEscrituracaoContabilShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the escrituracaoContabilList where valor equals to UPDATED_VALOR
        defaultEscrituracaoContabilShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where valor not equals to DEFAULT_VALOR
        defaultEscrituracaoContabilShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the escrituracaoContabilList where valor not equals to UPDATED_VALOR
        defaultEscrituracaoContabilShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByValorIsInShouldWork() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultEscrituracaoContabilShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the escrituracaoContabilList where valor equals to UPDATED_VALOR
        defaultEscrituracaoContabilShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where valor is not null
        defaultEscrituracaoContabilShouldBeFound("valor.specified=true");

        // Get all the escrituracaoContabilList where valor is null
        defaultEscrituracaoContabilShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where valor is greater than or equal to DEFAULT_VALOR
        defaultEscrituracaoContabilShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the escrituracaoContabilList where valor is greater than or equal to UPDATED_VALOR
        defaultEscrituracaoContabilShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where valor is less than or equal to DEFAULT_VALOR
        defaultEscrituracaoContabilShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the escrituracaoContabilList where valor is less than or equal to SMALLER_VALOR
        defaultEscrituracaoContabilShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where valor is less than DEFAULT_VALOR
        defaultEscrituracaoContabilShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the escrituracaoContabilList where valor is less than UPDATED_VALOR
        defaultEscrituracaoContabilShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where valor is greater than DEFAULT_VALOR
        defaultEscrituracaoContabilShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the escrituracaoContabilList where valor is greater than SMALLER_VALOR
        defaultEscrituracaoContabilShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByReferenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where referencia equals to DEFAULT_REFERENCIA
        defaultEscrituracaoContabilShouldBeFound("referencia.equals=" + DEFAULT_REFERENCIA);

        // Get all the escrituracaoContabilList where referencia equals to UPDATED_REFERENCIA
        defaultEscrituracaoContabilShouldNotBeFound("referencia.equals=" + UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByReferenciaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where referencia not equals to DEFAULT_REFERENCIA
        defaultEscrituracaoContabilShouldNotBeFound("referencia.notEquals=" + DEFAULT_REFERENCIA);

        // Get all the escrituracaoContabilList where referencia not equals to UPDATED_REFERENCIA
        defaultEscrituracaoContabilShouldBeFound("referencia.notEquals=" + UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByReferenciaIsInShouldWork() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where referencia in DEFAULT_REFERENCIA or UPDATED_REFERENCIA
        defaultEscrituracaoContabilShouldBeFound("referencia.in=" + DEFAULT_REFERENCIA + "," + UPDATED_REFERENCIA);

        // Get all the escrituracaoContabilList where referencia equals to UPDATED_REFERENCIA
        defaultEscrituracaoContabilShouldNotBeFound("referencia.in=" + UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByReferenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where referencia is not null
        defaultEscrituracaoContabilShouldBeFound("referencia.specified=true");

        // Get all the escrituracaoContabilList where referencia is null
        defaultEscrituracaoContabilShouldNotBeFound("referencia.specified=false");
    }
                @Test
    @Transactional
    public void getAllEscrituracaoContabilsByReferenciaContainsSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where referencia contains DEFAULT_REFERENCIA
        defaultEscrituracaoContabilShouldBeFound("referencia.contains=" + DEFAULT_REFERENCIA);

        // Get all the escrituracaoContabilList where referencia contains UPDATED_REFERENCIA
        defaultEscrituracaoContabilShouldNotBeFound("referencia.contains=" + UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByReferenciaNotContainsSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where referencia does not contain DEFAULT_REFERENCIA
        defaultEscrituracaoContabilShouldNotBeFound("referencia.doesNotContain=" + DEFAULT_REFERENCIA);

        // Get all the escrituracaoContabilList where referencia does not contain UPDATED_REFERENCIA
        defaultEscrituracaoContabilShouldBeFound("referencia.doesNotContain=" + UPDATED_REFERENCIA);
    }


    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByEntidadeReferenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where entidadeReferencia equals to DEFAULT_ENTIDADE_REFERENCIA
        defaultEscrituracaoContabilShouldBeFound("entidadeReferencia.equals=" + DEFAULT_ENTIDADE_REFERENCIA);

        // Get all the escrituracaoContabilList where entidadeReferencia equals to UPDATED_ENTIDADE_REFERENCIA
        defaultEscrituracaoContabilShouldNotBeFound("entidadeReferencia.equals=" + UPDATED_ENTIDADE_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByEntidadeReferenciaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where entidadeReferencia not equals to DEFAULT_ENTIDADE_REFERENCIA
        defaultEscrituracaoContabilShouldNotBeFound("entidadeReferencia.notEquals=" + DEFAULT_ENTIDADE_REFERENCIA);

        // Get all the escrituracaoContabilList where entidadeReferencia not equals to UPDATED_ENTIDADE_REFERENCIA
        defaultEscrituracaoContabilShouldBeFound("entidadeReferencia.notEquals=" + UPDATED_ENTIDADE_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByEntidadeReferenciaIsInShouldWork() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where entidadeReferencia in DEFAULT_ENTIDADE_REFERENCIA or UPDATED_ENTIDADE_REFERENCIA
        defaultEscrituracaoContabilShouldBeFound("entidadeReferencia.in=" + DEFAULT_ENTIDADE_REFERENCIA + "," + UPDATED_ENTIDADE_REFERENCIA);

        // Get all the escrituracaoContabilList where entidadeReferencia equals to UPDATED_ENTIDADE_REFERENCIA
        defaultEscrituracaoContabilShouldNotBeFound("entidadeReferencia.in=" + UPDATED_ENTIDADE_REFERENCIA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByEntidadeReferenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where entidadeReferencia is not null
        defaultEscrituracaoContabilShouldBeFound("entidadeReferencia.specified=true");

        // Get all the escrituracaoContabilList where entidadeReferencia is null
        defaultEscrituracaoContabilShouldNotBeFound("entidadeReferencia.specified=false");
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where tipo equals to DEFAULT_TIPO
        defaultEscrituracaoContabilShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the escrituracaoContabilList where tipo equals to UPDATED_TIPO
        defaultEscrituracaoContabilShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where tipo not equals to DEFAULT_TIPO
        defaultEscrituracaoContabilShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the escrituracaoContabilList where tipo not equals to UPDATED_TIPO
        defaultEscrituracaoContabilShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultEscrituracaoContabilShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the escrituracaoContabilList where tipo equals to UPDATED_TIPO
        defaultEscrituracaoContabilShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where tipo is not null
        defaultEscrituracaoContabilShouldBeFound("tipo.specified=true");

        // Get all the escrituracaoContabilList where tipo is null
        defaultEscrituracaoContabilShouldNotBeFound("tipo.specified=false");
    }
                @Test
    @Transactional
    public void getAllEscrituracaoContabilsByTipoContainsSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where tipo contains DEFAULT_TIPO
        defaultEscrituracaoContabilShouldBeFound("tipo.contains=" + DEFAULT_TIPO);

        // Get all the escrituracaoContabilList where tipo contains UPDATED_TIPO
        defaultEscrituracaoContabilShouldNotBeFound("tipo.contains=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByTipoNotContainsSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where tipo does not contain DEFAULT_TIPO
        defaultEscrituracaoContabilShouldNotBeFound("tipo.doesNotContain=" + DEFAULT_TIPO);

        // Get all the escrituracaoContabilList where tipo does not contain UPDATED_TIPO
        defaultEscrituracaoContabilShouldBeFound("tipo.doesNotContain=" + UPDATED_TIPO);
    }


    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where dataDocumento equals to DEFAULT_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldBeFound("dataDocumento.equals=" + DEFAULT_DATA_DOCUMENTO);

        // Get all the escrituracaoContabilList where dataDocumento equals to UPDATED_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldNotBeFound("dataDocumento.equals=" + UPDATED_DATA_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataDocumentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where dataDocumento not equals to DEFAULT_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldNotBeFound("dataDocumento.notEquals=" + DEFAULT_DATA_DOCUMENTO);

        // Get all the escrituracaoContabilList where dataDocumento not equals to UPDATED_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldBeFound("dataDocumento.notEquals=" + UPDATED_DATA_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where dataDocumento in DEFAULT_DATA_DOCUMENTO or UPDATED_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldBeFound("dataDocumento.in=" + DEFAULT_DATA_DOCUMENTO + "," + UPDATED_DATA_DOCUMENTO);

        // Get all the escrituracaoContabilList where dataDocumento equals to UPDATED_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldNotBeFound("dataDocumento.in=" + UPDATED_DATA_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where dataDocumento is not null
        defaultEscrituracaoContabilShouldBeFound("dataDocumento.specified=true");

        // Get all the escrituracaoContabilList where dataDocumento is null
        defaultEscrituracaoContabilShouldNotBeFound("dataDocumento.specified=false");
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataDocumentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where dataDocumento is greater than or equal to DEFAULT_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldBeFound("dataDocumento.greaterThanOrEqual=" + DEFAULT_DATA_DOCUMENTO);

        // Get all the escrituracaoContabilList where dataDocumento is greater than or equal to UPDATED_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldNotBeFound("dataDocumento.greaterThanOrEqual=" + UPDATED_DATA_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataDocumentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where dataDocumento is less than or equal to DEFAULT_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldBeFound("dataDocumento.lessThanOrEqual=" + DEFAULT_DATA_DOCUMENTO);

        // Get all the escrituracaoContabilList where dataDocumento is less than or equal to SMALLER_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldNotBeFound("dataDocumento.lessThanOrEqual=" + SMALLER_DATA_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataDocumentoIsLessThanSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where dataDocumento is less than DEFAULT_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldNotBeFound("dataDocumento.lessThan=" + DEFAULT_DATA_DOCUMENTO);

        // Get all the escrituracaoContabilList where dataDocumento is less than UPDATED_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldBeFound("dataDocumento.lessThan=" + UPDATED_DATA_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataDocumentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where dataDocumento is greater than DEFAULT_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldNotBeFound("dataDocumento.greaterThan=" + DEFAULT_DATA_DOCUMENTO);

        // Get all the escrituracaoContabilList where dataDocumento is greater than SMALLER_DATA_DOCUMENTO
        defaultEscrituracaoContabilShouldBeFound("dataDocumento.greaterThan=" + SMALLER_DATA_DOCUMENTO);
    }


    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where data equals to DEFAULT_DATA
        defaultEscrituracaoContabilShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the escrituracaoContabilList where data equals to UPDATED_DATA
        defaultEscrituracaoContabilShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where data not equals to DEFAULT_DATA
        defaultEscrituracaoContabilShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the escrituracaoContabilList where data not equals to UPDATED_DATA
        defaultEscrituracaoContabilShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataIsInShouldWork() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where data in DEFAULT_DATA or UPDATED_DATA
        defaultEscrituracaoContabilShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the escrituracaoContabilList where data equals to UPDATED_DATA
        defaultEscrituracaoContabilShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where data is not null
        defaultEscrituracaoContabilShouldBeFound("data.specified=true");

        // Get all the escrituracaoContabilList where data is null
        defaultEscrituracaoContabilShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where data is greater than or equal to DEFAULT_DATA
        defaultEscrituracaoContabilShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the escrituracaoContabilList where data is greater than or equal to UPDATED_DATA
        defaultEscrituracaoContabilShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where data is less than or equal to DEFAULT_DATA
        defaultEscrituracaoContabilShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the escrituracaoContabilList where data is less than or equal to SMALLER_DATA
        defaultEscrituracaoContabilShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where data is less than DEFAULT_DATA
        defaultEscrituracaoContabilShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the escrituracaoContabilList where data is less than UPDATED_DATA
        defaultEscrituracaoContabilShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        // Get all the escrituracaoContabilList where data is greater than DEFAULT_DATA
        defaultEscrituracaoContabilShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the escrituracaoContabilList where data is greater than SMALLER_DATA
        defaultEscrituracaoContabilShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByContaDebitoIsEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);
        ContaDebito contaDebito = ContaDebitoResourceIT.createEntity(em);
        em.persist(contaDebito);
        em.flush();
        escrituracaoContabil.addContaDebito(contaDebito);
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);
        Long contaDebitoId = contaDebito.getId();

        // Get all the escrituracaoContabilList where contaDebito equals to contaDebitoId
        defaultEscrituracaoContabilShouldBeFound("contaDebitoId.equals=" + contaDebitoId);

        // Get all the escrituracaoContabilList where contaDebito equals to contaDebitoId + 1
        defaultEscrituracaoContabilShouldNotBeFound("contaDebitoId.equals=" + (contaDebitoId + 1));
    }


    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByContaCreditoIsEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);
        ContaCredito contaCredito = ContaCreditoResourceIT.createEntity(em);
        em.persist(contaCredito);
        em.flush();
        escrituracaoContabil.addContaCredito(contaCredito);
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);
        Long contaCreditoId = contaCredito.getId();

        // Get all the escrituracaoContabilList where contaCredito equals to contaCreditoId
        defaultEscrituracaoContabilShouldBeFound("contaCreditoId.equals=" + contaCreditoId);

        // Get all the escrituracaoContabilList where contaCredito equals to contaCreditoId + 1
        defaultEscrituracaoContabilShouldNotBeFound("contaCreditoId.equals=" + (contaCreditoId + 1));
    }


    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByUtilizadorIsEqualToSomething() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);
        User utilizador = UserResourceIT.createEntity(em);
        em.persist(utilizador);
        em.flush();
        escrituracaoContabil.setUtilizador(utilizador);
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);
        Long utilizadorId = utilizador.getId();

        // Get all the escrituracaoContabilList where utilizador equals to utilizadorId
        defaultEscrituracaoContabilShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the escrituracaoContabilList where utilizador equals to utilizadorId + 1
        defaultEscrituracaoContabilShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }


    @Test
    @Transactional
    public void getAllEscrituracaoContabilsByEmpresaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Empresa empresa = escrituracaoContabil.getEmpresa();
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);
        Long empresaId = empresa.getId();

        // Get all the escrituracaoContabilList where empresa equals to empresaId
        defaultEscrituracaoContabilShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the escrituracaoContabilList where empresa equals to empresaId + 1
        defaultEscrituracaoContabilShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEscrituracaoContabilShouldBeFound(String filter) throws Exception {
        restEscrituracaoContabilMockMvc.perform(get("/api/escrituracao-contabils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escrituracaoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].historico").value(hasItem(DEFAULT_HISTORICO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].referencia").value(hasItem(DEFAULT_REFERENCIA)))
            .andExpect(jsonPath("$.[*].entidadeReferencia").value(hasItem(DEFAULT_ENTIDADE_REFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].dataDocumento").value(hasItem(DEFAULT_DATA_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));

        // Check, that the count call also returns 1
        restEscrituracaoContabilMockMvc.perform(get("/api/escrituracao-contabils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEscrituracaoContabilShouldNotBeFound(String filter) throws Exception {
        restEscrituracaoContabilMockMvc.perform(get("/api/escrituracao-contabils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEscrituracaoContabilMockMvc.perform(get("/api/escrituracao-contabils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEscrituracaoContabil() throws Exception {
        // Get the escrituracaoContabil
        restEscrituracaoContabilMockMvc.perform(get("/api/escrituracao-contabils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEscrituracaoContabil() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        int databaseSizeBeforeUpdate = escrituracaoContabilRepository.findAll().size();

        // Update the escrituracaoContabil
        EscrituracaoContabil updatedEscrituracaoContabil = escrituracaoContabilRepository.findById(escrituracaoContabil.getId()).get();
        // Disconnect from session so that the updates on updatedEscrituracaoContabil are not directly saved in db
        em.detach(updatedEscrituracaoContabil);
        updatedEscrituracaoContabil
            .numero(UPDATED_NUMERO)
            .historico(UPDATED_HISTORICO)
            .valor(UPDATED_VALOR)
            .referencia(UPDATED_REFERENCIA)
            .entidadeReferencia(UPDATED_ENTIDADE_REFERENCIA)
            .tipo(UPDATED_TIPO)
            .dataDocumento(UPDATED_DATA_DOCUMENTO)
            .data(UPDATED_DATA);
        EscrituracaoContabilDTO escrituracaoContabilDTO = escrituracaoContabilMapper.toDto(updatedEscrituracaoContabil);

        restEscrituracaoContabilMockMvc.perform(put("/api/escrituracao-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escrituracaoContabilDTO)))
            .andExpect(status().isOk());

        // Validate the EscrituracaoContabil in the database
        List<EscrituracaoContabil> escrituracaoContabilList = escrituracaoContabilRepository.findAll();
        assertThat(escrituracaoContabilList).hasSize(databaseSizeBeforeUpdate);
        EscrituracaoContabil testEscrituracaoContabil = escrituracaoContabilList.get(escrituracaoContabilList.size() - 1);
        assertThat(testEscrituracaoContabil.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEscrituracaoContabil.getHistorico()).isEqualTo(UPDATED_HISTORICO);
        assertThat(testEscrituracaoContabil.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testEscrituracaoContabil.getReferencia()).isEqualTo(UPDATED_REFERENCIA);
        assertThat(testEscrituracaoContabil.getEntidadeReferencia()).isEqualTo(UPDATED_ENTIDADE_REFERENCIA);
        assertThat(testEscrituracaoContabil.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testEscrituracaoContabil.getDataDocumento()).isEqualTo(UPDATED_DATA_DOCUMENTO);
        assertThat(testEscrituracaoContabil.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingEscrituracaoContabil() throws Exception {
        int databaseSizeBeforeUpdate = escrituracaoContabilRepository.findAll().size();

        // Create the EscrituracaoContabil
        EscrituracaoContabilDTO escrituracaoContabilDTO = escrituracaoContabilMapper.toDto(escrituracaoContabil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEscrituracaoContabilMockMvc.perform(put("/api/escrituracao-contabils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escrituracaoContabilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EscrituracaoContabil in the database
        List<EscrituracaoContabil> escrituracaoContabilList = escrituracaoContabilRepository.findAll();
        assertThat(escrituracaoContabilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEscrituracaoContabil() throws Exception {
        // Initialize the database
        escrituracaoContabilRepository.saveAndFlush(escrituracaoContabil);

        int databaseSizeBeforeDelete = escrituracaoContabilRepository.findAll().size();

        // Delete the escrituracaoContabil
        restEscrituracaoContabilMockMvc.perform(delete("/api/escrituracao-contabils/{id}", escrituracaoContabil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EscrituracaoContabil> escrituracaoContabilList = escrituracaoContabilRepository.findAll();
        assertThat(escrituracaoContabilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
