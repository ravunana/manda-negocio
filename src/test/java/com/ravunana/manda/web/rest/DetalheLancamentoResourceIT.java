package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.DetalheLancamento;
import com.ravunana.manda.domain.RetencaoFonte;
import com.ravunana.manda.domain.User;
import com.ravunana.manda.domain.LancamentoFinanceiro;
import com.ravunana.manda.domain.MeioLiquidacao;
import com.ravunana.manda.domain.Moeda;
import com.ravunana.manda.domain.CoordenadaBancaria;
import com.ravunana.manda.repository.DetalheLancamentoRepository;
import com.ravunana.manda.service.DetalheLancamentoService;
import com.ravunana.manda.service.dto.DetalheLancamentoDTO;
import com.ravunana.manda.service.mapper.DetalheLancamentoMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.DetalheLancamentoCriteria;
import com.ravunana.manda.service.DetalheLancamentoQueryService;

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

/**
 * Integration tests for the {@link DetalheLancamentoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class DetalheLancamentoResourceIT {

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(1);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(0 - 1);

    private static final BigDecimal DEFAULT_DESCONTO = new BigDecimal(0);
    private static final BigDecimal UPDATED_DESCONTO = new BigDecimal(1);
    private static final BigDecimal SMALLER_DESCONTO = new BigDecimal(0 - 1);

    private static final Double DEFAULT_JURO = 0D;
    private static final Double UPDATED_JURO = 1D;
    private static final Double SMALLER_JURO = 0D - 1D;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_RETENCAO_FONTE = false;
    private static final Boolean UPDATED_RETENCAO_FONTE = true;

    private static final LocalDate DEFAULT_DATA_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_VENCIMENTO = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_LIQUIDADO = false;
    private static final Boolean UPDATED_LIQUIDADO = true;

    @Autowired
    private DetalheLancamentoRepository detalheLancamentoRepository;

    @Autowired
    private DetalheLancamentoMapper detalheLancamentoMapper;

    @Autowired
    private DetalheLancamentoService detalheLancamentoService;

    @Autowired
    private DetalheLancamentoQueryService detalheLancamentoQueryService;

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

    private MockMvc restDetalheLancamentoMockMvc;

    private DetalheLancamento detalheLancamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetalheLancamentoResource detalheLancamentoResource = new DetalheLancamentoResource(detalheLancamentoService, detalheLancamentoQueryService);
        this.restDetalheLancamentoMockMvc = MockMvcBuilders.standaloneSetup(detalheLancamentoResource)
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
    public static DetalheLancamento createEntity(EntityManager em) {
        DetalheLancamento detalheLancamento = new DetalheLancamento()
            .valor(DEFAULT_VALOR)
            .desconto(DEFAULT_DESCONTO)
            .juro(DEFAULT_JURO)
            .descricao(DEFAULT_DESCRICAO)
            .data(DEFAULT_DATA)
            .retencaoFonte(DEFAULT_RETENCAO_FONTE)
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .liquidado(DEFAULT_LIQUIDADO);
        // Add required entity
        LancamentoFinanceiro lancamentoFinanceiro;
        if (TestUtil.findAll(em, LancamentoFinanceiro.class).isEmpty()) {
            lancamentoFinanceiro = LancamentoFinanceiroResourceIT.createEntity(em);
            em.persist(lancamentoFinanceiro);
            em.flush();
        } else {
            lancamentoFinanceiro = TestUtil.findAll(em, LancamentoFinanceiro.class).get(0);
        }
        detalheLancamento.setLancamentoFinanceiro(lancamentoFinanceiro);
        // Add required entity
        MeioLiquidacao meioLiquidacao;
        if (TestUtil.findAll(em, MeioLiquidacao.class).isEmpty()) {
            meioLiquidacao = MeioLiquidacaoResourceIT.createEntity(em);
            em.persist(meioLiquidacao);
            em.flush();
        } else {
            meioLiquidacao = TestUtil.findAll(em, MeioLiquidacao.class).get(0);
        }
        detalheLancamento.setMetodoLiquidacao(meioLiquidacao);
        // Add required entity
        Moeda moeda;
        if (TestUtil.findAll(em, Moeda.class).isEmpty()) {
            moeda = MoedaResourceIT.createEntity(em);
            em.persist(moeda);
            em.flush();
        } else {
            moeda = TestUtil.findAll(em, Moeda.class).get(0);
        }
        detalheLancamento.setMoeda(moeda);
        // Add required entity
        CoordenadaBancaria coordenadaBancaria;
        if (TestUtil.findAll(em, CoordenadaBancaria.class).isEmpty()) {
            coordenadaBancaria = CoordenadaBancariaResourceIT.createEntity(em);
            em.persist(coordenadaBancaria);
            em.flush();
        } else {
            coordenadaBancaria = TestUtil.findAll(em, CoordenadaBancaria.class).get(0);
        }
        detalheLancamento.setCoordenada(coordenadaBancaria);
        return detalheLancamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalheLancamento createUpdatedEntity(EntityManager em) {
        DetalheLancamento detalheLancamento = new DetalheLancamento()
            .valor(UPDATED_VALOR)
            .desconto(UPDATED_DESCONTO)
            .juro(UPDATED_JURO)
            .descricao(UPDATED_DESCRICAO)
            .data(UPDATED_DATA)
            .retencaoFonte(UPDATED_RETENCAO_FONTE)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .liquidado(UPDATED_LIQUIDADO);
        // Add required entity
        LancamentoFinanceiro lancamentoFinanceiro;
        if (TestUtil.findAll(em, LancamentoFinanceiro.class).isEmpty()) {
            lancamentoFinanceiro = LancamentoFinanceiroResourceIT.createUpdatedEntity(em);
            em.persist(lancamentoFinanceiro);
            em.flush();
        } else {
            lancamentoFinanceiro = TestUtil.findAll(em, LancamentoFinanceiro.class).get(0);
        }
        detalheLancamento.setLancamentoFinanceiro(lancamentoFinanceiro);
        // Add required entity
        MeioLiquidacao meioLiquidacao;
        if (TestUtil.findAll(em, MeioLiquidacao.class).isEmpty()) {
            meioLiquidacao = MeioLiquidacaoResourceIT.createUpdatedEntity(em);
            em.persist(meioLiquidacao);
            em.flush();
        } else {
            meioLiquidacao = TestUtil.findAll(em, MeioLiquidacao.class).get(0);
        }
        detalheLancamento.setMetodoLiquidacao(meioLiquidacao);
        // Add required entity
        Moeda moeda;
        if (TestUtil.findAll(em, Moeda.class).isEmpty()) {
            moeda = MoedaResourceIT.createUpdatedEntity(em);
            em.persist(moeda);
            em.flush();
        } else {
            moeda = TestUtil.findAll(em, Moeda.class).get(0);
        }
        detalheLancamento.setMoeda(moeda);
        // Add required entity
        CoordenadaBancaria coordenadaBancaria;
        if (TestUtil.findAll(em, CoordenadaBancaria.class).isEmpty()) {
            coordenadaBancaria = CoordenadaBancariaResourceIT.createUpdatedEntity(em);
            em.persist(coordenadaBancaria);
            em.flush();
        } else {
            coordenadaBancaria = TestUtil.findAll(em, CoordenadaBancaria.class).get(0);
        }
        detalheLancamento.setCoordenada(coordenadaBancaria);
        return detalheLancamento;
    }

    @BeforeEach
    public void initTest() {
        detalheLancamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalheLancamento() throws Exception {
        int databaseSizeBeforeCreate = detalheLancamentoRepository.findAll().size();

        // Create the DetalheLancamento
        DetalheLancamentoDTO detalheLancamentoDTO = detalheLancamentoMapper.toDto(detalheLancamento);
        restDetalheLancamentoMockMvc.perform(post("/api/detalhe-lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheLancamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the DetalheLancamento in the database
        List<DetalheLancamento> detalheLancamentoList = detalheLancamentoRepository.findAll();
        assertThat(detalheLancamentoList).hasSize(databaseSizeBeforeCreate + 1);
        DetalheLancamento testDetalheLancamento = detalheLancamentoList.get(detalheLancamentoList.size() - 1);
        assertThat(testDetalheLancamento.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testDetalheLancamento.getDesconto()).isEqualTo(DEFAULT_DESCONTO);
        assertThat(testDetalheLancamento.getJuro()).isEqualTo(DEFAULT_JURO);
        assertThat(testDetalheLancamento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDetalheLancamento.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDetalheLancamento.isRetencaoFonte()).isEqualTo(DEFAULT_RETENCAO_FONTE);
        assertThat(testDetalheLancamento.getDataVencimento()).isEqualTo(DEFAULT_DATA_VENCIMENTO);
        assertThat(testDetalheLancamento.isLiquidado()).isEqualTo(DEFAULT_LIQUIDADO);
    }

    @Test
    @Transactional
    public void createDetalheLancamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalheLancamentoRepository.findAll().size();

        // Create the DetalheLancamento with an existing ID
        detalheLancamento.setId(1L);
        DetalheLancamentoDTO detalheLancamentoDTO = detalheLancamentoMapper.toDto(detalheLancamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalheLancamentoMockMvc.perform(post("/api/detalhe-lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheLancamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalheLancamento in the database
        List<DetalheLancamento> detalheLancamentoList = detalheLancamentoRepository.findAll();
        assertThat(detalheLancamentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalheLancamentoRepository.findAll().size();
        // set the field null
        detalheLancamento.setValor(null);

        // Create the DetalheLancamento, which fails.
        DetalheLancamentoDTO detalheLancamentoDTO = detalheLancamentoMapper.toDto(detalheLancamento);

        restDetalheLancamentoMockMvc.perform(post("/api/detalhe-lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheLancamentoDTO)))
            .andExpect(status().isBadRequest());

        List<DetalheLancamento> detalheLancamentoList = detalheLancamentoRepository.findAll();
        assertThat(detalheLancamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalheLancamentoRepository.findAll().size();
        // set the field null
        detalheLancamento.setData(null);

        // Create the DetalheLancamento, which fails.
        DetalheLancamentoDTO detalheLancamentoDTO = detalheLancamentoMapper.toDto(detalheLancamento);

        restDetalheLancamentoMockMvc.perform(post("/api/detalhe-lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheLancamentoDTO)))
            .andExpect(status().isBadRequest());

        List<DetalheLancamento> detalheLancamentoList = detalheLancamentoRepository.findAll();
        assertThat(detalheLancamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataVencimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalheLancamentoRepository.findAll().size();
        // set the field null
        detalheLancamento.setDataVencimento(null);

        // Create the DetalheLancamento, which fails.
        DetalheLancamentoDTO detalheLancamentoDTO = detalheLancamentoMapper.toDto(detalheLancamento);

        restDetalheLancamentoMockMvc.perform(post("/api/detalhe-lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheLancamentoDTO)))
            .andExpect(status().isBadRequest());

        List<DetalheLancamento> detalheLancamentoList = detalheLancamentoRepository.findAll();
        assertThat(detalheLancamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentos() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList
        restDetalheLancamentoMockMvc.perform(get("/api/detalhe-lancamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalheLancamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.intValue())))
            .andExpect(jsonPath("$.[*].juro").value(hasItem(DEFAULT_JURO.doubleValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].retencaoFonte").value(hasItem(DEFAULT_RETENCAO_FONTE.booleanValue())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].liquidado").value(hasItem(DEFAULT_LIQUIDADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDetalheLancamento() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get the detalheLancamento
        restDetalheLancamentoMockMvc.perform(get("/api/detalhe-lancamentos/{id}", detalheLancamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalheLancamento.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.desconto").value(DEFAULT_DESCONTO.intValue()))
            .andExpect(jsonPath("$.juro").value(DEFAULT_JURO.doubleValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.retencaoFonte").value(DEFAULT_RETENCAO_FONTE.booleanValue()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.liquidado").value(DEFAULT_LIQUIDADO.booleanValue()));
    }


    @Test
    @Transactional
    public void getDetalheLancamentosByIdFiltering() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        Long id = detalheLancamento.getId();

        defaultDetalheLancamentoShouldBeFound("id.equals=" + id);
        defaultDetalheLancamentoShouldNotBeFound("id.notEquals=" + id);

        defaultDetalheLancamentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDetalheLancamentoShouldNotBeFound("id.greaterThan=" + id);

        defaultDetalheLancamentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDetalheLancamentoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDetalheLancamentosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where valor equals to DEFAULT_VALOR
        defaultDetalheLancamentoShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the detalheLancamentoList where valor equals to UPDATED_VALOR
        defaultDetalheLancamentoShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where valor not equals to DEFAULT_VALOR
        defaultDetalheLancamentoShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the detalheLancamentoList where valor not equals to UPDATED_VALOR
        defaultDetalheLancamentoShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultDetalheLancamentoShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the detalheLancamentoList where valor equals to UPDATED_VALOR
        defaultDetalheLancamentoShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where valor is not null
        defaultDetalheLancamentoShouldBeFound("valor.specified=true");

        // Get all the detalheLancamentoList where valor is null
        defaultDetalheLancamentoShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where valor is greater than or equal to DEFAULT_VALOR
        defaultDetalheLancamentoShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the detalheLancamentoList where valor is greater than or equal to UPDATED_VALOR
        defaultDetalheLancamentoShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where valor is less than or equal to DEFAULT_VALOR
        defaultDetalheLancamentoShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the detalheLancamentoList where valor is less than or equal to SMALLER_VALOR
        defaultDetalheLancamentoShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where valor is less than DEFAULT_VALOR
        defaultDetalheLancamentoShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the detalheLancamentoList where valor is less than UPDATED_VALOR
        defaultDetalheLancamentoShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where valor is greater than DEFAULT_VALOR
        defaultDetalheLancamentoShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the detalheLancamentoList where valor is greater than SMALLER_VALOR
        defaultDetalheLancamentoShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllDetalheLancamentosByDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where desconto equals to DEFAULT_DESCONTO
        defaultDetalheLancamentoShouldBeFound("desconto.equals=" + DEFAULT_DESCONTO);

        // Get all the detalheLancamentoList where desconto equals to UPDATED_DESCONTO
        defaultDetalheLancamentoShouldNotBeFound("desconto.equals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDescontoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where desconto not equals to DEFAULT_DESCONTO
        defaultDetalheLancamentoShouldNotBeFound("desconto.notEquals=" + DEFAULT_DESCONTO);

        // Get all the detalheLancamentoList where desconto not equals to UPDATED_DESCONTO
        defaultDetalheLancamentoShouldBeFound("desconto.notEquals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where desconto in DEFAULT_DESCONTO or UPDATED_DESCONTO
        defaultDetalheLancamentoShouldBeFound("desconto.in=" + DEFAULT_DESCONTO + "," + UPDATED_DESCONTO);

        // Get all the detalheLancamentoList where desconto equals to UPDATED_DESCONTO
        defaultDetalheLancamentoShouldNotBeFound("desconto.in=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where desconto is not null
        defaultDetalheLancamentoShouldBeFound("desconto.specified=true");

        // Get all the detalheLancamentoList where desconto is null
        defaultDetalheLancamentoShouldNotBeFound("desconto.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where desconto is greater than or equal to DEFAULT_DESCONTO
        defaultDetalheLancamentoShouldBeFound("desconto.greaterThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the detalheLancamentoList where desconto is greater than or equal to UPDATED_DESCONTO
        defaultDetalheLancamentoShouldNotBeFound("desconto.greaterThanOrEqual=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where desconto is less than or equal to DEFAULT_DESCONTO
        defaultDetalheLancamentoShouldBeFound("desconto.lessThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the detalheLancamentoList where desconto is less than or equal to SMALLER_DESCONTO
        defaultDetalheLancamentoShouldNotBeFound("desconto.lessThanOrEqual=" + SMALLER_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where desconto is less than DEFAULT_DESCONTO
        defaultDetalheLancamentoShouldNotBeFound("desconto.lessThan=" + DEFAULT_DESCONTO);

        // Get all the detalheLancamentoList where desconto is less than UPDATED_DESCONTO
        defaultDetalheLancamentoShouldBeFound("desconto.lessThan=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where desconto is greater than DEFAULT_DESCONTO
        defaultDetalheLancamentoShouldNotBeFound("desconto.greaterThan=" + DEFAULT_DESCONTO);

        // Get all the detalheLancamentoList where desconto is greater than SMALLER_DESCONTO
        defaultDetalheLancamentoShouldBeFound("desconto.greaterThan=" + SMALLER_DESCONTO);
    }


    @Test
    @Transactional
    public void getAllDetalheLancamentosByJuroIsEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where juro equals to DEFAULT_JURO
        defaultDetalheLancamentoShouldBeFound("juro.equals=" + DEFAULT_JURO);

        // Get all the detalheLancamentoList where juro equals to UPDATED_JURO
        defaultDetalheLancamentoShouldNotBeFound("juro.equals=" + UPDATED_JURO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByJuroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where juro not equals to DEFAULT_JURO
        defaultDetalheLancamentoShouldNotBeFound("juro.notEquals=" + DEFAULT_JURO);

        // Get all the detalheLancamentoList where juro not equals to UPDATED_JURO
        defaultDetalheLancamentoShouldBeFound("juro.notEquals=" + UPDATED_JURO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByJuroIsInShouldWork() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where juro in DEFAULT_JURO or UPDATED_JURO
        defaultDetalheLancamentoShouldBeFound("juro.in=" + DEFAULT_JURO + "," + UPDATED_JURO);

        // Get all the detalheLancamentoList where juro equals to UPDATED_JURO
        defaultDetalheLancamentoShouldNotBeFound("juro.in=" + UPDATED_JURO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByJuroIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where juro is not null
        defaultDetalheLancamentoShouldBeFound("juro.specified=true");

        // Get all the detalheLancamentoList where juro is null
        defaultDetalheLancamentoShouldNotBeFound("juro.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByJuroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where juro is greater than or equal to DEFAULT_JURO
        defaultDetalheLancamentoShouldBeFound("juro.greaterThanOrEqual=" + DEFAULT_JURO);

        // Get all the detalheLancamentoList where juro is greater than or equal to (DEFAULT_JURO + 1)
        defaultDetalheLancamentoShouldNotBeFound("juro.greaterThanOrEqual=" + (DEFAULT_JURO + 1));
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByJuroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where juro is less than or equal to DEFAULT_JURO
        defaultDetalheLancamentoShouldBeFound("juro.lessThanOrEqual=" + DEFAULT_JURO);

        // Get all the detalheLancamentoList where juro is less than or equal to SMALLER_JURO
        defaultDetalheLancamentoShouldNotBeFound("juro.lessThanOrEqual=" + SMALLER_JURO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByJuroIsLessThanSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where juro is less than DEFAULT_JURO
        defaultDetalheLancamentoShouldNotBeFound("juro.lessThan=" + DEFAULT_JURO);

        // Get all the detalheLancamentoList where juro is less than (DEFAULT_JURO + 1)
        defaultDetalheLancamentoShouldBeFound("juro.lessThan=" + (DEFAULT_JURO + 1));
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByJuroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where juro is greater than DEFAULT_JURO
        defaultDetalheLancamentoShouldNotBeFound("juro.greaterThan=" + DEFAULT_JURO);

        // Get all the detalheLancamentoList where juro is greater than SMALLER_JURO
        defaultDetalheLancamentoShouldBeFound("juro.greaterThan=" + SMALLER_JURO);
    }


    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where data equals to DEFAULT_DATA
        defaultDetalheLancamentoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the detalheLancamentoList where data equals to UPDATED_DATA
        defaultDetalheLancamentoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where data not equals to DEFAULT_DATA
        defaultDetalheLancamentoShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the detalheLancamentoList where data not equals to UPDATED_DATA
        defaultDetalheLancamentoShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultDetalheLancamentoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the detalheLancamentoList where data equals to UPDATED_DATA
        defaultDetalheLancamentoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where data is not null
        defaultDetalheLancamentoShouldBeFound("data.specified=true");

        // Get all the detalheLancamentoList where data is null
        defaultDetalheLancamentoShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where data is greater than or equal to DEFAULT_DATA
        defaultDetalheLancamentoShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the detalheLancamentoList where data is greater than or equal to UPDATED_DATA
        defaultDetalheLancamentoShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where data is less than or equal to DEFAULT_DATA
        defaultDetalheLancamentoShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the detalheLancamentoList where data is less than or equal to SMALLER_DATA
        defaultDetalheLancamentoShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where data is less than DEFAULT_DATA
        defaultDetalheLancamentoShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the detalheLancamentoList where data is less than UPDATED_DATA
        defaultDetalheLancamentoShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where data is greater than DEFAULT_DATA
        defaultDetalheLancamentoShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the detalheLancamentoList where data is greater than SMALLER_DATA
        defaultDetalheLancamentoShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllDetalheLancamentosByRetencaoFonteIsEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where retencaoFonte equals to DEFAULT_RETENCAO_FONTE
        defaultDetalheLancamentoShouldBeFound("retencaoFonte.equals=" + DEFAULT_RETENCAO_FONTE);

        // Get all the detalheLancamentoList where retencaoFonte equals to UPDATED_RETENCAO_FONTE
        defaultDetalheLancamentoShouldNotBeFound("retencaoFonte.equals=" + UPDATED_RETENCAO_FONTE);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByRetencaoFonteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where retencaoFonte not equals to DEFAULT_RETENCAO_FONTE
        defaultDetalheLancamentoShouldNotBeFound("retencaoFonte.notEquals=" + DEFAULT_RETENCAO_FONTE);

        // Get all the detalheLancamentoList where retencaoFonte not equals to UPDATED_RETENCAO_FONTE
        defaultDetalheLancamentoShouldBeFound("retencaoFonte.notEquals=" + UPDATED_RETENCAO_FONTE);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByRetencaoFonteIsInShouldWork() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where retencaoFonte in DEFAULT_RETENCAO_FONTE or UPDATED_RETENCAO_FONTE
        defaultDetalheLancamentoShouldBeFound("retencaoFonte.in=" + DEFAULT_RETENCAO_FONTE + "," + UPDATED_RETENCAO_FONTE);

        // Get all the detalheLancamentoList where retencaoFonte equals to UPDATED_RETENCAO_FONTE
        defaultDetalheLancamentoShouldNotBeFound("retencaoFonte.in=" + UPDATED_RETENCAO_FONTE);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByRetencaoFonteIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where retencaoFonte is not null
        defaultDetalheLancamentoShouldBeFound("retencaoFonte.specified=true");

        // Get all the detalheLancamentoList where retencaoFonte is null
        defaultDetalheLancamentoShouldNotBeFound("retencaoFonte.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataVencimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where dataVencimento equals to DEFAULT_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldBeFound("dataVencimento.equals=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the detalheLancamentoList where dataVencimento equals to UPDATED_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldNotBeFound("dataVencimento.equals=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataVencimentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where dataVencimento not equals to DEFAULT_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldNotBeFound("dataVencimento.notEquals=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the detalheLancamentoList where dataVencimento not equals to UPDATED_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldBeFound("dataVencimento.notEquals=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataVencimentoIsInShouldWork() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where dataVencimento in DEFAULT_DATA_VENCIMENTO or UPDATED_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldBeFound("dataVencimento.in=" + DEFAULT_DATA_VENCIMENTO + "," + UPDATED_DATA_VENCIMENTO);

        // Get all the detalheLancamentoList where dataVencimento equals to UPDATED_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldNotBeFound("dataVencimento.in=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataVencimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where dataVencimento is not null
        defaultDetalheLancamentoShouldBeFound("dataVencimento.specified=true");

        // Get all the detalheLancamentoList where dataVencimento is null
        defaultDetalheLancamentoShouldNotBeFound("dataVencimento.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataVencimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where dataVencimento is greater than or equal to DEFAULT_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldBeFound("dataVencimento.greaterThanOrEqual=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the detalheLancamentoList where dataVencimento is greater than or equal to UPDATED_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldNotBeFound("dataVencimento.greaterThanOrEqual=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataVencimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where dataVencimento is less than or equal to DEFAULT_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldBeFound("dataVencimento.lessThanOrEqual=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the detalheLancamentoList where dataVencimento is less than or equal to SMALLER_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldNotBeFound("dataVencimento.lessThanOrEqual=" + SMALLER_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataVencimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where dataVencimento is less than DEFAULT_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldNotBeFound("dataVencimento.lessThan=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the detalheLancamentoList where dataVencimento is less than UPDATED_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldBeFound("dataVencimento.lessThan=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByDataVencimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where dataVencimento is greater than DEFAULT_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldNotBeFound("dataVencimento.greaterThan=" + DEFAULT_DATA_VENCIMENTO);

        // Get all the detalheLancamentoList where dataVencimento is greater than SMALLER_DATA_VENCIMENTO
        defaultDetalheLancamentoShouldBeFound("dataVencimento.greaterThan=" + SMALLER_DATA_VENCIMENTO);
    }


    @Test
    @Transactional
    public void getAllDetalheLancamentosByLiquidadoIsEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where liquidado equals to DEFAULT_LIQUIDADO
        defaultDetalheLancamentoShouldBeFound("liquidado.equals=" + DEFAULT_LIQUIDADO);

        // Get all the detalheLancamentoList where liquidado equals to UPDATED_LIQUIDADO
        defaultDetalheLancamentoShouldNotBeFound("liquidado.equals=" + UPDATED_LIQUIDADO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByLiquidadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where liquidado not equals to DEFAULT_LIQUIDADO
        defaultDetalheLancamentoShouldNotBeFound("liquidado.notEquals=" + DEFAULT_LIQUIDADO);

        // Get all the detalheLancamentoList where liquidado not equals to UPDATED_LIQUIDADO
        defaultDetalheLancamentoShouldBeFound("liquidado.notEquals=" + UPDATED_LIQUIDADO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByLiquidadoIsInShouldWork() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where liquidado in DEFAULT_LIQUIDADO or UPDATED_LIQUIDADO
        defaultDetalheLancamentoShouldBeFound("liquidado.in=" + DEFAULT_LIQUIDADO + "," + UPDATED_LIQUIDADO);

        // Get all the detalheLancamentoList where liquidado equals to UPDATED_LIQUIDADO
        defaultDetalheLancamentoShouldNotBeFound("liquidado.in=" + UPDATED_LIQUIDADO);
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByLiquidadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        // Get all the detalheLancamentoList where liquidado is not null
        defaultDetalheLancamentoShouldBeFound("liquidado.specified=true");

        // Get all the detalheLancamentoList where liquidado is null
        defaultDetalheLancamentoShouldNotBeFound("liquidado.specified=false");
    }

    @Test
    @Transactional
    public void getAllDetalheLancamentosByRetencaoFonteIsEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);
        RetencaoFonte retencaoFonte = RetencaoFonteResourceIT.createEntity(em);
        em.persist(retencaoFonte);
        em.flush();
        detalheLancamento.addRetencaoFonte(retencaoFonte);
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);
        Long retencaoFonteId = retencaoFonte.getId();

        // Get all the detalheLancamentoList where retencaoFonte equals to retencaoFonteId
        defaultDetalheLancamentoShouldBeFound("retencaoFonteId.equals=" + retencaoFonteId);

        // Get all the detalheLancamentoList where retencaoFonte equals to retencaoFonteId + 1
        defaultDetalheLancamentoShouldNotBeFound("retencaoFonteId.equals=" + (retencaoFonteId + 1));
    }


    @Test
    @Transactional
    public void getAllDetalheLancamentosByUtilizadorIsEqualToSomething() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);
        User utilizador = UserResourceIT.createEntity(em);
        em.persist(utilizador);
        em.flush();
        detalheLancamento.setUtilizador(utilizador);
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);
        Long utilizadorId = utilizador.getId();

        // Get all the detalheLancamentoList where utilizador equals to utilizadorId
        defaultDetalheLancamentoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the detalheLancamentoList where utilizador equals to utilizadorId + 1
        defaultDetalheLancamentoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }


    @Test
    @Transactional
    public void getAllDetalheLancamentosByLancamentoFinanceiroIsEqualToSomething() throws Exception {
        // Get already existing entity
        LancamentoFinanceiro lancamentoFinanceiro = detalheLancamento.getLancamentoFinanceiro();
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);
        Long lancamentoFinanceiroId = lancamentoFinanceiro.getId();

        // Get all the detalheLancamentoList where lancamentoFinanceiro equals to lancamentoFinanceiroId
        defaultDetalheLancamentoShouldBeFound("lancamentoFinanceiroId.equals=" + lancamentoFinanceiroId);

        // Get all the detalheLancamentoList where lancamentoFinanceiro equals to lancamentoFinanceiroId + 1
        defaultDetalheLancamentoShouldNotBeFound("lancamentoFinanceiroId.equals=" + (lancamentoFinanceiroId + 1));
    }


    @Test
    @Transactional
    public void getAllDetalheLancamentosByMetodoLiquidacaoIsEqualToSomething() throws Exception {
        // Get already existing entity
        MeioLiquidacao metodoLiquidacao = detalheLancamento.getMetodoLiquidacao();
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);
        Long metodoLiquidacaoId = metodoLiquidacao.getId();

        // Get all the detalheLancamentoList where metodoLiquidacao equals to metodoLiquidacaoId
        defaultDetalheLancamentoShouldBeFound("metodoLiquidacaoId.equals=" + metodoLiquidacaoId);

        // Get all the detalheLancamentoList where metodoLiquidacao equals to metodoLiquidacaoId + 1
        defaultDetalheLancamentoShouldNotBeFound("metodoLiquidacaoId.equals=" + (metodoLiquidacaoId + 1));
    }


    @Test
    @Transactional
    public void getAllDetalheLancamentosByMoedaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Moeda moeda = detalheLancamento.getMoeda();
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);
        Long moedaId = moeda.getId();

        // Get all the detalheLancamentoList where moeda equals to moedaId
        defaultDetalheLancamentoShouldBeFound("moedaId.equals=" + moedaId);

        // Get all the detalheLancamentoList where moeda equals to moedaId + 1
        defaultDetalheLancamentoShouldNotBeFound("moedaId.equals=" + (moedaId + 1));
    }


    @Test
    @Transactional
    public void getAllDetalheLancamentosByCoordenadaIsEqualToSomething() throws Exception {
        // Get already existing entity
        CoordenadaBancaria coordenada = detalheLancamento.getCoordenada();
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);
        Long coordenadaId = coordenada.getId();

        // Get all the detalheLancamentoList where coordenada equals to coordenadaId
        defaultDetalheLancamentoShouldBeFound("coordenadaId.equals=" + coordenadaId);

        // Get all the detalheLancamentoList where coordenada equals to coordenadaId + 1
        defaultDetalheLancamentoShouldNotBeFound("coordenadaId.equals=" + (coordenadaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDetalheLancamentoShouldBeFound(String filter) throws Exception {
        restDetalheLancamentoMockMvc.perform(get("/api/detalhe-lancamentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalheLancamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.intValue())))
            .andExpect(jsonPath("$.[*].juro").value(hasItem(DEFAULT_JURO.doubleValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].retencaoFonte").value(hasItem(DEFAULT_RETENCAO_FONTE.booleanValue())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].liquidado").value(hasItem(DEFAULT_LIQUIDADO.booleanValue())));

        // Check, that the count call also returns 1
        restDetalheLancamentoMockMvc.perform(get("/api/detalhe-lancamentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDetalheLancamentoShouldNotBeFound(String filter) throws Exception {
        restDetalheLancamentoMockMvc.perform(get("/api/detalhe-lancamentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDetalheLancamentoMockMvc.perform(get("/api/detalhe-lancamentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDetalheLancamento() throws Exception {
        // Get the detalheLancamento
        restDetalheLancamentoMockMvc.perform(get("/api/detalhe-lancamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalheLancamento() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        int databaseSizeBeforeUpdate = detalheLancamentoRepository.findAll().size();

        // Update the detalheLancamento
        DetalheLancamento updatedDetalheLancamento = detalheLancamentoRepository.findById(detalheLancamento.getId()).get();
        // Disconnect from session so that the updates on updatedDetalheLancamento are not directly saved in db
        em.detach(updatedDetalheLancamento);
        updatedDetalheLancamento
            .valor(UPDATED_VALOR)
            .desconto(UPDATED_DESCONTO)
            .juro(UPDATED_JURO)
            .descricao(UPDATED_DESCRICAO)
            .data(UPDATED_DATA)
            .retencaoFonte(UPDATED_RETENCAO_FONTE)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .liquidado(UPDATED_LIQUIDADO);
        DetalheLancamentoDTO detalheLancamentoDTO = detalheLancamentoMapper.toDto(updatedDetalheLancamento);

        restDetalheLancamentoMockMvc.perform(put("/api/detalhe-lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheLancamentoDTO)))
            .andExpect(status().isOk());

        // Validate the DetalheLancamento in the database
        List<DetalheLancamento> detalheLancamentoList = detalheLancamentoRepository.findAll();
        assertThat(detalheLancamentoList).hasSize(databaseSizeBeforeUpdate);
        DetalheLancamento testDetalheLancamento = detalheLancamentoList.get(detalheLancamentoList.size() - 1);
        assertThat(testDetalheLancamento.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testDetalheLancamento.getDesconto()).isEqualTo(UPDATED_DESCONTO);
        assertThat(testDetalheLancamento.getJuro()).isEqualTo(UPDATED_JURO);
        assertThat(testDetalheLancamento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDetalheLancamento.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDetalheLancamento.isRetencaoFonte()).isEqualTo(UPDATED_RETENCAO_FONTE);
        assertThat(testDetalheLancamento.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testDetalheLancamento.isLiquidado()).isEqualTo(UPDATED_LIQUIDADO);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalheLancamento() throws Exception {
        int databaseSizeBeforeUpdate = detalheLancamentoRepository.findAll().size();

        // Create the DetalheLancamento
        DetalheLancamentoDTO detalheLancamentoDTO = detalheLancamentoMapper.toDto(detalheLancamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalheLancamentoMockMvc.perform(put("/api/detalhe-lancamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheLancamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalheLancamento in the database
        List<DetalheLancamento> detalheLancamentoList = detalheLancamentoRepository.findAll();
        assertThat(detalheLancamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetalheLancamento() throws Exception {
        // Initialize the database
        detalheLancamentoRepository.saveAndFlush(detalheLancamento);

        int databaseSizeBeforeDelete = detalheLancamentoRepository.findAll().size();

        // Delete the detalheLancamento
        restDetalheLancamentoMockMvc.perform(delete("/api/detalhe-lancamentos/{id}", detalheLancamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetalheLancamento> detalheLancamentoList = detalheLancamentoRepository.findAll();
        assertThat(detalheLancamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
