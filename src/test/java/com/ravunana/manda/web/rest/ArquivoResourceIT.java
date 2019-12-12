package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.Arquivo;
import com.ravunana.manda.domain.User;
import com.ravunana.manda.repository.ArquivoRepository;
import com.ravunana.manda.service.ArquivoService;
import com.ravunana.manda.service.dto.ArquivoDTO;
import com.ravunana.manda.service.mapper.ArquivoMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.ArquivoCriteria;
import com.ravunana.manda.service.ArquivoQueryService;

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

import com.ravunana.manda.domain.enumeration.EntidadeSistema;
/**
 * Integration tests for the {@link ArquivoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ArquivoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final EntidadeSistema DEFAULT_ENTIDADE = EntidadeSistema.SOFTWARE;
    private static final EntidadeSistema UPDATED_ENTIDADE = EntidadeSistema.LICENCA_SOFTWARE;

    private static final byte[] DEFAULT_ANEXO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ANEXO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ANEXO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ANEXO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CODIGO_ENTIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ENTIDADE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired
    private ArquivoMapper arquivoMapper;

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private ArquivoQueryService arquivoQueryService;

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

    private MockMvc restArquivoMockMvc;

    private Arquivo arquivo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArquivoResource arquivoResource = new ArquivoResource(arquivoService, arquivoQueryService);
        this.restArquivoMockMvc = MockMvcBuilders.standaloneSetup(arquivoResource)
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
    public static Arquivo createEntity(EntityManager em) {
        Arquivo arquivo = new Arquivo()
            .descricao(DEFAULT_DESCRICAO)
            .entidade(DEFAULT_ENTIDADE)
            .anexo(DEFAULT_ANEXO)
            .anexoContentType(DEFAULT_ANEXO_CONTENT_TYPE)
            .codigoEntidade(DEFAULT_CODIGO_ENTIDADE)
            .data(DEFAULT_DATA);
        return arquivo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arquivo createUpdatedEntity(EntityManager em) {
        Arquivo arquivo = new Arquivo()
            .descricao(UPDATED_DESCRICAO)
            .entidade(UPDATED_ENTIDADE)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .codigoEntidade(UPDATED_CODIGO_ENTIDADE)
            .data(UPDATED_DATA);
        return arquivo;
    }

    @BeforeEach
    public void initTest() {
        arquivo = createEntity(em);
    }

    @Test
    @Transactional
    public void createArquivo() throws Exception {
        int databaseSizeBeforeCreate = arquivoRepository.findAll().size();

        // Create the Arquivo
        ArquivoDTO arquivoDTO = arquivoMapper.toDto(arquivo);
        restArquivoMockMvc.perform(post("/api/arquivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivoDTO)))
            .andExpect(status().isCreated());

        // Validate the Arquivo in the database
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeCreate + 1);
        Arquivo testArquivo = arquivoList.get(arquivoList.size() - 1);
        assertThat(testArquivo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testArquivo.getEntidade()).isEqualTo(DEFAULT_ENTIDADE);
        assertThat(testArquivo.getAnexo()).isEqualTo(DEFAULT_ANEXO);
        assertThat(testArquivo.getAnexoContentType()).isEqualTo(DEFAULT_ANEXO_CONTENT_TYPE);
        assertThat(testArquivo.getCodigoEntidade()).isEqualTo(DEFAULT_CODIGO_ENTIDADE);
        assertThat(testArquivo.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createArquivoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = arquivoRepository.findAll().size();

        // Create the Arquivo with an existing ID
        arquivo.setId(1L);
        ArquivoDTO arquivoDTO = arquivoMapper.toDto(arquivo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArquivoMockMvc.perform(post("/api/arquivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Arquivo in the database
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArquivos() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList
        restArquivoMockMvc.perform(get("/api/arquivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arquivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].entidade").value(hasItem(DEFAULT_ENTIDADE.toString())))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))))
            .andExpect(jsonPath("$.[*].codigoEntidade").value(hasItem(DEFAULT_CODIGO_ENTIDADE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getArquivo() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get the arquivo
        restArquivoMockMvc.perform(get("/api/arquivos/{id}", arquivo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(arquivo.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.entidade").value(DEFAULT_ENTIDADE.toString()))
            .andExpect(jsonPath("$.anexoContentType").value(DEFAULT_ANEXO_CONTENT_TYPE))
            .andExpect(jsonPath("$.anexo").value(Base64Utils.encodeToString(DEFAULT_ANEXO)))
            .andExpect(jsonPath("$.codigoEntidade").value(DEFAULT_CODIGO_ENTIDADE))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)));
    }


    @Test
    @Transactional
    public void getArquivosByIdFiltering() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        Long id = arquivo.getId();

        defaultArquivoShouldBeFound("id.equals=" + id);
        defaultArquivoShouldNotBeFound("id.notEquals=" + id);

        defaultArquivoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultArquivoShouldNotBeFound("id.greaterThan=" + id);

        defaultArquivoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultArquivoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllArquivosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where descricao equals to DEFAULT_DESCRICAO
        defaultArquivoShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the arquivoList where descricao equals to UPDATED_DESCRICAO
        defaultArquivoShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllArquivosByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where descricao not equals to DEFAULT_DESCRICAO
        defaultArquivoShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the arquivoList where descricao not equals to UPDATED_DESCRICAO
        defaultArquivoShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllArquivosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultArquivoShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the arquivoList where descricao equals to UPDATED_DESCRICAO
        defaultArquivoShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllArquivosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where descricao is not null
        defaultArquivoShouldBeFound("descricao.specified=true");

        // Get all the arquivoList where descricao is null
        defaultArquivoShouldNotBeFound("descricao.specified=false");
    }
                @Test
    @Transactional
    public void getAllArquivosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where descricao contains DEFAULT_DESCRICAO
        defaultArquivoShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the arquivoList where descricao contains UPDATED_DESCRICAO
        defaultArquivoShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllArquivosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where descricao does not contain DEFAULT_DESCRICAO
        defaultArquivoShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the arquivoList where descricao does not contain UPDATED_DESCRICAO
        defaultArquivoShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }


    @Test
    @Transactional
    public void getAllArquivosByEntidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where entidade equals to DEFAULT_ENTIDADE
        defaultArquivoShouldBeFound("entidade.equals=" + DEFAULT_ENTIDADE);

        // Get all the arquivoList where entidade equals to UPDATED_ENTIDADE
        defaultArquivoShouldNotBeFound("entidade.equals=" + UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllArquivosByEntidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where entidade not equals to DEFAULT_ENTIDADE
        defaultArquivoShouldNotBeFound("entidade.notEquals=" + DEFAULT_ENTIDADE);

        // Get all the arquivoList where entidade not equals to UPDATED_ENTIDADE
        defaultArquivoShouldBeFound("entidade.notEquals=" + UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllArquivosByEntidadeIsInShouldWork() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where entidade in DEFAULT_ENTIDADE or UPDATED_ENTIDADE
        defaultArquivoShouldBeFound("entidade.in=" + DEFAULT_ENTIDADE + "," + UPDATED_ENTIDADE);

        // Get all the arquivoList where entidade equals to UPDATED_ENTIDADE
        defaultArquivoShouldNotBeFound("entidade.in=" + UPDATED_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllArquivosByEntidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where entidade is not null
        defaultArquivoShouldBeFound("entidade.specified=true");

        // Get all the arquivoList where entidade is null
        defaultArquivoShouldNotBeFound("entidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllArquivosByCodigoEntidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where codigoEntidade equals to DEFAULT_CODIGO_ENTIDADE
        defaultArquivoShouldBeFound("codigoEntidade.equals=" + DEFAULT_CODIGO_ENTIDADE);

        // Get all the arquivoList where codigoEntidade equals to UPDATED_CODIGO_ENTIDADE
        defaultArquivoShouldNotBeFound("codigoEntidade.equals=" + UPDATED_CODIGO_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllArquivosByCodigoEntidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where codigoEntidade not equals to DEFAULT_CODIGO_ENTIDADE
        defaultArquivoShouldNotBeFound("codigoEntidade.notEquals=" + DEFAULT_CODIGO_ENTIDADE);

        // Get all the arquivoList where codigoEntidade not equals to UPDATED_CODIGO_ENTIDADE
        defaultArquivoShouldBeFound("codigoEntidade.notEquals=" + UPDATED_CODIGO_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllArquivosByCodigoEntidadeIsInShouldWork() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where codigoEntidade in DEFAULT_CODIGO_ENTIDADE or UPDATED_CODIGO_ENTIDADE
        defaultArquivoShouldBeFound("codigoEntidade.in=" + DEFAULT_CODIGO_ENTIDADE + "," + UPDATED_CODIGO_ENTIDADE);

        // Get all the arquivoList where codigoEntidade equals to UPDATED_CODIGO_ENTIDADE
        defaultArquivoShouldNotBeFound("codigoEntidade.in=" + UPDATED_CODIGO_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllArquivosByCodigoEntidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where codigoEntidade is not null
        defaultArquivoShouldBeFound("codigoEntidade.specified=true");

        // Get all the arquivoList where codigoEntidade is null
        defaultArquivoShouldNotBeFound("codigoEntidade.specified=false");
    }
                @Test
    @Transactional
    public void getAllArquivosByCodigoEntidadeContainsSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where codigoEntidade contains DEFAULT_CODIGO_ENTIDADE
        defaultArquivoShouldBeFound("codigoEntidade.contains=" + DEFAULT_CODIGO_ENTIDADE);

        // Get all the arquivoList where codigoEntidade contains UPDATED_CODIGO_ENTIDADE
        defaultArquivoShouldNotBeFound("codigoEntidade.contains=" + UPDATED_CODIGO_ENTIDADE);
    }

    @Test
    @Transactional
    public void getAllArquivosByCodigoEntidadeNotContainsSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where codigoEntidade does not contain DEFAULT_CODIGO_ENTIDADE
        defaultArquivoShouldNotBeFound("codigoEntidade.doesNotContain=" + DEFAULT_CODIGO_ENTIDADE);

        // Get all the arquivoList where codigoEntidade does not contain UPDATED_CODIGO_ENTIDADE
        defaultArquivoShouldBeFound("codigoEntidade.doesNotContain=" + UPDATED_CODIGO_ENTIDADE);
    }


    @Test
    @Transactional
    public void getAllArquivosByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where data equals to DEFAULT_DATA
        defaultArquivoShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the arquivoList where data equals to UPDATED_DATA
        defaultArquivoShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllArquivosByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where data not equals to DEFAULT_DATA
        defaultArquivoShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the arquivoList where data not equals to UPDATED_DATA
        defaultArquivoShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllArquivosByDataIsInShouldWork() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where data in DEFAULT_DATA or UPDATED_DATA
        defaultArquivoShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the arquivoList where data equals to UPDATED_DATA
        defaultArquivoShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllArquivosByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where data is not null
        defaultArquivoShouldBeFound("data.specified=true");

        // Get all the arquivoList where data is null
        defaultArquivoShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllArquivosByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where data is greater than or equal to DEFAULT_DATA
        defaultArquivoShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the arquivoList where data is greater than or equal to UPDATED_DATA
        defaultArquivoShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllArquivosByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where data is less than or equal to DEFAULT_DATA
        defaultArquivoShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the arquivoList where data is less than or equal to SMALLER_DATA
        defaultArquivoShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllArquivosByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where data is less than DEFAULT_DATA
        defaultArquivoShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the arquivoList where data is less than UPDATED_DATA
        defaultArquivoShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllArquivosByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        // Get all the arquivoList where data is greater than DEFAULT_DATA
        defaultArquivoShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the arquivoList where data is greater than SMALLER_DATA
        defaultArquivoShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllArquivosByUtilizadorIsEqualToSomething() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);
        User utilizador = UserResourceIT.createEntity(em);
        em.persist(utilizador);
        em.flush();
        arquivo.setUtilizador(utilizador);
        arquivoRepository.saveAndFlush(arquivo);
        Long utilizadorId = utilizador.getId();

        // Get all the arquivoList where utilizador equals to utilizadorId
        defaultArquivoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the arquivoList where utilizador equals to utilizadorId + 1
        defaultArquivoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultArquivoShouldBeFound(String filter) throws Exception {
        restArquivoMockMvc.perform(get("/api/arquivos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arquivo.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].entidade").value(hasItem(DEFAULT_ENTIDADE.toString())))
            .andExpect(jsonPath("$.[*].anexoContentType").value(hasItem(DEFAULT_ANEXO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANEXO))))
            .andExpect(jsonPath("$.[*].codigoEntidade").value(hasItem(DEFAULT_CODIGO_ENTIDADE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));

        // Check, that the count call also returns 1
        restArquivoMockMvc.perform(get("/api/arquivos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultArquivoShouldNotBeFound(String filter) throws Exception {
        restArquivoMockMvc.perform(get("/api/arquivos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restArquivoMockMvc.perform(get("/api/arquivos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingArquivo() throws Exception {
        // Get the arquivo
        restArquivoMockMvc.perform(get("/api/arquivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArquivo() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        int databaseSizeBeforeUpdate = arquivoRepository.findAll().size();

        // Update the arquivo
        Arquivo updatedArquivo = arquivoRepository.findById(arquivo.getId()).get();
        // Disconnect from session so that the updates on updatedArquivo are not directly saved in db
        em.detach(updatedArquivo);
        updatedArquivo
            .descricao(UPDATED_DESCRICAO)
            .entidade(UPDATED_ENTIDADE)
            .anexo(UPDATED_ANEXO)
            .anexoContentType(UPDATED_ANEXO_CONTENT_TYPE)
            .codigoEntidade(UPDATED_CODIGO_ENTIDADE)
            .data(UPDATED_DATA);
        ArquivoDTO arquivoDTO = arquivoMapper.toDto(updatedArquivo);

        restArquivoMockMvc.perform(put("/api/arquivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivoDTO)))
            .andExpect(status().isOk());

        // Validate the Arquivo in the database
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeUpdate);
        Arquivo testArquivo = arquivoList.get(arquivoList.size() - 1);
        assertThat(testArquivo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testArquivo.getEntidade()).isEqualTo(UPDATED_ENTIDADE);
        assertThat(testArquivo.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testArquivo.getAnexoContentType()).isEqualTo(UPDATED_ANEXO_CONTENT_TYPE);
        assertThat(testArquivo.getCodigoEntidade()).isEqualTo(UPDATED_CODIGO_ENTIDADE);
        assertThat(testArquivo.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingArquivo() throws Exception {
        int databaseSizeBeforeUpdate = arquivoRepository.findAll().size();

        // Create the Arquivo
        ArquivoDTO arquivoDTO = arquivoMapper.toDto(arquivo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArquivoMockMvc.perform(put("/api/arquivos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(arquivoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Arquivo in the database
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArquivo() throws Exception {
        // Initialize the database
        arquivoRepository.saveAndFlush(arquivo);

        int databaseSizeBeforeDelete = arquivoRepository.findAll().size();

        // Delete the arquivo
        restArquivoMockMvc.perform(delete("/api/arquivos/{id}", arquivo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Arquivo> arquivoList = arquivoRepository.findAll();
        assertThat(arquivoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
