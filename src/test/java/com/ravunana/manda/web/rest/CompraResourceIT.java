package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.Compra;
import com.ravunana.manda.domain.ItemCompra;
import com.ravunana.manda.domain.User;
import com.ravunana.manda.domain.DocumentoComercial;
import com.ravunana.manda.domain.Empresa;
import com.ravunana.manda.repository.CompraRepository;
import com.ravunana.manda.service.CompraService;
import com.ravunana.manda.service.dto.CompraDTO;
import com.ravunana.manda.service.mapper.CompraMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.CompraCriteria;
import com.ravunana.manda.service.CompraQueryService;

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
 * Integration tests for the {@link CompraResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class CompraResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_OBSERVACAO_GERAL = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO_GERAL = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO_INTERNA = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO_INTERNA = "BBBBBBBBBB";

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private CompraMapper compraMapper;

    @Autowired
    private CompraService compraService;

    @Autowired
    private CompraQueryService compraQueryService;

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

    private MockMvc restCompraMockMvc;

    private Compra compra;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompraResource compraResource = new CompraResource(compraService, compraQueryService);
        this.restCompraMockMvc = MockMvcBuilders.standaloneSetup(compraResource)
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
    public static Compra createEntity(EntityManager em) {
        Compra compra = new Compra()
            .numero(DEFAULT_NUMERO)
            .data(DEFAULT_DATA)
            .observacaoGeral(DEFAULT_OBSERVACAO_GERAL)
            .observacaoInterna(DEFAULT_OBSERVACAO_INTERNA);
        // Add required entity
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercial = DocumentoComercialResourceIT.createEntity(em);
            em.persist(documentoComercial);
            em.flush();
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        compra.setTipoDocumento(documentoComercial);
        return compra;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compra createUpdatedEntity(EntityManager em) {
        Compra compra = new Compra()
            .numero(UPDATED_NUMERO)
            .data(UPDATED_DATA)
            .observacaoGeral(UPDATED_OBSERVACAO_GERAL)
            .observacaoInterna(UPDATED_OBSERVACAO_INTERNA);
        // Add required entity
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercial = DocumentoComercialResourceIT.createUpdatedEntity(em);
            em.persist(documentoComercial);
            em.flush();
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        compra.setTipoDocumento(documentoComercial);
        return compra;
    }

    @BeforeEach
    public void initTest() {
        compra = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompra() throws Exception {
        int databaseSizeBeforeCreate = compraRepository.findAll().size();

        // Create the Compra
        CompraDTO compraDTO = compraMapper.toDto(compra);
        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isCreated());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeCreate + 1);
        Compra testCompra = compraList.get(compraList.size() - 1);
        assertThat(testCompra.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCompra.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testCompra.getObservacaoGeral()).isEqualTo(DEFAULT_OBSERVACAO_GERAL);
        assertThat(testCompra.getObservacaoInterna()).isEqualTo(DEFAULT_OBSERVACAO_INTERNA);
    }

    @Test
    @Transactional
    public void createCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compraRepository.findAll().size();

        // Create the Compra with an existing ID
        compra.setId(1L);
        CompraDTO compraDTO = compraMapper.toDto(compra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = compraRepository.findAll().size();
        // set the field null
        compra.setNumero(null);

        // Create the Compra, which fails.
        CompraDTO compraDTO = compraMapper.toDto(compra);

        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompras() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList
        restCompraMockMvc.perform(get("/api/compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compra.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].observacaoGeral").value(hasItem(DEFAULT_OBSERVACAO_GERAL.toString())))
            .andExpect(jsonPath("$.[*].observacaoInterna").value(hasItem(DEFAULT_OBSERVACAO_INTERNA.toString())));
    }
    
    @Test
    @Transactional
    public void getCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get the compra
        restCompraMockMvc.perform(get("/api/compras/{id}", compra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compra.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.observacaoGeral").value(DEFAULT_OBSERVACAO_GERAL.toString()))
            .andExpect(jsonPath("$.observacaoInterna").value(DEFAULT_OBSERVACAO_INTERNA.toString()));
    }


    @Test
    @Transactional
    public void getComprasByIdFiltering() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        Long id = compra.getId();

        defaultCompraShouldBeFound("id.equals=" + id);
        defaultCompraShouldNotBeFound("id.notEquals=" + id);

        defaultCompraShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompraShouldNotBeFound("id.greaterThan=" + id);

        defaultCompraShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompraShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllComprasByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where numero equals to DEFAULT_NUMERO
        defaultCompraShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the compraList where numero equals to UPDATED_NUMERO
        defaultCompraShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllComprasByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where numero not equals to DEFAULT_NUMERO
        defaultCompraShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the compraList where numero not equals to UPDATED_NUMERO
        defaultCompraShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllComprasByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultCompraShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the compraList where numero equals to UPDATED_NUMERO
        defaultCompraShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllComprasByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where numero is not null
        defaultCompraShouldBeFound("numero.specified=true");

        // Get all the compraList where numero is null
        defaultCompraShouldNotBeFound("numero.specified=false");
    }
                @Test
    @Transactional
    public void getAllComprasByNumeroContainsSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where numero contains DEFAULT_NUMERO
        defaultCompraShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the compraList where numero contains UPDATED_NUMERO
        defaultCompraShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllComprasByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where numero does not contain DEFAULT_NUMERO
        defaultCompraShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the compraList where numero does not contain UPDATED_NUMERO
        defaultCompraShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }


    @Test
    @Transactional
    public void getAllComprasByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where data equals to DEFAULT_DATA
        defaultCompraShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the compraList where data equals to UPDATED_DATA
        defaultCompraShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllComprasByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where data not equals to DEFAULT_DATA
        defaultCompraShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the compraList where data not equals to UPDATED_DATA
        defaultCompraShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllComprasByDataIsInShouldWork() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where data in DEFAULT_DATA or UPDATED_DATA
        defaultCompraShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the compraList where data equals to UPDATED_DATA
        defaultCompraShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllComprasByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where data is not null
        defaultCompraShouldBeFound("data.specified=true");

        // Get all the compraList where data is null
        defaultCompraShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllComprasByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where data is greater than or equal to DEFAULT_DATA
        defaultCompraShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the compraList where data is greater than or equal to UPDATED_DATA
        defaultCompraShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllComprasByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where data is less than or equal to DEFAULT_DATA
        defaultCompraShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the compraList where data is less than or equal to SMALLER_DATA
        defaultCompraShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllComprasByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where data is less than DEFAULT_DATA
        defaultCompraShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the compraList where data is less than UPDATED_DATA
        defaultCompraShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllComprasByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList where data is greater than DEFAULT_DATA
        defaultCompraShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the compraList where data is greater than SMALLER_DATA
        defaultCompraShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllComprasByItemCompraIsEqualToSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);
        ItemCompra itemCompra = ItemCompraResourceIT.createEntity(em);
        em.persist(itemCompra);
        em.flush();
        compra.addItemCompra(itemCompra);
        compraRepository.saveAndFlush(compra);
        Long itemCompraId = itemCompra.getId();

        // Get all the compraList where itemCompra equals to itemCompraId
        defaultCompraShouldBeFound("itemCompraId.equals=" + itemCompraId);

        // Get all the compraList where itemCompra equals to itemCompraId + 1
        defaultCompraShouldNotBeFound("itemCompraId.equals=" + (itemCompraId + 1));
    }


    @Test
    @Transactional
    public void getAllComprasByUtilizadorIsEqualToSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);
        User utilizador = UserResourceIT.createEntity(em);
        em.persist(utilizador);
        em.flush();
        compra.setUtilizador(utilizador);
        compraRepository.saveAndFlush(compra);
        Long utilizadorId = utilizador.getId();

        // Get all the compraList where utilizador equals to utilizadorId
        defaultCompraShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the compraList where utilizador equals to utilizadorId + 1
        defaultCompraShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }


    @Test
    @Transactional
    public void getAllComprasByTipoDocumentoIsEqualToSomething() throws Exception {
        // Get already existing entity
        DocumentoComercial tipoDocumento = compra.getTipoDocumento();
        compraRepository.saveAndFlush(compra);
        Long tipoDocumentoId = tipoDocumento.getId();

        // Get all the compraList where tipoDocumento equals to tipoDocumentoId
        defaultCompraShouldBeFound("tipoDocumentoId.equals=" + tipoDocumentoId);

        // Get all the compraList where tipoDocumento equals to tipoDocumentoId + 1
        defaultCompraShouldNotBeFound("tipoDocumentoId.equals=" + (tipoDocumentoId + 1));
    }


    @Test
    @Transactional
    public void getAllComprasByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        compra.setEmpresa(empresa);
        compraRepository.saveAndFlush(compra);
        Long empresaId = empresa.getId();

        // Get all the compraList where empresa equals to empresaId
        defaultCompraShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the compraList where empresa equals to empresaId + 1
        defaultCompraShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompraShouldBeFound(String filter) throws Exception {
        restCompraMockMvc.perform(get("/api/compras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compra.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].observacaoGeral").value(hasItem(DEFAULT_OBSERVACAO_GERAL.toString())))
            .andExpect(jsonPath("$.[*].observacaoInterna").value(hasItem(DEFAULT_OBSERVACAO_INTERNA.toString())));

        // Check, that the count call also returns 1
        restCompraMockMvc.perform(get("/api/compras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompraShouldNotBeFound(String filter) throws Exception {
        restCompraMockMvc.perform(get("/api/compras?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompraMockMvc.perform(get("/api/compras/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCompra() throws Exception {
        // Get the compra
        restCompraMockMvc.perform(get("/api/compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        int databaseSizeBeforeUpdate = compraRepository.findAll().size();

        // Update the compra
        Compra updatedCompra = compraRepository.findById(compra.getId()).get();
        // Disconnect from session so that the updates on updatedCompra are not directly saved in db
        em.detach(updatedCompra);
        updatedCompra
            .numero(UPDATED_NUMERO)
            .data(UPDATED_DATA)
            .observacaoGeral(UPDATED_OBSERVACAO_GERAL)
            .observacaoInterna(UPDATED_OBSERVACAO_INTERNA);
        CompraDTO compraDTO = compraMapper.toDto(updatedCompra);

        restCompraMockMvc.perform(put("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isOk());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeUpdate);
        Compra testCompra = compraList.get(compraList.size() - 1);
        assertThat(testCompra.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCompra.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testCompra.getObservacaoGeral()).isEqualTo(UPDATED_OBSERVACAO_GERAL);
        assertThat(testCompra.getObservacaoInterna()).isEqualTo(UPDATED_OBSERVACAO_INTERNA);
    }

    @Test
    @Transactional
    public void updateNonExistingCompra() throws Exception {
        int databaseSizeBeforeUpdate = compraRepository.findAll().size();

        // Create the Compra
        CompraDTO compraDTO = compraMapper.toDto(compra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompraMockMvc.perform(put("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        int databaseSizeBeforeDelete = compraRepository.findAll().size();

        // Delete the compra
        restCompraMockMvc.perform(delete("/api/compras/{id}", compra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
