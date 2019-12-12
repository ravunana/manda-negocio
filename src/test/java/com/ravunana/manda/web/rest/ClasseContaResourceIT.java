package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.ClasseConta;
import com.ravunana.manda.repository.ClasseContaRepository;
import com.ravunana.manda.service.ClasseContaService;
import com.ravunana.manda.service.dto.ClasseContaDTO;
import com.ravunana.manda.service.mapper.ClasseContaMapper;
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
 * Integration tests for the {@link ClasseContaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ClasseContaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    @Autowired
    private ClasseContaRepository classeContaRepository;

    @Autowired
    private ClasseContaMapper classeContaMapper;

    @Autowired
    private ClasseContaService classeContaService;

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

    private MockMvc restClasseContaMockMvc;

    private ClasseConta classeConta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClasseContaResource classeContaResource = new ClasseContaResource(classeContaService);
        this.restClasseContaMockMvc = MockMvcBuilders.standaloneSetup(classeContaResource)
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
    public static ClasseConta createEntity(EntityManager em) {
        ClasseConta classeConta = new ClasseConta()
            .descricao(DEFAULT_DESCRICAO)
            .codigo(DEFAULT_CODIGO);
        return classeConta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClasseConta createUpdatedEntity(EntityManager em) {
        ClasseConta classeConta = new ClasseConta()
            .descricao(UPDATED_DESCRICAO)
            .codigo(UPDATED_CODIGO);
        return classeConta;
    }

    @BeforeEach
    public void initTest() {
        classeConta = createEntity(em);
    }

    @Test
    @Transactional
    public void createClasseConta() throws Exception {
        int databaseSizeBeforeCreate = classeContaRepository.findAll().size();

        // Create the ClasseConta
        ClasseContaDTO classeContaDTO = classeContaMapper.toDto(classeConta);
        restClasseContaMockMvc.perform(post("/api/classe-contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classeContaDTO)))
            .andExpect(status().isCreated());

        // Validate the ClasseConta in the database
        List<ClasseConta> classeContaList = classeContaRepository.findAll();
        assertThat(classeContaList).hasSize(databaseSizeBeforeCreate + 1);
        ClasseConta testClasseConta = classeContaList.get(classeContaList.size() - 1);
        assertThat(testClasseConta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testClasseConta.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    public void createClasseContaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classeContaRepository.findAll().size();

        // Create the ClasseConta with an existing ID
        classeConta.setId(1L);
        ClasseContaDTO classeContaDTO = classeContaMapper.toDto(classeConta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasseContaMockMvc.perform(post("/api/classe-contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classeContaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClasseConta in the database
        List<ClasseConta> classeContaList = classeContaRepository.findAll();
        assertThat(classeContaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = classeContaRepository.findAll().size();
        // set the field null
        classeConta.setDescricao(null);

        // Create the ClasseConta, which fails.
        ClasseContaDTO classeContaDTO = classeContaMapper.toDto(classeConta);

        restClasseContaMockMvc.perform(post("/api/classe-contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classeContaDTO)))
            .andExpect(status().isBadRequest());

        List<ClasseConta> classeContaList = classeContaRepository.findAll();
        assertThat(classeContaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = classeContaRepository.findAll().size();
        // set the field null
        classeConta.setCodigo(null);

        // Create the ClasseConta, which fails.
        ClasseContaDTO classeContaDTO = classeContaMapper.toDto(classeConta);

        restClasseContaMockMvc.perform(post("/api/classe-contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classeContaDTO)))
            .andExpect(status().isBadRequest());

        List<ClasseConta> classeContaList = classeContaRepository.findAll();
        assertThat(classeContaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClasseContas() throws Exception {
        // Initialize the database
        classeContaRepository.saveAndFlush(classeConta);

        // Get all the classeContaList
        restClasseContaMockMvc.perform(get("/api/classe-contas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classeConta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }
    
    @Test
    @Transactional
    public void getClasseConta() throws Exception {
        // Initialize the database
        classeContaRepository.saveAndFlush(classeConta);

        // Get the classeConta
        restClasseContaMockMvc.perform(get("/api/classe-contas/{id}", classeConta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classeConta.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO));
    }

    @Test
    @Transactional
    public void getNonExistingClasseConta() throws Exception {
        // Get the classeConta
        restClasseContaMockMvc.perform(get("/api/classe-contas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClasseConta() throws Exception {
        // Initialize the database
        classeContaRepository.saveAndFlush(classeConta);

        int databaseSizeBeforeUpdate = classeContaRepository.findAll().size();

        // Update the classeConta
        ClasseConta updatedClasseConta = classeContaRepository.findById(classeConta.getId()).get();
        // Disconnect from session so that the updates on updatedClasseConta are not directly saved in db
        em.detach(updatedClasseConta);
        updatedClasseConta
            .descricao(UPDATED_DESCRICAO)
            .codigo(UPDATED_CODIGO);
        ClasseContaDTO classeContaDTO = classeContaMapper.toDto(updatedClasseConta);

        restClasseContaMockMvc.perform(put("/api/classe-contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classeContaDTO)))
            .andExpect(status().isOk());

        // Validate the ClasseConta in the database
        List<ClasseConta> classeContaList = classeContaRepository.findAll();
        assertThat(classeContaList).hasSize(databaseSizeBeforeUpdate);
        ClasseConta testClasseConta = classeContaList.get(classeContaList.size() - 1);
        assertThat(testClasseConta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testClasseConta.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void updateNonExistingClasseConta() throws Exception {
        int databaseSizeBeforeUpdate = classeContaRepository.findAll().size();

        // Create the ClasseConta
        ClasseContaDTO classeContaDTO = classeContaMapper.toDto(classeConta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasseContaMockMvc.perform(put("/api/classe-contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classeContaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClasseConta in the database
        List<ClasseConta> classeContaList = classeContaRepository.findAll();
        assertThat(classeContaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClasseConta() throws Exception {
        // Initialize the database
        classeContaRepository.saveAndFlush(classeConta);

        int databaseSizeBeforeDelete = classeContaRepository.findAll().size();

        // Delete the classeConta
        restClasseContaMockMvc.perform(delete("/api/classe-contas/{id}", classeConta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClasseConta> classeContaList = classeContaRepository.findAll();
        assertThat(classeContaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
