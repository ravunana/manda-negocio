package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.ContaDebito;
import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.domain.EscrituracaoContabil;
import com.ravunana.manda.repository.ContaDebitoRepository;
import com.ravunana.manda.service.ContaDebitoService;
import com.ravunana.manda.service.dto.ContaDebitoDTO;
import com.ravunana.manda.service.mapper.ContaDebitoMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.ContaDebitoCriteria;
import com.ravunana.manda.service.ContaDebitoQueryService;

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
 * Integration tests for the {@link ContaDebitoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ContaDebitoResourceIT {

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(1);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(0 - 1);

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private ContaDebitoRepository contaDebitoRepository;

    @Autowired
    private ContaDebitoMapper contaDebitoMapper;

    @Autowired
    private ContaDebitoService contaDebitoService;

    @Autowired
    private ContaDebitoQueryService contaDebitoQueryService;

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

    private MockMvc restContaDebitoMockMvc;

    private ContaDebito contaDebito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContaDebitoResource contaDebitoResource = new ContaDebitoResource(contaDebitoService, contaDebitoQueryService);
        this.restContaDebitoMockMvc = MockMvcBuilders.standaloneSetup(contaDebitoResource)
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
    public static ContaDebito createEntity(EntityManager em) {
        ContaDebito contaDebito = new ContaDebito()
            .valor(DEFAULT_VALOR)
            .data(DEFAULT_DATA);
        // Add required entity
        Conta conta;
        if (TestUtil.findAll(em, Conta.class).isEmpty()) {
            conta = ContaResourceIT.createEntity(em);
            em.persist(conta);
            em.flush();
        } else {
            conta = TestUtil.findAll(em, Conta.class).get(0);
        }
        contaDebito.setContaDebitar(conta);
        // Add required entity
        EscrituracaoContabil escrituracaoContabil;
        if (TestUtil.findAll(em, EscrituracaoContabil.class).isEmpty()) {
            escrituracaoContabil = EscrituracaoContabilResourceIT.createEntity(em);
            em.persist(escrituracaoContabil);
            em.flush();
        } else {
            escrituracaoContabil = TestUtil.findAll(em, EscrituracaoContabil.class).get(0);
        }
        contaDebito.setLancamentoDebito(escrituracaoContabil);
        return contaDebito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContaDebito createUpdatedEntity(EntityManager em) {
        ContaDebito contaDebito = new ContaDebito()
            .valor(UPDATED_VALOR)
            .data(UPDATED_DATA);
        // Add required entity
        Conta conta;
        if (TestUtil.findAll(em, Conta.class).isEmpty()) {
            conta = ContaResourceIT.createUpdatedEntity(em);
            em.persist(conta);
            em.flush();
        } else {
            conta = TestUtil.findAll(em, Conta.class).get(0);
        }
        contaDebito.setContaDebitar(conta);
        // Add required entity
        EscrituracaoContabil escrituracaoContabil;
        if (TestUtil.findAll(em, EscrituracaoContabil.class).isEmpty()) {
            escrituracaoContabil = EscrituracaoContabilResourceIT.createUpdatedEntity(em);
            em.persist(escrituracaoContabil);
            em.flush();
        } else {
            escrituracaoContabil = TestUtil.findAll(em, EscrituracaoContabil.class).get(0);
        }
        contaDebito.setLancamentoDebito(escrituracaoContabil);
        return contaDebito;
    }

    @BeforeEach
    public void initTest() {
        contaDebito = createEntity(em);
    }

    @Test
    @Transactional
    public void createContaDebito() throws Exception {
        int databaseSizeBeforeCreate = contaDebitoRepository.findAll().size();

        // Create the ContaDebito
        ContaDebitoDTO contaDebitoDTO = contaDebitoMapper.toDto(contaDebito);
        restContaDebitoMockMvc.perform(post("/api/conta-debitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDebitoDTO)))
            .andExpect(status().isCreated());

        // Validate the ContaDebito in the database
        List<ContaDebito> contaDebitoList = contaDebitoRepository.findAll();
        assertThat(contaDebitoList).hasSize(databaseSizeBeforeCreate + 1);
        ContaDebito testContaDebito = contaDebitoList.get(contaDebitoList.size() - 1);
        assertThat(testContaDebito.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testContaDebito.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createContaDebitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contaDebitoRepository.findAll().size();

        // Create the ContaDebito with an existing ID
        contaDebito.setId(1L);
        ContaDebitoDTO contaDebitoDTO = contaDebitoMapper.toDto(contaDebito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContaDebitoMockMvc.perform(post("/api/conta-debitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDebitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContaDebito in the database
        List<ContaDebito> contaDebitoList = contaDebitoRepository.findAll();
        assertThat(contaDebitoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaDebitoRepository.findAll().size();
        // set the field null
        contaDebito.setValor(null);

        // Create the ContaDebito, which fails.
        ContaDebitoDTO contaDebitoDTO = contaDebitoMapper.toDto(contaDebito);

        restContaDebitoMockMvc.perform(post("/api/conta-debitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDebitoDTO)))
            .andExpect(status().isBadRequest());

        List<ContaDebito> contaDebitoList = contaDebitoRepository.findAll();
        assertThat(contaDebitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContaDebitos() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList
        restContaDebitoMockMvc.perform(get("/api/conta-debitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contaDebito.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getContaDebito() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get the contaDebito
        restContaDebitoMockMvc.perform(get("/api/conta-debitos/{id}", contaDebito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contaDebito.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)));
    }


    @Test
    @Transactional
    public void getContaDebitosByIdFiltering() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        Long id = contaDebito.getId();

        defaultContaDebitoShouldBeFound("id.equals=" + id);
        defaultContaDebitoShouldNotBeFound("id.notEquals=" + id);

        defaultContaDebitoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContaDebitoShouldNotBeFound("id.greaterThan=" + id);

        defaultContaDebitoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContaDebitoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContaDebitosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where valor equals to DEFAULT_VALOR
        defaultContaDebitoShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the contaDebitoList where valor equals to UPDATED_VALOR
        defaultContaDebitoShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllContaDebitosByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where valor not equals to DEFAULT_VALOR
        defaultContaDebitoShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the contaDebitoList where valor not equals to UPDATED_VALOR
        defaultContaDebitoShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllContaDebitosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultContaDebitoShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the contaDebitoList where valor equals to UPDATED_VALOR
        defaultContaDebitoShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllContaDebitosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where valor is not null
        defaultContaDebitoShouldBeFound("valor.specified=true");

        // Get all the contaDebitoList where valor is null
        defaultContaDebitoShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllContaDebitosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where valor is greater than or equal to DEFAULT_VALOR
        defaultContaDebitoShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the contaDebitoList where valor is greater than or equal to UPDATED_VALOR
        defaultContaDebitoShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllContaDebitosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where valor is less than or equal to DEFAULT_VALOR
        defaultContaDebitoShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the contaDebitoList where valor is less than or equal to SMALLER_VALOR
        defaultContaDebitoShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllContaDebitosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where valor is less than DEFAULT_VALOR
        defaultContaDebitoShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the contaDebitoList where valor is less than UPDATED_VALOR
        defaultContaDebitoShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllContaDebitosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where valor is greater than DEFAULT_VALOR
        defaultContaDebitoShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the contaDebitoList where valor is greater than SMALLER_VALOR
        defaultContaDebitoShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllContaDebitosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where data equals to DEFAULT_DATA
        defaultContaDebitoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the contaDebitoList where data equals to UPDATED_DATA
        defaultContaDebitoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllContaDebitosByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where data not equals to DEFAULT_DATA
        defaultContaDebitoShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the contaDebitoList where data not equals to UPDATED_DATA
        defaultContaDebitoShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllContaDebitosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultContaDebitoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the contaDebitoList where data equals to UPDATED_DATA
        defaultContaDebitoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllContaDebitosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where data is not null
        defaultContaDebitoShouldBeFound("data.specified=true");

        // Get all the contaDebitoList where data is null
        defaultContaDebitoShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllContaDebitosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where data is greater than or equal to DEFAULT_DATA
        defaultContaDebitoShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the contaDebitoList where data is greater than or equal to UPDATED_DATA
        defaultContaDebitoShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllContaDebitosByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where data is less than or equal to DEFAULT_DATA
        defaultContaDebitoShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the contaDebitoList where data is less than or equal to SMALLER_DATA
        defaultContaDebitoShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllContaDebitosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where data is less than DEFAULT_DATA
        defaultContaDebitoShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the contaDebitoList where data is less than UPDATED_DATA
        defaultContaDebitoShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllContaDebitosByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        // Get all the contaDebitoList where data is greater than DEFAULT_DATA
        defaultContaDebitoShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the contaDebitoList where data is greater than SMALLER_DATA
        defaultContaDebitoShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllContaDebitosByContaDebitarIsEqualToSomething() throws Exception {
        // Get already existing entity
        Conta contaDebitar = contaDebito.getContaDebitar();
        contaDebitoRepository.saveAndFlush(contaDebito);
        Long contaDebitarId = contaDebitar.getId();

        // Get all the contaDebitoList where contaDebitar equals to contaDebitarId
        defaultContaDebitoShouldBeFound("contaDebitarId.equals=" + contaDebitarId);

        // Get all the contaDebitoList where contaDebitar equals to contaDebitarId + 1
        defaultContaDebitoShouldNotBeFound("contaDebitarId.equals=" + (contaDebitarId + 1));
    }


    @Test
    @Transactional
    public void getAllContaDebitosByLancamentoDebitoIsEqualToSomething() throws Exception {
        // Get already existing entity
        EscrituracaoContabil lancamentoDebito = contaDebito.getLancamentoDebito();
        contaDebitoRepository.saveAndFlush(contaDebito);
        Long lancamentoDebitoId = lancamentoDebito.getId();

        // Get all the contaDebitoList where lancamentoDebito equals to lancamentoDebitoId
        defaultContaDebitoShouldBeFound("lancamentoDebitoId.equals=" + lancamentoDebitoId);

        // Get all the contaDebitoList where lancamentoDebito equals to lancamentoDebitoId + 1
        defaultContaDebitoShouldNotBeFound("lancamentoDebitoId.equals=" + (lancamentoDebitoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContaDebitoShouldBeFound(String filter) throws Exception {
        restContaDebitoMockMvc.perform(get("/api/conta-debitos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contaDebito.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));

        // Check, that the count call also returns 1
        restContaDebitoMockMvc.perform(get("/api/conta-debitos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContaDebitoShouldNotBeFound(String filter) throws Exception {
        restContaDebitoMockMvc.perform(get("/api/conta-debitos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContaDebitoMockMvc.perform(get("/api/conta-debitos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingContaDebito() throws Exception {
        // Get the contaDebito
        restContaDebitoMockMvc.perform(get("/api/conta-debitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContaDebito() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        int databaseSizeBeforeUpdate = contaDebitoRepository.findAll().size();

        // Update the contaDebito
        ContaDebito updatedContaDebito = contaDebitoRepository.findById(contaDebito.getId()).get();
        // Disconnect from session so that the updates on updatedContaDebito are not directly saved in db
        em.detach(updatedContaDebito);
        updatedContaDebito
            .valor(UPDATED_VALOR)
            .data(UPDATED_DATA);
        ContaDebitoDTO contaDebitoDTO = contaDebitoMapper.toDto(updatedContaDebito);

        restContaDebitoMockMvc.perform(put("/api/conta-debitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDebitoDTO)))
            .andExpect(status().isOk());

        // Validate the ContaDebito in the database
        List<ContaDebito> contaDebitoList = contaDebitoRepository.findAll();
        assertThat(contaDebitoList).hasSize(databaseSizeBeforeUpdate);
        ContaDebito testContaDebito = contaDebitoList.get(contaDebitoList.size() - 1);
        assertThat(testContaDebito.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testContaDebito.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingContaDebito() throws Exception {
        int databaseSizeBeforeUpdate = contaDebitoRepository.findAll().size();

        // Create the ContaDebito
        ContaDebitoDTO contaDebitoDTO = contaDebitoMapper.toDto(contaDebito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContaDebitoMockMvc.perform(put("/api/conta-debitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDebitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContaDebito in the database
        List<ContaDebito> contaDebitoList = contaDebitoRepository.findAll();
        assertThat(contaDebitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContaDebito() throws Exception {
        // Initialize the database
        contaDebitoRepository.saveAndFlush(contaDebito);

        int databaseSizeBeforeDelete = contaDebitoRepository.findAll().size();

        // Delete the contaDebito
        restContaDebitoMockMvc.perform(delete("/api/conta-debitos/{id}", contaDebito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContaDebito> contaDebitoList = contaDebitoRepository.findAll();
        assertThat(contaDebitoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
