package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.Imposto;
import com.ravunana.manda.domain.GrupoTributacaoImposto;
import com.ravunana.manda.domain.RetencaoFonte;
import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.domain.LancamentoFinanceiro;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.repository.ImpostoRepository;
import com.ravunana.manda.service.ImpostoService;
import com.ravunana.manda.service.dto.ImpostoDTO;
import com.ravunana.manda.service.mapper.ImpostoMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.ImpostoCriteria;
import com.ravunana.manda.service.ImpostoQueryService;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ImpostoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ImpostoResourceIT {

    private static final String DEFAULT_TIPO_IMPOSTO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_IMPOSTO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_IMPOSTO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_IMPOSTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PORCENTAGEM = false;
    private static final Boolean UPDATED_PORCENTAGEM = true;

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(0);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(1);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(0 - 1);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VIGENCIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VIGENCIA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VIGENCIA = LocalDate.ofEpochDay(-1L);

    @Autowired
    private ImpostoRepository impostoRepository;

    @Autowired
    private ImpostoMapper impostoMapper;

    @Autowired
    private ImpostoService impostoService;

    @Autowired
    private ImpostoQueryService impostoQueryService;

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

    private MockMvc restImpostoMockMvc;

    private Imposto imposto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImpostoResource impostoResource = new ImpostoResource(impostoService, impostoQueryService);
        this.restImpostoMockMvc = MockMvcBuilders.standaloneSetup(impostoResource)
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
    public static Imposto createEntity(EntityManager em) {
        Imposto imposto = new Imposto()
            .tipoImposto(DEFAULT_TIPO_IMPOSTO)
            .codigoImposto(DEFAULT_CODIGO_IMPOSTO)
            .porcentagem(DEFAULT_PORCENTAGEM)
            .valor(DEFAULT_VALOR)
            .descricao(DEFAULT_DESCRICAO)
            .pais(DEFAULT_PAIS)
            .vigencia(DEFAULT_VIGENCIA);
        return imposto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Imposto createUpdatedEntity(EntityManager em) {
        Imposto imposto = new Imposto()
            .tipoImposto(UPDATED_TIPO_IMPOSTO)
            .codigoImposto(UPDATED_CODIGO_IMPOSTO)
            .porcentagem(UPDATED_PORCENTAGEM)
            .valor(UPDATED_VALOR)
            .descricao(UPDATED_DESCRICAO)
            .pais(UPDATED_PAIS)
            .vigencia(UPDATED_VIGENCIA);
        return imposto;
    }

    @BeforeEach
    public void initTest() {
        imposto = createEntity(em);
    }

    @Test
    @Transactional
    public void createImposto() throws Exception {
        int databaseSizeBeforeCreate = impostoRepository.findAll().size();

        // Create the Imposto
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);
        restImpostoMockMvc.perform(post("/api/impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isCreated());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeCreate + 1);
        Imposto testImposto = impostoList.get(impostoList.size() - 1);
        assertThat(testImposto.getTipoImposto()).isEqualTo(DEFAULT_TIPO_IMPOSTO);
        assertThat(testImposto.getCodigoImposto()).isEqualTo(DEFAULT_CODIGO_IMPOSTO);
        assertThat(testImposto.isPorcentagem()).isEqualTo(DEFAULT_PORCENTAGEM);
        assertThat(testImposto.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testImposto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testImposto.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testImposto.getVigencia()).isEqualTo(DEFAULT_VIGENCIA);
    }

    @Test
    @Transactional
    public void createImpostoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = impostoRepository.findAll().size();

        // Create the Imposto with an existing ID
        imposto.setId(1L);
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImpostoMockMvc.perform(post("/api/impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoImpostoIsRequired() throws Exception {
        int databaseSizeBeforeTest = impostoRepository.findAll().size();
        // set the field null
        imposto.setTipoImposto(null);

        // Create the Imposto, which fails.
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        restImpostoMockMvc.perform(post("/api/impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isBadRequest());

        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoImpostoIsRequired() throws Exception {
        int databaseSizeBeforeTest = impostoRepository.findAll().size();
        // set the field null
        imposto.setCodigoImposto(null);

        // Create the Imposto, which fails.
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        restImpostoMockMvc.perform(post("/api/impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isBadRequest());

        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPorcentagemIsRequired() throws Exception {
        int databaseSizeBeforeTest = impostoRepository.findAll().size();
        // set the field null
        imposto.setPorcentagem(null);

        // Create the Imposto, which fails.
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        restImpostoMockMvc.perform(post("/api/impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isBadRequest());

        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = impostoRepository.findAll().size();
        // set the field null
        imposto.setValor(null);

        // Create the Imposto, which fails.
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        restImpostoMockMvc.perform(post("/api/impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isBadRequest());

        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImpostos() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList
        restImpostoMockMvc.perform(get("/api/impostos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imposto.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoImposto").value(hasItem(DEFAULT_TIPO_IMPOSTO)))
            .andExpect(jsonPath("$.[*].codigoImposto").value(hasItem(DEFAULT_CODIGO_IMPOSTO)))
            .andExpect(jsonPath("$.[*].porcentagem").value(hasItem(DEFAULT_PORCENTAGEM.booleanValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].vigencia").value(hasItem(DEFAULT_VIGENCIA.toString())));
    }
    
    @Test
    @Transactional
    public void getImposto() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get the imposto
        restImpostoMockMvc.perform(get("/api/impostos/{id}", imposto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imposto.getId().intValue()))
            .andExpect(jsonPath("$.tipoImposto").value(DEFAULT_TIPO_IMPOSTO))
            .andExpect(jsonPath("$.codigoImposto").value(DEFAULT_CODIGO_IMPOSTO))
            .andExpect(jsonPath("$.porcentagem").value(DEFAULT_PORCENTAGEM.booleanValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.vigencia").value(DEFAULT_VIGENCIA.toString()));
    }


    @Test
    @Transactional
    public void getImpostosByIdFiltering() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        Long id = imposto.getId();

        defaultImpostoShouldBeFound("id.equals=" + id);
        defaultImpostoShouldNotBeFound("id.notEquals=" + id);

        defaultImpostoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultImpostoShouldNotBeFound("id.greaterThan=" + id);

        defaultImpostoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultImpostoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllImpostosByTipoImpostoIsEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where tipoImposto equals to DEFAULT_TIPO_IMPOSTO
        defaultImpostoShouldBeFound("tipoImposto.equals=" + DEFAULT_TIPO_IMPOSTO);

        // Get all the impostoList where tipoImposto equals to UPDATED_TIPO_IMPOSTO
        defaultImpostoShouldNotBeFound("tipoImposto.equals=" + UPDATED_TIPO_IMPOSTO);
    }

    @Test
    @Transactional
    public void getAllImpostosByTipoImpostoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where tipoImposto not equals to DEFAULT_TIPO_IMPOSTO
        defaultImpostoShouldNotBeFound("tipoImposto.notEquals=" + DEFAULT_TIPO_IMPOSTO);

        // Get all the impostoList where tipoImposto not equals to UPDATED_TIPO_IMPOSTO
        defaultImpostoShouldBeFound("tipoImposto.notEquals=" + UPDATED_TIPO_IMPOSTO);
    }

    @Test
    @Transactional
    public void getAllImpostosByTipoImpostoIsInShouldWork() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where tipoImposto in DEFAULT_TIPO_IMPOSTO or UPDATED_TIPO_IMPOSTO
        defaultImpostoShouldBeFound("tipoImposto.in=" + DEFAULT_TIPO_IMPOSTO + "," + UPDATED_TIPO_IMPOSTO);

        // Get all the impostoList where tipoImposto equals to UPDATED_TIPO_IMPOSTO
        defaultImpostoShouldNotBeFound("tipoImposto.in=" + UPDATED_TIPO_IMPOSTO);
    }

    @Test
    @Transactional
    public void getAllImpostosByTipoImpostoIsNullOrNotNull() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where tipoImposto is not null
        defaultImpostoShouldBeFound("tipoImposto.specified=true");

        // Get all the impostoList where tipoImposto is null
        defaultImpostoShouldNotBeFound("tipoImposto.specified=false");
    }
                @Test
    @Transactional
    public void getAllImpostosByTipoImpostoContainsSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where tipoImposto contains DEFAULT_TIPO_IMPOSTO
        defaultImpostoShouldBeFound("tipoImposto.contains=" + DEFAULT_TIPO_IMPOSTO);

        // Get all the impostoList where tipoImposto contains UPDATED_TIPO_IMPOSTO
        defaultImpostoShouldNotBeFound("tipoImposto.contains=" + UPDATED_TIPO_IMPOSTO);
    }

    @Test
    @Transactional
    public void getAllImpostosByTipoImpostoNotContainsSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where tipoImposto does not contain DEFAULT_TIPO_IMPOSTO
        defaultImpostoShouldNotBeFound("tipoImposto.doesNotContain=" + DEFAULT_TIPO_IMPOSTO);

        // Get all the impostoList where tipoImposto does not contain UPDATED_TIPO_IMPOSTO
        defaultImpostoShouldBeFound("tipoImposto.doesNotContain=" + UPDATED_TIPO_IMPOSTO);
    }


    @Test
    @Transactional
    public void getAllImpostosByCodigoImpostoIsEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where codigoImposto equals to DEFAULT_CODIGO_IMPOSTO
        defaultImpostoShouldBeFound("codigoImposto.equals=" + DEFAULT_CODIGO_IMPOSTO);

        // Get all the impostoList where codigoImposto equals to UPDATED_CODIGO_IMPOSTO
        defaultImpostoShouldNotBeFound("codigoImposto.equals=" + UPDATED_CODIGO_IMPOSTO);
    }

    @Test
    @Transactional
    public void getAllImpostosByCodigoImpostoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where codigoImposto not equals to DEFAULT_CODIGO_IMPOSTO
        defaultImpostoShouldNotBeFound("codigoImposto.notEquals=" + DEFAULT_CODIGO_IMPOSTO);

        // Get all the impostoList where codigoImposto not equals to UPDATED_CODIGO_IMPOSTO
        defaultImpostoShouldBeFound("codigoImposto.notEquals=" + UPDATED_CODIGO_IMPOSTO);
    }

    @Test
    @Transactional
    public void getAllImpostosByCodigoImpostoIsInShouldWork() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where codigoImposto in DEFAULT_CODIGO_IMPOSTO or UPDATED_CODIGO_IMPOSTO
        defaultImpostoShouldBeFound("codigoImposto.in=" + DEFAULT_CODIGO_IMPOSTO + "," + UPDATED_CODIGO_IMPOSTO);

        // Get all the impostoList where codigoImposto equals to UPDATED_CODIGO_IMPOSTO
        defaultImpostoShouldNotBeFound("codigoImposto.in=" + UPDATED_CODIGO_IMPOSTO);
    }

    @Test
    @Transactional
    public void getAllImpostosByCodigoImpostoIsNullOrNotNull() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where codigoImposto is not null
        defaultImpostoShouldBeFound("codigoImposto.specified=true");

        // Get all the impostoList where codigoImposto is null
        defaultImpostoShouldNotBeFound("codigoImposto.specified=false");
    }
                @Test
    @Transactional
    public void getAllImpostosByCodigoImpostoContainsSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where codigoImposto contains DEFAULT_CODIGO_IMPOSTO
        defaultImpostoShouldBeFound("codigoImposto.contains=" + DEFAULT_CODIGO_IMPOSTO);

        // Get all the impostoList where codigoImposto contains UPDATED_CODIGO_IMPOSTO
        defaultImpostoShouldNotBeFound("codigoImposto.contains=" + UPDATED_CODIGO_IMPOSTO);
    }

    @Test
    @Transactional
    public void getAllImpostosByCodigoImpostoNotContainsSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where codigoImposto does not contain DEFAULT_CODIGO_IMPOSTO
        defaultImpostoShouldNotBeFound("codigoImposto.doesNotContain=" + DEFAULT_CODIGO_IMPOSTO);

        // Get all the impostoList where codigoImposto does not contain UPDATED_CODIGO_IMPOSTO
        defaultImpostoShouldBeFound("codigoImposto.doesNotContain=" + UPDATED_CODIGO_IMPOSTO);
    }


    @Test
    @Transactional
    public void getAllImpostosByPorcentagemIsEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where porcentagem equals to DEFAULT_PORCENTAGEM
        defaultImpostoShouldBeFound("porcentagem.equals=" + DEFAULT_PORCENTAGEM);

        // Get all the impostoList where porcentagem equals to UPDATED_PORCENTAGEM
        defaultImpostoShouldNotBeFound("porcentagem.equals=" + UPDATED_PORCENTAGEM);
    }

    @Test
    @Transactional
    public void getAllImpostosByPorcentagemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where porcentagem not equals to DEFAULT_PORCENTAGEM
        defaultImpostoShouldNotBeFound("porcentagem.notEquals=" + DEFAULT_PORCENTAGEM);

        // Get all the impostoList where porcentagem not equals to UPDATED_PORCENTAGEM
        defaultImpostoShouldBeFound("porcentagem.notEquals=" + UPDATED_PORCENTAGEM);
    }

    @Test
    @Transactional
    public void getAllImpostosByPorcentagemIsInShouldWork() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where porcentagem in DEFAULT_PORCENTAGEM or UPDATED_PORCENTAGEM
        defaultImpostoShouldBeFound("porcentagem.in=" + DEFAULT_PORCENTAGEM + "," + UPDATED_PORCENTAGEM);

        // Get all the impostoList where porcentagem equals to UPDATED_PORCENTAGEM
        defaultImpostoShouldNotBeFound("porcentagem.in=" + UPDATED_PORCENTAGEM);
    }

    @Test
    @Transactional
    public void getAllImpostosByPorcentagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where porcentagem is not null
        defaultImpostoShouldBeFound("porcentagem.specified=true");

        // Get all the impostoList where porcentagem is null
        defaultImpostoShouldNotBeFound("porcentagem.specified=false");
    }

    @Test
    @Transactional
    public void getAllImpostosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where valor equals to DEFAULT_VALOR
        defaultImpostoShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the impostoList where valor equals to UPDATED_VALOR
        defaultImpostoShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllImpostosByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where valor not equals to DEFAULT_VALOR
        defaultImpostoShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the impostoList where valor not equals to UPDATED_VALOR
        defaultImpostoShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllImpostosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultImpostoShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the impostoList where valor equals to UPDATED_VALOR
        defaultImpostoShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllImpostosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where valor is not null
        defaultImpostoShouldBeFound("valor.specified=true");

        // Get all the impostoList where valor is null
        defaultImpostoShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllImpostosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where valor is greater than or equal to DEFAULT_VALOR
        defaultImpostoShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the impostoList where valor is greater than or equal to UPDATED_VALOR
        defaultImpostoShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllImpostosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where valor is less than or equal to DEFAULT_VALOR
        defaultImpostoShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the impostoList where valor is less than or equal to SMALLER_VALOR
        defaultImpostoShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllImpostosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where valor is less than DEFAULT_VALOR
        defaultImpostoShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the impostoList where valor is less than UPDATED_VALOR
        defaultImpostoShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllImpostosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where valor is greater than DEFAULT_VALOR
        defaultImpostoShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the impostoList where valor is greater than SMALLER_VALOR
        defaultImpostoShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllImpostosByPaisIsEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where pais equals to DEFAULT_PAIS
        defaultImpostoShouldBeFound("pais.equals=" + DEFAULT_PAIS);

        // Get all the impostoList where pais equals to UPDATED_PAIS
        defaultImpostoShouldNotBeFound("pais.equals=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllImpostosByPaisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where pais not equals to DEFAULT_PAIS
        defaultImpostoShouldNotBeFound("pais.notEquals=" + DEFAULT_PAIS);

        // Get all the impostoList where pais not equals to UPDATED_PAIS
        defaultImpostoShouldBeFound("pais.notEquals=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllImpostosByPaisIsInShouldWork() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where pais in DEFAULT_PAIS or UPDATED_PAIS
        defaultImpostoShouldBeFound("pais.in=" + DEFAULT_PAIS + "," + UPDATED_PAIS);

        // Get all the impostoList where pais equals to UPDATED_PAIS
        defaultImpostoShouldNotBeFound("pais.in=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllImpostosByPaisIsNullOrNotNull() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where pais is not null
        defaultImpostoShouldBeFound("pais.specified=true");

        // Get all the impostoList where pais is null
        defaultImpostoShouldNotBeFound("pais.specified=false");
    }
                @Test
    @Transactional
    public void getAllImpostosByPaisContainsSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where pais contains DEFAULT_PAIS
        defaultImpostoShouldBeFound("pais.contains=" + DEFAULT_PAIS);

        // Get all the impostoList where pais contains UPDATED_PAIS
        defaultImpostoShouldNotBeFound("pais.contains=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllImpostosByPaisNotContainsSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where pais does not contain DEFAULT_PAIS
        defaultImpostoShouldNotBeFound("pais.doesNotContain=" + DEFAULT_PAIS);

        // Get all the impostoList where pais does not contain UPDATED_PAIS
        defaultImpostoShouldBeFound("pais.doesNotContain=" + UPDATED_PAIS);
    }


    @Test
    @Transactional
    public void getAllImpostosByVigenciaIsEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where vigencia equals to DEFAULT_VIGENCIA
        defaultImpostoShouldBeFound("vigencia.equals=" + DEFAULT_VIGENCIA);

        // Get all the impostoList where vigencia equals to UPDATED_VIGENCIA
        defaultImpostoShouldNotBeFound("vigencia.equals=" + UPDATED_VIGENCIA);
    }

    @Test
    @Transactional
    public void getAllImpostosByVigenciaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where vigencia not equals to DEFAULT_VIGENCIA
        defaultImpostoShouldNotBeFound("vigencia.notEquals=" + DEFAULT_VIGENCIA);

        // Get all the impostoList where vigencia not equals to UPDATED_VIGENCIA
        defaultImpostoShouldBeFound("vigencia.notEquals=" + UPDATED_VIGENCIA);
    }

    @Test
    @Transactional
    public void getAllImpostosByVigenciaIsInShouldWork() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where vigencia in DEFAULT_VIGENCIA or UPDATED_VIGENCIA
        defaultImpostoShouldBeFound("vigencia.in=" + DEFAULT_VIGENCIA + "," + UPDATED_VIGENCIA);

        // Get all the impostoList where vigencia equals to UPDATED_VIGENCIA
        defaultImpostoShouldNotBeFound("vigencia.in=" + UPDATED_VIGENCIA);
    }

    @Test
    @Transactional
    public void getAllImpostosByVigenciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where vigencia is not null
        defaultImpostoShouldBeFound("vigencia.specified=true");

        // Get all the impostoList where vigencia is null
        defaultImpostoShouldNotBeFound("vigencia.specified=false");
    }

    @Test
    @Transactional
    public void getAllImpostosByVigenciaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where vigencia is greater than or equal to DEFAULT_VIGENCIA
        defaultImpostoShouldBeFound("vigencia.greaterThanOrEqual=" + DEFAULT_VIGENCIA);

        // Get all the impostoList where vigencia is greater than or equal to UPDATED_VIGENCIA
        defaultImpostoShouldNotBeFound("vigencia.greaterThanOrEqual=" + UPDATED_VIGENCIA);
    }

    @Test
    @Transactional
    public void getAllImpostosByVigenciaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where vigencia is less than or equal to DEFAULT_VIGENCIA
        defaultImpostoShouldBeFound("vigencia.lessThanOrEqual=" + DEFAULT_VIGENCIA);

        // Get all the impostoList where vigencia is less than or equal to SMALLER_VIGENCIA
        defaultImpostoShouldNotBeFound("vigencia.lessThanOrEqual=" + SMALLER_VIGENCIA);
    }

    @Test
    @Transactional
    public void getAllImpostosByVigenciaIsLessThanSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where vigencia is less than DEFAULT_VIGENCIA
        defaultImpostoShouldNotBeFound("vigencia.lessThan=" + DEFAULT_VIGENCIA);

        // Get all the impostoList where vigencia is less than UPDATED_VIGENCIA
        defaultImpostoShouldBeFound("vigencia.lessThan=" + UPDATED_VIGENCIA);
    }

    @Test
    @Transactional
    public void getAllImpostosByVigenciaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        // Get all the impostoList where vigencia is greater than DEFAULT_VIGENCIA
        defaultImpostoShouldNotBeFound("vigencia.greaterThan=" + DEFAULT_VIGENCIA);

        // Get all the impostoList where vigencia is greater than SMALLER_VIGENCIA
        defaultImpostoShouldBeFound("vigencia.greaterThan=" + SMALLER_VIGENCIA);
    }


    @Test
    @Transactional
    public void getAllImpostosByGrupoTributacaoImpostoIsEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);
        GrupoTributacaoImposto grupoTributacaoImposto = GrupoTributacaoImpostoResourceIT.createEntity(em);
        em.persist(grupoTributacaoImposto);
        em.flush();
        imposto.addGrupoTributacaoImposto(grupoTributacaoImposto);
        impostoRepository.saveAndFlush(imposto);
        Long grupoTributacaoImpostoId = grupoTributacaoImposto.getId();

        // Get all the impostoList where grupoTributacaoImposto equals to grupoTributacaoImpostoId
        defaultImpostoShouldBeFound("grupoTributacaoImpostoId.equals=" + grupoTributacaoImpostoId);

        // Get all the impostoList where grupoTributacaoImposto equals to grupoTributacaoImpostoId + 1
        defaultImpostoShouldNotBeFound("grupoTributacaoImpostoId.equals=" + (grupoTributacaoImpostoId + 1));
    }


    @Test
    @Transactional
    public void getAllImpostosByRetencaoFonteIsEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);
        RetencaoFonte retencaoFonte = RetencaoFonteResourceIT.createEntity(em);
        em.persist(retencaoFonte);
        em.flush();
        imposto.addRetencaoFonte(retencaoFonte);
        impostoRepository.saveAndFlush(imposto);
        Long retencaoFonteId = retencaoFonte.getId();

        // Get all the impostoList where retencaoFonte equals to retencaoFonteId
        defaultImpostoShouldBeFound("retencaoFonteId.equals=" + retencaoFonteId);

        // Get all the impostoList where retencaoFonte equals to retencaoFonteId + 1
        defaultImpostoShouldNotBeFound("retencaoFonteId.equals=" + (retencaoFonteId + 1));
    }


    @Test
    @Transactional
    public void getAllImpostosByContaIsEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);
        Conta conta = ContaResourceIT.createEntity(em);
        em.persist(conta);
        em.flush();
        imposto.setConta(conta);
        impostoRepository.saveAndFlush(imposto);
        Long contaId = conta.getId();

        // Get all the impostoList where conta equals to contaId
        defaultImpostoShouldBeFound("contaId.equals=" + contaId);

        // Get all the impostoList where conta equals to contaId + 1
        defaultImpostoShouldNotBeFound("contaId.equals=" + (contaId + 1));
    }


    @Test
    @Transactional
    public void getAllImpostosByLancamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);
        LancamentoFinanceiro lancamento = LancamentoFinanceiroResourceIT.createEntity(em);
        em.persist(lancamento);
        em.flush();
        imposto.addLancamento(lancamento);
        impostoRepository.saveAndFlush(imposto);
        Long lancamentoId = lancamento.getId();

        // Get all the impostoList where lancamento equals to lancamentoId
        defaultImpostoShouldBeFound("lancamentoId.equals=" + lancamentoId);

        // Get all the impostoList where lancamento equals to lancamentoId + 1
        defaultImpostoShouldNotBeFound("lancamentoId.equals=" + (lancamentoId + 1));
    }


    @Test
    @Transactional
    public void getAllImpostosByProdutoIsEqualToSomething() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);
        Produto produto = ProdutoResourceIT.createEntity(em);
        em.persist(produto);
        em.flush();
        imposto.addProduto(produto);
        impostoRepository.saveAndFlush(imposto);
        Long produtoId = produto.getId();

        // Get all the impostoList where produto equals to produtoId
        defaultImpostoShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the impostoList where produto equals to produtoId + 1
        defaultImpostoShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImpostoShouldBeFound(String filter) throws Exception {
        restImpostoMockMvc.perform(get("/api/impostos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imposto.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoImposto").value(hasItem(DEFAULT_TIPO_IMPOSTO)))
            .andExpect(jsonPath("$.[*].codigoImposto").value(hasItem(DEFAULT_CODIGO_IMPOSTO)))
            .andExpect(jsonPath("$.[*].porcentagem").value(hasItem(DEFAULT_PORCENTAGEM.booleanValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].vigencia").value(hasItem(DEFAULT_VIGENCIA.toString())));

        // Check, that the count call also returns 1
        restImpostoMockMvc.perform(get("/api/impostos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImpostoShouldNotBeFound(String filter) throws Exception {
        restImpostoMockMvc.perform(get("/api/impostos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImpostoMockMvc.perform(get("/api/impostos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingImposto() throws Exception {
        // Get the imposto
        restImpostoMockMvc.perform(get("/api/impostos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImposto() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        int databaseSizeBeforeUpdate = impostoRepository.findAll().size();

        // Update the imposto
        Imposto updatedImposto = impostoRepository.findById(imposto.getId()).get();
        // Disconnect from session so that the updates on updatedImposto are not directly saved in db
        em.detach(updatedImposto);
        updatedImposto
            .tipoImposto(UPDATED_TIPO_IMPOSTO)
            .codigoImposto(UPDATED_CODIGO_IMPOSTO)
            .porcentagem(UPDATED_PORCENTAGEM)
            .valor(UPDATED_VALOR)
            .descricao(UPDATED_DESCRICAO)
            .pais(UPDATED_PAIS)
            .vigencia(UPDATED_VIGENCIA);
        ImpostoDTO impostoDTO = impostoMapper.toDto(updatedImposto);

        restImpostoMockMvc.perform(put("/api/impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isOk());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeUpdate);
        Imposto testImposto = impostoList.get(impostoList.size() - 1);
        assertThat(testImposto.getTipoImposto()).isEqualTo(UPDATED_TIPO_IMPOSTO);
        assertThat(testImposto.getCodigoImposto()).isEqualTo(UPDATED_CODIGO_IMPOSTO);
        assertThat(testImposto.isPorcentagem()).isEqualTo(UPDATED_PORCENTAGEM);
        assertThat(testImposto.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testImposto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testImposto.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testImposto.getVigencia()).isEqualTo(UPDATED_VIGENCIA);
    }

    @Test
    @Transactional
    public void updateNonExistingImposto() throws Exception {
        int databaseSizeBeforeUpdate = impostoRepository.findAll().size();

        // Create the Imposto
        ImpostoDTO impostoDTO = impostoMapper.toDto(imposto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpostoMockMvc.perform(put("/api/impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impostoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Imposto in the database
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImposto() throws Exception {
        // Initialize the database
        impostoRepository.saveAndFlush(imposto);

        int databaseSizeBeforeDelete = impostoRepository.findAll().size();

        // Delete the imposto
        restImpostoMockMvc.perform(delete("/api/impostos/{id}", imposto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Imposto> impostoList = impostoRepository.findAll();
        assertThat(impostoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
