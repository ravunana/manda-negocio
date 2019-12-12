package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.EstoqueConfig;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.repository.EstoqueConfigRepository;
import com.ravunana.manda.service.EstoqueConfigService;
import com.ravunana.manda.service.dto.EstoqueConfigDTO;
import com.ravunana.manda.service.mapper.EstoqueConfigMapper;
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
 * Integration tests for the {@link EstoqueConfigResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class EstoqueConfigResourceIT {

    @Autowired
    private EstoqueConfigRepository estoqueConfigRepository;

    @Autowired
    private EstoqueConfigMapper estoqueConfigMapper;

    @Autowired
    private EstoqueConfigService estoqueConfigService;

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

    private MockMvc restEstoqueConfigMockMvc;

    private EstoqueConfig estoqueConfig;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstoqueConfigResource estoqueConfigResource = new EstoqueConfigResource(estoqueConfigService);
        this.restEstoqueConfigMockMvc = MockMvcBuilders.standaloneSetup(estoqueConfigResource)
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
    public static EstoqueConfig createEntity(EntityManager em) {
        EstoqueConfig estoqueConfig = new EstoqueConfig();
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        estoqueConfig.setProduto(produto);
        return estoqueConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstoqueConfig createUpdatedEntity(EntityManager em) {
        EstoqueConfig estoqueConfig = new EstoqueConfig();
        // Add required entity
        Produto produto;
        if (TestUtil.findAll(em, Produto.class).isEmpty()) {
            produto = ProdutoResourceIT.createUpdatedEntity(em);
            em.persist(produto);
            em.flush();
        } else {
            produto = TestUtil.findAll(em, Produto.class).get(0);
        }
        estoqueConfig.setProduto(produto);
        return estoqueConfig;
    }

    @BeforeEach
    public void initTest() {
        estoqueConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstoqueConfig() throws Exception {
        int databaseSizeBeforeCreate = estoqueConfigRepository.findAll().size();

        // Create the EstoqueConfig
        EstoqueConfigDTO estoqueConfigDTO = estoqueConfigMapper.toDto(estoqueConfig);
        restEstoqueConfigMockMvc.perform(post("/api/estoque-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estoqueConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the EstoqueConfig in the database
        List<EstoqueConfig> estoqueConfigList = estoqueConfigRepository.findAll();
        assertThat(estoqueConfigList).hasSize(databaseSizeBeforeCreate + 1);
        EstoqueConfig testEstoqueConfig = estoqueConfigList.get(estoqueConfigList.size() - 1);
    }

    @Test
    @Transactional
    public void createEstoqueConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estoqueConfigRepository.findAll().size();

        // Create the EstoqueConfig with an existing ID
        estoqueConfig.setId(1L);
        EstoqueConfigDTO estoqueConfigDTO = estoqueConfigMapper.toDto(estoqueConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstoqueConfigMockMvc.perform(post("/api/estoque-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estoqueConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstoqueConfig in the database
        List<EstoqueConfig> estoqueConfigList = estoqueConfigRepository.findAll();
        assertThat(estoqueConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstoqueConfigs() throws Exception {
        // Initialize the database
        estoqueConfigRepository.saveAndFlush(estoqueConfig);

        // Get all the estoqueConfigList
        restEstoqueConfigMockMvc.perform(get("/api/estoque-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estoqueConfig.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getEstoqueConfig() throws Exception {
        // Initialize the database
        estoqueConfigRepository.saveAndFlush(estoqueConfig);

        // Get the estoqueConfig
        restEstoqueConfigMockMvc.perform(get("/api/estoque-configs/{id}", estoqueConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estoqueConfig.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEstoqueConfig() throws Exception {
        // Get the estoqueConfig
        restEstoqueConfigMockMvc.perform(get("/api/estoque-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstoqueConfig() throws Exception {
        // Initialize the database
        estoqueConfigRepository.saveAndFlush(estoqueConfig);

        int databaseSizeBeforeUpdate = estoqueConfigRepository.findAll().size();

        // Update the estoqueConfig
        EstoqueConfig updatedEstoqueConfig = estoqueConfigRepository.findById(estoqueConfig.getId()).get();
        // Disconnect from session so that the updates on updatedEstoqueConfig are not directly saved in db
        em.detach(updatedEstoqueConfig);
        EstoqueConfigDTO estoqueConfigDTO = estoqueConfigMapper.toDto(updatedEstoqueConfig);

        restEstoqueConfigMockMvc.perform(put("/api/estoque-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estoqueConfigDTO)))
            .andExpect(status().isOk());

        // Validate the EstoqueConfig in the database
        List<EstoqueConfig> estoqueConfigList = estoqueConfigRepository.findAll();
        assertThat(estoqueConfigList).hasSize(databaseSizeBeforeUpdate);
        EstoqueConfig testEstoqueConfig = estoqueConfigList.get(estoqueConfigList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingEstoqueConfig() throws Exception {
        int databaseSizeBeforeUpdate = estoqueConfigRepository.findAll().size();

        // Create the EstoqueConfig
        EstoqueConfigDTO estoqueConfigDTO = estoqueConfigMapper.toDto(estoqueConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstoqueConfigMockMvc.perform(put("/api/estoque-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estoqueConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstoqueConfig in the database
        List<EstoqueConfig> estoqueConfigList = estoqueConfigRepository.findAll();
        assertThat(estoqueConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstoqueConfig() throws Exception {
        // Initialize the database
        estoqueConfigRepository.saveAndFlush(estoqueConfig);

        int databaseSizeBeforeDelete = estoqueConfigRepository.findAll().size();

        // Delete the estoqueConfig
        restEstoqueConfigMockMvc.perform(delete("/api/estoque-configs/{id}", estoqueConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstoqueConfig> estoqueConfigList = estoqueConfigRepository.findAll();
        assertThat(estoqueConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
