package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.domain.ContaDebito;
import com.ravunana.manda.domain.ContaCredito;
import com.ravunana.manda.domain.Empresa;
import com.ravunana.manda.domain.ClasseConta;
import com.ravunana.manda.domain.LancamentoFinanceiro;
import com.ravunana.manda.repository.ContaRepository;
import com.ravunana.manda.service.ContaService;
import com.ravunana.manda.service.dto.ContaDTO;
import com.ravunana.manda.service.mapper.ContaMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.ContaCriteria;
import com.ravunana.manda.service.ContaQueryService;

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
import java.util.ArrayList;
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ContaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final Integer DEFAULT_GRAU = 1;
    private static final Integer UPDATED_GRAU = 2;
    private static final Integer SMALLER_GRAU = 1 - 1;

    private static final String DEFAULT_NATUREZA = "AAAAAAAAAA";
    private static final String UPDATED_NATUREZA = "BBBBBBBBBB";

    private static final String DEFAULT_GRUPO = "AAAAAAAAAA";
    private static final String UPDATED_GRUPO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEUDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTEUDO = "BBBBBBBBBB";

    @Autowired
    private ContaRepository contaRepository;

    @Mock
    private ContaRepository contaRepositoryMock;

    @Autowired
    private ContaMapper contaMapper;

    @Mock
    private ContaService contaServiceMock;

    @Autowired
    private ContaService contaService;

    @Autowired
    private ContaQueryService contaQueryService;

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

    private MockMvc restContaMockMvc;

    private Conta conta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContaResource contaResource = new ContaResource(contaService, contaQueryService);
        this.restContaMockMvc = MockMvcBuilders.standaloneSetup(contaResource)
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
    public static Conta createEntity(EntityManager em) {
        Conta conta = new Conta()
            .descricao(DEFAULT_DESCRICAO)
            .codigo(DEFAULT_CODIGO)
            .tipo(DEFAULT_TIPO)
            .grau(DEFAULT_GRAU)
            .natureza(DEFAULT_NATUREZA)
            .grupo(DEFAULT_GRUPO)
            .conteudo(DEFAULT_CONTEUDO);
        // Add required entity
        ClasseConta classeConta;
        if (TestUtil.findAll(em, ClasseConta.class).isEmpty()) {
            classeConta = ClasseContaResourceIT.createEntity(em);
            em.persist(classeConta);
            em.flush();
        } else {
            classeConta = TestUtil.findAll(em, ClasseConta.class).get(0);
        }
        conta.setClasseConta(classeConta);
        return conta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conta createUpdatedEntity(EntityManager em) {
        Conta conta = new Conta()
            .descricao(UPDATED_DESCRICAO)
            .codigo(UPDATED_CODIGO)
            .tipo(UPDATED_TIPO)
            .grau(UPDATED_GRAU)
            .natureza(UPDATED_NATUREZA)
            .grupo(UPDATED_GRUPO)
            .conteudo(UPDATED_CONTEUDO);
        // Add required entity
        ClasseConta classeConta;
        if (TestUtil.findAll(em, ClasseConta.class).isEmpty()) {
            classeConta = ClasseContaResourceIT.createUpdatedEntity(em);
            em.persist(classeConta);
            em.flush();
        } else {
            classeConta = TestUtil.findAll(em, ClasseConta.class).get(0);
        }
        conta.setClasseConta(classeConta);
        return conta;
    }

    @BeforeEach
    public void initTest() {
        conta = createEntity(em);
    }

    @Test
    @Transactional
    public void createConta() throws Exception {
        int databaseSizeBeforeCreate = contaRepository.findAll().size();

        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);
        restContaMockMvc.perform(post("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isCreated());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeCreate + 1);
        Conta testConta = contaList.get(contaList.size() - 1);
        assertThat(testConta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testConta.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testConta.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testConta.getGrau()).isEqualTo(DEFAULT_GRAU);
        assertThat(testConta.getNatureza()).isEqualTo(DEFAULT_NATUREZA);
        assertThat(testConta.getGrupo()).isEqualTo(DEFAULT_GRUPO);
        assertThat(testConta.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
    }

    @Test
    @Transactional
    public void createContaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contaRepository.findAll().size();

        // Create the Conta with an existing ID
        conta.setId(1L);
        ContaDTO contaDTO = contaMapper.toDto(conta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContaMockMvc.perform(post("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaRepository.findAll().size();
        // set the field null
        conta.setDescricao(null);

        // Create the Conta, which fails.
        ContaDTO contaDTO = contaMapper.toDto(conta);

        restContaMockMvc.perform(post("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaRepository.findAll().size();
        // set the field null
        conta.setCodigo(null);

        // Create the Conta, which fails.
        ContaDTO contaDTO = contaMapper.toDto(conta);

        restContaMockMvc.perform(post("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGrauIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaRepository.findAll().size();
        // set the field null
        conta.setGrau(null);

        // Create the Conta, which fails.
        ContaDTO contaDTO = contaMapper.toDto(conta);

        restContaMockMvc.perform(post("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContas() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList
        restContaMockMvc.perform(get("/api/contas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].grau").value(hasItem(DEFAULT_GRAU)))
            .andExpect(jsonPath("$.[*].natureza").value(hasItem(DEFAULT_NATUREZA)))
            .andExpect(jsonPath("$.[*].grupo").value(hasItem(DEFAULT_GRUPO)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllContasWithEagerRelationshipsIsEnabled() throws Exception {
        ContaResource contaResource = new ContaResource(contaServiceMock, contaQueryService);
        when(contaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restContaMockMvc = MockMvcBuilders.standaloneSetup(contaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restContaMockMvc.perform(get("/api/contas?eagerload=true"))
        .andExpect(status().isOk());

        verify(contaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllContasWithEagerRelationshipsIsNotEnabled() throws Exception {
        ContaResource contaResource = new ContaResource(contaServiceMock, contaQueryService);
            when(contaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restContaMockMvc = MockMvcBuilders.standaloneSetup(contaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restContaMockMvc.perform(get("/api/contas?eagerload=true"))
        .andExpect(status().isOk());

            verify(contaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getConta() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get the conta
        restContaMockMvc.perform(get("/api/contas/{id}", conta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conta.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.grau").value(DEFAULT_GRAU))
            .andExpect(jsonPath("$.natureza").value(DEFAULT_NATUREZA))
            .andExpect(jsonPath("$.grupo").value(DEFAULT_GRUPO))
            .andExpect(jsonPath("$.conteudo").value(DEFAULT_CONTEUDO.toString()));
    }


    @Test
    @Transactional
    public void getContasByIdFiltering() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        Long id = conta.getId();

        defaultContaShouldBeFound("id.equals=" + id);
        defaultContaShouldNotBeFound("id.notEquals=" + id);

        defaultContaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContaShouldNotBeFound("id.greaterThan=" + id);

        defaultContaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where descricao equals to DEFAULT_DESCRICAO
        defaultContaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the contaList where descricao equals to UPDATED_DESCRICAO
        defaultContaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where descricao not equals to DEFAULT_DESCRICAO
        defaultContaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the contaList where descricao not equals to UPDATED_DESCRICAO
        defaultContaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultContaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the contaList where descricao equals to UPDATED_DESCRICAO
        defaultContaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where descricao is not null
        defaultContaShouldBeFound("descricao.specified=true");

        // Get all the contaList where descricao is null
        defaultContaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllContasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where descricao contains DEFAULT_DESCRICAO
        defaultContaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the contaList where descricao contains UPDATED_DESCRICAO
        defaultContaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllContasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where descricao does not contain DEFAULT_DESCRICAO
        defaultContaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the contaList where descricao does not contain UPDATED_DESCRICAO
        defaultContaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllContasByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where codigo equals to DEFAULT_CODIGO
        defaultContaShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the contaList where codigo equals to UPDATED_CODIGO
        defaultContaShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllContasByCodigoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where codigo not equals to DEFAULT_CODIGO
        defaultContaShouldNotBeFound("codigo.notEquals=" + DEFAULT_CODIGO);

        // Get all the contaList where codigo not equals to UPDATED_CODIGO
        defaultContaShouldBeFound("codigo.notEquals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllContasByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultContaShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the contaList where codigo equals to UPDATED_CODIGO
        defaultContaShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllContasByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where codigo is not null
        defaultContaShouldBeFound("codigo.specified=true");

        // Get all the contaList where codigo is null
        defaultContaShouldNotBeFound("codigo.specified=false");
    }
                @Test
    @Transactional
    public void getAllContasByCodigoContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where codigo contains DEFAULT_CODIGO
        defaultContaShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the contaList where codigo contains UPDATED_CODIGO
        defaultContaShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllContasByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where codigo does not contain DEFAULT_CODIGO
        defaultContaShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the contaList where codigo does not contain UPDATED_CODIGO
        defaultContaShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }


    @Test
    @Transactional
    public void getAllContasByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where tipo equals to DEFAULT_TIPO
        defaultContaShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the contaList where tipo equals to UPDATED_TIPO
        defaultContaShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllContasByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where tipo not equals to DEFAULT_TIPO
        defaultContaShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the contaList where tipo not equals to UPDATED_TIPO
        defaultContaShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllContasByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultContaShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the contaList where tipo equals to UPDATED_TIPO
        defaultContaShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllContasByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where tipo is not null
        defaultContaShouldBeFound("tipo.specified=true");

        // Get all the contaList where tipo is null
        defaultContaShouldNotBeFound("tipo.specified=false");
    }
                @Test
    @Transactional
    public void getAllContasByTipoContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where tipo contains DEFAULT_TIPO
        defaultContaShouldBeFound("tipo.contains=" + DEFAULT_TIPO);

        // Get all the contaList where tipo contains UPDATED_TIPO
        defaultContaShouldNotBeFound("tipo.contains=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllContasByTipoNotContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where tipo does not contain DEFAULT_TIPO
        defaultContaShouldNotBeFound("tipo.doesNotContain=" + DEFAULT_TIPO);

        // Get all the contaList where tipo does not contain UPDATED_TIPO
        defaultContaShouldBeFound("tipo.doesNotContain=" + UPDATED_TIPO);
    }


    @Test
    @Transactional
    public void getAllContasByGrauIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grau equals to DEFAULT_GRAU
        defaultContaShouldBeFound("grau.equals=" + DEFAULT_GRAU);

        // Get all the contaList where grau equals to UPDATED_GRAU
        defaultContaShouldNotBeFound("grau.equals=" + UPDATED_GRAU);
    }

    @Test
    @Transactional
    public void getAllContasByGrauIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grau not equals to DEFAULT_GRAU
        defaultContaShouldNotBeFound("grau.notEquals=" + DEFAULT_GRAU);

        // Get all the contaList where grau not equals to UPDATED_GRAU
        defaultContaShouldBeFound("grau.notEquals=" + UPDATED_GRAU);
    }

    @Test
    @Transactional
    public void getAllContasByGrauIsInShouldWork() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grau in DEFAULT_GRAU or UPDATED_GRAU
        defaultContaShouldBeFound("grau.in=" + DEFAULT_GRAU + "," + UPDATED_GRAU);

        // Get all the contaList where grau equals to UPDATED_GRAU
        defaultContaShouldNotBeFound("grau.in=" + UPDATED_GRAU);
    }

    @Test
    @Transactional
    public void getAllContasByGrauIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grau is not null
        defaultContaShouldBeFound("grau.specified=true");

        // Get all the contaList where grau is null
        defaultContaShouldNotBeFound("grau.specified=false");
    }

    @Test
    @Transactional
    public void getAllContasByGrauIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grau is greater than or equal to DEFAULT_GRAU
        defaultContaShouldBeFound("grau.greaterThanOrEqual=" + DEFAULT_GRAU);

        // Get all the contaList where grau is greater than or equal to UPDATED_GRAU
        defaultContaShouldNotBeFound("grau.greaterThanOrEqual=" + UPDATED_GRAU);
    }

    @Test
    @Transactional
    public void getAllContasByGrauIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grau is less than or equal to DEFAULT_GRAU
        defaultContaShouldBeFound("grau.lessThanOrEqual=" + DEFAULT_GRAU);

        // Get all the contaList where grau is less than or equal to SMALLER_GRAU
        defaultContaShouldNotBeFound("grau.lessThanOrEqual=" + SMALLER_GRAU);
    }

    @Test
    @Transactional
    public void getAllContasByGrauIsLessThanSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grau is less than DEFAULT_GRAU
        defaultContaShouldNotBeFound("grau.lessThan=" + DEFAULT_GRAU);

        // Get all the contaList where grau is less than UPDATED_GRAU
        defaultContaShouldBeFound("grau.lessThan=" + UPDATED_GRAU);
    }

    @Test
    @Transactional
    public void getAllContasByGrauIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grau is greater than DEFAULT_GRAU
        defaultContaShouldNotBeFound("grau.greaterThan=" + DEFAULT_GRAU);

        // Get all the contaList where grau is greater than SMALLER_GRAU
        defaultContaShouldBeFound("grau.greaterThan=" + SMALLER_GRAU);
    }


    @Test
    @Transactional
    public void getAllContasByNaturezaIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where natureza equals to DEFAULT_NATUREZA
        defaultContaShouldBeFound("natureza.equals=" + DEFAULT_NATUREZA);

        // Get all the contaList where natureza equals to UPDATED_NATUREZA
        defaultContaShouldNotBeFound("natureza.equals=" + UPDATED_NATUREZA);
    }

    @Test
    @Transactional
    public void getAllContasByNaturezaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where natureza not equals to DEFAULT_NATUREZA
        defaultContaShouldNotBeFound("natureza.notEquals=" + DEFAULT_NATUREZA);

        // Get all the contaList where natureza not equals to UPDATED_NATUREZA
        defaultContaShouldBeFound("natureza.notEquals=" + UPDATED_NATUREZA);
    }

    @Test
    @Transactional
    public void getAllContasByNaturezaIsInShouldWork() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where natureza in DEFAULT_NATUREZA or UPDATED_NATUREZA
        defaultContaShouldBeFound("natureza.in=" + DEFAULT_NATUREZA + "," + UPDATED_NATUREZA);

        // Get all the contaList where natureza equals to UPDATED_NATUREZA
        defaultContaShouldNotBeFound("natureza.in=" + UPDATED_NATUREZA);
    }

    @Test
    @Transactional
    public void getAllContasByNaturezaIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where natureza is not null
        defaultContaShouldBeFound("natureza.specified=true");

        // Get all the contaList where natureza is null
        defaultContaShouldNotBeFound("natureza.specified=false");
    }
                @Test
    @Transactional
    public void getAllContasByNaturezaContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where natureza contains DEFAULT_NATUREZA
        defaultContaShouldBeFound("natureza.contains=" + DEFAULT_NATUREZA);

        // Get all the contaList where natureza contains UPDATED_NATUREZA
        defaultContaShouldNotBeFound("natureza.contains=" + UPDATED_NATUREZA);
    }

    @Test
    @Transactional
    public void getAllContasByNaturezaNotContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where natureza does not contain DEFAULT_NATUREZA
        defaultContaShouldNotBeFound("natureza.doesNotContain=" + DEFAULT_NATUREZA);

        // Get all the contaList where natureza does not contain UPDATED_NATUREZA
        defaultContaShouldBeFound("natureza.doesNotContain=" + UPDATED_NATUREZA);
    }


    @Test
    @Transactional
    public void getAllContasByGrupoIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grupo equals to DEFAULT_GRUPO
        defaultContaShouldBeFound("grupo.equals=" + DEFAULT_GRUPO);

        // Get all the contaList where grupo equals to UPDATED_GRUPO
        defaultContaShouldNotBeFound("grupo.equals=" + UPDATED_GRUPO);
    }

    @Test
    @Transactional
    public void getAllContasByGrupoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grupo not equals to DEFAULT_GRUPO
        defaultContaShouldNotBeFound("grupo.notEquals=" + DEFAULT_GRUPO);

        // Get all the contaList where grupo not equals to UPDATED_GRUPO
        defaultContaShouldBeFound("grupo.notEquals=" + UPDATED_GRUPO);
    }

    @Test
    @Transactional
    public void getAllContasByGrupoIsInShouldWork() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grupo in DEFAULT_GRUPO or UPDATED_GRUPO
        defaultContaShouldBeFound("grupo.in=" + DEFAULT_GRUPO + "," + UPDATED_GRUPO);

        // Get all the contaList where grupo equals to UPDATED_GRUPO
        defaultContaShouldNotBeFound("grupo.in=" + UPDATED_GRUPO);
    }

    @Test
    @Transactional
    public void getAllContasByGrupoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grupo is not null
        defaultContaShouldBeFound("grupo.specified=true");

        // Get all the contaList where grupo is null
        defaultContaShouldNotBeFound("grupo.specified=false");
    }
                @Test
    @Transactional
    public void getAllContasByGrupoContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grupo contains DEFAULT_GRUPO
        defaultContaShouldBeFound("grupo.contains=" + DEFAULT_GRUPO);

        // Get all the contaList where grupo contains UPDATED_GRUPO
        defaultContaShouldNotBeFound("grupo.contains=" + UPDATED_GRUPO);
    }

    @Test
    @Transactional
    public void getAllContasByGrupoNotContainsSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        // Get all the contaList where grupo does not contain DEFAULT_GRUPO
        defaultContaShouldNotBeFound("grupo.doesNotContain=" + DEFAULT_GRUPO);

        // Get all the contaList where grupo does not contain UPDATED_GRUPO
        defaultContaShouldBeFound("grupo.doesNotContain=" + UPDATED_GRUPO);
    }


    @Test
    @Transactional
    public void getAllContasByContaIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);
        Conta conta = ContaResourceIT.createEntity(em);
        em.persist(conta);
        em.flush();
        conta.addConta(conta);
        contaRepository.saveAndFlush(conta);
        Long contaId = conta.getId();

        // Get all the contaList where conta equals to contaId
        defaultContaShouldBeFound("contaId.equals=" + contaId);

        // Get all the contaList where conta equals to contaId + 1
        defaultContaShouldNotBeFound("contaId.equals=" + (contaId + 1));
    }


    @Test
    @Transactional
    public void getAllContasByContaDebitoIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);
        ContaDebito contaDebito = ContaDebitoResourceIT.createEntity(em);
        em.persist(contaDebito);
        em.flush();
        conta.addContaDebito(contaDebito);
        contaRepository.saveAndFlush(conta);
        Long contaDebitoId = contaDebito.getId();

        // Get all the contaList where contaDebito equals to contaDebitoId
        defaultContaShouldBeFound("contaDebitoId.equals=" + contaDebitoId);

        // Get all the contaList where contaDebito equals to contaDebitoId + 1
        defaultContaShouldNotBeFound("contaDebitoId.equals=" + (contaDebitoId + 1));
    }


    @Test
    @Transactional
    public void getAllContasByContaCreditoIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);
        ContaCredito contaCredito = ContaCreditoResourceIT.createEntity(em);
        em.persist(contaCredito);
        em.flush();
        conta.addContaCredito(contaCredito);
        contaRepository.saveAndFlush(conta);
        Long contaCreditoId = contaCredito.getId();

        // Get all the contaList where contaCredito equals to contaCreditoId
        defaultContaShouldBeFound("contaCreditoId.equals=" + contaCreditoId);

        // Get all the contaList where contaCredito equals to contaCreditoId + 1
        defaultContaShouldNotBeFound("contaCreditoId.equals=" + (contaCreditoId + 1));
    }


    @Test
    @Transactional
    public void getAllContasByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        conta.addEmpresa(empresa);
        contaRepository.saveAndFlush(conta);
        Long empresaId = empresa.getId();

        // Get all the contaList where empresa equals to empresaId
        defaultContaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the contaList where empresa equals to empresaId + 1
        defaultContaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }


    @Test
    @Transactional
    public void getAllContasByContaAgregadoraIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);
        Conta contaAgregadora = ContaResourceIT.createEntity(em);
        em.persist(contaAgregadora);
        em.flush();
        conta.setContaAgregadora(contaAgregadora);
        contaRepository.saveAndFlush(conta);
        Long contaAgregadoraId = contaAgregadora.getId();

        // Get all the contaList where contaAgregadora equals to contaAgregadoraId
        defaultContaShouldBeFound("contaAgregadoraId.equals=" + contaAgregadoraId);

        // Get all the contaList where contaAgregadora equals to contaAgregadoraId + 1
        defaultContaShouldNotBeFound("contaAgregadoraId.equals=" + (contaAgregadoraId + 1));
    }


    @Test
    @Transactional
    public void getAllContasByClasseContaIsEqualToSomething() throws Exception {
        // Get already existing entity
        ClasseConta classeConta = conta.getClasseConta();
        contaRepository.saveAndFlush(conta);
        Long classeContaId = classeConta.getId();

        // Get all the contaList where classeConta equals to classeContaId
        defaultContaShouldBeFound("classeContaId.equals=" + classeContaId);

        // Get all the contaList where classeConta equals to classeContaId + 1
        defaultContaShouldNotBeFound("classeContaId.equals=" + (classeContaId + 1));
    }


    @Test
    @Transactional
    public void getAllContasByLancamentoFinanceiroIsEqualToSomething() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);
        LancamentoFinanceiro lancamentoFinanceiro = LancamentoFinanceiroResourceIT.createEntity(em);
        em.persist(lancamentoFinanceiro);
        em.flush();
        conta.setLancamentoFinanceiro(lancamentoFinanceiro);
        contaRepository.saveAndFlush(conta);
        Long lancamentoFinanceiroId = lancamentoFinanceiro.getId();

        // Get all the contaList where lancamentoFinanceiro equals to lancamentoFinanceiroId
        defaultContaShouldBeFound("lancamentoFinanceiroId.equals=" + lancamentoFinanceiroId);

        // Get all the contaList where lancamentoFinanceiro equals to lancamentoFinanceiroId + 1
        defaultContaShouldNotBeFound("lancamentoFinanceiroId.equals=" + (lancamentoFinanceiroId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContaShouldBeFound(String filter) throws Exception {
        restContaMockMvc.perform(get("/api/contas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].grau").value(hasItem(DEFAULT_GRAU)))
            .andExpect(jsonPath("$.[*].natureza").value(hasItem(DEFAULT_NATUREZA)))
            .andExpect(jsonPath("$.[*].grupo").value(hasItem(DEFAULT_GRUPO)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO.toString())));

        // Check, that the count call also returns 1
        restContaMockMvc.perform(get("/api/contas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContaShouldNotBeFound(String filter) throws Exception {
        restContaMockMvc.perform(get("/api/contas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContaMockMvc.perform(get("/api/contas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingConta() throws Exception {
        // Get the conta
        restContaMockMvc.perform(get("/api/contas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConta() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        int databaseSizeBeforeUpdate = contaRepository.findAll().size();

        // Update the conta
        Conta updatedConta = contaRepository.findById(conta.getId()).get();
        // Disconnect from session so that the updates on updatedConta are not directly saved in db
        em.detach(updatedConta);
        updatedConta
            .descricao(UPDATED_DESCRICAO)
            .codigo(UPDATED_CODIGO)
            .tipo(UPDATED_TIPO)
            .grau(UPDATED_GRAU)
            .natureza(UPDATED_NATUREZA)
            .grupo(UPDATED_GRUPO)
            .conteudo(UPDATED_CONTEUDO);
        ContaDTO contaDTO = contaMapper.toDto(updatedConta);

        restContaMockMvc.perform(put("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isOk());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
        Conta testConta = contaList.get(contaList.size() - 1);
        assertThat(testConta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testConta.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testConta.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testConta.getGrau()).isEqualTo(UPDATED_GRAU);
        assertThat(testConta.getNatureza()).isEqualTo(UPDATED_NATUREZA);
        assertThat(testConta.getGrupo()).isEqualTo(UPDATED_GRUPO);
        assertThat(testConta.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
    }

    @Test
    @Transactional
    public void updateNonExistingConta() throws Exception {
        int databaseSizeBeforeUpdate = contaRepository.findAll().size();

        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContaMockMvc.perform(put("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConta() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);

        int databaseSizeBeforeDelete = contaRepository.findAll().size();

        // Delete the conta
        restContaMockMvc.perform(delete("/api/contas/{id}", conta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
