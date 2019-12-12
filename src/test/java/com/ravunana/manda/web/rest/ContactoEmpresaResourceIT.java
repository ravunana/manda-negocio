package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.ContactoEmpresa;
import com.ravunana.manda.domain.Empresa;
import com.ravunana.manda.repository.ContactoEmpresaRepository;
import com.ravunana.manda.service.ContactoEmpresaService;
import com.ravunana.manda.service.dto.ContactoEmpresaDTO;
import com.ravunana.manda.service.mapper.ContactoEmpresaMapper;
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
 * Integration tests for the {@link ContactoEmpresaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ContactoEmpresaResourceIT {

    private static final String DEFAULT_TIPO_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PADRAO = false;
    private static final Boolean UPDATED_PADRAO = true;

    @Autowired
    private ContactoEmpresaRepository contactoEmpresaRepository;

    @Autowired
    private ContactoEmpresaMapper contactoEmpresaMapper;

    @Autowired
    private ContactoEmpresaService contactoEmpresaService;

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

    private MockMvc restContactoEmpresaMockMvc;

    private ContactoEmpresa contactoEmpresa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactoEmpresaResource contactoEmpresaResource = new ContactoEmpresaResource(contactoEmpresaService);
        this.restContactoEmpresaMockMvc = MockMvcBuilders.standaloneSetup(contactoEmpresaResource)
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
    public static ContactoEmpresa createEntity(EntityManager em) {
        ContactoEmpresa contactoEmpresa = new ContactoEmpresa()
            .tipoContacto(DEFAULT_TIPO_CONTACTO)
            .descricao(DEFAULT_DESCRICAO)
            .contacto(DEFAULT_CONTACTO)
            .padrao(DEFAULT_PADRAO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        contactoEmpresa.setEmpresa(empresa);
        return contactoEmpresa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactoEmpresa createUpdatedEntity(EntityManager em) {
        ContactoEmpresa contactoEmpresa = new ContactoEmpresa()
            .tipoContacto(UPDATED_TIPO_CONTACTO)
            .descricao(UPDATED_DESCRICAO)
            .contacto(UPDATED_CONTACTO)
            .padrao(UPDATED_PADRAO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        contactoEmpresa.setEmpresa(empresa);
        return contactoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        contactoEmpresa = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactoEmpresa() throws Exception {
        int databaseSizeBeforeCreate = contactoEmpresaRepository.findAll().size();

        // Create the ContactoEmpresa
        ContactoEmpresaDTO contactoEmpresaDTO = contactoEmpresaMapper.toDto(contactoEmpresa);
        restContactoEmpresaMockMvc.perform(post("/api/contacto-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoEmpresaDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactoEmpresa in the database
        List<ContactoEmpresa> contactoEmpresaList = contactoEmpresaRepository.findAll();
        assertThat(contactoEmpresaList).hasSize(databaseSizeBeforeCreate + 1);
        ContactoEmpresa testContactoEmpresa = contactoEmpresaList.get(contactoEmpresaList.size() - 1);
        assertThat(testContactoEmpresa.getTipoContacto()).isEqualTo(DEFAULT_TIPO_CONTACTO);
        assertThat(testContactoEmpresa.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testContactoEmpresa.getContacto()).isEqualTo(DEFAULT_CONTACTO);
        assertThat(testContactoEmpresa.isPadrao()).isEqualTo(DEFAULT_PADRAO);
    }

    @Test
    @Transactional
    public void createContactoEmpresaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactoEmpresaRepository.findAll().size();

        // Create the ContactoEmpresa with an existing ID
        contactoEmpresa.setId(1L);
        ContactoEmpresaDTO contactoEmpresaDTO = contactoEmpresaMapper.toDto(contactoEmpresa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactoEmpresaMockMvc.perform(post("/api/contacto-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoEmpresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactoEmpresa in the database
        List<ContactoEmpresa> contactoEmpresaList = contactoEmpresaRepository.findAll();
        assertThat(contactoEmpresaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoEmpresaRepository.findAll().size();
        // set the field null
        contactoEmpresa.setTipoContacto(null);

        // Create the ContactoEmpresa, which fails.
        ContactoEmpresaDTO contactoEmpresaDTO = contactoEmpresaMapper.toDto(contactoEmpresa);

        restContactoEmpresaMockMvc.perform(post("/api/contacto-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoEmpresaDTO)))
            .andExpect(status().isBadRequest());

        List<ContactoEmpresa> contactoEmpresaList = contactoEmpresaRepository.findAll();
        assertThat(contactoEmpresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoEmpresaRepository.findAll().size();
        // set the field null
        contactoEmpresa.setContacto(null);

        // Create the ContactoEmpresa, which fails.
        ContactoEmpresaDTO contactoEmpresaDTO = contactoEmpresaMapper.toDto(contactoEmpresa);

        restContactoEmpresaMockMvc.perform(post("/api/contacto-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoEmpresaDTO)))
            .andExpect(status().isBadRequest());

        List<ContactoEmpresa> contactoEmpresaList = contactoEmpresaRepository.findAll();
        assertThat(contactoEmpresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactoEmpresas() throws Exception {
        // Initialize the database
        contactoEmpresaRepository.saveAndFlush(contactoEmpresa);

        // Get all the contactoEmpresaList
        restContactoEmpresaMockMvc.perform(get("/api/contacto-empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoContacto").value(hasItem(DEFAULT_TIPO_CONTACTO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].contacto").value(hasItem(DEFAULT_CONTACTO)))
            .andExpect(jsonPath("$.[*].padrao").value(hasItem(DEFAULT_PADRAO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getContactoEmpresa() throws Exception {
        // Initialize the database
        contactoEmpresaRepository.saveAndFlush(contactoEmpresa);

        // Get the contactoEmpresa
        restContactoEmpresaMockMvc.perform(get("/api/contacto-empresas/{id}", contactoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.tipoContacto").value(DEFAULT_TIPO_CONTACTO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.contacto").value(DEFAULT_CONTACTO))
            .andExpect(jsonPath("$.padrao").value(DEFAULT_PADRAO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContactoEmpresa() throws Exception {
        // Get the contactoEmpresa
        restContactoEmpresaMockMvc.perform(get("/api/contacto-empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactoEmpresa() throws Exception {
        // Initialize the database
        contactoEmpresaRepository.saveAndFlush(contactoEmpresa);

        int databaseSizeBeforeUpdate = contactoEmpresaRepository.findAll().size();

        // Update the contactoEmpresa
        ContactoEmpresa updatedContactoEmpresa = contactoEmpresaRepository.findById(contactoEmpresa.getId()).get();
        // Disconnect from session so that the updates on updatedContactoEmpresa are not directly saved in db
        em.detach(updatedContactoEmpresa);
        updatedContactoEmpresa
            .tipoContacto(UPDATED_TIPO_CONTACTO)
            .descricao(UPDATED_DESCRICAO)
            .contacto(UPDATED_CONTACTO)
            .padrao(UPDATED_PADRAO);
        ContactoEmpresaDTO contactoEmpresaDTO = contactoEmpresaMapper.toDto(updatedContactoEmpresa);

        restContactoEmpresaMockMvc.perform(put("/api/contacto-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoEmpresaDTO)))
            .andExpect(status().isOk());

        // Validate the ContactoEmpresa in the database
        List<ContactoEmpresa> contactoEmpresaList = contactoEmpresaRepository.findAll();
        assertThat(contactoEmpresaList).hasSize(databaseSizeBeforeUpdate);
        ContactoEmpresa testContactoEmpresa = contactoEmpresaList.get(contactoEmpresaList.size() - 1);
        assertThat(testContactoEmpresa.getTipoContacto()).isEqualTo(UPDATED_TIPO_CONTACTO);
        assertThat(testContactoEmpresa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testContactoEmpresa.getContacto()).isEqualTo(UPDATED_CONTACTO);
        assertThat(testContactoEmpresa.isPadrao()).isEqualTo(UPDATED_PADRAO);
    }

    @Test
    @Transactional
    public void updateNonExistingContactoEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = contactoEmpresaRepository.findAll().size();

        // Create the ContactoEmpresa
        ContactoEmpresaDTO contactoEmpresaDTO = contactoEmpresaMapper.toDto(contactoEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoEmpresaMockMvc.perform(put("/api/contacto-empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoEmpresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactoEmpresa in the database
        List<ContactoEmpresa> contactoEmpresaList = contactoEmpresaRepository.findAll();
        assertThat(contactoEmpresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactoEmpresa() throws Exception {
        // Initialize the database
        contactoEmpresaRepository.saveAndFlush(contactoEmpresa);

        int databaseSizeBeforeDelete = contactoEmpresaRepository.findAll().size();

        // Delete the contactoEmpresa
        restContactoEmpresaMockMvc.perform(delete("/api/contacto-empresas/{id}", contactoEmpresa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactoEmpresa> contactoEmpresaList = contactoEmpresaRepository.findAll();
        assertThat(contactoEmpresaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
