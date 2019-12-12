package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.DocumentoComercial;
import com.ravunana.manda.domain.LancamentoFinanceiro;
import com.ravunana.manda.domain.Compra;
import com.ravunana.manda.domain.Venda;
import com.ravunana.manda.repository.DocumentoComercialRepository;
import com.ravunana.manda.service.DocumentoComercialService;
import com.ravunana.manda.service.dto.DocumentoComercialDTO;
import com.ravunana.manda.service.mapper.DocumentoComercialMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.DocumentoComercialCriteria;
import com.ravunana.manda.service.DocumentoComercialQueryService;

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
 * Integration tests for the {@link DocumentoComercialResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class DocumentoComercialResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PADRAO = false;
    private static final Boolean UPDATED_PADRAO = true;

    private static final Boolean DEFAULT_MOVIMENTA_ESTOQUE = false;
    private static final Boolean UPDATED_MOVIMENTA_ESTOQUE = true;

    private static final Boolean DEFAULT_MOVIMENTA_CAIXA = false;
    private static final Boolean UPDATED_MOVIMENTA_CAIXA = true;

    private static final Boolean DEFAULT_MOVIMENTA_CONTABILIDADE = false;
    private static final Boolean UPDATED_MOVIMENTA_CONTABILIDADE = true;

    private static final String DEFAULT_COR = "AAAAAAAAAA";
    private static final String UPDATED_COR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MOSTRA_PONTO_VENDA = false;
    private static final Boolean UPDATED_MOSTRA_PONTO_VENDA = true;

    private static final EntidadeSistema DEFAULT_ENTIDADE = EntidadeSistema.SOFTWARE;
    private static final EntidadeSistema UPDATED_ENTIDADE = EntidadeSistema.LICENCA_SOFTWARE;

    @Autowired
    private DocumentoComercialRepository documentoComercialRepository;

    @Autowired
    private DocumentoComercialMapper documentoComercialMapper;

    @Autowired
    private DocumentoComercialService documentoComercialService;

    @Autowired
    private DocumentoComercialQueryService documentoComercialQueryService;

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

    private MockMvc restDocumentoComercialMockMvc;

    private DocumentoComercial documentoComercial;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentoComercialResource documentoComercialResource = new DocumentoComercialResource(documentoComercialService, documentoComercialQueryService);
        this.restDocumentoComercialMockMvc = MockMvcBuilders.standaloneSetup(documentoComercialResource)
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
    public static DocumentoComercial createEntity(EntityManager em) {
        DocumentoComercial documentoComercial = new DocumentoComercial()
            .nome(DEFAULT_NOME)
            .serie(DEFAULT_SERIE)
            .padrao(DEFAULT_PADRAO)
            .movimentaEstoque(DEFAULT_MOVIMENTA_ESTOQUE)
            .movimentaCaixa(DEFAULT_MOVIMENTA_CAIXA)
            .movimentaContabilidade(DEFAULT_MOVIMENTA_CONTABILIDADE)
            .cor(DEFAULT_COR)
            .descricao(DEFAULT_DESCRICAO)
            .mostraPontoVenda(DEFAULT_MOSTRA_PONTO_VENDA)
            .entidade(DEFAULT_ENTIDADE);
        return documentoComercial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentoComercial createUpdatedEntity(EntityManager em) {
        DocumentoComercial documentoComercial = new DocumentoComercial()
            .nome(UPDATED_NOME)
            .serie(UPDATED_SERIE)
            .padrao(UPDATED_PADRAO)
            .movimentaEstoque(UPDATED_MOVIMENTA_ESTOQUE)
            .movimentaCaixa(UPDATED_MOVIMENTA_CAIXA)
            .movimentaContabilidade(UPDATED_MOVIMENTA_CONTABILIDADE)
            .cor(UPDATED_COR)
            .descricao(UPDATED_DESCRICAO)
            .mostraPontoVenda(UPDATED_MOSTRA_PONTO_VENDA)
            .entidade(UPDATED_ENTIDADE);
        return documentoComercial;
    }

    @BeforeEach
    public void initTest() {
        documentoComercial = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentoComercial() throws Exception {
        int databaseSizeBeforeCreate = documentoComercialRepository.findAll().size();

        // Create the DocumentoComercial
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);
        restDocumentoComercialMockMvc.perform(post("/api/documento-comercials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentoComercial testDocumentoComercial = documentoComercialList.get(documentoComercialList.size() - 1);
        assertThat(testDocumentoComercial.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDocumentoComercial.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testDocumentoComercial.isPadrao()).isEqualTo(DEFAULT_PADRAO);
        assertThat(testDocumentoComercial.isMovimentaEstoque()).isEqualTo(DEFAULT_MOVIMENTA_ESTOQUE);
        assertThat(testDocumentoComercial.isMovimentaCaixa()).isEqualTo(DEFAULT_MOVIMENTA_CAIXA);
        assertThat(testDocumentoComercial.isMovimentaContabilidade()).isEqualTo(DEFAULT_MOVIMENTA_CONTABILIDADE);
        assertThat(testDocumentoComercial.getCor()).isEqualTo(DEFAULT_COR);
        assertThat(testDocumentoComercial.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testDocumentoComercial.isMostraPontoVenda()).isEqualTo(DEFAULT_MOSTRA_PONTO_VENDA);
        assertThat(testDocumentoComercial.getEntidade()).isEqualTo(DEFAULT_ENTIDADE);
    }

    @Test
    @Transactional
    public void createDocumentoComercialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentoComercialRepository.findAll().size();

        // Create the DocumentoComercial with an existing ID
        documentoComercial.setId(1L);
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentoComercialMockMvc.perform(post("/api/documento-comercials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoComercialRepository.findAll().size();
        // set the field null
        documentoComercial.setNome(null);

        // Create the DocumentoComercial, which fails.
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        restDocumentoComercialMockMvc.perform(post("/api/documento-comercials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSerieIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoComercialRepository.findAll().size();
        // set the field null
        documentoComercial.setSerie(null);

        // Create the DocumentoComercial, which fails.
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        restDocumentoComercialMockMvc.perform(post("/api/documento-comercials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPadraoIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoComercialRepository.findAll().size();
        // set the field null
        documentoComercial.setPadrao(null);

        // Create the DocumentoComercial, which fails.
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        restDocumentoComercialMockMvc.perform(post("/api/documento-comercials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMovimentaEstoqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoComercialRepository.findAll().size();
        // set the field null
        documentoComercial.setMovimentaEstoque(null);

        // Create the DocumentoComercial, which fails.
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        restDocumentoComercialMockMvc.perform(post("/api/documento-comercials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMovimentaCaixaIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoComercialRepository.findAll().size();
        // set the field null
        documentoComercial.setMovimentaCaixa(null);

        // Create the DocumentoComercial, which fails.
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        restDocumentoComercialMockMvc.perform(post("/api/documento-comercials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMovimentaContabilidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentoComercialRepository.findAll().size();
        // set the field null
        documentoComercial.setMovimentaContabilidade(null);

        // Create the DocumentoComercial, which fails.
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        restDocumentoComercialMockMvc.perform(post("/api/documento-comercials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercials() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList
        restDocumentoComercialMockMvc.perform(get("/api/documento-comercials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentoComercial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].padrao").value(hasItem(DEFAULT_PADRAO.booleanValue())))
            .andExpect(jsonPath("$.[*].movimentaEstoque").value(hasItem(DEFAULT_MOVIMENTA_ESTOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].movimentaCaixa").value(hasItem(DEFAULT_MOVIMENTA_CAIXA.booleanValue())))
            .andExpect(jsonPath("$.[*].movimentaContabilidade").value(hasItem(DEFAULT_MOVIMENTA_CONTABILIDADE.booleanValue())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].mostraPontoVenda").value(hasItem(DEFAULT_MOSTRA_PONTO_VENDA.booleanValue())))
            .andExpect(jsonPath("$.[*].entidade").value(hasItem(DEFAULT_ENTIDADE.toString())));
    }
    
    @Test
    @Transactional
    public void getDocumentoComercial() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get the documentoComercial
        restDocumentoComercialMockMvc.perform(get("/api/documento-comercials/{id}", documentoComercial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentoComercial.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE))
            .andExpect(jsonPath("$.padrao").value(DEFAULT_PADRAO.booleanValue()))
            .andExpect(jsonPath("$.movimentaEstoque").value(DEFAULT_MOVIMENTA_ESTOQUE.booleanValue()))
            .andExpect(jsonPath("$.movimentaCaixa").value(DEFAULT_MOVIMENTA_CAIXA.booleanValue()))
            .andExpect(jsonPath("$.movimentaContabilidade").value(DEFAULT_MOVIMENTA_CONTABILIDADE.booleanValue()))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.mostraPontoVenda").value(DEFAULT_MOSTRA_PONTO_VENDA.booleanValue()))
            .andExpect(jsonPath("$.entidade").value(DEFAULT_ENTIDADE.toString()));
    }


    @Test
    @Transactional
    public void getDocumentoComercialsByIdFiltering() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        Long id = documentoComercial.getId();

        defaultDocumentoComercialShouldBeFound("id.equals=" + id);
        defaultDocumentoComercialShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentoComercialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentoComercialShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentoComercialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentoComercialShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDocumentoComercialsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where nome equals to DEFAULT_NOME
        defaultDocumentoComercialShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the documentoComercialList where nome equals to UPDATED_NOME
        defaultDocumentoComercialShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where nome not equals to DEFAULT_NOME
        defaultDocumentoComercialShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the documentoComercialList where nome not equals to UPDATED_NOME
        defaultDocumentoComercialShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultDocumentoComercialShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the documentoComercialList where nome equals to UPDATED_NOME
        defaultDocumentoComercialShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where nome is not null
        defaultDocumentoComercialShouldBeFound("nome.specified=true");

        // Get all the documentoComercialList where nome is null
        defaultDocumentoComercialShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentoComercialsByNomeContainsSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where nome contains DEFAULT_NOME
        defaultDocumentoComercialShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the documentoComercialList where nome contains UPDATED_NOME
        defaultDocumentoComercialShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where nome does not contain DEFAULT_NOME
        defaultDocumentoComercialShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the documentoComercialList where nome does not contain UPDATED_NOME
        defaultDocumentoComercialShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllDocumentoComercialsBySerieIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where serie equals to DEFAULT_SERIE
        defaultDocumentoComercialShouldBeFound("serie.equals=" + DEFAULT_SERIE);

        // Get all the documentoComercialList where serie equals to UPDATED_SERIE
        defaultDocumentoComercialShouldNotBeFound("serie.equals=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsBySerieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where serie not equals to DEFAULT_SERIE
        defaultDocumentoComercialShouldNotBeFound("serie.notEquals=" + DEFAULT_SERIE);

        // Get all the documentoComercialList where serie not equals to UPDATED_SERIE
        defaultDocumentoComercialShouldBeFound("serie.notEquals=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsBySerieIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where serie in DEFAULT_SERIE or UPDATED_SERIE
        defaultDocumentoComercialShouldBeFound("serie.in=" + DEFAULT_SERIE + "," + UPDATED_SERIE);

        // Get all the documentoComercialList where serie equals to UPDATED_SERIE
        defaultDocumentoComercialShouldNotBeFound("serie.in=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsBySerieIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where serie is not null
        defaultDocumentoComercialShouldBeFound("serie.specified=true");

        // Get all the documentoComercialList where serie is null
        defaultDocumentoComercialShouldNotBeFound("serie.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentoComercialsBySerieContainsSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where serie contains DEFAULT_SERIE
        defaultDocumentoComercialShouldBeFound("serie.contains=" + DEFAULT_SERIE);

        // Get all the documentoComercialList where serie contains UPDATED_SERIE
        defaultDocumentoComercialShouldNotBeFound("serie.contains=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsBySerieNotContainsSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where serie does not contain DEFAULT_SERIE
        defaultDocumentoComercialShouldNotBeFound("serie.doesNotContain=" + DEFAULT_SERIE);

        // Get all the documentoComercialList where serie does not contain UPDATED_SERIE
        defaultDocumentoComercialShouldBeFound("serie.doesNotContain=" + UPDATED_SERIE);
    }


    @Test
    @Transactional
    public void getAllDocumentoComercialsByPadraoIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where padrao equals to DEFAULT_PADRAO
        defaultDocumentoComercialShouldBeFound("padrao.equals=" + DEFAULT_PADRAO);

        // Get all the documentoComercialList where padrao equals to UPDATED_PADRAO
        defaultDocumentoComercialShouldNotBeFound("padrao.equals=" + UPDATED_PADRAO);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByPadraoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where padrao not equals to DEFAULT_PADRAO
        defaultDocumentoComercialShouldNotBeFound("padrao.notEquals=" + DEFAULT_PADRAO);

        // Get all the documentoComercialList where padrao not equals to UPDATED_PADRAO
        defaultDocumentoComercialShouldBeFound("padrao.notEquals=" + UPDATED_PADRAO);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByPadraoIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where padrao in DEFAULT_PADRAO or UPDATED_PADRAO
        defaultDocumentoComercialShouldBeFound("padrao.in=" + DEFAULT_PADRAO + "," + UPDATED_PADRAO);

        // Get all the documentoComercialList where padrao equals to UPDATED_PADRAO
        defaultDocumentoComercialShouldNotBeFound("padrao.in=" + UPDATED_PADRAO);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByPadraoIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where padrao is not null
        defaultDocumentoComercialShouldBeFound("padrao.specified=true");

        // Get all the documentoComercialList where padrao is null
        defaultDocumentoComercialShouldNotBeFound("padrao.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMovimentaEstoqueIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where movimentaEstoque equals to DEFAULT_MOVIMENTA_ESTOQUE
        defaultDocumentoComercialShouldBeFound("movimentaEstoque.equals=" + DEFAULT_MOVIMENTA_ESTOQUE);

        // Get all the documentoComercialList where movimentaEstoque equals to UPDATED_MOVIMENTA_ESTOQUE
        defaultDocumentoComercialShouldNotBeFound("movimentaEstoque.equals=" + UPDATED_MOVIMENTA_ESTOQUE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMovimentaEstoqueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where movimentaEstoque not equals to DEFAULT_MOVIMENTA_ESTOQUE
        defaultDocumentoComercialShouldNotBeFound("movimentaEstoque.notEquals=" + DEFAULT_MOVIMENTA_ESTOQUE);

        // Get all the documentoComercialList where movimentaEstoque not equals to UPDATED_MOVIMENTA_ESTOQUE
        defaultDocumentoComercialShouldBeFound("movimentaEstoque.notEquals=" + UPDATED_MOVIMENTA_ESTOQUE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMovimentaEstoqueIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where movimentaEstoque in DEFAULT_MOVIMENTA_ESTOQUE or UPDATED_MOVIMENTA_ESTOQUE
        defaultDocumentoComercialShouldBeFound("movimentaEstoque.in=" + DEFAULT_MOVIMENTA_ESTOQUE + "," + UPDATED_MOVIMENTA_ESTOQUE);

        // Get all the documentoComercialList where movimentaEstoque equals to UPDATED_MOVIMENTA_ESTOQUE
        defaultDocumentoComercialShouldNotBeFound("movimentaEstoque.in=" + UPDATED_MOVIMENTA_ESTOQUE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMovimentaEstoqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where movimentaEstoque is not null
        defaultDocumentoComercialShouldBeFound("movimentaEstoque.specified=true");

        // Get all the documentoComercialList where movimentaEstoque is null
        defaultDocumentoComercialShouldNotBeFound("movimentaEstoque.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMovimentaCaixaIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where movimentaCaixa equals to DEFAULT_MOVIMENTA_CAIXA
        defaultDocumentoComercialShouldBeFound("movimentaCaixa.equals=" + DEFAULT_MOVIMENTA_CAIXA);

        // Get all the documentoComercialList where movimentaCaixa equals to UPDATED_MOVIMENTA_CAIXA
        defaultDocumentoComercialShouldNotBeFound("movimentaCaixa.equals=" + UPDATED_MOVIMENTA_CAIXA);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMovimentaCaixaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where movimentaCaixa not equals to DEFAULT_MOVIMENTA_CAIXA
        defaultDocumentoComercialShouldNotBeFound("movimentaCaixa.notEquals=" + DEFAULT_MOVIMENTA_CAIXA);

        // Get all the documentoComercialList where movimentaCaixa not equals to UPDATED_MOVIMENTA_CAIXA
        defaultDocumentoComercialShouldBeFound("movimentaCaixa.notEquals=" + UPDATED_MOVIMENTA_CAIXA);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMovimentaCaixaIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where movimentaCaixa in DEFAULT_MOVIMENTA_CAIXA or UPDATED_MOVIMENTA_CAIXA
        defaultDocumentoComercialShouldBeFound("movimentaCaixa.in=" + DEFAULT_MOVIMENTA_CAIXA + "," + UPDATED_MOVIMENTA_CAIXA);

        // Get all the documentoComercialList where movimentaCaixa equals to UPDATED_MOVIMENTA_CAIXA
        defaultDocumentoComercialShouldNotBeFound("movimentaCaixa.in=" + UPDATED_MOVIMENTA_CAIXA);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMovimentaCaixaIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where movimentaCaixa is not null
        defaultDocumentoComercialShouldBeFound("movimentaCaixa.specified=true");

        // Get all the documentoComercialList where movimentaCaixa is null
        defaultDocumentoComercialShouldNotBeFound("movimentaCaixa.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMovimentaContabilidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where movimentaContabilidade equals to DEFAULT_MOVIMENTA_CONTABILIDADE
        defaultDocumentoComercialShouldBeFound("movimentaContabilidade.equals=" + DEFAULT_MOVIMENTA_CONTABILIDADE);

        // Get all the documentoComercialList where movimentaContabilidade equals to UPDATED_MOVIMENTA_CONTABILIDADE
        defaultDocumentoComercialShouldNotBeFound("movimentaContabilidade.equals=" + UPDATED_MOVIMENTA_CONTABILIDADE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMovimentaContabilidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where movimentaContabilidade not equals to DEFAULT_MOVIMENTA_CONTABILIDADE
        defaultDocumentoComercialShouldNotBeFound("movimentaContabilidade.notEquals=" + DEFAULT_MOVIMENTA_CONTABILIDADE);

        // Get all the documentoComercialList where movimentaContabilidade not equals to UPDATED_MOVIMENTA_CONTABILIDADE
        defaultDocumentoComercialShouldBeFound("movimentaContabilidade.notEquals=" + UPDATED_MOVIMENTA_CONTABILIDADE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMovimentaContabilidadeIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where movimentaContabilidade in DEFAULT_MOVIMENTA_CONTABILIDADE or UPDATED_MOVIMENTA_CONTABILIDADE
        defaultDocumentoComercialShouldBeFound("movimentaContabilidade.in=" + DEFAULT_MOVIMENTA_CONTABILIDADE + "," + UPDATED_MOVIMENTA_CONTABILIDADE);

        // Get all the documentoComercialList where movimentaContabilidade equals to UPDATED_MOVIMENTA_CONTABILIDADE
        defaultDocumentoComercialShouldNotBeFound("movimentaContabilidade.in=" + UPDATED_MOVIMENTA_CONTABILIDADE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMovimentaContabilidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where movimentaContabilidade is not null
        defaultDocumentoComercialShouldBeFound("movimentaContabilidade.specified=true");

        // Get all the documentoComercialList where movimentaContabilidade is null
        defaultDocumentoComercialShouldNotBeFound("movimentaContabilidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByCorIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where cor equals to DEFAULT_COR
        defaultDocumentoComercialShouldBeFound("cor.equals=" + DEFAULT_COR);

        // Get all the documentoComercialList where cor equals to UPDATED_COR
        defaultDocumentoComercialShouldNotBeFound("cor.equals=" + UPDATED_COR);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByCorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where cor not equals to DEFAULT_COR
        defaultDocumentoComercialShouldNotBeFound("cor.notEquals=" + DEFAULT_COR);

        // Get all the documentoComercialList where cor not equals to UPDATED_COR
        defaultDocumentoComercialShouldBeFound("cor.notEquals=" + UPDATED_COR);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByCorIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where cor in DEFAULT_COR or UPDATED_COR
        defaultDocumentoComercialShouldBeFound("cor.in=" + DEFAULT_COR + "," + UPDATED_COR);

        // Get all the documentoComercialList where cor equals to UPDATED_COR
        defaultDocumentoComercialShouldNotBeFound("cor.in=" + UPDATED_COR);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByCorIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where cor is not null
        defaultDocumentoComercialShouldBeFound("cor.specified=true");

        // Get all the documentoComercialList where cor is null
        defaultDocumentoComercialShouldNotBeFound("cor.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentoComercialsByCorContainsSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where cor contains DEFAULT_COR
        defaultDocumentoComercialShouldBeFound("cor.contains=" + DEFAULT_COR);

        // Get all the documentoComercialList where cor contains UPDATED_COR
        defaultDocumentoComercialShouldNotBeFound("cor.contains=" + UPDATED_COR);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByCorNotContainsSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where cor does not contain DEFAULT_COR
        defaultDocumentoComercialShouldNotBeFound("cor.doesNotContain=" + DEFAULT_COR);

        // Get all the documentoComercialList where cor does not contain UPDATED_COR
        defaultDocumentoComercialShouldBeFound("cor.doesNotContain=" + UPDATED_COR);
    }


    @Test
    @Transactional
    public void getAllDocumentoComercialsByMostraPontoVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where mostraPontoVenda equals to DEFAULT_MOSTRA_PONTO_VENDA
        defaultDocumentoComercialShouldBeFound("mostraPontoVenda.equals=" + DEFAULT_MOSTRA_PONTO_VENDA);

        // Get all the documentoComercialList where mostraPontoVenda equals to UPDATED_MOSTRA_PONTO_VENDA
        defaultDocumentoComercialShouldNotBeFound("mostraPontoVenda.equals=" + UPDATED_MOSTRA_PONTO_VENDA);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMostraPontoVendaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where mostraPontoVenda not equals to DEFAULT_MOSTRA_PONTO_VENDA
        defaultDocumentoComercialShouldNotBeFound("mostraPontoVenda.notEquals=" + DEFAULT_MOSTRA_PONTO_VENDA);

        // Get all the documentoComercialList where mostraPontoVenda not equals to UPDATED_MOSTRA_PONTO_VENDA
        defaultDocumentoComercialShouldBeFound("mostraPontoVenda.notEquals=" + UPDATED_MOSTRA_PONTO_VENDA);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMostraPontoVendaIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where mostraPontoVenda in DEFAULT_MOSTRA_PONTO_VENDA or UPDATED_MOSTRA_PONTO_VENDA
        defaultDocumentoComercialShouldBeFound("mostraPontoVenda.in=" + DEFAULT_MOSTRA_PONTO_VENDA + "," + UPDATED_MOSTRA_PONTO_VENDA);

        // Get all the documentoComercialList where mostraPontoVenda equals to UPDATED_MOSTRA_PONTO_VENDA
        defaultDocumentoComercialShouldNotBeFound("mostraPontoVenda.in=" + UPDATED_MOSTRA_PONTO_VENDA);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByMostraPontoVendaIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where mostraPontoVenda is not null
        defaultDocumentoComercialShouldBeFound("mostraPontoVenda.specified=true");

        // Get all the documentoComercialList where mostraPontoVenda is null
        defaultDocumentoComercialShouldNotBeFound("mostraPontoVenda.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByEntidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where entidade equals to DEFAULT_ENTIDADE
        defaultDocumentoComercialShouldBeFound("entidade.equals=" + DEFAULT_ENTIDADE);

        // Get all the documentoComercialList where entidade equals to UPDATED_ENTIDADE
        defaultDocumentoComercialShouldNotBeFound("entidade.equals=" + UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByEntidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where entidade not equals to DEFAULT_ENTIDADE
        defaultDocumentoComercialShouldNotBeFound("entidade.notEquals=" + DEFAULT_ENTIDADE);

        // Get all the documentoComercialList where entidade not equals to UPDATED_ENTIDADE
        defaultDocumentoComercialShouldBeFound("entidade.notEquals=" + UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByEntidadeIsInShouldWork() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where entidade in DEFAULT_ENTIDADE or UPDATED_ENTIDADE
        defaultDocumentoComercialShouldBeFound("entidade.in=" + DEFAULT_ENTIDADE + "," + UPDATED_ENTIDADE);

        // Get all the documentoComercialList where entidade equals to UPDATED_ENTIDADE
        defaultDocumentoComercialShouldNotBeFound("entidade.in=" + UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByEntidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        // Get all the documentoComercialList where entidade is not null
        defaultDocumentoComercialShouldBeFound("entidade.specified=true");

        // Get all the documentoComercialList where entidade is null
        defaultDocumentoComercialShouldNotBeFound("entidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentoComercialsByLancamentoFinanceiroIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);
        LancamentoFinanceiro lancamentoFinanceiro = LancamentoFinanceiroResourceIT.createEntity(em);
        em.persist(lancamentoFinanceiro);
        em.flush();
        documentoComercial.addLancamentoFinanceiro(lancamentoFinanceiro);
        documentoComercialRepository.saveAndFlush(documentoComercial);
        Long lancamentoFinanceiroId = lancamentoFinanceiro.getId();

        // Get all the documentoComercialList where lancamentoFinanceiro equals to lancamentoFinanceiroId
        defaultDocumentoComercialShouldBeFound("lancamentoFinanceiroId.equals=" + lancamentoFinanceiroId);

        // Get all the documentoComercialList where lancamentoFinanceiro equals to lancamentoFinanceiroId + 1
        defaultDocumentoComercialShouldNotBeFound("lancamentoFinanceiroId.equals=" + (lancamentoFinanceiroId + 1));
    }


    @Test
    @Transactional
    public void getAllDocumentoComercialsByCompraIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);
        Compra compra = CompraResourceIT.createEntity(em);
        em.persist(compra);
        em.flush();
        documentoComercial.addCompra(compra);
        documentoComercialRepository.saveAndFlush(documentoComercial);
        Long compraId = compra.getId();

        // Get all the documentoComercialList where compra equals to compraId
        defaultDocumentoComercialShouldBeFound("compraId.equals=" + compraId);

        // Get all the documentoComercialList where compra equals to compraId + 1
        defaultDocumentoComercialShouldNotBeFound("compraId.equals=" + (compraId + 1));
    }


    @Test
    @Transactional
    public void getAllDocumentoComercialsByVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);
        Venda venda = VendaResourceIT.createEntity(em);
        em.persist(venda);
        em.flush();
        documentoComercial.addVenda(venda);
        documentoComercialRepository.saveAndFlush(documentoComercial);
        Long vendaId = venda.getId();

        // Get all the documentoComercialList where venda equals to vendaId
        defaultDocumentoComercialShouldBeFound("vendaId.equals=" + vendaId);

        // Get all the documentoComercialList where venda equals to vendaId + 1
        defaultDocumentoComercialShouldNotBeFound("vendaId.equals=" + (vendaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentoComercialShouldBeFound(String filter) throws Exception {
        restDocumentoComercialMockMvc.perform(get("/api/documento-comercials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentoComercial.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].padrao").value(hasItem(DEFAULT_PADRAO.booleanValue())))
            .andExpect(jsonPath("$.[*].movimentaEstoque").value(hasItem(DEFAULT_MOVIMENTA_ESTOQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].movimentaCaixa").value(hasItem(DEFAULT_MOVIMENTA_CAIXA.booleanValue())))
            .andExpect(jsonPath("$.[*].movimentaContabilidade").value(hasItem(DEFAULT_MOVIMENTA_CONTABILIDADE.booleanValue())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].mostraPontoVenda").value(hasItem(DEFAULT_MOSTRA_PONTO_VENDA.booleanValue())))
            .andExpect(jsonPath("$.[*].entidade").value(hasItem(DEFAULT_ENTIDADE.toString())));

        // Check, that the count call also returns 1
        restDocumentoComercialMockMvc.perform(get("/api/documento-comercials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentoComercialShouldNotBeFound(String filter) throws Exception {
        restDocumentoComercialMockMvc.perform(get("/api/documento-comercials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentoComercialMockMvc.perform(get("/api/documento-comercials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDocumentoComercial() throws Exception {
        // Get the documentoComercial
        restDocumentoComercialMockMvc.perform(get("/api/documento-comercials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentoComercial() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        int databaseSizeBeforeUpdate = documentoComercialRepository.findAll().size();

        // Update the documentoComercial
        DocumentoComercial updatedDocumentoComercial = documentoComercialRepository.findById(documentoComercial.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentoComercial are not directly saved in db
        em.detach(updatedDocumentoComercial);
        updatedDocumentoComercial
            .nome(UPDATED_NOME)
            .serie(UPDATED_SERIE)
            .padrao(UPDATED_PADRAO)
            .movimentaEstoque(UPDATED_MOVIMENTA_ESTOQUE)
            .movimentaCaixa(UPDATED_MOVIMENTA_CAIXA)
            .movimentaContabilidade(UPDATED_MOVIMENTA_CONTABILIDADE)
            .cor(UPDATED_COR)
            .descricao(UPDATED_DESCRICAO)
            .mostraPontoVenda(UPDATED_MOSTRA_PONTO_VENDA)
            .entidade(UPDATED_ENTIDADE);
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(updatedDocumentoComercial);

        restDocumentoComercialMockMvc.perform(put("/api/documento-comercials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeUpdate);
        DocumentoComercial testDocumentoComercial = documentoComercialList.get(documentoComercialList.size() - 1);
        assertThat(testDocumentoComercial.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDocumentoComercial.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testDocumentoComercial.isPadrao()).isEqualTo(UPDATED_PADRAO);
        assertThat(testDocumentoComercial.isMovimentaEstoque()).isEqualTo(UPDATED_MOVIMENTA_ESTOQUE);
        assertThat(testDocumentoComercial.isMovimentaCaixa()).isEqualTo(UPDATED_MOVIMENTA_CAIXA);
        assertThat(testDocumentoComercial.isMovimentaContabilidade()).isEqualTo(UPDATED_MOVIMENTA_CONTABILIDADE);
        assertThat(testDocumentoComercial.getCor()).isEqualTo(UPDATED_COR);
        assertThat(testDocumentoComercial.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testDocumentoComercial.isMostraPontoVenda()).isEqualTo(UPDATED_MOSTRA_PONTO_VENDA);
        assertThat(testDocumentoComercial.getEntidade()).isEqualTo(UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentoComercial() throws Exception {
        int databaseSizeBeforeUpdate = documentoComercialRepository.findAll().size();

        // Create the DocumentoComercial
        DocumentoComercialDTO documentoComercialDTO = documentoComercialMapper.toDto(documentoComercial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoComercialMockMvc.perform(put("/api/documento-comercials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentoComercialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentoComercial in the database
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentoComercial() throws Exception {
        // Initialize the database
        documentoComercialRepository.saveAndFlush(documentoComercial);

        int databaseSizeBeforeDelete = documentoComercialRepository.findAll().size();

        // Delete the documentoComercial
        restDocumentoComercialMockMvc.perform(delete("/api/documento-comercials/{id}", documentoComercial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentoComercial> documentoComercialList = documentoComercialRepository.findAll();
        assertThat(documentoComercialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
