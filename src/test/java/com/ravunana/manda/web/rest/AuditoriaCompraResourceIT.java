package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.AuditoriaCompra;
import com.ravunana.manda.repository.AuditoriaCompraRepository;
import com.ravunana.manda.service.AuditoriaCompraService;
import com.ravunana.manda.service.dto.AuditoriaCompraDTO;
import com.ravunana.manda.service.mapper.AuditoriaCompraMapper;
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
 * Integration tests for the {@link AuditoriaCompraResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class AuditoriaCompraResourceIT {

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
    private AuditoriaCompraRepository auditoriaCompraRepository;

    @Autowired
    private AuditoriaCompraMapper auditoriaCompraMapper;

    @Autowired
    private AuditoriaCompraService auditoriaCompraService;

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

    private MockMvc restAuditoriaCompraMockMvc;

    private AuditoriaCompra auditoriaCompra;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuditoriaCompraResource auditoriaCompraResource = new AuditoriaCompraResource(auditoriaCompraService);
        this.restAuditoriaCompraMockMvc = MockMvcBuilders.standaloneSetup(auditoriaCompraResource)
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
    public static AuditoriaCompra createEntity(EntityManager em) {
        AuditoriaCompra auditoriaCompra = new AuditoriaCompra()
            .estado(DEFAULT_ESTADO)
            .data(DEFAULT_DATA)
            .motivoAlteracaoDocumento(DEFAULT_MOTIVO_ALTERACAO_DOCUMENTO)
            .origemDocumento(DEFAULT_ORIGEM_DOCUMENTO)
            .hash(DEFAULT_HASH)
            .hashControl(DEFAULT_HASH_CONTROL);
        return auditoriaCompra;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditoriaCompra createUpdatedEntity(EntityManager em) {
        AuditoriaCompra auditoriaCompra = new AuditoriaCompra()
            .estado(UPDATED_ESTADO)
            .data(UPDATED_DATA)
            .motivoAlteracaoDocumento(UPDATED_MOTIVO_ALTERACAO_DOCUMENTO)
            .origemDocumento(UPDATED_ORIGEM_DOCUMENTO)
            .hash(UPDATED_HASH)
            .hashControl(UPDATED_HASH_CONTROL);
        return auditoriaCompra;
    }

    @BeforeEach
    public void initTest() {
        auditoriaCompra = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuditoriaCompra() throws Exception {
        int databaseSizeBeforeCreate = auditoriaCompraRepository.findAll().size();

        // Create the AuditoriaCompra
        AuditoriaCompraDTO auditoriaCompraDTO = auditoriaCompraMapper.toDto(auditoriaCompra);
        restAuditoriaCompraMockMvc.perform(post("/api/auditoria-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoriaCompraDTO)))
            .andExpect(status().isCreated());

        // Validate the AuditoriaCompra in the database
        List<AuditoriaCompra> auditoriaCompraList = auditoriaCompraRepository.findAll();
        assertThat(auditoriaCompraList).hasSize(databaseSizeBeforeCreate + 1);
        AuditoriaCompra testAuditoriaCompra = auditoriaCompraList.get(auditoriaCompraList.size() - 1);
        assertThat(testAuditoriaCompra.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAuditoriaCompra.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testAuditoriaCompra.getMotivoAlteracaoDocumento()).isEqualTo(DEFAULT_MOTIVO_ALTERACAO_DOCUMENTO);
        assertThat(testAuditoriaCompra.getOrigemDocumento()).isEqualTo(DEFAULT_ORIGEM_DOCUMENTO);
        assertThat(testAuditoriaCompra.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testAuditoriaCompra.getHashControl()).isEqualTo(DEFAULT_HASH_CONTROL);
    }

    @Test
    @Transactional
    public void createAuditoriaCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auditoriaCompraRepository.findAll().size();

        // Create the AuditoriaCompra with an existing ID
        auditoriaCompra.setId(1L);
        AuditoriaCompraDTO auditoriaCompraDTO = auditoriaCompraMapper.toDto(auditoriaCompra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditoriaCompraMockMvc.perform(post("/api/auditoria-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoriaCompraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditoriaCompra in the database
        List<AuditoriaCompra> auditoriaCompraList = auditoriaCompraRepository.findAll();
        assertThat(auditoriaCompraList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAuditoriaCompras() throws Exception {
        // Initialize the database
        auditoriaCompraRepository.saveAndFlush(auditoriaCompra);

        // Get all the auditoriaCompraList
        restAuditoriaCompraMockMvc.perform(get("/api/auditoria-compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditoriaCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].motivoAlteracaoDocumento").value(hasItem(DEFAULT_MOTIVO_ALTERACAO_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].origemDocumento").value(hasItem(DEFAULT_ORIGEM_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].hashControl").value(hasItem(DEFAULT_HASH_CONTROL)));
    }
    
    @Test
    @Transactional
    public void getAuditoriaCompra() throws Exception {
        // Initialize the database
        auditoriaCompraRepository.saveAndFlush(auditoriaCompra);

        // Get the auditoriaCompra
        restAuditoriaCompraMockMvc.perform(get("/api/auditoria-compras/{id}", auditoriaCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auditoriaCompra.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.motivoAlteracaoDocumento").value(DEFAULT_MOTIVO_ALTERACAO_DOCUMENTO))
            .andExpect(jsonPath("$.origemDocumento").value(DEFAULT_ORIGEM_DOCUMENTO))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.hashControl").value(DEFAULT_HASH_CONTROL));
    }

    @Test
    @Transactional
    public void getNonExistingAuditoriaCompra() throws Exception {
        // Get the auditoriaCompra
        restAuditoriaCompraMockMvc.perform(get("/api/auditoria-compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditoriaCompra() throws Exception {
        // Initialize the database
        auditoriaCompraRepository.saveAndFlush(auditoriaCompra);

        int databaseSizeBeforeUpdate = auditoriaCompraRepository.findAll().size();

        // Update the auditoriaCompra
        AuditoriaCompra updatedAuditoriaCompra = auditoriaCompraRepository.findById(auditoriaCompra.getId()).get();
        // Disconnect from session so that the updates on updatedAuditoriaCompra are not directly saved in db
        em.detach(updatedAuditoriaCompra);
        updatedAuditoriaCompra
            .estado(UPDATED_ESTADO)
            .data(UPDATED_DATA)
            .motivoAlteracaoDocumento(UPDATED_MOTIVO_ALTERACAO_DOCUMENTO)
            .origemDocumento(UPDATED_ORIGEM_DOCUMENTO)
            .hash(UPDATED_HASH)
            .hashControl(UPDATED_HASH_CONTROL);
        AuditoriaCompraDTO auditoriaCompraDTO = auditoriaCompraMapper.toDto(updatedAuditoriaCompra);

        restAuditoriaCompraMockMvc.perform(put("/api/auditoria-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoriaCompraDTO)))
            .andExpect(status().isOk());

        // Validate the AuditoriaCompra in the database
        List<AuditoriaCompra> auditoriaCompraList = auditoriaCompraRepository.findAll();
        assertThat(auditoriaCompraList).hasSize(databaseSizeBeforeUpdate);
        AuditoriaCompra testAuditoriaCompra = auditoriaCompraList.get(auditoriaCompraList.size() - 1);
        assertThat(testAuditoriaCompra.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAuditoriaCompra.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testAuditoriaCompra.getMotivoAlteracaoDocumento()).isEqualTo(UPDATED_MOTIVO_ALTERACAO_DOCUMENTO);
        assertThat(testAuditoriaCompra.getOrigemDocumento()).isEqualTo(UPDATED_ORIGEM_DOCUMENTO);
        assertThat(testAuditoriaCompra.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testAuditoriaCompra.getHashControl()).isEqualTo(UPDATED_HASH_CONTROL);
    }

    @Test
    @Transactional
    public void updateNonExistingAuditoriaCompra() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaCompraRepository.findAll().size();

        // Create the AuditoriaCompra
        AuditoriaCompraDTO auditoriaCompraDTO = auditoriaCompraMapper.toDto(auditoriaCompra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditoriaCompraMockMvc.perform(put("/api/auditoria-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditoriaCompraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditoriaCompra in the database
        List<AuditoriaCompra> auditoriaCompraList = auditoriaCompraRepository.findAll();
        assertThat(auditoriaCompraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAuditoriaCompra() throws Exception {
        // Initialize the database
        auditoriaCompraRepository.saveAndFlush(auditoriaCompra);

        int databaseSizeBeforeDelete = auditoriaCompraRepository.findAll().size();

        // Delete the auditoriaCompra
        restAuditoriaCompraMockMvc.perform(delete("/api/auditoria-compras/{id}", auditoriaCompra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuditoriaCompra> auditoriaCompraList = auditoriaCompraRepository.findAll();
        assertThat(auditoriaCompraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
