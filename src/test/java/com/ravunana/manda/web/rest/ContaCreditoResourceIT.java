package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.ContaCredito;
import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.domain.EscrituracaoContabil;
import com.ravunana.manda.repository.ContaCreditoRepository;
import com.ravunana.manda.service.ContaCreditoService;
import com.ravunana.manda.service.dto.ContaCreditoDTO;
import com.ravunana.manda.service.mapper.ContaCreditoMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.ContaCreditoCriteria;
import com.ravunana.manda.service.ContaCreditoQueryService;

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
 * Integration tests for the {@link ContaCreditoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ContaCreditoResourceIT {

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(1);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(0 - 1);

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private ContaCreditoRepository contaCreditoRepository;

    @Autowired
    private ContaCreditoMapper contaCreditoMapper;

    @Autowired
    private ContaCreditoService contaCreditoService;

    @Autowired
    private ContaCreditoQueryService contaCreditoQueryService;

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

    private MockMvc restContaCreditoMockMvc;

    private ContaCredito contaCredito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContaCreditoResource contaCreditoResource = new ContaCreditoResource(contaCreditoService, contaCreditoQueryService);
        this.restContaCreditoMockMvc = MockMvcBuilders.standaloneSetup(contaCreditoResource)
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
    public static ContaCredito createEntity(EntityManager em) {
        ContaCredito contaCredito = new ContaCredito()
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
        contaCredito.setContaCreditar(conta);
        // Add required entity
        EscrituracaoContabil escrituracaoContabil;
        if (TestUtil.findAll(em, EscrituracaoContabil.class).isEmpty()) {
            escrituracaoContabil = EscrituracaoContabilResourceIT.createEntity(em);
            em.persist(escrituracaoContabil);
            em.flush();
        } else {
            escrituracaoContabil = TestUtil.findAll(em, EscrituracaoContabil.class).get(0);
        }
        contaCredito.setLancamentoCredito(escrituracaoContabil);
        return contaCredito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContaCredito createUpdatedEntity(EntityManager em) {
        ContaCredito contaCredito = new ContaCredito()
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
        contaCredito.setContaCreditar(conta);
        // Add required entity
        EscrituracaoContabil escrituracaoContabil;
        if (TestUtil.findAll(em, EscrituracaoContabil.class).isEmpty()) {
            escrituracaoContabil = EscrituracaoContabilResourceIT.createUpdatedEntity(em);
            em.persist(escrituracaoContabil);
            em.flush();
        } else {
            escrituracaoContabil = TestUtil.findAll(em, EscrituracaoContabil.class).get(0);
        }
        contaCredito.setLancamentoCredito(escrituracaoContabil);
        return contaCredito;
    }

    @BeforeEach
    public void initTest() {
        contaCredito = createEntity(em);
    }

    @Test
    @Transactional
    public void createContaCredito() throws Exception {
        int databaseSizeBeforeCreate = contaCreditoRepository.findAll().size();

        // Create the ContaCredito
        ContaCreditoDTO contaCreditoDTO = contaCreditoMapper.toDto(contaCredito);
        restContaCreditoMockMvc.perform(post("/api/conta-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaCreditoDTO)))
            .andExpect(status().isCreated());

        // Validate the ContaCredito in the database
        List<ContaCredito> contaCreditoList = contaCreditoRepository.findAll();
        assertThat(contaCreditoList).hasSize(databaseSizeBeforeCreate + 1);
        ContaCredito testContaCredito = contaCreditoList.get(contaCreditoList.size() - 1);
        assertThat(testContaCredito.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testContaCredito.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createContaCreditoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contaCreditoRepository.findAll().size();

        // Create the ContaCredito with an existing ID
        contaCredito.setId(1L);
        ContaCreditoDTO contaCreditoDTO = contaCreditoMapper.toDto(contaCredito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContaCreditoMockMvc.perform(post("/api/conta-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaCreditoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContaCredito in the database
        List<ContaCredito> contaCreditoList = contaCreditoRepository.findAll();
        assertThat(contaCreditoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaCreditoRepository.findAll().size();
        // set the field null
        contaCredito.setValor(null);

        // Create the ContaCredito, which fails.
        ContaCreditoDTO contaCreditoDTO = contaCreditoMapper.toDto(contaCredito);

        restContaCreditoMockMvc.perform(post("/api/conta-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaCreditoDTO)))
            .andExpect(status().isBadRequest());

        List<ContaCredito> contaCreditoList = contaCreditoRepository.findAll();
        assertThat(contaCreditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContaCreditos() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList
        restContaCreditoMockMvc.perform(get("/api/conta-creditos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contaCredito.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getContaCredito() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get the contaCredito
        restContaCreditoMockMvc.perform(get("/api/conta-creditos/{id}", contaCredito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contaCredito.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)));
    }


    @Test
    @Transactional
    public void getContaCreditosByIdFiltering() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        Long id = contaCredito.getId();

        defaultContaCreditoShouldBeFound("id.equals=" + id);
        defaultContaCreditoShouldNotBeFound("id.notEquals=" + id);

        defaultContaCreditoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContaCreditoShouldNotBeFound("id.greaterThan=" + id);

        defaultContaCreditoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContaCreditoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContaCreditosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where valor equals to DEFAULT_VALOR
        defaultContaCreditoShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the contaCreditoList where valor equals to UPDATED_VALOR
        defaultContaCreditoShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllContaCreditosByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where valor not equals to DEFAULT_VALOR
        defaultContaCreditoShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the contaCreditoList where valor not equals to UPDATED_VALOR
        defaultContaCreditoShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllContaCreditosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultContaCreditoShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the contaCreditoList where valor equals to UPDATED_VALOR
        defaultContaCreditoShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllContaCreditosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where valor is not null
        defaultContaCreditoShouldBeFound("valor.specified=true");

        // Get all the contaCreditoList where valor is null
        defaultContaCreditoShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllContaCreditosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where valor is greater than or equal to DEFAULT_VALOR
        defaultContaCreditoShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the contaCreditoList where valor is greater than or equal to UPDATED_VALOR
        defaultContaCreditoShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllContaCreditosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where valor is less than or equal to DEFAULT_VALOR
        defaultContaCreditoShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the contaCreditoList where valor is less than or equal to SMALLER_VALOR
        defaultContaCreditoShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllContaCreditosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where valor is less than DEFAULT_VALOR
        defaultContaCreditoShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the contaCreditoList where valor is less than UPDATED_VALOR
        defaultContaCreditoShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllContaCreditosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where valor is greater than DEFAULT_VALOR
        defaultContaCreditoShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the contaCreditoList where valor is greater than SMALLER_VALOR
        defaultContaCreditoShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllContaCreditosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where data equals to DEFAULT_DATA
        defaultContaCreditoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the contaCreditoList where data equals to UPDATED_DATA
        defaultContaCreditoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllContaCreditosByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where data not equals to DEFAULT_DATA
        defaultContaCreditoShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the contaCreditoList where data not equals to UPDATED_DATA
        defaultContaCreditoShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllContaCreditosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultContaCreditoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the contaCreditoList where data equals to UPDATED_DATA
        defaultContaCreditoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllContaCreditosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where data is not null
        defaultContaCreditoShouldBeFound("data.specified=true");

        // Get all the contaCreditoList where data is null
        defaultContaCreditoShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllContaCreditosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where data is greater than or equal to DEFAULT_DATA
        defaultContaCreditoShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the contaCreditoList where data is greater than or equal to UPDATED_DATA
        defaultContaCreditoShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllContaCreditosByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where data is less than or equal to DEFAULT_DATA
        defaultContaCreditoShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the contaCreditoList where data is less than or equal to SMALLER_DATA
        defaultContaCreditoShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllContaCreditosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where data is less than DEFAULT_DATA
        defaultContaCreditoShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the contaCreditoList where data is less than UPDATED_DATA
        defaultContaCreditoShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllContaCreditosByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        // Get all the contaCreditoList where data is greater than DEFAULT_DATA
        defaultContaCreditoShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the contaCreditoList where data is greater than SMALLER_DATA
        defaultContaCreditoShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllContaCreditosByContaCreditarIsEqualToSomething() throws Exception {
        // Get already existing entity
        Conta contaCreditar = contaCredito.getContaCreditar();
        contaCreditoRepository.saveAndFlush(contaCredito);
        Long contaCreditarId = contaCreditar.getId();

        // Get all the contaCreditoList where contaCreditar equals to contaCreditarId
        defaultContaCreditoShouldBeFound("contaCreditarId.equals=" + contaCreditarId);

        // Get all the contaCreditoList where contaCreditar equals to contaCreditarId + 1
        defaultContaCreditoShouldNotBeFound("contaCreditarId.equals=" + (contaCreditarId + 1));
    }


    @Test
    @Transactional
    public void getAllContaCreditosByLancamentoCreditoIsEqualToSomething() throws Exception {
        // Get already existing entity
        EscrituracaoContabil lancamentoCredito = contaCredito.getLancamentoCredito();
        contaCreditoRepository.saveAndFlush(contaCredito);
        Long lancamentoCreditoId = lancamentoCredito.getId();

        // Get all the contaCreditoList where lancamentoCredito equals to lancamentoCreditoId
        defaultContaCreditoShouldBeFound("lancamentoCreditoId.equals=" + lancamentoCreditoId);

        // Get all the contaCreditoList where lancamentoCredito equals to lancamentoCreditoId + 1
        defaultContaCreditoShouldNotBeFound("lancamentoCreditoId.equals=" + (lancamentoCreditoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContaCreditoShouldBeFound(String filter) throws Exception {
        restContaCreditoMockMvc.perform(get("/api/conta-creditos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contaCredito.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));

        // Check, that the count call also returns 1
        restContaCreditoMockMvc.perform(get("/api/conta-creditos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContaCreditoShouldNotBeFound(String filter) throws Exception {
        restContaCreditoMockMvc.perform(get("/api/conta-creditos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContaCreditoMockMvc.perform(get("/api/conta-creditos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingContaCredito() throws Exception {
        // Get the contaCredito
        restContaCreditoMockMvc.perform(get("/api/conta-creditos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContaCredito() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        int databaseSizeBeforeUpdate = contaCreditoRepository.findAll().size();

        // Update the contaCredito
        ContaCredito updatedContaCredito = contaCreditoRepository.findById(contaCredito.getId()).get();
        // Disconnect from session so that the updates on updatedContaCredito are not directly saved in db
        em.detach(updatedContaCredito);
        updatedContaCredito
            .valor(UPDATED_VALOR)
            .data(UPDATED_DATA);
        ContaCreditoDTO contaCreditoDTO = contaCreditoMapper.toDto(updatedContaCredito);

        restContaCreditoMockMvc.perform(put("/api/conta-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaCreditoDTO)))
            .andExpect(status().isOk());

        // Validate the ContaCredito in the database
        List<ContaCredito> contaCreditoList = contaCreditoRepository.findAll();
        assertThat(contaCreditoList).hasSize(databaseSizeBeforeUpdate);
        ContaCredito testContaCredito = contaCreditoList.get(contaCreditoList.size() - 1);
        assertThat(testContaCredito.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testContaCredito.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingContaCredito() throws Exception {
        int databaseSizeBeforeUpdate = contaCreditoRepository.findAll().size();

        // Create the ContaCredito
        ContaCreditoDTO contaCreditoDTO = contaCreditoMapper.toDto(contaCredito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContaCreditoMockMvc.perform(put("/api/conta-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaCreditoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContaCredito in the database
        List<ContaCredito> contaCreditoList = contaCreditoRepository.findAll();
        assertThat(contaCreditoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContaCredito() throws Exception {
        // Initialize the database
        contaCreditoRepository.saveAndFlush(contaCredito);

        int databaseSizeBeforeDelete = contaCreditoRepository.findAll().size();

        // Delete the contaCredito
        restContaCreditoMockMvc.perform(delete("/api/conta-creditos/{id}", contaCredito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContaCredito> contaCreditoList = contaCreditoRepository.findAll();
        assertThat(contaCreditoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
