package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.FormaLiquidacao;
import com.ravunana.manda.domain.LancamentoFinanceiro;
import com.ravunana.manda.repository.FormaLiquidacaoRepository;
import com.ravunana.manda.service.FormaLiquidacaoService;
import com.ravunana.manda.service.dto.FormaLiquidacaoDTO;
import com.ravunana.manda.service.mapper.FormaLiquidacaoMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.FormaLiquidacaoCriteria;
import com.ravunana.manda.service.FormaLiquidacaoQueryService;

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
 * Integration tests for the {@link FormaLiquidacaoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class FormaLiquidacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Double DEFAULT_JURO = 0D;
    private static final Double UPDATED_JURO = 1D;
    private static final Double SMALLER_JURO = 0D - 1D;

    private static final Integer DEFAULT_PRAZO_LIQUIDACAO = 0;
    private static final Integer UPDATED_PRAZO_LIQUIDACAO = 1;
    private static final Integer SMALLER_PRAZO_LIQUIDACAO = 0 - 1;

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;
    private static final Integer SMALLER_QUANTIDADE = 1 - 1;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    @Autowired
    private FormaLiquidacaoRepository formaLiquidacaoRepository;

    @Autowired
    private FormaLiquidacaoMapper formaLiquidacaoMapper;

    @Autowired
    private FormaLiquidacaoService formaLiquidacaoService;

    @Autowired
    private FormaLiquidacaoQueryService formaLiquidacaoQueryService;

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

    private MockMvc restFormaLiquidacaoMockMvc;

    private FormaLiquidacao formaLiquidacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormaLiquidacaoResource formaLiquidacaoResource = new FormaLiquidacaoResource(formaLiquidacaoService, formaLiquidacaoQueryService);
        this.restFormaLiquidacaoMockMvc = MockMvcBuilders.standaloneSetup(formaLiquidacaoResource)
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
    public static FormaLiquidacao createEntity(EntityManager em) {
        FormaLiquidacao formaLiquidacao = new FormaLiquidacao()
            .nome(DEFAULT_NOME)
            .juro(DEFAULT_JURO)
            .prazoLiquidacao(DEFAULT_PRAZO_LIQUIDACAO)
            .quantidade(DEFAULT_QUANTIDADE)
            .icon(DEFAULT_ICON);
        return formaLiquidacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaLiquidacao createUpdatedEntity(EntityManager em) {
        FormaLiquidacao formaLiquidacao = new FormaLiquidacao()
            .nome(UPDATED_NOME)
            .juro(UPDATED_JURO)
            .prazoLiquidacao(UPDATED_PRAZO_LIQUIDACAO)
            .quantidade(UPDATED_QUANTIDADE)
            .icon(UPDATED_ICON);
        return formaLiquidacao;
    }

    @BeforeEach
    public void initTest() {
        formaLiquidacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormaLiquidacao() throws Exception {
        int databaseSizeBeforeCreate = formaLiquidacaoRepository.findAll().size();

        // Create the FormaLiquidacao
        FormaLiquidacaoDTO formaLiquidacaoDTO = formaLiquidacaoMapper.toDto(formaLiquidacao);
        restFormaLiquidacaoMockMvc.perform(post("/api/forma-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaLiquidacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the FormaLiquidacao in the database
        List<FormaLiquidacao> formaLiquidacaoList = formaLiquidacaoRepository.findAll();
        assertThat(formaLiquidacaoList).hasSize(databaseSizeBeforeCreate + 1);
        FormaLiquidacao testFormaLiquidacao = formaLiquidacaoList.get(formaLiquidacaoList.size() - 1);
        assertThat(testFormaLiquidacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFormaLiquidacao.getJuro()).isEqualTo(DEFAULT_JURO);
        assertThat(testFormaLiquidacao.getPrazoLiquidacao()).isEqualTo(DEFAULT_PRAZO_LIQUIDACAO);
        assertThat(testFormaLiquidacao.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testFormaLiquidacao.getIcon()).isEqualTo(DEFAULT_ICON);
    }

    @Test
    @Transactional
    public void createFormaLiquidacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formaLiquidacaoRepository.findAll().size();

        // Create the FormaLiquidacao with an existing ID
        formaLiquidacao.setId(1L);
        FormaLiquidacaoDTO formaLiquidacaoDTO = formaLiquidacaoMapper.toDto(formaLiquidacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormaLiquidacaoMockMvc.perform(post("/api/forma-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaLiquidacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormaLiquidacao in the database
        List<FormaLiquidacao> formaLiquidacaoList = formaLiquidacaoRepository.findAll();
        assertThat(formaLiquidacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = formaLiquidacaoRepository.findAll().size();
        // set the field null
        formaLiquidacao.setNome(null);

        // Create the FormaLiquidacao, which fails.
        FormaLiquidacaoDTO formaLiquidacaoDTO = formaLiquidacaoMapper.toDto(formaLiquidacao);

        restFormaLiquidacaoMockMvc.perform(post("/api/forma-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaLiquidacaoDTO)))
            .andExpect(status().isBadRequest());

        List<FormaLiquidacao> formaLiquidacaoList = formaLiquidacaoRepository.findAll();
        assertThat(formaLiquidacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJuroIsRequired() throws Exception {
        int databaseSizeBeforeTest = formaLiquidacaoRepository.findAll().size();
        // set the field null
        formaLiquidacao.setJuro(null);

        // Create the FormaLiquidacao, which fails.
        FormaLiquidacaoDTO formaLiquidacaoDTO = formaLiquidacaoMapper.toDto(formaLiquidacao);

        restFormaLiquidacaoMockMvc.perform(post("/api/forma-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaLiquidacaoDTO)))
            .andExpect(status().isBadRequest());

        List<FormaLiquidacao> formaLiquidacaoList = formaLiquidacaoRepository.findAll();
        assertThat(formaLiquidacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrazoLiquidacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = formaLiquidacaoRepository.findAll().size();
        // set the field null
        formaLiquidacao.setPrazoLiquidacao(null);

        // Create the FormaLiquidacao, which fails.
        FormaLiquidacaoDTO formaLiquidacaoDTO = formaLiquidacaoMapper.toDto(formaLiquidacao);

        restFormaLiquidacaoMockMvc.perform(post("/api/forma-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaLiquidacaoDTO)))
            .andExpect(status().isBadRequest());

        List<FormaLiquidacao> formaLiquidacaoList = formaLiquidacaoRepository.findAll();
        assertThat(formaLiquidacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = formaLiquidacaoRepository.findAll().size();
        // set the field null
        formaLiquidacao.setQuantidade(null);

        // Create the FormaLiquidacao, which fails.
        FormaLiquidacaoDTO formaLiquidacaoDTO = formaLiquidacaoMapper.toDto(formaLiquidacao);

        restFormaLiquidacaoMockMvc.perform(post("/api/forma-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaLiquidacaoDTO)))
            .andExpect(status().isBadRequest());

        List<FormaLiquidacao> formaLiquidacaoList = formaLiquidacaoRepository.findAll();
        assertThat(formaLiquidacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaos() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList
        restFormaLiquidacaoMockMvc.perform(get("/api/forma-liquidacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaLiquidacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].juro").value(hasItem(DEFAULT_JURO.doubleValue())))
            .andExpect(jsonPath("$.[*].prazoLiquidacao").value(hasItem(DEFAULT_PRAZO_LIQUIDACAO)))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));
    }
    
    @Test
    @Transactional
    public void getFormaLiquidacao() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get the formaLiquidacao
        restFormaLiquidacaoMockMvc.perform(get("/api/forma-liquidacaos/{id}", formaLiquidacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formaLiquidacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.juro").value(DEFAULT_JURO.doubleValue()))
            .andExpect(jsonPath("$.prazoLiquidacao").value(DEFAULT_PRAZO_LIQUIDACAO))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON));
    }


    @Test
    @Transactional
    public void getFormaLiquidacaosByIdFiltering() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        Long id = formaLiquidacao.getId();

        defaultFormaLiquidacaoShouldBeFound("id.equals=" + id);
        defaultFormaLiquidacaoShouldNotBeFound("id.notEquals=" + id);

        defaultFormaLiquidacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFormaLiquidacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultFormaLiquidacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFormaLiquidacaoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFormaLiquidacaosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where nome equals to DEFAULT_NOME
        defaultFormaLiquidacaoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the formaLiquidacaoList where nome equals to UPDATED_NOME
        defaultFormaLiquidacaoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where nome not equals to DEFAULT_NOME
        defaultFormaLiquidacaoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the formaLiquidacaoList where nome not equals to UPDATED_NOME
        defaultFormaLiquidacaoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultFormaLiquidacaoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the formaLiquidacaoList where nome equals to UPDATED_NOME
        defaultFormaLiquidacaoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where nome is not null
        defaultFormaLiquidacaoShouldBeFound("nome.specified=true");

        // Get all the formaLiquidacaoList where nome is null
        defaultFormaLiquidacaoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllFormaLiquidacaosByNomeContainsSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where nome contains DEFAULT_NOME
        defaultFormaLiquidacaoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the formaLiquidacaoList where nome contains UPDATED_NOME
        defaultFormaLiquidacaoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where nome does not contain DEFAULT_NOME
        defaultFormaLiquidacaoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the formaLiquidacaoList where nome does not contain UPDATED_NOME
        defaultFormaLiquidacaoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllFormaLiquidacaosByJuroIsEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where juro equals to DEFAULT_JURO
        defaultFormaLiquidacaoShouldBeFound("juro.equals=" + DEFAULT_JURO);

        // Get all the formaLiquidacaoList where juro equals to UPDATED_JURO
        defaultFormaLiquidacaoShouldNotBeFound("juro.equals=" + UPDATED_JURO);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByJuroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where juro not equals to DEFAULT_JURO
        defaultFormaLiquidacaoShouldNotBeFound("juro.notEquals=" + DEFAULT_JURO);

        // Get all the formaLiquidacaoList where juro not equals to UPDATED_JURO
        defaultFormaLiquidacaoShouldBeFound("juro.notEquals=" + UPDATED_JURO);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByJuroIsInShouldWork() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where juro in DEFAULT_JURO or UPDATED_JURO
        defaultFormaLiquidacaoShouldBeFound("juro.in=" + DEFAULT_JURO + "," + UPDATED_JURO);

        // Get all the formaLiquidacaoList where juro equals to UPDATED_JURO
        defaultFormaLiquidacaoShouldNotBeFound("juro.in=" + UPDATED_JURO);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByJuroIsNullOrNotNull() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where juro is not null
        defaultFormaLiquidacaoShouldBeFound("juro.specified=true");

        // Get all the formaLiquidacaoList where juro is null
        defaultFormaLiquidacaoShouldNotBeFound("juro.specified=false");
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByJuroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where juro is greater than or equal to DEFAULT_JURO
        defaultFormaLiquidacaoShouldBeFound("juro.greaterThanOrEqual=" + DEFAULT_JURO);

        // Get all the formaLiquidacaoList where juro is greater than or equal to (DEFAULT_JURO + 1)
        defaultFormaLiquidacaoShouldNotBeFound("juro.greaterThanOrEqual=" + (DEFAULT_JURO + 1));
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByJuroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where juro is less than or equal to DEFAULT_JURO
        defaultFormaLiquidacaoShouldBeFound("juro.lessThanOrEqual=" + DEFAULT_JURO);

        // Get all the formaLiquidacaoList where juro is less than or equal to SMALLER_JURO
        defaultFormaLiquidacaoShouldNotBeFound("juro.lessThanOrEqual=" + SMALLER_JURO);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByJuroIsLessThanSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where juro is less than DEFAULT_JURO
        defaultFormaLiquidacaoShouldNotBeFound("juro.lessThan=" + DEFAULT_JURO);

        // Get all the formaLiquidacaoList where juro is less than (DEFAULT_JURO + 1)
        defaultFormaLiquidacaoShouldBeFound("juro.lessThan=" + (DEFAULT_JURO + 1));
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByJuroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where juro is greater than DEFAULT_JURO
        defaultFormaLiquidacaoShouldNotBeFound("juro.greaterThan=" + DEFAULT_JURO);

        // Get all the formaLiquidacaoList where juro is greater than SMALLER_JURO
        defaultFormaLiquidacaoShouldBeFound("juro.greaterThan=" + SMALLER_JURO);
    }


    @Test
    @Transactional
    public void getAllFormaLiquidacaosByPrazoLiquidacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where prazoLiquidacao equals to DEFAULT_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldBeFound("prazoLiquidacao.equals=" + DEFAULT_PRAZO_LIQUIDACAO);

        // Get all the formaLiquidacaoList where prazoLiquidacao equals to UPDATED_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldNotBeFound("prazoLiquidacao.equals=" + UPDATED_PRAZO_LIQUIDACAO);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByPrazoLiquidacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where prazoLiquidacao not equals to DEFAULT_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldNotBeFound("prazoLiquidacao.notEquals=" + DEFAULT_PRAZO_LIQUIDACAO);

        // Get all the formaLiquidacaoList where prazoLiquidacao not equals to UPDATED_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldBeFound("prazoLiquidacao.notEquals=" + UPDATED_PRAZO_LIQUIDACAO);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByPrazoLiquidacaoIsInShouldWork() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where prazoLiquidacao in DEFAULT_PRAZO_LIQUIDACAO or UPDATED_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldBeFound("prazoLiquidacao.in=" + DEFAULT_PRAZO_LIQUIDACAO + "," + UPDATED_PRAZO_LIQUIDACAO);

        // Get all the formaLiquidacaoList where prazoLiquidacao equals to UPDATED_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldNotBeFound("prazoLiquidacao.in=" + UPDATED_PRAZO_LIQUIDACAO);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByPrazoLiquidacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where prazoLiquidacao is not null
        defaultFormaLiquidacaoShouldBeFound("prazoLiquidacao.specified=true");

        // Get all the formaLiquidacaoList where prazoLiquidacao is null
        defaultFormaLiquidacaoShouldNotBeFound("prazoLiquidacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByPrazoLiquidacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where prazoLiquidacao is greater than or equal to DEFAULT_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldBeFound("prazoLiquidacao.greaterThanOrEqual=" + DEFAULT_PRAZO_LIQUIDACAO);

        // Get all the formaLiquidacaoList where prazoLiquidacao is greater than or equal to UPDATED_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldNotBeFound("prazoLiquidacao.greaterThanOrEqual=" + UPDATED_PRAZO_LIQUIDACAO);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByPrazoLiquidacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where prazoLiquidacao is less than or equal to DEFAULT_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldBeFound("prazoLiquidacao.lessThanOrEqual=" + DEFAULT_PRAZO_LIQUIDACAO);

        // Get all the formaLiquidacaoList where prazoLiquidacao is less than or equal to SMALLER_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldNotBeFound("prazoLiquidacao.lessThanOrEqual=" + SMALLER_PRAZO_LIQUIDACAO);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByPrazoLiquidacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where prazoLiquidacao is less than DEFAULT_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldNotBeFound("prazoLiquidacao.lessThan=" + DEFAULT_PRAZO_LIQUIDACAO);

        // Get all the formaLiquidacaoList where prazoLiquidacao is less than UPDATED_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldBeFound("prazoLiquidacao.lessThan=" + UPDATED_PRAZO_LIQUIDACAO);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByPrazoLiquidacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where prazoLiquidacao is greater than DEFAULT_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldNotBeFound("prazoLiquidacao.greaterThan=" + DEFAULT_PRAZO_LIQUIDACAO);

        // Get all the formaLiquidacaoList where prazoLiquidacao is greater than SMALLER_PRAZO_LIQUIDACAO
        defaultFormaLiquidacaoShouldBeFound("prazoLiquidacao.greaterThan=" + SMALLER_PRAZO_LIQUIDACAO);
    }


    @Test
    @Transactional
    public void getAllFormaLiquidacaosByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where quantidade equals to DEFAULT_QUANTIDADE
        defaultFormaLiquidacaoShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the formaLiquidacaoList where quantidade equals to UPDATED_QUANTIDADE
        defaultFormaLiquidacaoShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByQuantidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where quantidade not equals to DEFAULT_QUANTIDADE
        defaultFormaLiquidacaoShouldNotBeFound("quantidade.notEquals=" + DEFAULT_QUANTIDADE);

        // Get all the formaLiquidacaoList where quantidade not equals to UPDATED_QUANTIDADE
        defaultFormaLiquidacaoShouldBeFound("quantidade.notEquals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultFormaLiquidacaoShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the formaLiquidacaoList where quantidade equals to UPDATED_QUANTIDADE
        defaultFormaLiquidacaoShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where quantidade is not null
        defaultFormaLiquidacaoShouldBeFound("quantidade.specified=true");

        // Get all the formaLiquidacaoList where quantidade is null
        defaultFormaLiquidacaoShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultFormaLiquidacaoShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the formaLiquidacaoList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultFormaLiquidacaoShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultFormaLiquidacaoShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the formaLiquidacaoList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultFormaLiquidacaoShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where quantidade is less than DEFAULT_QUANTIDADE
        defaultFormaLiquidacaoShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the formaLiquidacaoList where quantidade is less than UPDATED_QUANTIDADE
        defaultFormaLiquidacaoShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultFormaLiquidacaoShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the formaLiquidacaoList where quantidade is greater than SMALLER_QUANTIDADE
        defaultFormaLiquidacaoShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }


    @Test
    @Transactional
    public void getAllFormaLiquidacaosByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where icon equals to DEFAULT_ICON
        defaultFormaLiquidacaoShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the formaLiquidacaoList where icon equals to UPDATED_ICON
        defaultFormaLiquidacaoShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where icon not equals to DEFAULT_ICON
        defaultFormaLiquidacaoShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the formaLiquidacaoList where icon not equals to UPDATED_ICON
        defaultFormaLiquidacaoShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByIconIsInShouldWork() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultFormaLiquidacaoShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the formaLiquidacaoList where icon equals to UPDATED_ICON
        defaultFormaLiquidacaoShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where icon is not null
        defaultFormaLiquidacaoShouldBeFound("icon.specified=true");

        // Get all the formaLiquidacaoList where icon is null
        defaultFormaLiquidacaoShouldNotBeFound("icon.specified=false");
    }
                @Test
    @Transactional
    public void getAllFormaLiquidacaosByIconContainsSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where icon contains DEFAULT_ICON
        defaultFormaLiquidacaoShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the formaLiquidacaoList where icon contains UPDATED_ICON
        defaultFormaLiquidacaoShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllFormaLiquidacaosByIconNotContainsSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        // Get all the formaLiquidacaoList where icon does not contain DEFAULT_ICON
        defaultFormaLiquidacaoShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the formaLiquidacaoList where icon does not contain UPDATED_ICON
        defaultFormaLiquidacaoShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }


    @Test
    @Transactional
    public void getAllFormaLiquidacaosByLancamentoFinanceiroIsEqualToSomething() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);
        LancamentoFinanceiro lancamentoFinanceiro = LancamentoFinanceiroResourceIT.createEntity(em);
        em.persist(lancamentoFinanceiro);
        em.flush();
        formaLiquidacao.addLancamentoFinanceiro(lancamentoFinanceiro);
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);
        Long lancamentoFinanceiroId = lancamentoFinanceiro.getId();

        // Get all the formaLiquidacaoList where lancamentoFinanceiro equals to lancamentoFinanceiroId
        defaultFormaLiquidacaoShouldBeFound("lancamentoFinanceiroId.equals=" + lancamentoFinanceiroId);

        // Get all the formaLiquidacaoList where lancamentoFinanceiro equals to lancamentoFinanceiroId + 1
        defaultFormaLiquidacaoShouldNotBeFound("lancamentoFinanceiroId.equals=" + (lancamentoFinanceiroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFormaLiquidacaoShouldBeFound(String filter) throws Exception {
        restFormaLiquidacaoMockMvc.perform(get("/api/forma-liquidacaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaLiquidacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].juro").value(hasItem(DEFAULT_JURO.doubleValue())))
            .andExpect(jsonPath("$.[*].prazoLiquidacao").value(hasItem(DEFAULT_PRAZO_LIQUIDACAO)))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));

        // Check, that the count call also returns 1
        restFormaLiquidacaoMockMvc.perform(get("/api/forma-liquidacaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFormaLiquidacaoShouldNotBeFound(String filter) throws Exception {
        restFormaLiquidacaoMockMvc.perform(get("/api/forma-liquidacaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFormaLiquidacaoMockMvc.perform(get("/api/forma-liquidacaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFormaLiquidacao() throws Exception {
        // Get the formaLiquidacao
        restFormaLiquidacaoMockMvc.perform(get("/api/forma-liquidacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormaLiquidacao() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        int databaseSizeBeforeUpdate = formaLiquidacaoRepository.findAll().size();

        // Update the formaLiquidacao
        FormaLiquidacao updatedFormaLiquidacao = formaLiquidacaoRepository.findById(formaLiquidacao.getId()).get();
        // Disconnect from session so that the updates on updatedFormaLiquidacao are not directly saved in db
        em.detach(updatedFormaLiquidacao);
        updatedFormaLiquidacao
            .nome(UPDATED_NOME)
            .juro(UPDATED_JURO)
            .prazoLiquidacao(UPDATED_PRAZO_LIQUIDACAO)
            .quantidade(UPDATED_QUANTIDADE)
            .icon(UPDATED_ICON);
        FormaLiquidacaoDTO formaLiquidacaoDTO = formaLiquidacaoMapper.toDto(updatedFormaLiquidacao);

        restFormaLiquidacaoMockMvc.perform(put("/api/forma-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaLiquidacaoDTO)))
            .andExpect(status().isOk());

        // Validate the FormaLiquidacao in the database
        List<FormaLiquidacao> formaLiquidacaoList = formaLiquidacaoRepository.findAll();
        assertThat(formaLiquidacaoList).hasSize(databaseSizeBeforeUpdate);
        FormaLiquidacao testFormaLiquidacao = formaLiquidacaoList.get(formaLiquidacaoList.size() - 1);
        assertThat(testFormaLiquidacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFormaLiquidacao.getJuro()).isEqualTo(UPDATED_JURO);
        assertThat(testFormaLiquidacao.getPrazoLiquidacao()).isEqualTo(UPDATED_PRAZO_LIQUIDACAO);
        assertThat(testFormaLiquidacao.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testFormaLiquidacao.getIcon()).isEqualTo(UPDATED_ICON);
    }

    @Test
    @Transactional
    public void updateNonExistingFormaLiquidacao() throws Exception {
        int databaseSizeBeforeUpdate = formaLiquidacaoRepository.findAll().size();

        // Create the FormaLiquidacao
        FormaLiquidacaoDTO formaLiquidacaoDTO = formaLiquidacaoMapper.toDto(formaLiquidacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormaLiquidacaoMockMvc.perform(put("/api/forma-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaLiquidacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormaLiquidacao in the database
        List<FormaLiquidacao> formaLiquidacaoList = formaLiquidacaoRepository.findAll();
        assertThat(formaLiquidacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormaLiquidacao() throws Exception {
        // Initialize the database
        formaLiquidacaoRepository.saveAndFlush(formaLiquidacao);

        int databaseSizeBeforeDelete = formaLiquidacaoRepository.findAll().size();

        // Delete the formaLiquidacao
        restFormaLiquidacaoMockMvc.perform(delete("/api/forma-liquidacaos/{id}", formaLiquidacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormaLiquidacao> formaLiquidacaoList = formaLiquidacaoRepository.findAll();
        assertThat(formaLiquidacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
