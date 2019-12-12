package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.RetencaoFonte;
import com.ravunana.manda.repository.RetencaoFonteRepository;
import com.ravunana.manda.service.RetencaoFonteService;
import com.ravunana.manda.service.dto.RetencaoFonteDTO;
import com.ravunana.manda.service.mapper.RetencaoFonteMapper;
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
 * Integration tests for the {@link RetencaoFonteResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class RetencaoFonteResourceIT {

    private static final String DEFAULT_MOTIVO_RETENCAO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO_RETENCAO = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PORCENTAGEM = false;
    private static final Boolean UPDATED_PORCENTAGEM = true;

    @Autowired
    private RetencaoFonteRepository retencaoFonteRepository;

    @Autowired
    private RetencaoFonteMapper retencaoFonteMapper;

    @Autowired
    private RetencaoFonteService retencaoFonteService;

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

    private MockMvc restRetencaoFonteMockMvc;

    private RetencaoFonte retencaoFonte;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RetencaoFonteResource retencaoFonteResource = new RetencaoFonteResource(retencaoFonteService);
        this.restRetencaoFonteMockMvc = MockMvcBuilders.standaloneSetup(retencaoFonteResource)
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
    public static RetencaoFonte createEntity(EntityManager em) {
        RetencaoFonte retencaoFonte = new RetencaoFonte()
            .motivoRetencao(DEFAULT_MOTIVO_RETENCAO)
            .valor(DEFAULT_VALOR)
            .porcentagem(DEFAULT_PORCENTAGEM);
        return retencaoFonte;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RetencaoFonte createUpdatedEntity(EntityManager em) {
        RetencaoFonte retencaoFonte = new RetencaoFonte()
            .motivoRetencao(UPDATED_MOTIVO_RETENCAO)
            .valor(UPDATED_VALOR)
            .porcentagem(UPDATED_PORCENTAGEM);
        return retencaoFonte;
    }

    @BeforeEach
    public void initTest() {
        retencaoFonte = createEntity(em);
    }

    @Test
    @Transactional
    public void createRetencaoFonte() throws Exception {
        int databaseSizeBeforeCreate = retencaoFonteRepository.findAll().size();

        // Create the RetencaoFonte
        RetencaoFonteDTO retencaoFonteDTO = retencaoFonteMapper.toDto(retencaoFonte);
        restRetencaoFonteMockMvc.perform(post("/api/retencao-fontes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retencaoFonteDTO)))
            .andExpect(status().isCreated());

        // Validate the RetencaoFonte in the database
        List<RetencaoFonte> retencaoFonteList = retencaoFonteRepository.findAll();
        assertThat(retencaoFonteList).hasSize(databaseSizeBeforeCreate + 1);
        RetencaoFonte testRetencaoFonte = retencaoFonteList.get(retencaoFonteList.size() - 1);
        assertThat(testRetencaoFonte.getMotivoRetencao()).isEqualTo(DEFAULT_MOTIVO_RETENCAO);
        assertThat(testRetencaoFonte.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testRetencaoFonte.isPorcentagem()).isEqualTo(DEFAULT_PORCENTAGEM);
    }

    @Test
    @Transactional
    public void createRetencaoFonteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = retencaoFonteRepository.findAll().size();

        // Create the RetencaoFonte with an existing ID
        retencaoFonte.setId(1L);
        RetencaoFonteDTO retencaoFonteDTO = retencaoFonteMapper.toDto(retencaoFonte);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRetencaoFonteMockMvc.perform(post("/api/retencao-fontes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retencaoFonteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RetencaoFonte in the database
        List<RetencaoFonte> retencaoFonteList = retencaoFonteRepository.findAll();
        assertThat(retencaoFonteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRetencaoFontes() throws Exception {
        // Initialize the database
        retencaoFonteRepository.saveAndFlush(retencaoFonte);

        // Get all the retencaoFonteList
        restRetencaoFonteMockMvc.perform(get("/api/retencao-fontes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(retencaoFonte.getId().intValue())))
            .andExpect(jsonPath("$.[*].motivoRetencao").value(hasItem(DEFAULT_MOTIVO_RETENCAO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].porcentagem").value(hasItem(DEFAULT_PORCENTAGEM.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRetencaoFonte() throws Exception {
        // Initialize the database
        retencaoFonteRepository.saveAndFlush(retencaoFonte);

        // Get the retencaoFonte
        restRetencaoFonteMockMvc.perform(get("/api/retencao-fontes/{id}", retencaoFonte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(retencaoFonte.getId().intValue()))
            .andExpect(jsonPath("$.motivoRetencao").value(DEFAULT_MOTIVO_RETENCAO))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.porcentagem").value(DEFAULT_PORCENTAGEM.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRetencaoFonte() throws Exception {
        // Get the retencaoFonte
        restRetencaoFonteMockMvc.perform(get("/api/retencao-fontes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRetencaoFonte() throws Exception {
        // Initialize the database
        retencaoFonteRepository.saveAndFlush(retencaoFonte);

        int databaseSizeBeforeUpdate = retencaoFonteRepository.findAll().size();

        // Update the retencaoFonte
        RetencaoFonte updatedRetencaoFonte = retencaoFonteRepository.findById(retencaoFonte.getId()).get();
        // Disconnect from session so that the updates on updatedRetencaoFonte are not directly saved in db
        em.detach(updatedRetencaoFonte);
        updatedRetencaoFonte
            .motivoRetencao(UPDATED_MOTIVO_RETENCAO)
            .valor(UPDATED_VALOR)
            .porcentagem(UPDATED_PORCENTAGEM);
        RetencaoFonteDTO retencaoFonteDTO = retencaoFonteMapper.toDto(updatedRetencaoFonte);

        restRetencaoFonteMockMvc.perform(put("/api/retencao-fontes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retencaoFonteDTO)))
            .andExpect(status().isOk());

        // Validate the RetencaoFonte in the database
        List<RetencaoFonte> retencaoFonteList = retencaoFonteRepository.findAll();
        assertThat(retencaoFonteList).hasSize(databaseSizeBeforeUpdate);
        RetencaoFonte testRetencaoFonte = retencaoFonteList.get(retencaoFonteList.size() - 1);
        assertThat(testRetencaoFonte.getMotivoRetencao()).isEqualTo(UPDATED_MOTIVO_RETENCAO);
        assertThat(testRetencaoFonte.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testRetencaoFonte.isPorcentagem()).isEqualTo(UPDATED_PORCENTAGEM);
    }

    @Test
    @Transactional
    public void updateNonExistingRetencaoFonte() throws Exception {
        int databaseSizeBeforeUpdate = retencaoFonteRepository.findAll().size();

        // Create the RetencaoFonte
        RetencaoFonteDTO retencaoFonteDTO = retencaoFonteMapper.toDto(retencaoFonte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRetencaoFonteMockMvc.perform(put("/api/retencao-fontes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retencaoFonteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RetencaoFonte in the database
        List<RetencaoFonte> retencaoFonteList = retencaoFonteRepository.findAll();
        assertThat(retencaoFonteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRetencaoFonte() throws Exception {
        // Initialize the database
        retencaoFonteRepository.saveAndFlush(retencaoFonte);

        int databaseSizeBeforeDelete = retencaoFonteRepository.findAll().size();

        // Delete the retencaoFonte
        restRetencaoFonteMockMvc.perform(delete("/api/retencao-fontes/{id}", retencaoFonte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RetencaoFonte> retencaoFonteList = retencaoFonteRepository.findAll();
        assertThat(retencaoFonteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
