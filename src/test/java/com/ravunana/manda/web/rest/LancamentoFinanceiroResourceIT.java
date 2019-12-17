package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.LancamentoFinanceiro;
import com.ravunana.manda.domain.DetalheLancamento;
import com.ravunana.manda.domain.User;
import com.ravunana.manda.domain.Imposto;
import com.ravunana.manda.domain.FormaLiquidacao;
import com.ravunana.manda.domain.Empresa;
import com.ravunana.manda.domain.DocumentoComercial;
import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.repository.LancamentoFinanceiroRepository;
import com.ravunana.manda.service.LancamentoFinanceiroService;
import com.ravunana.manda.service.dto.LancamentoFinanceiroDTO;
import com.ravunana.manda.service.mapper.LancamentoFinanceiroMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.LancamentoFinanceiroCriteria;
import com.ravunana.manda.service.LancamentoFinanceiroQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ravunana.manda.domain.enumeration.EntidadeSistema;
/**
 * Integration tests for the {@link LancamentoFinanceiroResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class LancamentoFinanceiroResourceIT {

    private static final String DEFAULT_TIPO_LANCAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_LANCAMENTO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(1);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(0 - 1);

    private static final Boolean DEFAULT_EXTERNO = false;
    private static final Boolean UPDATED_EXTERNO = true;

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final EntidadeSistema DEFAULT_ENTIDADE_DOCUMENTO = EntidadeSistema.SOFTWARE;
    private static final EntidadeSistema UPDATED_ENTIDADE_DOCUMENTO = EntidadeSistema.LICENCA_SOFTWARE;

    private static final String DEFAULT_NUMERO_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DOCUMENTO = "BBBBBBBBBB";

    @Autowired
    private LancamentoFinanceiroRepository lancamentoFinanceiroRepository;

    @Mock
    private LancamentoFinanceiroRepository lancamentoFinanceiroRepositoryMock;

    @Autowired
    private LancamentoFinanceiroMapper lancamentoFinanceiroMapper;

    @Mock
    private LancamentoFinanceiroService lancamentoFinanceiroServiceMock;

    @Autowired
    private LancamentoFinanceiroService lancamentoFinanceiroService;

    @Autowired
    private LancamentoFinanceiroQueryService lancamentoFinanceiroQueryService;

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

    private MockMvc restLancamentoFinanceiroMockMvc;

    private LancamentoFinanceiro lancamentoFinanceiro;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LancamentoFinanceiroResource lancamentoFinanceiroResource = new LancamentoFinanceiroResource(lancamentoFinanceiroService, lancamentoFinanceiroQueryService);
        this.restLancamentoFinanceiroMockMvc = MockMvcBuilders.standaloneSetup(lancamentoFinanceiroResource)
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
    public static LancamentoFinanceiro createEntity(EntityManager em) {
        LancamentoFinanceiro lancamentoFinanceiro = new LancamentoFinanceiro()
            .tipoLancamento(DEFAULT_TIPO_LANCAMENTO)
            .valor(DEFAULT_VALOR)
            .externo(DEFAULT_EXTERNO)
            .numero(DEFAULT_NUMERO)
            .descricao(DEFAULT_DESCRICAO)
            .entidadeDocumento(DEFAULT_ENTIDADE_DOCUMENTO)
            .numeroDocumento(DEFAULT_NUMERO_DOCUMENTO);
        // Add required entity
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            imposto = ImpostoResourceIT.createEntity(em);
            em.persist(imposto);
            em.flush();
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        lancamentoFinanceiro.getImpostos().add(imposto);
        // Add required entity
        FormaLiquidacao formaLiquidacao;
        if (TestUtil.findAll(em, FormaLiquidacao.class).isEmpty()) {
            formaLiquidacao = FormaLiquidacaoResourceIT.createEntity(em);
            em.persist(formaLiquidacao);
            em.flush();
        } else {
            formaLiquidacao = TestUtil.findAll(em, FormaLiquidacao.class).get(0);
        }
        lancamentoFinanceiro.setFormaLiquidacao(formaLiquidacao);
        // Add required entity
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercial = DocumentoComercialResourceIT.createEntity(em);
            em.persist(documentoComercial);
            em.flush();
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        lancamentoFinanceiro.setTipoRecibo(documentoComercial);
        return lancamentoFinanceiro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LancamentoFinanceiro createUpdatedEntity(EntityManager em) {
        LancamentoFinanceiro lancamentoFinanceiro = new LancamentoFinanceiro()
            .tipoLancamento(UPDATED_TIPO_LANCAMENTO)
            .valor(UPDATED_VALOR)
            .externo(UPDATED_EXTERNO)
            .numero(UPDATED_NUMERO)
            .descricao(UPDATED_DESCRICAO)
            .entidadeDocumento(UPDATED_ENTIDADE_DOCUMENTO)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO);
        // Add required entity
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            imposto = ImpostoResourceIT.createUpdatedEntity(em);
            em.persist(imposto);
            em.flush();
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        lancamentoFinanceiro.getImpostos().add(imposto);
        // Add required entity
        FormaLiquidacao formaLiquidacao;
        if (TestUtil.findAll(em, FormaLiquidacao.class).isEmpty()) {
            formaLiquidacao = FormaLiquidacaoResourceIT.createUpdatedEntity(em);
            em.persist(formaLiquidacao);
            em.flush();
        } else {
            formaLiquidacao = TestUtil.findAll(em, FormaLiquidacao.class).get(0);
        }
        lancamentoFinanceiro.setFormaLiquidacao(formaLiquidacao);
        // Add required entity
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercial = DocumentoComercialResourceIT.createUpdatedEntity(em);
            em.persist(documentoComercial);
            em.flush();
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        lancamentoFinanceiro.setTipoRecibo(documentoComercial);
        return lancamentoFinanceiro;
    }

    @BeforeEach
    public void initTest() {
        lancamentoFinanceiro = createEntity(em);
    }

    @Test
    @Transactional
    public void createLancamentoFinanceiro() throws Exception {
        int databaseSizeBeforeCreate = lancamentoFinanceiroRepository.findAll().size();

        // Create the LancamentoFinanceiro
        LancamentoFinanceiroDTO lancamentoFinanceiroDTO = lancamentoFinanceiroMapper.toDto(lancamentoFinanceiro);
        restLancamentoFinanceiroMockMvc.perform(post("/api/lancamento-financeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoFinanceiroDTO)))
            .andExpect(status().isCreated());

        // Validate the LancamentoFinanceiro in the database
        List<LancamentoFinanceiro> lancamentoFinanceiroList = lancamentoFinanceiroRepository.findAll();
        assertThat(lancamentoFinanceiroList).hasSize(databaseSizeBeforeCreate + 1);
        LancamentoFinanceiro testLancamentoFinanceiro = lancamentoFinanceiroList.get(lancamentoFinanceiroList.size() - 1);
        assertThat(testLancamentoFinanceiro.getTipoLancamento()).isEqualTo(DEFAULT_TIPO_LANCAMENTO);
        assertThat(testLancamentoFinanceiro.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testLancamentoFinanceiro.isExterno()).isEqualTo(DEFAULT_EXTERNO);
        assertThat(testLancamentoFinanceiro.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testLancamentoFinanceiro.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testLancamentoFinanceiro.getEntidadeDocumento()).isEqualTo(DEFAULT_ENTIDADE_DOCUMENTO);
        assertThat(testLancamentoFinanceiro.getNumeroDocumento()).isEqualTo(DEFAULT_NUMERO_DOCUMENTO);
    }

    @Test
    @Transactional
    public void createLancamentoFinanceiroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lancamentoFinanceiroRepository.findAll().size();

        // Create the LancamentoFinanceiro with an existing ID
        lancamentoFinanceiro.setId(1L);
        LancamentoFinanceiroDTO lancamentoFinanceiroDTO = lancamentoFinanceiroMapper.toDto(lancamentoFinanceiro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLancamentoFinanceiroMockMvc.perform(post("/api/lancamento-financeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoFinanceiroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LancamentoFinanceiro in the database
        List<LancamentoFinanceiro> lancamentoFinanceiroList = lancamentoFinanceiroRepository.findAll();
        assertThat(lancamentoFinanceiroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoLancamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoFinanceiroRepository.findAll().size();
        // set the field null
        lancamentoFinanceiro.setTipoLancamento(null);

        // Create the LancamentoFinanceiro, which fails.
        LancamentoFinanceiroDTO lancamentoFinanceiroDTO = lancamentoFinanceiroMapper.toDto(lancamentoFinanceiro);

        restLancamentoFinanceiroMockMvc.perform(post("/api/lancamento-financeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoFinanceiroDTO)))
            .andExpect(status().isBadRequest());

        List<LancamentoFinanceiro> lancamentoFinanceiroList = lancamentoFinanceiroRepository.findAll();
        assertThat(lancamentoFinanceiroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoFinanceiroRepository.findAll().size();
        // set the field null
        lancamentoFinanceiro.setValor(null);

        // Create the LancamentoFinanceiro, which fails.
        LancamentoFinanceiroDTO lancamentoFinanceiroDTO = lancamentoFinanceiroMapper.toDto(lancamentoFinanceiro);

        restLancamentoFinanceiroMockMvc.perform(post("/api/lancamento-financeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoFinanceiroDTO)))
            .andExpect(status().isBadRequest());

        List<LancamentoFinanceiro> lancamentoFinanceiroList = lancamentoFinanceiroRepository.findAll();
        assertThat(lancamentoFinanceiroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoFinanceiroRepository.findAll().size();
        // set the field null
        lancamentoFinanceiro.setNumero(null);

        // Create the LancamentoFinanceiro, which fails.
        LancamentoFinanceiroDTO lancamentoFinanceiroDTO = lancamentoFinanceiroMapper.toDto(lancamentoFinanceiro);

        restLancamentoFinanceiroMockMvc.perform(post("/api/lancamento-financeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoFinanceiroDTO)))
            .andExpect(status().isBadRequest());

        List<LancamentoFinanceiro> lancamentoFinanceiroList = lancamentoFinanceiroRepository.findAll();
        assertThat(lancamentoFinanceiroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEntidadeDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoFinanceiroRepository.findAll().size();
        // set the field null
        lancamentoFinanceiro.setEntidadeDocumento(null);

        // Create the LancamentoFinanceiro, which fails.
        LancamentoFinanceiroDTO lancamentoFinanceiroDTO = lancamentoFinanceiroMapper.toDto(lancamentoFinanceiro);

        restLancamentoFinanceiroMockMvc.perform(post("/api/lancamento-financeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoFinanceiroDTO)))
            .andExpect(status().isBadRequest());

        List<LancamentoFinanceiro> lancamentoFinanceiroList = lancamentoFinanceiroRepository.findAll();
        assertThat(lancamentoFinanceiroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = lancamentoFinanceiroRepository.findAll().size();
        // set the field null
        lancamentoFinanceiro.setNumeroDocumento(null);

        // Create the LancamentoFinanceiro, which fails.
        LancamentoFinanceiroDTO lancamentoFinanceiroDTO = lancamentoFinanceiroMapper.toDto(lancamentoFinanceiro);

        restLancamentoFinanceiroMockMvc.perform(post("/api/lancamento-financeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoFinanceiroDTO)))
            .andExpect(status().isBadRequest());

        List<LancamentoFinanceiro> lancamentoFinanceiroList = lancamentoFinanceiroRepository.findAll();
        assertThat(lancamentoFinanceiroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceiros() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList
        restLancamentoFinanceiroMockMvc.perform(get("/api/lancamento-financeiros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lancamentoFinanceiro.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoLancamento").value(hasItem(DEFAULT_TIPO_LANCAMENTO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].externo").value(hasItem(DEFAULT_EXTERNO.booleanValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].entidadeDocumento").value(hasItem(DEFAULT_ENTIDADE_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllLancamentoFinanceirosWithEagerRelationshipsIsEnabled() throws Exception {
        LancamentoFinanceiroResource lancamentoFinanceiroResource = new LancamentoFinanceiroResource(lancamentoFinanceiroServiceMock, lancamentoFinanceiroQueryService);
        when(lancamentoFinanceiroServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restLancamentoFinanceiroMockMvc = MockMvcBuilders.standaloneSetup(lancamentoFinanceiroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restLancamentoFinanceiroMockMvc.perform(get("/api/lancamento-financeiros?eagerload=true"))
        .andExpect(status().isOk());

        verify(lancamentoFinanceiroServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllLancamentoFinanceirosWithEagerRelationshipsIsNotEnabled() throws Exception {
        LancamentoFinanceiroResource lancamentoFinanceiroResource = new LancamentoFinanceiroResource(lancamentoFinanceiroServiceMock, lancamentoFinanceiroQueryService);
            when(lancamentoFinanceiroServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restLancamentoFinanceiroMockMvc = MockMvcBuilders.standaloneSetup(lancamentoFinanceiroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restLancamentoFinanceiroMockMvc.perform(get("/api/lancamento-financeiros?eagerload=true"))
        .andExpect(status().isOk());

            verify(lancamentoFinanceiroServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getLancamentoFinanceiro() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get the lancamentoFinanceiro
        restLancamentoFinanceiroMockMvc.perform(get("/api/lancamento-financeiros/{id}", lancamentoFinanceiro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lancamentoFinanceiro.getId().intValue()))
            .andExpect(jsonPath("$.tipoLancamento").value(DEFAULT_TIPO_LANCAMENTO))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.externo").value(DEFAULT_EXTERNO.booleanValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.entidadeDocumento").value(DEFAULT_ENTIDADE_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.numeroDocumento").value(DEFAULT_NUMERO_DOCUMENTO));
    }


    @Test
    @Transactional
    public void getLancamentoFinanceirosByIdFiltering() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        Long id = lancamentoFinanceiro.getId();

        defaultLancamentoFinanceiroShouldBeFound("id.equals=" + id);
        defaultLancamentoFinanceiroShouldNotBeFound("id.notEquals=" + id);

        defaultLancamentoFinanceiroShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLancamentoFinanceiroShouldNotBeFound("id.greaterThan=" + id);

        defaultLancamentoFinanceiroShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLancamentoFinanceiroShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByTipoLancamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where tipoLancamento equals to DEFAULT_TIPO_LANCAMENTO
        defaultLancamentoFinanceiroShouldBeFound("tipoLancamento.equals=" + DEFAULT_TIPO_LANCAMENTO);

        // Get all the lancamentoFinanceiroList where tipoLancamento equals to UPDATED_TIPO_LANCAMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("tipoLancamento.equals=" + UPDATED_TIPO_LANCAMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByTipoLancamentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where tipoLancamento not equals to DEFAULT_TIPO_LANCAMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("tipoLancamento.notEquals=" + DEFAULT_TIPO_LANCAMENTO);

        // Get all the lancamentoFinanceiroList where tipoLancamento not equals to UPDATED_TIPO_LANCAMENTO
        defaultLancamentoFinanceiroShouldBeFound("tipoLancamento.notEquals=" + UPDATED_TIPO_LANCAMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByTipoLancamentoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where tipoLancamento in DEFAULT_TIPO_LANCAMENTO or UPDATED_TIPO_LANCAMENTO
        defaultLancamentoFinanceiroShouldBeFound("tipoLancamento.in=" + DEFAULT_TIPO_LANCAMENTO + "," + UPDATED_TIPO_LANCAMENTO);

        // Get all the lancamentoFinanceiroList where tipoLancamento equals to UPDATED_TIPO_LANCAMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("tipoLancamento.in=" + UPDATED_TIPO_LANCAMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByTipoLancamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where tipoLancamento is not null
        defaultLancamentoFinanceiroShouldBeFound("tipoLancamento.specified=true");

        // Get all the lancamentoFinanceiroList where tipoLancamento is null
        defaultLancamentoFinanceiroShouldNotBeFound("tipoLancamento.specified=false");
    }
                @Test
    @Transactional
    public void getAllLancamentoFinanceirosByTipoLancamentoContainsSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where tipoLancamento contains DEFAULT_TIPO_LANCAMENTO
        defaultLancamentoFinanceiroShouldBeFound("tipoLancamento.contains=" + DEFAULT_TIPO_LANCAMENTO);

        // Get all the lancamentoFinanceiroList where tipoLancamento contains UPDATED_TIPO_LANCAMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("tipoLancamento.contains=" + UPDATED_TIPO_LANCAMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByTipoLancamentoNotContainsSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where tipoLancamento does not contain DEFAULT_TIPO_LANCAMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("tipoLancamento.doesNotContain=" + DEFAULT_TIPO_LANCAMENTO);

        // Get all the lancamentoFinanceiroList where tipoLancamento does not contain UPDATED_TIPO_LANCAMENTO
        defaultLancamentoFinanceiroShouldBeFound("tipoLancamento.doesNotContain=" + UPDATED_TIPO_LANCAMENTO);
    }


    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where valor equals to DEFAULT_VALOR
        defaultLancamentoFinanceiroShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the lancamentoFinanceiroList where valor equals to UPDATED_VALOR
        defaultLancamentoFinanceiroShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where valor not equals to DEFAULT_VALOR
        defaultLancamentoFinanceiroShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the lancamentoFinanceiroList where valor not equals to UPDATED_VALOR
        defaultLancamentoFinanceiroShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultLancamentoFinanceiroShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the lancamentoFinanceiroList where valor equals to UPDATED_VALOR
        defaultLancamentoFinanceiroShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where valor is not null
        defaultLancamentoFinanceiroShouldBeFound("valor.specified=true");

        // Get all the lancamentoFinanceiroList where valor is null
        defaultLancamentoFinanceiroShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where valor is greater than or equal to DEFAULT_VALOR
        defaultLancamentoFinanceiroShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the lancamentoFinanceiroList where valor is greater than or equal to UPDATED_VALOR
        defaultLancamentoFinanceiroShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where valor is less than or equal to DEFAULT_VALOR
        defaultLancamentoFinanceiroShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the lancamentoFinanceiroList where valor is less than or equal to SMALLER_VALOR
        defaultLancamentoFinanceiroShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where valor is less than DEFAULT_VALOR
        defaultLancamentoFinanceiroShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the lancamentoFinanceiroList where valor is less than UPDATED_VALOR
        defaultLancamentoFinanceiroShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where valor is greater than DEFAULT_VALOR
        defaultLancamentoFinanceiroShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the lancamentoFinanceiroList where valor is greater than SMALLER_VALOR
        defaultLancamentoFinanceiroShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where externo equals to DEFAULT_EXTERNO
        defaultLancamentoFinanceiroShouldBeFound("externo.equals=" + DEFAULT_EXTERNO);

        // Get all the lancamentoFinanceiroList where externo equals to UPDATED_EXTERNO
        defaultLancamentoFinanceiroShouldNotBeFound("externo.equals=" + UPDATED_EXTERNO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByExternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where externo not equals to DEFAULT_EXTERNO
        defaultLancamentoFinanceiroShouldNotBeFound("externo.notEquals=" + DEFAULT_EXTERNO);

        // Get all the lancamentoFinanceiroList where externo not equals to UPDATED_EXTERNO
        defaultLancamentoFinanceiroShouldBeFound("externo.notEquals=" + UPDATED_EXTERNO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByExternoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where externo in DEFAULT_EXTERNO or UPDATED_EXTERNO
        defaultLancamentoFinanceiroShouldBeFound("externo.in=" + DEFAULT_EXTERNO + "," + UPDATED_EXTERNO);

        // Get all the lancamentoFinanceiroList where externo equals to UPDATED_EXTERNO
        defaultLancamentoFinanceiroShouldNotBeFound("externo.in=" + UPDATED_EXTERNO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByExternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where externo is not null
        defaultLancamentoFinanceiroShouldBeFound("externo.specified=true");

        // Get all the lancamentoFinanceiroList where externo is null
        defaultLancamentoFinanceiroShouldNotBeFound("externo.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where numero equals to DEFAULT_NUMERO
        defaultLancamentoFinanceiroShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the lancamentoFinanceiroList where numero equals to UPDATED_NUMERO
        defaultLancamentoFinanceiroShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where numero not equals to DEFAULT_NUMERO
        defaultLancamentoFinanceiroShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the lancamentoFinanceiroList where numero not equals to UPDATED_NUMERO
        defaultLancamentoFinanceiroShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultLancamentoFinanceiroShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the lancamentoFinanceiroList where numero equals to UPDATED_NUMERO
        defaultLancamentoFinanceiroShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where numero is not null
        defaultLancamentoFinanceiroShouldBeFound("numero.specified=true");

        // Get all the lancamentoFinanceiroList where numero is null
        defaultLancamentoFinanceiroShouldNotBeFound("numero.specified=false");
    }
                @Test
    @Transactional
    public void getAllLancamentoFinanceirosByNumeroContainsSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where numero contains DEFAULT_NUMERO
        defaultLancamentoFinanceiroShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the lancamentoFinanceiroList where numero contains UPDATED_NUMERO
        defaultLancamentoFinanceiroShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where numero does not contain DEFAULT_NUMERO
        defaultLancamentoFinanceiroShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the lancamentoFinanceiroList where numero does not contain UPDATED_NUMERO
        defaultLancamentoFinanceiroShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }


    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByEntidadeDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where entidadeDocumento equals to DEFAULT_ENTIDADE_DOCUMENTO
        defaultLancamentoFinanceiroShouldBeFound("entidadeDocumento.equals=" + DEFAULT_ENTIDADE_DOCUMENTO);

        // Get all the lancamentoFinanceiroList where entidadeDocumento equals to UPDATED_ENTIDADE_DOCUMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("entidadeDocumento.equals=" + UPDATED_ENTIDADE_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByEntidadeDocumentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where entidadeDocumento not equals to DEFAULT_ENTIDADE_DOCUMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("entidadeDocumento.notEquals=" + DEFAULT_ENTIDADE_DOCUMENTO);

        // Get all the lancamentoFinanceiroList where entidadeDocumento not equals to UPDATED_ENTIDADE_DOCUMENTO
        defaultLancamentoFinanceiroShouldBeFound("entidadeDocumento.notEquals=" + UPDATED_ENTIDADE_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByEntidadeDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where entidadeDocumento in DEFAULT_ENTIDADE_DOCUMENTO or UPDATED_ENTIDADE_DOCUMENTO
        defaultLancamentoFinanceiroShouldBeFound("entidadeDocumento.in=" + DEFAULT_ENTIDADE_DOCUMENTO + "," + UPDATED_ENTIDADE_DOCUMENTO);

        // Get all the lancamentoFinanceiroList where entidadeDocumento equals to UPDATED_ENTIDADE_DOCUMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("entidadeDocumento.in=" + UPDATED_ENTIDADE_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByEntidadeDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where entidadeDocumento is not null
        defaultLancamentoFinanceiroShouldBeFound("entidadeDocumento.specified=true");

        // Get all the lancamentoFinanceiroList where entidadeDocumento is null
        defaultLancamentoFinanceiroShouldNotBeFound("entidadeDocumento.specified=false");
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByNumeroDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where numeroDocumento equals to DEFAULT_NUMERO_DOCUMENTO
        defaultLancamentoFinanceiroShouldBeFound("numeroDocumento.equals=" + DEFAULT_NUMERO_DOCUMENTO);

        // Get all the lancamentoFinanceiroList where numeroDocumento equals to UPDATED_NUMERO_DOCUMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("numeroDocumento.equals=" + UPDATED_NUMERO_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByNumeroDocumentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where numeroDocumento not equals to DEFAULT_NUMERO_DOCUMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("numeroDocumento.notEquals=" + DEFAULT_NUMERO_DOCUMENTO);

        // Get all the lancamentoFinanceiroList where numeroDocumento not equals to UPDATED_NUMERO_DOCUMENTO
        defaultLancamentoFinanceiroShouldBeFound("numeroDocumento.notEquals=" + UPDATED_NUMERO_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByNumeroDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where numeroDocumento in DEFAULT_NUMERO_DOCUMENTO or UPDATED_NUMERO_DOCUMENTO
        defaultLancamentoFinanceiroShouldBeFound("numeroDocumento.in=" + DEFAULT_NUMERO_DOCUMENTO + "," + UPDATED_NUMERO_DOCUMENTO);

        // Get all the lancamentoFinanceiroList where numeroDocumento equals to UPDATED_NUMERO_DOCUMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("numeroDocumento.in=" + UPDATED_NUMERO_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByNumeroDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where numeroDocumento is not null
        defaultLancamentoFinanceiroShouldBeFound("numeroDocumento.specified=true");

        // Get all the lancamentoFinanceiroList where numeroDocumento is null
        defaultLancamentoFinanceiroShouldNotBeFound("numeroDocumento.specified=false");
    }
                @Test
    @Transactional
    public void getAllLancamentoFinanceirosByNumeroDocumentoContainsSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where numeroDocumento contains DEFAULT_NUMERO_DOCUMENTO
        defaultLancamentoFinanceiroShouldBeFound("numeroDocumento.contains=" + DEFAULT_NUMERO_DOCUMENTO);

        // Get all the lancamentoFinanceiroList where numeroDocumento contains UPDATED_NUMERO_DOCUMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("numeroDocumento.contains=" + UPDATED_NUMERO_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByNumeroDocumentoNotContainsSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        // Get all the lancamentoFinanceiroList where numeroDocumento does not contain DEFAULT_NUMERO_DOCUMENTO
        defaultLancamentoFinanceiroShouldNotBeFound("numeroDocumento.doesNotContain=" + DEFAULT_NUMERO_DOCUMENTO);

        // Get all the lancamentoFinanceiroList where numeroDocumento does not contain UPDATED_NUMERO_DOCUMENTO
        defaultLancamentoFinanceiroShouldBeFound("numeroDocumento.doesNotContain=" + UPDATED_NUMERO_DOCUMENTO);
    }


    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByDetalheLancamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);
        DetalheLancamento detalheLancamento = DetalheLancamentoResourceIT.createEntity(em);
        em.persist(detalheLancamento);
        em.flush();
        lancamentoFinanceiro.addDetalheLancamento(detalheLancamento);
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);
        Long detalheLancamentoId = detalheLancamento.getId();

        // Get all the lancamentoFinanceiroList where detalheLancamento equals to detalheLancamentoId
        defaultLancamentoFinanceiroShouldBeFound("detalheLancamentoId.equals=" + detalheLancamentoId);

        // Get all the lancamentoFinanceiroList where detalheLancamento equals to detalheLancamentoId + 1
        defaultLancamentoFinanceiroShouldNotBeFound("detalheLancamentoId.equals=" + (detalheLancamentoId + 1));
    }


    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByUtilizadorIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);
        User utilizador = UserResourceIT.createEntity(em);
        em.persist(utilizador);
        em.flush();
        lancamentoFinanceiro.setUtilizador(utilizador);
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);
        Long utilizadorId = utilizador.getId();

        // Get all the lancamentoFinanceiroList where utilizador equals to utilizadorId
        defaultLancamentoFinanceiroShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the lancamentoFinanceiroList where utilizador equals to utilizadorId + 1
        defaultLancamentoFinanceiroShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }


    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByImpostoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Imposto imposto = lancamentoFinanceiro.getImposto();
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);
        Long impostoId = imposto.getId();

        // Get all the lancamentoFinanceiroList where imposto equals to impostoId
        defaultLancamentoFinanceiroShouldBeFound("impostoId.equals=" + impostoId);

        // Get all the lancamentoFinanceiroList where imposto equals to impostoId + 1
        defaultLancamentoFinanceiroShouldNotBeFound("impostoId.equals=" + (impostoId + 1));
    }


    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByFormaLiquidacaoIsEqualToSomething() throws Exception {
        // Get already existing entity
        FormaLiquidacao formaLiquidacao = lancamentoFinanceiro.getFormaLiquidacao();
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);
        Long formaLiquidacaoId = formaLiquidacao.getId();

        // Get all the lancamentoFinanceiroList where formaLiquidacao equals to formaLiquidacaoId
        defaultLancamentoFinanceiroShouldBeFound("formaLiquidacaoId.equals=" + formaLiquidacaoId);

        // Get all the lancamentoFinanceiroList where formaLiquidacao equals to formaLiquidacaoId + 1
        defaultLancamentoFinanceiroShouldNotBeFound("formaLiquidacaoId.equals=" + (formaLiquidacaoId + 1));
    }


    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        lancamentoFinanceiro.setEmpresa(empresa);
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);
        Long empresaId = empresa.getId();

        // Get all the lancamentoFinanceiroList where empresa equals to empresaId
        defaultLancamentoFinanceiroShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the lancamentoFinanceiroList where empresa equals to empresaId + 1
        defaultLancamentoFinanceiroShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }


    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByTipoReciboIsEqualToSomething() throws Exception {
        // Get already existing entity
        DocumentoComercial tipoRecibo = lancamentoFinanceiro.getTipoRecibo();
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);
        Long tipoReciboId = tipoRecibo.getId();

        // Get all the lancamentoFinanceiroList where tipoRecibo equals to tipoReciboId
        defaultLancamentoFinanceiroShouldBeFound("tipoReciboId.equals=" + tipoReciboId);

        // Get all the lancamentoFinanceiroList where tipoRecibo equals to tipoReciboId + 1
        defaultLancamentoFinanceiroShouldNotBeFound("tipoReciboId.equals=" + (tipoReciboId + 1));
    }


    @Test
    @Transactional
    public void getAllLancamentoFinanceirosByContaIsEqualToSomething() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);
        Conta conta = ContaResourceIT.createEntity(em);
        em.persist(conta);
        em.flush();
        lancamentoFinanceiro.setConta(conta);
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);
        Long contaId = conta.getId();

        // Get all the lancamentoFinanceiroList where conta equals to contaId
        defaultLancamentoFinanceiroShouldBeFound("contaId.equals=" + contaId);

        // Get all the lancamentoFinanceiroList where conta equals to contaId + 1
        defaultLancamentoFinanceiroShouldNotBeFound("contaId.equals=" + (contaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLancamentoFinanceiroShouldBeFound(String filter) throws Exception {
        restLancamentoFinanceiroMockMvc.perform(get("/api/lancamento-financeiros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lancamentoFinanceiro.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoLancamento").value(hasItem(DEFAULT_TIPO_LANCAMENTO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].externo").value(hasItem(DEFAULT_EXTERNO.booleanValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].entidadeDocumento").value(hasItem(DEFAULT_ENTIDADE_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].numeroDocumento").value(hasItem(DEFAULT_NUMERO_DOCUMENTO)));

        // Check, that the count call also returns 1
        restLancamentoFinanceiroMockMvc.perform(get("/api/lancamento-financeiros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLancamentoFinanceiroShouldNotBeFound(String filter) throws Exception {
        restLancamentoFinanceiroMockMvc.perform(get("/api/lancamento-financeiros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLancamentoFinanceiroMockMvc.perform(get("/api/lancamento-financeiros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLancamentoFinanceiro() throws Exception {
        // Get the lancamentoFinanceiro
        restLancamentoFinanceiroMockMvc.perform(get("/api/lancamento-financeiros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLancamentoFinanceiro() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        int databaseSizeBeforeUpdate = lancamentoFinanceiroRepository.findAll().size();

        // Update the lancamentoFinanceiro
        LancamentoFinanceiro updatedLancamentoFinanceiro = lancamentoFinanceiroRepository.findById(lancamentoFinanceiro.getId()).get();
        // Disconnect from session so that the updates on updatedLancamentoFinanceiro are not directly saved in db
        em.detach(updatedLancamentoFinanceiro);
        updatedLancamentoFinanceiro
            .tipoLancamento(UPDATED_TIPO_LANCAMENTO)
            .valor(UPDATED_VALOR)
            .externo(UPDATED_EXTERNO)
            .numero(UPDATED_NUMERO)
            .descricao(UPDATED_DESCRICAO)
            .entidadeDocumento(UPDATED_ENTIDADE_DOCUMENTO)
            .numeroDocumento(UPDATED_NUMERO_DOCUMENTO);
        LancamentoFinanceiroDTO lancamentoFinanceiroDTO = lancamentoFinanceiroMapper.toDto(updatedLancamentoFinanceiro);

        restLancamentoFinanceiroMockMvc.perform(put("/api/lancamento-financeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoFinanceiroDTO)))
            .andExpect(status().isOk());

        // Validate the LancamentoFinanceiro in the database
        List<LancamentoFinanceiro> lancamentoFinanceiroList = lancamentoFinanceiroRepository.findAll();
        assertThat(lancamentoFinanceiroList).hasSize(databaseSizeBeforeUpdate);
        LancamentoFinanceiro testLancamentoFinanceiro = lancamentoFinanceiroList.get(lancamentoFinanceiroList.size() - 1);
        assertThat(testLancamentoFinanceiro.getTipoLancamento()).isEqualTo(UPDATED_TIPO_LANCAMENTO);
        assertThat(testLancamentoFinanceiro.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testLancamentoFinanceiro.isExterno()).isEqualTo(UPDATED_EXTERNO);
        assertThat(testLancamentoFinanceiro.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testLancamentoFinanceiro.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testLancamentoFinanceiro.getEntidadeDocumento()).isEqualTo(UPDATED_ENTIDADE_DOCUMENTO);
        assertThat(testLancamentoFinanceiro.getNumeroDocumento()).isEqualTo(UPDATED_NUMERO_DOCUMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingLancamentoFinanceiro() throws Exception {
        int databaseSizeBeforeUpdate = lancamentoFinanceiroRepository.findAll().size();

        // Create the LancamentoFinanceiro
        LancamentoFinanceiroDTO lancamentoFinanceiroDTO = lancamentoFinanceiroMapper.toDto(lancamentoFinanceiro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLancamentoFinanceiroMockMvc.perform(put("/api/lancamento-financeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lancamentoFinanceiroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LancamentoFinanceiro in the database
        List<LancamentoFinanceiro> lancamentoFinanceiroList = lancamentoFinanceiroRepository.findAll();
        assertThat(lancamentoFinanceiroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLancamentoFinanceiro() throws Exception {
        // Initialize the database
        lancamentoFinanceiroRepository.saveAndFlush(lancamentoFinanceiro);

        int databaseSizeBeforeDelete = lancamentoFinanceiroRepository.findAll().size();

        // Delete the lancamentoFinanceiro
        restLancamentoFinanceiroMockMvc.perform(delete("/api/lancamento-financeiros/{id}", lancamentoFinanceiro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LancamentoFinanceiro> lancamentoFinanceiroList = lancamentoFinanceiroRepository.findAll();
        assertThat(lancamentoFinanceiroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
