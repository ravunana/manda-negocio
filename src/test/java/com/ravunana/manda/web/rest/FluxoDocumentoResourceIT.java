package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.FluxoDocumento;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.domain.ItemCompra;
import com.ravunana.manda.domain.ItemVenda;
import com.ravunana.manda.repository.FluxoDocumentoRepository;
import com.ravunana.manda.service.FluxoDocumentoService;
import com.ravunana.manda.service.dto.FluxoDocumentoDTO;
import com.ravunana.manda.service.mapper.FluxoDocumentoMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.FluxoDocumentoCriteria;
import com.ravunana.manda.service.FluxoDocumentoQueryService;

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
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.manda.domain.enumeration.EntidadeSistema;
/**
 * Integration tests for the {@link FluxoDocumentoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class FluxoDocumentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUMENTA_ESTOQUE = false;
    private static final Boolean UPDATED_AUMENTA_ESTOQUE = true;

    private static final Boolean DEFAULT_AUMENTA_VALOR_CAIXA = false;
    private static final Boolean UPDATED_AUMENTA_VALOR_CAIXA = true;

    private static final String DEFAULT_COR = "AAAAAAAAAA";
    private static final String UPDATED_COR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PADRAO = false;
    private static final Boolean UPDATED_PADRAO = true;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final EntidadeSistema DEFAULT_ENTIDADE = EntidadeSistema.SOFTWARE;
    private static final EntidadeSistema UPDATED_ENTIDADE = EntidadeSistema.LICENCA_SOFTWARE;

    @Autowired
    private FluxoDocumentoRepository fluxoDocumentoRepository;

    @Autowired
    private FluxoDocumentoMapper fluxoDocumentoMapper;

    @Autowired
    private FluxoDocumentoService fluxoDocumentoService;

    @Autowired
    private FluxoDocumentoQueryService fluxoDocumentoQueryService;

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

    private MockMvc restFluxoDocumentoMockMvc;

    private FluxoDocumento fluxoDocumento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FluxoDocumentoResource fluxoDocumentoResource = new FluxoDocumentoResource(fluxoDocumentoService, fluxoDocumentoQueryService);
        this.restFluxoDocumentoMockMvc = MockMvcBuilders.standaloneSetup(fluxoDocumentoResource)
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
    public static FluxoDocumento createEntity(EntityManager em) {
        FluxoDocumento fluxoDocumento = new FluxoDocumento()
            .nome(DEFAULT_NOME)
            .aumentaEstoque(DEFAULT_AUMENTA_ESTOQUE)
            .aumentaValorCaixa(DEFAULT_AUMENTA_VALOR_CAIXA)
            .cor(DEFAULT_COR)
            .padrao(DEFAULT_PADRAO)
            .descricao(DEFAULT_DESCRICAO)
            .entidade(DEFAULT_ENTIDADE);
        return fluxoDocumento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FluxoDocumento createUpdatedEntity(EntityManager em) {
        FluxoDocumento fluxoDocumento = new FluxoDocumento()
            .nome(UPDATED_NOME)
            .aumentaEstoque(UPDATED_AUMENTA_ESTOQUE)
            .aumentaValorCaixa(UPDATED_AUMENTA_VALOR_CAIXA)
            .cor(UPDATED_COR)
            .padrao(UPDATED_PADRAO)
            .descricao(UPDATED_DESCRICAO)
            .entidade(UPDATED_ENTIDADE);
        return fluxoDocumento;
    }

    @BeforeEach
    public void initTest() {
        fluxoDocumento = createEntity(em);
    }

    @Test
    @Transactional
    public void createFluxoDocumento() throws Exception {
        int databaseSizeBeforeCreate = fluxoDocumentoRepository.findAll().size();

        // Create the FluxoDocumento
        FluxoDocumentoDTO fluxoDocumentoDTO = fluxoDocumentoMapper.toDto(fluxoDocumento);
        restFluxoDocumentoMockMvc.perform(post("/api/fluxo-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoDocumentoDTO)))
            .andExpect(status().isCreated());

        // Validate the FluxoDocumento in the database
        List<FluxoDocumento> fluxoDocumentoList = fluxoDocumentoRepository.findAll();
        assertThat(fluxoDocumentoList).hasSize(databaseSizeBeforeCreate + 1);
        FluxoDocumento testFluxoDocumento = fluxoDocumentoList.get(fluxoDocumentoList.size() - 1);
        assertThat(testFluxoDocumento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFluxoDocumento.isAumentaEstoque()).isEqualTo(DEFAULT_AUMENTA_ESTOQUE);
        assertThat(testFluxoDocumento.isAumentaValorCaixa()).isEqualTo(DEFAULT_AUMENTA_VALOR_CAIXA);
        assertThat(testFluxoDocumento.getCor()).isEqualTo(DEFAULT_COR);
        assertThat(testFluxoDocumento.isPadrao()).isEqualTo(DEFAULT_PADRAO);
        assertThat(testFluxoDocumento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testFluxoDocumento.getEntidade()).isEqualTo(DEFAULT_ENTIDADE);
    }

    @Test
    @Transactional
    public void createFluxoDocumentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fluxoDocumentoRepository.findAll().size();

        // Create the FluxoDocumento with an existing ID
        fluxoDocumento.setId(1L);
        FluxoDocumentoDTO fluxoDocumentoDTO = fluxoDocumentoMapper.toDto(fluxoDocumento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFluxoDocumentoMockMvc.perform(post("/api/fluxo-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoDocumentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FluxoDocumento in the database
        List<FluxoDocumento> fluxoDocumentoList = fluxoDocumentoRepository.findAll();
        assertThat(fluxoDocumentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fluxoDocumentoRepository.findAll().size();
        // set the field null
        fluxoDocumento.setNome(null);

        // Create the FluxoDocumento, which fails.
        FluxoDocumentoDTO fluxoDocumentoDTO = fluxoDocumentoMapper.toDto(fluxoDocumento);

        restFluxoDocumentoMockMvc.perform(post("/api/fluxo-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoDocumentoDTO)))
            .andExpect(status().isBadRequest());

        List<FluxoDocumento> fluxoDocumentoList = fluxoDocumentoRepository.findAll();
        assertThat(fluxoDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPadraoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fluxoDocumentoRepository.findAll().size();
        // set the field null
        fluxoDocumento.setPadrao(null);

        // Create the FluxoDocumento, which fails.
        FluxoDocumentoDTO fluxoDocumentoDTO = fluxoDocumentoMapper.toDto(fluxoDocumento);

        restFluxoDocumentoMockMvc.perform(post("/api/fluxo-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoDocumentoDTO)))
            .andExpect(status().isBadRequest());

        List<FluxoDocumento> fluxoDocumentoList = fluxoDocumentoRepository.findAll();
        assertThat(fluxoDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEntidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fluxoDocumentoRepository.findAll().size();
        // set the field null
        fluxoDocumento.setEntidade(null);

        // Create the FluxoDocumento, which fails.
        FluxoDocumentoDTO fluxoDocumentoDTO = fluxoDocumentoMapper.toDto(fluxoDocumento);

        restFluxoDocumentoMockMvc.perform(post("/api/fluxo-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoDocumentoDTO)))
            .andExpect(status().isBadRequest());

        List<FluxoDocumento> fluxoDocumentoList = fluxoDocumentoRepository.findAll();
        assertThat(fluxoDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentos() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList
        restFluxoDocumentoMockMvc.perform(get("/api/fluxo-documentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fluxoDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].aumentaEstoque").value(hasItem(DEFAULT_AUMENTA_ESTOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].aumentaValorCaixa").value(hasItem(DEFAULT_AUMENTA_VALOR_CAIXA.booleanValue())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR)))
            .andExpect(jsonPath("$.[*].padrao").value(hasItem(DEFAULT_PADRAO.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].entidade").value(hasItem(DEFAULT_ENTIDADE.toString())));
    }
    
    @Test
    @Transactional
    public void getFluxoDocumento() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get the fluxoDocumento
        restFluxoDocumentoMockMvc.perform(get("/api/fluxo-documentos/{id}", fluxoDocumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fluxoDocumento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.aumentaEstoque").value(DEFAULT_AUMENTA_ESTOQUE.booleanValue()))
            .andExpect(jsonPath("$.aumentaValorCaixa").value(DEFAULT_AUMENTA_VALOR_CAIXA.booleanValue()))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR))
            .andExpect(jsonPath("$.padrao").value(DEFAULT_PADRAO.booleanValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.entidade").value(DEFAULT_ENTIDADE.toString()));
    }


    @Test
    @Transactional
    public void getFluxoDocumentosByIdFiltering() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        Long id = fluxoDocumento.getId();

        defaultFluxoDocumentoShouldBeFound("id.equals=" + id);
        defaultFluxoDocumentoShouldNotBeFound("id.notEquals=" + id);

        defaultFluxoDocumentoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFluxoDocumentoShouldNotBeFound("id.greaterThan=" + id);

        defaultFluxoDocumentoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFluxoDocumentoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFluxoDocumentosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where nome equals to DEFAULT_NOME
        defaultFluxoDocumentoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the fluxoDocumentoList where nome equals to UPDATED_NOME
        defaultFluxoDocumentoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where nome not equals to DEFAULT_NOME
        defaultFluxoDocumentoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the fluxoDocumentoList where nome not equals to UPDATED_NOME
        defaultFluxoDocumentoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultFluxoDocumentoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the fluxoDocumentoList where nome equals to UPDATED_NOME
        defaultFluxoDocumentoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where nome is not null
        defaultFluxoDocumentoShouldBeFound("nome.specified=true");

        // Get all the fluxoDocumentoList where nome is null
        defaultFluxoDocumentoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllFluxoDocumentosByNomeContainsSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where nome contains DEFAULT_NOME
        defaultFluxoDocumentoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the fluxoDocumentoList where nome contains UPDATED_NOME
        defaultFluxoDocumentoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where nome does not contain DEFAULT_NOME
        defaultFluxoDocumentoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the fluxoDocumentoList where nome does not contain UPDATED_NOME
        defaultFluxoDocumentoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllFluxoDocumentosByAumentaEstoqueIsEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where aumentaEstoque equals to DEFAULT_AUMENTA_ESTOQUE
        defaultFluxoDocumentoShouldBeFound("aumentaEstoque.equals=" + DEFAULT_AUMENTA_ESTOQUE);

        // Get all the fluxoDocumentoList where aumentaEstoque equals to UPDATED_AUMENTA_ESTOQUE
        defaultFluxoDocumentoShouldNotBeFound("aumentaEstoque.equals=" + UPDATED_AUMENTA_ESTOQUE);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByAumentaEstoqueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where aumentaEstoque not equals to DEFAULT_AUMENTA_ESTOQUE
        defaultFluxoDocumentoShouldNotBeFound("aumentaEstoque.notEquals=" + DEFAULT_AUMENTA_ESTOQUE);

        // Get all the fluxoDocumentoList where aumentaEstoque not equals to UPDATED_AUMENTA_ESTOQUE
        defaultFluxoDocumentoShouldBeFound("aumentaEstoque.notEquals=" + UPDATED_AUMENTA_ESTOQUE);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByAumentaEstoqueIsInShouldWork() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where aumentaEstoque in DEFAULT_AUMENTA_ESTOQUE or UPDATED_AUMENTA_ESTOQUE
        defaultFluxoDocumentoShouldBeFound("aumentaEstoque.in=" + DEFAULT_AUMENTA_ESTOQUE + "," + UPDATED_AUMENTA_ESTOQUE);

        // Get all the fluxoDocumentoList where aumentaEstoque equals to UPDATED_AUMENTA_ESTOQUE
        defaultFluxoDocumentoShouldNotBeFound("aumentaEstoque.in=" + UPDATED_AUMENTA_ESTOQUE);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByAumentaEstoqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where aumentaEstoque is not null
        defaultFluxoDocumentoShouldBeFound("aumentaEstoque.specified=true");

        // Get all the fluxoDocumentoList where aumentaEstoque is null
        defaultFluxoDocumentoShouldNotBeFound("aumentaEstoque.specified=false");
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByAumentaValorCaixaIsEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where aumentaValorCaixa equals to DEFAULT_AUMENTA_VALOR_CAIXA
        defaultFluxoDocumentoShouldBeFound("aumentaValorCaixa.equals=" + DEFAULT_AUMENTA_VALOR_CAIXA);

        // Get all the fluxoDocumentoList where aumentaValorCaixa equals to UPDATED_AUMENTA_VALOR_CAIXA
        defaultFluxoDocumentoShouldNotBeFound("aumentaValorCaixa.equals=" + UPDATED_AUMENTA_VALOR_CAIXA);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByAumentaValorCaixaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where aumentaValorCaixa not equals to DEFAULT_AUMENTA_VALOR_CAIXA
        defaultFluxoDocumentoShouldNotBeFound("aumentaValorCaixa.notEquals=" + DEFAULT_AUMENTA_VALOR_CAIXA);

        // Get all the fluxoDocumentoList where aumentaValorCaixa not equals to UPDATED_AUMENTA_VALOR_CAIXA
        defaultFluxoDocumentoShouldBeFound("aumentaValorCaixa.notEquals=" + UPDATED_AUMENTA_VALOR_CAIXA);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByAumentaValorCaixaIsInShouldWork() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where aumentaValorCaixa in DEFAULT_AUMENTA_VALOR_CAIXA or UPDATED_AUMENTA_VALOR_CAIXA
        defaultFluxoDocumentoShouldBeFound("aumentaValorCaixa.in=" + DEFAULT_AUMENTA_VALOR_CAIXA + "," + UPDATED_AUMENTA_VALOR_CAIXA);

        // Get all the fluxoDocumentoList where aumentaValorCaixa equals to UPDATED_AUMENTA_VALOR_CAIXA
        defaultFluxoDocumentoShouldNotBeFound("aumentaValorCaixa.in=" + UPDATED_AUMENTA_VALOR_CAIXA);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByAumentaValorCaixaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where aumentaValorCaixa is not null
        defaultFluxoDocumentoShouldBeFound("aumentaValorCaixa.specified=true");

        // Get all the fluxoDocumentoList where aumentaValorCaixa is null
        defaultFluxoDocumentoShouldNotBeFound("aumentaValorCaixa.specified=false");
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByCorIsEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where cor equals to DEFAULT_COR
        defaultFluxoDocumentoShouldBeFound("cor.equals=" + DEFAULT_COR);

        // Get all the fluxoDocumentoList where cor equals to UPDATED_COR
        defaultFluxoDocumentoShouldNotBeFound("cor.equals=" + UPDATED_COR);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByCorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where cor not equals to DEFAULT_COR
        defaultFluxoDocumentoShouldNotBeFound("cor.notEquals=" + DEFAULT_COR);

        // Get all the fluxoDocumentoList where cor not equals to UPDATED_COR
        defaultFluxoDocumentoShouldBeFound("cor.notEquals=" + UPDATED_COR);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByCorIsInShouldWork() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where cor in DEFAULT_COR or UPDATED_COR
        defaultFluxoDocumentoShouldBeFound("cor.in=" + DEFAULT_COR + "," + UPDATED_COR);

        // Get all the fluxoDocumentoList where cor equals to UPDATED_COR
        defaultFluxoDocumentoShouldNotBeFound("cor.in=" + UPDATED_COR);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByCorIsNullOrNotNull() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where cor is not null
        defaultFluxoDocumentoShouldBeFound("cor.specified=true");

        // Get all the fluxoDocumentoList where cor is null
        defaultFluxoDocumentoShouldNotBeFound("cor.specified=false");
    }
                @Test
    @Transactional
    public void getAllFluxoDocumentosByCorContainsSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where cor contains DEFAULT_COR
        defaultFluxoDocumentoShouldBeFound("cor.contains=" + DEFAULT_COR);

        // Get all the fluxoDocumentoList where cor contains UPDATED_COR
        defaultFluxoDocumentoShouldNotBeFound("cor.contains=" + UPDATED_COR);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByCorNotContainsSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where cor does not contain DEFAULT_COR
        defaultFluxoDocumentoShouldNotBeFound("cor.doesNotContain=" + DEFAULT_COR);

        // Get all the fluxoDocumentoList where cor does not contain UPDATED_COR
        defaultFluxoDocumentoShouldBeFound("cor.doesNotContain=" + UPDATED_COR);
    }


    @Test
    @Transactional
    public void getAllFluxoDocumentosByPadraoIsEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where padrao equals to DEFAULT_PADRAO
        defaultFluxoDocumentoShouldBeFound("padrao.equals=" + DEFAULT_PADRAO);

        // Get all the fluxoDocumentoList where padrao equals to UPDATED_PADRAO
        defaultFluxoDocumentoShouldNotBeFound("padrao.equals=" + UPDATED_PADRAO);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByPadraoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where padrao not equals to DEFAULT_PADRAO
        defaultFluxoDocumentoShouldNotBeFound("padrao.notEquals=" + DEFAULT_PADRAO);

        // Get all the fluxoDocumentoList where padrao not equals to UPDATED_PADRAO
        defaultFluxoDocumentoShouldBeFound("padrao.notEquals=" + UPDATED_PADRAO);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByPadraoIsInShouldWork() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where padrao in DEFAULT_PADRAO or UPDATED_PADRAO
        defaultFluxoDocumentoShouldBeFound("padrao.in=" + DEFAULT_PADRAO + "," + UPDATED_PADRAO);

        // Get all the fluxoDocumentoList where padrao equals to UPDATED_PADRAO
        defaultFluxoDocumentoShouldNotBeFound("padrao.in=" + UPDATED_PADRAO);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByPadraoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where padrao is not null
        defaultFluxoDocumentoShouldBeFound("padrao.specified=true");

        // Get all the fluxoDocumentoList where padrao is null
        defaultFluxoDocumentoShouldNotBeFound("padrao.specified=false");
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByEntidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where entidade equals to DEFAULT_ENTIDADE
        defaultFluxoDocumentoShouldBeFound("entidade.equals=" + DEFAULT_ENTIDADE);

        // Get all the fluxoDocumentoList where entidade equals to UPDATED_ENTIDADE
        defaultFluxoDocumentoShouldNotBeFound("entidade.equals=" + UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByEntidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where entidade not equals to DEFAULT_ENTIDADE
        defaultFluxoDocumentoShouldNotBeFound("entidade.notEquals=" + DEFAULT_ENTIDADE);

        // Get all the fluxoDocumentoList where entidade not equals to UPDATED_ENTIDADE
        defaultFluxoDocumentoShouldBeFound("entidade.notEquals=" + UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByEntidadeIsInShouldWork() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where entidade in DEFAULT_ENTIDADE or UPDATED_ENTIDADE
        defaultFluxoDocumentoShouldBeFound("entidade.in=" + DEFAULT_ENTIDADE + "," + UPDATED_ENTIDADE);

        // Get all the fluxoDocumentoList where entidade equals to UPDATED_ENTIDADE
        defaultFluxoDocumentoShouldNotBeFound("entidade.in=" + UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByEntidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        // Get all the fluxoDocumentoList where entidade is not null
        defaultFluxoDocumentoShouldBeFound("entidade.specified=true");

        // Get all the fluxoDocumentoList where entidade is null
        defaultFluxoDocumentoShouldNotBeFound("entidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllFluxoDocumentosByProdutoIsEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);
        Produto produto = ProdutoResourceIT.createEntity(em);
        em.persist(produto);
        em.flush();
        fluxoDocumento.addProduto(produto);
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);
        Long produtoId = produto.getId();

        // Get all the fluxoDocumentoList where produto equals to produtoId
        defaultFluxoDocumentoShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the fluxoDocumentoList where produto equals to produtoId + 1
        defaultFluxoDocumentoShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }


    @Test
    @Transactional
    public void getAllFluxoDocumentosByItemCompraIsEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);
        ItemCompra itemCompra = ItemCompraResourceIT.createEntity(em);
        em.persist(itemCompra);
        em.flush();
        fluxoDocumento.addItemCompra(itemCompra);
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);
        Long itemCompraId = itemCompra.getId();

        // Get all the fluxoDocumentoList where itemCompra equals to itemCompraId
        defaultFluxoDocumentoShouldBeFound("itemCompraId.equals=" + itemCompraId);

        // Get all the fluxoDocumentoList where itemCompra equals to itemCompraId + 1
        defaultFluxoDocumentoShouldNotBeFound("itemCompraId.equals=" + (itemCompraId + 1));
    }


    @Test
    @Transactional
    public void getAllFluxoDocumentosByItemVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);
        ItemVenda itemVenda = ItemVendaResourceIT.createEntity(em);
        em.persist(itemVenda);
        em.flush();
        fluxoDocumento.addItemVenda(itemVenda);
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);
        Long itemVendaId = itemVenda.getId();

        // Get all the fluxoDocumentoList where itemVenda equals to itemVendaId
        defaultFluxoDocumentoShouldBeFound("itemVendaId.equals=" + itemVendaId);

        // Get all the fluxoDocumentoList where itemVenda equals to itemVendaId + 1
        defaultFluxoDocumentoShouldNotBeFound("itemVendaId.equals=" + (itemVendaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFluxoDocumentoShouldBeFound(String filter) throws Exception {
        restFluxoDocumentoMockMvc.perform(get("/api/fluxo-documentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fluxoDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].aumentaEstoque").value(hasItem(DEFAULT_AUMENTA_ESTOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].aumentaValorCaixa").value(hasItem(DEFAULT_AUMENTA_VALOR_CAIXA.booleanValue())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR)))
            .andExpect(jsonPath("$.[*].padrao").value(hasItem(DEFAULT_PADRAO.booleanValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].entidade").value(hasItem(DEFAULT_ENTIDADE.toString())));

        // Check, that the count call also returns 1
        restFluxoDocumentoMockMvc.perform(get("/api/fluxo-documentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFluxoDocumentoShouldNotBeFound(String filter) throws Exception {
        restFluxoDocumentoMockMvc.perform(get("/api/fluxo-documentos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFluxoDocumentoMockMvc.perform(get("/api/fluxo-documentos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFluxoDocumento() throws Exception {
        // Get the fluxoDocumento
        restFluxoDocumentoMockMvc.perform(get("/api/fluxo-documentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFluxoDocumento() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        int databaseSizeBeforeUpdate = fluxoDocumentoRepository.findAll().size();

        // Update the fluxoDocumento
        FluxoDocumento updatedFluxoDocumento = fluxoDocumentoRepository.findById(fluxoDocumento.getId()).get();
        // Disconnect from session so that the updates on updatedFluxoDocumento are not directly saved in db
        em.detach(updatedFluxoDocumento);
        updatedFluxoDocumento
            .nome(UPDATED_NOME)
            .aumentaEstoque(UPDATED_AUMENTA_ESTOQUE)
            .aumentaValorCaixa(UPDATED_AUMENTA_VALOR_CAIXA)
            .cor(UPDATED_COR)
            .padrao(UPDATED_PADRAO)
            .descricao(UPDATED_DESCRICAO)
            .entidade(UPDATED_ENTIDADE);
        FluxoDocumentoDTO fluxoDocumentoDTO = fluxoDocumentoMapper.toDto(updatedFluxoDocumento);

        restFluxoDocumentoMockMvc.perform(put("/api/fluxo-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoDocumentoDTO)))
            .andExpect(status().isOk());

        // Validate the FluxoDocumento in the database
        List<FluxoDocumento> fluxoDocumentoList = fluxoDocumentoRepository.findAll();
        assertThat(fluxoDocumentoList).hasSize(databaseSizeBeforeUpdate);
        FluxoDocumento testFluxoDocumento = fluxoDocumentoList.get(fluxoDocumentoList.size() - 1);
        assertThat(testFluxoDocumento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFluxoDocumento.isAumentaEstoque()).isEqualTo(UPDATED_AUMENTA_ESTOQUE);
        assertThat(testFluxoDocumento.isAumentaValorCaixa()).isEqualTo(UPDATED_AUMENTA_VALOR_CAIXA);
        assertThat(testFluxoDocumento.getCor()).isEqualTo(UPDATED_COR);
        assertThat(testFluxoDocumento.isPadrao()).isEqualTo(UPDATED_PADRAO);
        assertThat(testFluxoDocumento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testFluxoDocumento.getEntidade()).isEqualTo(UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingFluxoDocumento() throws Exception {
        int databaseSizeBeforeUpdate = fluxoDocumentoRepository.findAll().size();

        // Create the FluxoDocumento
        FluxoDocumentoDTO fluxoDocumentoDTO = fluxoDocumentoMapper.toDto(fluxoDocumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFluxoDocumentoMockMvc.perform(put("/api/fluxo-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoDocumentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FluxoDocumento in the database
        List<FluxoDocumento> fluxoDocumentoList = fluxoDocumentoRepository.findAll();
        assertThat(fluxoDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFluxoDocumento() throws Exception {
        // Initialize the database
        fluxoDocumentoRepository.saveAndFlush(fluxoDocumento);

        int databaseSizeBeforeDelete = fluxoDocumentoRepository.findAll().size();

        // Delete the fluxoDocumento
        restFluxoDocumentoMockMvc.perform(delete("/api/fluxo-documentos/{id}", fluxoDocumento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FluxoDocumento> fluxoDocumentoList = fluxoDocumentoRepository.findAll();
        assertThat(fluxoDocumentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
