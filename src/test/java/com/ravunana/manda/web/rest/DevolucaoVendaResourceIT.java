package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.DevolucaoVenda;
import com.ravunana.manda.domain.ItemVenda;
import com.ravunana.manda.repository.DevolucaoVendaRepository;
import com.ravunana.manda.service.DevolucaoVendaService;
import com.ravunana.manda.service.dto.DevolucaoVendaDTO;
import com.ravunana.manda.service.mapper.DevolucaoVendaMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.DevolucaoVendaCriteria;
import com.ravunana.manda.service.DevolucaoVendaQueryService;

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
 * Integration tests for the {@link DevolucaoVendaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class DevolucaoVendaResourceIT {

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
    private DevolucaoVendaRepository devolucaoVendaRepository;

    @Autowired
    private DevolucaoVendaMapper devolucaoVendaMapper;

    @Autowired
    private DevolucaoVendaService devolucaoVendaService;

    @Autowired
    private DevolucaoVendaQueryService devolucaoVendaQueryService;

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

    private MockMvc restDevolucaoVendaMockMvc;

    private DevolucaoVenda devolucaoVenda;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DevolucaoVendaResource devolucaoVendaResource = new DevolucaoVendaResource(devolucaoVendaService, devolucaoVendaQueryService);
        this.restDevolucaoVendaMockMvc = MockMvcBuilders.standaloneSetup(devolucaoVendaResource)
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
    public static DevolucaoVenda createEntity(EntityManager em) {
        DevolucaoVenda devolucaoVenda = new DevolucaoVenda()
            .quantidade(DEFAULT_QUANTIDADE)
            .valor(DEFAULT_VALOR)
            .desconto(DEFAULT_DESCONTO)
            .data(DEFAULT_DATA)
            .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        ItemVenda itemVenda;
        if (TestUtil.findAll(em, ItemVenda.class).isEmpty()) {
            itemVenda = ItemVendaResourceIT.createEntity(em);
            em.persist(itemVenda);
            em.flush();
        } else {
            itemVenda = TestUtil.findAll(em, ItemVenda.class).get(0);
        }
        devolucaoVenda.setItem(itemVenda);
        return devolucaoVenda;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DevolucaoVenda createUpdatedEntity(EntityManager em) {
        DevolucaoVenda devolucaoVenda = new DevolucaoVenda()
            .quantidade(UPDATED_QUANTIDADE)
            .valor(UPDATED_VALOR)
            .desconto(UPDATED_DESCONTO)
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO);
        // Add required entity
        ItemVenda itemVenda;
        if (TestUtil.findAll(em, ItemVenda.class).isEmpty()) {
            itemVenda = ItemVendaResourceIT.createUpdatedEntity(em);
            em.persist(itemVenda);
            em.flush();
        } else {
            itemVenda = TestUtil.findAll(em, ItemVenda.class).get(0);
        }
        devolucaoVenda.setItem(itemVenda);
        return devolucaoVenda;
    }

    @BeforeEach
    public void initTest() {
        devolucaoVenda = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevolucaoVenda() throws Exception {
        int databaseSizeBeforeCreate = devolucaoVendaRepository.findAll().size();

        // Create the DevolucaoVenda
        DevolucaoVendaDTO devolucaoVendaDTO = devolucaoVendaMapper.toDto(devolucaoVenda);
        restDevolucaoVendaMockMvc.perform(post("/api/devolucao-vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devolucaoVendaDTO)))
            .andExpect(status().isCreated());

        // Validate the DevolucaoVenda in the database
        List<DevolucaoVenda> devolucaoVendaList = devolucaoVendaRepository.findAll();
        assertThat(devolucaoVendaList).hasSize(databaseSizeBeforeCreate + 1);
        DevolucaoVenda testDevolucaoVenda = devolucaoVendaList.get(devolucaoVendaList.size() - 1);
        assertThat(testDevolucaoVenda.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testDevolucaoVenda.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testDevolucaoVenda.getDesconto()).isEqualTo(DEFAULT_DESCONTO);
        assertThat(testDevolucaoVenda.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDevolucaoVenda.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createDevolucaoVendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = devolucaoVendaRepository.findAll().size();

        // Create the DevolucaoVenda with an existing ID
        devolucaoVenda.setId(1L);
        DevolucaoVendaDTO devolucaoVendaDTO = devolucaoVendaMapper.toDto(devolucaoVenda);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevolucaoVendaMockMvc.perform(post("/api/devolucao-vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devolucaoVendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DevolucaoVenda in the database
        List<DevolucaoVenda> devolucaoVendaList = devolucaoVendaRepository.findAll();
        assertThat(devolucaoVendaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDevolucaoVendas() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList
        restDevolucaoVendaMockMvc.perform(get("/api/devolucao-vendas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devolucaoVenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getDevolucaoVenda() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get the devolucaoVenda
        restDevolucaoVendaMockMvc.perform(get("/api/devolucao-vendas/{id}", devolucaoVenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(devolucaoVenda.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.doubleValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.desconto").value(DEFAULT_DESCONTO.doubleValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }


    @Test
    @Transactional
    public void getDevolucaoVendasByIdFiltering() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        Long id = devolucaoVenda.getId();

        defaultDevolucaoVendaShouldBeFound("id.equals=" + id);
        defaultDevolucaoVendaShouldNotBeFound("id.notEquals=" + id);

        defaultDevolucaoVendaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDevolucaoVendaShouldNotBeFound("id.greaterThan=" + id);

        defaultDevolucaoVendaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDevolucaoVendaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDevolucaoVendasByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where quantidade equals to DEFAULT_QUANTIDADE
        defaultDevolucaoVendaShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the devolucaoVendaList where quantidade equals to UPDATED_QUANTIDADE
        defaultDevolucaoVendaShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByQuantidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where quantidade not equals to DEFAULT_QUANTIDADE
        defaultDevolucaoVendaShouldNotBeFound("quantidade.notEquals=" + DEFAULT_QUANTIDADE);

        // Get all the devolucaoVendaList where quantidade not equals to UPDATED_QUANTIDADE
        defaultDevolucaoVendaShouldBeFound("quantidade.notEquals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultDevolucaoVendaShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the devolucaoVendaList where quantidade equals to UPDATED_QUANTIDADE
        defaultDevolucaoVendaShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where quantidade is not null
        defaultDevolucaoVendaShouldBeFound("quantidade.specified=true");

        // Get all the devolucaoVendaList where quantidade is null
        defaultDevolucaoVendaShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultDevolucaoVendaShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the devolucaoVendaList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultDevolucaoVendaShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultDevolucaoVendaShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the devolucaoVendaList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultDevolucaoVendaShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where quantidade is less than DEFAULT_QUANTIDADE
        defaultDevolucaoVendaShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the devolucaoVendaList where quantidade is less than UPDATED_QUANTIDADE
        defaultDevolucaoVendaShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultDevolucaoVendaShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the devolucaoVendaList where quantidade is greater than SMALLER_QUANTIDADE
        defaultDevolucaoVendaShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }


    @Test
    @Transactional
    public void getAllDevolucaoVendasByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where valor equals to DEFAULT_VALOR
        defaultDevolucaoVendaShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the devolucaoVendaList where valor equals to UPDATED_VALOR
        defaultDevolucaoVendaShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where valor not equals to DEFAULT_VALOR
        defaultDevolucaoVendaShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the devolucaoVendaList where valor not equals to UPDATED_VALOR
        defaultDevolucaoVendaShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByValorIsInShouldWork() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultDevolucaoVendaShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the devolucaoVendaList where valor equals to UPDATED_VALOR
        defaultDevolucaoVendaShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where valor is not null
        defaultDevolucaoVendaShouldBeFound("valor.specified=true");

        // Get all the devolucaoVendaList where valor is null
        defaultDevolucaoVendaShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where valor is greater than or equal to DEFAULT_VALOR
        defaultDevolucaoVendaShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the devolucaoVendaList where valor is greater than or equal to UPDATED_VALOR
        defaultDevolucaoVendaShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where valor is less than or equal to DEFAULT_VALOR
        defaultDevolucaoVendaShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the devolucaoVendaList where valor is less than or equal to SMALLER_VALOR
        defaultDevolucaoVendaShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where valor is less than DEFAULT_VALOR
        defaultDevolucaoVendaShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the devolucaoVendaList where valor is less than UPDATED_VALOR
        defaultDevolucaoVendaShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where valor is greater than DEFAULT_VALOR
        defaultDevolucaoVendaShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the devolucaoVendaList where valor is greater than SMALLER_VALOR
        defaultDevolucaoVendaShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllDevolucaoVendasByDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where desconto equals to DEFAULT_DESCONTO
        defaultDevolucaoVendaShouldBeFound("desconto.equals=" + DEFAULT_DESCONTO);

        // Get all the devolucaoVendaList where desconto equals to UPDATED_DESCONTO
        defaultDevolucaoVendaShouldNotBeFound("desconto.equals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDescontoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where desconto not equals to DEFAULT_DESCONTO
        defaultDevolucaoVendaShouldNotBeFound("desconto.notEquals=" + DEFAULT_DESCONTO);

        // Get all the devolucaoVendaList where desconto not equals to UPDATED_DESCONTO
        defaultDevolucaoVendaShouldBeFound("desconto.notEquals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where desconto in DEFAULT_DESCONTO or UPDATED_DESCONTO
        defaultDevolucaoVendaShouldBeFound("desconto.in=" + DEFAULT_DESCONTO + "," + UPDATED_DESCONTO);

        // Get all the devolucaoVendaList where desconto equals to UPDATED_DESCONTO
        defaultDevolucaoVendaShouldNotBeFound("desconto.in=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where desconto is not null
        defaultDevolucaoVendaShouldBeFound("desconto.specified=true");

        // Get all the devolucaoVendaList where desconto is null
        defaultDevolucaoVendaShouldNotBeFound("desconto.specified=false");
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where desconto is greater than or equal to DEFAULT_DESCONTO
        defaultDevolucaoVendaShouldBeFound("desconto.greaterThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the devolucaoVendaList where desconto is greater than or equal to UPDATED_DESCONTO
        defaultDevolucaoVendaShouldNotBeFound("desconto.greaterThanOrEqual=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where desconto is less than or equal to DEFAULT_DESCONTO
        defaultDevolucaoVendaShouldBeFound("desconto.lessThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the devolucaoVendaList where desconto is less than or equal to SMALLER_DESCONTO
        defaultDevolucaoVendaShouldNotBeFound("desconto.lessThanOrEqual=" + SMALLER_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where desconto is less than DEFAULT_DESCONTO
        defaultDevolucaoVendaShouldNotBeFound("desconto.lessThan=" + DEFAULT_DESCONTO);

        // Get all the devolucaoVendaList where desconto is less than UPDATED_DESCONTO
        defaultDevolucaoVendaShouldBeFound("desconto.lessThan=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where desconto is greater than DEFAULT_DESCONTO
        defaultDevolucaoVendaShouldNotBeFound("desconto.greaterThan=" + DEFAULT_DESCONTO);

        // Get all the devolucaoVendaList where desconto is greater than SMALLER_DESCONTO
        defaultDevolucaoVendaShouldBeFound("desconto.greaterThan=" + SMALLER_DESCONTO);
    }


    @Test
    @Transactional
    public void getAllDevolucaoVendasByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where data equals to DEFAULT_DATA
        defaultDevolucaoVendaShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the devolucaoVendaList where data equals to UPDATED_DATA
        defaultDevolucaoVendaShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where data not equals to DEFAULT_DATA
        defaultDevolucaoVendaShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the devolucaoVendaList where data not equals to UPDATED_DATA
        defaultDevolucaoVendaShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDataIsInShouldWork() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where data in DEFAULT_DATA or UPDATED_DATA
        defaultDevolucaoVendaShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the devolucaoVendaList where data equals to UPDATED_DATA
        defaultDevolucaoVendaShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where data is not null
        defaultDevolucaoVendaShouldBeFound("data.specified=true");

        // Get all the devolucaoVendaList where data is null
        defaultDevolucaoVendaShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where data is greater than or equal to DEFAULT_DATA
        defaultDevolucaoVendaShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the devolucaoVendaList where data is greater than or equal to UPDATED_DATA
        defaultDevolucaoVendaShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where data is less than or equal to DEFAULT_DATA
        defaultDevolucaoVendaShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the devolucaoVendaList where data is less than or equal to SMALLER_DATA
        defaultDevolucaoVendaShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where data is less than DEFAULT_DATA
        defaultDevolucaoVendaShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the devolucaoVendaList where data is less than UPDATED_DATA
        defaultDevolucaoVendaShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllDevolucaoVendasByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        // Get all the devolucaoVendaList where data is greater than DEFAULT_DATA
        defaultDevolucaoVendaShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the devolucaoVendaList where data is greater than SMALLER_DATA
        defaultDevolucaoVendaShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllDevolucaoVendasByItemIsEqualToSomething() throws Exception {
        // Get already existing entity
        ItemVenda item = devolucaoVenda.getItem();
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);
        Long itemId = item.getId();

        // Get all the devolucaoVendaList where item equals to itemId
        defaultDevolucaoVendaShouldBeFound("itemId.equals=" + itemId);

        // Get all the devolucaoVendaList where item equals to itemId + 1
        defaultDevolucaoVendaShouldNotBeFound("itemId.equals=" + (itemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDevolucaoVendaShouldBeFound(String filter) throws Exception {
        restDevolucaoVendaMockMvc.perform(get("/api/devolucao-vendas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devolucaoVenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restDevolucaoVendaMockMvc.perform(get("/api/devolucao-vendas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDevolucaoVendaShouldNotBeFound(String filter) throws Exception {
        restDevolucaoVendaMockMvc.perform(get("/api/devolucao-vendas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDevolucaoVendaMockMvc.perform(get("/api/devolucao-vendas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDevolucaoVenda() throws Exception {
        // Get the devolucaoVenda
        restDevolucaoVendaMockMvc.perform(get("/api/devolucao-vendas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevolucaoVenda() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        int databaseSizeBeforeUpdate = devolucaoVendaRepository.findAll().size();

        // Update the devolucaoVenda
        DevolucaoVenda updatedDevolucaoVenda = devolucaoVendaRepository.findById(devolucaoVenda.getId()).get();
        // Disconnect from session so that the updates on updatedDevolucaoVenda are not directly saved in db
        em.detach(updatedDevolucaoVenda);
        updatedDevolucaoVenda
            .quantidade(UPDATED_QUANTIDADE)
            .valor(UPDATED_VALOR)
            .desconto(UPDATED_DESCONTO)
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO);
        DevolucaoVendaDTO devolucaoVendaDTO = devolucaoVendaMapper.toDto(updatedDevolucaoVenda);

        restDevolucaoVendaMockMvc.perform(put("/api/devolucao-vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devolucaoVendaDTO)))
            .andExpect(status().isOk());

        // Validate the DevolucaoVenda in the database
        List<DevolucaoVenda> devolucaoVendaList = devolucaoVendaRepository.findAll();
        assertThat(devolucaoVendaList).hasSize(databaseSizeBeforeUpdate);
        DevolucaoVenda testDevolucaoVenda = devolucaoVendaList.get(devolucaoVendaList.size() - 1);
        assertThat(testDevolucaoVenda.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testDevolucaoVenda.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testDevolucaoVenda.getDesconto()).isEqualTo(UPDATED_DESCONTO);
        assertThat(testDevolucaoVenda.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDevolucaoVenda.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingDevolucaoVenda() throws Exception {
        int databaseSizeBeforeUpdate = devolucaoVendaRepository.findAll().size();

        // Create the DevolucaoVenda
        DevolucaoVendaDTO devolucaoVendaDTO = devolucaoVendaMapper.toDto(devolucaoVenda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevolucaoVendaMockMvc.perform(put("/api/devolucao-vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devolucaoVendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DevolucaoVenda in the database
        List<DevolucaoVenda> devolucaoVendaList = devolucaoVendaRepository.findAll();
        assertThat(devolucaoVendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDevolucaoVenda() throws Exception {
        // Initialize the database
        devolucaoVendaRepository.saveAndFlush(devolucaoVenda);

        int databaseSizeBeforeDelete = devolucaoVendaRepository.findAll().size();

        // Delete the devolucaoVenda
        restDevolucaoVendaMockMvc.perform(delete("/api/devolucao-vendas/{id}", devolucaoVenda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DevolucaoVenda> devolucaoVendaList = devolucaoVendaRepository.findAll();
        assertThat(devolucaoVendaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
