package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.RelacionamentoPessoa;
import com.ravunana.manda.domain.Pessoa;
import com.ravunana.manda.repository.RelacionamentoPessoaRepository;
import com.ravunana.manda.service.RelacionamentoPessoaService;
import com.ravunana.manda.service.dto.RelacionamentoPessoaDTO;
import com.ravunana.manda.service.mapper.RelacionamentoPessoaMapper;
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
 * Integration tests for the {@link RelacionamentoPessoaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class RelacionamentoPessoaResourceIT {

    private static final String DEFAULT_GRAU_PARENTESCO = "AAAAAAAAAA";
    private static final String UPDATED_GRAU_PARENTESCO = "BBBBBBBBBB";

    @Autowired
    private RelacionamentoPessoaRepository relacionamentoPessoaRepository;

    @Autowired
    private RelacionamentoPessoaMapper relacionamentoPessoaMapper;

    @Autowired
    private RelacionamentoPessoaService relacionamentoPessoaService;

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

    private MockMvc restRelacionamentoPessoaMockMvc;

    private RelacionamentoPessoa relacionamentoPessoa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RelacionamentoPessoaResource relacionamentoPessoaResource = new RelacionamentoPessoaResource(relacionamentoPessoaService);
        this.restRelacionamentoPessoaMockMvc = MockMvcBuilders.standaloneSetup(relacionamentoPessoaResource)
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
    public static RelacionamentoPessoa createEntity(EntityManager em) {
        RelacionamentoPessoa relacionamentoPessoa = new RelacionamentoPessoa()
            .grauParentesco(DEFAULT_GRAU_PARENTESCO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        relacionamentoPessoa.setDe(pessoa);
        // Add required entity
        relacionamentoPessoa.setPara(pessoa);
        return relacionamentoPessoa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelacionamentoPessoa createUpdatedEntity(EntityManager em) {
        RelacionamentoPessoa relacionamentoPessoa = new RelacionamentoPessoa()
            .grauParentesco(UPDATED_GRAU_PARENTESCO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        relacionamentoPessoa.setDe(pessoa);
        // Add required entity
        relacionamentoPessoa.setPara(pessoa);
        return relacionamentoPessoa;
    }

    @BeforeEach
    public void initTest() {
        relacionamentoPessoa = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelacionamentoPessoa() throws Exception {
        int databaseSizeBeforeCreate = relacionamentoPessoaRepository.findAll().size();

        // Create the RelacionamentoPessoa
        RelacionamentoPessoaDTO relacionamentoPessoaDTO = relacionamentoPessoaMapper.toDto(relacionamentoPessoa);
        restRelacionamentoPessoaMockMvc.perform(post("/api/relacionamento-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relacionamentoPessoaDTO)))
            .andExpect(status().isCreated());

        // Validate the RelacionamentoPessoa in the database
        List<RelacionamentoPessoa> relacionamentoPessoaList = relacionamentoPessoaRepository.findAll();
        assertThat(relacionamentoPessoaList).hasSize(databaseSizeBeforeCreate + 1);
        RelacionamentoPessoa testRelacionamentoPessoa = relacionamentoPessoaList.get(relacionamentoPessoaList.size() - 1);
        assertThat(testRelacionamentoPessoa.getGrauParentesco()).isEqualTo(DEFAULT_GRAU_PARENTESCO);
    }

    @Test
    @Transactional
    public void createRelacionamentoPessoaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relacionamentoPessoaRepository.findAll().size();

        // Create the RelacionamentoPessoa with an existing ID
        relacionamentoPessoa.setId(1L);
        RelacionamentoPessoaDTO relacionamentoPessoaDTO = relacionamentoPessoaMapper.toDto(relacionamentoPessoa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelacionamentoPessoaMockMvc.perform(post("/api/relacionamento-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relacionamentoPessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RelacionamentoPessoa in the database
        List<RelacionamentoPessoa> relacionamentoPessoaList = relacionamentoPessoaRepository.findAll();
        assertThat(relacionamentoPessoaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGrauParentescoIsRequired() throws Exception {
        int databaseSizeBeforeTest = relacionamentoPessoaRepository.findAll().size();
        // set the field null
        relacionamentoPessoa.setGrauParentesco(null);

        // Create the RelacionamentoPessoa, which fails.
        RelacionamentoPessoaDTO relacionamentoPessoaDTO = relacionamentoPessoaMapper.toDto(relacionamentoPessoa);

        restRelacionamentoPessoaMockMvc.perform(post("/api/relacionamento-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relacionamentoPessoaDTO)))
            .andExpect(status().isBadRequest());

        List<RelacionamentoPessoa> relacionamentoPessoaList = relacionamentoPessoaRepository.findAll();
        assertThat(relacionamentoPessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRelacionamentoPessoas() throws Exception {
        // Initialize the database
        relacionamentoPessoaRepository.saveAndFlush(relacionamentoPessoa);

        // Get all the relacionamentoPessoaList
        restRelacionamentoPessoaMockMvc.perform(get("/api/relacionamento-pessoas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relacionamentoPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].grauParentesco").value(hasItem(DEFAULT_GRAU_PARENTESCO)));
    }
    
    @Test
    @Transactional
    public void getRelacionamentoPessoa() throws Exception {
        // Initialize the database
        relacionamentoPessoaRepository.saveAndFlush(relacionamentoPessoa);

        // Get the relacionamentoPessoa
        restRelacionamentoPessoaMockMvc.perform(get("/api/relacionamento-pessoas/{id}", relacionamentoPessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relacionamentoPessoa.getId().intValue()))
            .andExpect(jsonPath("$.grauParentesco").value(DEFAULT_GRAU_PARENTESCO));
    }

    @Test
    @Transactional
    public void getNonExistingRelacionamentoPessoa() throws Exception {
        // Get the relacionamentoPessoa
        restRelacionamentoPessoaMockMvc.perform(get("/api/relacionamento-pessoas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelacionamentoPessoa() throws Exception {
        // Initialize the database
        relacionamentoPessoaRepository.saveAndFlush(relacionamentoPessoa);

        int databaseSizeBeforeUpdate = relacionamentoPessoaRepository.findAll().size();

        // Update the relacionamentoPessoa
        RelacionamentoPessoa updatedRelacionamentoPessoa = relacionamentoPessoaRepository.findById(relacionamentoPessoa.getId()).get();
        // Disconnect from session so that the updates on updatedRelacionamentoPessoa are not directly saved in db
        em.detach(updatedRelacionamentoPessoa);
        updatedRelacionamentoPessoa
            .grauParentesco(UPDATED_GRAU_PARENTESCO);
        RelacionamentoPessoaDTO relacionamentoPessoaDTO = relacionamentoPessoaMapper.toDto(updatedRelacionamentoPessoa);

        restRelacionamentoPessoaMockMvc.perform(put("/api/relacionamento-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relacionamentoPessoaDTO)))
            .andExpect(status().isOk());

        // Validate the RelacionamentoPessoa in the database
        List<RelacionamentoPessoa> relacionamentoPessoaList = relacionamentoPessoaRepository.findAll();
        assertThat(relacionamentoPessoaList).hasSize(databaseSizeBeforeUpdate);
        RelacionamentoPessoa testRelacionamentoPessoa = relacionamentoPessoaList.get(relacionamentoPessoaList.size() - 1);
        assertThat(testRelacionamentoPessoa.getGrauParentesco()).isEqualTo(UPDATED_GRAU_PARENTESCO);
    }

    @Test
    @Transactional
    public void updateNonExistingRelacionamentoPessoa() throws Exception {
        int databaseSizeBeforeUpdate = relacionamentoPessoaRepository.findAll().size();

        // Create the RelacionamentoPessoa
        RelacionamentoPessoaDTO relacionamentoPessoaDTO = relacionamentoPessoaMapper.toDto(relacionamentoPessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelacionamentoPessoaMockMvc.perform(put("/api/relacionamento-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relacionamentoPessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RelacionamentoPessoa in the database
        List<RelacionamentoPessoa> relacionamentoPessoaList = relacionamentoPessoaRepository.findAll();
        assertThat(relacionamentoPessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRelacionamentoPessoa() throws Exception {
        // Initialize the database
        relacionamentoPessoaRepository.saveAndFlush(relacionamentoPessoa);

        int databaseSizeBeforeDelete = relacionamentoPessoaRepository.findAll().size();

        // Delete the relacionamentoPessoa
        restRelacionamentoPessoaMockMvc.perform(delete("/api/relacionamento-pessoas/{id}", relacionamentoPessoa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RelacionamentoPessoa> relacionamentoPessoaList = relacionamentoPessoaRepository.findAll();
        assertThat(relacionamentoPessoaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
