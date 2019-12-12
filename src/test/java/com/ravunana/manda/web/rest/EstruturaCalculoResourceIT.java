package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.EstruturaCalculo;
import com.ravunana.manda.domain.User;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.repository.EstruturaCalculoRepository;
import com.ravunana.manda.service.EstruturaCalculoService;
import com.ravunana.manda.service.dto.EstruturaCalculoDTO;
import com.ravunana.manda.service.mapper.EstruturaCalculoMapper;
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
import java.math.BigDecimal;
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
 * Integration tests for the {@link EstruturaCalculoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class EstruturaCalculoResourceIT {

    private static final BigDecimal DEFAULT_CUSTO_MATERIA_PRIMA = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUSTO_MATERIA_PRIMA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CUSTO_MAO_OBRA = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUSTO_MAO_OBRA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CUSTO_EMBALAGEM = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUSTO_EMBALAGEM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CUSTO_AQUISICAO = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUSTO_AQUISICAO = new BigDecimal(2);

    private static final Double DEFAULT_COMISSAO = 0D;
    private static final Double UPDATED_COMISSAO = 1D;

    private static final Double DEFAULT_MARGEM_LUCRO = 0D;
    private static final Double UPDATED_MARGEM_LUCRO = 1D;

    private static final BigDecimal DEFAULT_PRECO_VENDA = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECO_VENDA = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EstruturaCalculoRepository estruturaCalculoRepository;

    @Autowired
    private EstruturaCalculoMapper estruturaCalculoMapper;

    @Autowired
    private EstruturaCalculoService estruturaCalculoService;

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

    private MockMvc restEstruturaCalculoMockMvc;

    private EstruturaCalculo estruturaCalculo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstruturaCalculoResource estruturaCalculoResource = new EstruturaCalculoResource(estruturaCalculoService);
        this.restEstruturaCalculoMockMvc = MockMvcBuilders.standaloneSetup(estruturaCalculoResource)
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
    public static EstruturaCalculo createEntity(EntityManager em) {
        EstruturaCalculo estruturaCalculo = new EstruturaCalculo()
            .custoMateriaPrima(DEFAULT_CUSTO_MATERIA_PRIMA)
            .custoMaoObra(DEFAULT_CUSTO_MAO_OBRA)
            .custoEmbalagem(DEFAULT_CUSTO_EMBALAGEM)
            .custoAquisicao(DEFAULT_CUSTO_AQUISICAO)
            .comissao(DEFAULT_COMISSAO)
            .margemLucro(DEFAULT_MARGEM_LUCRO)
            .precoVenda(DEFAULT_PRECO_VENDA)
            .data(DEFAULT_DATA);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        estruturaCalculo.setUtilizador(user);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        estruturaCalculo.setProduto(produto);
        return estruturaCalculo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstruturaCalculo createUpdatedEntity(EntityManager em) {
        EstruturaCalculo estruturaCalculo = new EstruturaCalculo()
            .custoMateriaPrima(UPDATED_CUSTO_MATERIA_PRIMA)
            .custoMaoObra(UPDATED_CUSTO_MAO_OBRA)
            .custoEmbalagem(UPDATED_CUSTO_EMBALAGEM)
            .custoAquisicao(UPDATED_CUSTO_AQUISICAO)
            .comissao(UPDATED_COMISSAO)
            .margemLucro(UPDATED_MARGEM_LUCRO)
            .precoVenda(UPDATED_PRECO_VENDA)
            .data(UPDATED_DATA);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        estruturaCalculo.setUtilizador(user);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createUpdatedEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        estruturaCalculo.setProduto(produto);
        return estruturaCalculo;
    }

    @BeforeEach
    public void initTest() {
        estruturaCalculo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstruturaCalculo() throws Exception {
        int databaseSizeBeforeCreate = estruturaCalculoRepository.findAll().size();

        // Create the EstruturaCalculo
        EstruturaCalculoDTO estruturaCalculoDTO = estruturaCalculoMapper.toDto(estruturaCalculo);
        restEstruturaCalculoMockMvc.perform(post("/api/estrutura-calculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estruturaCalculoDTO)))
            .andExpect(status().isCreated());

        // Validate the EstruturaCalculo in the database
        List<EstruturaCalculo> estruturaCalculoList = estruturaCalculoRepository.findAll();
        assertThat(estruturaCalculoList).hasSize(databaseSizeBeforeCreate + 1);
        EstruturaCalculo testEstruturaCalculo = estruturaCalculoList.get(estruturaCalculoList.size() - 1);
        assertThat(testEstruturaCalculo.getCustoMateriaPrima()).isEqualTo(DEFAULT_CUSTO_MATERIA_PRIMA);
        assertThat(testEstruturaCalculo.getCustoMaoObra()).isEqualTo(DEFAULT_CUSTO_MAO_OBRA);
        assertThat(testEstruturaCalculo.getCustoEmbalagem()).isEqualTo(DEFAULT_CUSTO_EMBALAGEM);
        assertThat(testEstruturaCalculo.getCustoAquisicao()).isEqualTo(DEFAULT_CUSTO_AQUISICAO);
        assertThat(testEstruturaCalculo.getComissao()).isEqualTo(DEFAULT_COMISSAO);
        assertThat(testEstruturaCalculo.getMargemLucro()).isEqualTo(DEFAULT_MARGEM_LUCRO);
        assertThat(testEstruturaCalculo.getPrecoVenda()).isEqualTo(DEFAULT_PRECO_VENDA);
        assertThat(testEstruturaCalculo.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createEstruturaCalculoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estruturaCalculoRepository.findAll().size();

        // Create the EstruturaCalculo with an existing ID
        estruturaCalculo.setId(1L);
        EstruturaCalculoDTO estruturaCalculoDTO = estruturaCalculoMapper.toDto(estruturaCalculo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstruturaCalculoMockMvc.perform(post("/api/estrutura-calculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estruturaCalculoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstruturaCalculo in the database
        List<EstruturaCalculo> estruturaCalculoList = estruturaCalculoRepository.findAll();
        assertThat(estruturaCalculoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstruturaCalculos() throws Exception {
        // Initialize the database
        estruturaCalculoRepository.saveAndFlush(estruturaCalculo);

        // Get all the estruturaCalculoList
        restEstruturaCalculoMockMvc.perform(get("/api/estrutura-calculos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estruturaCalculo.getId().intValue())))
            .andExpect(jsonPath("$.[*].custoMateriaPrima").value(hasItem(DEFAULT_CUSTO_MATERIA_PRIMA.intValue())))
            .andExpect(jsonPath("$.[*].custoMaoObra").value(hasItem(DEFAULT_CUSTO_MAO_OBRA.intValue())))
            .andExpect(jsonPath("$.[*].custoEmbalagem").value(hasItem(DEFAULT_CUSTO_EMBALAGEM.intValue())))
            .andExpect(jsonPath("$.[*].custoAquisicao").value(hasItem(DEFAULT_CUSTO_AQUISICAO.intValue())))
            .andExpect(jsonPath("$.[*].comissao").value(hasItem(DEFAULT_COMISSAO.doubleValue())))
            .andExpect(jsonPath("$.[*].margemLucro").value(hasItem(DEFAULT_MARGEM_LUCRO.doubleValue())))
            .andExpect(jsonPath("$.[*].precoVenda").value(hasItem(DEFAULT_PRECO_VENDA.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getEstruturaCalculo() throws Exception {
        // Initialize the database
        estruturaCalculoRepository.saveAndFlush(estruturaCalculo);

        // Get the estruturaCalculo
        restEstruturaCalculoMockMvc.perform(get("/api/estrutura-calculos/{id}", estruturaCalculo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estruturaCalculo.getId().intValue()))
            .andExpect(jsonPath("$.custoMateriaPrima").value(DEFAULT_CUSTO_MATERIA_PRIMA.intValue()))
            .andExpect(jsonPath("$.custoMaoObra").value(DEFAULT_CUSTO_MAO_OBRA.intValue()))
            .andExpect(jsonPath("$.custoEmbalagem").value(DEFAULT_CUSTO_EMBALAGEM.intValue()))
            .andExpect(jsonPath("$.custoAquisicao").value(DEFAULT_CUSTO_AQUISICAO.intValue()))
            .andExpect(jsonPath("$.comissao").value(DEFAULT_COMISSAO.doubleValue()))
            .andExpect(jsonPath("$.margemLucro").value(DEFAULT_MARGEM_LUCRO.doubleValue()))
            .andExpect(jsonPath("$.precoVenda").value(DEFAULT_PRECO_VENDA.intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    public void getNonExistingEstruturaCalculo() throws Exception {
        // Get the estruturaCalculo
        restEstruturaCalculoMockMvc.perform(get("/api/estrutura-calculos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstruturaCalculo() throws Exception {
        // Initialize the database
        estruturaCalculoRepository.saveAndFlush(estruturaCalculo);

        int databaseSizeBeforeUpdate = estruturaCalculoRepository.findAll().size();

        // Update the estruturaCalculo
        EstruturaCalculo updatedEstruturaCalculo = estruturaCalculoRepository.findById(estruturaCalculo.getId()).get();
        // Disconnect from session so that the updates on updatedEstruturaCalculo are not directly saved in db
        em.detach(updatedEstruturaCalculo);
        updatedEstruturaCalculo
            .custoMateriaPrima(UPDATED_CUSTO_MATERIA_PRIMA)
            .custoMaoObra(UPDATED_CUSTO_MAO_OBRA)
            .custoEmbalagem(UPDATED_CUSTO_EMBALAGEM)
            .custoAquisicao(UPDATED_CUSTO_AQUISICAO)
            .comissao(UPDATED_COMISSAO)
            .margemLucro(UPDATED_MARGEM_LUCRO)
            .precoVenda(UPDATED_PRECO_VENDA)
            .data(UPDATED_DATA);
        EstruturaCalculoDTO estruturaCalculoDTO = estruturaCalculoMapper.toDto(updatedEstruturaCalculo);

        restEstruturaCalculoMockMvc.perform(put("/api/estrutura-calculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estruturaCalculoDTO)))
            .andExpect(status().isOk());

        // Validate the EstruturaCalculo in the database
        List<EstruturaCalculo> estruturaCalculoList = estruturaCalculoRepository.findAll();
        assertThat(estruturaCalculoList).hasSize(databaseSizeBeforeUpdate);
        EstruturaCalculo testEstruturaCalculo = estruturaCalculoList.get(estruturaCalculoList.size() - 1);
        assertThat(testEstruturaCalculo.getCustoMateriaPrima()).isEqualTo(UPDATED_CUSTO_MATERIA_PRIMA);
        assertThat(testEstruturaCalculo.getCustoMaoObra()).isEqualTo(UPDATED_CUSTO_MAO_OBRA);
        assertThat(testEstruturaCalculo.getCustoEmbalagem()).isEqualTo(UPDATED_CUSTO_EMBALAGEM);
        assertThat(testEstruturaCalculo.getCustoAquisicao()).isEqualTo(UPDATED_CUSTO_AQUISICAO);
        assertThat(testEstruturaCalculo.getComissao()).isEqualTo(UPDATED_COMISSAO);
        assertThat(testEstruturaCalculo.getMargemLucro()).isEqualTo(UPDATED_MARGEM_LUCRO);
        assertThat(testEstruturaCalculo.getPrecoVenda()).isEqualTo(UPDATED_PRECO_VENDA);
        assertThat(testEstruturaCalculo.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingEstruturaCalculo() throws Exception {
        int databaseSizeBeforeUpdate = estruturaCalculoRepository.findAll().size();

        // Create the EstruturaCalculo
        EstruturaCalculoDTO estruturaCalculoDTO = estruturaCalculoMapper.toDto(estruturaCalculo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstruturaCalculoMockMvc.perform(put("/api/estrutura-calculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estruturaCalculoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstruturaCalculo in the database
        List<EstruturaCalculo> estruturaCalculoList = estruturaCalculoRepository.findAll();
        assertThat(estruturaCalculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstruturaCalculo() throws Exception {
        // Initialize the database
        estruturaCalculoRepository.saveAndFlush(estruturaCalculo);

        int databaseSizeBeforeDelete = estruturaCalculoRepository.findAll().size();

        // Delete the estruturaCalculo
        restEstruturaCalculoMockMvc.perform(delete("/api/estrutura-calculos/{id}", estruturaCalculo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstruturaCalculo> estruturaCalculoList = estruturaCalculoRepository.findAll();
        assertThat(estruturaCalculoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
