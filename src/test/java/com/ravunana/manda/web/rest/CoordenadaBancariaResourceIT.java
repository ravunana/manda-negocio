package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.CoordenadaBancaria;
import com.ravunana.manda.domain.DetalheLancamento;
import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.domain.Empresa;
import com.ravunana.manda.repository.CoordenadaBancariaRepository;
import com.ravunana.manda.service.CoordenadaBancariaService;
import com.ravunana.manda.service.dto.CoordenadaBancariaDTO;
import com.ravunana.manda.service.mapper.CoordenadaBancariaMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.CoordenadaBancariaCriteria;
import com.ravunana.manda.service.CoordenadaBancariaQueryService;

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
 * Integration tests for the {@link CoordenadaBancariaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class CoordenadaBancariaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_PROPRIETARIO = "AAAAAAAAAA";
    private static final String UPDATED_PROPRIETARIO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CONTA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CONTA = "BBBBBBBBBB";

    private static final String DEFAULT_IBAN = "AAAAAAAAAA";
    private static final String UPDATED_IBAN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Boolean DEFAULT_MOSTRAR_DOCUMENTO = false;
    private static final Boolean UPDATED_MOSTRAR_DOCUMENTO = true;

    private static final Boolean DEFAULT_MOSTRAR_PONTO_VENDA = false;
    private static final Boolean UPDATED_MOSTRAR_PONTO_VENDA = true;

    private static final Boolean DEFAULT_PADRAO_RECEBIMENTO = false;
    private static final Boolean UPDATED_PADRAO_RECEBIMENTO = true;

    private static final Boolean DEFAULT_PADRAO_PAGAMENTO = false;
    private static final Boolean UPDATED_PADRAO_PAGAMENTO = true;

    @Autowired
    private CoordenadaBancariaRepository coordenadaBancariaRepository;

    @Mock
    private CoordenadaBancariaRepository coordenadaBancariaRepositoryMock;

    @Autowired
    private CoordenadaBancariaMapper coordenadaBancariaMapper;

    @Mock
    private CoordenadaBancariaService coordenadaBancariaServiceMock;

    @Autowired
    private CoordenadaBancariaService coordenadaBancariaService;

    @Autowired
    private CoordenadaBancariaQueryService coordenadaBancariaQueryService;

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

    private MockMvc restCoordenadaBancariaMockMvc;

    private CoordenadaBancaria coordenadaBancaria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoordenadaBancariaResource coordenadaBancariaResource = new CoordenadaBancariaResource(coordenadaBancariaService, coordenadaBancariaQueryService);
        this.restCoordenadaBancariaMockMvc = MockMvcBuilders.standaloneSetup(coordenadaBancariaResource)
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
    public static CoordenadaBancaria createEntity(EntityManager em) {
        CoordenadaBancaria coordenadaBancaria = new CoordenadaBancaria()
            .descricao(DEFAULT_DESCRICAO)
            .proprietario(DEFAULT_PROPRIETARIO)
            .numeroConta(DEFAULT_NUMERO_CONTA)
            .iban(DEFAULT_IBAN)
            .ativo(DEFAULT_ATIVO)
            .mostrarDocumento(DEFAULT_MOSTRAR_DOCUMENTO)
            .mostrarPontoVenda(DEFAULT_MOSTRAR_PONTO_VENDA)
            .padraoRecebimento(DEFAULT_PADRAO_RECEBIMENTO)
            .padraoPagamento(DEFAULT_PADRAO_PAGAMENTO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        coordenadaBancaria.getEmpresas().add(empresa);
        return coordenadaBancaria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoordenadaBancaria createUpdatedEntity(EntityManager em) {
        CoordenadaBancaria coordenadaBancaria = new CoordenadaBancaria()
            .descricao(UPDATED_DESCRICAO)
            .proprietario(UPDATED_PROPRIETARIO)
            .numeroConta(UPDATED_NUMERO_CONTA)
            .iban(UPDATED_IBAN)
            .ativo(UPDATED_ATIVO)
            .mostrarDocumento(UPDATED_MOSTRAR_DOCUMENTO)
            .mostrarPontoVenda(UPDATED_MOSTRAR_PONTO_VENDA)
            .padraoRecebimento(UPDATED_PADRAO_RECEBIMENTO)
            .padraoPagamento(UPDATED_PADRAO_PAGAMENTO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        coordenadaBancaria.getEmpresas().add(empresa);
        return coordenadaBancaria;
    }

    @BeforeEach
    public void initTest() {
        coordenadaBancaria = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoordenadaBancaria() throws Exception {
        int databaseSizeBeforeCreate = coordenadaBancariaRepository.findAll().size();

        // Create the CoordenadaBancaria
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);
        restCoordenadaBancariaMockMvc.perform(post("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isCreated());

        // Validate the CoordenadaBancaria in the database
        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeCreate + 1);
        CoordenadaBancaria testCoordenadaBancaria = coordenadaBancariaList.get(coordenadaBancariaList.size() - 1);
        assertThat(testCoordenadaBancaria.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCoordenadaBancaria.getProprietario()).isEqualTo(DEFAULT_PROPRIETARIO);
        assertThat(testCoordenadaBancaria.getNumeroConta()).isEqualTo(DEFAULT_NUMERO_CONTA);
        assertThat(testCoordenadaBancaria.getIban()).isEqualTo(DEFAULT_IBAN);
        assertThat(testCoordenadaBancaria.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testCoordenadaBancaria.isMostrarDocumento()).isEqualTo(DEFAULT_MOSTRAR_DOCUMENTO);
        assertThat(testCoordenadaBancaria.isMostrarPontoVenda()).isEqualTo(DEFAULT_MOSTRAR_PONTO_VENDA);
        assertThat(testCoordenadaBancaria.isPadraoRecebimento()).isEqualTo(DEFAULT_PADRAO_RECEBIMENTO);
        assertThat(testCoordenadaBancaria.isPadraoPagamento()).isEqualTo(DEFAULT_PADRAO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void createCoordenadaBancariaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coordenadaBancariaRepository.findAll().size();

        // Create the CoordenadaBancaria with an existing ID
        coordenadaBancaria.setId(1L);
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoordenadaBancariaMockMvc.perform(post("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoordenadaBancaria in the database
        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = coordenadaBancariaRepository.findAll().size();
        // set the field null
        coordenadaBancaria.setDescricao(null);

        // Create the CoordenadaBancaria, which fails.
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);

        restCoordenadaBancariaMockMvc.perform(post("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isBadRequest());

        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProprietarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = coordenadaBancariaRepository.findAll().size();
        // set the field null
        coordenadaBancaria.setProprietario(null);

        // Create the CoordenadaBancaria, which fails.
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);

        restCoordenadaBancariaMockMvc.perform(post("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isBadRequest());

        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroContaIsRequired() throws Exception {
        int databaseSizeBeforeTest = coordenadaBancariaRepository.findAll().size();
        // set the field null
        coordenadaBancaria.setNumeroConta(null);

        // Create the CoordenadaBancaria, which fails.
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);

        restCoordenadaBancariaMockMvc.perform(post("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isBadRequest());

        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = coordenadaBancariaRepository.findAll().size();
        // set the field null
        coordenadaBancaria.setAtivo(null);

        // Create the CoordenadaBancaria, which fails.
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);

        restCoordenadaBancariaMockMvc.perform(post("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isBadRequest());

        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancarias() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList
        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordenadaBancaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].proprietario").value(hasItem(DEFAULT_PROPRIETARIO)))
            .andExpect(jsonPath("$.[*].numeroConta").value(hasItem(DEFAULT_NUMERO_CONTA)))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].mostrarDocumento").value(hasItem(DEFAULT_MOSTRAR_DOCUMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].mostrarPontoVenda").value(hasItem(DEFAULT_MOSTRAR_PONTO_VENDA.booleanValue())))
            .andExpect(jsonPath("$.[*].padraoRecebimento").value(hasItem(DEFAULT_PADRAO_RECEBIMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].padraoPagamento").value(hasItem(DEFAULT_PADRAO_PAGAMENTO.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCoordenadaBancariasWithEagerRelationshipsIsEnabled() throws Exception {
        CoordenadaBancariaResource coordenadaBancariaResource = new CoordenadaBancariaResource(coordenadaBancariaServiceMock, coordenadaBancariaQueryService);
        when(coordenadaBancariaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCoordenadaBancariaMockMvc = MockMvcBuilders.standaloneSetup(coordenadaBancariaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias?eagerload=true"))
        .andExpect(status().isOk());

        verify(coordenadaBancariaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCoordenadaBancariasWithEagerRelationshipsIsNotEnabled() throws Exception {
        CoordenadaBancariaResource coordenadaBancariaResource = new CoordenadaBancariaResource(coordenadaBancariaServiceMock, coordenadaBancariaQueryService);
            when(coordenadaBancariaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCoordenadaBancariaMockMvc = MockMvcBuilders.standaloneSetup(coordenadaBancariaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias?eagerload=true"))
        .andExpect(status().isOk());

            verify(coordenadaBancariaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCoordenadaBancaria() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get the coordenadaBancaria
        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias/{id}", coordenadaBancaria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coordenadaBancaria.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.proprietario").value(DEFAULT_PROPRIETARIO))
            .andExpect(jsonPath("$.numeroConta").value(DEFAULT_NUMERO_CONTA))
            .andExpect(jsonPath("$.iban").value(DEFAULT_IBAN))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.mostrarDocumento").value(DEFAULT_MOSTRAR_DOCUMENTO.booleanValue()))
            .andExpect(jsonPath("$.mostrarPontoVenda").value(DEFAULT_MOSTRAR_PONTO_VENDA.booleanValue()))
            .andExpect(jsonPath("$.padraoRecebimento").value(DEFAULT_PADRAO_RECEBIMENTO.booleanValue()))
            .andExpect(jsonPath("$.padraoPagamento").value(DEFAULT_PADRAO_PAGAMENTO.booleanValue()));
    }


    @Test
    @Transactional
    public void getCoordenadaBancariasByIdFiltering() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        Long id = coordenadaBancaria.getId();

        defaultCoordenadaBancariaShouldBeFound("id.equals=" + id);
        defaultCoordenadaBancariaShouldNotBeFound("id.notEquals=" + id);

        defaultCoordenadaBancariaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCoordenadaBancariaShouldNotBeFound("id.greaterThan=" + id);

        defaultCoordenadaBancariaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCoordenadaBancariaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCoordenadaBancariasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where descricao equals to DEFAULT_DESCRICAO
        defaultCoordenadaBancariaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the coordenadaBancariaList where descricao equals to UPDATED_DESCRICAO
        defaultCoordenadaBancariaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where descricao not equals to DEFAULT_DESCRICAO
        defaultCoordenadaBancariaShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the coordenadaBancariaList where descricao not equals to UPDATED_DESCRICAO
        defaultCoordenadaBancariaShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultCoordenadaBancariaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the coordenadaBancariaList where descricao equals to UPDATED_DESCRICAO
        defaultCoordenadaBancariaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where descricao is not null
        defaultCoordenadaBancariaShouldBeFound("descricao.specified=true");

        // Get all the coordenadaBancariaList where descricao is null
        defaultCoordenadaBancariaShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoordenadaBancariasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where descricao contains DEFAULT_DESCRICAO
        defaultCoordenadaBancariaShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the coordenadaBancariaList where descricao contains UPDATED_DESCRICAO
        defaultCoordenadaBancariaShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where descricao does not contain DEFAULT_DESCRICAO
        defaultCoordenadaBancariaShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the coordenadaBancariaList where descricao does not contain UPDATED_DESCRICAO
        defaultCoordenadaBancariaShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllCoordenadaBancariasByProprietarioIsEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where proprietario equals to DEFAULT_PROPRIETARIO
        defaultCoordenadaBancariaShouldBeFound("proprietario.equals=" + DEFAULT_PROPRIETARIO);

        // Get all the coordenadaBancariaList where proprietario equals to UPDATED_PROPRIETARIO
        defaultCoordenadaBancariaShouldNotBeFound("proprietario.equals=" + UPDATED_PROPRIETARIO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByProprietarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where proprietario not equals to DEFAULT_PROPRIETARIO
        defaultCoordenadaBancariaShouldNotBeFound("proprietario.notEquals=" + DEFAULT_PROPRIETARIO);

        // Get all the coordenadaBancariaList where proprietario not equals to UPDATED_PROPRIETARIO
        defaultCoordenadaBancariaShouldBeFound("proprietario.notEquals=" + UPDATED_PROPRIETARIO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByProprietarioIsInShouldWork() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where proprietario in DEFAULT_PROPRIETARIO or UPDATED_PROPRIETARIO
        defaultCoordenadaBancariaShouldBeFound("proprietario.in=" + DEFAULT_PROPRIETARIO + "," + UPDATED_PROPRIETARIO);

        // Get all the coordenadaBancariaList where proprietario equals to UPDATED_PROPRIETARIO
        defaultCoordenadaBancariaShouldNotBeFound("proprietario.in=" + UPDATED_PROPRIETARIO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByProprietarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where proprietario is not null
        defaultCoordenadaBancariaShouldBeFound("proprietario.specified=true");

        // Get all the coordenadaBancariaList where proprietario is null
        defaultCoordenadaBancariaShouldNotBeFound("proprietario.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoordenadaBancariasByProprietarioContainsSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where proprietario contains DEFAULT_PROPRIETARIO
        defaultCoordenadaBancariaShouldBeFound("proprietario.contains=" + DEFAULT_PROPRIETARIO);

        // Get all the coordenadaBancariaList where proprietario contains UPDATED_PROPRIETARIO
        defaultCoordenadaBancariaShouldNotBeFound("proprietario.contains=" + UPDATED_PROPRIETARIO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByProprietarioNotContainsSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where proprietario does not contain DEFAULT_PROPRIETARIO
        defaultCoordenadaBancariaShouldNotBeFound("proprietario.doesNotContain=" + DEFAULT_PROPRIETARIO);

        // Get all the coordenadaBancariaList where proprietario does not contain UPDATED_PROPRIETARIO
        defaultCoordenadaBancariaShouldBeFound("proprietario.doesNotContain=" + UPDATED_PROPRIETARIO);
    }


    @Test
    @Transactional
    public void getAllCoordenadaBancariasByNumeroContaIsEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where numeroConta equals to DEFAULT_NUMERO_CONTA
        defaultCoordenadaBancariaShouldBeFound("numeroConta.equals=" + DEFAULT_NUMERO_CONTA);

        // Get all the coordenadaBancariaList where numeroConta equals to UPDATED_NUMERO_CONTA
        defaultCoordenadaBancariaShouldNotBeFound("numeroConta.equals=" + UPDATED_NUMERO_CONTA);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByNumeroContaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where numeroConta not equals to DEFAULT_NUMERO_CONTA
        defaultCoordenadaBancariaShouldNotBeFound("numeroConta.notEquals=" + DEFAULT_NUMERO_CONTA);

        // Get all the coordenadaBancariaList where numeroConta not equals to UPDATED_NUMERO_CONTA
        defaultCoordenadaBancariaShouldBeFound("numeroConta.notEquals=" + UPDATED_NUMERO_CONTA);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByNumeroContaIsInShouldWork() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where numeroConta in DEFAULT_NUMERO_CONTA or UPDATED_NUMERO_CONTA
        defaultCoordenadaBancariaShouldBeFound("numeroConta.in=" + DEFAULT_NUMERO_CONTA + "," + UPDATED_NUMERO_CONTA);

        // Get all the coordenadaBancariaList where numeroConta equals to UPDATED_NUMERO_CONTA
        defaultCoordenadaBancariaShouldNotBeFound("numeroConta.in=" + UPDATED_NUMERO_CONTA);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByNumeroContaIsNullOrNotNull() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where numeroConta is not null
        defaultCoordenadaBancariaShouldBeFound("numeroConta.specified=true");

        // Get all the coordenadaBancariaList where numeroConta is null
        defaultCoordenadaBancariaShouldNotBeFound("numeroConta.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoordenadaBancariasByNumeroContaContainsSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where numeroConta contains DEFAULT_NUMERO_CONTA
        defaultCoordenadaBancariaShouldBeFound("numeroConta.contains=" + DEFAULT_NUMERO_CONTA);

        // Get all the coordenadaBancariaList where numeroConta contains UPDATED_NUMERO_CONTA
        defaultCoordenadaBancariaShouldNotBeFound("numeroConta.contains=" + UPDATED_NUMERO_CONTA);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByNumeroContaNotContainsSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where numeroConta does not contain DEFAULT_NUMERO_CONTA
        defaultCoordenadaBancariaShouldNotBeFound("numeroConta.doesNotContain=" + DEFAULT_NUMERO_CONTA);

        // Get all the coordenadaBancariaList where numeroConta does not contain UPDATED_NUMERO_CONTA
        defaultCoordenadaBancariaShouldBeFound("numeroConta.doesNotContain=" + UPDATED_NUMERO_CONTA);
    }


    @Test
    @Transactional
    public void getAllCoordenadaBancariasByIbanIsEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where iban equals to DEFAULT_IBAN
        defaultCoordenadaBancariaShouldBeFound("iban.equals=" + DEFAULT_IBAN);

        // Get all the coordenadaBancariaList where iban equals to UPDATED_IBAN
        defaultCoordenadaBancariaShouldNotBeFound("iban.equals=" + UPDATED_IBAN);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByIbanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where iban not equals to DEFAULT_IBAN
        defaultCoordenadaBancariaShouldNotBeFound("iban.notEquals=" + DEFAULT_IBAN);

        // Get all the coordenadaBancariaList where iban not equals to UPDATED_IBAN
        defaultCoordenadaBancariaShouldBeFound("iban.notEquals=" + UPDATED_IBAN);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByIbanIsInShouldWork() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where iban in DEFAULT_IBAN or UPDATED_IBAN
        defaultCoordenadaBancariaShouldBeFound("iban.in=" + DEFAULT_IBAN + "," + UPDATED_IBAN);

        // Get all the coordenadaBancariaList where iban equals to UPDATED_IBAN
        defaultCoordenadaBancariaShouldNotBeFound("iban.in=" + UPDATED_IBAN);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByIbanIsNullOrNotNull() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where iban is not null
        defaultCoordenadaBancariaShouldBeFound("iban.specified=true");

        // Get all the coordenadaBancariaList where iban is null
        defaultCoordenadaBancariaShouldNotBeFound("iban.specified=false");
    }
                @Test
    @Transactional
    public void getAllCoordenadaBancariasByIbanContainsSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where iban contains DEFAULT_IBAN
        defaultCoordenadaBancariaShouldBeFound("iban.contains=" + DEFAULT_IBAN);

        // Get all the coordenadaBancariaList where iban contains UPDATED_IBAN
        defaultCoordenadaBancariaShouldNotBeFound("iban.contains=" + UPDATED_IBAN);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByIbanNotContainsSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where iban does not contain DEFAULT_IBAN
        defaultCoordenadaBancariaShouldNotBeFound("iban.doesNotContain=" + DEFAULT_IBAN);

        // Get all the coordenadaBancariaList where iban does not contain UPDATED_IBAN
        defaultCoordenadaBancariaShouldBeFound("iban.doesNotContain=" + UPDATED_IBAN);
    }


    @Test
    @Transactional
    public void getAllCoordenadaBancariasByAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where ativo equals to DEFAULT_ATIVO
        defaultCoordenadaBancariaShouldBeFound("ativo.equals=" + DEFAULT_ATIVO);

        // Get all the coordenadaBancariaList where ativo equals to UPDATED_ATIVO
        defaultCoordenadaBancariaShouldNotBeFound("ativo.equals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where ativo not equals to DEFAULT_ATIVO
        defaultCoordenadaBancariaShouldNotBeFound("ativo.notEquals=" + DEFAULT_ATIVO);

        // Get all the coordenadaBancariaList where ativo not equals to UPDATED_ATIVO
        defaultCoordenadaBancariaShouldBeFound("ativo.notEquals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where ativo in DEFAULT_ATIVO or UPDATED_ATIVO
        defaultCoordenadaBancariaShouldBeFound("ativo.in=" + DEFAULT_ATIVO + "," + UPDATED_ATIVO);

        // Get all the coordenadaBancariaList where ativo equals to UPDATED_ATIVO
        defaultCoordenadaBancariaShouldNotBeFound("ativo.in=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where ativo is not null
        defaultCoordenadaBancariaShouldBeFound("ativo.specified=true");

        // Get all the coordenadaBancariaList where ativo is null
        defaultCoordenadaBancariaShouldNotBeFound("ativo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByMostrarDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where mostrarDocumento equals to DEFAULT_MOSTRAR_DOCUMENTO
        defaultCoordenadaBancariaShouldBeFound("mostrarDocumento.equals=" + DEFAULT_MOSTRAR_DOCUMENTO);

        // Get all the coordenadaBancariaList where mostrarDocumento equals to UPDATED_MOSTRAR_DOCUMENTO
        defaultCoordenadaBancariaShouldNotBeFound("mostrarDocumento.equals=" + UPDATED_MOSTRAR_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByMostrarDocumentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where mostrarDocumento not equals to DEFAULT_MOSTRAR_DOCUMENTO
        defaultCoordenadaBancariaShouldNotBeFound("mostrarDocumento.notEquals=" + DEFAULT_MOSTRAR_DOCUMENTO);

        // Get all the coordenadaBancariaList where mostrarDocumento not equals to UPDATED_MOSTRAR_DOCUMENTO
        defaultCoordenadaBancariaShouldBeFound("mostrarDocumento.notEquals=" + UPDATED_MOSTRAR_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByMostrarDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where mostrarDocumento in DEFAULT_MOSTRAR_DOCUMENTO or UPDATED_MOSTRAR_DOCUMENTO
        defaultCoordenadaBancariaShouldBeFound("mostrarDocumento.in=" + DEFAULT_MOSTRAR_DOCUMENTO + "," + UPDATED_MOSTRAR_DOCUMENTO);

        // Get all the coordenadaBancariaList where mostrarDocumento equals to UPDATED_MOSTRAR_DOCUMENTO
        defaultCoordenadaBancariaShouldNotBeFound("mostrarDocumento.in=" + UPDATED_MOSTRAR_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByMostrarDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where mostrarDocumento is not null
        defaultCoordenadaBancariaShouldBeFound("mostrarDocumento.specified=true");

        // Get all the coordenadaBancariaList where mostrarDocumento is null
        defaultCoordenadaBancariaShouldNotBeFound("mostrarDocumento.specified=false");
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByMostrarPontoVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where mostrarPontoVenda equals to DEFAULT_MOSTRAR_PONTO_VENDA
        defaultCoordenadaBancariaShouldBeFound("mostrarPontoVenda.equals=" + DEFAULT_MOSTRAR_PONTO_VENDA);

        // Get all the coordenadaBancariaList where mostrarPontoVenda equals to UPDATED_MOSTRAR_PONTO_VENDA
        defaultCoordenadaBancariaShouldNotBeFound("mostrarPontoVenda.equals=" + UPDATED_MOSTRAR_PONTO_VENDA);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByMostrarPontoVendaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where mostrarPontoVenda not equals to DEFAULT_MOSTRAR_PONTO_VENDA
        defaultCoordenadaBancariaShouldNotBeFound("mostrarPontoVenda.notEquals=" + DEFAULT_MOSTRAR_PONTO_VENDA);

        // Get all the coordenadaBancariaList where mostrarPontoVenda not equals to UPDATED_MOSTRAR_PONTO_VENDA
        defaultCoordenadaBancariaShouldBeFound("mostrarPontoVenda.notEquals=" + UPDATED_MOSTRAR_PONTO_VENDA);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByMostrarPontoVendaIsInShouldWork() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where mostrarPontoVenda in DEFAULT_MOSTRAR_PONTO_VENDA or UPDATED_MOSTRAR_PONTO_VENDA
        defaultCoordenadaBancariaShouldBeFound("mostrarPontoVenda.in=" + DEFAULT_MOSTRAR_PONTO_VENDA + "," + UPDATED_MOSTRAR_PONTO_VENDA);

        // Get all the coordenadaBancariaList where mostrarPontoVenda equals to UPDATED_MOSTRAR_PONTO_VENDA
        defaultCoordenadaBancariaShouldNotBeFound("mostrarPontoVenda.in=" + UPDATED_MOSTRAR_PONTO_VENDA);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByMostrarPontoVendaIsNullOrNotNull() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where mostrarPontoVenda is not null
        defaultCoordenadaBancariaShouldBeFound("mostrarPontoVenda.specified=true");

        // Get all the coordenadaBancariaList where mostrarPontoVenda is null
        defaultCoordenadaBancariaShouldNotBeFound("mostrarPontoVenda.specified=false");
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByPadraoRecebimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where padraoRecebimento equals to DEFAULT_PADRAO_RECEBIMENTO
        defaultCoordenadaBancariaShouldBeFound("padraoRecebimento.equals=" + DEFAULT_PADRAO_RECEBIMENTO);

        // Get all the coordenadaBancariaList where padraoRecebimento equals to UPDATED_PADRAO_RECEBIMENTO
        defaultCoordenadaBancariaShouldNotBeFound("padraoRecebimento.equals=" + UPDATED_PADRAO_RECEBIMENTO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByPadraoRecebimentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where padraoRecebimento not equals to DEFAULT_PADRAO_RECEBIMENTO
        defaultCoordenadaBancariaShouldNotBeFound("padraoRecebimento.notEquals=" + DEFAULT_PADRAO_RECEBIMENTO);

        // Get all the coordenadaBancariaList where padraoRecebimento not equals to UPDATED_PADRAO_RECEBIMENTO
        defaultCoordenadaBancariaShouldBeFound("padraoRecebimento.notEquals=" + UPDATED_PADRAO_RECEBIMENTO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByPadraoRecebimentoIsInShouldWork() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where padraoRecebimento in DEFAULT_PADRAO_RECEBIMENTO or UPDATED_PADRAO_RECEBIMENTO
        defaultCoordenadaBancariaShouldBeFound("padraoRecebimento.in=" + DEFAULT_PADRAO_RECEBIMENTO + "," + UPDATED_PADRAO_RECEBIMENTO);

        // Get all the coordenadaBancariaList where padraoRecebimento equals to UPDATED_PADRAO_RECEBIMENTO
        defaultCoordenadaBancariaShouldNotBeFound("padraoRecebimento.in=" + UPDATED_PADRAO_RECEBIMENTO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByPadraoRecebimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where padraoRecebimento is not null
        defaultCoordenadaBancariaShouldBeFound("padraoRecebimento.specified=true");

        // Get all the coordenadaBancariaList where padraoRecebimento is null
        defaultCoordenadaBancariaShouldNotBeFound("padraoRecebimento.specified=false");
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByPadraoPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where padraoPagamento equals to DEFAULT_PADRAO_PAGAMENTO
        defaultCoordenadaBancariaShouldBeFound("padraoPagamento.equals=" + DEFAULT_PADRAO_PAGAMENTO);

        // Get all the coordenadaBancariaList where padraoPagamento equals to UPDATED_PADRAO_PAGAMENTO
        defaultCoordenadaBancariaShouldNotBeFound("padraoPagamento.equals=" + UPDATED_PADRAO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByPadraoPagamentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where padraoPagamento not equals to DEFAULT_PADRAO_PAGAMENTO
        defaultCoordenadaBancariaShouldNotBeFound("padraoPagamento.notEquals=" + DEFAULT_PADRAO_PAGAMENTO);

        // Get all the coordenadaBancariaList where padraoPagamento not equals to UPDATED_PADRAO_PAGAMENTO
        defaultCoordenadaBancariaShouldBeFound("padraoPagamento.notEquals=" + UPDATED_PADRAO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByPadraoPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where padraoPagamento in DEFAULT_PADRAO_PAGAMENTO or UPDATED_PADRAO_PAGAMENTO
        defaultCoordenadaBancariaShouldBeFound("padraoPagamento.in=" + DEFAULT_PADRAO_PAGAMENTO + "," + UPDATED_PADRAO_PAGAMENTO);

        // Get all the coordenadaBancariaList where padraoPagamento equals to UPDATED_PADRAO_PAGAMENTO
        defaultCoordenadaBancariaShouldNotBeFound("padraoPagamento.in=" + UPDATED_PADRAO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByPadraoPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        // Get all the coordenadaBancariaList where padraoPagamento is not null
        defaultCoordenadaBancariaShouldBeFound("padraoPagamento.specified=true");

        // Get all the coordenadaBancariaList where padraoPagamento is null
        defaultCoordenadaBancariaShouldNotBeFound("padraoPagamento.specified=false");
    }

    @Test
    @Transactional
    public void getAllCoordenadaBancariasByDetalheLancamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);
        DetalheLancamento detalheLancamento = DetalheLancamentoResourceIT.createEntity(em);
        em.persist(detalheLancamento);
        em.flush();
        coordenadaBancaria.addDetalheLancamento(detalheLancamento);
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);
        Long detalheLancamentoId = detalheLancamento.getId();

        // Get all the coordenadaBancariaList where detalheLancamento equals to detalheLancamentoId
        defaultCoordenadaBancariaShouldBeFound("detalheLancamentoId.equals=" + detalheLancamentoId);

        // Get all the coordenadaBancariaList where detalheLancamento equals to detalheLancamentoId + 1
        defaultCoordenadaBancariaShouldNotBeFound("detalheLancamentoId.equals=" + (detalheLancamentoId + 1));
    }


    @Test
    @Transactional
    public void getAllCoordenadaBancariasByContaIsEqualToSomething() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);
        Conta conta = ContaResourceIT.createEntity(em);
        em.persist(conta);
        em.flush();
        coordenadaBancaria.setConta(conta);
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);
        Long contaId = conta.getId();

        // Get all the coordenadaBancariaList where conta equals to contaId
        defaultCoordenadaBancariaShouldBeFound("contaId.equals=" + contaId);

        // Get all the coordenadaBancariaList where conta equals to contaId + 1
        defaultCoordenadaBancariaShouldNotBeFound("contaId.equals=" + (contaId + 1));
    }


    @Test
    @Transactional
    public void getAllCoordenadaBancariasByEmpresaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Empresa empresa = coordenadaBancaria.getEmpresa();
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);
        Long empresaId = empresa.getId();

        // Get all the coordenadaBancariaList where empresa equals to empresaId
        defaultCoordenadaBancariaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the coordenadaBancariaList where empresa equals to empresaId + 1
        defaultCoordenadaBancariaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCoordenadaBancariaShouldBeFound(String filter) throws Exception {
        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordenadaBancaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].proprietario").value(hasItem(DEFAULT_PROPRIETARIO)))
            .andExpect(jsonPath("$.[*].numeroConta").value(hasItem(DEFAULT_NUMERO_CONTA)))
            .andExpect(jsonPath("$.[*].iban").value(hasItem(DEFAULT_IBAN)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].mostrarDocumento").value(hasItem(DEFAULT_MOSTRAR_DOCUMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].mostrarPontoVenda").value(hasItem(DEFAULT_MOSTRAR_PONTO_VENDA.booleanValue())))
            .andExpect(jsonPath("$.[*].padraoRecebimento").value(hasItem(DEFAULT_PADRAO_RECEBIMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].padraoPagamento").value(hasItem(DEFAULT_PADRAO_PAGAMENTO.booleanValue())));

        // Check, that the count call also returns 1
        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCoordenadaBancariaShouldNotBeFound(String filter) throws Exception {
        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCoordenadaBancaria() throws Exception {
        // Get the coordenadaBancaria
        restCoordenadaBancariaMockMvc.perform(get("/api/coordenada-bancarias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoordenadaBancaria() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        int databaseSizeBeforeUpdate = coordenadaBancariaRepository.findAll().size();

        // Update the coordenadaBancaria
        CoordenadaBancaria updatedCoordenadaBancaria = coordenadaBancariaRepository.findById(coordenadaBancaria.getId()).get();
        // Disconnect from session so that the updates on updatedCoordenadaBancaria are not directly saved in db
        em.detach(updatedCoordenadaBancaria);
        updatedCoordenadaBancaria
            .descricao(UPDATED_DESCRICAO)
            .proprietario(UPDATED_PROPRIETARIO)
            .numeroConta(UPDATED_NUMERO_CONTA)
            .iban(UPDATED_IBAN)
            .ativo(UPDATED_ATIVO)
            .mostrarDocumento(UPDATED_MOSTRAR_DOCUMENTO)
            .mostrarPontoVenda(UPDATED_MOSTRAR_PONTO_VENDA)
            .padraoRecebimento(UPDATED_PADRAO_RECEBIMENTO)
            .padraoPagamento(UPDATED_PADRAO_PAGAMENTO);
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(updatedCoordenadaBancaria);

        restCoordenadaBancariaMockMvc.perform(put("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isOk());

        // Validate the CoordenadaBancaria in the database
        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeUpdate);
        CoordenadaBancaria testCoordenadaBancaria = coordenadaBancariaList.get(coordenadaBancariaList.size() - 1);
        assertThat(testCoordenadaBancaria.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCoordenadaBancaria.getProprietario()).isEqualTo(UPDATED_PROPRIETARIO);
        assertThat(testCoordenadaBancaria.getNumeroConta()).isEqualTo(UPDATED_NUMERO_CONTA);
        assertThat(testCoordenadaBancaria.getIban()).isEqualTo(UPDATED_IBAN);
        assertThat(testCoordenadaBancaria.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testCoordenadaBancaria.isMostrarDocumento()).isEqualTo(UPDATED_MOSTRAR_DOCUMENTO);
        assertThat(testCoordenadaBancaria.isMostrarPontoVenda()).isEqualTo(UPDATED_MOSTRAR_PONTO_VENDA);
        assertThat(testCoordenadaBancaria.isPadraoRecebimento()).isEqualTo(UPDATED_PADRAO_RECEBIMENTO);
        assertThat(testCoordenadaBancaria.isPadraoPagamento()).isEqualTo(UPDATED_PADRAO_PAGAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingCoordenadaBancaria() throws Exception {
        int databaseSizeBeforeUpdate = coordenadaBancariaRepository.findAll().size();

        // Create the CoordenadaBancaria
        CoordenadaBancariaDTO coordenadaBancariaDTO = coordenadaBancariaMapper.toDto(coordenadaBancaria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoordenadaBancariaMockMvc.perform(put("/api/coordenada-bancarias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordenadaBancariaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoordenadaBancaria in the database
        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoordenadaBancaria() throws Exception {
        // Initialize the database
        coordenadaBancariaRepository.saveAndFlush(coordenadaBancaria);

        int databaseSizeBeforeDelete = coordenadaBancariaRepository.findAll().size();

        // Delete the coordenadaBancaria
        restCoordenadaBancariaMockMvc.perform(delete("/api/coordenada-bancarias/{id}", coordenadaBancaria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CoordenadaBancaria> coordenadaBancariaList = coordenadaBancariaRepository.findAll();
        assertThat(coordenadaBancariaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
