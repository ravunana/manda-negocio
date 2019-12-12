package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.LocalArmazenamento;
import com.ravunana.manda.repository.LocalArmazenamentoRepository;
import com.ravunana.manda.service.LocalArmazenamentoService;
import com.ravunana.manda.service.dto.LocalArmazenamentoDTO;
import com.ravunana.manda.service.mapper.LocalArmazenamentoMapper;
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
 * Integration tests for the {@link LocalArmazenamentoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class LocalArmazenamentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private LocalArmazenamentoRepository localArmazenamentoRepository;

    @Autowired
    private LocalArmazenamentoMapper localArmazenamentoMapper;

    @Autowired
    private LocalArmazenamentoService localArmazenamentoService;

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

    private MockMvc restLocalArmazenamentoMockMvc;

    private LocalArmazenamento localArmazenamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalArmazenamentoResource localArmazenamentoResource = new LocalArmazenamentoResource(localArmazenamentoService);
        this.restLocalArmazenamentoMockMvc = MockMvcBuilders.standaloneSetup(localArmazenamentoResource)
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
    public static LocalArmazenamento createEntity(EntityManager em) {
        LocalArmazenamento localArmazenamento = new LocalArmazenamento()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO);
        return localArmazenamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocalArmazenamento createUpdatedEntity(EntityManager em) {
        LocalArmazenamento localArmazenamento = new LocalArmazenamento()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        return localArmazenamento;
    }

    @BeforeEach
    public void initTest() {
        localArmazenamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalArmazenamento() throws Exception {
        int databaseSizeBeforeCreate = localArmazenamentoRepository.findAll().size();

        // Create the LocalArmazenamento
        LocalArmazenamentoDTO localArmazenamentoDTO = localArmazenamentoMapper.toDto(localArmazenamento);
        restLocalArmazenamentoMockMvc.perform(post("/api/local-armazenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localArmazenamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the LocalArmazenamento in the database
        List<LocalArmazenamento> localArmazenamentoList = localArmazenamentoRepository.findAll();
        assertThat(localArmazenamentoList).hasSize(databaseSizeBeforeCreate + 1);
        LocalArmazenamento testLocalArmazenamento = localArmazenamentoList.get(localArmazenamentoList.size() - 1);
        assertThat(testLocalArmazenamento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testLocalArmazenamento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createLocalArmazenamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localArmazenamentoRepository.findAll().size();

        // Create the LocalArmazenamento with an existing ID
        localArmazenamento.setId(1L);
        LocalArmazenamentoDTO localArmazenamentoDTO = localArmazenamentoMapper.toDto(localArmazenamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalArmazenamentoMockMvc.perform(post("/api/local-armazenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localArmazenamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocalArmazenamento in the database
        List<LocalArmazenamento> localArmazenamentoList = localArmazenamentoRepository.findAll();
        assertThat(localArmazenamentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = localArmazenamentoRepository.findAll().size();
        // set the field null
        localArmazenamento.setNome(null);

        // Create the LocalArmazenamento, which fails.
        LocalArmazenamentoDTO localArmazenamentoDTO = localArmazenamentoMapper.toDto(localArmazenamento);

        restLocalArmazenamentoMockMvc.perform(post("/api/local-armazenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localArmazenamentoDTO)))
            .andExpect(status().isBadRequest());

        List<LocalArmazenamento> localArmazenamentoList = localArmazenamentoRepository.findAll();
        assertThat(localArmazenamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocalArmazenamentos() throws Exception {
        // Initialize the database
        localArmazenamentoRepository.saveAndFlush(localArmazenamento);

        // Get all the localArmazenamentoList
        restLocalArmazenamentoMockMvc.perform(get("/api/local-armazenamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localArmazenamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getLocalArmazenamento() throws Exception {
        // Initialize the database
        localArmazenamentoRepository.saveAndFlush(localArmazenamento);

        // Get the localArmazenamento
        restLocalArmazenamentoMockMvc.perform(get("/api/local-armazenamentos/{id}", localArmazenamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localArmazenamento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalArmazenamento() throws Exception {
        // Get the localArmazenamento
        restLocalArmazenamentoMockMvc.perform(get("/api/local-armazenamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalArmazenamento() throws Exception {
        // Initialize the database
        localArmazenamentoRepository.saveAndFlush(localArmazenamento);

        int databaseSizeBeforeUpdate = localArmazenamentoRepository.findAll().size();

        // Update the localArmazenamento
        LocalArmazenamento updatedLocalArmazenamento = localArmazenamentoRepository.findById(localArmazenamento.getId()).get();
        // Disconnect from session so that the updates on updatedLocalArmazenamento are not directly saved in db
        em.detach(updatedLocalArmazenamento);
        updatedLocalArmazenamento
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        LocalArmazenamentoDTO localArmazenamentoDTO = localArmazenamentoMapper.toDto(updatedLocalArmazenamento);

        restLocalArmazenamentoMockMvc.perform(put("/api/local-armazenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localArmazenamentoDTO)))
            .andExpect(status().isOk());

        // Validate the LocalArmazenamento in the database
        List<LocalArmazenamento> localArmazenamentoList = localArmazenamentoRepository.findAll();
        assertThat(localArmazenamentoList).hasSize(databaseSizeBeforeUpdate);
        LocalArmazenamento testLocalArmazenamento = localArmazenamentoList.get(localArmazenamentoList.size() - 1);
        assertThat(testLocalArmazenamento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLocalArmazenamento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalArmazenamento() throws Exception {
        int databaseSizeBeforeUpdate = localArmazenamentoRepository.findAll().size();

        // Create the LocalArmazenamento
        LocalArmazenamentoDTO localArmazenamentoDTO = localArmazenamentoMapper.toDto(localArmazenamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalArmazenamentoMockMvc.perform(put("/api/local-armazenamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localArmazenamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocalArmazenamento in the database
        List<LocalArmazenamento> localArmazenamentoList = localArmazenamentoRepository.findAll();
        assertThat(localArmazenamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocalArmazenamento() throws Exception {
        // Initialize the database
        localArmazenamentoRepository.saveAndFlush(localArmazenamento);

        int databaseSizeBeforeDelete = localArmazenamentoRepository.findAll().size();

        // Delete the localArmazenamento
        restLocalArmazenamentoMockMvc.perform(delete("/api/local-armazenamentos/{id}", localArmazenamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocalArmazenamento> localArmazenamentoList = localArmazenamentoRepository.findAll();
        assertThat(localArmazenamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
