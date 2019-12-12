package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.CompostoProduto;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.domain.UnidadeMedida;
import com.ravunana.manda.repository.CompostoProdutoRepository;
import com.ravunana.manda.service.CompostoProdutoService;
import com.ravunana.manda.service.dto.CompostoProdutoDTO;
import com.ravunana.manda.service.mapper.CompostoProdutoMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.CompostoProdutoCriteria;
import com.ravunana.manda.service.CompostoProdutoQueryService;

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
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompostoProdutoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class CompostoProdutoResourceIT {

    private static final Double DEFAULT_QUANTIDADE = 1D;
    private static final Double UPDATED_QUANTIDADE = 2D;
    private static final Double SMALLER_QUANTIDADE = 1D - 1D;

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_PERMANENTE = false;
    private static final Boolean UPDATED_PERMANENTE = true;

    @Autowired
    private CompostoProdutoRepository compostoProdutoRepository;

    @Autowired
    private CompostoProdutoMapper compostoProdutoMapper;

    @Autowired
    private CompostoProdutoService compostoProdutoService;

    @Autowired
    private CompostoProdutoQueryService compostoProdutoQueryService;

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

    private MockMvc restCompostoProdutoMockMvc;

    private CompostoProduto compostoProduto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompostoProdutoResource compostoProdutoResource = new CompostoProdutoResource(compostoProdutoService, compostoProdutoQueryService);
        this.restCompostoProdutoMockMvc = MockMvcBuilders.standaloneSetup(compostoProdutoResource)
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
    public static CompostoProduto createEntity(EntityManager em) {
        CompostoProduto compostoProduto = new CompostoProduto()
            .quantidade(DEFAULT_QUANTIDADE)
            .valor(DEFAULT_VALOR)
            .permanente(DEFAULT_PERMANENTE);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        compostoProduto.setProduto(produto);
        // Add required entity
        UnidadeMedida unidadeMedida;
        if (TestUtil.findAll(em, UnidadeMedida.class).isEmpty()) {
            unidadeMedida = UnidadeMedidaResourceIT.createEntity(em);
            em.persist(unidadeMedida);
            em.flush();
        } else {
            unidadeMedida = TestUtil.findAll(em, UnidadeMedida.class).get(0);
        }
        compostoProduto.setUnidade(unidadeMedida);
        // Add required entity
        compostoProduto.setComposto(produto);
        return compostoProduto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompostoProduto createUpdatedEntity(EntityManager em) {
        CompostoProduto compostoProduto = new CompostoProduto()
            .quantidade(UPDATED_QUANTIDADE)
            .valor(UPDATED_VALOR)
            .permanente(UPDATED_PERMANENTE);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createUpdatedEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        compostoProduto.setProduto(produto);
        // Add required entity
        UnidadeMedida unidadeMedida;
        if (TestUtil.findAll(em, UnidadeMedida.class).isEmpty()) {
            unidadeMedida = UnidadeMedidaResourceIT.createUpdatedEntity(em);
            em.persist(unidadeMedida);
            em.flush();
        } else {
            unidadeMedida = TestUtil.findAll(em, UnidadeMedida.class).get(0);
        }
        compostoProduto.setUnidade(unidadeMedida);
        // Add required entity
        compostoProduto.setComposto(produto);
        return compostoProduto;
    }

    @BeforeEach
    public void initTest() {
        compostoProduto = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompostoProduto() throws Exception {
        int databaseSizeBeforeCreate = compostoProdutoRepository.findAll().size();

        // Create the CompostoProduto
        CompostoProdutoDTO compostoProdutoDTO = compostoProdutoMapper.toDto(compostoProduto);
        restCompostoProdutoMockMvc.perform(post("/api/composto-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compostoProdutoDTO)))
            .andExpect(status().isCreated());

        // Validate the CompostoProduto in the database
        List<CompostoProduto> compostoProdutoList = compostoProdutoRepository.findAll();
        assertThat(compostoProdutoList).hasSize(databaseSizeBeforeCreate + 1);
        CompostoProduto testCompostoProduto = compostoProdutoList.get(compostoProdutoList.size() - 1);
        assertThat(testCompostoProduto.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testCompostoProduto.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testCompostoProduto.isPermanente()).isEqualTo(DEFAULT_PERMANENTE);
    }

    @Test
    @Transactional
    public void createCompostoProdutoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compostoProdutoRepository.findAll().size();

        // Create the CompostoProduto with an existing ID
        compostoProduto.setId(1L);
        CompostoProdutoDTO compostoProdutoDTO = compostoProdutoMapper.toDto(compostoProduto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompostoProdutoMockMvc.perform(post("/api/composto-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compostoProdutoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompostoProduto in the database
        List<CompostoProduto> compostoProdutoList = compostoProdutoRepository.findAll();
        assertThat(compostoProdutoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = compostoProdutoRepository.findAll().size();
        // set the field null
        compostoProduto.setQuantidade(null);

        // Create the CompostoProduto, which fails.
        CompostoProdutoDTO compostoProdutoDTO = compostoProdutoMapper.toDto(compostoProduto);

        restCompostoProdutoMockMvc.perform(post("/api/composto-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compostoProdutoDTO)))
            .andExpect(status().isBadRequest());

        List<CompostoProduto> compostoProdutoList = compostoProdutoRepository.findAll();
        assertThat(compostoProdutoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutos() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList
        restCompostoProdutoMockMvc.perform(get("/api/composto-produtos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compostoProduto.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].permanente").value(hasItem(DEFAULT_PERMANENTE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCompostoProduto() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get the compostoProduto
        restCompostoProdutoMockMvc.perform(get("/api/composto-produtos/{id}", compostoProduto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compostoProduto.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.doubleValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.permanente").value(DEFAULT_PERMANENTE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCompostoProdutosByIdFiltering() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        Long id = compostoProduto.getId();

        defaultCompostoProdutoShouldBeFound("id.equals=" + id);
        defaultCompostoProdutoShouldNotBeFound("id.notEquals=" + id);

        defaultCompostoProdutoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompostoProdutoShouldNotBeFound("id.greaterThan=" + id);

        defaultCompostoProdutoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompostoProdutoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompostoProdutosByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where quantidade equals to DEFAULT_QUANTIDADE
        defaultCompostoProdutoShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the compostoProdutoList where quantidade equals to UPDATED_QUANTIDADE
        defaultCompostoProdutoShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByQuantidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where quantidade not equals to DEFAULT_QUANTIDADE
        defaultCompostoProdutoShouldNotBeFound("quantidade.notEquals=" + DEFAULT_QUANTIDADE);

        // Get all the compostoProdutoList where quantidade not equals to UPDATED_QUANTIDADE
        defaultCompostoProdutoShouldBeFound("quantidade.notEquals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultCompostoProdutoShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the compostoProdutoList where quantidade equals to UPDATED_QUANTIDADE
        defaultCompostoProdutoShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where quantidade is not null
        defaultCompostoProdutoShouldBeFound("quantidade.specified=true");

        // Get all the compostoProdutoList where quantidade is null
        defaultCompostoProdutoShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultCompostoProdutoShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the compostoProdutoList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultCompostoProdutoShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultCompostoProdutoShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the compostoProdutoList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultCompostoProdutoShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where quantidade is less than DEFAULT_QUANTIDADE
        defaultCompostoProdutoShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the compostoProdutoList where quantidade is less than UPDATED_QUANTIDADE
        defaultCompostoProdutoShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultCompostoProdutoShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the compostoProdutoList where quantidade is greater than SMALLER_QUANTIDADE
        defaultCompostoProdutoShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }


    @Test
    @Transactional
    public void getAllCompostoProdutosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where valor equals to DEFAULT_VALOR
        defaultCompostoProdutoShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the compostoProdutoList where valor equals to UPDATED_VALOR
        defaultCompostoProdutoShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where valor not equals to DEFAULT_VALOR
        defaultCompostoProdutoShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the compostoProdutoList where valor not equals to UPDATED_VALOR
        defaultCompostoProdutoShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultCompostoProdutoShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the compostoProdutoList where valor equals to UPDATED_VALOR
        defaultCompostoProdutoShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where valor is not null
        defaultCompostoProdutoShouldBeFound("valor.specified=true");

        // Get all the compostoProdutoList where valor is null
        defaultCompostoProdutoShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where valor is greater than or equal to DEFAULT_VALOR
        defaultCompostoProdutoShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the compostoProdutoList where valor is greater than or equal to UPDATED_VALOR
        defaultCompostoProdutoShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where valor is less than or equal to DEFAULT_VALOR
        defaultCompostoProdutoShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the compostoProdutoList where valor is less than or equal to SMALLER_VALOR
        defaultCompostoProdutoShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where valor is less than DEFAULT_VALOR
        defaultCompostoProdutoShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the compostoProdutoList where valor is less than UPDATED_VALOR
        defaultCompostoProdutoShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where valor is greater than DEFAULT_VALOR
        defaultCompostoProdutoShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the compostoProdutoList where valor is greater than SMALLER_VALOR
        defaultCompostoProdutoShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllCompostoProdutosByPermanenteIsEqualToSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where permanente equals to DEFAULT_PERMANENTE
        defaultCompostoProdutoShouldBeFound("permanente.equals=" + DEFAULT_PERMANENTE);

        // Get all the compostoProdutoList where permanente equals to UPDATED_PERMANENTE
        defaultCompostoProdutoShouldNotBeFound("permanente.equals=" + UPDATED_PERMANENTE);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByPermanenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where permanente not equals to DEFAULT_PERMANENTE
        defaultCompostoProdutoShouldNotBeFound("permanente.notEquals=" + DEFAULT_PERMANENTE);

        // Get all the compostoProdutoList where permanente not equals to UPDATED_PERMANENTE
        defaultCompostoProdutoShouldBeFound("permanente.notEquals=" + UPDATED_PERMANENTE);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByPermanenteIsInShouldWork() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where permanente in DEFAULT_PERMANENTE or UPDATED_PERMANENTE
        defaultCompostoProdutoShouldBeFound("permanente.in=" + DEFAULT_PERMANENTE + "," + UPDATED_PERMANENTE);

        // Get all the compostoProdutoList where permanente equals to UPDATED_PERMANENTE
        defaultCompostoProdutoShouldNotBeFound("permanente.in=" + UPDATED_PERMANENTE);
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByPermanenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        // Get all the compostoProdutoList where permanente is not null
        defaultCompostoProdutoShouldBeFound("permanente.specified=true");

        // Get all the compostoProdutoList where permanente is null
        defaultCompostoProdutoShouldNotBeFound("permanente.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompostoProdutosByProdutoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Produto produto = compostoProduto.getProduto();
        compostoProdutoRepository.saveAndFlush(compostoProduto);
        Long produtoId = produto.getId();

        // Get all the compostoProdutoList where produto equals to produtoId
        defaultCompostoProdutoShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the compostoProdutoList where produto equals to produtoId + 1
        defaultCompostoProdutoShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }


    @Test
    @Transactional
    public void getAllCompostoProdutosByUnidadeIsEqualToSomething() throws Exception {
        // Get already existing entity
        UnidadeMedida unidade = compostoProduto.getUnidade();
        compostoProdutoRepository.saveAndFlush(compostoProduto);
        Long unidadeId = unidade.getId();

        // Get all the compostoProdutoList where unidade equals to unidadeId
        defaultCompostoProdutoShouldBeFound("unidadeId.equals=" + unidadeId);

        // Get all the compostoProdutoList where unidade equals to unidadeId + 1
        defaultCompostoProdutoShouldNotBeFound("unidadeId.equals=" + (unidadeId + 1));
    }


    @Test
    @Transactional
    public void getAllCompostoProdutosByCompostoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Produto composto = compostoProduto.getComposto();
        compostoProdutoRepository.saveAndFlush(compostoProduto);
        Long compostoId = composto.getId();

        // Get all the compostoProdutoList where composto equals to compostoId
        defaultCompostoProdutoShouldBeFound("compostoId.equals=" + compostoId);

        // Get all the compostoProdutoList where composto equals to compostoId + 1
        defaultCompostoProdutoShouldNotBeFound("compostoId.equals=" + (compostoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompostoProdutoShouldBeFound(String filter) throws Exception {
        restCompostoProdutoMockMvc.perform(get("/api/composto-produtos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compostoProduto.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].permanente").value(hasItem(DEFAULT_PERMANENTE.booleanValue())));

        // Check, that the count call also returns 1
        restCompostoProdutoMockMvc.perform(get("/api/composto-produtos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompostoProdutoShouldNotBeFound(String filter) throws Exception {
        restCompostoProdutoMockMvc.perform(get("/api/composto-produtos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompostoProdutoMockMvc.perform(get("/api/composto-produtos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCompostoProduto() throws Exception {
        // Get the compostoProduto
        restCompostoProdutoMockMvc.perform(get("/api/composto-produtos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompostoProduto() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        int databaseSizeBeforeUpdate = compostoProdutoRepository.findAll().size();

        // Update the compostoProduto
        CompostoProduto updatedCompostoProduto = compostoProdutoRepository.findById(compostoProduto.getId()).get();
        // Disconnect from session so that the updates on updatedCompostoProduto are not directly saved in db
        em.detach(updatedCompostoProduto);
        updatedCompostoProduto
            .quantidade(UPDATED_QUANTIDADE)
            .valor(UPDATED_VALOR)
            .permanente(UPDATED_PERMANENTE);
        CompostoProdutoDTO compostoProdutoDTO = compostoProdutoMapper.toDto(updatedCompostoProduto);

        restCompostoProdutoMockMvc.perform(put("/api/composto-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compostoProdutoDTO)))
            .andExpect(status().isOk());

        // Validate the CompostoProduto in the database
        List<CompostoProduto> compostoProdutoList = compostoProdutoRepository.findAll();
        assertThat(compostoProdutoList).hasSize(databaseSizeBeforeUpdate);
        CompostoProduto testCompostoProduto = compostoProdutoList.get(compostoProdutoList.size() - 1);
        assertThat(testCompostoProduto.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testCompostoProduto.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testCompostoProduto.isPermanente()).isEqualTo(UPDATED_PERMANENTE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompostoProduto() throws Exception {
        int databaseSizeBeforeUpdate = compostoProdutoRepository.findAll().size();

        // Create the CompostoProduto
        CompostoProdutoDTO compostoProdutoDTO = compostoProdutoMapper.toDto(compostoProduto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompostoProdutoMockMvc.perform(put("/api/composto-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compostoProdutoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompostoProduto in the database
        List<CompostoProduto> compostoProdutoList = compostoProdutoRepository.findAll();
        assertThat(compostoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompostoProduto() throws Exception {
        // Initialize the database
        compostoProdutoRepository.saveAndFlush(compostoProduto);

        int databaseSizeBeforeDelete = compostoProdutoRepository.findAll().size();

        // Delete the compostoProduto
        restCompostoProdutoMockMvc.perform(delete("/api/composto-produtos/{id}", compostoProduto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompostoProduto> compostoProdutoList = compostoProdutoRepository.findAll();
        assertThat(compostoProdutoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
