package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.RegraMovimentacaoDebito;
import com.ravunana.manda.repository.RegraMovimentacaoDebitoRepository;
import com.ravunana.manda.service.RegraMovimentacaoDebitoService;
import com.ravunana.manda.service.dto.RegraMovimentacaoDebitoDTO;
import com.ravunana.manda.service.mapper.RegraMovimentacaoDebitoMapper;
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
 * Integration tests for the {@link RegraMovimentacaoDebitoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class RegraMovimentacaoDebitoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private RegraMovimentacaoDebitoRepository regraMovimentacaoDebitoRepository;

    @Autowired
    private RegraMovimentacaoDebitoMapper regraMovimentacaoDebitoMapper;

    @Autowired
    private RegraMovimentacaoDebitoService regraMovimentacaoDebitoService;

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

    private MockMvc restRegraMovimentacaoDebitoMockMvc;

    private RegraMovimentacaoDebito regraMovimentacaoDebito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegraMovimentacaoDebitoResource regraMovimentacaoDebitoResource = new RegraMovimentacaoDebitoResource(regraMovimentacaoDebitoService);
        this.restRegraMovimentacaoDebitoMockMvc = MockMvcBuilders.standaloneSetup(regraMovimentacaoDebitoResource)
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
    public static RegraMovimentacaoDebito createEntity(EntityManager em) {
        RegraMovimentacaoDebito regraMovimentacaoDebito = new RegraMovimentacaoDebito()
            .descricao(DEFAULT_DESCRICAO);
        return regraMovimentacaoDebito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegraMovimentacaoDebito createUpdatedEntity(EntityManager em) {
        RegraMovimentacaoDebito regraMovimentacaoDebito = new RegraMovimentacaoDebito()
            .descricao(UPDATED_DESCRICAO);
        return regraMovimentacaoDebito;
    }

    @BeforeEach
    public void initTest() {
        regraMovimentacaoDebito = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegraMovimentacaoDebito() throws Exception {
        int databaseSizeBeforeCreate = regraMovimentacaoDebitoRepository.findAll().size();

        // Create the RegraMovimentacaoDebito
        RegraMovimentacaoDebitoDTO regraMovimentacaoDebitoDTO = regraMovimentacaoDebitoMapper.toDto(regraMovimentacaoDebito);
        restRegraMovimentacaoDebitoMockMvc.perform(post("/api/regra-movimentacao-debitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regraMovimentacaoDebitoDTO)))
            .andExpect(status().isCreated());

        // Validate the RegraMovimentacaoDebito in the database
        List<RegraMovimentacaoDebito> regraMovimentacaoDebitoList = regraMovimentacaoDebitoRepository.findAll();
        assertThat(regraMovimentacaoDebitoList).hasSize(databaseSizeBeforeCreate + 1);
        RegraMovimentacaoDebito testRegraMovimentacaoDebito = regraMovimentacaoDebitoList.get(regraMovimentacaoDebitoList.size() - 1);
        assertThat(testRegraMovimentacaoDebito.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createRegraMovimentacaoDebitoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regraMovimentacaoDebitoRepository.findAll().size();

        // Create the RegraMovimentacaoDebito with an existing ID
        regraMovimentacaoDebito.setId(1L);
        RegraMovimentacaoDebitoDTO regraMovimentacaoDebitoDTO = regraMovimentacaoDebitoMapper.toDto(regraMovimentacaoDebito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegraMovimentacaoDebitoMockMvc.perform(post("/api/regra-movimentacao-debitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regraMovimentacaoDebitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegraMovimentacaoDebito in the database
        List<RegraMovimentacaoDebito> regraMovimentacaoDebitoList = regraMovimentacaoDebitoRepository.findAll();
        assertThat(regraMovimentacaoDebitoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRegraMovimentacaoDebitos() throws Exception {
        // Initialize the database
        regraMovimentacaoDebitoRepository.saveAndFlush(regraMovimentacaoDebito);

        // Get all the regraMovimentacaoDebitoList
        restRegraMovimentacaoDebitoMockMvc.perform(get("/api/regra-movimentacao-debitos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regraMovimentacaoDebito.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getRegraMovimentacaoDebito() throws Exception {
        // Initialize the database
        regraMovimentacaoDebitoRepository.saveAndFlush(regraMovimentacaoDebito);

        // Get the regraMovimentacaoDebito
        restRegraMovimentacaoDebitoMockMvc.perform(get("/api/regra-movimentacao-debitos/{id}", regraMovimentacaoDebito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regraMovimentacaoDebito.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegraMovimentacaoDebito() throws Exception {
        // Get the regraMovimentacaoDebito
        restRegraMovimentacaoDebitoMockMvc.perform(get("/api/regra-movimentacao-debitos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegraMovimentacaoDebito() throws Exception {
        // Initialize the database
        regraMovimentacaoDebitoRepository.saveAndFlush(regraMovimentacaoDebito);

        int databaseSizeBeforeUpdate = regraMovimentacaoDebitoRepository.findAll().size();

        // Update the regraMovimentacaoDebito
        RegraMovimentacaoDebito updatedRegraMovimentacaoDebito = regraMovimentacaoDebitoRepository.findById(regraMovimentacaoDebito.getId()).get();
        // Disconnect from session so that the updates on updatedRegraMovimentacaoDebito are not directly saved in db
        em.detach(updatedRegraMovimentacaoDebito);
        updatedRegraMovimentacaoDebito
            .descricao(UPDATED_DESCRICAO);
        RegraMovimentacaoDebitoDTO regraMovimentacaoDebitoDTO = regraMovimentacaoDebitoMapper.toDto(updatedRegraMovimentacaoDebito);

        restRegraMovimentacaoDebitoMockMvc.perform(put("/api/regra-movimentacao-debitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regraMovimentacaoDebitoDTO)))
            .andExpect(status().isOk());

        // Validate the RegraMovimentacaoDebito in the database
        List<RegraMovimentacaoDebito> regraMovimentacaoDebitoList = regraMovimentacaoDebitoRepository.findAll();
        assertThat(regraMovimentacaoDebitoList).hasSize(databaseSizeBeforeUpdate);
        RegraMovimentacaoDebito testRegraMovimentacaoDebito = regraMovimentacaoDebitoList.get(regraMovimentacaoDebitoList.size() - 1);
        assertThat(testRegraMovimentacaoDebito.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingRegraMovimentacaoDebito() throws Exception {
        int databaseSizeBeforeUpdate = regraMovimentacaoDebitoRepository.findAll().size();

        // Create the RegraMovimentacaoDebito
        RegraMovimentacaoDebitoDTO regraMovimentacaoDebitoDTO = regraMovimentacaoDebitoMapper.toDto(regraMovimentacaoDebito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegraMovimentacaoDebitoMockMvc.perform(put("/api/regra-movimentacao-debitos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regraMovimentacaoDebitoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegraMovimentacaoDebito in the database
        List<RegraMovimentacaoDebito> regraMovimentacaoDebitoList = regraMovimentacaoDebitoRepository.findAll();
        assertThat(regraMovimentacaoDebitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegraMovimentacaoDebito() throws Exception {
        // Initialize the database
        regraMovimentacaoDebitoRepository.saveAndFlush(regraMovimentacaoDebito);

        int databaseSizeBeforeDelete = regraMovimentacaoDebitoRepository.findAll().size();

        // Delete the regraMovimentacaoDebito
        restRegraMovimentacaoDebitoMockMvc.perform(delete("/api/regra-movimentacao-debitos/{id}", regraMovimentacaoDebito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegraMovimentacaoDebito> regraMovimentacaoDebitoList = regraMovimentacaoDebitoRepository.findAll();
        assertThat(regraMovimentacaoDebitoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
