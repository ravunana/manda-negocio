package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.VariacaoProduto;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.repository.VariacaoProdutoRepository;
import com.ravunana.manda.service.VariacaoProdutoService;
import com.ravunana.manda.service.dto.VariacaoProdutoDTO;
import com.ravunana.manda.service.mapper.VariacaoProdutoMapper;
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
 * Integration tests for the {@link VariacaoProdutoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class VariacaoProdutoResourceIT {

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final String DEFAULT_COR = "AAAAAAAAAA";
    private static final String UPDATED_COR = "BBBBBBBBBB";

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_TAMANHO = "AAAAAAAAAA";
    private static final String UPDATED_TAMANHO = "BBBBBBBBBB";

    @Autowired
    private VariacaoProdutoRepository variacaoProdutoRepository;

    @Autowired
    private VariacaoProdutoMapper variacaoProdutoMapper;

    @Autowired
    private VariacaoProdutoService variacaoProdutoService;

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

    private MockMvc restVariacaoProdutoMockMvc;

    private VariacaoProduto variacaoProduto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VariacaoProdutoResource variacaoProdutoResource = new VariacaoProdutoResource(variacaoProdutoService);
        this.restVariacaoProdutoMockMvc = MockMvcBuilders.standaloneSetup(variacaoProdutoResource)
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
    public static VariacaoProduto createEntity(EntityManager em) {
        VariacaoProduto variacaoProduto = new VariacaoProduto()
            .genero(DEFAULT_GENERO)
            .cor(DEFAULT_COR)
            .modelo(DEFAULT_MODELO)
            .marca(DEFAULT_MARCA)
            .tamanho(DEFAULT_TAMANHO);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        variacaoProduto.setProduto(produto);
        return variacaoProduto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VariacaoProduto createUpdatedEntity(EntityManager em) {
        VariacaoProduto variacaoProduto = new VariacaoProduto()
            .genero(UPDATED_GENERO)
            .cor(UPDATED_COR)
            .modelo(UPDATED_MODELO)
            .marca(UPDATED_MARCA)
            .tamanho(UPDATED_TAMANHO);
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createUpdatedEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        variacaoProduto.setProduto(produto);
        return variacaoProduto;
    }

    @BeforeEach
    public void initTest() {
        variacaoProduto = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariacaoProduto() throws Exception {
        int databaseSizeBeforeCreate = variacaoProdutoRepository.findAll().size();

        // Create the VariacaoProduto
        VariacaoProdutoDTO variacaoProdutoDTO = variacaoProdutoMapper.toDto(variacaoProduto);
        restVariacaoProdutoMockMvc.perform(post("/api/variacao-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variacaoProdutoDTO)))
            .andExpect(status().isCreated());

        // Validate the VariacaoProduto in the database
        List<VariacaoProduto> variacaoProdutoList = variacaoProdutoRepository.findAll();
        assertThat(variacaoProdutoList).hasSize(databaseSizeBeforeCreate + 1);
        VariacaoProduto testVariacaoProduto = variacaoProdutoList.get(variacaoProdutoList.size() - 1);
        assertThat(testVariacaoProduto.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testVariacaoProduto.getCor()).isEqualTo(DEFAULT_COR);
        assertThat(testVariacaoProduto.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testVariacaoProduto.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testVariacaoProduto.getTamanho()).isEqualTo(DEFAULT_TAMANHO);
    }

    @Test
    @Transactional
    public void createVariacaoProdutoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = variacaoProdutoRepository.findAll().size();

        // Create the VariacaoProduto with an existing ID
        variacaoProduto.setId(1L);
        VariacaoProdutoDTO variacaoProdutoDTO = variacaoProdutoMapper.toDto(variacaoProduto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVariacaoProdutoMockMvc.perform(post("/api/variacao-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variacaoProdutoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VariacaoProduto in the database
        List<VariacaoProduto> variacaoProdutoList = variacaoProdutoRepository.findAll();
        assertThat(variacaoProdutoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVariacaoProdutos() throws Exception {
        // Initialize the database
        variacaoProdutoRepository.saveAndFlush(variacaoProduto);

        // Get all the variacaoProdutoList
        restVariacaoProdutoMockMvc.perform(get("/api/variacao-produtos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variacaoProduto.getId().intValue())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO)))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].tamanho").value(hasItem(DEFAULT_TAMANHO)));
    }
    
    @Test
    @Transactional
    public void getVariacaoProduto() throws Exception {
        // Initialize the database
        variacaoProdutoRepository.saveAndFlush(variacaoProduto);

        // Get the variacaoProduto
        restVariacaoProdutoMockMvc.perform(get("/api/variacao-produtos/{id}", variacaoProduto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(variacaoProduto.getId().intValue()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA))
            .andExpect(jsonPath("$.tamanho").value(DEFAULT_TAMANHO));
    }

    @Test
    @Transactional
    public void getNonExistingVariacaoProduto() throws Exception {
        // Get the variacaoProduto
        restVariacaoProdutoMockMvc.perform(get("/api/variacao-produtos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariacaoProduto() throws Exception {
        // Initialize the database
        variacaoProdutoRepository.saveAndFlush(variacaoProduto);

        int databaseSizeBeforeUpdate = variacaoProdutoRepository.findAll().size();

        // Update the variacaoProduto
        VariacaoProduto updatedVariacaoProduto = variacaoProdutoRepository.findById(variacaoProduto.getId()).get();
        // Disconnect from session so that the updates on updatedVariacaoProduto are not directly saved in db
        em.detach(updatedVariacaoProduto);
        updatedVariacaoProduto
            .genero(UPDATED_GENERO)
            .cor(UPDATED_COR)
            .modelo(UPDATED_MODELO)
            .marca(UPDATED_MARCA)
            .tamanho(UPDATED_TAMANHO);
        VariacaoProdutoDTO variacaoProdutoDTO = variacaoProdutoMapper.toDto(updatedVariacaoProduto);

        restVariacaoProdutoMockMvc.perform(put("/api/variacao-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variacaoProdutoDTO)))
            .andExpect(status().isOk());

        // Validate the VariacaoProduto in the database
        List<VariacaoProduto> variacaoProdutoList = variacaoProdutoRepository.findAll();
        assertThat(variacaoProdutoList).hasSize(databaseSizeBeforeUpdate);
        VariacaoProduto testVariacaoProduto = variacaoProdutoList.get(variacaoProdutoList.size() - 1);
        assertThat(testVariacaoProduto.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testVariacaoProduto.getCor()).isEqualTo(UPDATED_COR);
        assertThat(testVariacaoProduto.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testVariacaoProduto.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testVariacaoProduto.getTamanho()).isEqualTo(UPDATED_TAMANHO);
    }

    @Test
    @Transactional
    public void updateNonExistingVariacaoProduto() throws Exception {
        int databaseSizeBeforeUpdate = variacaoProdutoRepository.findAll().size();

        // Create the VariacaoProduto
        VariacaoProdutoDTO variacaoProdutoDTO = variacaoProdutoMapper.toDto(variacaoProduto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVariacaoProdutoMockMvc.perform(put("/api/variacao-produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variacaoProdutoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VariacaoProduto in the database
        List<VariacaoProduto> variacaoProdutoList = variacaoProdutoRepository.findAll();
        assertThat(variacaoProdutoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVariacaoProduto() throws Exception {
        // Initialize the database
        variacaoProdutoRepository.saveAndFlush(variacaoProduto);

        int databaseSizeBeforeDelete = variacaoProdutoRepository.findAll().size();

        // Delete the variacaoProduto
        restVariacaoProdutoMockMvc.perform(delete("/api/variacao-produtos/{id}", variacaoProduto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VariacaoProduto> variacaoProdutoList = variacaoProdutoRepository.findAll();
        assertThat(variacaoProdutoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
