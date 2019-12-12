package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.ConversaoUnidade;
import com.ravunana.manda.domain.UnidadeMedida;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.repository.ConversaoUnidadeRepository;
import com.ravunana.manda.service.ConversaoUnidadeService;
import com.ravunana.manda.service.dto.ConversaoUnidadeDTO;
import com.ravunana.manda.service.mapper.ConversaoUnidadeMapper;
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
 * Integration tests for the {@link ConversaoUnidadeResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ConversaoUnidadeResourceIT {

    private static final Double DEFAULT_VALOR_ENTRADA = 1D;
    private static final Double UPDATED_VALOR_ENTRADA = 2D;

    private static final Double DEFAULT_VALOR_SAIDA = 1D;
    private static final Double UPDATED_VALOR_SAIDA = 2D;

    @Autowired
    private ConversaoUnidadeRepository conversaoUnidadeRepository;

    @Autowired
    private ConversaoUnidadeMapper conversaoUnidadeMapper;

    @Autowired
    private ConversaoUnidadeService conversaoUnidadeService;

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

    private MockMvc restConversaoUnidadeMockMvc;

    private ConversaoUnidade conversaoUnidade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConversaoUnidadeResource conversaoUnidadeResource = new ConversaoUnidadeResource(conversaoUnidadeService);
        this.restConversaoUnidadeMockMvc = MockMvcBuilders.standaloneSetup(conversaoUnidadeResource)
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
    public static ConversaoUnidade createEntity(EntityManager em) {
        ConversaoUnidade conversaoUnidade = new ConversaoUnidade()
            .valorEntrada(DEFAULT_VALOR_ENTRADA)
            .valorSaida(DEFAULT_VALOR_SAIDA);
        // Add required entity
        UnidadeMedida unidadeMedida;
        if (TestUtil.findAll(em, UnidadeMedida.class).isEmpty()) {
            unidadeMedida = UnidadeMedidaResourceIT.createEntity(em);
            em.persist(unidadeMedida);
            em.flush();
        } else {
            unidadeMedida = TestUtil.findAll(em, UnidadeMedida.class).get(0);
        }
        conversaoUnidade.setEntrada(unidadeMedida);
        // Add required entity
        conversaoUnidade.setSaida(unidadeMedida);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        conversaoUnidade.setProduto(produto);
        return conversaoUnidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConversaoUnidade createUpdatedEntity(EntityManager em) {
        ConversaoUnidade conversaoUnidade = new ConversaoUnidade()
            .valorEntrada(UPDATED_VALOR_ENTRADA)
            .valorSaida(UPDATED_VALOR_SAIDA);
        // Add required entity
        UnidadeMedida unidadeMedida;
        if (TestUtil.findAll(em, UnidadeMedida.class).isEmpty()) {
            unidadeMedida = UnidadeMedidaResourceIT.createUpdatedEntity(em);
            em.persist(unidadeMedida);
            em.flush();
        } else {
            unidadeMedida = TestUtil.findAll(em, UnidadeMedida.class).get(0);
        }
        conversaoUnidade.setEntrada(unidadeMedida);
        // Add required entity
        conversaoUnidade.setSaida(unidadeMedida);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createUpdatedEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        conversaoUnidade.setProduto(produto);
        return conversaoUnidade;
    }

    @BeforeEach
    public void initTest() {
        conversaoUnidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createConversaoUnidade() throws Exception {
        int databaseSizeBeforeCreate = conversaoUnidadeRepository.findAll().size();

        // Create the ConversaoUnidade
        ConversaoUnidadeDTO conversaoUnidadeDTO = conversaoUnidadeMapper.toDto(conversaoUnidade);
        restConversaoUnidadeMockMvc.perform(post("/api/conversao-unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversaoUnidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the ConversaoUnidade in the database
        List<ConversaoUnidade> conversaoUnidadeList = conversaoUnidadeRepository.findAll();
        assertThat(conversaoUnidadeList).hasSize(databaseSizeBeforeCreate + 1);
        ConversaoUnidade testConversaoUnidade = conversaoUnidadeList.get(conversaoUnidadeList.size() - 1);
        assertThat(testConversaoUnidade.getValorEntrada()).isEqualTo(DEFAULT_VALOR_ENTRADA);
        assertThat(testConversaoUnidade.getValorSaida()).isEqualTo(DEFAULT_VALOR_SAIDA);
    }

    @Test
    @Transactional
    public void createConversaoUnidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conversaoUnidadeRepository.findAll().size();

        // Create the ConversaoUnidade with an existing ID
        conversaoUnidade.setId(1L);
        ConversaoUnidadeDTO conversaoUnidadeDTO = conversaoUnidadeMapper.toDto(conversaoUnidade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConversaoUnidadeMockMvc.perform(post("/api/conversao-unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversaoUnidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConversaoUnidade in the database
        List<ConversaoUnidade> conversaoUnidadeList = conversaoUnidadeRepository.findAll();
        assertThat(conversaoUnidadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValorEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversaoUnidadeRepository.findAll().size();
        // set the field null
        conversaoUnidade.setValorEntrada(null);

        // Create the ConversaoUnidade, which fails.
        ConversaoUnidadeDTO conversaoUnidadeDTO = conversaoUnidadeMapper.toDto(conversaoUnidade);

        restConversaoUnidadeMockMvc.perform(post("/api/conversao-unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversaoUnidadeDTO)))
            .andExpect(status().isBadRequest());

        List<ConversaoUnidade> conversaoUnidadeList = conversaoUnidadeRepository.findAll();
        assertThat(conversaoUnidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorSaidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversaoUnidadeRepository.findAll().size();
        // set the field null
        conversaoUnidade.setValorSaida(null);

        // Create the ConversaoUnidade, which fails.
        ConversaoUnidadeDTO conversaoUnidadeDTO = conversaoUnidadeMapper.toDto(conversaoUnidade);

        restConversaoUnidadeMockMvc.perform(post("/api/conversao-unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversaoUnidadeDTO)))
            .andExpect(status().isBadRequest());

        List<ConversaoUnidade> conversaoUnidadeList = conversaoUnidadeRepository.findAll();
        assertThat(conversaoUnidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConversaoUnidades() throws Exception {
        // Initialize the database
        conversaoUnidadeRepository.saveAndFlush(conversaoUnidade);

        // Get all the conversaoUnidadeList
        restConversaoUnidadeMockMvc.perform(get("/api/conversao-unidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversaoUnidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].valorEntrada").value(hasItem(DEFAULT_VALOR_ENTRADA.doubleValue())))
            .andExpect(jsonPath("$.[*].valorSaida").value(hasItem(DEFAULT_VALOR_SAIDA.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getConversaoUnidade() throws Exception {
        // Initialize the database
        conversaoUnidadeRepository.saveAndFlush(conversaoUnidade);

        // Get the conversaoUnidade
        restConversaoUnidadeMockMvc.perform(get("/api/conversao-unidades/{id}", conversaoUnidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conversaoUnidade.getId().intValue()))
            .andExpect(jsonPath("$.valorEntrada").value(DEFAULT_VALOR_ENTRADA.doubleValue()))
            .andExpect(jsonPath("$.valorSaida").value(DEFAULT_VALOR_SAIDA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConversaoUnidade() throws Exception {
        // Get the conversaoUnidade
        restConversaoUnidadeMockMvc.perform(get("/api/conversao-unidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConversaoUnidade() throws Exception {
        // Initialize the database
        conversaoUnidadeRepository.saveAndFlush(conversaoUnidade);

        int databaseSizeBeforeUpdate = conversaoUnidadeRepository.findAll().size();

        // Update the conversaoUnidade
        ConversaoUnidade updatedConversaoUnidade = conversaoUnidadeRepository.findById(conversaoUnidade.getId()).get();
        // Disconnect from session so that the updates on updatedConversaoUnidade are not directly saved in db
        em.detach(updatedConversaoUnidade);
        updatedConversaoUnidade
            .valorEntrada(UPDATED_VALOR_ENTRADA)
            .valorSaida(UPDATED_VALOR_SAIDA);
        ConversaoUnidadeDTO conversaoUnidadeDTO = conversaoUnidadeMapper.toDto(updatedConversaoUnidade);

        restConversaoUnidadeMockMvc.perform(put("/api/conversao-unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversaoUnidadeDTO)))
            .andExpect(status().isOk());

        // Validate the ConversaoUnidade in the database
        List<ConversaoUnidade> conversaoUnidadeList = conversaoUnidadeRepository.findAll();
        assertThat(conversaoUnidadeList).hasSize(databaseSizeBeforeUpdate);
        ConversaoUnidade testConversaoUnidade = conversaoUnidadeList.get(conversaoUnidadeList.size() - 1);
        assertThat(testConversaoUnidade.getValorEntrada()).isEqualTo(UPDATED_VALOR_ENTRADA);
        assertThat(testConversaoUnidade.getValorSaida()).isEqualTo(UPDATED_VALOR_SAIDA);
    }

    @Test
    @Transactional
    public void updateNonExistingConversaoUnidade() throws Exception {
        int databaseSizeBeforeUpdate = conversaoUnidadeRepository.findAll().size();

        // Create the ConversaoUnidade
        ConversaoUnidadeDTO conversaoUnidadeDTO = conversaoUnidadeMapper.toDto(conversaoUnidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConversaoUnidadeMockMvc.perform(put("/api/conversao-unidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversaoUnidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConversaoUnidade in the database
        List<ConversaoUnidade> conversaoUnidadeList = conversaoUnidadeRepository.findAll();
        assertThat(conversaoUnidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConversaoUnidade() throws Exception {
        // Initialize the database
        conversaoUnidadeRepository.saveAndFlush(conversaoUnidade);

        int databaseSizeBeforeDelete = conversaoUnidadeRepository.findAll().size();

        // Delete the conversaoUnidade
        restConversaoUnidadeMockMvc.perform(delete("/api/conversao-unidades/{id}", conversaoUnidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConversaoUnidade> conversaoUnidadeList = conversaoUnidadeRepository.findAll();
        assertThat(conversaoUnidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
