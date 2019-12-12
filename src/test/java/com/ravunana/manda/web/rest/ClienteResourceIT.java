package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.Cliente;
import com.ravunana.manda.domain.Pessoa;
import com.ravunana.manda.domain.Venda;
import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.repository.ClienteRepository;
import com.ravunana.manda.service.ClienteService;
import com.ravunana.manda.service.dto.ClienteDTO;
import com.ravunana.manda.service.mapper.ClienteMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.ClienteCriteria;
import com.ravunana.manda.service.ClienteQueryService;

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
 * Integration tests for the {@link ClienteResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ClienteResourceIT {

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final String DEFAULT_PERFIL_PROFISSIONAL = "AAAAAAAAAA";
    private static final String UPDATED_PERFIL_PROFISSIONAL = "BBBBBBBBBB";

    private static final Double DEFAULT_SATISFACAO = 1D;
    private static final Double UPDATED_SATISFACAO = 2D;
    private static final Double SMALLER_SATISFACAO = 1D - 1D;

    private static final Double DEFAULT_FREQUENCIA = 1D;
    private static final Double UPDATED_FREQUENCIA = 2D;
    private static final Double SMALLER_FREQUENCIA = 1D - 1D;

    private static final String DEFAULT_CANAL_USADO = "AAAAAAAAAA";
    private static final String UPDATED_CANAL_USADO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTOFACTURACAO = false;
    private static final Boolean UPDATED_AUTOFACTURACAO = true;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteQueryService clienteQueryService;

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

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteResource clienteResource = new ClienteResource(clienteService, clienteQueryService);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
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
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .ativo(DEFAULT_ATIVO)
            .perfilProfissional(DEFAULT_PERFIL_PROFISSIONAL)
            .satisfacao(DEFAULT_SATISFACAO)
            .frequencia(DEFAULT_FREQUENCIA)
            .canalUsado(DEFAULT_CANAL_USADO)
            .numero(DEFAULT_NUMERO)
            .autofacturacao(DEFAULT_AUTOFACTURACAO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        cliente.setPessoa(pessoa);
        return cliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createUpdatedEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .ativo(UPDATED_ATIVO)
            .perfilProfissional(UPDATED_PERFIL_PROFISSIONAL)
            .satisfacao(UPDATED_SATISFACAO)
            .frequencia(UPDATED_FREQUENCIA)
            .canalUsado(UPDATED_CANAL_USADO)
            .numero(UPDATED_NUMERO)
            .autofacturacao(UPDATED_AUTOFACTURACAO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        cliente.setPessoa(pessoa);
        return cliente;
    }

    @BeforeEach
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.isAtivo()).isEqualTo(DEFAULT_ATIVO);
        assertThat(testCliente.getPerfilProfissional()).isEqualTo(DEFAULT_PERFIL_PROFISSIONAL);
        assertThat(testCliente.getSatisfacao()).isEqualTo(DEFAULT_SATISFACAO);
        assertThat(testCliente.getFrequencia()).isEqualTo(DEFAULT_FREQUENCIA);
        assertThat(testCliente.getCanalUsado()).isEqualTo(DEFAULT_CANAL_USADO);
        assertThat(testCliente.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCliente.isAutofacturacao()).isEqualTo(DEFAULT_AUTOFACTURACAO);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNumero(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].perfilProfissional").value(hasItem(DEFAULT_PERFIL_PROFISSIONAL.toString())))
            .andExpect(jsonPath("$.[*].satisfacao").value(hasItem(DEFAULT_SATISFACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].frequencia").value(hasItem(DEFAULT_FREQUENCIA.doubleValue())))
            .andExpect(jsonPath("$.[*].canalUsado").value(hasItem(DEFAULT_CANAL_USADO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].autofacturacao").value(hasItem(DEFAULT_AUTOFACTURACAO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.perfilProfissional").value(DEFAULT_PERFIL_PROFISSIONAL.toString()))
            .andExpect(jsonPath("$.satisfacao").value(DEFAULT_SATISFACAO.doubleValue()))
            .andExpect(jsonPath("$.frequencia").value(DEFAULT_FREQUENCIA.doubleValue()))
            .andExpect(jsonPath("$.canalUsado").value(DEFAULT_CANAL_USADO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.autofacturacao").value(DEFAULT_AUTOFACTURACAO.booleanValue()));
    }


    @Test
    @Transactional
    public void getClientesByIdFiltering() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        Long id = cliente.getId();

        defaultClienteShouldBeFound("id.equals=" + id);
        defaultClienteShouldNotBeFound("id.notEquals=" + id);

        defaultClienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClienteShouldNotBeFound("id.greaterThan=" + id);

        defaultClienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClienteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClientesByAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where ativo equals to DEFAULT_ATIVO
        defaultClienteShouldBeFound("ativo.equals=" + DEFAULT_ATIVO);

        // Get all the clienteList where ativo equals to UPDATED_ATIVO
        defaultClienteShouldNotBeFound("ativo.equals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllClientesByAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where ativo not equals to DEFAULT_ATIVO
        defaultClienteShouldNotBeFound("ativo.notEquals=" + DEFAULT_ATIVO);

        // Get all the clienteList where ativo not equals to UPDATED_ATIVO
        defaultClienteShouldBeFound("ativo.notEquals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllClientesByAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where ativo in DEFAULT_ATIVO or UPDATED_ATIVO
        defaultClienteShouldBeFound("ativo.in=" + DEFAULT_ATIVO + "," + UPDATED_ATIVO);

        // Get all the clienteList where ativo equals to UPDATED_ATIVO
        defaultClienteShouldNotBeFound("ativo.in=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void getAllClientesByAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where ativo is not null
        defaultClienteShouldBeFound("ativo.specified=true");

        // Get all the clienteList where ativo is null
        defaultClienteShouldNotBeFound("ativo.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesBySatisfacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where satisfacao equals to DEFAULT_SATISFACAO
        defaultClienteShouldBeFound("satisfacao.equals=" + DEFAULT_SATISFACAO);

        // Get all the clienteList where satisfacao equals to UPDATED_SATISFACAO
        defaultClienteShouldNotBeFound("satisfacao.equals=" + UPDATED_SATISFACAO);
    }

    @Test
    @Transactional
    public void getAllClientesBySatisfacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where satisfacao not equals to DEFAULT_SATISFACAO
        defaultClienteShouldNotBeFound("satisfacao.notEquals=" + DEFAULT_SATISFACAO);

        // Get all the clienteList where satisfacao not equals to UPDATED_SATISFACAO
        defaultClienteShouldBeFound("satisfacao.notEquals=" + UPDATED_SATISFACAO);
    }

    @Test
    @Transactional
    public void getAllClientesBySatisfacaoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where satisfacao in DEFAULT_SATISFACAO or UPDATED_SATISFACAO
        defaultClienteShouldBeFound("satisfacao.in=" + DEFAULT_SATISFACAO + "," + UPDATED_SATISFACAO);

        // Get all the clienteList where satisfacao equals to UPDATED_SATISFACAO
        defaultClienteShouldNotBeFound("satisfacao.in=" + UPDATED_SATISFACAO);
    }

    @Test
    @Transactional
    public void getAllClientesBySatisfacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where satisfacao is not null
        defaultClienteShouldBeFound("satisfacao.specified=true");

        // Get all the clienteList where satisfacao is null
        defaultClienteShouldNotBeFound("satisfacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesBySatisfacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where satisfacao is greater than or equal to DEFAULT_SATISFACAO
        defaultClienteShouldBeFound("satisfacao.greaterThanOrEqual=" + DEFAULT_SATISFACAO);

        // Get all the clienteList where satisfacao is greater than or equal to UPDATED_SATISFACAO
        defaultClienteShouldNotBeFound("satisfacao.greaterThanOrEqual=" + UPDATED_SATISFACAO);
    }

    @Test
    @Transactional
    public void getAllClientesBySatisfacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where satisfacao is less than or equal to DEFAULT_SATISFACAO
        defaultClienteShouldBeFound("satisfacao.lessThanOrEqual=" + DEFAULT_SATISFACAO);

        // Get all the clienteList where satisfacao is less than or equal to SMALLER_SATISFACAO
        defaultClienteShouldNotBeFound("satisfacao.lessThanOrEqual=" + SMALLER_SATISFACAO);
    }

    @Test
    @Transactional
    public void getAllClientesBySatisfacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where satisfacao is less than DEFAULT_SATISFACAO
        defaultClienteShouldNotBeFound("satisfacao.lessThan=" + DEFAULT_SATISFACAO);

        // Get all the clienteList where satisfacao is less than UPDATED_SATISFACAO
        defaultClienteShouldBeFound("satisfacao.lessThan=" + UPDATED_SATISFACAO);
    }

    @Test
    @Transactional
    public void getAllClientesBySatisfacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where satisfacao is greater than DEFAULT_SATISFACAO
        defaultClienteShouldNotBeFound("satisfacao.greaterThan=" + DEFAULT_SATISFACAO);

        // Get all the clienteList where satisfacao is greater than SMALLER_SATISFACAO
        defaultClienteShouldBeFound("satisfacao.greaterThan=" + SMALLER_SATISFACAO);
    }


    @Test
    @Transactional
    public void getAllClientesByFrequenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where frequencia equals to DEFAULT_FREQUENCIA
        defaultClienteShouldBeFound("frequencia.equals=" + DEFAULT_FREQUENCIA);

        // Get all the clienteList where frequencia equals to UPDATED_FREQUENCIA
        defaultClienteShouldNotBeFound("frequencia.equals=" + UPDATED_FREQUENCIA);
    }

    @Test
    @Transactional
    public void getAllClientesByFrequenciaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where frequencia not equals to DEFAULT_FREQUENCIA
        defaultClienteShouldNotBeFound("frequencia.notEquals=" + DEFAULT_FREQUENCIA);

        // Get all the clienteList where frequencia not equals to UPDATED_FREQUENCIA
        defaultClienteShouldBeFound("frequencia.notEquals=" + UPDATED_FREQUENCIA);
    }

    @Test
    @Transactional
    public void getAllClientesByFrequenciaIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where frequencia in DEFAULT_FREQUENCIA or UPDATED_FREQUENCIA
        defaultClienteShouldBeFound("frequencia.in=" + DEFAULT_FREQUENCIA + "," + UPDATED_FREQUENCIA);

        // Get all the clienteList where frequencia equals to UPDATED_FREQUENCIA
        defaultClienteShouldNotBeFound("frequencia.in=" + UPDATED_FREQUENCIA);
    }

    @Test
    @Transactional
    public void getAllClientesByFrequenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where frequencia is not null
        defaultClienteShouldBeFound("frequencia.specified=true");

        // Get all the clienteList where frequencia is null
        defaultClienteShouldNotBeFound("frequencia.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByFrequenciaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where frequencia is greater than or equal to DEFAULT_FREQUENCIA
        defaultClienteShouldBeFound("frequencia.greaterThanOrEqual=" + DEFAULT_FREQUENCIA);

        // Get all the clienteList where frequencia is greater than or equal to UPDATED_FREQUENCIA
        defaultClienteShouldNotBeFound("frequencia.greaterThanOrEqual=" + UPDATED_FREQUENCIA);
    }

    @Test
    @Transactional
    public void getAllClientesByFrequenciaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where frequencia is less than or equal to DEFAULT_FREQUENCIA
        defaultClienteShouldBeFound("frequencia.lessThanOrEqual=" + DEFAULT_FREQUENCIA);

        // Get all the clienteList where frequencia is less than or equal to SMALLER_FREQUENCIA
        defaultClienteShouldNotBeFound("frequencia.lessThanOrEqual=" + SMALLER_FREQUENCIA);
    }

    @Test
    @Transactional
    public void getAllClientesByFrequenciaIsLessThanSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where frequencia is less than DEFAULT_FREQUENCIA
        defaultClienteShouldNotBeFound("frequencia.lessThan=" + DEFAULT_FREQUENCIA);

        // Get all the clienteList where frequencia is less than UPDATED_FREQUENCIA
        defaultClienteShouldBeFound("frequencia.lessThan=" + UPDATED_FREQUENCIA);
    }

    @Test
    @Transactional
    public void getAllClientesByFrequenciaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where frequencia is greater than DEFAULT_FREQUENCIA
        defaultClienteShouldNotBeFound("frequencia.greaterThan=" + DEFAULT_FREQUENCIA);

        // Get all the clienteList where frequencia is greater than SMALLER_FREQUENCIA
        defaultClienteShouldBeFound("frequencia.greaterThan=" + SMALLER_FREQUENCIA);
    }


    @Test
    @Transactional
    public void getAllClientesByCanalUsadoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where canalUsado equals to DEFAULT_CANAL_USADO
        defaultClienteShouldBeFound("canalUsado.equals=" + DEFAULT_CANAL_USADO);

        // Get all the clienteList where canalUsado equals to UPDATED_CANAL_USADO
        defaultClienteShouldNotBeFound("canalUsado.equals=" + UPDATED_CANAL_USADO);
    }

    @Test
    @Transactional
    public void getAllClientesByCanalUsadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where canalUsado not equals to DEFAULT_CANAL_USADO
        defaultClienteShouldNotBeFound("canalUsado.notEquals=" + DEFAULT_CANAL_USADO);

        // Get all the clienteList where canalUsado not equals to UPDATED_CANAL_USADO
        defaultClienteShouldBeFound("canalUsado.notEquals=" + UPDATED_CANAL_USADO);
    }

    @Test
    @Transactional
    public void getAllClientesByCanalUsadoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where canalUsado in DEFAULT_CANAL_USADO or UPDATED_CANAL_USADO
        defaultClienteShouldBeFound("canalUsado.in=" + DEFAULT_CANAL_USADO + "," + UPDATED_CANAL_USADO);

        // Get all the clienteList where canalUsado equals to UPDATED_CANAL_USADO
        defaultClienteShouldNotBeFound("canalUsado.in=" + UPDATED_CANAL_USADO);
    }

    @Test
    @Transactional
    public void getAllClientesByCanalUsadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where canalUsado is not null
        defaultClienteShouldBeFound("canalUsado.specified=true");

        // Get all the clienteList where canalUsado is null
        defaultClienteShouldNotBeFound("canalUsado.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientesByCanalUsadoContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where canalUsado contains DEFAULT_CANAL_USADO
        defaultClienteShouldBeFound("canalUsado.contains=" + DEFAULT_CANAL_USADO);

        // Get all the clienteList where canalUsado contains UPDATED_CANAL_USADO
        defaultClienteShouldNotBeFound("canalUsado.contains=" + UPDATED_CANAL_USADO);
    }

    @Test
    @Transactional
    public void getAllClientesByCanalUsadoNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where canalUsado does not contain DEFAULT_CANAL_USADO
        defaultClienteShouldNotBeFound("canalUsado.doesNotContain=" + DEFAULT_CANAL_USADO);

        // Get all the clienteList where canalUsado does not contain UPDATED_CANAL_USADO
        defaultClienteShouldBeFound("canalUsado.doesNotContain=" + UPDATED_CANAL_USADO);
    }


    @Test
    @Transactional
    public void getAllClientesByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where numero equals to DEFAULT_NUMERO
        defaultClienteShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the clienteList where numero equals to UPDATED_NUMERO
        defaultClienteShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllClientesByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where numero not equals to DEFAULT_NUMERO
        defaultClienteShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the clienteList where numero not equals to UPDATED_NUMERO
        defaultClienteShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllClientesByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultClienteShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the clienteList where numero equals to UPDATED_NUMERO
        defaultClienteShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllClientesByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where numero is not null
        defaultClienteShouldBeFound("numero.specified=true");

        // Get all the clienteList where numero is null
        defaultClienteShouldNotBeFound("numero.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientesByNumeroContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where numero contains DEFAULT_NUMERO
        defaultClienteShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the clienteList where numero contains UPDATED_NUMERO
        defaultClienteShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllClientesByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where numero does not contain DEFAULT_NUMERO
        defaultClienteShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the clienteList where numero does not contain UPDATED_NUMERO
        defaultClienteShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }


    @Test
    @Transactional
    public void getAllClientesByAutofacturacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where autofacturacao equals to DEFAULT_AUTOFACTURACAO
        defaultClienteShouldBeFound("autofacturacao.equals=" + DEFAULT_AUTOFACTURACAO);

        // Get all the clienteList where autofacturacao equals to UPDATED_AUTOFACTURACAO
        defaultClienteShouldNotBeFound("autofacturacao.equals=" + UPDATED_AUTOFACTURACAO);
    }

    @Test
    @Transactional
    public void getAllClientesByAutofacturacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where autofacturacao not equals to DEFAULT_AUTOFACTURACAO
        defaultClienteShouldNotBeFound("autofacturacao.notEquals=" + DEFAULT_AUTOFACTURACAO);

        // Get all the clienteList where autofacturacao not equals to UPDATED_AUTOFACTURACAO
        defaultClienteShouldBeFound("autofacturacao.notEquals=" + UPDATED_AUTOFACTURACAO);
    }

    @Test
    @Transactional
    public void getAllClientesByAutofacturacaoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where autofacturacao in DEFAULT_AUTOFACTURACAO or UPDATED_AUTOFACTURACAO
        defaultClienteShouldBeFound("autofacturacao.in=" + DEFAULT_AUTOFACTURACAO + "," + UPDATED_AUTOFACTURACAO);

        // Get all the clienteList where autofacturacao equals to UPDATED_AUTOFACTURACAO
        defaultClienteShouldNotBeFound("autofacturacao.in=" + UPDATED_AUTOFACTURACAO);
    }

    @Test
    @Transactional
    public void getAllClientesByAutofacturacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where autofacturacao is not null
        defaultClienteShouldBeFound("autofacturacao.specified=true");

        // Get all the clienteList where autofacturacao is null
        defaultClienteShouldNotBeFound("autofacturacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByPessoaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Pessoa pessoa = cliente.getPessoa();
        clienteRepository.saveAndFlush(cliente);
        Long pessoaId = pessoa.getId();

        // Get all the clienteList where pessoa equals to pessoaId
        defaultClienteShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the clienteList where pessoa equals to pessoaId + 1
        defaultClienteShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }


    @Test
    @Transactional
    public void getAllClientesByVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);
        Venda venda = VendaResourceIT.createEntity(em);
        em.persist(venda);
        em.flush();
        cliente.addVenda(venda);
        clienteRepository.saveAndFlush(cliente);
        Long vendaId = venda.getId();

        // Get all the clienteList where venda equals to vendaId
        defaultClienteShouldBeFound("vendaId.equals=" + vendaId);

        // Get all the clienteList where venda equals to vendaId + 1
        defaultClienteShouldNotBeFound("vendaId.equals=" + (vendaId + 1));
    }


    @Test
    @Transactional
    public void getAllClientesByContaIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);
        Conta conta = ContaResourceIT.createEntity(em);
        em.persist(conta);
        em.flush();
        cliente.setConta(conta);
        clienteRepository.saveAndFlush(cliente);
        Long contaId = conta.getId();

        // Get all the clienteList where conta equals to contaId
        defaultClienteShouldBeFound("contaId.equals=" + contaId);

        // Get all the clienteList where conta equals to contaId + 1
        defaultClienteShouldNotBeFound("contaId.equals=" + (contaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClienteShouldBeFound(String filter) throws Exception {
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].perfilProfissional").value(hasItem(DEFAULT_PERFIL_PROFISSIONAL.toString())))
            .andExpect(jsonPath("$.[*].satisfacao").value(hasItem(DEFAULT_SATISFACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].frequencia").value(hasItem(DEFAULT_FREQUENCIA.doubleValue())))
            .andExpect(jsonPath("$.[*].canalUsado").value(hasItem(DEFAULT_CANAL_USADO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].autofacturacao").value(hasItem(DEFAULT_AUTOFACTURACAO.booleanValue())));

        // Check, that the count call also returns 1
        restClienteMockMvc.perform(get("/api/clientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClienteShouldNotBeFound(String filter) throws Exception {
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClienteMockMvc.perform(get("/api/clientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .ativo(UPDATED_ATIVO)
            .perfilProfissional(UPDATED_PERFIL_PROFISSIONAL)
            .satisfacao(UPDATED_SATISFACAO)
            .frequencia(UPDATED_FREQUENCIA)
            .canalUsado(UPDATED_CANAL_USADO)
            .numero(UPDATED_NUMERO)
            .autofacturacao(UPDATED_AUTOFACTURACAO);
        ClienteDTO clienteDTO = clienteMapper.toDto(updatedCliente);

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.isAtivo()).isEqualTo(UPDATED_ATIVO);
        assertThat(testCliente.getPerfilProfissional()).isEqualTo(UPDATED_PERFIL_PROFISSIONAL);
        assertThat(testCliente.getSatisfacao()).isEqualTo(UPDATED_SATISFACAO);
        assertThat(testCliente.getFrequencia()).isEqualTo(UPDATED_FREQUENCIA);
        assertThat(testCliente.getCanalUsado()).isEqualTo(UPDATED_CANAL_USADO);
        assertThat(testCliente.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCliente.isAutofacturacao()).isEqualTo(UPDATED_AUTOFACTURACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
