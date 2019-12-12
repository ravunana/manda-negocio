package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.MeioLiquidacao;
import com.ravunana.manda.domain.DetalheLancamento;
import com.ravunana.manda.repository.MeioLiquidacaoRepository;
import com.ravunana.manda.service.MeioLiquidacaoService;
import com.ravunana.manda.service.dto.MeioLiquidacaoDTO;
import com.ravunana.manda.service.mapper.MeioLiquidacaoMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.MeioLiquidacaoCriteria;
import com.ravunana.manda.service.MeioLiquidacaoQueryService;

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
 * Integration tests for the {@link MeioLiquidacaoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class MeioLiquidacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MOSTRAR_PONTO_VENDA = false;
    private static final Boolean UPDATED_MOSTRAR_PONTO_VENDA = true;

    @Autowired
    private MeioLiquidacaoRepository meioLiquidacaoRepository;

    @Autowired
    private MeioLiquidacaoMapper meioLiquidacaoMapper;

    @Autowired
    private MeioLiquidacaoService meioLiquidacaoService;

    @Autowired
    private MeioLiquidacaoQueryService meioLiquidacaoQueryService;

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

    private MockMvc restMeioLiquidacaoMockMvc;

    private MeioLiquidacao meioLiquidacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeioLiquidacaoResource meioLiquidacaoResource = new MeioLiquidacaoResource(meioLiquidacaoService, meioLiquidacaoQueryService);
        this.restMeioLiquidacaoMockMvc = MockMvcBuilders.standaloneSetup(meioLiquidacaoResource)
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
    public static MeioLiquidacao createEntity(EntityManager em) {
        MeioLiquidacao meioLiquidacao = new MeioLiquidacao()
            .nome(DEFAULT_NOME)
            .sigla(DEFAULT_SIGLA)
            .icon(DEFAULT_ICON)
            .mostrarPontoVenda(DEFAULT_MOSTRAR_PONTO_VENDA);
        return meioLiquidacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeioLiquidacao createUpdatedEntity(EntityManager em) {
        MeioLiquidacao meioLiquidacao = new MeioLiquidacao()
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .icon(UPDATED_ICON)
            .mostrarPontoVenda(UPDATED_MOSTRAR_PONTO_VENDA);
        return meioLiquidacao;
    }

    @BeforeEach
    public void initTest() {
        meioLiquidacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeioLiquidacao() throws Exception {
        int databaseSizeBeforeCreate = meioLiquidacaoRepository.findAll().size();

        // Create the MeioLiquidacao
        MeioLiquidacaoDTO meioLiquidacaoDTO = meioLiquidacaoMapper.toDto(meioLiquidacao);
        restMeioLiquidacaoMockMvc.perform(post("/api/meio-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meioLiquidacaoDTO)))
            .andExpect(status().isCreated());

        // Validate the MeioLiquidacao in the database
        List<MeioLiquidacao> meioLiquidacaoList = meioLiquidacaoRepository.findAll();
        assertThat(meioLiquidacaoList).hasSize(databaseSizeBeforeCreate + 1);
        MeioLiquidacao testMeioLiquidacao = meioLiquidacaoList.get(meioLiquidacaoList.size() - 1);
        assertThat(testMeioLiquidacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMeioLiquidacao.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testMeioLiquidacao.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testMeioLiquidacao.isMostrarPontoVenda()).isEqualTo(DEFAULT_MOSTRAR_PONTO_VENDA);
    }

    @Test
    @Transactional
    public void createMeioLiquidacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meioLiquidacaoRepository.findAll().size();

        // Create the MeioLiquidacao with an existing ID
        meioLiquidacao.setId(1L);
        MeioLiquidacaoDTO meioLiquidacaoDTO = meioLiquidacaoMapper.toDto(meioLiquidacao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeioLiquidacaoMockMvc.perform(post("/api/meio-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meioLiquidacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MeioLiquidacao in the database
        List<MeioLiquidacao> meioLiquidacaoList = meioLiquidacaoRepository.findAll();
        assertThat(meioLiquidacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMeioLiquidacaos() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList
        restMeioLiquidacaoMockMvc.perform(get("/api/meio-liquidacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meioLiquidacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].mostrarPontoVenda").value(hasItem(DEFAULT_MOSTRAR_PONTO_VENDA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMeioLiquidacao() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get the meioLiquidacao
        restMeioLiquidacaoMockMvc.perform(get("/api/meio-liquidacaos/{id}", meioLiquidacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meioLiquidacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.mostrarPontoVenda").value(DEFAULT_MOSTRAR_PONTO_VENDA.booleanValue()));
    }


    @Test
    @Transactional
    public void getMeioLiquidacaosByIdFiltering() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        Long id = meioLiquidacao.getId();

        defaultMeioLiquidacaoShouldBeFound("id.equals=" + id);
        defaultMeioLiquidacaoShouldNotBeFound("id.notEquals=" + id);

        defaultMeioLiquidacaoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMeioLiquidacaoShouldNotBeFound("id.greaterThan=" + id);

        defaultMeioLiquidacaoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMeioLiquidacaoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMeioLiquidacaosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where nome equals to DEFAULT_NOME
        defaultMeioLiquidacaoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the meioLiquidacaoList where nome equals to UPDATED_NOME
        defaultMeioLiquidacaoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where nome not equals to DEFAULT_NOME
        defaultMeioLiquidacaoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the meioLiquidacaoList where nome not equals to UPDATED_NOME
        defaultMeioLiquidacaoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultMeioLiquidacaoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the meioLiquidacaoList where nome equals to UPDATED_NOME
        defaultMeioLiquidacaoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where nome is not null
        defaultMeioLiquidacaoShouldBeFound("nome.specified=true");

        // Get all the meioLiquidacaoList where nome is null
        defaultMeioLiquidacaoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllMeioLiquidacaosByNomeContainsSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where nome contains DEFAULT_NOME
        defaultMeioLiquidacaoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the meioLiquidacaoList where nome contains UPDATED_NOME
        defaultMeioLiquidacaoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where nome does not contain DEFAULT_NOME
        defaultMeioLiquidacaoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the meioLiquidacaoList where nome does not contain UPDATED_NOME
        defaultMeioLiquidacaoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllMeioLiquidacaosBySiglaIsEqualToSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where sigla equals to DEFAULT_SIGLA
        defaultMeioLiquidacaoShouldBeFound("sigla.equals=" + DEFAULT_SIGLA);

        // Get all the meioLiquidacaoList where sigla equals to UPDATED_SIGLA
        defaultMeioLiquidacaoShouldNotBeFound("sigla.equals=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosBySiglaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where sigla not equals to DEFAULT_SIGLA
        defaultMeioLiquidacaoShouldNotBeFound("sigla.notEquals=" + DEFAULT_SIGLA);

        // Get all the meioLiquidacaoList where sigla not equals to UPDATED_SIGLA
        defaultMeioLiquidacaoShouldBeFound("sigla.notEquals=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosBySiglaIsInShouldWork() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where sigla in DEFAULT_SIGLA or UPDATED_SIGLA
        defaultMeioLiquidacaoShouldBeFound("sigla.in=" + DEFAULT_SIGLA + "," + UPDATED_SIGLA);

        // Get all the meioLiquidacaoList where sigla equals to UPDATED_SIGLA
        defaultMeioLiquidacaoShouldNotBeFound("sigla.in=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosBySiglaIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where sigla is not null
        defaultMeioLiquidacaoShouldBeFound("sigla.specified=true");

        // Get all the meioLiquidacaoList where sigla is null
        defaultMeioLiquidacaoShouldNotBeFound("sigla.specified=false");
    }
                @Test
    @Transactional
    public void getAllMeioLiquidacaosBySiglaContainsSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where sigla contains DEFAULT_SIGLA
        defaultMeioLiquidacaoShouldBeFound("sigla.contains=" + DEFAULT_SIGLA);

        // Get all the meioLiquidacaoList where sigla contains UPDATED_SIGLA
        defaultMeioLiquidacaoShouldNotBeFound("sigla.contains=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosBySiglaNotContainsSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where sigla does not contain DEFAULT_SIGLA
        defaultMeioLiquidacaoShouldNotBeFound("sigla.doesNotContain=" + DEFAULT_SIGLA);

        // Get all the meioLiquidacaoList where sigla does not contain UPDATED_SIGLA
        defaultMeioLiquidacaoShouldBeFound("sigla.doesNotContain=" + UPDATED_SIGLA);
    }


    @Test
    @Transactional
    public void getAllMeioLiquidacaosByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where icon equals to DEFAULT_ICON
        defaultMeioLiquidacaoShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the meioLiquidacaoList where icon equals to UPDATED_ICON
        defaultMeioLiquidacaoShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where icon not equals to DEFAULT_ICON
        defaultMeioLiquidacaoShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the meioLiquidacaoList where icon not equals to UPDATED_ICON
        defaultMeioLiquidacaoShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosByIconIsInShouldWork() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultMeioLiquidacaoShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the meioLiquidacaoList where icon equals to UPDATED_ICON
        defaultMeioLiquidacaoShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where icon is not null
        defaultMeioLiquidacaoShouldBeFound("icon.specified=true");

        // Get all the meioLiquidacaoList where icon is null
        defaultMeioLiquidacaoShouldNotBeFound("icon.specified=false");
    }
                @Test
    @Transactional
    public void getAllMeioLiquidacaosByIconContainsSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where icon contains DEFAULT_ICON
        defaultMeioLiquidacaoShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the meioLiquidacaoList where icon contains UPDATED_ICON
        defaultMeioLiquidacaoShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosByIconNotContainsSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where icon does not contain DEFAULT_ICON
        defaultMeioLiquidacaoShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the meioLiquidacaoList where icon does not contain UPDATED_ICON
        defaultMeioLiquidacaoShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }


    @Test
    @Transactional
    public void getAllMeioLiquidacaosByMostrarPontoVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where mostrarPontoVenda equals to DEFAULT_MOSTRAR_PONTO_VENDA
        defaultMeioLiquidacaoShouldBeFound("mostrarPontoVenda.equals=" + DEFAULT_MOSTRAR_PONTO_VENDA);

        // Get all the meioLiquidacaoList where mostrarPontoVenda equals to UPDATED_MOSTRAR_PONTO_VENDA
        defaultMeioLiquidacaoShouldNotBeFound("mostrarPontoVenda.equals=" + UPDATED_MOSTRAR_PONTO_VENDA);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosByMostrarPontoVendaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where mostrarPontoVenda not equals to DEFAULT_MOSTRAR_PONTO_VENDA
        defaultMeioLiquidacaoShouldNotBeFound("mostrarPontoVenda.notEquals=" + DEFAULT_MOSTRAR_PONTO_VENDA);

        // Get all the meioLiquidacaoList where mostrarPontoVenda not equals to UPDATED_MOSTRAR_PONTO_VENDA
        defaultMeioLiquidacaoShouldBeFound("mostrarPontoVenda.notEquals=" + UPDATED_MOSTRAR_PONTO_VENDA);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosByMostrarPontoVendaIsInShouldWork() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where mostrarPontoVenda in DEFAULT_MOSTRAR_PONTO_VENDA or UPDATED_MOSTRAR_PONTO_VENDA
        defaultMeioLiquidacaoShouldBeFound("mostrarPontoVenda.in=" + DEFAULT_MOSTRAR_PONTO_VENDA + "," + UPDATED_MOSTRAR_PONTO_VENDA);

        // Get all the meioLiquidacaoList where mostrarPontoVenda equals to UPDATED_MOSTRAR_PONTO_VENDA
        defaultMeioLiquidacaoShouldNotBeFound("mostrarPontoVenda.in=" + UPDATED_MOSTRAR_PONTO_VENDA);
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosByMostrarPontoVendaIsNullOrNotNull() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        // Get all the meioLiquidacaoList where mostrarPontoVenda is not null
        defaultMeioLiquidacaoShouldBeFound("mostrarPontoVenda.specified=true");

        // Get all the meioLiquidacaoList where mostrarPontoVenda is null
        defaultMeioLiquidacaoShouldNotBeFound("mostrarPontoVenda.specified=false");
    }

    @Test
    @Transactional
    public void getAllMeioLiquidacaosByDetalheLancamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);
        DetalheLancamento detalheLancamento = DetalheLancamentoResourceIT.createEntity(em);
        em.persist(detalheLancamento);
        em.flush();
        meioLiquidacao.addDetalheLancamento(detalheLancamento);
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);
        Long detalheLancamentoId = detalheLancamento.getId();

        // Get all the meioLiquidacaoList where detalheLancamento equals to detalheLancamentoId
        defaultMeioLiquidacaoShouldBeFound("detalheLancamentoId.equals=" + detalheLancamentoId);

        // Get all the meioLiquidacaoList where detalheLancamento equals to detalheLancamentoId + 1
        defaultMeioLiquidacaoShouldNotBeFound("detalheLancamentoId.equals=" + (detalheLancamentoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMeioLiquidacaoShouldBeFound(String filter) throws Exception {
        restMeioLiquidacaoMockMvc.perform(get("/api/meio-liquidacaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meioLiquidacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].mostrarPontoVenda").value(hasItem(DEFAULT_MOSTRAR_PONTO_VENDA.booleanValue())));

        // Check, that the count call also returns 1
        restMeioLiquidacaoMockMvc.perform(get("/api/meio-liquidacaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMeioLiquidacaoShouldNotBeFound(String filter) throws Exception {
        restMeioLiquidacaoMockMvc.perform(get("/api/meio-liquidacaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMeioLiquidacaoMockMvc.perform(get("/api/meio-liquidacaos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMeioLiquidacao() throws Exception {
        // Get the meioLiquidacao
        restMeioLiquidacaoMockMvc.perform(get("/api/meio-liquidacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeioLiquidacao() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        int databaseSizeBeforeUpdate = meioLiquidacaoRepository.findAll().size();

        // Update the meioLiquidacao
        MeioLiquidacao updatedMeioLiquidacao = meioLiquidacaoRepository.findById(meioLiquidacao.getId()).get();
        // Disconnect from session so that the updates on updatedMeioLiquidacao are not directly saved in db
        em.detach(updatedMeioLiquidacao);
        updatedMeioLiquidacao
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .icon(UPDATED_ICON)
            .mostrarPontoVenda(UPDATED_MOSTRAR_PONTO_VENDA);
        MeioLiquidacaoDTO meioLiquidacaoDTO = meioLiquidacaoMapper.toDto(updatedMeioLiquidacao);

        restMeioLiquidacaoMockMvc.perform(put("/api/meio-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meioLiquidacaoDTO)))
            .andExpect(status().isOk());

        // Validate the MeioLiquidacao in the database
        List<MeioLiquidacao> meioLiquidacaoList = meioLiquidacaoRepository.findAll();
        assertThat(meioLiquidacaoList).hasSize(databaseSizeBeforeUpdate);
        MeioLiquidacao testMeioLiquidacao = meioLiquidacaoList.get(meioLiquidacaoList.size() - 1);
        assertThat(testMeioLiquidacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMeioLiquidacao.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testMeioLiquidacao.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testMeioLiquidacao.isMostrarPontoVenda()).isEqualTo(UPDATED_MOSTRAR_PONTO_VENDA);
    }

    @Test
    @Transactional
    public void updateNonExistingMeioLiquidacao() throws Exception {
        int databaseSizeBeforeUpdate = meioLiquidacaoRepository.findAll().size();

        // Create the MeioLiquidacao
        MeioLiquidacaoDTO meioLiquidacaoDTO = meioLiquidacaoMapper.toDto(meioLiquidacao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeioLiquidacaoMockMvc.perform(put("/api/meio-liquidacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meioLiquidacaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MeioLiquidacao in the database
        List<MeioLiquidacao> meioLiquidacaoList = meioLiquidacaoRepository.findAll();
        assertThat(meioLiquidacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeioLiquidacao() throws Exception {
        // Initialize the database
        meioLiquidacaoRepository.saveAndFlush(meioLiquidacao);

        int databaseSizeBeforeDelete = meioLiquidacaoRepository.findAll().size();

        // Delete the meioLiquidacao
        restMeioLiquidacaoMockMvc.perform(delete("/api/meio-liquidacaos/{id}", meioLiquidacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MeioLiquidacao> meioLiquidacaoList = meioLiquidacaoRepository.findAll();
        assertThat(meioLiquidacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
