package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.AuditoriaVenda;
import com.ravunana.manda.domain.Venda;
import com.ravunana.manda.repository.AuditoriaVendaRepository;
import com.ravunana.manda.service.AuditoriaVendaService;
import com.ravunana.manda.service.dto.AuditoriaVendaDTO;
import com.ravunana.manda.service.mapper.AuditoriaVendaMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.sameInstant;
import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AuditoriaVendaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class AuditoriaVendaResourceIT {

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MOTIVO_ALTERACAO_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO_ALTERACAO_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGEM_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_ORIGEM_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_CONTROL = "AAAAAAAAAA";
    private static final String UPDATED_HASH_CONTROL = "BBBBBBBBBB";

    @Autowired
    private AuditoriaVendaRepository auditoriaVendaRepository;

    @Autowired
    private AuditoriaVendaMapper auditoriaVendaMapper;

    @Autowired
    private AuditoriaVendaService auditoriaVendaService;

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

    private MockMvc restAuditoriaVendaMockMvc;

    private AuditoriaVenda auditoriaVenda;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuditoriaVendaResource auditoriaVendaResource = new AuditoriaVendaResource(auditoriaVendaService);
        this.restAuditoriaVendaMockMvc = MockMvcBuilders.standaloneSetup(auditoriaVendaResource)
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
    public static AuditoriaVenda createEntity(EntityManager em) {
        AuditoriaVenda auditoriaVenda = new AuditoriaVenda()
            .estado(DEFAULT_ESTADO)
            .data(DEFAULT_DATA)
            .motivoAlteracaoDocumento(DEFAULT_MOTIVO_ALTERACAO_DOCUMENTO)
            .origemDocumento(DEFAULT_ORIGEM_DOCUMENTO)
            .hash(DEFAULT_HASH)
            .hashControl(DEFAULT_HASH_CONTROL);
        // Add required entity
        Venda venda;
        if (TestUtil.findAll(em, Venda.class).isEmpty()) {
            venda = VendaResourceIT.createEntity(em);
            em.persist(venda);
            em.flush();
        } else {
            venda = TestUtil.findAll(em, Venda.class).get(0);
        }
        auditoriaVenda.setVenda(venda);
        return auditoriaVenda;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditoriaVenda createUpdatedEntity(EntityManager em) {
        AuditoriaVenda auditoriaVenda = new AuditoriaVenda()
            .estado(UPDATED_ESTADO)
            .data(UPDATED_DATA)
            .motivoAlteracaoDocumento(UPDATED_MOTIVO_ALTERACAO_DOCUMENTO)
            .origemDocumento(UPDATED_ORIGEM_DOCUMENTO)
            .hash(UPDATED_HASH)
            .hashControl(UPDATED_HASH_CONTROL);
        // Add required entity
        Venda venda;
        if (TestUtil.findAll(em, Venda.class).isEmpty()) {
            venda = VendaResourceIT.createUpdatedEntity(em);
            em.persist(venda);
            em.flush();
        } else {
            venda = TestUtil.findAll(em, Venda.class).get(0);
        }
        auditoriaVenda.setVenda(venda);
        return auditoriaVenda;
    }

    @BeforeEach
    public void initTest() {
        auditoriaVenda = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuditoriaVenda() throws Exception {
        int databaseSizeBeforeCreate = auditoriaVendaRepository.findAll().size();

        // Create the AuditoriaVenda
        AuditoriaVendaDTO auditoriaVendaDTO = auditoriaVendaMapper.toDto(auditoriaVenda);
        restAuditoriaVendaMockMvc.perform(post("/api/auditoria-vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoriaVendaDTO)))
            .andExpect(status().isCreated());

        // Validate the AuditoriaVenda in the database
        List<AuditoriaVenda> auditoriaVendaList = auditoriaVendaRepository.findAll();
        assertThat(auditoriaVendaList).hasSize(databaseSizeBeforeCreate + 1);
        AuditoriaVenda testAuditoriaVenda = auditoriaVendaList.get(auditoriaVendaList.size() - 1);
        assertThat(testAuditoriaVenda.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAuditoriaVenda.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testAuditoriaVenda.getMotivoAlteracaoDocumento()).isEqualTo(DEFAULT_MOTIVO_ALTERACAO_DOCUMENTO);
        assertThat(testAuditoriaVenda.getOrigemDocumento()).isEqualTo(DEFAULT_ORIGEM_DOCUMENTO);
        assertThat(testAuditoriaVenda.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testAuditoriaVenda.getHashControl()).isEqualTo(DEFAULT_HASH_CONTROL);
    }

    @Test
    @Transactional
    public void createAuditoriaVendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auditoriaVendaRepository.findAll().size();

        // Create the AuditoriaVenda with an existing ID
        auditoriaVenda.setId(1L);
        AuditoriaVendaDTO auditoriaVendaDTO = auditoriaVendaMapper.toDto(auditoriaVenda);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditoriaVendaMockMvc.perform(post("/api/auditoria-vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoriaVendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditoriaVenda in the database
        List<AuditoriaVenda> auditoriaVendaList = auditoriaVendaRepository.findAll();
        assertThat(auditoriaVendaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAuditoriaVendas() throws Exception {
        // Initialize the database
        auditoriaVendaRepository.saveAndFlush(auditoriaVenda);

        // Get all the auditoriaVendaList
        restAuditoriaVendaMockMvc.perform(get("/api/auditoria-vendas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditoriaVenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].motivoAlteracaoDocumento").value(hasItem(DEFAULT_MOTIVO_ALTERACAO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].origemDocumento").value(hasItem(DEFAULT_ORIGEM_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].hashControl").value(hasItem(DEFAULT_HASH_CONTROL)));
    }
    
    @Test
    @Transactional
    public void getAuditoriaVenda() throws Exception {
        // Initialize the database
        auditoriaVendaRepository.saveAndFlush(auditoriaVenda);

        // Get the auditoriaVenda
        restAuditoriaVendaMockMvc.perform(get("/api/auditoria-vendas/{id}", auditoriaVenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auditoriaVenda.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.motivoAlteracaoDocumento").value(DEFAULT_MOTIVO_ALTERACAO_DOCUMENTO))
            .andExpect(jsonPath("$.origemDocumento").value(DEFAULT_ORIGEM_DOCUMENTO))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.hashControl").value(DEFAULT_HASH_CONTROL));
    }

    @Test
    @Transactional
    public void getNonExistingAuditoriaVenda() throws Exception {
        // Get the auditoriaVenda
        restAuditoriaVendaMockMvc.perform(get("/api/auditoria-vendas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditoriaVenda() throws Exception {
        // Initialize the database
        auditoriaVendaRepository.saveAndFlush(auditoriaVenda);

        int databaseSizeBeforeUpdate = auditoriaVendaRepository.findAll().size();

        // Update the auditoriaVenda
        AuditoriaVenda updatedAuditoriaVenda = auditoriaVendaRepository.findById(auditoriaVenda.getId()).get();
        // Disconnect from session so that the updates on updatedAuditoriaVenda are not directly saved in db
        em.detach(updatedAuditoriaVenda);
        updatedAuditoriaVenda
            .estado(UPDATED_ESTADO)
            .data(UPDATED_DATA)
            .motivoAlteracaoDocumento(UPDATED_MOTIVO_ALTERACAO_DOCUMENTO)
            .origemDocumento(UPDATED_ORIGEM_DOCUMENTO)
            .hash(UPDATED_HASH)
            .hashControl(UPDATED_HASH_CONTROL);
        AuditoriaVendaDTO auditoriaVendaDTO = auditoriaVendaMapper.toDto(updatedAuditoriaVenda);

        restAuditoriaVendaMockMvc.perform(put("/api/auditoria-vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoriaVendaDTO)))
            .andExpect(status().isOk());

        // Validate the AuditoriaVenda in the database
        List<AuditoriaVenda> auditoriaVendaList = auditoriaVendaRepository.findAll();
        assertThat(auditoriaVendaList).hasSize(databaseSizeBeforeUpdate);
        AuditoriaVenda testAuditoriaVenda = auditoriaVendaList.get(auditoriaVendaList.size() - 1);
        assertThat(testAuditoriaVenda.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAuditoriaVenda.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testAuditoriaVenda.getMotivoAlteracaoDocumento()).isEqualTo(UPDATED_MOTIVO_ALTERACAO_DOCUMENTO);
        assertThat(testAuditoriaVenda.getOrigemDocumento()).isEqualTo(UPDATED_ORIGEM_DOCUMENTO);
        assertThat(testAuditoriaVenda.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testAuditoriaVenda.getHashControl()).isEqualTo(UPDATED_HASH_CONTROL);
    }

    @Test
    @Transactional
    public void updateNonExistingAuditoriaVenda() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaVendaRepository.findAll().size();

        // Create the AuditoriaVenda
        AuditoriaVendaDTO auditoriaVendaDTO = auditoriaVendaMapper.toDto(auditoriaVenda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditoriaVendaMockMvc.perform(put("/api/auditoria-vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoriaVendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditoriaVenda in the database
        List<AuditoriaVenda> auditoriaVendaList = auditoriaVendaRepository.findAll();
        assertThat(auditoriaVendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAuditoriaVenda() throws Exception {
        // Initialize the database
        auditoriaVendaRepository.saveAndFlush(auditoriaVenda);

        int databaseSizeBeforeDelete = auditoriaVendaRepository.findAll().size();

        // Delete the auditoriaVenda
        restAuditoriaVendaMockMvc.perform(delete("/api/auditoria-vendas/{id}", auditoriaVenda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuditoriaVenda> auditoriaVendaList = auditoriaVendaRepository.findAll();
        assertThat(auditoriaVendaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
