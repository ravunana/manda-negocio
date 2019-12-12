package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.SerieDocumento;
import com.ravunana.manda.repository.SerieDocumentoRepository;
import com.ravunana.manda.service.SerieDocumentoService;
import com.ravunana.manda.service.dto.SerieDocumentoDTO;
import com.ravunana.manda.service.mapper.SerieDocumentoMapper;
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
 * Integration tests for the {@link SerieDocumentoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class SerieDocumentoResourceIT {

    private static final String DEFAULT_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODIGO_LANCAMENTO_FINANCEIRO = 1;
    private static final Integer UPDATED_CODIGO_LANCAMENTO_FINANCEIRO = 2;

    private static final Integer DEFAULT_CODIGO_ESCRITURACAO_CONTABIL = 1;
    private static final Integer UPDATED_CODIGO_ESCRITURACAO_CONTABIL = 2;

    private static final Integer DEFAULT_CODIGO_VENDA = 1;
    private static final Integer UPDATED_CODIGO_VENDA = 2;

    private static final Integer DEFAULT_CODIGO_COMPRA = 1;
    private static final Integer UPDATED_CODIGO_COMPRA = 2;

    private static final Integer DEFAULT_CODIGO_CLIENTE = 1;
    private static final Integer UPDATED_CODIGO_CLIENTE = 2;

    private static final Integer DEFAULT_CODIGO_FORNECEDOR = 1;
    private static final Integer UPDATED_CODIGO_FORNECEDOR = 2;

    private static final Integer DEFAULT_CODIGO_DEVOLUCAO_VENDA = 1;
    private static final Integer UPDATED_CODIGO_DEVOLUCAO_VENDA = 2;

    private static final Integer DEFAULT_CODIGO_DEVOLUCAO_COMPRA = 1;
    private static final Integer UPDATED_CODIGO_DEVOLUCAO_COMPRA = 2;

    private static final Integer DEFAULT_CODIGO_PRODUTO = 1;
    private static final Integer UPDATED_CODIGO_PRODUTO = 2;

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SerieDocumentoRepository serieDocumentoRepository;

    @Autowired
    private SerieDocumentoMapper serieDocumentoMapper;

    @Autowired
    private SerieDocumentoService serieDocumentoService;

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

    private MockMvc restSerieDocumentoMockMvc;

    private SerieDocumento serieDocumento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SerieDocumentoResource serieDocumentoResource = new SerieDocumentoResource(serieDocumentoService);
        this.restSerieDocumentoMockMvc = MockMvcBuilders.standaloneSetup(serieDocumentoResource)
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
    public static SerieDocumento createEntity(EntityManager em) {
        SerieDocumento serieDocumento = new SerieDocumento()
            .serie(DEFAULT_SERIE)
            .codigoLancamentoFinanceiro(DEFAULT_CODIGO_LANCAMENTO_FINANCEIRO)
            .codigoEscrituracaoContabil(DEFAULT_CODIGO_ESCRITURACAO_CONTABIL)
            .codigoVenda(DEFAULT_CODIGO_VENDA)
            .codigoCompra(DEFAULT_CODIGO_COMPRA)
            .codigoCliente(DEFAULT_CODIGO_CLIENTE)
            .codigoFornecedor(DEFAULT_CODIGO_FORNECEDOR)
            .codigoDevolucaoVenda(DEFAULT_CODIGO_DEVOLUCAO_VENDA)
            .codigoDevolucaoCompra(DEFAULT_CODIGO_DEVOLUCAO_COMPRA)
            .codigoProduto(DEFAULT_CODIGO_PRODUTO)
            .data(DEFAULT_DATA);
        return serieDocumento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SerieDocumento createUpdatedEntity(EntityManager em) {
        SerieDocumento serieDocumento = new SerieDocumento()
            .serie(UPDATED_SERIE)
            .codigoLancamentoFinanceiro(UPDATED_CODIGO_LANCAMENTO_FINANCEIRO)
            .codigoEscrituracaoContabil(UPDATED_CODIGO_ESCRITURACAO_CONTABIL)
            .codigoVenda(UPDATED_CODIGO_VENDA)
            .codigoCompra(UPDATED_CODIGO_COMPRA)
            .codigoCliente(UPDATED_CODIGO_CLIENTE)
            .codigoFornecedor(UPDATED_CODIGO_FORNECEDOR)
            .codigoDevolucaoVenda(UPDATED_CODIGO_DEVOLUCAO_VENDA)
            .codigoDevolucaoCompra(UPDATED_CODIGO_DEVOLUCAO_COMPRA)
            .codigoProduto(UPDATED_CODIGO_PRODUTO)
            .data(UPDATED_DATA);
        return serieDocumento;
    }

    @BeforeEach
    public void initTest() {
        serieDocumento = createEntity(em);
    }

    @Test
    @Transactional
    public void createSerieDocumento() throws Exception {
        int databaseSizeBeforeCreate = serieDocumentoRepository.findAll().size();

        // Create the SerieDocumento
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);
        restSerieDocumentoMockMvc.perform(post("/api/serie-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO)))
            .andExpect(status().isCreated());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeCreate + 1);
        SerieDocumento testSerieDocumento = serieDocumentoList.get(serieDocumentoList.size() - 1);
        assertThat(testSerieDocumento.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testSerieDocumento.getCodigoLancamentoFinanceiro()).isEqualTo(DEFAULT_CODIGO_LANCAMENTO_FINANCEIRO);
        assertThat(testSerieDocumento.getCodigoEscrituracaoContabil()).isEqualTo(DEFAULT_CODIGO_ESCRITURACAO_CONTABIL);
        assertThat(testSerieDocumento.getCodigoVenda()).isEqualTo(DEFAULT_CODIGO_VENDA);
        assertThat(testSerieDocumento.getCodigoCompra()).isEqualTo(DEFAULT_CODIGO_COMPRA);
        assertThat(testSerieDocumento.getCodigoCliente()).isEqualTo(DEFAULT_CODIGO_CLIENTE);
        assertThat(testSerieDocumento.getCodigoFornecedor()).isEqualTo(DEFAULT_CODIGO_FORNECEDOR);
        assertThat(testSerieDocumento.getCodigoDevolucaoVenda()).isEqualTo(DEFAULT_CODIGO_DEVOLUCAO_VENDA);
        assertThat(testSerieDocumento.getCodigoDevolucaoCompra()).isEqualTo(DEFAULT_CODIGO_DEVOLUCAO_COMPRA);
        assertThat(testSerieDocumento.getCodigoProduto()).isEqualTo(DEFAULT_CODIGO_PRODUTO);
        assertThat(testSerieDocumento.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createSerieDocumentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serieDocumentoRepository.findAll().size();

        // Create the SerieDocumento with an existing ID
        serieDocumento.setId(1L);
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSerieDocumentoMockMvc.perform(post("/api/serie-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSerieDocumentos() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get all the serieDocumentoList
        restSerieDocumentoMockMvc.perform(get("/api/serie-documentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serieDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].codigoLancamentoFinanceiro").value(hasItem(DEFAULT_CODIGO_LANCAMENTO_FINANCEIRO)))
            .andExpect(jsonPath("$.[*].codigoEscrituracaoContabil").value(hasItem(DEFAULT_CODIGO_ESCRITURACAO_CONTABIL)))
            .andExpect(jsonPath("$.[*].codigoVenda").value(hasItem(DEFAULT_CODIGO_VENDA)))
            .andExpect(jsonPath("$.[*].codigoCompra").value(hasItem(DEFAULT_CODIGO_COMPRA)))
            .andExpect(jsonPath("$.[*].codigoCliente").value(hasItem(DEFAULT_CODIGO_CLIENTE)))
            .andExpect(jsonPath("$.[*].codigoFornecedor").value(hasItem(DEFAULT_CODIGO_FORNECEDOR)))
            .andExpect(jsonPath("$.[*].codigoDevolucaoVenda").value(hasItem(DEFAULT_CODIGO_DEVOLUCAO_VENDA)))
            .andExpect(jsonPath("$.[*].codigoDevolucaoCompra").value(hasItem(DEFAULT_CODIGO_DEVOLUCAO_COMPRA)))
            .andExpect(jsonPath("$.[*].codigoProduto").value(hasItem(DEFAULT_CODIGO_PRODUTO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getSerieDocumento() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        // Get the serieDocumento
        restSerieDocumentoMockMvc.perform(get("/api/serie-documentos/{id}", serieDocumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serieDocumento.getId().intValue()))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE))
            .andExpect(jsonPath("$.codigoLancamentoFinanceiro").value(DEFAULT_CODIGO_LANCAMENTO_FINANCEIRO))
            .andExpect(jsonPath("$.codigoEscrituracaoContabil").value(DEFAULT_CODIGO_ESCRITURACAO_CONTABIL))
            .andExpect(jsonPath("$.codigoVenda").value(DEFAULT_CODIGO_VENDA))
            .andExpect(jsonPath("$.codigoCompra").value(DEFAULT_CODIGO_COMPRA))
            .andExpect(jsonPath("$.codigoCliente").value(DEFAULT_CODIGO_CLIENTE))
            .andExpect(jsonPath("$.codigoFornecedor").value(DEFAULT_CODIGO_FORNECEDOR))
            .andExpect(jsonPath("$.codigoDevolucaoVenda").value(DEFAULT_CODIGO_DEVOLUCAO_VENDA))
            .andExpect(jsonPath("$.codigoDevolucaoCompra").value(DEFAULT_CODIGO_DEVOLUCAO_COMPRA))
            .andExpect(jsonPath("$.codigoProduto").value(DEFAULT_CODIGO_PRODUTO))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSerieDocumento() throws Exception {
        // Get the serieDocumento
        restSerieDocumentoMockMvc.perform(get("/api/serie-documentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSerieDocumento() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();

        // Update the serieDocumento
        SerieDocumento updatedSerieDocumento = serieDocumentoRepository.findById(serieDocumento.getId()).get();
        // Disconnect from session so that the updates on updatedSerieDocumento are not directly saved in db
        em.detach(updatedSerieDocumento);
        updatedSerieDocumento
            .serie(UPDATED_SERIE)
            .codigoLancamentoFinanceiro(UPDATED_CODIGO_LANCAMENTO_FINANCEIRO)
            .codigoEscrituracaoContabil(UPDATED_CODIGO_ESCRITURACAO_CONTABIL)
            .codigoVenda(UPDATED_CODIGO_VENDA)
            .codigoCompra(UPDATED_CODIGO_COMPRA)
            .codigoCliente(UPDATED_CODIGO_CLIENTE)
            .codigoFornecedor(UPDATED_CODIGO_FORNECEDOR)
            .codigoDevolucaoVenda(UPDATED_CODIGO_DEVOLUCAO_VENDA)
            .codigoDevolucaoCompra(UPDATED_CODIGO_DEVOLUCAO_COMPRA)
            .codigoProduto(UPDATED_CODIGO_PRODUTO)
            .data(UPDATED_DATA);
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(updatedSerieDocumento);

        restSerieDocumentoMockMvc.perform(put("/api/serie-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO)))
            .andExpect(status().isOk());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);
        SerieDocumento testSerieDocumento = serieDocumentoList.get(serieDocumentoList.size() - 1);
        assertThat(testSerieDocumento.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testSerieDocumento.getCodigoLancamentoFinanceiro()).isEqualTo(UPDATED_CODIGO_LANCAMENTO_FINANCEIRO);
        assertThat(testSerieDocumento.getCodigoEscrituracaoContabil()).isEqualTo(UPDATED_CODIGO_ESCRITURACAO_CONTABIL);
        assertThat(testSerieDocumento.getCodigoVenda()).isEqualTo(UPDATED_CODIGO_VENDA);
        assertThat(testSerieDocumento.getCodigoCompra()).isEqualTo(UPDATED_CODIGO_COMPRA);
        assertThat(testSerieDocumento.getCodigoCliente()).isEqualTo(UPDATED_CODIGO_CLIENTE);
        assertThat(testSerieDocumento.getCodigoFornecedor()).isEqualTo(UPDATED_CODIGO_FORNECEDOR);
        assertThat(testSerieDocumento.getCodigoDevolucaoVenda()).isEqualTo(UPDATED_CODIGO_DEVOLUCAO_VENDA);
        assertThat(testSerieDocumento.getCodigoDevolucaoCompra()).isEqualTo(UPDATED_CODIGO_DEVOLUCAO_COMPRA);
        assertThat(testSerieDocumento.getCodigoProduto()).isEqualTo(UPDATED_CODIGO_PRODUTO);
        assertThat(testSerieDocumento.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingSerieDocumento() throws Exception {
        int databaseSizeBeforeUpdate = serieDocumentoRepository.findAll().size();

        // Create the SerieDocumento
        SerieDocumentoDTO serieDocumentoDTO = serieDocumentoMapper.toDto(serieDocumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSerieDocumentoMockMvc.perform(put("/api/serie-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serieDocumentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SerieDocumento in the database
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSerieDocumento() throws Exception {
        // Initialize the database
        serieDocumentoRepository.saveAndFlush(serieDocumento);

        int databaseSizeBeforeDelete = serieDocumentoRepository.findAll().size();

        // Delete the serieDocumento
        restSerieDocumentoMockMvc.perform(delete("/api/serie-documentos/{id}", serieDocumento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SerieDocumento> serieDocumentoList = serieDocumentoRepository.findAll();
        assertThat(serieDocumentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
