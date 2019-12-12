package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.GrupoTributacaoImposto;
import com.ravunana.manda.domain.Imposto;
import com.ravunana.manda.repository.GrupoTributacaoImpostoRepository;
import com.ravunana.manda.service.GrupoTributacaoImpostoService;
import com.ravunana.manda.service.dto.GrupoTributacaoImpostoDTO;
import com.ravunana.manda.service.mapper.GrupoTributacaoImpostoMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GrupoTributacaoImpostoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class GrupoTributacaoImpostoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Double DEFAULT_VALOR = 0D;
    private static final Double UPDATED_VALOR = 1D;

    private static final Double DEFAULT_DE = 1D;
    private static final Double UPDATED_DE = 2D;

    private static final Double DEFAULT_ATE = 1D;
    private static final Double UPDATED_ATE = 2D;

    private static final Integer DEFAULT_LIMITE_LIQUIDACAO = 1;
    private static final Integer UPDATED_LIMITE_LIQUIDACAO = 2;

    @Autowired
    private GrupoTributacaoImpostoRepository grupoTributacaoImpostoRepository;

    @Autowired
    private GrupoTributacaoImpostoMapper grupoTributacaoImpostoMapper;

    @Autowired
    private GrupoTributacaoImpostoService grupoTributacaoImpostoService;

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

    private MockMvc restGrupoTributacaoImpostoMockMvc;

    private GrupoTributacaoImposto grupoTributacaoImposto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrupoTributacaoImpostoResource grupoTributacaoImpostoResource = new GrupoTributacaoImpostoResource(grupoTributacaoImpostoService);
        this.restGrupoTributacaoImpostoMockMvc = MockMvcBuilders.standaloneSetup(grupoTributacaoImpostoResource)
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
    public static GrupoTributacaoImposto createEntity(EntityManager em) {
        GrupoTributacaoImposto grupoTributacaoImposto = new GrupoTributacaoImposto()
            .nome(DEFAULT_NOME)
            .valor(DEFAULT_VALOR)
            .de(DEFAULT_DE)
            .ate(DEFAULT_ATE)
            .limiteLiquidacao(DEFAULT_LIMITE_LIQUIDACAO);
        // Add required entity
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            imposto = ImpostoResourceIT.createEntity(em);
            em.persist(imposto);
            em.flush();
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        grupoTributacaoImposto.setImposto(imposto);
        return grupoTributacaoImposto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoTributacaoImposto createUpdatedEntity(EntityManager em) {
        GrupoTributacaoImposto grupoTributacaoImposto = new GrupoTributacaoImposto()
            .nome(UPDATED_NOME)
            .valor(UPDATED_VALOR)
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .limiteLiquidacao(UPDATED_LIMITE_LIQUIDACAO);
        // Add required entity
        Imposto imposto;
        if (TestUtil.findAll(em, Imposto.class).isEmpty()) {
            imposto = ImpostoResourceIT.createUpdatedEntity(em);
            em.persist(imposto);
            em.flush();
        } else {
            imposto = TestUtil.findAll(em, Imposto.class).get(0);
        }
        grupoTributacaoImposto.setImposto(imposto);
        return grupoTributacaoImposto;
    }

    @BeforeEach
    public void initTest() {
        grupoTributacaoImposto = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupoTributacaoImposto() throws Exception {
        int databaseSizeBeforeCreate = grupoTributacaoImpostoRepository.findAll().size();

        // Create the GrupoTributacaoImposto
        GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO = grupoTributacaoImpostoMapper.toDto(grupoTributacaoImposto);
        restGrupoTributacaoImpostoMockMvc.perform(post("/api/grupo-tributacao-impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoTributacaoImpostoDTO)))
            .andExpect(status().isCreated());

        // Validate the GrupoTributacaoImposto in the database
        List<GrupoTributacaoImposto> grupoTributacaoImpostoList = grupoTributacaoImpostoRepository.findAll();
        assertThat(grupoTributacaoImpostoList).hasSize(databaseSizeBeforeCreate + 1);
        GrupoTributacaoImposto testGrupoTributacaoImposto = grupoTributacaoImpostoList.get(grupoTributacaoImpostoList.size() - 1);
        assertThat(testGrupoTributacaoImposto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testGrupoTributacaoImposto.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testGrupoTributacaoImposto.getDe()).isEqualTo(DEFAULT_DE);
        assertThat(testGrupoTributacaoImposto.getAte()).isEqualTo(DEFAULT_ATE);
        assertThat(testGrupoTributacaoImposto.getLimiteLiquidacao()).isEqualTo(DEFAULT_LIMITE_LIQUIDACAO);
    }

    @Test
    @Transactional
    public void createGrupoTributacaoImpostoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grupoTributacaoImpostoRepository.findAll().size();

        // Create the GrupoTributacaoImposto with an existing ID
        grupoTributacaoImposto.setId(1L);
        GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO = grupoTributacaoImpostoMapper.toDto(grupoTributacaoImposto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoTributacaoImpostoMockMvc.perform(post("/api/grupo-tributacao-impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoTributacaoImpostoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoTributacaoImposto in the database
        List<GrupoTributacaoImposto> grupoTributacaoImpostoList = grupoTributacaoImpostoRepository.findAll();
        assertThat(grupoTributacaoImpostoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoTributacaoImpostoRepository.findAll().size();
        // set the field null
        grupoTributacaoImposto.setNome(null);

        // Create the GrupoTributacaoImposto, which fails.
        GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO = grupoTributacaoImpostoMapper.toDto(grupoTributacaoImposto);

        restGrupoTributacaoImpostoMockMvc.perform(post("/api/grupo-tributacao-impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoTributacaoImpostoDTO)))
            .andExpect(status().isBadRequest());

        List<GrupoTributacaoImposto> grupoTributacaoImpostoList = grupoTributacaoImpostoRepository.findAll();
        assertThat(grupoTributacaoImpostoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoTributacaoImpostoRepository.findAll().size();
        // set the field null
        grupoTributacaoImposto.setValor(null);

        // Create the GrupoTributacaoImposto, which fails.
        GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO = grupoTributacaoImpostoMapper.toDto(grupoTributacaoImposto);

        restGrupoTributacaoImpostoMockMvc.perform(post("/api/grupo-tributacao-impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoTributacaoImpostoDTO)))
            .andExpect(status().isBadRequest());

        List<GrupoTributacaoImposto> grupoTributacaoImpostoList = grupoTributacaoImpostoRepository.findAll();
        assertThat(grupoTributacaoImpostoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrupoTributacaoImpostos() throws Exception {
        // Initialize the database
        grupoTributacaoImpostoRepository.saveAndFlush(grupoTributacaoImposto);

        // Get all the grupoTributacaoImpostoList
        restGrupoTributacaoImpostoMockMvc.perform(get("/api/grupo-tributacao-impostos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoTributacaoImposto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].de").value(hasItem(DEFAULT_DE.doubleValue())))
            .andExpect(jsonPath("$.[*].ate").value(hasItem(DEFAULT_ATE.doubleValue())))
            .andExpect(jsonPath("$.[*].limiteLiquidacao").value(hasItem(DEFAULT_LIMITE_LIQUIDACAO)));
    }
    
    @Test
    @Transactional
    public void getGrupoTributacaoImposto() throws Exception {
        // Initialize the database
        grupoTributacaoImpostoRepository.saveAndFlush(grupoTributacaoImposto);

        // Get the grupoTributacaoImposto
        restGrupoTributacaoImpostoMockMvc.perform(get("/api/grupo-tributacao-impostos/{id}", grupoTributacaoImposto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grupoTributacaoImposto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.de").value(DEFAULT_DE.doubleValue()))
            .andExpect(jsonPath("$.ate").value(DEFAULT_ATE.doubleValue()))
            .andExpect(jsonPath("$.limiteLiquidacao").value(DEFAULT_LIMITE_LIQUIDACAO));
    }

    @Test
    @Transactional
    public void getNonExistingGrupoTributacaoImposto() throws Exception {
        // Get the grupoTributacaoImposto
        restGrupoTributacaoImpostoMockMvc.perform(get("/api/grupo-tributacao-impostos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupoTributacaoImposto() throws Exception {
        // Initialize the database
        grupoTributacaoImpostoRepository.saveAndFlush(grupoTributacaoImposto);

        int databaseSizeBeforeUpdate = grupoTributacaoImpostoRepository.findAll().size();

        // Update the grupoTributacaoImposto
        GrupoTributacaoImposto updatedGrupoTributacaoImposto = grupoTributacaoImpostoRepository.findById(grupoTributacaoImposto.getId()).get();
        // Disconnect from session so that the updates on updatedGrupoTributacaoImposto are not directly saved in db
        em.detach(updatedGrupoTributacaoImposto);
        updatedGrupoTributacaoImposto
            .nome(UPDATED_NOME)
            .valor(UPDATED_VALOR)
            .de(UPDATED_DE)
            .ate(UPDATED_ATE)
            .limiteLiquidacao(UPDATED_LIMITE_LIQUIDACAO);
        GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO = grupoTributacaoImpostoMapper.toDto(updatedGrupoTributacaoImposto);

        restGrupoTributacaoImpostoMockMvc.perform(put("/api/grupo-tributacao-impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoTributacaoImpostoDTO)))
            .andExpect(status().isOk());

        // Validate the GrupoTributacaoImposto in the database
        List<GrupoTributacaoImposto> grupoTributacaoImpostoList = grupoTributacaoImpostoRepository.findAll();
        assertThat(grupoTributacaoImpostoList).hasSize(databaseSizeBeforeUpdate);
        GrupoTributacaoImposto testGrupoTributacaoImposto = grupoTributacaoImpostoList.get(grupoTributacaoImpostoList.size() - 1);
        assertThat(testGrupoTributacaoImposto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testGrupoTributacaoImposto.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testGrupoTributacaoImposto.getDe()).isEqualTo(UPDATED_DE);
        assertThat(testGrupoTributacaoImposto.getAte()).isEqualTo(UPDATED_ATE);
        assertThat(testGrupoTributacaoImposto.getLimiteLiquidacao()).isEqualTo(UPDATED_LIMITE_LIQUIDACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupoTributacaoImposto() throws Exception {
        int databaseSizeBeforeUpdate = grupoTributacaoImpostoRepository.findAll().size();

        // Create the GrupoTributacaoImposto
        GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO = grupoTributacaoImpostoMapper.toDto(grupoTributacaoImposto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoTributacaoImpostoMockMvc.perform(put("/api/grupo-tributacao-impostos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupoTributacaoImpostoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoTributacaoImposto in the database
        List<GrupoTributacaoImposto> grupoTributacaoImpostoList = grupoTributacaoImpostoRepository.findAll();
        assertThat(grupoTributacaoImpostoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrupoTributacaoImposto() throws Exception {
        // Initialize the database
        grupoTributacaoImpostoRepository.saveAndFlush(grupoTributacaoImposto);

        int databaseSizeBeforeDelete = grupoTributacaoImpostoRepository.findAll().size();

        // Delete the grupoTributacaoImposto
        restGrupoTributacaoImpostoMockMvc.perform(delete("/api/grupo-tributacao-impostos/{id}", grupoTributacaoImposto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrupoTributacaoImposto> grupoTributacaoImpostoList = grupoTributacaoImpostoRepository.findAll();
        assertThat(grupoTributacaoImpostoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
