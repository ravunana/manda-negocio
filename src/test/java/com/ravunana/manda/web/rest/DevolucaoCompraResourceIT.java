package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.DevolucaoCompra;
import com.ravunana.manda.domain.ItemCompra;
import com.ravunana.manda.repository.DevolucaoCompraRepository;
import com.ravunana.manda.service.DevolucaoCompraService;
import com.ravunana.manda.service.dto.DevolucaoCompraDTO;
import com.ravunana.manda.service.mapper.DevolucaoCompraMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.DevolucaoCompraCriteria;
import com.ravunana.manda.service.DevolucaoCompraQueryService;

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
 * Integration tests for the {@link DevolucaoCompraResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class DevolucaoCompraResourceIT {

    private static final Double DEFAULT_QUANTIDADE = 1D;
    private static final Double UPDATED_QUANTIDADE = 2D;
    private static final Double SMALLER_QUANTIDADE = 1D - 1D;

    private static final Double DEFAULT_VALOR = 0D;
    private static final Double UPDATED_VALOR = 1D;
    private static final Double SMALLER_VALOR = 0D - 1D;

    private static final Double DEFAULT_DESCONTO = 0D;
    private static final Double UPDATED_DESCONTO = 1D;
    private static final Double SMALLER_DESCONTO = 0D - 1D;

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private DevolucaoCompraRepository devolucaoCompraRepository;

    @Autowired
    private DevolucaoCompraMapper devolucaoCompraMapper;

    @Autowired
    private DevolucaoCompraService devolucaoCompraService;

    @Autowired
    private DevolucaoCompraQueryService devolucaoCompraQueryService;

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

    private MockMvc restDevolucaoCompraMockMvc;

    private DevolucaoCompra devolucaoCompra;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DevolucaoCompraResource devolucaoCompraResource = new DevolucaoCompraResource(devolucaoCompraService, devolucaoCompraQueryService);
        this.restDevolucaoCompraMockMvc = MockMvcBuilders.standaloneSetup(devolucaoCompraResource)
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
    public static DevolucaoCompra createEntity(EntityManager em) {
        DevolucaoCompra devolucaoCompra = new DevolucaoCompra()
            .quantidade(DEFAULT_QUANTIDADE)
            .valor(DEFAULT_VALOR)
            .desconto(DEFAULT_DESCONTO)
            .data(DEFAULT_DATA)
            .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        ItemCompra itemCompra;
        if (TestUtil.findAll(em, ItemCompra.class).isEmpty()) {
            itemCompra = ItemCompraResourceIT.createEntity(em);
            em.persist(itemCompra);
            em.flush();
        } else {
            itemCompra = TestUtil.findAll(em, ItemCompra.class).get(0);
        }
        devolucaoCompra.setItem(itemCompra);
        return devolucaoCompra;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DevolucaoCompra createUpdatedEntity(EntityManager em) {
        DevolucaoCompra devolucaoCompra = new DevolucaoCompra()
            .quantidade(UPDATED_QUANTIDADE)
            .valor(UPDATED_VALOR)
            .desconto(UPDATED_DESCONTO)
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO);
        // Add required entity
        ItemCompra itemCompra;
        if (TestUtil.findAll(em, ItemCompra.class).isEmpty()) {
            itemCompra = ItemCompraResourceIT.createUpdatedEntity(em);
            em.persist(itemCompra);
            em.flush();
        } else {
            itemCompra = TestUtil.findAll(em, ItemCompra.class).get(0);
        }
        devolucaoCompra.setItem(itemCompra);
        return devolucaoCompra;
    }

    @BeforeEach
    public void initTest() {
        devolucaoCompra = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevolucaoCompra() throws Exception {
        int databaseSizeBeforeCreate = devolucaoCompraRepository.findAll().size();

        // Create the DevolucaoCompra
        DevolucaoCompraDTO devolucaoCompraDTO = devolucaoCompraMapper.toDto(devolucaoCompra);
        restDevolucaoCompraMockMvc.perform(post("/api/devolucao-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devolucaoCompraDTO)))
            .andExpect(status().isCreated());

        // Validate the DevolucaoCompra in the database
        List<DevolucaoCompra> devolucaoCompraList = devolucaoCompraRepository.findAll();
        assertThat(devolucaoCompraList).hasSize(databaseSizeBeforeCreate + 1);
        DevolucaoCompra testDevolucaoCompra = devolucaoCompraList.get(devolucaoCompraList.size() - 1);
        assertThat(testDevolucaoCompra.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testDevolucaoCompra.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testDevolucaoCompra.getDesconto()).isEqualTo(DEFAULT_DESCONTO);
        assertThat(testDevolucaoCompra.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDevolucaoCompra.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createDevolucaoCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = devolucaoCompraRepository.findAll().size();

        // Create the DevolucaoCompra with an existing ID
        devolucaoCompra.setId(1L);
        DevolucaoCompraDTO devolucaoCompraDTO = devolucaoCompraMapper.toDto(devolucaoCompra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevolucaoCompraMockMvc.perform(post("/api/devolucao-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devolucaoCompraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DevolucaoCompra in the database
        List<DevolucaoCompra> devolucaoCompraList = devolucaoCompraRepository.findAll();
        assertThat(devolucaoCompraList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDevolucaoCompras() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList
        restDevolucaoCompraMockMvc.perform(get("/api/devolucao-compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devolucaoCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getDevolucaoCompra() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get the devolucaoCompra
        restDevolucaoCompraMockMvc.perform(get("/api/devolucao-compras/{id}", devolucaoCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(devolucaoCompra.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.doubleValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.desconto").value(DEFAULT_DESCONTO.doubleValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }


    @Test
    @Transactional
    public void getDevolucaoComprasByIdFiltering() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        Long id = devolucaoCompra.getId();

        defaultDevolucaoCompraShouldBeFound("id.equals=" + id);
        defaultDevolucaoCompraShouldNotBeFound("id.notEquals=" + id);

        defaultDevolucaoCompraShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDevolucaoCompraShouldNotBeFound("id.greaterThan=" + id);

        defaultDevolucaoCompraShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDevolucaoCompraShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDevolucaoComprasByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where quantidade equals to DEFAULT_QUANTIDADE
        defaultDevolucaoCompraShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the devolucaoCompraList where quantidade equals to UPDATED_QUANTIDADE
        defaultDevolucaoCompraShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByQuantidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where quantidade not equals to DEFAULT_QUANTIDADE
        defaultDevolucaoCompraShouldNotBeFound("quantidade.notEquals=" + DEFAULT_QUANTIDADE);

        // Get all the devolucaoCompraList where quantidade not equals to UPDATED_QUANTIDADE
        defaultDevolucaoCompraShouldBeFound("quantidade.notEquals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultDevolucaoCompraShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the devolucaoCompraList where quantidade equals to UPDATED_QUANTIDADE
        defaultDevolucaoCompraShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where quantidade is not null
        defaultDevolucaoCompraShouldBeFound("quantidade.specified=true");

        // Get all the devolucaoCompraList where quantidade is null
        defaultDevolucaoCompraShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultDevolucaoCompraShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the devolucaoCompraList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultDevolucaoCompraShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultDevolucaoCompraShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the devolucaoCompraList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultDevolucaoCompraShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where quantidade is less than DEFAULT_QUANTIDADE
        defaultDevolucaoCompraShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the devolucaoCompraList where quantidade is less than UPDATED_QUANTIDADE
        defaultDevolucaoCompraShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultDevolucaoCompraShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the devolucaoCompraList where quantidade is greater than SMALLER_QUANTIDADE
        defaultDevolucaoCompraShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }


    @Test
    @Transactional
    public void getAllDevolucaoComprasByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where valor equals to DEFAULT_VALOR
        defaultDevolucaoCompraShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the devolucaoCompraList where valor equals to UPDATED_VALOR
        defaultDevolucaoCompraShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where valor not equals to DEFAULT_VALOR
        defaultDevolucaoCompraShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the devolucaoCompraList where valor not equals to UPDATED_VALOR
        defaultDevolucaoCompraShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByValorIsInShouldWork() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultDevolucaoCompraShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the devolucaoCompraList where valor equals to UPDATED_VALOR
        defaultDevolucaoCompraShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where valor is not null
        defaultDevolucaoCompraShouldBeFound("valor.specified=true");

        // Get all the devolucaoCompraList where valor is null
        defaultDevolucaoCompraShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where valor is greater than or equal to DEFAULT_VALOR
        defaultDevolucaoCompraShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the devolucaoCompraList where valor is greater than or equal to UPDATED_VALOR
        defaultDevolucaoCompraShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where valor is less than or equal to DEFAULT_VALOR
        defaultDevolucaoCompraShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the devolucaoCompraList where valor is less than or equal to SMALLER_VALOR
        defaultDevolucaoCompraShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where valor is less than DEFAULT_VALOR
        defaultDevolucaoCompraShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the devolucaoCompraList where valor is less than UPDATED_VALOR
        defaultDevolucaoCompraShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where valor is greater than DEFAULT_VALOR
        defaultDevolucaoCompraShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the devolucaoCompraList where valor is greater than SMALLER_VALOR
        defaultDevolucaoCompraShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllDevolucaoComprasByDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where desconto equals to DEFAULT_DESCONTO
        defaultDevolucaoCompraShouldBeFound("desconto.equals=" + DEFAULT_DESCONTO);

        // Get all the devolucaoCompraList where desconto equals to UPDATED_DESCONTO
        defaultDevolucaoCompraShouldNotBeFound("desconto.equals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDescontoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where desconto not equals to DEFAULT_DESCONTO
        defaultDevolucaoCompraShouldNotBeFound("desconto.notEquals=" + DEFAULT_DESCONTO);

        // Get all the devolucaoCompraList where desconto not equals to UPDATED_DESCONTO
        defaultDevolucaoCompraShouldBeFound("desconto.notEquals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where desconto in DEFAULT_DESCONTO or UPDATED_DESCONTO
        defaultDevolucaoCompraShouldBeFound("desconto.in=" + DEFAULT_DESCONTO + "," + UPDATED_DESCONTO);

        // Get all the devolucaoCompraList where desconto equals to UPDATED_DESCONTO
        defaultDevolucaoCompraShouldNotBeFound("desconto.in=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where desconto is not null
        defaultDevolucaoCompraShouldBeFound("desconto.specified=true");

        // Get all the devolucaoCompraList where desconto is null
        defaultDevolucaoCompraShouldNotBeFound("desconto.specified=false");
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where desconto is greater than or equal to DEFAULT_DESCONTO
        defaultDevolucaoCompraShouldBeFound("desconto.greaterThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the devolucaoCompraList where desconto is greater than or equal to UPDATED_DESCONTO
        defaultDevolucaoCompraShouldNotBeFound("desconto.greaterThanOrEqual=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where desconto is less than or equal to DEFAULT_DESCONTO
        defaultDevolucaoCompraShouldBeFound("desconto.lessThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the devolucaoCompraList where desconto is less than or equal to SMALLER_DESCONTO
        defaultDevolucaoCompraShouldNotBeFound("desconto.lessThanOrEqual=" + SMALLER_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where desconto is less than DEFAULT_DESCONTO
        defaultDevolucaoCompraShouldNotBeFound("desconto.lessThan=" + DEFAULT_DESCONTO);

        // Get all the devolucaoCompraList where desconto is less than UPDATED_DESCONTO
        defaultDevolucaoCompraShouldBeFound("desconto.lessThan=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where desconto is greater than DEFAULT_DESCONTO
        defaultDevolucaoCompraShouldNotBeFound("desconto.greaterThan=" + DEFAULT_DESCONTO);

        // Get all the devolucaoCompraList where desconto is greater than SMALLER_DESCONTO
        defaultDevolucaoCompraShouldBeFound("desconto.greaterThan=" + SMALLER_DESCONTO);
    }


    @Test
    @Transactional
    public void getAllDevolucaoComprasByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where data equals to DEFAULT_DATA
        defaultDevolucaoCompraShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the devolucaoCompraList where data equals to UPDATED_DATA
        defaultDevolucaoCompraShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where data not equals to DEFAULT_DATA
        defaultDevolucaoCompraShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the devolucaoCompraList where data not equals to UPDATED_DATA
        defaultDevolucaoCompraShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDataIsInShouldWork() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where data in DEFAULT_DATA or UPDATED_DATA
        defaultDevolucaoCompraShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the devolucaoCompraList where data equals to UPDATED_DATA
        defaultDevolucaoCompraShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where data is not null
        defaultDevolucaoCompraShouldBeFound("data.specified=true");

        // Get all the devolucaoCompraList where data is null
        defaultDevolucaoCompraShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where data is greater than or equal to DEFAULT_DATA
        defaultDevolucaoCompraShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the devolucaoCompraList where data is greater than or equal to UPDATED_DATA
        defaultDevolucaoCompraShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where data is less than or equal to DEFAULT_DATA
        defaultDevolucaoCompraShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the devolucaoCompraList where data is less than or equal to SMALLER_DATA
        defaultDevolucaoCompraShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where data is less than DEFAULT_DATA
        defaultDevolucaoCompraShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the devolucaoCompraList where data is less than UPDATED_DATA
        defaultDevolucaoCompraShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDevolucaoComprasByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        // Get all the devolucaoCompraList where data is greater than DEFAULT_DATA
        defaultDevolucaoCompraShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the devolucaoCompraList where data is greater than SMALLER_DATA
        defaultDevolucaoCompraShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllDevolucaoComprasByItemIsEqualToSomething() throws Exception {
        // Get already existing entity
        ItemCompra item = devolucaoCompra.getItem();
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);
        Long itemId = item.getId();

        // Get all the devolucaoCompraList where item equals to itemId
        defaultDevolucaoCompraShouldBeFound("itemId.equals=" + itemId);

        // Get all the devolucaoCompraList where item equals to itemId + 1
        defaultDevolucaoCompraShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDevolucaoCompraShouldBeFound(String filter) throws Exception {
        restDevolucaoCompraMockMvc.perform(get("/api/devolucao-compras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devolucaoCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restDevolucaoCompraMockMvc.perform(get("/api/devolucao-compras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDevolucaoCompraShouldNotBeFound(String filter) throws Exception {
        restDevolucaoCompraMockMvc.perform(get("/api/devolucao-compras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDevolucaoCompraMockMvc.perform(get("/api/devolucao-compras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDevolucaoCompra() throws Exception {
        // Get the devolucaoCompra
        restDevolucaoCompraMockMvc.perform(get("/api/devolucao-compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevolucaoCompra() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        int databaseSizeBeforeUpdate = devolucaoCompraRepository.findAll().size();

        // Update the devolucaoCompra
        DevolucaoCompra updatedDevolucaoCompra = devolucaoCompraRepository.findById(devolucaoCompra.getId()).get();
        // Disconnect from session so that the updates on updatedDevolucaoCompra are not directly saved in db
        em.detach(updatedDevolucaoCompra);
        updatedDevolucaoCompra
            .quantidade(UPDATED_QUANTIDADE)
            .valor(UPDATED_VALOR)
            .desconto(UPDATED_DESCONTO)
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO);
        DevolucaoCompraDTO devolucaoCompraDTO = devolucaoCompraMapper.toDto(updatedDevolucaoCompra);

        restDevolucaoCompraMockMvc.perform(put("/api/devolucao-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devolucaoCompraDTO)))
            .andExpect(status().isOk());

        // Validate the DevolucaoCompra in the database
        List<DevolucaoCompra> devolucaoCompraList = devolucaoCompraRepository.findAll();
        assertThat(devolucaoCompraList).hasSize(databaseSizeBeforeUpdate);
        DevolucaoCompra testDevolucaoCompra = devolucaoCompraList.get(devolucaoCompraList.size() - 1);
        assertThat(testDevolucaoCompra.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testDevolucaoCompra.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testDevolucaoCompra.getDesconto()).isEqualTo(UPDATED_DESCONTO);
        assertThat(testDevolucaoCompra.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDevolucaoCompra.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingDevolucaoCompra() throws Exception {
        int databaseSizeBeforeUpdate = devolucaoCompraRepository.findAll().size();

        // Create the DevolucaoCompra
        DevolucaoCompraDTO devolucaoCompraDTO = devolucaoCompraMapper.toDto(devolucaoCompra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevolucaoCompraMockMvc.perform(put("/api/devolucao-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devolucaoCompraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DevolucaoCompra in the database
        List<DevolucaoCompra> devolucaoCompraList = devolucaoCompraRepository.findAll();
        assertThat(devolucaoCompraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDevolucaoCompra() throws Exception {
        // Initialize the database
        devolucaoCompraRepository.saveAndFlush(devolucaoCompra);

        int databaseSizeBeforeDelete = devolucaoCompraRepository.findAll().size();

        // Delete the devolucaoCompra
        restDevolucaoCompraMockMvc.perform(delete("/api/devolucao-compras/{id}", devolucaoCompra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DevolucaoCompra> devolucaoCompraList = devolucaoCompraRepository.findAll();
        assertThat(devolucaoCompraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
