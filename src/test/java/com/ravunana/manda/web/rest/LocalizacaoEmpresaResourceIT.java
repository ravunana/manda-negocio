package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.LocalizacaoEmpresa;
import com.ravunana.manda.domain.Empresa;
import com.ravunana.manda.repository.LocalizacaoEmpresaRepository;
import com.ravunana.manda.service.LocalizacaoEmpresaService;
import com.ravunana.manda.service.dto.LocalizacaoEmpresaDTO;
import com.ravunana.manda.service.mapper.LocalizacaoEmpresaMapper;
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
 * Integration tests for the {@link LocalizacaoEmpresaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class LocalizacaoEmpresaResourceIT {

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

    private static final String DEFAULT_CAIXA_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CAIXA_POSTAL = "BBBBBBBBBB";

    @Autowired
    private LocalizacaoEmpresaRepository localizacaoEmpresaRepository;

    @Autowired
    private LocalizacaoEmpresaMapper localizacaoEmpresaMapper;

    @Autowired
    private LocalizacaoEmpresaService localizacaoEmpresaService;

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

    private MockMvc restLocalizacaoEmpresaMockMvc;

    private LocalizacaoEmpresa localizacaoEmpresa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalizacaoEmpresaResource localizacaoEmpresaResource = new LocalizacaoEmpresaResource(localizacaoEmpresaService);
        this.restLocalizacaoEmpresaMockMvc = MockMvcBuilders.standaloneSetup(localizacaoEmpresaResource)
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
    public static LocalizacaoEmpresa createEntity(EntityManager em) {
        LocalizacaoEmpresa localizacaoEmpresa = new LocalizacaoEmpresa()
            .pais(DEFAULT_PAIS)
            .provincia(DEFAULT_PROVINCIA)
            .municipio(DEFAULT_MUNICIPIO)
            .bairro(DEFAULT_BAIRRO)
            .rua(DEFAULT_RUA)
            .quarteirao(DEFAULT_QUARTEIRAO)
            .numeroPorta(DEFAULT_NUMERO_PORTA)
            .caixaPostal(DEFAULT_CAIXA_POSTAL);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        localizacaoEmpresa.setEmpresa(empresa);
        return localizacaoEmpresa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocalizacaoEmpresa createUpdatedEntity(EntityManager em) {
        LocalizacaoEmpresa localizacaoEmpresa = new LocalizacaoEmpresa()
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .municipio(UPDATED_MUNICIPIO)
            .bairro(UPDATED_BAIRRO)
            .rua(UPDATED_RUA)
            .quarteirao(UPDATED_QUARTEIRAO)
            .numeroPorta(UPDATED_NUMERO_PORTA)
            .caixaPostal(UPDATED_CAIXA_POSTAL);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        localizacaoEmpresa.setEmpresa(empresa);
        return localizacaoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        localizacaoEmpresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalizacaoEmpresa() throws Exception {
        int databaseSizeBeforeCreate = localizacaoEmpresaRepository.findAll().size();

        // Create the LocalizacaoEmpresa
        LocalizacaoEmpresaDTO localizacaoEmpresaDTO = localizacaoEmpresaMapper.toDto(localizacaoEmpresa);
        restLocalizacaoEmpresaMockMvc.perform(post("/api/localizacao-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoEmpresaDTO)))
            .andExpect(status().isCreated());

        // Validate the LocalizacaoEmpresa in the database
        List<LocalizacaoEmpresa> localizacaoEmpresaList = localizacaoEmpresaRepository.findAll();
        assertThat(localizacaoEmpresaList).hasSize(databaseSizeBeforeCreate + 1);
        LocalizacaoEmpresa testLocalizacaoEmpresa = localizacaoEmpresaList.get(localizacaoEmpresaList.size() - 1);
        assertThat(testLocalizacaoEmpresa.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testLocalizacaoEmpresa.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testLocalizacaoEmpresa.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testLocalizacaoEmpresa.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testLocalizacaoEmpresa.getRua()).isEqualTo(DEFAULT_RUA);
        assertThat(testLocalizacaoEmpresa.getQuarteirao()).isEqualTo(DEFAULT_QUARTEIRAO);
        assertThat(testLocalizacaoEmpresa.getNumeroPorta()).isEqualTo(DEFAULT_NUMERO_PORTA);
        assertThat(testLocalizacaoEmpresa.getCaixaPostal()).isEqualTo(DEFAULT_CAIXA_POSTAL);
    }

    @Test
    @Transactional
    public void createLocalizacaoEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localizacaoEmpresaRepository.findAll().size();

        // Create the LocalizacaoEmpresa with an existing ID
        localizacaoEmpresa.setId(1L);
        LocalizacaoEmpresaDTO localizacaoEmpresaDTO = localizacaoEmpresaMapper.toDto(localizacaoEmpresa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalizacaoEmpresaMockMvc.perform(post("/api/localizacao-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoEmpresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocalizacaoEmpresa in the database
        List<LocalizacaoEmpresa> localizacaoEmpresaList = localizacaoEmpresaRepository.findAll();
        assertThat(localizacaoEmpresaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = localizacaoEmpresaRepository.findAll().size();
        // set the field null
        localizacaoEmpresa.setBairro(null);

        // Create the LocalizacaoEmpresa, which fails.
        LocalizacaoEmpresaDTO localizacaoEmpresaDTO = localizacaoEmpresaMapper.toDto(localizacaoEmpresa);

        restLocalizacaoEmpresaMockMvc.perform(post("/api/localizacao-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoEmpresaDTO)))
            .andExpect(status().isBadRequest());

        List<LocalizacaoEmpresa> localizacaoEmpresaList = localizacaoEmpresaRepository.findAll();
        assertThat(localizacaoEmpresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuaIsRequired() throws Exception {
        int databaseSizeBeforeTest = localizacaoEmpresaRepository.findAll().size();
        // set the field null
        localizacaoEmpresa.setRua(null);

        // Create the LocalizacaoEmpresa, which fails.
        LocalizacaoEmpresaDTO localizacaoEmpresaDTO = localizacaoEmpresaMapper.toDto(localizacaoEmpresa);

        restLocalizacaoEmpresaMockMvc.perform(post("/api/localizacao-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoEmpresaDTO)))
            .andExpect(status().isBadRequest());

        List<LocalizacaoEmpresa> localizacaoEmpresaList = localizacaoEmpresaRepository.findAll();
        assertThat(localizacaoEmpresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocalizacaoEmpresas() throws Exception {
        // Initialize the database
        localizacaoEmpresaRepository.saveAndFlush(localizacaoEmpresa);

        // Get all the localizacaoEmpresaList
        restLocalizacaoEmpresaMockMvc.perform(get("/api/localizacao-empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localizacaoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA)))
            .andExpect(jsonPath("$.[*].quarteirao").value(hasItem(DEFAULT_QUARTEIRAO)))
            .andExpect(jsonPath("$.[*].numeroPorta").value(hasItem(DEFAULT_NUMERO_PORTA)))
            .andExpect(jsonPath("$.[*].caixaPostal").value(hasItem(DEFAULT_CAIXA_POSTAL)));
    }
    
    @Test
    @Transactional
    public void getLocalizacaoEmpresa() throws Exception {
        // Initialize the database
        localizacaoEmpresaRepository.saveAndFlush(localizacaoEmpresa);

        // Get the localizacaoEmpresa
        restLocalizacaoEmpresaMockMvc.perform(get("/api/localizacao-empresas/{id}", localizacaoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localizacaoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.rua").value(DEFAULT_RUA))
            .andExpect(jsonPath("$.quarteirao").value(DEFAULT_QUARTEIRAO))
            .andExpect(jsonPath("$.numeroPorta").value(DEFAULT_NUMERO_PORTA))
            .andExpect(jsonPath("$.caixaPostal").value(DEFAULT_CAIXA_POSTAL));
    }

    @Test
    @Transactional
    public void getNonExistingLocalizacaoEmpresa() throws Exception {
        // Get the localizacaoEmpresa
        restLocalizacaoEmpresaMockMvc.perform(get("/api/localizacao-empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalizacaoEmpresa() throws Exception {
        // Initialize the database
        localizacaoEmpresaRepository.saveAndFlush(localizacaoEmpresa);

        int databaseSizeBeforeUpdate = localizacaoEmpresaRepository.findAll().size();

        // Update the localizacaoEmpresa
        LocalizacaoEmpresa updatedLocalizacaoEmpresa = localizacaoEmpresaRepository.findById(localizacaoEmpresa.getId()).get();
        // Disconnect from session so that the updates on updatedLocalizacaoEmpresa are not directly saved in db
        em.detach(updatedLocalizacaoEmpresa);
        updatedLocalizacaoEmpresa
            .pais(UPDATED_PAIS)
            .provincia(UPDATED_PROVINCIA)
            .municipio(UPDATED_MUNICIPIO)
            .bairro(UPDATED_BAIRRO)
            .rua(UPDATED_RUA)
            .quarteirao(UPDATED_QUARTEIRAO)
            .numeroPorta(UPDATED_NUMERO_PORTA)
            .caixaPostal(UPDATED_CAIXA_POSTAL);
        LocalizacaoEmpresaDTO localizacaoEmpresaDTO = localizacaoEmpresaMapper.toDto(updatedLocalizacaoEmpresa);

        restLocalizacaoEmpresaMockMvc.perform(put("/api/localizacao-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoEmpresaDTO)))
            .andExpect(status().isOk());

        // Validate the LocalizacaoEmpresa in the database
        List<LocalizacaoEmpresa> localizacaoEmpresaList = localizacaoEmpresaRepository.findAll();
        assertThat(localizacaoEmpresaList).hasSize(databaseSizeBeforeUpdate);
        LocalizacaoEmpresa testLocalizacaoEmpresa = localizacaoEmpresaList.get(localizacaoEmpresaList.size() - 1);
        assertThat(testLocalizacaoEmpresa.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testLocalizacaoEmpresa.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testLocalizacaoEmpresa.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testLocalizacaoEmpresa.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testLocalizacaoEmpresa.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testLocalizacaoEmpresa.getQuarteirao()).isEqualTo(UPDATED_QUARTEIRAO);
        assertThat(testLocalizacaoEmpresa.getNumeroPorta()).isEqualTo(UPDATED_NUMERO_PORTA);
        assertThat(testLocalizacaoEmpresa.getCaixaPostal()).isEqualTo(UPDATED_CAIXA_POSTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalizacaoEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = localizacaoEmpresaRepository.findAll().size();

        // Create the LocalizacaoEmpresa
        LocalizacaoEmpresaDTO localizacaoEmpresaDTO = localizacaoEmpresaMapper.toDto(localizacaoEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalizacaoEmpresaMockMvc.perform(put("/api/localizacao-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localizacaoEmpresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LocalizacaoEmpresa in the database
        List<LocalizacaoEmpresa> localizacaoEmpresaList = localizacaoEmpresaRepository.findAll();
        assertThat(localizacaoEmpresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocalizacaoEmpresa() throws Exception {
        // Initialize the database
        localizacaoEmpresaRepository.saveAndFlush(localizacaoEmpresa);

        int databaseSizeBeforeDelete = localizacaoEmpresaRepository.findAll().size();

        // Delete the localizacaoEmpresa
        restLocalizacaoEmpresaMockMvc.perform(delete("/api/localizacao-empresas/{id}", localizacaoEmpresa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocalizacaoEmpresa> localizacaoEmpresaList = localizacaoEmpresaRepository.findAll();
        assertThat(localizacaoEmpresaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
