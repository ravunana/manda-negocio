package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.ContactoPessoa;
import com.ravunana.manda.domain.Pessoa;
import com.ravunana.manda.repository.ContactoPessoaRepository;
import com.ravunana.manda.service.ContactoPessoaService;
import com.ravunana.manda.service.dto.ContactoPessoaDTO;
import com.ravunana.manda.service.mapper.ContactoPessoaMapper;
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
 * Integration tests for the {@link ContactoPessoaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ContactoPessoaResourceIT {

    private static final String DEFAULT_TIPO_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO = "BBBBBBBBBB";

    @Autowired
    private ContactoPessoaRepository contactoPessoaRepository;

    @Autowired
    private ContactoPessoaMapper contactoPessoaMapper;

    @Autowired
    private ContactoPessoaService contactoPessoaService;

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

    private MockMvc restContactoPessoaMockMvc;

    private ContactoPessoa contactoPessoa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactoPessoaResource contactoPessoaResource = new ContactoPessoaResource(contactoPessoaService);
        this.restContactoPessoaMockMvc = MockMvcBuilders.standaloneSetup(contactoPessoaResource)
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
    public static ContactoPessoa createEntity(EntityManager em) {
        ContactoPessoa contactoPessoa = new ContactoPessoa()
            .tipoContacto(DEFAULT_TIPO_CONTACTO)
            .descricao(DEFAULT_DESCRICAO)
            .contacto(DEFAULT_CONTACTO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        contactoPessoa.setPessoa(pessoa);
        return contactoPessoa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactoPessoa createUpdatedEntity(EntityManager em) {
        ContactoPessoa contactoPessoa = new ContactoPessoa()
            .tipoContacto(UPDATED_TIPO_CONTACTO)
            .descricao(UPDATED_DESCRICAO)
            .contacto(UPDATED_CONTACTO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        contactoPessoa.setPessoa(pessoa);
        return contactoPessoa;
    }

    @BeforeEach
    public void initTest() {
        contactoPessoa = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactoPessoa() throws Exception {
        int databaseSizeBeforeCreate = contactoPessoaRepository.findAll().size();

        // Create the ContactoPessoa
        ContactoPessoaDTO contactoPessoaDTO = contactoPessoaMapper.toDto(contactoPessoa);
        restContactoPessoaMockMvc.perform(post("/api/contacto-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoPessoaDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactoPessoa in the database
        List<ContactoPessoa> contactoPessoaList = contactoPessoaRepository.findAll();
        assertThat(contactoPessoaList).hasSize(databaseSizeBeforeCreate + 1);
        ContactoPessoa testContactoPessoa = contactoPessoaList.get(contactoPessoaList.size() - 1);
        assertThat(testContactoPessoa.getTipoContacto()).isEqualTo(DEFAULT_TIPO_CONTACTO);
        assertThat(testContactoPessoa.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testContactoPessoa.getContacto()).isEqualTo(DEFAULT_CONTACTO);
    }

    @Test
    @Transactional
    public void createContactoPessoaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactoPessoaRepository.findAll().size();

        // Create the ContactoPessoa with an existing ID
        contactoPessoa.setId(1L);
        ContactoPessoaDTO contactoPessoaDTO = contactoPessoaMapper.toDto(contactoPessoa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactoPessoaMockMvc.perform(post("/api/contacto-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoPessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactoPessoa in the database
        List<ContactoPessoa> contactoPessoaList = contactoPessoaRepository.findAll();
        assertThat(contactoPessoaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoPessoaRepository.findAll().size();
        // set the field null
        contactoPessoa.setTipoContacto(null);

        // Create the ContactoPessoa, which fails.
        ContactoPessoaDTO contactoPessoaDTO = contactoPessoaMapper.toDto(contactoPessoa);

        restContactoPessoaMockMvc.perform(post("/api/contacto-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoPessoaDTO)))
            .andExpect(status().isBadRequest());

        List<ContactoPessoa> contactoPessoaList = contactoPessoaRepository.findAll();
        assertThat(contactoPessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoPessoaRepository.findAll().size();
        // set the field null
        contactoPessoa.setContacto(null);

        // Create the ContactoPessoa, which fails.
        ContactoPessoaDTO contactoPessoaDTO = contactoPessoaMapper.toDto(contactoPessoa);

        restContactoPessoaMockMvc.perform(post("/api/contacto-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoPessoaDTO)))
            .andExpect(status().isBadRequest());

        List<ContactoPessoa> contactoPessoaList = contactoPessoaRepository.findAll();
        assertThat(contactoPessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactoPessoas() throws Exception {
        // Initialize the database
        contactoPessoaRepository.saveAndFlush(contactoPessoa);

        // Get all the contactoPessoaList
        restContactoPessoaMockMvc.perform(get("/api/contacto-pessoas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactoPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoContacto").value(hasItem(DEFAULT_TIPO_CONTACTO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].contacto").value(hasItem(DEFAULT_CONTACTO)));
    }
    
    @Test
    @Transactional
    public void getContactoPessoa() throws Exception {
        // Initialize the database
        contactoPessoaRepository.saveAndFlush(contactoPessoa);

        // Get the contactoPessoa
        restContactoPessoaMockMvc.perform(get("/api/contacto-pessoas/{id}", contactoPessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactoPessoa.getId().intValue()))
            .andExpect(jsonPath("$.tipoContacto").value(DEFAULT_TIPO_CONTACTO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.contacto").value(DEFAULT_CONTACTO));
    }

    @Test
    @Transactional
    public void getNonExistingContactoPessoa() throws Exception {
        // Get the contactoPessoa
        restContactoPessoaMockMvc.perform(get("/api/contacto-pessoas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactoPessoa() throws Exception {
        // Initialize the database
        contactoPessoaRepository.saveAndFlush(contactoPessoa);

        int databaseSizeBeforeUpdate = contactoPessoaRepository.findAll().size();

        // Update the contactoPessoa
        ContactoPessoa updatedContactoPessoa = contactoPessoaRepository.findById(contactoPessoa.getId()).get();
        // Disconnect from session so that the updates on updatedContactoPessoa are not directly saved in db
        em.detach(updatedContactoPessoa);
        updatedContactoPessoa
            .tipoContacto(UPDATED_TIPO_CONTACTO)
            .descricao(UPDATED_DESCRICAO)
            .contacto(UPDATED_CONTACTO);
        ContactoPessoaDTO contactoPessoaDTO = contactoPessoaMapper.toDto(updatedContactoPessoa);

        restContactoPessoaMockMvc.perform(put("/api/contacto-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoPessoaDTO)))
            .andExpect(status().isOk());

        // Validate the ContactoPessoa in the database
        List<ContactoPessoa> contactoPessoaList = contactoPessoaRepository.findAll();
        assertThat(contactoPessoaList).hasSize(databaseSizeBeforeUpdate);
        ContactoPessoa testContactoPessoa = contactoPessoaList.get(contactoPessoaList.size() - 1);
        assertThat(testContactoPessoa.getTipoContacto()).isEqualTo(UPDATED_TIPO_CONTACTO);
        assertThat(testContactoPessoa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testContactoPessoa.getContacto()).isEqualTo(UPDATED_CONTACTO);
    }

    @Test
    @Transactional
    public void updateNonExistingContactoPessoa() throws Exception {
        int databaseSizeBeforeUpdate = contactoPessoaRepository.findAll().size();

        // Create the ContactoPessoa
        ContactoPessoaDTO contactoPessoaDTO = contactoPessoaMapper.toDto(contactoPessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoPessoaMockMvc.perform(put("/api/contacto-pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoPessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactoPessoa in the database
        List<ContactoPessoa> contactoPessoaList = contactoPessoaRepository.findAll();
        assertThat(contactoPessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactoPessoa() throws Exception {
        // Initialize the database
        contactoPessoaRepository.saveAndFlush(contactoPessoa);

        int databaseSizeBeforeDelete = contactoPessoaRepository.findAll().size();

        // Delete the contactoPessoa
        restContactoPessoaMockMvc.perform(delete("/api/contacto-pessoas/{id}", contactoPessoa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactoPessoa> contactoPessoaList = contactoPessoaRepository.findAll();
        assertThat(contactoPessoaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
