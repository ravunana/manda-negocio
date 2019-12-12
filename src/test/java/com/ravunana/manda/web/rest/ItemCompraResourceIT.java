package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.ItemCompra;
import com.ravunana.manda.domain.User;
import com.ravunana.manda.domain.Compra;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.domain.Fornecedor;
import com.ravunana.manda.domain.FluxoDocumento;
import com.ravunana.manda.repository.ItemCompraRepository;
import com.ravunana.manda.service.ItemCompraService;
import com.ravunana.manda.service.dto.ItemCompraDTO;
import com.ravunana.manda.service.mapper.ItemCompraMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.ItemCompraCriteria;
import com.ravunana.manda.service.ItemCompraQueryService;

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
 * Integration tests for the {@link ItemCompraResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ItemCompraResourceIT {

    private static final Double DEFAULT_QUANTIDADE = 1D;
    private static final Double UPDATED_QUANTIDADE = 2D;
    private static final Double SMALLER_QUANTIDADE = 1D - 1D;

    private static final Double DEFAULT_DESCONTO = 0D;
    private static final Double UPDATED_DESCONTO = 1D;
    private static final Double SMALLER_DESCONTO = 0D - 1D;

    private static final ZonedDateTime DEFAULT_DATA_SOLICITACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_SOLICITACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_SOLICITACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATA_ENTREGA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_ENTREGA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA_ENTREGA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(1 - 1);

    @Autowired
    private ItemCompraRepository itemCompraRepository;

    @Autowired
    private ItemCompraMapper itemCompraMapper;

    @Autowired
    private ItemCompraService itemCompraService;

    @Autowired
    private ItemCompraQueryService itemCompraQueryService;

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

    private MockMvc restItemCompraMockMvc;

    private ItemCompra itemCompra;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemCompraResource itemCompraResource = new ItemCompraResource(itemCompraService, itemCompraQueryService);
        this.restItemCompraMockMvc = MockMvcBuilders.standaloneSetup(itemCompraResource)
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
    public static ItemCompra createEntity(EntityManager em) {
        ItemCompra itemCompra = new ItemCompra()
            .quantidade(DEFAULT_QUANTIDADE)
            .desconto(DEFAULT_DESCONTO)
            .dataSolicitacao(DEFAULT_DATA_SOLICITACAO)
            .dataEntrega(DEFAULT_DATA_ENTREGA)
            .descricao(DEFAULT_DESCRICAO)
            .valor(DEFAULT_VALOR);
        // Add required entity
        Compra compra;
        if (TestUtil.findAll(em, Compra.class).isEmpty()) {
            compra = CompraResourceIT.createEntity(em);
            em.persist(compra);
            em.flush();
        } else {
            compra = TestUtil.findAll(em, Compra.class).get(0);
        }
        itemCompra.setCompra(compra);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        itemCompra.setProduto(produto);
        // Add required entity
        FluxoDocumento fluxoDocumento;
        if (TestUtil.findAll(em, FluxoDocumento.class).isEmpty()) {
            fluxoDocumento = FluxoDocumentoResourceIT.createEntity(em);
            em.persist(fluxoDocumento);
            em.flush();
        } else {
            fluxoDocumento = TestUtil.findAll(em, FluxoDocumento.class).get(0);
        }
        itemCompra.setStatus(fluxoDocumento);
        return itemCompra;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemCompra createUpdatedEntity(EntityManager em) {
        ItemCompra itemCompra = new ItemCompra()
            .quantidade(UPDATED_QUANTIDADE)
            .desconto(UPDATED_DESCONTO)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .descricao(UPDATED_DESCRICAO)
            .valor(UPDATED_VALOR);
        // Add required entity
        Compra compra;
        if (TestUtil.findAll(em, Compra.class).isEmpty()) {
            compra = CompraResourceIT.createUpdatedEntity(em);
            em.persist(compra);
            em.flush();
        } else {
            compra = TestUtil.findAll(em, Compra.class).get(0);
        }
        itemCompra.setCompra(compra);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createUpdatedEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        itemCompra.setProduto(produto);
        // Add required entity
        FluxoDocumento fluxoDocumento;
        if (TestUtil.findAll(em, FluxoDocumento.class).isEmpty()) {
            fluxoDocumento = FluxoDocumentoResourceIT.createUpdatedEntity(em);
            em.persist(fluxoDocumento);
            em.flush();
        } else {
            fluxoDocumento = TestUtil.findAll(em, FluxoDocumento.class).get(0);
        }
        itemCompra.setStatus(fluxoDocumento);
        return itemCompra;
    }

    @BeforeEach
    public void initTest() {
        itemCompra = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemCompra() throws Exception {
        int databaseSizeBeforeCreate = itemCompraRepository.findAll().size();

        // Create the ItemCompra
        ItemCompraDTO itemCompraDTO = itemCompraMapper.toDto(itemCompra);
        restItemCompraMockMvc.perform(post("/api/item-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCompraDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemCompra in the database
        List<ItemCompra> itemCompraList = itemCompraRepository.findAll();
        assertThat(itemCompraList).hasSize(databaseSizeBeforeCreate + 1);
        ItemCompra testItemCompra = itemCompraList.get(itemCompraList.size() - 1);
        assertThat(testItemCompra.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testItemCompra.getDesconto()).isEqualTo(DEFAULT_DESCONTO);
        assertThat(testItemCompra.getDataSolicitacao()).isEqualTo(DEFAULT_DATA_SOLICITACAO);
        assertThat(testItemCompra.getDataEntrega()).isEqualTo(DEFAULT_DATA_ENTREGA);
        assertThat(testItemCompra.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testItemCompra.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createItemCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemCompraRepository.findAll().size();

        // Create the ItemCompra with an existing ID
        itemCompra.setId(1L);
        ItemCompraDTO itemCompraDTO = itemCompraMapper.toDto(itemCompra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemCompraMockMvc.perform(post("/api/item-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCompraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemCompra in the database
        List<ItemCompra> itemCompraList = itemCompraRepository.findAll();
        assertThat(itemCompraList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllItemCompras() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList
        restItemCompraMockMvc.perform(get("/api/item-compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].dataSolicitacao").value(hasItem(sameInstant(DEFAULT_DATA_SOLICITACAO))))
            .andExpect(jsonPath("$.[*].dataEntrega").value(hasItem(sameInstant(DEFAULT_DATA_ENTREGA))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }
    
    @Test
    @Transactional
    public void getItemCompra() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get the itemCompra
        restItemCompraMockMvc.perform(get("/api/item-compras/{id}", itemCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemCompra.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.doubleValue()))
            .andExpect(jsonPath("$.desconto").value(DEFAULT_DESCONTO.doubleValue()))
            .andExpect(jsonPath("$.dataSolicitacao").value(sameInstant(DEFAULT_DATA_SOLICITACAO)))
            .andExpect(jsonPath("$.dataEntrega").value(sameInstant(DEFAULT_DATA_ENTREGA)))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }


    @Test
    @Transactional
    public void getItemComprasByIdFiltering() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        Long id = itemCompra.getId();

        defaultItemCompraShouldBeFound("id.equals=" + id);
        defaultItemCompraShouldNotBeFound("id.notEquals=" + id);

        defaultItemCompraShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemCompraShouldNotBeFound("id.greaterThan=" + id);

        defaultItemCompraShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemCompraShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllItemComprasByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where quantidade equals to DEFAULT_QUANTIDADE
        defaultItemCompraShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the itemCompraList where quantidade equals to UPDATED_QUANTIDADE
        defaultItemCompraShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllItemComprasByQuantidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where quantidade not equals to DEFAULT_QUANTIDADE
        defaultItemCompraShouldNotBeFound("quantidade.notEquals=" + DEFAULT_QUANTIDADE);

        // Get all the itemCompraList where quantidade not equals to UPDATED_QUANTIDADE
        defaultItemCompraShouldBeFound("quantidade.notEquals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllItemComprasByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultItemCompraShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the itemCompraList where quantidade equals to UPDATED_QUANTIDADE
        defaultItemCompraShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllItemComprasByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where quantidade is not null
        defaultItemCompraShouldBeFound("quantidade.specified=true");

        // Get all the itemCompraList where quantidade is null
        defaultItemCompraShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemComprasByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultItemCompraShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the itemCompraList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultItemCompraShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllItemComprasByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultItemCompraShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the itemCompraList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultItemCompraShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllItemComprasByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where quantidade is less than DEFAULT_QUANTIDADE
        defaultItemCompraShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the itemCompraList where quantidade is less than UPDATED_QUANTIDADE
        defaultItemCompraShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllItemComprasByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultItemCompraShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the itemCompraList where quantidade is greater than SMALLER_QUANTIDADE
        defaultItemCompraShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }


    @Test
    @Transactional
    public void getAllItemComprasByDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where desconto equals to DEFAULT_DESCONTO
        defaultItemCompraShouldBeFound("desconto.equals=" + DEFAULT_DESCONTO);

        // Get all the itemCompraList where desconto equals to UPDATED_DESCONTO
        defaultItemCompraShouldNotBeFound("desconto.equals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDescontoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where desconto not equals to DEFAULT_DESCONTO
        defaultItemCompraShouldNotBeFound("desconto.notEquals=" + DEFAULT_DESCONTO);

        // Get all the itemCompraList where desconto not equals to UPDATED_DESCONTO
        defaultItemCompraShouldBeFound("desconto.notEquals=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where desconto in DEFAULT_DESCONTO or UPDATED_DESCONTO
        defaultItemCompraShouldBeFound("desconto.in=" + DEFAULT_DESCONTO + "," + UPDATED_DESCONTO);

        // Get all the itemCompraList where desconto equals to UPDATED_DESCONTO
        defaultItemCompraShouldNotBeFound("desconto.in=" + UPDATED_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where desconto is not null
        defaultItemCompraShouldBeFound("desconto.specified=true");

        // Get all the itemCompraList where desconto is null
        defaultItemCompraShouldNotBeFound("desconto.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemComprasByDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where desconto is greater than or equal to DEFAULT_DESCONTO
        defaultItemCompraShouldBeFound("desconto.greaterThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the itemCompraList where desconto is greater than or equal to (DEFAULT_DESCONTO + 1)
        defaultItemCompraShouldNotBeFound("desconto.greaterThanOrEqual=" + (DEFAULT_DESCONTO + 1));
    }

    @Test
    @Transactional
    public void getAllItemComprasByDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where desconto is less than or equal to DEFAULT_DESCONTO
        defaultItemCompraShouldBeFound("desconto.lessThanOrEqual=" + DEFAULT_DESCONTO);

        // Get all the itemCompraList where desconto is less than or equal to SMALLER_DESCONTO
        defaultItemCompraShouldNotBeFound("desconto.lessThanOrEqual=" + SMALLER_DESCONTO);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where desconto is less than DEFAULT_DESCONTO
        defaultItemCompraShouldNotBeFound("desconto.lessThan=" + DEFAULT_DESCONTO);

        // Get all the itemCompraList where desconto is less than (DEFAULT_DESCONTO + 1)
        defaultItemCompraShouldBeFound("desconto.lessThan=" + (DEFAULT_DESCONTO + 1));
    }

    @Test
    @Transactional
    public void getAllItemComprasByDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where desconto is greater than DEFAULT_DESCONTO
        defaultItemCompraShouldNotBeFound("desconto.greaterThan=" + DEFAULT_DESCONTO);

        // Get all the itemCompraList where desconto is greater than SMALLER_DESCONTO
        defaultItemCompraShouldBeFound("desconto.greaterThan=" + SMALLER_DESCONTO);
    }


    @Test
    @Transactional
    public void getAllItemComprasByDataSolicitacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataSolicitacao equals to DEFAULT_DATA_SOLICITACAO
        defaultItemCompraShouldBeFound("dataSolicitacao.equals=" + DEFAULT_DATA_SOLICITACAO);

        // Get all the itemCompraList where dataSolicitacao equals to UPDATED_DATA_SOLICITACAO
        defaultItemCompraShouldNotBeFound("dataSolicitacao.equals=" + UPDATED_DATA_SOLICITACAO);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataSolicitacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataSolicitacao not equals to DEFAULT_DATA_SOLICITACAO
        defaultItemCompraShouldNotBeFound("dataSolicitacao.notEquals=" + DEFAULT_DATA_SOLICITACAO);

        // Get all the itemCompraList where dataSolicitacao not equals to UPDATED_DATA_SOLICITACAO
        defaultItemCompraShouldBeFound("dataSolicitacao.notEquals=" + UPDATED_DATA_SOLICITACAO);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataSolicitacaoIsInShouldWork() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataSolicitacao in DEFAULT_DATA_SOLICITACAO or UPDATED_DATA_SOLICITACAO
        defaultItemCompraShouldBeFound("dataSolicitacao.in=" + DEFAULT_DATA_SOLICITACAO + "," + UPDATED_DATA_SOLICITACAO);

        // Get all the itemCompraList where dataSolicitacao equals to UPDATED_DATA_SOLICITACAO
        defaultItemCompraShouldNotBeFound("dataSolicitacao.in=" + UPDATED_DATA_SOLICITACAO);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataSolicitacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataSolicitacao is not null
        defaultItemCompraShouldBeFound("dataSolicitacao.specified=true");

        // Get all the itemCompraList where dataSolicitacao is null
        defaultItemCompraShouldNotBeFound("dataSolicitacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataSolicitacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataSolicitacao is greater than or equal to DEFAULT_DATA_SOLICITACAO
        defaultItemCompraShouldBeFound("dataSolicitacao.greaterThanOrEqual=" + DEFAULT_DATA_SOLICITACAO);

        // Get all the itemCompraList where dataSolicitacao is greater than or equal to UPDATED_DATA_SOLICITACAO
        defaultItemCompraShouldNotBeFound("dataSolicitacao.greaterThanOrEqual=" + UPDATED_DATA_SOLICITACAO);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataSolicitacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataSolicitacao is less than or equal to DEFAULT_DATA_SOLICITACAO
        defaultItemCompraShouldBeFound("dataSolicitacao.lessThanOrEqual=" + DEFAULT_DATA_SOLICITACAO);

        // Get all the itemCompraList where dataSolicitacao is less than or equal to SMALLER_DATA_SOLICITACAO
        defaultItemCompraShouldNotBeFound("dataSolicitacao.lessThanOrEqual=" + SMALLER_DATA_SOLICITACAO);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataSolicitacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataSolicitacao is less than DEFAULT_DATA_SOLICITACAO
        defaultItemCompraShouldNotBeFound("dataSolicitacao.lessThan=" + DEFAULT_DATA_SOLICITACAO);

        // Get all the itemCompraList where dataSolicitacao is less than UPDATED_DATA_SOLICITACAO
        defaultItemCompraShouldBeFound("dataSolicitacao.lessThan=" + UPDATED_DATA_SOLICITACAO);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataSolicitacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataSolicitacao is greater than DEFAULT_DATA_SOLICITACAO
        defaultItemCompraShouldNotBeFound("dataSolicitacao.greaterThan=" + DEFAULT_DATA_SOLICITACAO);

        // Get all the itemCompraList where dataSolicitacao is greater than SMALLER_DATA_SOLICITACAO
        defaultItemCompraShouldBeFound("dataSolicitacao.greaterThan=" + SMALLER_DATA_SOLICITACAO);
    }


    @Test
    @Transactional
    public void getAllItemComprasByDataEntregaIsEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataEntrega equals to DEFAULT_DATA_ENTREGA
        defaultItemCompraShouldBeFound("dataEntrega.equals=" + DEFAULT_DATA_ENTREGA);

        // Get all the itemCompraList where dataEntrega equals to UPDATED_DATA_ENTREGA
        defaultItemCompraShouldNotBeFound("dataEntrega.equals=" + UPDATED_DATA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataEntregaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataEntrega not equals to DEFAULT_DATA_ENTREGA
        defaultItemCompraShouldNotBeFound("dataEntrega.notEquals=" + DEFAULT_DATA_ENTREGA);

        // Get all the itemCompraList where dataEntrega not equals to UPDATED_DATA_ENTREGA
        defaultItemCompraShouldBeFound("dataEntrega.notEquals=" + UPDATED_DATA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataEntregaIsInShouldWork() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataEntrega in DEFAULT_DATA_ENTREGA or UPDATED_DATA_ENTREGA
        defaultItemCompraShouldBeFound("dataEntrega.in=" + DEFAULT_DATA_ENTREGA + "," + UPDATED_DATA_ENTREGA);

        // Get all the itemCompraList where dataEntrega equals to UPDATED_DATA_ENTREGA
        defaultItemCompraShouldNotBeFound("dataEntrega.in=" + UPDATED_DATA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataEntregaIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataEntrega is not null
        defaultItemCompraShouldBeFound("dataEntrega.specified=true");

        // Get all the itemCompraList where dataEntrega is null
        defaultItemCompraShouldNotBeFound("dataEntrega.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataEntregaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataEntrega is greater than or equal to DEFAULT_DATA_ENTREGA
        defaultItemCompraShouldBeFound("dataEntrega.greaterThanOrEqual=" + DEFAULT_DATA_ENTREGA);

        // Get all the itemCompraList where dataEntrega is greater than or equal to UPDATED_DATA_ENTREGA
        defaultItemCompraShouldNotBeFound("dataEntrega.greaterThanOrEqual=" + UPDATED_DATA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataEntregaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataEntrega is less than or equal to DEFAULT_DATA_ENTREGA
        defaultItemCompraShouldBeFound("dataEntrega.lessThanOrEqual=" + DEFAULT_DATA_ENTREGA);

        // Get all the itemCompraList where dataEntrega is less than or equal to SMALLER_DATA_ENTREGA
        defaultItemCompraShouldNotBeFound("dataEntrega.lessThanOrEqual=" + SMALLER_DATA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataEntregaIsLessThanSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataEntrega is less than DEFAULT_DATA_ENTREGA
        defaultItemCompraShouldNotBeFound("dataEntrega.lessThan=" + DEFAULT_DATA_ENTREGA);

        // Get all the itemCompraList where dataEntrega is less than UPDATED_DATA_ENTREGA
        defaultItemCompraShouldBeFound("dataEntrega.lessThan=" + UPDATED_DATA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllItemComprasByDataEntregaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where dataEntrega is greater than DEFAULT_DATA_ENTREGA
        defaultItemCompraShouldNotBeFound("dataEntrega.greaterThan=" + DEFAULT_DATA_ENTREGA);

        // Get all the itemCompraList where dataEntrega is greater than SMALLER_DATA_ENTREGA
        defaultItemCompraShouldBeFound("dataEntrega.greaterThan=" + SMALLER_DATA_ENTREGA);
    }


    @Test
    @Transactional
    public void getAllItemComprasByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where valor equals to DEFAULT_VALOR
        defaultItemCompraShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the itemCompraList where valor equals to UPDATED_VALOR
        defaultItemCompraShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllItemComprasByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where valor not equals to DEFAULT_VALOR
        defaultItemCompraShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the itemCompraList where valor not equals to UPDATED_VALOR
        defaultItemCompraShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllItemComprasByValorIsInShouldWork() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultItemCompraShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the itemCompraList where valor equals to UPDATED_VALOR
        defaultItemCompraShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllItemComprasByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where valor is not null
        defaultItemCompraShouldBeFound("valor.specified=true");

        // Get all the itemCompraList where valor is null
        defaultItemCompraShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemComprasByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where valor is greater than or equal to DEFAULT_VALOR
        defaultItemCompraShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the itemCompraList where valor is greater than or equal to UPDATED_VALOR
        defaultItemCompraShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllItemComprasByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where valor is less than or equal to DEFAULT_VALOR
        defaultItemCompraShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the itemCompraList where valor is less than or equal to SMALLER_VALOR
        defaultItemCompraShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllItemComprasByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where valor is less than DEFAULT_VALOR
        defaultItemCompraShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the itemCompraList where valor is less than UPDATED_VALOR
        defaultItemCompraShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllItemComprasByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        // Get all the itemCompraList where valor is greater than DEFAULT_VALOR
        defaultItemCompraShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the itemCompraList where valor is greater than SMALLER_VALOR
        defaultItemCompraShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllItemComprasBySolicitanteIsEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);
        User solicitante = UserResourceIT.createEntity(em);
        em.persist(solicitante);
        em.flush();
        itemCompra.setSolicitante(solicitante);
        itemCompraRepository.saveAndFlush(itemCompra);
        Long solicitanteId = solicitante.getId();

        // Get all the itemCompraList where solicitante equals to solicitanteId
        defaultItemCompraShouldBeFound("solicitanteId.equals=" + solicitanteId);

        // Get all the itemCompraList where solicitante equals to solicitanteId + 1
        defaultItemCompraShouldNotBeFound("solicitanteId.equals=" + (solicitanteId + 1));
    }


    @Test
    @Transactional
    public void getAllItemComprasByCompraIsEqualToSomething() throws Exception {
        // Get already existing entity
        Compra compra = itemCompra.getCompra();
        itemCompraRepository.saveAndFlush(itemCompra);
        Long compraId = compra.getId();

        // Get all the itemCompraList where compra equals to compraId
        defaultItemCompraShouldBeFound("compraId.equals=" + compraId);

        // Get all the itemCompraList where compra equals to compraId + 1
        defaultItemCompraShouldNotBeFound("compraId.equals=" + (compraId + 1));
    }


    @Test
    @Transactional
    public void getAllItemComprasByProdutoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Produto produto = itemCompra.getProduto();
        itemCompraRepository.saveAndFlush(itemCompra);
        Long produtoId = produto.getId();

        // Get all the itemCompraList where produto equals to produtoId
        defaultItemCompraShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the itemCompraList where produto equals to produtoId + 1
        defaultItemCompraShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }


    @Test
    @Transactional
    public void getAllItemComprasByFornecedorIsEqualToSomething() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);
        Fornecedor fornecedor = FornecedorResourceIT.createEntity(em);
        em.persist(fornecedor);
        em.flush();
        itemCompra.setFornecedor(fornecedor);
        itemCompraRepository.saveAndFlush(itemCompra);
        Long fornecedorId = fornecedor.getId();

        // Get all the itemCompraList where fornecedor equals to fornecedorId
        defaultItemCompraShouldBeFound("fornecedorId.equals=" + fornecedorId);

        // Get all the itemCompraList where fornecedor equals to fornecedorId + 1
        defaultItemCompraShouldNotBeFound("fornecedorId.equals=" + (fornecedorId + 1));
    }


    @Test
    @Transactional
    public void getAllItemComprasByStatusIsEqualToSomething() throws Exception {
        // Get already existing entity
        FluxoDocumento status = itemCompra.getStatus();
        itemCompraRepository.saveAndFlush(itemCompra);
        Long statusId = status.getId();

        // Get all the itemCompraList where status equals to statusId
        defaultItemCompraShouldBeFound("statusId.equals=" + statusId);

        // Get all the itemCompraList where status equals to statusId + 1
        defaultItemCompraShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemCompraShouldBeFound(String filter) throws Exception {
        restItemCompraMockMvc.perform(get("/api/item-compras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].desconto").value(hasItem(DEFAULT_DESCONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].dataSolicitacao").value(hasItem(sameInstant(DEFAULT_DATA_SOLICITACAO))))
            .andExpect(jsonPath("$.[*].dataEntrega").value(hasItem(sameInstant(DEFAULT_DATA_ENTREGA))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));

        // Check, that the count call also returns 1
        restItemCompraMockMvc.perform(get("/api/item-compras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemCompraShouldNotBeFound(String filter) throws Exception {
        restItemCompraMockMvc.perform(get("/api/item-compras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemCompraMockMvc.perform(get("/api/item-compras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingItemCompra() throws Exception {
        // Get the itemCompra
        restItemCompraMockMvc.perform(get("/api/item-compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemCompra() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        int databaseSizeBeforeUpdate = itemCompraRepository.findAll().size();

        // Update the itemCompra
        ItemCompra updatedItemCompra = itemCompraRepository.findById(itemCompra.getId()).get();
        // Disconnect from session so that the updates on updatedItemCompra are not directly saved in db
        em.detach(updatedItemCompra);
        updatedItemCompra
            .quantidade(UPDATED_QUANTIDADE)
            .desconto(UPDATED_DESCONTO)
            .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .descricao(UPDATED_DESCRICAO)
            .valor(UPDATED_VALOR);
        ItemCompraDTO itemCompraDTO = itemCompraMapper.toDto(updatedItemCompra);

        restItemCompraMockMvc.perform(put("/api/item-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCompraDTO)))
            .andExpect(status().isOk());

        // Validate the ItemCompra in the database
        List<ItemCompra> itemCompraList = itemCompraRepository.findAll();
        assertThat(itemCompraList).hasSize(databaseSizeBeforeUpdate);
        ItemCompra testItemCompra = itemCompraList.get(itemCompraList.size() - 1);
        assertThat(testItemCompra.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testItemCompra.getDesconto()).isEqualTo(UPDATED_DESCONTO);
        assertThat(testItemCompra.getDataSolicitacao()).isEqualTo(UPDATED_DATA_SOLICITACAO);
        assertThat(testItemCompra.getDataEntrega()).isEqualTo(UPDATED_DATA_ENTREGA);
        assertThat(testItemCompra.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testItemCompra.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingItemCompra() throws Exception {
        int databaseSizeBeforeUpdate = itemCompraRepository.findAll().size();

        // Create the ItemCompra
        ItemCompraDTO itemCompraDTO = itemCompraMapper.toDto(itemCompra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemCompraMockMvc.perform(put("/api/item-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCompraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemCompra in the database
        List<ItemCompra> itemCompraList = itemCompraRepository.findAll();
        assertThat(itemCompraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemCompra() throws Exception {
        // Initialize the database
        itemCompraRepository.saveAndFlush(itemCompra);

        int databaseSizeBeforeDelete = itemCompraRepository.findAll().size();

        // Delete the itemCompra
        restItemCompraMockMvc.perform(delete("/api/item-compras/{id}", itemCompra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemCompra> itemCompraList = itemCompraRepository.findAll();
        assertThat(itemCompraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
