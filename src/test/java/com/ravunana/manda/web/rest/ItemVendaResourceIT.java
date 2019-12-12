package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.ItemVenda;
import com.ravunana.manda.domain.Venda;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.domain.FluxoDocumento;
import com.ravunana.manda.repository.ItemVendaRepository;
import com.ravunana.manda.service.ItemVendaService;
import com.ravunana.manda.service.dto.ItemVendaDTO;
import com.ravunana.manda.service.mapper.ItemVendaMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.ItemVendaCriteria;
import com.ravunana.manda.service.ItemVendaQueryService;

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
import java.math.BigDecimal;
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
 * Integration tests for the {@link ItemVendaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ItemVendaResourceIT {

    private static final Double DEFAULT_QUANTIDADE = 1D;
    private static final Double UPDATED_QUANTIDADE = 2D;
    private static final Double SMALLER_QUANTIDADE = 1D - 1D;

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(1);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(0 - 1);

    private static final Double DEFAULT_DESCONTO = 0D;
    private static final Double UPDATED_DESCONTO = 1D;
    private static final Double SMALLER_DESCONTO = 0D - 1D;

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    private ItemVendaMapper itemVendaMapper;

    @Autowired
    private ItemVendaService itemVendaService;

    @Autowired
    private ItemVendaQueryService itemVendaQueryService;

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

    private MockMvc restItemVendaMockMvc;

    private ItemVenda itemVenda;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemVendaResource itemVendaResource = new ItemVendaResource(itemVendaService, itemVendaQueryService);
        this.restItemVendaMockMvc = MockMvcBuilders.standaloneSetup(itemVendaResource)
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
    public static ItemVenda createEntity(EntityManager em) {
        ItemVenda itemVenda = new ItemVenda()
            .quantidade(DEFAULT_QUANTIDADE)
            .valor(DEFAULT_VALOR)
            .desconto(DEFAULT_DESCONTO)
            .data(DEFAULT_DATA);
        // Add required entity
        Venda venda;
        if (TestUtil.findAll(em, Venda.class).isEmpty()) {
            venda = VendaResourceIT.createEntity(em);
            em.persist(venda);
            em.flush();
        } else {
            venda = TestUtil.findAll(em, Venda.class).get(0);
        }
        itemVenda.setVenda(venda);
        return itemVenda;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemVenda createUpdatedEntity(EntityManager em) {
        ItemVenda itemVenda = new ItemVenda()
            .quantidade(UPDATED_QUANTIDADE)
            .valor(UPDATED_VALOR)
            .desconto(UPDATED_DESCONTO)
            .data(UPDATED_DATA);
        // Add required entity
        Venda venda;
        if (TestUtil.findAll(em, Venda.class).isEmpty()) {
            venda = VendaResourceIT.createUpdatedEntity(em);
            em.persist(venda);
            em.flush();
        } else {
            venda = TestUtil.findAll(em, Venda.class).get(0);
        }
        itemVenda.setVenda(venda);
        return itemVenda;
    }

    @BeforeEach
    public void initTest() {
        itemVenda = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemVenda() throws Exception {
        int databaseSizeBeforeCreate = itemVendaRepository.findAll().size();

        // Create the ItemVenda
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(itemVenda);
        restItemVendaMockMvc.perform(post("/api/item-vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemVendaDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeCreate + 1);
        ItemVenda testItemVenda = itemVendaList.get(itemVendaList.size() - 1);
        assertThat(testItemVenda.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testItemVenda.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testItemVenda.getDesconto()).isEqualTo(DEFAULT_DESCONTO);
        assertThat(testItemVenda.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createItemVendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemVendaRepository.findAll().size();

        // Create the ItemVenda with an existing ID
        itemVenda.setId(1L);
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(itemVenda);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemVendaMockMvc.perform(post("/api/item-vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemVendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllItemVendas() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList
        restItemVendaMockMvc.perform(get("/api/item-vendas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemVenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getItemVenda() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get the itemVenda
        restItemVendaMockMvc.perform(get("/api/item-vendas/{id}", itemVenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemVenda.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.doubleValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.desconto").value(DEFAULT_DESCONTO.doubleValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)));
    }


    @Test
    @Transactional
    public void getItemVendasByIdFiltering() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        Long id = itemVenda.getId();

        defaultItemVendaShouldBeFound("id.equals=" + id);
        defaultItemVendaShouldNotBeFound("id.notEquals=" + id);

        defaultItemVendaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemVendaShouldNotBeFound("id.greaterThan=" + id);

        defaultItemVendaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemVendaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllItemVendasByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade equals to DEFAULT_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the itemVendaList where quantidade equals to UPDATED_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllItemVendasByQuantidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade not equals to DEFAULT_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.notEquals=" + DEFAULT_QUANTIDADE);

        // Get all the itemVendaList where quantidade not equals to UPDATED_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.notEquals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllItemVendasByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the itemVendaList where quantidade equals to UPDATED_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllItemVendasByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade is not null
        defaultItemVendaShouldBeFound("quantidade.specified=true");

        // Get all the itemVendaList where quantidade is null
        defaultItemVendaShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemVendasByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the itemVendaList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllItemVendasByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the itemVendaList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllItemVendasByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade is less than DEFAULT_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the itemVendaList where quantidade is less than UPDATED_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllItemVendasByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultItemVendaShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the itemVendaList where quantidade is greater than SMALLER_QUANTIDADE
        defaultItemVendaShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }


    @Test
    @Transactional
    public void getAllItemVendasByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valor equals to DEFAULT_VALOR
        defaultItemVendaShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the itemVendaList where valor equals to UPDATED_VALOR
        defaultItemVendaShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllItemVendasByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valor not equals to DEFAULT_VALOR
        defaultItemVendaShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the itemVendaList where valor not equals to UPDATED_VALOR
        defaultItemVendaShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllItemVendasByValorIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultItemVendaShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the itemVendaList where valor equals to UPDATED_VALOR
        defaultItemVendaShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllItemVendasByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valor is not null
        defaultItemVendaShouldBeFound("valor.specified=true");

        // Get all the itemVendaList where valor is null
        defaultItemVendaShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemVendasByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valor is greater than or equal to DEFAULT_VALOR
        defaultItemVendaShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the itemVendaList where valor is greater than or equal to UPDATED_VALOR
        defaultItemVendaShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllItemVendasByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valor is less than or equal to DEFAULT_VALOR
        defaultItemVendaShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the itemVendaList where valor is less than or equal to SMALLER_VALOR
        defaultItemVendaShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllItemVendasByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valor is less than DEFAULT_VALOR
        defaultItemVendaShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the itemVendaList where valor is less than UPDATED_VALOR
        defaultItemVendaShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllItemVendasByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where valor is greater than DEFAULT_VALOR
        defaultItemVendaShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the itemVendaList where valor is greater than SMALLER_VALOR
        defaultItemVendaShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllItemVendasByDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where desconto equals to DEFAULT_DESCONTO
        defaultItemVendaShouldBeFound("desconto.equals=" + DEFAULT_DESCONTO);

        // Get all the itemVendaList where desconto equals to UPDATED_DESCONTO
        defaultItemVendaShouldNotBeFound("desconto.equals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllItemVendasByDescontoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where desconto not equals to DEFAULT_DESCONTO
        defaultItemVendaShouldNotBeFound("desconto.notEquals=" + DEFAULT_DESCONTO);

        // Get all the itemVendaList where desconto not equals to UPDATED_DESCONTO
        defaultItemVendaShouldBeFound("desconto.notEquals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllItemVendasByDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where desconto in DEFAULT_DESCONTO or UPDATED_DESCONTO
        defaultItemVendaShouldBeFound("desconto.in=" + DEFAULT_DESCONTO + "," + UPDATED_DESCONTO);

        // Get all the itemVendaList where desconto equals to UPDATED_DESCONTO
        defaultItemVendaShouldNotBeFound("desconto.in=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllItemVendasByDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where desconto is not null
        defaultItemVendaShouldBeFound("desconto.specified=true");

        // Get all the itemVendaList where desconto is null
        defaultItemVendaShouldNotBeFound("desconto.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemVendasByDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where desconto is greater than or equal to DEFAULT_DESCONTO
        defaultItemVendaShouldBeFound("desconto.greaterThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the itemVendaList where desconto is greater than or equal to (DEFAULT_DESCONTO + 1)
        defaultItemVendaShouldNotBeFound("desconto.greaterThanOrEqual=" + (DEFAULT_DESCONTO + 1));
    }

    @Test
    @Transactional
    public void getAllItemVendasByDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where desconto is less than or equal to DEFAULT_DESCONTO
        defaultItemVendaShouldBeFound("desconto.lessThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the itemVendaList where desconto is less than or equal to SMALLER_DESCONTO
        defaultItemVendaShouldNotBeFound("desconto.lessThanOrEqual=" + SMALLER_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllItemVendasByDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where desconto is less than DEFAULT_DESCONTO
        defaultItemVendaShouldNotBeFound("desconto.lessThan=" + DEFAULT_DESCONTO);

        // Get all the itemVendaList where desconto is less than (DEFAULT_DESCONTO + 1)
        defaultItemVendaShouldBeFound("desconto.lessThan=" + (DEFAULT_DESCONTO + 1));
    }

    @Test
    @Transactional
    public void getAllItemVendasByDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where desconto is greater than DEFAULT_DESCONTO
        defaultItemVendaShouldNotBeFound("desconto.greaterThan=" + DEFAULT_DESCONTO);

        // Get all the itemVendaList where desconto is greater than SMALLER_DESCONTO
        defaultItemVendaShouldBeFound("desconto.greaterThan=" + SMALLER_DESCONTO);
    }


    @Test
    @Transactional
    public void getAllItemVendasByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where data equals to DEFAULT_DATA
        defaultItemVendaShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the itemVendaList where data equals to UPDATED_DATA
        defaultItemVendaShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllItemVendasByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where data not equals to DEFAULT_DATA
        defaultItemVendaShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the itemVendaList where data not equals to UPDATED_DATA
        defaultItemVendaShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllItemVendasByDataIsInShouldWork() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where data in DEFAULT_DATA or UPDATED_DATA
        defaultItemVendaShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the itemVendaList where data equals to UPDATED_DATA
        defaultItemVendaShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllItemVendasByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where data is not null
        defaultItemVendaShouldBeFound("data.specified=true");

        // Get all the itemVendaList where data is null
        defaultItemVendaShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemVendasByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where data is greater than or equal to DEFAULT_DATA
        defaultItemVendaShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the itemVendaList where data is greater than or equal to UPDATED_DATA
        defaultItemVendaShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllItemVendasByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where data is less than or equal to DEFAULT_DATA
        defaultItemVendaShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the itemVendaList where data is less than or equal to SMALLER_DATA
        defaultItemVendaShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllItemVendasByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where data is less than DEFAULT_DATA
        defaultItemVendaShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the itemVendaList where data is less than UPDATED_DATA
        defaultItemVendaShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllItemVendasByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        // Get all the itemVendaList where data is greater than DEFAULT_DATA
        defaultItemVendaShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the itemVendaList where data is greater than SMALLER_DATA
        defaultItemVendaShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllItemVendasByVendaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Venda venda = itemVenda.getVenda();
        itemVendaRepository.saveAndFlush(itemVenda);
        Long vendaId = venda.getId();

        // Get all the itemVendaList where venda equals to vendaId
        defaultItemVendaShouldBeFound("vendaId.equals=" + vendaId);

        // Get all the itemVendaList where venda equals to vendaId + 1
        defaultItemVendaShouldNotBeFound("vendaId.equals=" + (vendaId + 1));
    }


    @Test
    @Transactional
    public void getAllItemVendasByProdutoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);
        Produto produto = ProdutoResourceIT.createEntity(em);
        em.persist(produto);
        em.flush();
        itemVenda.setProduto(produto);
        itemVendaRepository.saveAndFlush(itemVenda);
        Long produtoId = produto.getId();

        // Get all the itemVendaList where produto equals to produtoId
        defaultItemVendaShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the itemVendaList where produto equals to produtoId + 1
        defaultItemVendaShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }


    @Test
    @Transactional
    public void getAllItemVendasByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);
        FluxoDocumento status = FluxoDocumentoResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        itemVenda.setStatus(status);
        itemVendaRepository.saveAndFlush(itemVenda);
        Long statusId = status.getId();

        // Get all the itemVendaList where status equals to statusId
        defaultItemVendaShouldBeFound("statusId.equals=" + statusId);

        // Get all the itemVendaList where status equals to statusId + 1
        defaultItemVendaShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemVendaShouldBeFound(String filter) throws Exception {
        restItemVendaMockMvc.perform(get("/api/item-vendas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemVenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));

        // Check, that the count call also returns 1
        restItemVendaMockMvc.perform(get("/api/item-vendas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemVendaShouldNotBeFound(String filter) throws Exception {
        restItemVendaMockMvc.perform(get("/api/item-vendas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemVendaMockMvc.perform(get("/api/item-vendas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItemVenda() throws Exception {
        // Get the itemVenda
        restItemVendaMockMvc.perform(get("/api/item-vendas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemVenda() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        int databaseSizeBeforeUpdate = itemVendaRepository.findAll().size();

        // Update the itemVenda
        ItemVenda updatedItemVenda = itemVendaRepository.findById(itemVenda.getId()).get();
        // Disconnect from session so that the updates on updatedItemVenda are not directly saved in db
        em.detach(updatedItemVenda);
        updatedItemVenda
            .quantidade(UPDATED_QUANTIDADE)
            .valor(UPDATED_VALOR)
            .desconto(UPDATED_DESCONTO)
            .data(UPDATED_DATA);
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(updatedItemVenda);

        restItemVendaMockMvc.perform(put("/api/item-vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemVendaDTO)))
            .andExpect(status().isOk());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeUpdate);
        ItemVenda testItemVenda = itemVendaList.get(itemVendaList.size() - 1);
        assertThat(testItemVenda.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testItemVenda.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testItemVenda.getDesconto()).isEqualTo(UPDATED_DESCONTO);
        assertThat(testItemVenda.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingItemVenda() throws Exception {
        int databaseSizeBeforeUpdate = itemVendaRepository.findAll().size();

        // Create the ItemVenda
        ItemVendaDTO itemVendaDTO = itemVendaMapper.toDto(itemVenda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemVendaMockMvc.perform(put("/api/item-vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemVendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemVenda in the database
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemVenda() throws Exception {
        // Initialize the database
        itemVendaRepository.saveAndFlush(itemVenda);

        int databaseSizeBeforeDelete = itemVendaRepository.findAll().size();

        // Delete the itemVenda
        restItemVendaMockMvc.perform(delete("/api/item-vendas/{id}", itemVenda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemVenda> itemVendaList = itemVendaRepository.findAll();
        assertThat(itemVendaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
