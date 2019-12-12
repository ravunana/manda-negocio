package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.Empresa;
import com.ravunana.manda.domain.EscrituracaoContabil;
import com.ravunana.manda.domain.Empresa;
import com.ravunana.manda.domain.LocalizacaoEmpresa;
import com.ravunana.manda.domain.ContactoEmpresa;
import com.ravunana.manda.domain.LicencaSoftware;
import com.ravunana.manda.domain.LancamentoFinanceiro;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.domain.LocalArmazenamento;
import com.ravunana.manda.domain.Compra;
import com.ravunana.manda.domain.Venda;
import com.ravunana.manda.domain.User;
import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.domain.CoordenadaBancaria;
import com.ravunana.manda.repository.EmpresaRepository;
import com.ravunana.manda.service.EmpresaService;
import com.ravunana.manda.service.dto.EmpresaDTO;
import com.ravunana.manda.service.mapper.EmpresaMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.EmpresaCriteria;
import com.ravunana.manda.service.EmpresaQueryService;

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
import java.time.ZoneId;
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmpresaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class EmpresaResourceIT {

    private static final String DEFAULT_TIPO_SOCIEDADE = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SOCIEDADE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGOTIPO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGOTIPO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGOTIPO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGOTIPO_CONTENT_TYPE = "image/png";

    private static final BigDecimal DEFAULT_CAPITAL_SOCIAL = new BigDecimal(0);
    private static final BigDecimal UPDATED_CAPITAL_SOCIAL = new BigDecimal(1);
    private static final BigDecimal SMALLER_CAPITAL_SOCIAL = new BigDecimal(0 - 1);

    private static final LocalDate DEFAULT_FUNDACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FUNDACAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FUNDACAO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NIF = "AAAAAAAAAA";
    private static final String UPDATED_NIF = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_REGISTRO_COMERCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_REGISTRO_COMERCIAL = "BBBBBBBBBB";

    private static final Double DEFAULT_DESPESA_FIXA = 0D;
    private static final Double UPDATED_DESPESA_FIXA = 1D;
    private static final Double SMALLER_DESPESA_FIXA = 0D - 1D;

    private static final Double DEFAULT_DESPESA_VARIAVEL = 0D;
    private static final Double UPDATED_DESPESA_VARIAVEL = 1D;
    private static final Double SMALLER_DESPESA_VARIAVEL = 0D - 1D;

    private static final LocalDate DEFAULT_ABERTURA_EXERCIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ABERTURA_EXERCIO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ABERTURA_EXERCIO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DESIGNACAO_COMERCIAL = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNACAO_COMERCIAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SEDE = false;
    private static final Boolean UPDATED_SEDE = true;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private EmpresaQueryService empresaQueryService;

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

    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpresaResource empresaResource = new EmpresaResource(empresaService, empresaQueryService);
        this.restEmpresaMockMvc = MockMvcBuilders.standaloneSetup(empresaResource)
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
    public static Empresa createEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .tipoSociedade(DEFAULT_TIPO_SOCIEDADE)
            .nome(DEFAULT_NOME)
            .logotipo(DEFAULT_LOGOTIPO)
            .logotipoContentType(DEFAULT_LOGOTIPO_CONTENT_TYPE)
            .capitalSocial(DEFAULT_CAPITAL_SOCIAL)
            .fundacao(DEFAULT_FUNDACAO)
            .nif(DEFAULT_NIF)
            .numeroRegistroComercial(DEFAULT_NUMERO_REGISTRO_COMERCIAL)
            .despesaFixa(DEFAULT_DESPESA_FIXA)
            .despesaVariavel(DEFAULT_DESPESA_VARIAVEL)
            .aberturaExercio(DEFAULT_ABERTURA_EXERCIO)
            .designacaoComercial(DEFAULT_DESIGNACAO_COMERCIAL)
            .sede(DEFAULT_SEDE);
        return empresa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createUpdatedEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .tipoSociedade(UPDATED_TIPO_SOCIEDADE)
            .nome(UPDATED_NOME)
            .logotipo(UPDATED_LOGOTIPO)
            .logotipoContentType(UPDATED_LOGOTIPO_CONTENT_TYPE)
            .capitalSocial(UPDATED_CAPITAL_SOCIAL)
            .fundacao(UPDATED_FUNDACAO)
            .nif(UPDATED_NIF)
            .numeroRegistroComercial(UPDATED_NUMERO_REGISTRO_COMERCIAL)
            .despesaFixa(UPDATED_DESPESA_FIXA)
            .despesaVariavel(UPDATED_DESPESA_VARIAVEL)
            .aberturaExercio(UPDATED_ABERTURA_EXERCIO)
            .designacaoComercial(UPDATED_DESIGNACAO_COMERCIAL)
            .sede(UPDATED_SEDE);
        return empresa;
    }

    @BeforeEach
    public void initTest() {
        empresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpresa() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isCreated());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate + 1);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getTipoSociedade()).isEqualTo(DEFAULT_TIPO_SOCIEDADE);
        assertThat(testEmpresa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEmpresa.getLogotipo()).isEqualTo(DEFAULT_LOGOTIPO);
        assertThat(testEmpresa.getLogotipoContentType()).isEqualTo(DEFAULT_LOGOTIPO_CONTENT_TYPE);
        assertThat(testEmpresa.getCapitalSocial()).isEqualTo(DEFAULT_CAPITAL_SOCIAL);
        assertThat(testEmpresa.getFundacao()).isEqualTo(DEFAULT_FUNDACAO);
        assertThat(testEmpresa.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testEmpresa.getNumeroRegistroComercial()).isEqualTo(DEFAULT_NUMERO_REGISTRO_COMERCIAL);
        assertThat(testEmpresa.getDespesaFixa()).isEqualTo(DEFAULT_DESPESA_FIXA);
        assertThat(testEmpresa.getDespesaVariavel()).isEqualTo(DEFAULT_DESPESA_VARIAVEL);
        assertThat(testEmpresa.getAberturaExercio()).isEqualTo(DEFAULT_ABERTURA_EXERCIO);
        assertThat(testEmpresa.getDesignacaoComercial()).isEqualTo(DEFAULT_DESIGNACAO_COMERCIAL);
        assertThat(testEmpresa.isSede()).isEqualTo(DEFAULT_SEDE);
    }

    @Test
    @Transactional
    public void createEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // Create the Empresa with an existing ID
        empresa.setId(1L);
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setNome(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNifIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setNif(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroRegistroComercialIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setNumeroRegistroComercial(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDespesaFixaIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setDespesaFixa(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDespesaVariavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setDespesaVariavel(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmpresas() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoSociedade").value(hasItem(DEFAULT_TIPO_SOCIEDADE)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].logotipoContentType").value(hasItem(DEFAULT_LOGOTIPO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logotipo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGOTIPO))))
            .andExpect(jsonPath("$.[*].capitalSocial").value(hasItem(DEFAULT_CAPITAL_SOCIAL.intValue())))
            .andExpect(jsonPath("$.[*].fundacao").value(hasItem(DEFAULT_FUNDACAO.toString())))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].numeroRegistroComercial").value(hasItem(DEFAULT_NUMERO_REGISTRO_COMERCIAL)))
            .andExpect(jsonPath("$.[*].despesaFixa").value(hasItem(DEFAULT_DESPESA_FIXA.doubleValue())))
            .andExpect(jsonPath("$.[*].despesaVariavel").value(hasItem(DEFAULT_DESPESA_VARIAVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].aberturaExercio").value(hasItem(DEFAULT_ABERTURA_EXERCIO.toString())))
            .andExpect(jsonPath("$.[*].designacaoComercial").value(hasItem(DEFAULT_DESIGNACAO_COMERCIAL.toString())))
            .andExpect(jsonPath("$.[*].sede").value(hasItem(DEFAULT_SEDE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.tipoSociedade").value(DEFAULT_TIPO_SOCIEDADE))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.logotipoContentType").value(DEFAULT_LOGOTIPO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logotipo").value(Base64Utils.encodeToString(DEFAULT_LOGOTIPO)))
            .andExpect(jsonPath("$.capitalSocial").value(DEFAULT_CAPITAL_SOCIAL.intValue()))
            .andExpect(jsonPath("$.fundacao").value(DEFAULT_FUNDACAO.toString()))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.numeroRegistroComercial").value(DEFAULT_NUMERO_REGISTRO_COMERCIAL))
            .andExpect(jsonPath("$.despesaFixa").value(DEFAULT_DESPESA_FIXA.doubleValue()))
            .andExpect(jsonPath("$.despesaVariavel").value(DEFAULT_DESPESA_VARIAVEL.doubleValue()))
            .andExpect(jsonPath("$.aberturaExercio").value(DEFAULT_ABERTURA_EXERCIO.toString()))
            .andExpect(jsonPath("$.designacaoComercial").value(DEFAULT_DESIGNACAO_COMERCIAL.toString()))
            .andExpect(jsonPath("$.sede").value(DEFAULT_SEDE.booleanValue()));
    }


    @Test
    @Transactional
    public void getEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        Long id = empresa.getId();

        defaultEmpresaShouldBeFound("id.equals=" + id);
        defaultEmpresaShouldNotBeFound("id.notEquals=" + id);

        defaultEmpresaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmpresaShouldNotBeFound("id.greaterThan=" + id);

        defaultEmpresaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmpresaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmpresasByTipoSociedadeIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where tipoSociedade equals to DEFAULT_TIPO_SOCIEDADE
        defaultEmpresaShouldBeFound("tipoSociedade.equals=" + DEFAULT_TIPO_SOCIEDADE);

        // Get all the empresaList where tipoSociedade equals to UPDATED_TIPO_SOCIEDADE
        defaultEmpresaShouldNotBeFound("tipoSociedade.equals=" + UPDATED_TIPO_SOCIEDADE);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTipoSociedadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where tipoSociedade not equals to DEFAULT_TIPO_SOCIEDADE
        defaultEmpresaShouldNotBeFound("tipoSociedade.notEquals=" + DEFAULT_TIPO_SOCIEDADE);

        // Get all the empresaList where tipoSociedade not equals to UPDATED_TIPO_SOCIEDADE
        defaultEmpresaShouldBeFound("tipoSociedade.notEquals=" + UPDATED_TIPO_SOCIEDADE);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTipoSociedadeIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where tipoSociedade in DEFAULT_TIPO_SOCIEDADE or UPDATED_TIPO_SOCIEDADE
        defaultEmpresaShouldBeFound("tipoSociedade.in=" + DEFAULT_TIPO_SOCIEDADE + "," + UPDATED_TIPO_SOCIEDADE);

        // Get all the empresaList where tipoSociedade equals to UPDATED_TIPO_SOCIEDADE
        defaultEmpresaShouldNotBeFound("tipoSociedade.in=" + UPDATED_TIPO_SOCIEDADE);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTipoSociedadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where tipoSociedade is not null
        defaultEmpresaShouldBeFound("tipoSociedade.specified=true");

        // Get all the empresaList where tipoSociedade is null
        defaultEmpresaShouldNotBeFound("tipoSociedade.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByTipoSociedadeContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where tipoSociedade contains DEFAULT_TIPO_SOCIEDADE
        defaultEmpresaShouldBeFound("tipoSociedade.contains=" + DEFAULT_TIPO_SOCIEDADE);

        // Get all the empresaList where tipoSociedade contains UPDATED_TIPO_SOCIEDADE
        defaultEmpresaShouldNotBeFound("tipoSociedade.contains=" + UPDATED_TIPO_SOCIEDADE);
    }

    @Test
    @Transactional
    public void getAllEmpresasByTipoSociedadeNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where tipoSociedade does not contain DEFAULT_TIPO_SOCIEDADE
        defaultEmpresaShouldNotBeFound("tipoSociedade.doesNotContain=" + DEFAULT_TIPO_SOCIEDADE);

        // Get all the empresaList where tipoSociedade does not contain UPDATED_TIPO_SOCIEDADE
        defaultEmpresaShouldBeFound("tipoSociedade.doesNotContain=" + UPDATED_TIPO_SOCIEDADE);
    }


    @Test
    @Transactional
    public void getAllEmpresasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nome equals to DEFAULT_NOME
        defaultEmpresaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the empresaList where nome equals to UPDATED_NOME
        defaultEmpresaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nome not equals to DEFAULT_NOME
        defaultEmpresaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the empresaList where nome not equals to UPDATED_NOME
        defaultEmpresaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultEmpresaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the empresaList where nome equals to UPDATED_NOME
        defaultEmpresaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nome is not null
        defaultEmpresaShouldBeFound("nome.specified=true");

        // Get all the empresaList where nome is null
        defaultEmpresaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByNomeContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nome contains DEFAULT_NOME
        defaultEmpresaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the empresaList where nome contains UPDATED_NOME
        defaultEmpresaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nome does not contain DEFAULT_NOME
        defaultEmpresaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the empresaList where nome does not contain UPDATED_NOME
        defaultEmpresaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllEmpresasByCapitalSocialIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial equals to DEFAULT_CAPITAL_SOCIAL
        defaultEmpresaShouldBeFound("capitalSocial.equals=" + DEFAULT_CAPITAL_SOCIAL);

        // Get all the empresaList where capitalSocial equals to UPDATED_CAPITAL_SOCIAL
        defaultEmpresaShouldNotBeFound("capitalSocial.equals=" + UPDATED_CAPITAL_SOCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCapitalSocialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial not equals to DEFAULT_CAPITAL_SOCIAL
        defaultEmpresaShouldNotBeFound("capitalSocial.notEquals=" + DEFAULT_CAPITAL_SOCIAL);

        // Get all the empresaList where capitalSocial not equals to UPDATED_CAPITAL_SOCIAL
        defaultEmpresaShouldBeFound("capitalSocial.notEquals=" + UPDATED_CAPITAL_SOCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCapitalSocialIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial in DEFAULT_CAPITAL_SOCIAL or UPDATED_CAPITAL_SOCIAL
        defaultEmpresaShouldBeFound("capitalSocial.in=" + DEFAULT_CAPITAL_SOCIAL + "," + UPDATED_CAPITAL_SOCIAL);

        // Get all the empresaList where capitalSocial equals to UPDATED_CAPITAL_SOCIAL
        defaultEmpresaShouldNotBeFound("capitalSocial.in=" + UPDATED_CAPITAL_SOCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCapitalSocialIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial is not null
        defaultEmpresaShouldBeFound("capitalSocial.specified=true");

        // Get all the empresaList where capitalSocial is null
        defaultEmpresaShouldNotBeFound("capitalSocial.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByCapitalSocialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial is greater than or equal to DEFAULT_CAPITAL_SOCIAL
        defaultEmpresaShouldBeFound("capitalSocial.greaterThanOrEqual=" + DEFAULT_CAPITAL_SOCIAL);

        // Get all the empresaList where capitalSocial is greater than or equal to UPDATED_CAPITAL_SOCIAL
        defaultEmpresaShouldNotBeFound("capitalSocial.greaterThanOrEqual=" + UPDATED_CAPITAL_SOCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCapitalSocialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial is less than or equal to DEFAULT_CAPITAL_SOCIAL
        defaultEmpresaShouldBeFound("capitalSocial.lessThanOrEqual=" + DEFAULT_CAPITAL_SOCIAL);

        // Get all the empresaList where capitalSocial is less than or equal to SMALLER_CAPITAL_SOCIAL
        defaultEmpresaShouldNotBeFound("capitalSocial.lessThanOrEqual=" + SMALLER_CAPITAL_SOCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCapitalSocialIsLessThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial is less than DEFAULT_CAPITAL_SOCIAL
        defaultEmpresaShouldNotBeFound("capitalSocial.lessThan=" + DEFAULT_CAPITAL_SOCIAL);

        // Get all the empresaList where capitalSocial is less than UPDATED_CAPITAL_SOCIAL
        defaultEmpresaShouldBeFound("capitalSocial.lessThan=" + UPDATED_CAPITAL_SOCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByCapitalSocialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial is greater than DEFAULT_CAPITAL_SOCIAL
        defaultEmpresaShouldNotBeFound("capitalSocial.greaterThan=" + DEFAULT_CAPITAL_SOCIAL);

        // Get all the empresaList where capitalSocial is greater than SMALLER_CAPITAL_SOCIAL
        defaultEmpresaShouldBeFound("capitalSocial.greaterThan=" + SMALLER_CAPITAL_SOCIAL);
    }


    @Test
    @Transactional
    public void getAllEmpresasByFundacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fundacao equals to DEFAULT_FUNDACAO
        defaultEmpresaShouldBeFound("fundacao.equals=" + DEFAULT_FUNDACAO);

        // Get all the empresaList where fundacao equals to UPDATED_FUNDACAO
        defaultEmpresaShouldNotBeFound("fundacao.equals=" + UPDATED_FUNDACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFundacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fundacao not equals to DEFAULT_FUNDACAO
        defaultEmpresaShouldNotBeFound("fundacao.notEquals=" + DEFAULT_FUNDACAO);

        // Get all the empresaList where fundacao not equals to UPDATED_FUNDACAO
        defaultEmpresaShouldBeFound("fundacao.notEquals=" + UPDATED_FUNDACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFundacaoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fundacao in DEFAULT_FUNDACAO or UPDATED_FUNDACAO
        defaultEmpresaShouldBeFound("fundacao.in=" + DEFAULT_FUNDACAO + "," + UPDATED_FUNDACAO);

        // Get all the empresaList where fundacao equals to UPDATED_FUNDACAO
        defaultEmpresaShouldNotBeFound("fundacao.in=" + UPDATED_FUNDACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFundacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fundacao is not null
        defaultEmpresaShouldBeFound("fundacao.specified=true");

        // Get all the empresaList where fundacao is null
        defaultEmpresaShouldNotBeFound("fundacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByFundacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fundacao is greater than or equal to DEFAULT_FUNDACAO
        defaultEmpresaShouldBeFound("fundacao.greaterThanOrEqual=" + DEFAULT_FUNDACAO);

        // Get all the empresaList where fundacao is greater than or equal to UPDATED_FUNDACAO
        defaultEmpresaShouldNotBeFound("fundacao.greaterThanOrEqual=" + UPDATED_FUNDACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFundacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fundacao is less than or equal to DEFAULT_FUNDACAO
        defaultEmpresaShouldBeFound("fundacao.lessThanOrEqual=" + DEFAULT_FUNDACAO);

        // Get all the empresaList where fundacao is less than or equal to SMALLER_FUNDACAO
        defaultEmpresaShouldNotBeFound("fundacao.lessThanOrEqual=" + SMALLER_FUNDACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFundacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fundacao is less than DEFAULT_FUNDACAO
        defaultEmpresaShouldNotBeFound("fundacao.lessThan=" + DEFAULT_FUNDACAO);

        // Get all the empresaList where fundacao is less than UPDATED_FUNDACAO
        defaultEmpresaShouldBeFound("fundacao.lessThan=" + UPDATED_FUNDACAO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByFundacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where fundacao is greater than DEFAULT_FUNDACAO
        defaultEmpresaShouldNotBeFound("fundacao.greaterThan=" + DEFAULT_FUNDACAO);

        // Get all the empresaList where fundacao is greater than SMALLER_FUNDACAO
        defaultEmpresaShouldBeFound("fundacao.greaterThan=" + SMALLER_FUNDACAO);
    }


    @Test
    @Transactional
    public void getAllEmpresasByNifIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nif equals to DEFAULT_NIF
        defaultEmpresaShouldBeFound("nif.equals=" + DEFAULT_NIF);

        // Get all the empresaList where nif equals to UPDATED_NIF
        defaultEmpresaShouldNotBeFound("nif.equals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNifIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nif not equals to DEFAULT_NIF
        defaultEmpresaShouldNotBeFound("nif.notEquals=" + DEFAULT_NIF);

        // Get all the empresaList where nif not equals to UPDATED_NIF
        defaultEmpresaShouldBeFound("nif.notEquals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNifIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nif in DEFAULT_NIF or UPDATED_NIF
        defaultEmpresaShouldBeFound("nif.in=" + DEFAULT_NIF + "," + UPDATED_NIF);

        // Get all the empresaList where nif equals to UPDATED_NIF
        defaultEmpresaShouldNotBeFound("nif.in=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNifIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nif is not null
        defaultEmpresaShouldBeFound("nif.specified=true");

        // Get all the empresaList where nif is null
        defaultEmpresaShouldNotBeFound("nif.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByNifContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nif contains DEFAULT_NIF
        defaultEmpresaShouldBeFound("nif.contains=" + DEFAULT_NIF);

        // Get all the empresaList where nif contains UPDATED_NIF
        defaultEmpresaShouldNotBeFound("nif.contains=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNifNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nif does not contain DEFAULT_NIF
        defaultEmpresaShouldNotBeFound("nif.doesNotContain=" + DEFAULT_NIF);

        // Get all the empresaList where nif does not contain UPDATED_NIF
        defaultEmpresaShouldBeFound("nif.doesNotContain=" + UPDATED_NIF);
    }


    @Test
    @Transactional
    public void getAllEmpresasByNumeroRegistroComercialIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where numeroRegistroComercial equals to DEFAULT_NUMERO_REGISTRO_COMERCIAL
        defaultEmpresaShouldBeFound("numeroRegistroComercial.equals=" + DEFAULT_NUMERO_REGISTRO_COMERCIAL);

        // Get all the empresaList where numeroRegistroComercial equals to UPDATED_NUMERO_REGISTRO_COMERCIAL
        defaultEmpresaShouldNotBeFound("numeroRegistroComercial.equals=" + UPDATED_NUMERO_REGISTRO_COMERCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNumeroRegistroComercialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where numeroRegistroComercial not equals to DEFAULT_NUMERO_REGISTRO_COMERCIAL
        defaultEmpresaShouldNotBeFound("numeroRegistroComercial.notEquals=" + DEFAULT_NUMERO_REGISTRO_COMERCIAL);

        // Get all the empresaList where numeroRegistroComercial not equals to UPDATED_NUMERO_REGISTRO_COMERCIAL
        defaultEmpresaShouldBeFound("numeroRegistroComercial.notEquals=" + UPDATED_NUMERO_REGISTRO_COMERCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNumeroRegistroComercialIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where numeroRegistroComercial in DEFAULT_NUMERO_REGISTRO_COMERCIAL or UPDATED_NUMERO_REGISTRO_COMERCIAL
        defaultEmpresaShouldBeFound("numeroRegistroComercial.in=" + DEFAULT_NUMERO_REGISTRO_COMERCIAL + "," + UPDATED_NUMERO_REGISTRO_COMERCIAL);

        // Get all the empresaList where numeroRegistroComercial equals to UPDATED_NUMERO_REGISTRO_COMERCIAL
        defaultEmpresaShouldNotBeFound("numeroRegistroComercial.in=" + UPDATED_NUMERO_REGISTRO_COMERCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNumeroRegistroComercialIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where numeroRegistroComercial is not null
        defaultEmpresaShouldBeFound("numeroRegistroComercial.specified=true");

        // Get all the empresaList where numeroRegistroComercial is null
        defaultEmpresaShouldNotBeFound("numeroRegistroComercial.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmpresasByNumeroRegistroComercialContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where numeroRegistroComercial contains DEFAULT_NUMERO_REGISTRO_COMERCIAL
        defaultEmpresaShouldBeFound("numeroRegistroComercial.contains=" + DEFAULT_NUMERO_REGISTRO_COMERCIAL);

        // Get all the empresaList where numeroRegistroComercial contains UPDATED_NUMERO_REGISTRO_COMERCIAL
        defaultEmpresaShouldNotBeFound("numeroRegistroComercial.contains=" + UPDATED_NUMERO_REGISTRO_COMERCIAL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByNumeroRegistroComercialNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where numeroRegistroComercial does not contain DEFAULT_NUMERO_REGISTRO_COMERCIAL
        defaultEmpresaShouldNotBeFound("numeroRegistroComercial.doesNotContain=" + DEFAULT_NUMERO_REGISTRO_COMERCIAL);

        // Get all the empresaList where numeroRegistroComercial does not contain UPDATED_NUMERO_REGISTRO_COMERCIAL
        defaultEmpresaShouldBeFound("numeroRegistroComercial.doesNotContain=" + UPDATED_NUMERO_REGISTRO_COMERCIAL);
    }


    @Test
    @Transactional
    public void getAllEmpresasByDespesaFixaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaFixa equals to DEFAULT_DESPESA_FIXA
        defaultEmpresaShouldBeFound("despesaFixa.equals=" + DEFAULT_DESPESA_FIXA);

        // Get all the empresaList where despesaFixa equals to UPDATED_DESPESA_FIXA
        defaultEmpresaShouldNotBeFound("despesaFixa.equals=" + UPDATED_DESPESA_FIXA);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaFixaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaFixa not equals to DEFAULT_DESPESA_FIXA
        defaultEmpresaShouldNotBeFound("despesaFixa.notEquals=" + DEFAULT_DESPESA_FIXA);

        // Get all the empresaList where despesaFixa not equals to UPDATED_DESPESA_FIXA
        defaultEmpresaShouldBeFound("despesaFixa.notEquals=" + UPDATED_DESPESA_FIXA);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaFixaIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaFixa in DEFAULT_DESPESA_FIXA or UPDATED_DESPESA_FIXA
        defaultEmpresaShouldBeFound("despesaFixa.in=" + DEFAULT_DESPESA_FIXA + "," + UPDATED_DESPESA_FIXA);

        // Get all the empresaList where despesaFixa equals to UPDATED_DESPESA_FIXA
        defaultEmpresaShouldNotBeFound("despesaFixa.in=" + UPDATED_DESPESA_FIXA);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaFixaIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaFixa is not null
        defaultEmpresaShouldBeFound("despesaFixa.specified=true");

        // Get all the empresaList where despesaFixa is null
        defaultEmpresaShouldNotBeFound("despesaFixa.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaFixaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaFixa is greater than or equal to DEFAULT_DESPESA_FIXA
        defaultEmpresaShouldBeFound("despesaFixa.greaterThanOrEqual=" + DEFAULT_DESPESA_FIXA);

        // Get all the empresaList where despesaFixa is greater than or equal to (DEFAULT_DESPESA_FIXA + 1)
        defaultEmpresaShouldNotBeFound("despesaFixa.greaterThanOrEqual=" + (DEFAULT_DESPESA_FIXA + 1));
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaFixaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaFixa is less than or equal to DEFAULT_DESPESA_FIXA
        defaultEmpresaShouldBeFound("despesaFixa.lessThanOrEqual=" + DEFAULT_DESPESA_FIXA);

        // Get all the empresaList where despesaFixa is less than or equal to SMALLER_DESPESA_FIXA
        defaultEmpresaShouldNotBeFound("despesaFixa.lessThanOrEqual=" + SMALLER_DESPESA_FIXA);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaFixaIsLessThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaFixa is less than DEFAULT_DESPESA_FIXA
        defaultEmpresaShouldNotBeFound("despesaFixa.lessThan=" + DEFAULT_DESPESA_FIXA);

        // Get all the empresaList where despesaFixa is less than (DEFAULT_DESPESA_FIXA + 1)
        defaultEmpresaShouldBeFound("despesaFixa.lessThan=" + (DEFAULT_DESPESA_FIXA + 1));
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaFixaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaFixa is greater than DEFAULT_DESPESA_FIXA
        defaultEmpresaShouldNotBeFound("despesaFixa.greaterThan=" + DEFAULT_DESPESA_FIXA);

        // Get all the empresaList where despesaFixa is greater than SMALLER_DESPESA_FIXA
        defaultEmpresaShouldBeFound("despesaFixa.greaterThan=" + SMALLER_DESPESA_FIXA);
    }


    @Test
    @Transactional
    public void getAllEmpresasByDespesaVariavelIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaVariavel equals to DEFAULT_DESPESA_VARIAVEL
        defaultEmpresaShouldBeFound("despesaVariavel.equals=" + DEFAULT_DESPESA_VARIAVEL);

        // Get all the empresaList where despesaVariavel equals to UPDATED_DESPESA_VARIAVEL
        defaultEmpresaShouldNotBeFound("despesaVariavel.equals=" + UPDATED_DESPESA_VARIAVEL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaVariavelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaVariavel not equals to DEFAULT_DESPESA_VARIAVEL
        defaultEmpresaShouldNotBeFound("despesaVariavel.notEquals=" + DEFAULT_DESPESA_VARIAVEL);

        // Get all the empresaList where despesaVariavel not equals to UPDATED_DESPESA_VARIAVEL
        defaultEmpresaShouldBeFound("despesaVariavel.notEquals=" + UPDATED_DESPESA_VARIAVEL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaVariavelIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaVariavel in DEFAULT_DESPESA_VARIAVEL or UPDATED_DESPESA_VARIAVEL
        defaultEmpresaShouldBeFound("despesaVariavel.in=" + DEFAULT_DESPESA_VARIAVEL + "," + UPDATED_DESPESA_VARIAVEL);

        // Get all the empresaList where despesaVariavel equals to UPDATED_DESPESA_VARIAVEL
        defaultEmpresaShouldNotBeFound("despesaVariavel.in=" + UPDATED_DESPESA_VARIAVEL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaVariavelIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaVariavel is not null
        defaultEmpresaShouldBeFound("despesaVariavel.specified=true");

        // Get all the empresaList where despesaVariavel is null
        defaultEmpresaShouldNotBeFound("despesaVariavel.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaVariavelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaVariavel is greater than or equal to DEFAULT_DESPESA_VARIAVEL
        defaultEmpresaShouldBeFound("despesaVariavel.greaterThanOrEqual=" + DEFAULT_DESPESA_VARIAVEL);

        // Get all the empresaList where despesaVariavel is greater than or equal to (DEFAULT_DESPESA_VARIAVEL + 1)
        defaultEmpresaShouldNotBeFound("despesaVariavel.greaterThanOrEqual=" + (DEFAULT_DESPESA_VARIAVEL + 1));
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaVariavelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaVariavel is less than or equal to DEFAULT_DESPESA_VARIAVEL
        defaultEmpresaShouldBeFound("despesaVariavel.lessThanOrEqual=" + DEFAULT_DESPESA_VARIAVEL);

        // Get all the empresaList where despesaVariavel is less than or equal to SMALLER_DESPESA_VARIAVEL
        defaultEmpresaShouldNotBeFound("despesaVariavel.lessThanOrEqual=" + SMALLER_DESPESA_VARIAVEL);
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaVariavelIsLessThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaVariavel is less than DEFAULT_DESPESA_VARIAVEL
        defaultEmpresaShouldNotBeFound("despesaVariavel.lessThan=" + DEFAULT_DESPESA_VARIAVEL);

        // Get all the empresaList where despesaVariavel is less than (DEFAULT_DESPESA_VARIAVEL + 1)
        defaultEmpresaShouldBeFound("despesaVariavel.lessThan=" + (DEFAULT_DESPESA_VARIAVEL + 1));
    }

    @Test
    @Transactional
    public void getAllEmpresasByDespesaVariavelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where despesaVariavel is greater than DEFAULT_DESPESA_VARIAVEL
        defaultEmpresaShouldNotBeFound("despesaVariavel.greaterThan=" + DEFAULT_DESPESA_VARIAVEL);

        // Get all the empresaList where despesaVariavel is greater than SMALLER_DESPESA_VARIAVEL
        defaultEmpresaShouldBeFound("despesaVariavel.greaterThan=" + SMALLER_DESPESA_VARIAVEL);
    }


    @Test
    @Transactional
    public void getAllEmpresasByAberturaExercioIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where aberturaExercio equals to DEFAULT_ABERTURA_EXERCIO
        defaultEmpresaShouldBeFound("aberturaExercio.equals=" + DEFAULT_ABERTURA_EXERCIO);

        // Get all the empresaList where aberturaExercio equals to UPDATED_ABERTURA_EXERCIO
        defaultEmpresaShouldNotBeFound("aberturaExercio.equals=" + UPDATED_ABERTURA_EXERCIO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByAberturaExercioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where aberturaExercio not equals to DEFAULT_ABERTURA_EXERCIO
        defaultEmpresaShouldNotBeFound("aberturaExercio.notEquals=" + DEFAULT_ABERTURA_EXERCIO);

        // Get all the empresaList where aberturaExercio not equals to UPDATED_ABERTURA_EXERCIO
        defaultEmpresaShouldBeFound("aberturaExercio.notEquals=" + UPDATED_ABERTURA_EXERCIO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByAberturaExercioIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where aberturaExercio in DEFAULT_ABERTURA_EXERCIO or UPDATED_ABERTURA_EXERCIO
        defaultEmpresaShouldBeFound("aberturaExercio.in=" + DEFAULT_ABERTURA_EXERCIO + "," + UPDATED_ABERTURA_EXERCIO);

        // Get all the empresaList where aberturaExercio equals to UPDATED_ABERTURA_EXERCIO
        defaultEmpresaShouldNotBeFound("aberturaExercio.in=" + UPDATED_ABERTURA_EXERCIO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByAberturaExercioIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where aberturaExercio is not null
        defaultEmpresaShouldBeFound("aberturaExercio.specified=true");

        // Get all the empresaList where aberturaExercio is null
        defaultEmpresaShouldNotBeFound("aberturaExercio.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByAberturaExercioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where aberturaExercio is greater than or equal to DEFAULT_ABERTURA_EXERCIO
        defaultEmpresaShouldBeFound("aberturaExercio.greaterThanOrEqual=" + DEFAULT_ABERTURA_EXERCIO);

        // Get all the empresaList where aberturaExercio is greater than or equal to UPDATED_ABERTURA_EXERCIO
        defaultEmpresaShouldNotBeFound("aberturaExercio.greaterThanOrEqual=" + UPDATED_ABERTURA_EXERCIO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByAberturaExercioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where aberturaExercio is less than or equal to DEFAULT_ABERTURA_EXERCIO
        defaultEmpresaShouldBeFound("aberturaExercio.lessThanOrEqual=" + DEFAULT_ABERTURA_EXERCIO);

        // Get all the empresaList where aberturaExercio is less than or equal to SMALLER_ABERTURA_EXERCIO
        defaultEmpresaShouldNotBeFound("aberturaExercio.lessThanOrEqual=" + SMALLER_ABERTURA_EXERCIO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByAberturaExercioIsLessThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where aberturaExercio is less than DEFAULT_ABERTURA_EXERCIO
        defaultEmpresaShouldNotBeFound("aberturaExercio.lessThan=" + DEFAULT_ABERTURA_EXERCIO);

        // Get all the empresaList where aberturaExercio is less than UPDATED_ABERTURA_EXERCIO
        defaultEmpresaShouldBeFound("aberturaExercio.lessThan=" + UPDATED_ABERTURA_EXERCIO);
    }

    @Test
    @Transactional
    public void getAllEmpresasByAberturaExercioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where aberturaExercio is greater than DEFAULT_ABERTURA_EXERCIO
        defaultEmpresaShouldNotBeFound("aberturaExercio.greaterThan=" + DEFAULT_ABERTURA_EXERCIO);

        // Get all the empresaList where aberturaExercio is greater than SMALLER_ABERTURA_EXERCIO
        defaultEmpresaShouldBeFound("aberturaExercio.greaterThan=" + SMALLER_ABERTURA_EXERCIO);
    }


    @Test
    @Transactional
    public void getAllEmpresasBySedeIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where sede equals to DEFAULT_SEDE
        defaultEmpresaShouldBeFound("sede.equals=" + DEFAULT_SEDE);

        // Get all the empresaList where sede equals to UPDATED_SEDE
        defaultEmpresaShouldNotBeFound("sede.equals=" + UPDATED_SEDE);
    }

    @Test
    @Transactional
    public void getAllEmpresasBySedeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where sede not equals to DEFAULT_SEDE
        defaultEmpresaShouldNotBeFound("sede.notEquals=" + DEFAULT_SEDE);

        // Get all the empresaList where sede not equals to UPDATED_SEDE
        defaultEmpresaShouldBeFound("sede.notEquals=" + UPDATED_SEDE);
    }

    @Test
    @Transactional
    public void getAllEmpresasBySedeIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where sede in DEFAULT_SEDE or UPDATED_SEDE
        defaultEmpresaShouldBeFound("sede.in=" + DEFAULT_SEDE + "," + UPDATED_SEDE);

        // Get all the empresaList where sede equals to UPDATED_SEDE
        defaultEmpresaShouldNotBeFound("sede.in=" + UPDATED_SEDE);
    }

    @Test
    @Transactional
    public void getAllEmpresasBySedeIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where sede is not null
        defaultEmpresaShouldBeFound("sede.specified=true");

        // Get all the empresaList where sede is null
        defaultEmpresaShouldNotBeFound("sede.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmpresasByEscrituracaoContabilIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        EscrituracaoContabil escrituracaoContabil = EscrituracaoContabilResourceIT.createEntity(em);
        em.persist(escrituracaoContabil);
        em.flush();
        empresa.addEscrituracaoContabil(escrituracaoContabil);
        empresaRepository.saveAndFlush(empresa);
        Long escrituracaoContabilId = escrituracaoContabil.getId();

        // Get all the empresaList where escrituracaoContabil equals to escrituracaoContabilId
        defaultEmpresaShouldBeFound("escrituracaoContabilId.equals=" + escrituracaoContabilId);

        // Get all the empresaList where escrituracaoContabil equals to escrituracaoContabilId + 1
        defaultEmpresaShouldNotBeFound("escrituracaoContabilId.equals=" + (escrituracaoContabilId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        empresa.addEmpresa(empresa);
        empresaRepository.saveAndFlush(empresa);
        Long empresaId = empresa.getId();

        // Get all the empresaList where empresa equals to empresaId
        defaultEmpresaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the empresaList where empresa equals to empresaId + 1
        defaultEmpresaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByLocalizacaoEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        LocalizacaoEmpresa localizacaoEmpresa = LocalizacaoEmpresaResourceIT.createEntity(em);
        em.persist(localizacaoEmpresa);
        em.flush();
        empresa.addLocalizacaoEmpresa(localizacaoEmpresa);
        empresaRepository.saveAndFlush(empresa);
        Long localizacaoEmpresaId = localizacaoEmpresa.getId();

        // Get all the empresaList where localizacaoEmpresa equals to localizacaoEmpresaId
        defaultEmpresaShouldBeFound("localizacaoEmpresaId.equals=" + localizacaoEmpresaId);

        // Get all the empresaList where localizacaoEmpresa equals to localizacaoEmpresaId + 1
        defaultEmpresaShouldNotBeFound("localizacaoEmpresaId.equals=" + (localizacaoEmpresaId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByContactoEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        ContactoEmpresa contactoEmpresa = ContactoEmpresaResourceIT.createEntity(em);
        em.persist(contactoEmpresa);
        em.flush();
        empresa.addContactoEmpresa(contactoEmpresa);
        empresaRepository.saveAndFlush(empresa);
        Long contactoEmpresaId = contactoEmpresa.getId();

        // Get all the empresaList where contactoEmpresa equals to contactoEmpresaId
        defaultEmpresaShouldBeFound("contactoEmpresaId.equals=" + contactoEmpresaId);

        // Get all the empresaList where contactoEmpresa equals to contactoEmpresaId + 1
        defaultEmpresaShouldNotBeFound("contactoEmpresaId.equals=" + (contactoEmpresaId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByLicencaSoftwareIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        LicencaSoftware licencaSoftware = LicencaSoftwareResourceIT.createEntity(em);
        em.persist(licencaSoftware);
        em.flush();
        empresa.addLicencaSoftware(licencaSoftware);
        empresaRepository.saveAndFlush(empresa);
        Long licencaSoftwareId = licencaSoftware.getId();

        // Get all the empresaList where licencaSoftware equals to licencaSoftwareId
        defaultEmpresaShouldBeFound("licencaSoftwareId.equals=" + licencaSoftwareId);

        // Get all the empresaList where licencaSoftware equals to licencaSoftwareId + 1
        defaultEmpresaShouldNotBeFound("licencaSoftwareId.equals=" + (licencaSoftwareId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByLancamentoFinanceiroIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        LancamentoFinanceiro lancamentoFinanceiro = LancamentoFinanceiroResourceIT.createEntity(em);
        em.persist(lancamentoFinanceiro);
        em.flush();
        empresa.addLancamentoFinanceiro(lancamentoFinanceiro);
        empresaRepository.saveAndFlush(empresa);
        Long lancamentoFinanceiroId = lancamentoFinanceiro.getId();

        // Get all the empresaList where lancamentoFinanceiro equals to lancamentoFinanceiroId
        defaultEmpresaShouldBeFound("lancamentoFinanceiroId.equals=" + lancamentoFinanceiroId);

        // Get all the empresaList where lancamentoFinanceiro equals to lancamentoFinanceiroId + 1
        defaultEmpresaShouldNotBeFound("lancamentoFinanceiroId.equals=" + (lancamentoFinanceiroId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByProdutoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        Produto produto = ProdutoResourceIT.createEntity(em);
        em.persist(produto);
        em.flush();
        empresa.addProduto(produto);
        empresaRepository.saveAndFlush(empresa);
        Long produtoId = produto.getId();

        // Get all the empresaList where produto equals to produtoId
        defaultEmpresaShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the empresaList where produto equals to produtoId + 1
        defaultEmpresaShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByLocalArmazenamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        LocalArmazenamento localArmazenamento = LocalArmazenamentoResourceIT.createEntity(em);
        em.persist(localArmazenamento);
        em.flush();
        empresa.addLocalArmazenamento(localArmazenamento);
        empresaRepository.saveAndFlush(empresa);
        Long localArmazenamentoId = localArmazenamento.getId();

        // Get all the empresaList where localArmazenamento equals to localArmazenamentoId
        defaultEmpresaShouldBeFound("localArmazenamentoId.equals=" + localArmazenamentoId);

        // Get all the empresaList where localArmazenamento equals to localArmazenamentoId + 1
        defaultEmpresaShouldNotBeFound("localArmazenamentoId.equals=" + (localArmazenamentoId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByCompraIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        Compra compra = CompraResourceIT.createEntity(em);
        em.persist(compra);
        em.flush();
        empresa.addCompra(compra);
        empresaRepository.saveAndFlush(empresa);
        Long compraId = compra.getId();

        // Get all the empresaList where compra equals to compraId
        defaultEmpresaShouldBeFound("compraId.equals=" + compraId);

        // Get all the empresaList where compra equals to compraId + 1
        defaultEmpresaShouldNotBeFound("compraId.equals=" + (compraId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        Venda venda = VendaResourceIT.createEntity(em);
        em.persist(venda);
        em.flush();
        empresa.addVenda(venda);
        empresaRepository.saveAndFlush(empresa);
        Long vendaId = venda.getId();

        // Get all the empresaList where venda equals to vendaId
        defaultEmpresaShouldBeFound("vendaId.equals=" + vendaId);

        // Get all the empresaList where venda equals to vendaId + 1
        defaultEmpresaShouldNotBeFound("vendaId.equals=" + (vendaId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByUtilizadorIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        User utilizador = UserResourceIT.createEntity(em);
        em.persist(utilizador);
        em.flush();
        empresa.setUtilizador(utilizador);
        empresaRepository.saveAndFlush(empresa);
        Long utilizadorId = utilizador.getId();

        // Get all the empresaList where utilizador equals to utilizadorId
        defaultEmpresaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the empresaList where utilizador equals to utilizadorId + 1
        defaultEmpresaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByContaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        Conta conta = ContaResourceIT.createEntity(em);
        em.persist(conta);
        em.flush();
        empresa.setConta(conta);
        empresaRepository.saveAndFlush(empresa);
        Long contaId = conta.getId();

        // Get all the empresaList where conta equals to contaId
        defaultEmpresaShouldBeFound("contaId.equals=" + contaId);

        // Get all the empresaList where conta equals to contaId + 1
        defaultEmpresaShouldNotBeFound("contaId.equals=" + (contaId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByHierarquiaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        Empresa hierarquia = EmpresaResourceIT.createEntity(em);
        em.persist(hierarquia);
        em.flush();
        empresa.setHierarquia(hierarquia);
        empresaRepository.saveAndFlush(empresa);
        Long hierarquiaId = hierarquia.getId();

        // Get all the empresaList where hierarquia equals to hierarquiaId
        defaultEmpresaShouldBeFound("hierarquiaId.equals=" + hierarquiaId);

        // Get all the empresaList where hierarquia equals to hierarquiaId + 1
        defaultEmpresaShouldNotBeFound("hierarquiaId.equals=" + (hierarquiaId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByContaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        Conta conta = ContaResourceIT.createEntity(em);
        em.persist(conta);
        em.flush();
        empresa.addConta(conta);
        empresaRepository.saveAndFlush(empresa);
        Long contaId = conta.getId();

        // Get all the empresaList where conta equals to contaId
        defaultEmpresaShouldBeFound("contaId.equals=" + contaId);

        // Get all the empresaList where conta equals to contaId + 1
        defaultEmpresaShouldNotBeFound("contaId.equals=" + (contaId + 1));
    }


    @Test
    @Transactional
    public void getAllEmpresasByCoordenadaBancariaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        CoordenadaBancaria coordenadaBancaria = CoordenadaBancariaResourceIT.createEntity(em);
        em.persist(coordenadaBancaria);
        em.flush();
        empresa.addCoordenadaBancaria(coordenadaBancaria);
        empresaRepository.saveAndFlush(empresa);
        Long coordenadaBancariaId = coordenadaBancaria.getId();

        // Get all the empresaList where coordenadaBancaria equals to coordenadaBancariaId
        defaultEmpresaShouldBeFound("coordenadaBancariaId.equals=" + coordenadaBancariaId);

        // Get all the empresaList where coordenadaBancaria equals to coordenadaBancariaId + 1
        defaultEmpresaShouldNotBeFound("coordenadaBancariaId.equals=" + (coordenadaBancariaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpresaShouldBeFound(String filter) throws Exception {
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoSociedade").value(hasItem(DEFAULT_TIPO_SOCIEDADE)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].logotipoContentType").value(hasItem(DEFAULT_LOGOTIPO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logotipo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGOTIPO))))
            .andExpect(jsonPath("$.[*].capitalSocial").value(hasItem(DEFAULT_CAPITAL_SOCIAL.intValue())))
            .andExpect(jsonPath("$.[*].fundacao").value(hasItem(DEFAULT_FUNDACAO.toString())))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].numeroRegistroComercial").value(hasItem(DEFAULT_NUMERO_REGISTRO_COMERCIAL)))
            .andExpect(jsonPath("$.[*].despesaFixa").value(hasItem(DEFAULT_DESPESA_FIXA.doubleValue())))
            .andExpect(jsonPath("$.[*].despesaVariavel").value(hasItem(DEFAULT_DESPESA_VARIAVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].aberturaExercio").value(hasItem(DEFAULT_ABERTURA_EXERCIO.toString())))
            .andExpect(jsonPath("$.[*].designacaoComercial").value(hasItem(DEFAULT_DESIGNACAO_COMERCIAL.toString())))
            .andExpect(jsonPath("$.[*].sede").value(hasItem(DEFAULT_SEDE.booleanValue())));

        // Check, that the count call also returns 1
        restEmpresaMockMvc.perform(get("/api/empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpresaShouldNotBeFound(String filter) throws Exception {
        restEmpresaMockMvc.perform(get("/api/empresas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpresaMockMvc.perform(get("/api/empresas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get("/api/empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa
        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).get();
        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
        em.detach(updatedEmpresa);
        updatedEmpresa
            .tipoSociedade(UPDATED_TIPO_SOCIEDADE)
            .nome(UPDATED_NOME)
            .logotipo(UPDATED_LOGOTIPO)
            .logotipoContentType(UPDATED_LOGOTIPO_CONTENT_TYPE)
            .capitalSocial(UPDATED_CAPITAL_SOCIAL)
            .fundacao(UPDATED_FUNDACAO)
            .nif(UPDATED_NIF)
            .numeroRegistroComercial(UPDATED_NUMERO_REGISTRO_COMERCIAL)
            .despesaFixa(UPDATED_DESPESA_FIXA)
            .despesaVariavel(UPDATED_DESPESA_VARIAVEL)
            .aberturaExercio(UPDATED_ABERTURA_EXERCIO)
            .designacaoComercial(UPDATED_DESIGNACAO_COMERCIAL)
            .sede(UPDATED_SEDE);
        EmpresaDTO empresaDTO = empresaMapper.toDto(updatedEmpresa);

        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getTipoSociedade()).isEqualTo(UPDATED_TIPO_SOCIEDADE);
        assertThat(testEmpresa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEmpresa.getLogotipo()).isEqualTo(UPDATED_LOGOTIPO);
        assertThat(testEmpresa.getLogotipoContentType()).isEqualTo(UPDATED_LOGOTIPO_CONTENT_TYPE);
        assertThat(testEmpresa.getCapitalSocial()).isEqualTo(UPDATED_CAPITAL_SOCIAL);
        assertThat(testEmpresa.getFundacao()).isEqualTo(UPDATED_FUNDACAO);
        assertThat(testEmpresa.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testEmpresa.getNumeroRegistroComercial()).isEqualTo(UPDATED_NUMERO_REGISTRO_COMERCIAL);
        assertThat(testEmpresa.getDespesaFixa()).isEqualTo(UPDATED_DESPESA_FIXA);
        assertThat(testEmpresa.getDespesaVariavel()).isEqualTo(UPDATED_DESPESA_VARIAVEL);
        assertThat(testEmpresa.getAberturaExercio()).isEqualTo(UPDATED_ABERTURA_EXERCIO);
        assertThat(testEmpresa.getDesignacaoComercial()).isEqualTo(UPDATED_DESIGNACAO_COMERCIAL);
        assertThat(testEmpresa.isSede()).isEqualTo(UPDATED_SEDE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc.perform(put("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeDelete = empresaRepository.findAll().size();

        // Delete the empresa
        restEmpresaMockMvc.perform(delete("/api/empresas/{id}", empresa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
