package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.Fornecedor;
import com.ravunana.manda.domain.Pessoa;
import com.ravunana.manda.domain.ItemCompra;
import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.repository.FornecedorRepository;
import com.ravunana.manda.service.FornecedorService;
import com.ravunana.manda.service.dto.FornecedorDTO;
import com.ravunana.manda.service.mapper.FornecedorMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.FornecedorCriteria;
import com.ravunana.manda.service.FornecedorQueryService;

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

/**
 * Integration tests for the {@link FornecedorResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class FornecedorResourceIT {

    private static final Boolean DEFAULT_CERTIFICADO_ISO_9001 = false;
    private static final Boolean UPDATED_CERTIFICADO_ISO_9001 = true;

    private static final Boolean DEFAULT_GARANTIA_DEFEITO_FABRICA = false;
    private static final Boolean UPDATED_GARANTIA_DEFEITO_FABRICA = true;

    private static final Boolean DEFAULT_TRANSPORTE = false;
    private static final Boolean UPDATED_TRANSPORTE = true;

    private static final Double DEFAULT_QUALIDADE = 0D;
    private static final Double UPDATED_QUALIDADE = 1D;
    private static final Double SMALLER_QUALIDADE = 0D - 1D;

    private static final Boolean DEFAULT_PAGAMENTO_PRAZO = false;
    private static final Boolean UPDATED_PAGAMENTO_PRAZO = true;

    private static final String DEFAULT_METODOS_PAGAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_METODOS_PAGAMENTO = "BBBBBBBBBB";

    private static final Double DEFAULT_CLASSIFICACAO = 0D;
    private static final Double UPDATED_CLASSIFICACAO = 1D;
    private static final Double SMALLER_CLASSIFICACAO = 0D - 1D;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorMapper fornecedorMapper;

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private FornecedorQueryService fornecedorQueryService;

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

    private MockMvc restFornecedorMockMvc;

    private Fornecedor fornecedor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FornecedorResource fornecedorResource = new FornecedorResource(fornecedorService, fornecedorQueryService);
        this.restFornecedorMockMvc = MockMvcBuilders.standaloneSetup(fornecedorResource)
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
    public static Fornecedor createEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor()
            .certificadoISO9001(DEFAULT_CERTIFICADO_ISO_9001)
            .garantiaDefeitoFabrica(DEFAULT_GARANTIA_DEFEITO_FABRICA)
            .transporte(DEFAULT_TRANSPORTE)
            .qualidade(DEFAULT_QUALIDADE)
            .pagamentoPrazo(DEFAULT_PAGAMENTO_PRAZO)
            .metodosPagamento(DEFAULT_METODOS_PAGAMENTO)
            .classificacao(DEFAULT_CLASSIFICACAO)
            .descricao(DEFAULT_DESCRICAO)
            .ativo(DEFAULT_ATIVO)
            .numero(DEFAULT_NUMERO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        fornecedor.setPessoa(pessoa);
        return fornecedor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornecedor createUpdatedEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor()
            .certificadoISO9001(UPDATED_CERTIFICADO_ISO_9001)
            .garantiaDefeitoFabrica(UPDATED_GARANTIA_DEFEITO_FABRICA)
            .transporte(UPDATED_TRANSPORTE)
            .qualidade(UPDATED_QUALIDADE)
            .pagamentoPrazo(UPDATED_PAGAMENTO_PRAZO)
            .metodosPagamento(UPDATED_METODOS_PAGAMENTO)
            .classificacao(UPDATED_CLASSIFICACAO)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .numero(UPDATED_NUMERO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        fornecedor.setPessoa(pessoa);
        return fornecedor;
    }

    @BeforeEach
    public void initTest() {
        fornecedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createFornecedor() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);
        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isCreated());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate + 1);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.isCertificadoISO9001()).isEqualTo(DEFAULT_CERTIFICADO_ISO_9001);
        assertThat(testFornecedor.isGarantiaDefeitoFabrica()).isEqualTo(DEFAULT_GARANTIA_DEFEITO_FABRICA);
        assertThat(testFornecedor.isTransporte()).isEqualTo(DEFAULT_TRANSPORTE);
        assertThat(testFornecedor.getQualidade()).isEqualTo(DEFAULT_QUALIDADE);
        assertThat(testFornecedor.isPagamentoPrazo()).isEqualTo(DEFAULT_PAGAMENTO_PRAZO);
        assertThat(testFornecedor.getMetodosPagamento()).isEqualTo(DEFAULT_METODOS_PAGAMENTO);
        assertThat(testFornecedor.getClassificacao()).isEqualTo(DEFAULT_CLASSIFICACAO);
        assertThat(testFornecedor.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testFornecedor.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testFornecedor.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    public void createFornecedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // Create the Fornecedor with an existing ID
        fornecedor.setId(1L);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClassificacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setClassificacao(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setNumero(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFornecedors() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList
        restFornecedorMockMvc.perform(get("/api/fornecedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].certificadoISO9001").value(hasItem(DEFAULT_CERTIFICADO_ISO_9001.booleanValue())))
            .andExpect(jsonPath("$.[*].garantiaDefeitoFabrica").value(hasItem(DEFAULT_GARANTIA_DEFEITO_FABRICA.booleanValue())))
            .andExpect(jsonPath("$.[*].transporte").value(hasItem(DEFAULT_TRANSPORTE.booleanValue())))
            .andExpect(jsonPath("$.[*].qualidade").value(hasItem(DEFAULT_QUALIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].pagamentoPrazo").value(hasItem(DEFAULT_PAGAMENTO_PRAZO.booleanValue())))
            .andExpect(jsonPath("$.[*].metodosPagamento").value(hasItem(DEFAULT_METODOS_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].classificacao").value(hasItem(DEFAULT_CLASSIFICACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }
    
    @Test
    @Transactional
    public void getFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get the fornecedor
        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", fornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedor.getId().intValue()))
            .andExpect(jsonPath("$.certificadoISO9001").value(DEFAULT_CERTIFICADO_ISO_9001.booleanValue()))
            .andExpect(jsonPath("$.garantiaDefeitoFabrica").value(DEFAULT_GARANTIA_DEFEITO_FABRICA.booleanValue()))
            .andExpect(jsonPath("$.transporte").value(DEFAULT_TRANSPORTE.booleanValue()))
            .andExpect(jsonPath("$.qualidade").value(DEFAULT_QUALIDADE.doubleValue()))
            .andExpect(jsonPath("$.pagamentoPrazo").value(DEFAULT_PAGAMENTO_PRAZO.booleanValue()))
            .andExpect(jsonPath("$.metodosPagamento").value(DEFAULT_METODOS_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.classificacao").value(DEFAULT_CLASSIFICACAO.doubleValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }


    @Test
    @Transactional
    public void getFornecedorsByIdFiltering() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        Long id = fornecedor.getId();

        defaultFornecedorShouldBeFound("id.equals=" + id);
        defaultFornecedorShouldNotBeFound("id.notEquals=" + id);

        defaultFornecedorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFornecedorShouldNotBeFound("id.greaterThan=" + id);

        defaultFornecedorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFornecedorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFornecedorsByCertificadoISO9001IsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where certificadoISO9001 equals to DEFAULT_CERTIFICADO_ISO_9001
        defaultFornecedorShouldBeFound("certificadoISO9001.equals=" + DEFAULT_CERTIFICADO_ISO_9001);

        // Get all the fornecedorList where certificadoISO9001 equals to UPDATED_CERTIFICADO_ISO_9001
        defaultFornecedorShouldNotBeFound("certificadoISO9001.equals=" + UPDATED_CERTIFICADO_ISO_9001);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByCertificadoISO9001IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where certificadoISO9001 not equals to DEFAULT_CERTIFICADO_ISO_9001
        defaultFornecedorShouldNotBeFound("certificadoISO9001.notEquals=" + DEFAULT_CERTIFICADO_ISO_9001);

        // Get all the fornecedorList where certificadoISO9001 not equals to UPDATED_CERTIFICADO_ISO_9001
        defaultFornecedorShouldBeFound("certificadoISO9001.notEquals=" + UPDATED_CERTIFICADO_ISO_9001);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByCertificadoISO9001IsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where certificadoISO9001 in DEFAULT_CERTIFICADO_ISO_9001 or UPDATED_CERTIFICADO_ISO_9001
        defaultFornecedorShouldBeFound("certificadoISO9001.in=" + DEFAULT_CERTIFICADO_ISO_9001 + "," + UPDATED_CERTIFICADO_ISO_9001);

        // Get all the fornecedorList where certificadoISO9001 equals to UPDATED_CERTIFICADO_ISO_9001
        defaultFornecedorShouldNotBeFound("certificadoISO9001.in=" + UPDATED_CERTIFICADO_ISO_9001);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByCertificadoISO9001IsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where certificadoISO9001 is not null
        defaultFornecedorShouldBeFound("certificadoISO9001.specified=true");

        // Get all the fornecedorList where certificadoISO9001 is null
        defaultFornecedorShouldNotBeFound("certificadoISO9001.specified=false");
    }

    @Test
    @Transactional
    public void getAllFornecedorsByGarantiaDefeitoFabricaIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where garantiaDefeitoFabrica equals to DEFAULT_GARANTIA_DEFEITO_FABRICA
        defaultFornecedorShouldBeFound("garantiaDefeitoFabrica.equals=" + DEFAULT_GARANTIA_DEFEITO_FABRICA);

        // Get all the fornecedorList where garantiaDefeitoFabrica equals to UPDATED_GARANTIA_DEFEITO_FABRICA
        defaultFornecedorShouldNotBeFound("garantiaDefeitoFabrica.equals=" + UPDATED_GARANTIA_DEFEITO_FABRICA);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByGarantiaDefeitoFabricaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where garantiaDefeitoFabrica not equals to DEFAULT_GARANTIA_DEFEITO_FABRICA
        defaultFornecedorShouldNotBeFound("garantiaDefeitoFabrica.notEquals=" + DEFAULT_GARANTIA_DEFEITO_FABRICA);

        // Get all the fornecedorList where garantiaDefeitoFabrica not equals to UPDATED_GARANTIA_DEFEITO_FABRICA
        defaultFornecedorShouldBeFound("garantiaDefeitoFabrica.notEquals=" + UPDATED_GARANTIA_DEFEITO_FABRICA);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByGarantiaDefeitoFabricaIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where garantiaDefeitoFabrica in DEFAULT_GARANTIA_DEFEITO_FABRICA or UPDATED_GARANTIA_DEFEITO_FABRICA
        defaultFornecedorShouldBeFound("garantiaDefeitoFabrica.in=" + DEFAULT_GARANTIA_DEFEITO_FABRICA + "," + UPDATED_GARANTIA_DEFEITO_FABRICA);

        // Get all the fornecedorList where garantiaDefeitoFabrica equals to UPDATED_GARANTIA_DEFEITO_FABRICA
        defaultFornecedorShouldNotBeFound("garantiaDefeitoFabrica.in=" + UPDATED_GARANTIA_DEFEITO_FABRICA);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByGarantiaDefeitoFabricaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where garantiaDefeitoFabrica is not null
        defaultFornecedorShouldBeFound("garantiaDefeitoFabrica.specified=true");

        // Get all the fornecedorList where garantiaDefeitoFabrica is null
        defaultFornecedorShouldNotBeFound("garantiaDefeitoFabrica.specified=false");
    }

    @Test
    @Transactional
    public void getAllFornecedorsByTransporteIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where transporte equals to DEFAULT_TRANSPORTE
        defaultFornecedorShouldBeFound("transporte.equals=" + DEFAULT_TRANSPORTE);

        // Get all the fornecedorList where transporte equals to UPDATED_TRANSPORTE
        defaultFornecedorShouldNotBeFound("transporte.equals=" + UPDATED_TRANSPORTE);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByTransporteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where transporte not equals to DEFAULT_TRANSPORTE
        defaultFornecedorShouldNotBeFound("transporte.notEquals=" + DEFAULT_TRANSPORTE);

        // Get all the fornecedorList where transporte not equals to UPDATED_TRANSPORTE
        defaultFornecedorShouldBeFound("transporte.notEquals=" + UPDATED_TRANSPORTE);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByTransporteIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where transporte in DEFAULT_TRANSPORTE or UPDATED_TRANSPORTE
        defaultFornecedorShouldBeFound("transporte.in=" + DEFAULT_TRANSPORTE + "," + UPDATED_TRANSPORTE);

        // Get all the fornecedorList where transporte equals to UPDATED_TRANSPORTE
        defaultFornecedorShouldNotBeFound("transporte.in=" + UPDATED_TRANSPORTE);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByTransporteIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where transporte is not null
        defaultFornecedorShouldBeFound("transporte.specified=true");

        // Get all the fornecedorList where transporte is null
        defaultFornecedorShouldNotBeFound("transporte.specified=false");
    }

    @Test
    @Transactional
    public void getAllFornecedorsByQualidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where qualidade equals to DEFAULT_QUALIDADE
        defaultFornecedorShouldBeFound("qualidade.equals=" + DEFAULT_QUALIDADE);

        // Get all the fornecedorList where qualidade equals to UPDATED_QUALIDADE
        defaultFornecedorShouldNotBeFound("qualidade.equals=" + UPDATED_QUALIDADE);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByQualidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where qualidade not equals to DEFAULT_QUALIDADE
        defaultFornecedorShouldNotBeFound("qualidade.notEquals=" + DEFAULT_QUALIDADE);

        // Get all the fornecedorList where qualidade not equals to UPDATED_QUALIDADE
        defaultFornecedorShouldBeFound("qualidade.notEquals=" + UPDATED_QUALIDADE);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByQualidadeIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where qualidade in DEFAULT_QUALIDADE or UPDATED_QUALIDADE
        defaultFornecedorShouldBeFound("qualidade.in=" + DEFAULT_QUALIDADE + "," + UPDATED_QUALIDADE);

        // Get all the fornecedorList where qualidade equals to UPDATED_QUALIDADE
        defaultFornecedorShouldNotBeFound("qualidade.in=" + UPDATED_QUALIDADE);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByQualidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where qualidade is not null
        defaultFornecedorShouldBeFound("qualidade.specified=true");

        // Get all the fornecedorList where qualidade is null
        defaultFornecedorShouldNotBeFound("qualidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllFornecedorsByQualidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where qualidade is greater than or equal to DEFAULT_QUALIDADE
        defaultFornecedorShouldBeFound("qualidade.greaterThanOrEqual=" + DEFAULT_QUALIDADE);

        // Get all the fornecedorList where qualidade is greater than or equal to (DEFAULT_QUALIDADE + 1)
        defaultFornecedorShouldNotBeFound("qualidade.greaterThanOrEqual=" + (DEFAULT_QUALIDADE + 1));
    }

    @Test
    @Transactional
    public void getAllFornecedorsByQualidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where qualidade is less than or equal to DEFAULT_QUALIDADE
        defaultFornecedorShouldBeFound("qualidade.lessThanOrEqual=" + DEFAULT_QUALIDADE);

        // Get all the fornecedorList where qualidade is less than or equal to SMALLER_QUALIDADE
        defaultFornecedorShouldNotBeFound("qualidade.lessThanOrEqual=" + SMALLER_QUALIDADE);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByQualidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where qualidade is less than DEFAULT_QUALIDADE
        defaultFornecedorShouldNotBeFound("qualidade.lessThan=" + DEFAULT_QUALIDADE);

        // Get all the fornecedorList where qualidade is less than (DEFAULT_QUALIDADE + 1)
        defaultFornecedorShouldBeFound("qualidade.lessThan=" + (DEFAULT_QUALIDADE + 1));
    }

    @Test
    @Transactional
    public void getAllFornecedorsByQualidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where qualidade is greater than DEFAULT_QUALIDADE
        defaultFornecedorShouldNotBeFound("qualidade.greaterThan=" + DEFAULT_QUALIDADE);

        // Get all the fornecedorList where qualidade is greater than SMALLER_QUALIDADE
        defaultFornecedorShouldBeFound("qualidade.greaterThan=" + SMALLER_QUALIDADE);
    }


    @Test
    @Transactional
    public void getAllFornecedorsByPagamentoPrazoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where pagamentoPrazo equals to DEFAULT_PAGAMENTO_PRAZO
        defaultFornecedorShouldBeFound("pagamentoPrazo.equals=" + DEFAULT_PAGAMENTO_PRAZO);

        // Get all the fornecedorList where pagamentoPrazo equals to UPDATED_PAGAMENTO_PRAZO
        defaultFornecedorShouldNotBeFound("pagamentoPrazo.equals=" + UPDATED_PAGAMENTO_PRAZO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByPagamentoPrazoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where pagamentoPrazo not equals to DEFAULT_PAGAMENTO_PRAZO
        defaultFornecedorShouldNotBeFound("pagamentoPrazo.notEquals=" + DEFAULT_PAGAMENTO_PRAZO);

        // Get all the fornecedorList where pagamentoPrazo not equals to UPDATED_PAGAMENTO_PRAZO
        defaultFornecedorShouldBeFound("pagamentoPrazo.notEquals=" + UPDATED_PAGAMENTO_PRAZO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByPagamentoPrazoIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where pagamentoPrazo in DEFAULT_PAGAMENTO_PRAZO or UPDATED_PAGAMENTO_PRAZO
        defaultFornecedorShouldBeFound("pagamentoPrazo.in=" + DEFAULT_PAGAMENTO_PRAZO + "," + UPDATED_PAGAMENTO_PRAZO);

        // Get all the fornecedorList where pagamentoPrazo equals to UPDATED_PAGAMENTO_PRAZO
        defaultFornecedorShouldNotBeFound("pagamentoPrazo.in=" + UPDATED_PAGAMENTO_PRAZO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByPagamentoPrazoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where pagamentoPrazo is not null
        defaultFornecedorShouldBeFound("pagamentoPrazo.specified=true");

        // Get all the fornecedorList where pagamentoPrazo is null
        defaultFornecedorShouldNotBeFound("pagamentoPrazo.specified=false");
    }

    @Test
    @Transactional
    public void getAllFornecedorsByClassificacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where classificacao equals to DEFAULT_CLASSIFICACAO
        defaultFornecedorShouldBeFound("classificacao.equals=" + DEFAULT_CLASSIFICACAO);

        // Get all the fornecedorList where classificacao equals to UPDATED_CLASSIFICACAO
        defaultFornecedorShouldNotBeFound("classificacao.equals=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByClassificacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where classificacao not equals to DEFAULT_CLASSIFICACAO
        defaultFornecedorShouldNotBeFound("classificacao.notEquals=" + DEFAULT_CLASSIFICACAO);

        // Get all the fornecedorList where classificacao not equals to UPDATED_CLASSIFICACAO
        defaultFornecedorShouldBeFound("classificacao.notEquals=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByClassificacaoIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where classificacao in DEFAULT_CLASSIFICACAO or UPDATED_CLASSIFICACAO
        defaultFornecedorShouldBeFound("classificacao.in=" + DEFAULT_CLASSIFICACAO + "," + UPDATED_CLASSIFICACAO);

        // Get all the fornecedorList where classificacao equals to UPDATED_CLASSIFICACAO
        defaultFornecedorShouldNotBeFound("classificacao.in=" + UPDATED_CLASSIFICACAO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByClassificacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where classificacao is not null
        defaultFornecedorShouldBeFound("classificacao.specified=true");

        // Get all the fornecedorList where classificacao is null
        defaultFornecedorShouldNotBeFound("classificacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllFornecedorsByClassificacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where classificacao is greater than or equal to DEFAULT_CLASSIFICACAO
        defaultFornecedorShouldBeFound("classificacao.greaterThanOrEqual=" + DEFAULT_CLASSIFICACAO);

        // Get all the fornecedorList where classificacao is greater than or equal to (DEFAULT_CLASSIFICACAO + 1)
        defaultFornecedorShouldNotBeFound("classificacao.greaterThanOrEqual=" + (DEFAULT_CLASSIFICACAO + 1));
    }

    @Test
    @Transactional
    public void getAllFornecedorsByClassificacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where classificacao is less than or equal to DEFAULT_CLASSIFICACAO
        defaultFornecedorShouldBeFound("classificacao.lessThanOrEqual=" + DEFAULT_CLASSIFICACAO);

        // Get all the fornecedorList where classificacao is less than or equal to SMALLER_CLASSIFICACAO
        defaultFornecedorShouldNotBeFound("classificacao.lessThanOrEqual=" + SMALLER_CLASSIFICACAO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByClassificacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where classificacao is less than DEFAULT_CLASSIFICACAO
        defaultFornecedorShouldNotBeFound("classificacao.lessThan=" + DEFAULT_CLASSIFICACAO);

        // Get all the fornecedorList where classificacao is less than (DEFAULT_CLASSIFICACAO + 1)
        defaultFornecedorShouldBeFound("classificacao.lessThan=" + (DEFAULT_CLASSIFICACAO + 1));
    }

    @Test
    @Transactional
    public void getAllFornecedorsByClassificacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where classificacao is greater than DEFAULT_CLASSIFICACAO
        defaultFornecedorShouldNotBeFound("classificacao.greaterThan=" + DEFAULT_CLASSIFICACAO);

        // Get all the fornecedorList where classificacao is greater than SMALLER_CLASSIFICACAO
        defaultFornecedorShouldBeFound("classificacao.greaterThan=" + SMALLER_CLASSIFICACAO);
    }


    @Test
    @Transactional
    public void getAllFornecedorsByAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where ativo equals to DEFAULT_ATIVO
        defaultFornecedorShouldBeFound("ativo.equals=" + DEFAULT_ATIVO);

        // Get all the fornecedorList where ativo equals to UPDATED_ATIVO
        defaultFornecedorShouldNotBeFound("ativo.equals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where ativo not equals to DEFAULT_ATIVO
        defaultFornecedorShouldNotBeFound("ativo.notEquals=" + DEFAULT_ATIVO);

        // Get all the fornecedorList where ativo not equals to UPDATED_ATIVO
        defaultFornecedorShouldBeFound("ativo.notEquals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where ativo in DEFAULT_ATIVO or UPDATED_ATIVO
        defaultFornecedorShouldBeFound("ativo.in=" + DEFAULT_ATIVO + "," + UPDATED_ATIVO);

        // Get all the fornecedorList where ativo equals to UPDATED_ATIVO
        defaultFornecedorShouldNotBeFound("ativo.in=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where ativo is not null
        defaultFornecedorShouldBeFound("ativo.specified=true");

        // Get all the fornecedorList where ativo is null
        defaultFornecedorShouldNotBeFound("ativo.specified=false");
    }

    @Test
    @Transactional
    public void getAllFornecedorsByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where numero equals to DEFAULT_NUMERO
        defaultFornecedorShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the fornecedorList where numero equals to UPDATED_NUMERO
        defaultFornecedorShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where numero not equals to DEFAULT_NUMERO
        defaultFornecedorShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the fornecedorList where numero not equals to UPDATED_NUMERO
        defaultFornecedorShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultFornecedorShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the fornecedorList where numero equals to UPDATED_NUMERO
        defaultFornecedorShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where numero is not null
        defaultFornecedorShouldBeFound("numero.specified=true");

        // Get all the fornecedorList where numero is null
        defaultFornecedorShouldNotBeFound("numero.specified=false");
    }
                @Test
    @Transactional
    public void getAllFornecedorsByNumeroContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where numero contains DEFAULT_NUMERO
        defaultFornecedorShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the fornecedorList where numero contains UPDATED_NUMERO
        defaultFornecedorShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllFornecedorsByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList where numero does not contain DEFAULT_NUMERO
        defaultFornecedorShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the fornecedorList where numero does not contain UPDATED_NUMERO
        defaultFornecedorShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }


    @Test
    @Transactional
    public void getAllFornecedorsByPessoaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Pessoa pessoa = fornecedor.getPessoa();
        fornecedorRepository.saveAndFlush(fornecedor);
        Long pessoaId = pessoa.getId();

        // Get all the fornecedorList where pessoa equals to pessoaId
        defaultFornecedorShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the fornecedorList where pessoa equals to pessoaId + 1
        defaultFornecedorShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }


    @Test
    @Transactional
    public void getAllFornecedorsByItemCompraIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);
        ItemCompra itemCompra = ItemCompraResourceIT.createEntity(em);
        em.persist(itemCompra);
        em.flush();
        fornecedor.addItemCompra(itemCompra);
        fornecedorRepository.saveAndFlush(fornecedor);
        Long itemCompraId = itemCompra.getId();

        // Get all the fornecedorList where itemCompra equals to itemCompraId
        defaultFornecedorShouldBeFound("itemCompraId.equals=" + itemCompraId);

        // Get all the fornecedorList where itemCompra equals to itemCompraId + 1
        defaultFornecedorShouldNotBeFound("itemCompraId.equals=" + (itemCompraId + 1));
    }


    @Test
    @Transactional
    public void getAllFornecedorsByContaIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);
        Conta conta = ContaResourceIT.createEntity(em);
        em.persist(conta);
        em.flush();
        fornecedor.setConta(conta);
        fornecedorRepository.saveAndFlush(fornecedor);
        Long contaId = conta.getId();

        // Get all the fornecedorList where conta equals to contaId
        defaultFornecedorShouldBeFound("contaId.equals=" + contaId);

        // Get all the fornecedorList where conta equals to contaId + 1
        defaultFornecedorShouldNotBeFound("contaId.equals=" + (contaId + 1));
    }


    @Test
    @Transactional
    public void getAllFornecedorsByProdutoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);
        Produto produto = ProdutoResourceIT.createEntity(em);
        em.persist(produto);
        em.flush();
        fornecedor.addProduto(produto);
        fornecedorRepository.saveAndFlush(fornecedor);
        Long produtoId = produto.getId();

        // Get all the fornecedorList where produto equals to produtoId
        defaultFornecedorShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the fornecedorList where produto equals to produtoId + 1
        defaultFornecedorShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFornecedorShouldBeFound(String filter) throws Exception {
        restFornecedorMockMvc.perform(get("/api/fornecedors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].certificadoISO9001").value(hasItem(DEFAULT_CERTIFICADO_ISO_9001.booleanValue())))
            .andExpect(jsonPath("$.[*].garantiaDefeitoFabrica").value(hasItem(DEFAULT_GARANTIA_DEFEITO_FABRICA.booleanValue())))
            .andExpect(jsonPath("$.[*].transporte").value(hasItem(DEFAULT_TRANSPORTE.booleanValue())))
            .andExpect(jsonPath("$.[*].qualidade").value(hasItem(DEFAULT_QUALIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].pagamentoPrazo").value(hasItem(DEFAULT_PAGAMENTO_PRAZO.booleanValue())))
            .andExpect(jsonPath("$.[*].metodosPagamento").value(hasItem(DEFAULT_METODOS_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].classificacao").value(hasItem(DEFAULT_CLASSIFICACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));

        // Check, that the count call also returns 1
        restFornecedorMockMvc.perform(get("/api/fornecedors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFornecedorShouldNotBeFound(String filter) throws Exception {
        restFornecedorMockMvc.perform(get("/api/fornecedors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFornecedorMockMvc.perform(get("/api/fornecedors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFornecedor() throws Exception {
        // Get the fornecedor
        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Update the fornecedor
        Fornecedor updatedFornecedor = fornecedorRepository.findById(fornecedor.getId()).get();
        // Disconnect from session so that the updates on updatedFornecedor are not directly saved in db
        em.detach(updatedFornecedor);
        updatedFornecedor
            .certificadoISO9001(UPDATED_CERTIFICADO_ISO_9001)
            .garantiaDefeitoFabrica(UPDATED_GARANTIA_DEFEITO_FABRICA)
            .transporte(UPDATED_TRANSPORTE)
            .qualidade(UPDATED_QUALIDADE)
            .pagamentoPrazo(UPDATED_PAGAMENTO_PRAZO)
            .metodosPagamento(UPDATED_METODOS_PAGAMENTO)
            .classificacao(UPDATED_CLASSIFICACAO)
            .descricao(UPDATED_DESCRICAO)
            .ativo(UPDATED_ATIVO)
            .numero(UPDATED_NUMERO);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(updatedFornecedor);

        restFornecedorMockMvc.perform(put("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.isCertificadoISO9001()).isEqualTo(UPDATED_CERTIFICADO_ISO_9001);
        assertThat(testFornecedor.isGarantiaDefeitoFabrica()).isEqualTo(UPDATED_GARANTIA_DEFEITO_FABRICA);
        assertThat(testFornecedor.isTransporte()).isEqualTo(UPDATED_TRANSPORTE);
        assertThat(testFornecedor.getQualidade()).isEqualTo(UPDATED_QUALIDADE);
        assertThat(testFornecedor.isPagamentoPrazo()).isEqualTo(UPDATED_PAGAMENTO_PRAZO);
        assertThat(testFornecedor.getMetodosPagamento()).isEqualTo(UPDATED_METODOS_PAGAMENTO);
        assertThat(testFornecedor.getClassificacao()).isEqualTo(UPDATED_CLASSIFICACAO);
        assertThat(testFornecedor.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testFornecedor.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testFornecedor.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void updateNonExistingFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorMockMvc.perform(put("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeDelete = fornecedorRepository.findAll().size();

        // Delete the fornecedor
        restFornecedorMockMvc.perform(delete("/api/fornecedors/{id}", fornecedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
