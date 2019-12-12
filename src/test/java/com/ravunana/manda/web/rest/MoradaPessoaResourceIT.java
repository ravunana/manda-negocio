package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.MoradaPessoa;
import com.ravunana.manda.domain.Pessoa;
import com.ravunana.manda.repository.MoradaPessoaRepository;
import com.ravunana.manda.service.MoradaPessoaService;
import com.ravunana.manda.service.dto.MoradaPessoaDTO;
import com.ravunana.manda.service.mapper.MoradaPessoaMapper;
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
 * Integration tests for the {@link MoradaPessoaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class MoradaPessoaResourceIT {

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_RUA = "AAAAAAAAAA";
    private static final String UPDATED_RUA = "BBBBBBBBBB";

    private static final String DEFAULT_QUARTEIRAO = "AAAAAAAAAA";
    private static final String UPDATED_QUARTEIRAO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_PORTA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PORTA = "BBBBBBBBBB";

    @Autowired
    private MoradaPessoaRepository moradaPessoaRepository;

    @Autowired
    private MoradaPessoaMapper moradaPessoaMapper;

    @Autowired
    private MoradaPessoaService moradaPessoaService;

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

    private MockMvc restMoradaPessoaMockMvc;

    private MoradaPessoa moradaPessoa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MoradaPessoaResource moradaPessoaResource = new MoradaPessoaResource(moradaPessoaService);
        this.restMoradaPessoaMockMvc = MockMvcBuilders.standaloneSetup(moradaPessoaResource)
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
    public static MoradaPessoa createEntity(EntityManager em) {
        MoradaPessoa moradaPessoa = new MoradaPessoa()
            .pais(DEFAULT_PAIS)
            .provincia(DEFAULT_PROVINCIA)
            .municipio(DEFAULT_MUNICIPIO)
            .bairro(DEFAULT_BAIRRO)
            .rua(DEFAULT_RUA)
            .quarteirao(DEFAULT_QUARTEIRAO)
            .numeroPorta(DEFAULT_NUMERO_PORTA);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        moradaPessoa.setPessoa(pessoa);
        return moradaPessoa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoradaPessoa createUpdatedEntity(EntityManager em) {
        MoradaPessoa moradaPessoa = new MoradaPessoa()
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .municipio(UPDATED_MUNICIPIO)
            .bairro(UPDATED_BAIRRO)
            .rua(UPDATED_RUA)
            .quarteirao(UPDATED_QUARTEIRAO)
            .numeroPorta(UPDATED_NUMERO_PORTA);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        moradaPessoa.setPessoa(pessoa);
        return moradaPessoa;
    }

    @BeforeEach
    public void initTest() {
        moradaPessoa = createEntity(em);
    }

    @Test
    @Transactional
    public void createMoradaPessoa() throws Exception {
        int databaseSizeBeforeCreate = moradaPessoaRepository.findAll().size();

        // Create the MoradaPessoa
        MoradaPessoaDTO moradaPessoaDTO = moradaPessoaMapper.toDto(moradaPessoa);
        restMoradaPessoaMockMvc.perform(post("/api/morada-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moradaPessoaDTO)))
            .andExpect(status().isCreated());

        // Validate the MoradaPessoa in the database
        List<MoradaPessoa> moradaPessoaList = moradaPessoaRepository.findAll();
        assertThat(moradaPessoaList).hasSize(databaseSizeBeforeCreate + 1);
        MoradaPessoa testMoradaPessoa = moradaPessoaList.get(moradaPessoaList.size() - 1);
        assertThat(testMoradaPessoa.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testMoradaPessoa.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testMoradaPessoa.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testMoradaPessoa.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testMoradaPessoa.getRua()).isEqualTo(DEFAULT_RUA);
        assertThat(testMoradaPessoa.getQuarteirao()).isEqualTo(DEFAULT_QUARTEIRAO);
        assertThat(testMoradaPessoa.getNumeroPorta()).isEqualTo(DEFAULT_NUMERO_PORTA);
    }

    @Test
    @Transactional
    public void createMoradaPessoaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moradaPessoaRepository.findAll().size();

        // Create the MoradaPessoa with an existing ID
        moradaPessoa.setId(1L);
        MoradaPessoaDTO moradaPessoaDTO = moradaPessoaMapper.toDto(moradaPessoa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoradaPessoaMockMvc.perform(post("/api/morada-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moradaPessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MoradaPessoa in the database
        List<MoradaPessoa> moradaPessoaList = moradaPessoaRepository.findAll();
        assertThat(moradaPessoaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = moradaPessoaRepository.findAll().size();
        // set the field null
        moradaPessoa.setBairro(null);

        // Create the MoradaPessoa, which fails.
        MoradaPessoaDTO moradaPessoaDTO = moradaPessoaMapper.toDto(moradaPessoa);

        restMoradaPessoaMockMvc.perform(post("/api/morada-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moradaPessoaDTO)))
            .andExpect(status().isBadRequest());

        List<MoradaPessoa> moradaPessoaList = moradaPessoaRepository.findAll();
        assertThat(moradaPessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuaIsRequired() throws Exception {
        int databaseSizeBeforeTest = moradaPessoaRepository.findAll().size();
        // set the field null
        moradaPessoa.setRua(null);

        // Create the MoradaPessoa, which fails.
        MoradaPessoaDTO moradaPessoaDTO = moradaPessoaMapper.toDto(moradaPessoa);

        restMoradaPessoaMockMvc.perform(post("/api/morada-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moradaPessoaDTO)))
            .andExpect(status().isBadRequest());

        List<MoradaPessoa> moradaPessoaList = moradaPessoaRepository.findAll();
        assertThat(moradaPessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMoradaPessoas() throws Exception {
        // Initialize the database
        moradaPessoaRepository.saveAndFlush(moradaPessoa);

        // Get all the moradaPessoaList
        restMoradaPessoaMockMvc.perform(get("/api/morada-pessoas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moradaPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA)))
            .andExpect(jsonPath("$.[*].quarteirao").value(hasItem(DEFAULT_QUARTEIRAO)))
            .andExpect(jsonPath("$.[*].numeroPorta").value(hasItem(DEFAULT_NUMERO_PORTA)));
    }
    
    @Test
    @Transactional
    public void getMoradaPessoa() throws Exception {
        // Initialize the database
        moradaPessoaRepository.saveAndFlush(moradaPessoa);

        // Get the moradaPessoa
        restMoradaPessoaMockMvc.perform(get("/api/morada-pessoas/{id}", moradaPessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moradaPessoa.getId().intValue()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.rua").value(DEFAULT_RUA))
            .andExpect(jsonPath("$.quarteirao").value(DEFAULT_QUARTEIRAO))
            .andExpect(jsonPath("$.numeroPorta").value(DEFAULT_NUMERO_PORTA));
    }

    @Test
    @Transactional
    public void getNonExistingMoradaPessoa() throws Exception {
        // Get the moradaPessoa
        restMoradaPessoaMockMvc.perform(get("/api/morada-pessoas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMoradaPessoa() throws Exception {
        // Initialize the database
        moradaPessoaRepository.saveAndFlush(moradaPessoa);

        int databaseSizeBeforeUpdate = moradaPessoaRepository.findAll().size();

        // Update the moradaPessoa
        MoradaPessoa updatedMoradaPessoa = moradaPessoaRepository.findById(moradaPessoa.getId()).get();
        // Disconnect from session so that the updates on updatedMoradaPessoa are not directly saved in db
        em.detach(updatedMoradaPessoa);
        updatedMoradaPessoa
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .municipio(UPDATED_MUNICIPIO)
            .bairro(UPDATED_BAIRRO)
            .rua(UPDATED_RUA)
            .quarteirao(UPDATED_QUARTEIRAO)
            .numeroPorta(UPDATED_NUMERO_PORTA);
        MoradaPessoaDTO moradaPessoaDTO = moradaPessoaMapper.toDto(updatedMoradaPessoa);

        restMoradaPessoaMockMvc.perform(put("/api/morada-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moradaPessoaDTO)))
            .andExpect(status().isOk());

        // Validate the MoradaPessoa in the database
        List<MoradaPessoa> moradaPessoaList = moradaPessoaRepository.findAll();
        assertThat(moradaPessoaList).hasSize(databaseSizeBeforeUpdate);
        MoradaPessoa testMoradaPessoa = moradaPessoaList.get(moradaPessoaList.size() - 1);
        assertThat(testMoradaPessoa.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testMoradaPessoa.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testMoradaPessoa.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testMoradaPessoa.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testMoradaPessoa.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testMoradaPessoa.getQuarteirao()).isEqualTo(UPDATED_QUARTEIRAO);
        assertThat(testMoradaPessoa.getNumeroPorta()).isEqualTo(UPDATED_NUMERO_PORTA);
    }

    @Test
    @Transactional
    public void updateNonExistingMoradaPessoa() throws Exception {
        int databaseSizeBeforeUpdate = moradaPessoaRepository.findAll().size();

        // Create the MoradaPessoa
        MoradaPessoaDTO moradaPessoaDTO = moradaPessoaMapper.toDto(moradaPessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoradaPessoaMockMvc.perform(put("/api/morada-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moradaPessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MoradaPessoa in the database
        List<MoradaPessoa> moradaPessoaList = moradaPessoaRepository.findAll();
        assertThat(moradaPessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMoradaPessoa() throws Exception {
        // Initialize the database
        moradaPessoaRepository.saveAndFlush(moradaPessoa);

        int databaseSizeBeforeDelete = moradaPessoaRepository.findAll().size();

        // Delete the moradaPessoa
        restMoradaPessoaMockMvc.perform(delete("/api/morada-pessoas/{id}", moradaPessoa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MoradaPessoa> moradaPessoaList = moradaPessoaRepository.findAll();
        assertThat(moradaPessoaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
