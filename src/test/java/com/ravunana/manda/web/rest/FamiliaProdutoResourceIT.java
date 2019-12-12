package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.FamiliaProduto;
import com.ravunana.manda.repository.FamiliaProdutoRepository;
import com.ravunana.manda.service.FamiliaProdutoService;
import com.ravunana.manda.service.dto.FamiliaProdutoDTO;
import com.ravunana.manda.service.mapper.FamiliaProdutoMapper;
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
 * Integration tests for the {@link FamiliaProdutoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class FamiliaProdutoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private FamiliaProdutoRepository familiaProdutoRepository;

    @Autowired
    private FamiliaProdutoMapper familiaProdutoMapper;

    @Autowired
    private FamiliaProdutoService familiaProdutoService;

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

    private MockMvc restFamiliaProdutoMockMvc;

    private FamiliaProduto familiaProduto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamiliaProdutoResource familiaProdutoResource = new FamiliaProdutoResource(familiaProdutoService);
        this.restFamiliaProdutoMockMvc = MockMvcBuilders.standaloneSetup(familiaProdutoResource)
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
    public static FamiliaProduto createEntity(EntityManager em) {
        FamiliaProduto familiaProduto = new FamiliaProduto()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO);
        return familiaProduto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamiliaProduto createUpdatedEntity(EntityManager em) {
        FamiliaProduto familiaProduto = new FamiliaProduto()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        return familiaProduto;
    }

    @BeforeEach
    public void initTest() {
        familiaProduto = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamiliaProduto() throws Exception {
        int databaseSizeBeforeCreate = familiaProdutoRepository.findAll().size();

        // Create the FamiliaProduto
        FamiliaProdutoDTO familiaProdutoDTO = familiaProdutoMapper.toDto(familiaProduto);
        restFamiliaProdutoMockMvc.perform(post("/api/familia-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiaProdutoDTO)))
            .andExpect(status().isCreated());

        // Validate the FamiliaProduto in the database
        List<FamiliaProduto> familiaProdutoList = familiaProdutoRepository.findAll();
        assertThat(familiaProdutoList).hasSize(databaseSizeBeforeCreate + 1);
        FamiliaProduto testFamiliaProduto = familiaProdutoList.get(familiaProdutoList.size() - 1);
        assertThat(testFamiliaProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFamiliaProduto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createFamiliaProdutoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familiaProdutoRepository.findAll().size();

        // Create the FamiliaProduto with an existing ID
        familiaProduto.setId(1L);
        FamiliaProdutoDTO familiaProdutoDTO = familiaProdutoMapper.toDto(familiaProduto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamiliaProdutoMockMvc.perform(post("/api/familia-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiaProdutoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FamiliaProduto in the database
        List<FamiliaProduto> familiaProdutoList = familiaProdutoRepository.findAll();
        assertThat(familiaProdutoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = familiaProdutoRepository.findAll().size();
        // set the field null
        familiaProduto.setNome(null);

        // Create the FamiliaProduto, which fails.
        FamiliaProdutoDTO familiaProdutoDTO = familiaProdutoMapper.toDto(familiaProduto);

        restFamiliaProdutoMockMvc.perform(post("/api/familia-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiaProdutoDTO)))
            .andExpect(status().isBadRequest());

        List<FamiliaProduto> familiaProdutoList = familiaProdutoRepository.findAll();
        assertThat(familiaProdutoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFamiliaProdutos() throws Exception {
        // Initialize the database
        familiaProdutoRepository.saveAndFlush(familiaProduto);

        // Get all the familiaProdutoList
        restFamiliaProdutoMockMvc.perform(get("/api/familia-produtos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familiaProduto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getFamiliaProduto() throws Exception {
        // Initialize the database
        familiaProdutoRepository.saveAndFlush(familiaProduto);

        // Get the familiaProduto
        restFamiliaProdutoMockMvc.perform(get("/api/familia-produtos/{id}", familiaProduto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(familiaProduto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFamiliaProduto() throws Exception {
        // Get the familiaProduto
        restFamiliaProdutoMockMvc.perform(get("/api/familia-produtos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamiliaProduto() throws Exception {
        // Initialize the database
        familiaProdutoRepository.saveAndFlush(familiaProduto);

        int databaseSizeBeforeUpdate = familiaProdutoRepository.findAll().size();

        // Update the familiaProduto
        FamiliaProduto updatedFamiliaProduto = familiaProdutoRepository.findById(familiaProduto.getId()).get();
        // Disconnect from session so that the updates on updatedFamiliaProduto are not directly saved in db
        em.detach(updatedFamiliaProduto);
        updatedFamiliaProduto
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        FamiliaProdutoDTO familiaProdutoDTO = familiaProdutoMapper.toDto(updatedFamiliaProduto);

        restFamiliaProdutoMockMvc.perform(put("/api/familia-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiaProdutoDTO)))
            .andExpect(status().isOk());

        // Validate the FamiliaProduto in the database
        List<FamiliaProduto> familiaProdutoList = familiaProdutoRepository.findAll();
        assertThat(familiaProdutoList).hasSize(databaseSizeBeforeUpdate);
        FamiliaProduto testFamiliaProduto = familiaProdutoList.get(familiaProdutoList.size() - 1);
        assertThat(testFamiliaProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFamiliaProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingFamiliaProduto() throws Exception {
        int databaseSizeBeforeUpdate = familiaProdutoRepository.findAll().size();

        // Create the FamiliaProduto
        FamiliaProdutoDTO familiaProdutoDTO = familiaProdutoMapper.toDto(familiaProduto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamiliaProdutoMockMvc.perform(put("/api/familia-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(familiaProdutoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FamiliaProduto in the database
        List<FamiliaProduto> familiaProdutoList = familiaProdutoRepository.findAll();
        assertThat(familiaProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFamiliaProduto() throws Exception {
        // Initialize the database
        familiaProdutoRepository.saveAndFlush(familiaProduto);

        int databaseSizeBeforeDelete = familiaProdutoRepository.findAll().size();

        // Delete the familiaProduto
        restFamiliaProdutoMockMvc.perform(delete("/api/familia-produtos/{id}", familiaProduto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FamiliaProduto> familiaProdutoList = familiaProdutoRepository.findAll();
        assertThat(familiaProdutoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
