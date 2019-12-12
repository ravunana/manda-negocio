package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.DetalheAduaneiro;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.repository.DetalheAduaneiroRepository;
import com.ravunana.manda.service.DetalheAduaneiroService;
import com.ravunana.manda.service.dto.DetalheAduaneiroDTO;
import com.ravunana.manda.service.mapper.DetalheAduaneiroMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DetalheAduaneiroResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class DetalheAduaneiroResourceIT {

    private static final String DEFAULT_NUMERO_ONU = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_ONU = "BBBBBBBBBB";

    private static final Double DEFAULT_LARGURA = 1D;
    private static final Double UPDATED_LARGURA = 2D;

    private static final Double DEFAULT_ALTURA = 1D;
    private static final Double UPDATED_ALTURA = 2D;

    private static final Double DEFAULT_PESO_LIQUIDO = 1D;
    private static final Double UPDATED_PESO_LIQUIDO = 2D;

    private static final Double DEFAULT_PESO_BRUTO = 1D;
    private static final Double UPDATED_PESO_BRUTO = 2D;

    private static final LocalDate DEFAULT_DATA_FABRICO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FABRICO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_EXPIRACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_EXPIRACAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DetalheAduaneiroRepository detalheAduaneiroRepository;

    @Autowired
    private DetalheAduaneiroMapper detalheAduaneiroMapper;

    @Autowired
    private DetalheAduaneiroService detalheAduaneiroService;

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

    private MockMvc restDetalheAduaneiroMockMvc;

    private DetalheAduaneiro detalheAduaneiro;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetalheAduaneiroResource detalheAduaneiroResource = new DetalheAduaneiroResource(detalheAduaneiroService);
        this.restDetalheAduaneiroMockMvc = MockMvcBuilders.standaloneSetup(detalheAduaneiroResource)
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
    public static DetalheAduaneiro createEntity(EntityManager em) {
        DetalheAduaneiro detalheAduaneiro = new DetalheAduaneiro()
            .numeroONU(DEFAULT_NUMERO_ONU)
            .largura(DEFAULT_LARGURA)
            .altura(DEFAULT_ALTURA)
            .pesoLiquido(DEFAULT_PESO_LIQUIDO)
            .pesoBruto(DEFAULT_PESO_BRUTO)
            .dataFabrico(DEFAULT_DATA_FABRICO)
            .dataExpiracao(DEFAULT_DATA_EXPIRACAO);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        detalheAduaneiro.setProduto(produto);
        return detalheAduaneiro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalheAduaneiro createUpdatedEntity(EntityManager em) {
        DetalheAduaneiro detalheAduaneiro = new DetalheAduaneiro()
            .numeroONU(UPDATED_NUMERO_ONU)
            .largura(UPDATED_LARGURA)
            .altura(UPDATED_ALTURA)
            .pesoLiquido(UPDATED_PESO_LIQUIDO)
            .pesoBruto(UPDATED_PESO_BRUTO)
            .dataFabrico(UPDATED_DATA_FABRICO)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createUpdatedEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        detalheAduaneiro.setProduto(produto);
        return detalheAduaneiro;
    }

    @BeforeEach
    public void initTest() {
        detalheAduaneiro = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalheAduaneiro() throws Exception {
        int databaseSizeBeforeCreate = detalheAduaneiroRepository.findAll().size();

        // Create the DetalheAduaneiro
        DetalheAduaneiroDTO detalheAduaneiroDTO = detalheAduaneiroMapper.toDto(detalheAduaneiro);
        restDetalheAduaneiroMockMvc.perform(post("/api/detalhe-aduaneiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheAduaneiroDTO)))
            .andExpect(status().isCreated());

        // Validate the DetalheAduaneiro in the database
        List<DetalheAduaneiro> detalheAduaneiroList = detalheAduaneiroRepository.findAll();
        assertThat(detalheAduaneiroList).hasSize(databaseSizeBeforeCreate + 1);
        DetalheAduaneiro testDetalheAduaneiro = detalheAduaneiroList.get(detalheAduaneiroList.size() - 1);
        assertThat(testDetalheAduaneiro.getNumeroONU()).isEqualTo(DEFAULT_NUMERO_ONU);
        assertThat(testDetalheAduaneiro.getLargura()).isEqualTo(DEFAULT_LARGURA);
        assertThat(testDetalheAduaneiro.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testDetalheAduaneiro.getPesoLiquido()).isEqualTo(DEFAULT_PESO_LIQUIDO);
        assertThat(testDetalheAduaneiro.getPesoBruto()).isEqualTo(DEFAULT_PESO_BRUTO);
        assertThat(testDetalheAduaneiro.getDataFabrico()).isEqualTo(DEFAULT_DATA_FABRICO);
        assertThat(testDetalheAduaneiro.getDataExpiracao()).isEqualTo(DEFAULT_DATA_EXPIRACAO);
    }

    @Test
    @Transactional
    public void createDetalheAduaneiroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalheAduaneiroRepository.findAll().size();

        // Create the DetalheAduaneiro with an existing ID
        detalheAduaneiro.setId(1L);
        DetalheAduaneiroDTO detalheAduaneiroDTO = detalheAduaneiroMapper.toDto(detalheAduaneiro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalheAduaneiroMockMvc.perform(post("/api/detalhe-aduaneiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheAduaneiroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalheAduaneiro in the database
        List<DetalheAduaneiro> detalheAduaneiroList = detalheAduaneiroRepository.findAll();
        assertThat(detalheAduaneiroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDetalheAduaneiros() throws Exception {
        // Initialize the database
        detalheAduaneiroRepository.saveAndFlush(detalheAduaneiro);

        // Get all the detalheAduaneiroList
        restDetalheAduaneiroMockMvc.perform(get("/api/detalhe-aduaneiros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalheAduaneiro.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroONU").value(hasItem(DEFAULT_NUMERO_ONU)))
            .andExpect(jsonPath("$.[*].largura").value(hasItem(DEFAULT_LARGURA.doubleValue())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoLiquido").value(hasItem(DEFAULT_PESO_LIQUIDO.doubleValue())))
            .andExpect(jsonPath("$.[*].pesoBruto").value(hasItem(DEFAULT_PESO_BRUTO.doubleValue())))
            .andExpect(jsonPath("$.[*].dataFabrico").value(hasItem(DEFAULT_DATA_FABRICO.toString())))
            .andExpect(jsonPath("$.[*].dataExpiracao").value(hasItem(DEFAULT_DATA_EXPIRACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getDetalheAduaneiro() throws Exception {
        // Initialize the database
        detalheAduaneiroRepository.saveAndFlush(detalheAduaneiro);

        // Get the detalheAduaneiro
        restDetalheAduaneiroMockMvc.perform(get("/api/detalhe-aduaneiros/{id}", detalheAduaneiro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalheAduaneiro.getId().intValue()))
            .andExpect(jsonPath("$.numeroONU").value(DEFAULT_NUMERO_ONU))
            .andExpect(jsonPath("$.largura").value(DEFAULT_LARGURA.doubleValue()))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA.doubleValue()))
            .andExpect(jsonPath("$.pesoLiquido").value(DEFAULT_PESO_LIQUIDO.doubleValue()))
            .andExpect(jsonPath("$.pesoBruto").value(DEFAULT_PESO_BRUTO.doubleValue()))
            .andExpect(jsonPath("$.dataFabrico").value(DEFAULT_DATA_FABRICO.toString()))
            .andExpect(jsonPath("$.dataExpiracao").value(DEFAULT_DATA_EXPIRACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDetalheAduaneiro() throws Exception {
        // Get the detalheAduaneiro
        restDetalheAduaneiroMockMvc.perform(get("/api/detalhe-aduaneiros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalheAduaneiro() throws Exception {
        // Initialize the database
        detalheAduaneiroRepository.saveAndFlush(detalheAduaneiro);

        int databaseSizeBeforeUpdate = detalheAduaneiroRepository.findAll().size();

        // Update the detalheAduaneiro
        DetalheAduaneiro updatedDetalheAduaneiro = detalheAduaneiroRepository.findById(detalheAduaneiro.getId()).get();
        // Disconnect from session so that the updates on updatedDetalheAduaneiro are not directly saved in db
        em.detach(updatedDetalheAduaneiro);
        updatedDetalheAduaneiro
            .numeroONU(UPDATED_NUMERO_ONU)
            .largura(UPDATED_LARGURA)
            .altura(UPDATED_ALTURA)
            .pesoLiquido(UPDATED_PESO_LIQUIDO)
            .pesoBruto(UPDATED_PESO_BRUTO)
            .dataFabrico(UPDATED_DATA_FABRICO)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO);
        DetalheAduaneiroDTO detalheAduaneiroDTO = detalheAduaneiroMapper.toDto(updatedDetalheAduaneiro);

        restDetalheAduaneiroMockMvc.perform(put("/api/detalhe-aduaneiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheAduaneiroDTO)))
            .andExpect(status().isOk());

        // Validate the DetalheAduaneiro in the database
        List<DetalheAduaneiro> detalheAduaneiroList = detalheAduaneiroRepository.findAll();
        assertThat(detalheAduaneiroList).hasSize(databaseSizeBeforeUpdate);
        DetalheAduaneiro testDetalheAduaneiro = detalheAduaneiroList.get(detalheAduaneiroList.size() - 1);
        assertThat(testDetalheAduaneiro.getNumeroONU()).isEqualTo(UPDATED_NUMERO_ONU);
        assertThat(testDetalheAduaneiro.getLargura()).isEqualTo(UPDATED_LARGURA);
        assertThat(testDetalheAduaneiro.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testDetalheAduaneiro.getPesoLiquido()).isEqualTo(UPDATED_PESO_LIQUIDO);
        assertThat(testDetalheAduaneiro.getPesoBruto()).isEqualTo(UPDATED_PESO_BRUTO);
        assertThat(testDetalheAduaneiro.getDataFabrico()).isEqualTo(UPDATED_DATA_FABRICO);
        assertThat(testDetalheAduaneiro.getDataExpiracao()).isEqualTo(UPDATED_DATA_EXPIRACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalheAduaneiro() throws Exception {
        int databaseSizeBeforeUpdate = detalheAduaneiroRepository.findAll().size();

        // Create the DetalheAduaneiro
        DetalheAduaneiroDTO detalheAduaneiroDTO = detalheAduaneiroMapper.toDto(detalheAduaneiro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalheAduaneiroMockMvc.perform(put("/api/detalhe-aduaneiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalheAduaneiroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetalheAduaneiro in the database
        List<DetalheAduaneiro> detalheAduaneiroList = detalheAduaneiroRepository.findAll();
        assertThat(detalheAduaneiroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetalheAduaneiro() throws Exception {
        // Initialize the database
        detalheAduaneiroRepository.saveAndFlush(detalheAduaneiro);

        int databaseSizeBeforeDelete = detalheAduaneiroRepository.findAll().size();

        // Delete the detalheAduaneiro
        restDetalheAduaneiroMockMvc.perform(delete("/api/detalhe-aduaneiros/{id}", detalheAduaneiro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetalheAduaneiro> detalheAduaneiroList = detalheAduaneiroRepository.findAll();
        assertThat(detalheAduaneiroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
