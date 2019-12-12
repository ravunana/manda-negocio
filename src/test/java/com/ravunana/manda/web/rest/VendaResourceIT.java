package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.Venda;
import com.ravunana.manda.domain.ItemVenda;
import com.ravunana.manda.domain.User;
import com.ravunana.manda.domain.Cliente;
import com.ravunana.manda.domain.DocumentoComercial;
import com.ravunana.manda.domain.Empresa;
import com.ravunana.manda.repository.VendaRepository;
import com.ravunana.manda.service.VendaService;
import com.ravunana.manda.service.dto.VendaDTO;
import com.ravunana.manda.service.mapper.VendaMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.VendaCriteria;
import com.ravunana.manda.service.VendaQueryService;

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
 * Integration tests for the {@link VendaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class VendaResourceIT {

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
    private VendaRepository vendaRepository;

    @Autowired
    private VendaMapper vendaMapper;

    @Autowired
    private VendaService vendaService;

    @Autowired
    private VendaQueryService vendaQueryService;

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

    private MockMvc restVendaMockMvc;

    private Venda venda;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VendaResource vendaResource = new VendaResource(vendaService, vendaQueryService);
        this.restVendaMockMvc = MockMvcBuilders.standaloneSetup(vendaResource)
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
    public static Venda createEntity(EntityManager em) {
        Venda venda = new Venda()
            .numero(DEFAULT_NUMERO)
            .data(DEFAULT_DATA)
            .observacaoGeral(DEFAULT_OBSERVACAO_GERAL)
            .observacaoInterna(DEFAULT_OBSERVACAO_INTERNA);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        venda.setCliente(cliente);
        // Add required entity
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercial = DocumentoComercialResourceIT.createEntity(em);
            em.persist(documentoComercial);
            em.flush();
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        venda.setTipoDocumento(documentoComercial);
        return venda;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venda createUpdatedEntity(EntityManager em) {
        Venda venda = new Venda()
            .numero(UPDATED_NUMERO)
            .data(UPDATED_DATA)
            .observacaoGeral(UPDATED_OBSERVACAO_GERAL)
            .observacaoInterna(UPDATED_OBSERVACAO_INTERNA);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createUpdatedEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        venda.setCliente(cliente);
        // Add required entity
        DocumentoComercial documentoComercial;
        if (TestUtil.findAll(em, DocumentoComercial.class).isEmpty()) {
            documentoComercial = DocumentoComercialResourceIT.createUpdatedEntity(em);
            em.persist(documentoComercial);
            em.flush();
        } else {
            documentoComercial = TestUtil.findAll(em, DocumentoComercial.class).get(0);
        }
        venda.setTipoDocumento(documentoComercial);
        return venda;
    }

    @BeforeEach
    public void initTest() {
        venda = createEntity(em);
    }

    @Test
    @Transactional
    public void createVenda() throws Exception {
        int databaseSizeBeforeCreate = vendaRepository.findAll().size();

        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);
        restVendaMockMvc.perform(post("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isCreated());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeCreate + 1);
        Venda testVenda = vendaList.get(vendaList.size() - 1);
        assertThat(testVenda.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testVenda.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testVenda.getObservacaoGeral()).isEqualTo(DEFAULT_OBSERVACAO_GERAL);
        assertThat(testVenda.getObservacaoInterna()).isEqualTo(DEFAULT_OBSERVACAO_INTERNA);
    }

    @Test
    @Transactional
    public void createVendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendaRepository.findAll().size();

        // Create the Venda with an existing ID
        venda.setId(1L);
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendaMockMvc.perform(post("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVendas() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList
        restVendaMockMvc.perform(get("/api/vendas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venda.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].observacaoGeral").value(hasItem(DEFAULT_OBSERVACAO_GERAL.toString())))
            .andExpect(jsonPath("$.[*].observacaoInterna").value(hasItem(DEFAULT_OBSERVACAO_INTERNA.toString())));
    }
    
    @Test
    @Transactional
    public void getVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get the venda
        restVendaMockMvc.perform(get("/api/vendas/{id}", venda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(venda.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.observacaoGeral").value(DEFAULT_OBSERVACAO_GERAL.toString()))
            .andExpect(jsonPath("$.observacaoInterna").value(DEFAULT_OBSERVACAO_INTERNA.toString()));
    }


    @Test
    @Transactional
    public void getVendasByIdFiltering() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        Long id = venda.getId();

        defaultVendaShouldBeFound("id.equals=" + id);
        defaultVendaShouldNotBeFound("id.notEquals=" + id);

        defaultVendaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVendaShouldNotBeFound("id.greaterThan=" + id);

        defaultVendaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVendaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVendasByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where numero equals to DEFAULT_NUMERO
        defaultVendaShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the vendaList where numero equals to UPDATED_NUMERO
        defaultVendaShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllVendasByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where numero not equals to DEFAULT_NUMERO
        defaultVendaShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the vendaList where numero not equals to UPDATED_NUMERO
        defaultVendaShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllVendasByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultVendaShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the vendaList where numero equals to UPDATED_NUMERO
        defaultVendaShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllVendasByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where numero is not null
        defaultVendaShouldBeFound("numero.specified=true");

        // Get all the vendaList where numero is null
        defaultVendaShouldNotBeFound("numero.specified=false");
    }
                @Test
    @Transactional
    public void getAllVendasByNumeroContainsSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where numero contains DEFAULT_NUMERO
        defaultVendaShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the vendaList where numero contains UPDATED_NUMERO
        defaultVendaShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllVendasByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where numero does not contain DEFAULT_NUMERO
        defaultVendaShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the vendaList where numero does not contain UPDATED_NUMERO
        defaultVendaShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }


    @Test
    @Transactional
    public void getAllVendasByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data equals to DEFAULT_DATA
        defaultVendaShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the vendaList where data equals to UPDATED_DATA
        defaultVendaShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data not equals to DEFAULT_DATA
        defaultVendaShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the vendaList where data not equals to UPDATED_DATA
        defaultVendaShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data in DEFAULT_DATA or UPDATED_DATA
        defaultVendaShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the vendaList where data equals to UPDATED_DATA
        defaultVendaShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data is not null
        defaultVendaShouldBeFound("data.specified=true");

        // Get all the vendaList where data is null
        defaultVendaShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data is greater than or equal to DEFAULT_DATA
        defaultVendaShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the vendaList where data is greater than or equal to UPDATED_DATA
        defaultVendaShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data is less than or equal to DEFAULT_DATA
        defaultVendaShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the vendaList where data is less than or equal to SMALLER_DATA
        defaultVendaShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data is less than DEFAULT_DATA
        defaultVendaShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the vendaList where data is less than UPDATED_DATA
        defaultVendaShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data is greater than DEFAULT_DATA
        defaultVendaShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the vendaList where data is greater than SMALLER_DATA
        defaultVendaShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllVendasByItemVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);
        ItemVenda itemVenda = ItemVendaResourceIT.createEntity(em);
        em.persist(itemVenda);
        em.flush();
        venda.addItemVenda(itemVenda);
        vendaRepository.saveAndFlush(venda);
        Long itemVendaId = itemVenda.getId();

        // Get all the vendaList where itemVenda equals to itemVendaId
        defaultVendaShouldBeFound("itemVendaId.equals=" + itemVendaId);

        // Get all the vendaList where itemVenda equals to itemVendaId + 1
        defaultVendaShouldNotBeFound("itemVendaId.equals=" + (itemVendaId + 1));
    }


    @Test
    @Transactional
    public void getAllVendasByVendedorIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);
        User vendedor = UserResourceIT.createEntity(em);
        em.persist(vendedor);
        em.flush();
        venda.setVendedor(vendedor);
        vendaRepository.saveAndFlush(venda);
        Long vendedorId = vendedor.getId();

        // Get all the vendaList where vendedor equals to vendedorId
        defaultVendaShouldBeFound("vendedorId.equals=" + vendedorId);

        // Get all the vendaList where vendedor equals to vendedorId + 1
        defaultVendaShouldNotBeFound("vendedorId.equals=" + (vendedorId + 1));
    }


    @Test
    @Transactional
    public void getAllVendasByClienteIsEqualToSomething() throws Exception {
        // Get already existing entity
        Cliente cliente = venda.getCliente();
        vendaRepository.saveAndFlush(venda);
        Long clienteId = cliente.getId();

        // Get all the vendaList where cliente equals to clienteId
        defaultVendaShouldBeFound("clienteId.equals=" + clienteId);

        // Get all the vendaList where cliente equals to clienteId + 1
        defaultVendaShouldNotBeFound("clienteId.equals=" + (clienteId + 1));
    }


    @Test
    @Transactional
    public void getAllVendasByTipoDocumentoIsEqualToSomething() throws Exception {
        // Get already existing entity
        DocumentoComercial tipoDocumento = venda.getTipoDocumento();
        vendaRepository.saveAndFlush(venda);
        Long tipoDocumentoId = tipoDocumento.getId();

        // Get all the vendaList where tipoDocumento equals to tipoDocumentoId
        defaultVendaShouldBeFound("tipoDocumentoId.equals=" + tipoDocumentoId);

        // Get all the vendaList where tipoDocumento equals to tipoDocumentoId + 1
        defaultVendaShouldNotBeFound("tipoDocumentoId.equals=" + (tipoDocumentoId + 1));
    }


    @Test
    @Transactional
    public void getAllVendasByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        venda.setEmpresa(empresa);
        vendaRepository.saveAndFlush(venda);
        Long empresaId = empresa.getId();

        // Get all the vendaList where empresa equals to empresaId
        defaultVendaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the vendaList where empresa equals to empresaId + 1
        defaultVendaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVendaShouldBeFound(String filter) throws Exception {
        restVendaMockMvc.perform(get("/api/vendas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venda.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].observacaoGeral").value(hasItem(DEFAULT_OBSERVACAO_GERAL.toString())))
            .andExpect(jsonPath("$.[*].observacaoInterna").value(hasItem(DEFAULT_OBSERVACAO_INTERNA.toString())));

        // Check, that the count call also returns 1
        restVendaMockMvc.perform(get("/api/vendas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVendaShouldNotBeFound(String filter) throws Exception {
        restVendaMockMvc.perform(get("/api/vendas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVendaMockMvc.perform(get("/api/vendas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVenda() throws Exception {
        // Get the venda
        restVendaMockMvc.perform(get("/api/vendas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();

        // Update the venda
        Venda updatedVenda = vendaRepository.findById(venda.getId()).get();
        // Disconnect from session so that the updates on updatedVenda are not directly saved in db
        em.detach(updatedVenda);
        updatedVenda
            .numero(UPDATED_NUMERO)
            .data(UPDATED_DATA)
            .observacaoGeral(UPDATED_OBSERVACAO_GERAL)
            .observacaoInterna(UPDATED_OBSERVACAO_INTERNA);
        VendaDTO vendaDTO = vendaMapper.toDto(updatedVenda);

        restVendaMockMvc.perform(put("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isOk());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
        Venda testVenda = vendaList.get(vendaList.size() - 1);
        assertThat(testVenda.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testVenda.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testVenda.getObservacaoGeral()).isEqualTo(UPDATED_OBSERVACAO_GERAL);
        assertThat(testVenda.getObservacaoInterna()).isEqualTo(UPDATED_OBSERVACAO_INTERNA);
    }

    @Test
    @Transactional
    public void updateNonExistingVenda() throws Exception {
        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();

        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendaMockMvc.perform(put("/api/vendas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        int databaseSizeBeforeDelete = vendaRepository.findAll().size();

        // Delete the venda
        restVendaMockMvc.perform(delete("/api/vendas/{id}", venda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
