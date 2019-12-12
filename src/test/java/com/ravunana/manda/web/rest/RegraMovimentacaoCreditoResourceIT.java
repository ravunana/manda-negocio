package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.RegraMovimentacaoCredito;
import com.ravunana.manda.repository.RegraMovimentacaoCreditoRepository;
import com.ravunana.manda.service.RegraMovimentacaoCreditoService;
import com.ravunana.manda.service.dto.RegraMovimentacaoCreditoDTO;
import com.ravunana.manda.service.mapper.RegraMovimentacaoCreditoMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RegraMovimentacaoCreditoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class RegraMovimentacaoCreditoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private RegraMovimentacaoCreditoRepository regraMovimentacaoCreditoRepository;

    @Autowired
    private RegraMovimentacaoCreditoMapper regraMovimentacaoCreditoMapper;

    @Autowired
    private RegraMovimentacaoCreditoService regraMovimentacaoCreditoService;

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

    private MockMvc restRegraMovimentacaoCreditoMockMvc;

    private RegraMovimentacaoCredito regraMovimentacaoCredito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegraMovimentacaoCreditoResource regraMovimentacaoCreditoResource = new RegraMovimentacaoCreditoResource(regraMovimentacaoCreditoService);
        this.restRegraMovimentacaoCreditoMockMvc = MockMvcBuilders.standaloneSetup(regraMovimentacaoCreditoResource)
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
    public static RegraMovimentacaoCredito createEntity(EntityManager em) {
        RegraMovimentacaoCredito regraMovimentacaoCredito = new RegraMovimentacaoCredito()
            .descricao(DEFAULT_DESCRICAO);
        return regraMovimentacaoCredito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegraMovimentacaoCredito createUpdatedEntity(EntityManager em) {
        RegraMovimentacaoCredito regraMovimentacaoCredito = new RegraMovimentacaoCredito()
            .descricao(UPDATED_DESCRICAO);
        return regraMovimentacaoCredito;
    }

    @BeforeEach
    public void initTest() {
        regraMovimentacaoCredito = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegraMovimentacaoCredito() throws Exception {
        int databaseSizeBeforeCreate = regraMovimentacaoCreditoRepository.findAll().size();

        // Create the RegraMovimentacaoCredito
        RegraMovimentacaoCreditoDTO regraMovimentacaoCreditoDTO = regraMovimentacaoCreditoMapper.toDto(regraMovimentacaoCredito);
        restRegraMovimentacaoCreditoMockMvc.perform(post("/api/regra-movimentacao-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regraMovimentacaoCreditoDTO)))
            .andExpect(status().isCreated());

        // Validate the RegraMovimentacaoCredito in the database
        List<RegraMovimentacaoCredito> regraMovimentacaoCreditoList = regraMovimentacaoCreditoRepository.findAll();
        assertThat(regraMovimentacaoCreditoList).hasSize(databaseSizeBeforeCreate + 1);
        RegraMovimentacaoCredito testRegraMovimentacaoCredito = regraMovimentacaoCreditoList.get(regraMovimentacaoCreditoList.size() - 1);
        assertThat(testRegraMovimentacaoCredito.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createRegraMovimentacaoCreditoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regraMovimentacaoCreditoRepository.findAll().size();

        // Create the RegraMovimentacaoCredito with an existing ID
        regraMovimentacaoCredito.setId(1L);
        RegraMovimentacaoCreditoDTO regraMovimentacaoCreditoDTO = regraMovimentacaoCreditoMapper.toDto(regraMovimentacaoCredito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegraMovimentacaoCreditoMockMvc.perform(post("/api/regra-movimentacao-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regraMovimentacaoCreditoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegraMovimentacaoCredito in the database
        List<RegraMovimentacaoCredito> regraMovimentacaoCreditoList = regraMovimentacaoCreditoRepository.findAll();
        assertThat(regraMovimentacaoCreditoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRegraMovimentacaoCreditos() throws Exception {
        // Initialize the database
        regraMovimentacaoCreditoRepository.saveAndFlush(regraMovimentacaoCredito);

        // Get all the regraMovimentacaoCreditoList
        restRegraMovimentacaoCreditoMockMvc.perform(get("/api/regra-movimentacao-creditos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regraMovimentacaoCredito.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getRegraMovimentacaoCredito() throws Exception {
        // Initialize the database
        regraMovimentacaoCreditoRepository.saveAndFlush(regraMovimentacaoCredito);

        // Get the regraMovimentacaoCredito
        restRegraMovimentacaoCreditoMockMvc.perform(get("/api/regra-movimentacao-creditos/{id}", regraMovimentacaoCredito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regraMovimentacaoCredito.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegraMovimentacaoCredito() throws Exception {
        // Get the regraMovimentacaoCredito
        restRegraMovimentacaoCreditoMockMvc.perform(get("/api/regra-movimentacao-creditos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegraMovimentacaoCredito() throws Exception {
        // Initialize the database
        regraMovimentacaoCreditoRepository.saveAndFlush(regraMovimentacaoCredito);

        int databaseSizeBeforeUpdate = regraMovimentacaoCreditoRepository.findAll().size();

        // Update the regraMovimentacaoCredito
        RegraMovimentacaoCredito updatedRegraMovimentacaoCredito = regraMovimentacaoCreditoRepository.findById(regraMovimentacaoCredito.getId()).get();
        // Disconnect from session so that the updates on updatedRegraMovimentacaoCredito are not directly saved in db
        em.detach(updatedRegraMovimentacaoCredito);
        updatedRegraMovimentacaoCredito
            .descricao(UPDATED_DESCRICAO);
        RegraMovimentacaoCreditoDTO regraMovimentacaoCreditoDTO = regraMovimentacaoCreditoMapper.toDto(updatedRegraMovimentacaoCredito);

        restRegraMovimentacaoCreditoMockMvc.perform(put("/api/regra-movimentacao-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regraMovimentacaoCreditoDTO)))
            .andExpect(status().isOk());

        // Validate the RegraMovimentacaoCredito in the database
        List<RegraMovimentacaoCredito> regraMovimentacaoCreditoList = regraMovimentacaoCreditoRepository.findAll();
        assertThat(regraMovimentacaoCreditoList).hasSize(databaseSizeBeforeUpdate);
        RegraMovimentacaoCredito testRegraMovimentacaoCredito = regraMovimentacaoCreditoList.get(regraMovimentacaoCreditoList.size() - 1);
        assertThat(testRegraMovimentacaoCredito.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingRegraMovimentacaoCredito() throws Exception {
        int databaseSizeBeforeUpdate = regraMovimentacaoCreditoRepository.findAll().size();

        // Create the RegraMovimentacaoCredito
        RegraMovimentacaoCreditoDTO regraMovimentacaoCreditoDTO = regraMovimentacaoCreditoMapper.toDto(regraMovimentacaoCredito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegraMovimentacaoCreditoMockMvc.perform(put("/api/regra-movimentacao-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regraMovimentacaoCreditoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegraMovimentacaoCredito in the database
        List<RegraMovimentacaoCredito> regraMovimentacaoCreditoList = regraMovimentacaoCreditoRepository.findAll();
        assertThat(regraMovimentacaoCreditoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegraMovimentacaoCredito() throws Exception {
        // Initialize the database
        regraMovimentacaoCreditoRepository.saveAndFlush(regraMovimentacaoCredito);

        int databaseSizeBeforeDelete = regraMovimentacaoCreditoRepository.findAll().size();

        // Delete the regraMovimentacaoCredito
        restRegraMovimentacaoCreditoMockMvc.perform(delete("/api/regra-movimentacao-creditos/{id}", regraMovimentacaoCredito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegraMovimentacaoCredito> regraMovimentacaoCreditoList = regraMovimentacaoCreditoRepository.findAll();
        assertThat(regraMovimentacaoCreditoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
