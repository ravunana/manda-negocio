package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.UnidadeMedida;
import com.ravunana.manda.repository.UnidadeMedidaRepository;
import com.ravunana.manda.service.UnidadeMedidaService;
import com.ravunana.manda.service.dto.UnidadeMedidaDTO;
import com.ravunana.manda.service.mapper.UnidadeMedidaMapper;
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
 * Integration tests for the {@link UnidadeMedidaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class UnidadeMedidaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final Double DEFAULT_VALOR = 0D;
    private static final Double UPDATED_VALOR = 1D;

    @Autowired
    private UnidadeMedidaRepository unidadeMedidaRepository;

    @Autowired
    private UnidadeMedidaMapper unidadeMedidaMapper;

    @Autowired
    private UnidadeMedidaService unidadeMedidaService;

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

    private MockMvc restUnidadeMedidaMockMvc;

    private UnidadeMedida unidadeMedida;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadeMedidaResource unidadeMedidaResource = new UnidadeMedidaResource(unidadeMedidaService);
        this.restUnidadeMedidaMockMvc = MockMvcBuilders.standaloneSetup(unidadeMedidaResource)
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
    public static UnidadeMedida createEntity(EntityManager em) {
        UnidadeMedida unidadeMedida = new UnidadeMedida()
            .nome(DEFAULT_NOME)
            .sigla(DEFAULT_SIGLA)
            .valor(DEFAULT_VALOR);
        return unidadeMedida;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeMedida createUpdatedEntity(EntityManager em) {
        UnidadeMedida unidadeMedida = new UnidadeMedida()
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .valor(UPDATED_VALOR);
        return unidadeMedida;
    }

    @BeforeEach
    public void initTest() {
        unidadeMedida = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadeMedida() throws Exception {
        int databaseSizeBeforeCreate = unidadeMedidaRepository.findAll().size();

        // Create the UnidadeMedida
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(unidadeMedida);
        restUnidadeMedidaMockMvc.perform(post("/api/unidade-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isCreated());

        // Validate the UnidadeMedida in the database
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadeMedida testUnidadeMedida = unidadeMedidaList.get(unidadeMedidaList.size() - 1);
        assertThat(testUnidadeMedida.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testUnidadeMedida.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testUnidadeMedida.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createUnidadeMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadeMedidaRepository.findAll().size();

        // Create the UnidadeMedida with an existing ID
        unidadeMedida.setId(1L);
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(unidadeMedida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeMedidaMockMvc.perform(post("/api/unidade-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeMedida in the database
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeMedidaRepository.findAll().size();
        // set the field null
        unidadeMedida.setNome(null);

        // Create the UnidadeMedida, which fails.
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(unidadeMedida);

        restUnidadeMedidaMockMvc.perform(post("/api/unidade-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeMedidaRepository.findAll().size();
        // set the field null
        unidadeMedida.setSigla(null);

        // Create the UnidadeMedida, which fails.
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(unidadeMedida);

        restUnidadeMedidaMockMvc.perform(post("/api/unidade-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeMedidaRepository.findAll().size();
        // set the field null
        unidadeMedida.setValor(null);

        // Create the UnidadeMedida, which fails.
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(unidadeMedida);

        restUnidadeMedidaMockMvc.perform(post("/api/unidade-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidadeMedidas() throws Exception {
        // Initialize the database
        unidadeMedidaRepository.saveAndFlush(unidadeMedida);

        // Get all the unidadeMedidaList
        restUnidadeMedidaMockMvc.perform(get("/api/unidade-medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getUnidadeMedida() throws Exception {
        // Initialize the database
        unidadeMedidaRepository.saveAndFlush(unidadeMedida);

        // Get the unidadeMedida
        restUnidadeMedidaMockMvc.perform(get("/api/unidade-medidas/{id}", unidadeMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadeMedida.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadeMedida() throws Exception {
        // Get the unidadeMedida
        restUnidadeMedidaMockMvc.perform(get("/api/unidade-medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadeMedida() throws Exception {
        // Initialize the database
        unidadeMedidaRepository.saveAndFlush(unidadeMedida);

        int databaseSizeBeforeUpdate = unidadeMedidaRepository.findAll().size();

        // Update the unidadeMedida
        UnidadeMedida updatedUnidadeMedida = unidadeMedidaRepository.findById(unidadeMedida.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadeMedida are not directly saved in db
        em.detach(updatedUnidadeMedida);
        updatedUnidadeMedida
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .valor(UPDATED_VALOR);
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(updatedUnidadeMedida);

        restUnidadeMedidaMockMvc.perform(put("/api/unidade-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isOk());

        // Validate the UnidadeMedida in the database
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeUpdate);
        UnidadeMedida testUnidadeMedida = unidadeMedidaList.get(unidadeMedidaList.size() - 1);
        assertThat(testUnidadeMedida.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testUnidadeMedida.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testUnidadeMedida.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadeMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadeMedidaRepository.findAll().size();

        // Create the UnidadeMedida
        UnidadeMedidaDTO unidadeMedidaDTO = unidadeMedidaMapper.toDto(unidadeMedida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeMedidaMockMvc.perform(put("/api/unidade-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeMedidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeMedida in the database
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnidadeMedida() throws Exception {
        // Initialize the database
        unidadeMedidaRepository.saveAndFlush(unidadeMedida);

        int databaseSizeBeforeDelete = unidadeMedidaRepository.findAll().size();

        // Delete the unidadeMedida
        restUnidadeMedidaMockMvc.perform(delete("/api/unidade-medidas/{id}", unidadeMedida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadeMedida> unidadeMedidaList = unidadeMedidaRepository.findAll();
        assertThat(unidadeMedidaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
