package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.Produto;
import com.ravunana.manda.domain.CompostoProduto;
import com.ravunana.manda.domain.ConversaoUnidade;
import com.ravunana.manda.domain.EstruturaCalculo;
import com.ravunana.manda.domain.ItemCompra;
import com.ravunana.manda.domain.ItemVenda;
import com.ravunana.manda.domain.User;
import com.ravunana.manda.domain.Imposto;
import com.ravunana.manda.domain.Fornecedor;
import com.ravunana.manda.domain.LocalArmazenamento;
import com.ravunana.manda.domain.FamiliaProduto;
import com.ravunana.manda.domain.Empresa;
import com.ravunana.manda.domain.FluxoDocumento;
import com.ravunana.manda.repository.ProdutoRepository;
import com.ravunana.manda.service.ProdutoService;
import com.ravunana.manda.service.dto.ProdutoDTO;
import com.ravunana.manda.service.mapper.ProdutoMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.ProdutoCriteria;
import com.ravunana.manda.service.ProdutoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.ravunana.manda.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProdutoResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class ProdutoResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_EAN = "AAAAAAAAAA";
    private static final String UPDATED_EAN = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_COMPOSTO = false;
    private static final Boolean UPDATED_COMPOSTO = true;

    private static final Double DEFAULT_ESTOQUE_MINIMO = 0D;
    private static final Double UPDATED_ESTOQUE_MINIMO = 1D;
    private static final Double SMALLER_ESTOQUE_MINIMO = 0D - 1D;

    private static final Double DEFAULT_ESTOQUE_MAXIMO = 0D;
    private static final Double UPDATED_ESTOQUE_MAXIMO = 1D;
    private static final Double SMALLER_ESTOQUE_MAXIMO = 0D - 1D;

    private static final Double DEFAULT_ESTOQUE_ATUAL = 0D;
    private static final Double UPDATED_ESTOQUE_ATUAL = 1D;
    private static final Double SMALLER_ESTOQUE_ATUAL = 0D - 1D;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MOSTRAR_PDV = false;
    private static final Boolean UPDATED_MOSTRAR_PDV = true;

    private static final String DEFAULT_PRAZO_MEDIO_ENTREGA = "AAAAAAAAAA";
    private static final String UPDATED_PRAZO_MEDIO_ENTREGA = "BBBBBBBBBB";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoRepository produtoRepositoryMock;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Mock
    private ProdutoService produtoServiceMock;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoQueryService produtoQueryService;

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

    private MockMvc restProdutoMockMvc;

    private Produto produto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProdutoResource produtoResource = new ProdutoResource(produtoService, produtoQueryService);
        this.restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
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
    public static Produto createEntity(EntityManager em) {
        Produto produto = new Produto()
            .tipo(DEFAULT_TIPO)
            .ean(DEFAULT_EAN)
            .numero(DEFAULT_NUMERO)
            .nome(DEFAULT_NOME)
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE)
            .composto(DEFAULT_COMPOSTO)
            .estoqueMinimo(DEFAULT_ESTOQUE_MINIMO)
            .estoqueMaximo(DEFAULT_ESTOQUE_MAXIMO)
            .estoqueAtual(DEFAULT_ESTOQUE_ATUAL)
            .descricao(DEFAULT_DESCRICAO)
            .mostrarPDV(DEFAULT_MOSTRAR_PDV)
            .prazoMedioEntrega(DEFAULT_PRAZO_MEDIO_ENTREGA);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        produto.setUtilizador(user);
        // Add required entity
        FamiliaProduto familiaProduto;
        if (TestUtil.findAll(em, FamiliaProduto.class).isEmpty()) {
            familiaProduto = FamiliaProdutoResourceIT.createEntity(em);
            em.persist(familiaProduto);
            em.flush();
        } else {
            familiaProduto = TestUtil.findAll(em, FamiliaProduto.class).get(0);
        }
        produto.setFamilia(familiaProduto);
        // Add required entity
        FluxoDocumento fluxoDocumento;
        if (TestUtil.findAll(em, FluxoDocumento.class).isEmpty()) {
            fluxoDocumento = FluxoDocumentoResourceIT.createEntity(em);
            em.persist(fluxoDocumento);
            em.flush();
        } else {
            fluxoDocumento = TestUtil.findAll(em, FluxoDocumento.class).get(0);
        }
        produto.setEstado(fluxoDocumento);
        return produto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produto createUpdatedEntity(EntityManager em) {
        Produto produto = new Produto()
            .tipo(UPDATED_TIPO)
            .ean(UPDATED_EAN)
            .numero(UPDATED_NUMERO)
            .nome(UPDATED_NOME)
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .composto(UPDATED_COMPOSTO)
            .estoqueMinimo(UPDATED_ESTOQUE_MINIMO)
            .estoqueMaximo(UPDATED_ESTOQUE_MAXIMO)
            .estoqueAtual(UPDATED_ESTOQUE_ATUAL)
            .descricao(UPDATED_DESCRICAO)
            .mostrarPDV(UPDATED_MOSTRAR_PDV)
            .prazoMedioEntrega(UPDATED_PRAZO_MEDIO_ENTREGA);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        produto.setUtilizador(user);
        // Add required entity
        FamiliaProduto familiaProduto;
        if (TestUtil.findAll(em, FamiliaProduto.class).isEmpty()) {
            familiaProduto = FamiliaProdutoResourceIT.createUpdatedEntity(em);
            em.persist(familiaProduto);
            em.flush();
        } else {
            familiaProduto = TestUtil.findAll(em, FamiliaProduto.class).get(0);
        }
        produto.setFamilia(familiaProduto);
        // Add required entity
        FluxoDocumento fluxoDocumento;
        if (TestUtil.findAll(em, FluxoDocumento.class).isEmpty()) {
            fluxoDocumento = FluxoDocumentoResourceIT.createUpdatedEntity(em);
            em.persist(fluxoDocumento);
            em.flush();
        } else {
            fluxoDocumento = TestUtil.findAll(em, FluxoDocumento.class).get(0);
        }
        produto.setEstado(fluxoDocumento);
        return produto;
    }

    @BeforeEach
    public void initTest() {
        produto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduto() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isCreated());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate + 1);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testProduto.getEan()).isEqualTo(DEFAULT_EAN);
        assertThat(testProduto.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProduto.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testProduto.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testProduto.isComposto()).isEqualTo(DEFAULT_COMPOSTO);
        assertThat(testProduto.getEstoqueMinimo()).isEqualTo(DEFAULT_ESTOQUE_MINIMO);
        assertThat(testProduto.getEstoqueMaximo()).isEqualTo(DEFAULT_ESTOQUE_MAXIMO);
        assertThat(testProduto.getEstoqueAtual()).isEqualTo(DEFAULT_ESTOQUE_ATUAL);
        assertThat(testProduto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProduto.isMostrarPDV()).isEqualTo(DEFAULT_MOSTRAR_PDV);
        assertThat(testProduto.getPrazoMedioEntrega()).isEqualTo(DEFAULT_PRAZO_MEDIO_ENTREGA);
    }

    @Test
    @Transactional
    public void createProdutoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto with an existing ID
        produto.setId(1L);
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setNumero(null);

        // Create the Produto, which fails.
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setNome(null);

        // Create the Produto, which fails.
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompostoIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setComposto(null);

        // Create the Produto, which fails.
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstoqueAtualIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setEstoqueAtual(null);

        // Create the Produto, which fails.
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProdutos() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].ean").value(hasItem(DEFAULT_EAN)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].composto").value(hasItem(DEFAULT_COMPOSTO.booleanValue())))
            .andExpect(jsonPath("$.[*].estoqueMinimo").value(hasItem(DEFAULT_ESTOQUE_MINIMO.doubleValue())))
            .andExpect(jsonPath("$.[*].estoqueMaximo").value(hasItem(DEFAULT_ESTOQUE_MAXIMO.doubleValue())))
            .andExpect(jsonPath("$.[*].estoqueAtual").value(hasItem(DEFAULT_ESTOQUE_ATUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].mostrarPDV").value(hasItem(DEFAULT_MOSTRAR_PDV.booleanValue())))
            .andExpect(jsonPath("$.[*].prazoMedioEntrega").value(hasItem(DEFAULT_PRAZO_MEDIO_ENTREGA)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProdutosWithEagerRelationshipsIsEnabled() throws Exception {
        ProdutoResource produtoResource = new ProdutoResource(produtoServiceMock, produtoQueryService);
        when(produtoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProdutoMockMvc.perform(get("/api/produtos?eagerload=true"))
        .andExpect(status().isOk());

        verify(produtoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProdutosWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProdutoResource produtoResource = new ProdutoResource(produtoServiceMock, produtoQueryService);
            when(produtoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProdutoMockMvc.perform(get("/api/produtos?eagerload=true"))
        .andExpect(status().isOk());

            verify(produtoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produto.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.ean").value(DEFAULT_EAN))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.composto").value(DEFAULT_COMPOSTO.booleanValue()))
            .andExpect(jsonPath("$.estoqueMinimo").value(DEFAULT_ESTOQUE_MINIMO.doubleValue()))
            .andExpect(jsonPath("$.estoqueMaximo").value(DEFAULT_ESTOQUE_MAXIMO.doubleValue()))
            .andExpect(jsonPath("$.estoqueAtual").value(DEFAULT_ESTOQUE_ATUAL.doubleValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.mostrarPDV").value(DEFAULT_MOSTRAR_PDV.booleanValue()))
            .andExpect(jsonPath("$.prazoMedioEntrega").value(DEFAULT_PRAZO_MEDIO_ENTREGA));
    }


    @Test
    @Transactional
    public void getProdutosByIdFiltering() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        Long id = produto.getId();

        defaultProdutoShouldBeFound("id.equals=" + id);
        defaultProdutoShouldNotBeFound("id.notEquals=" + id);

        defaultProdutoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProdutoShouldNotBeFound("id.greaterThan=" + id);

        defaultProdutoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProdutoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProdutosByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where tipo equals to DEFAULT_TIPO
        defaultProdutoShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the produtoList where tipo equals to UPDATED_TIPO
        defaultProdutoShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllProdutosByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where tipo not equals to DEFAULT_TIPO
        defaultProdutoShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the produtoList where tipo not equals to UPDATED_TIPO
        defaultProdutoShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllProdutosByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultProdutoShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the produtoList where tipo equals to UPDATED_TIPO
        defaultProdutoShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllProdutosByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where tipo is not null
        defaultProdutoShouldBeFound("tipo.specified=true");

        // Get all the produtoList where tipo is null
        defaultProdutoShouldNotBeFound("tipo.specified=false");
    }
                @Test
    @Transactional
    public void getAllProdutosByTipoContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where tipo contains DEFAULT_TIPO
        defaultProdutoShouldBeFound("tipo.contains=" + DEFAULT_TIPO);

        // Get all the produtoList where tipo contains UPDATED_TIPO
        defaultProdutoShouldNotBeFound("tipo.contains=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllProdutosByTipoNotContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where tipo does not contain DEFAULT_TIPO
        defaultProdutoShouldNotBeFound("tipo.doesNotContain=" + DEFAULT_TIPO);

        // Get all the produtoList where tipo does not contain UPDATED_TIPO
        defaultProdutoShouldBeFound("tipo.doesNotContain=" + UPDATED_TIPO);
    }


    @Test
    @Transactional
    public void getAllProdutosByEanIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where ean equals to DEFAULT_EAN
        defaultProdutoShouldBeFound("ean.equals=" + DEFAULT_EAN);

        // Get all the produtoList where ean equals to UPDATED_EAN
        defaultProdutoShouldNotBeFound("ean.equals=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllProdutosByEanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where ean not equals to DEFAULT_EAN
        defaultProdutoShouldNotBeFound("ean.notEquals=" + DEFAULT_EAN);

        // Get all the produtoList where ean not equals to UPDATED_EAN
        defaultProdutoShouldBeFound("ean.notEquals=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllProdutosByEanIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where ean in DEFAULT_EAN or UPDATED_EAN
        defaultProdutoShouldBeFound("ean.in=" + DEFAULT_EAN + "," + UPDATED_EAN);

        // Get all the produtoList where ean equals to UPDATED_EAN
        defaultProdutoShouldNotBeFound("ean.in=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllProdutosByEanIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where ean is not null
        defaultProdutoShouldBeFound("ean.specified=true");

        // Get all the produtoList where ean is null
        defaultProdutoShouldNotBeFound("ean.specified=false");
    }
                @Test
    @Transactional
    public void getAllProdutosByEanContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where ean contains DEFAULT_EAN
        defaultProdutoShouldBeFound("ean.contains=" + DEFAULT_EAN);

        // Get all the produtoList where ean contains UPDATED_EAN
        defaultProdutoShouldNotBeFound("ean.contains=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllProdutosByEanNotContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where ean does not contain DEFAULT_EAN
        defaultProdutoShouldNotBeFound("ean.doesNotContain=" + DEFAULT_EAN);

        // Get all the produtoList where ean does not contain UPDATED_EAN
        defaultProdutoShouldBeFound("ean.doesNotContain=" + UPDATED_EAN);
    }


    @Test
    @Transactional
    public void getAllProdutosByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where numero equals to DEFAULT_NUMERO
        defaultProdutoShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the produtoList where numero equals to UPDATED_NUMERO
        defaultProdutoShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllProdutosByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where numero not equals to DEFAULT_NUMERO
        defaultProdutoShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the produtoList where numero not equals to UPDATED_NUMERO
        defaultProdutoShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllProdutosByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultProdutoShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the produtoList where numero equals to UPDATED_NUMERO
        defaultProdutoShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllProdutosByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where numero is not null
        defaultProdutoShouldBeFound("numero.specified=true");

        // Get all the produtoList where numero is null
        defaultProdutoShouldNotBeFound("numero.specified=false");
    }
                @Test
    @Transactional
    public void getAllProdutosByNumeroContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where numero contains DEFAULT_NUMERO
        defaultProdutoShouldBeFound("numero.contains=" + DEFAULT_NUMERO);

        // Get all the produtoList where numero contains UPDATED_NUMERO
        defaultProdutoShouldNotBeFound("numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllProdutosByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where numero does not contain DEFAULT_NUMERO
        defaultProdutoShouldNotBeFound("numero.doesNotContain=" + DEFAULT_NUMERO);

        // Get all the produtoList where numero does not contain UPDATED_NUMERO
        defaultProdutoShouldBeFound("numero.doesNotContain=" + UPDATED_NUMERO);
    }


    @Test
    @Transactional
    public void getAllProdutosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome equals to DEFAULT_NOME
        defaultProdutoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the produtoList where nome equals to UPDATED_NOME
        defaultProdutoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProdutosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome not equals to DEFAULT_NOME
        defaultProdutoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the produtoList where nome not equals to UPDATED_NOME
        defaultProdutoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProdutosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultProdutoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the produtoList where nome equals to UPDATED_NOME
        defaultProdutoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProdutosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome is not null
        defaultProdutoShouldBeFound("nome.specified=true");

        // Get all the produtoList where nome is null
        defaultProdutoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllProdutosByNomeContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome contains DEFAULT_NOME
        defaultProdutoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the produtoList where nome contains UPDATED_NOME
        defaultProdutoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProdutosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where nome does not contain DEFAULT_NOME
        defaultProdutoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the produtoList where nome does not contain UPDATED_NOME
        defaultProdutoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllProdutosByCompostoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where composto equals to DEFAULT_COMPOSTO
        defaultProdutoShouldBeFound("composto.equals=" + DEFAULT_COMPOSTO);

        // Get all the produtoList where composto equals to UPDATED_COMPOSTO
        defaultProdutoShouldNotBeFound("composto.equals=" + UPDATED_COMPOSTO);
    }

    @Test
    @Transactional
    public void getAllProdutosByCompostoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where composto not equals to DEFAULT_COMPOSTO
        defaultProdutoShouldNotBeFound("composto.notEquals=" + DEFAULT_COMPOSTO);

        // Get all the produtoList where composto not equals to UPDATED_COMPOSTO
        defaultProdutoShouldBeFound("composto.notEquals=" + UPDATED_COMPOSTO);
    }

    @Test
    @Transactional
    public void getAllProdutosByCompostoIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where composto in DEFAULT_COMPOSTO or UPDATED_COMPOSTO
        defaultProdutoShouldBeFound("composto.in=" + DEFAULT_COMPOSTO + "," + UPDATED_COMPOSTO);

        // Get all the produtoList where composto equals to UPDATED_COMPOSTO
        defaultProdutoShouldNotBeFound("composto.in=" + UPDATED_COMPOSTO);
    }

    @Test
    @Transactional
    public void getAllProdutosByCompostoIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where composto is not null
        defaultProdutoShouldBeFound("composto.specified=true");

        // Get all the produtoList where composto is null
        defaultProdutoShouldNotBeFound("composto.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMinimoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMinimo equals to DEFAULT_ESTOQUE_MINIMO
        defaultProdutoShouldBeFound("estoqueMinimo.equals=" + DEFAULT_ESTOQUE_MINIMO);

        // Get all the produtoList where estoqueMinimo equals to UPDATED_ESTOQUE_MINIMO
        defaultProdutoShouldNotBeFound("estoqueMinimo.equals=" + UPDATED_ESTOQUE_MINIMO);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMinimoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMinimo not equals to DEFAULT_ESTOQUE_MINIMO
        defaultProdutoShouldNotBeFound("estoqueMinimo.notEquals=" + DEFAULT_ESTOQUE_MINIMO);

        // Get all the produtoList where estoqueMinimo not equals to UPDATED_ESTOQUE_MINIMO
        defaultProdutoShouldBeFound("estoqueMinimo.notEquals=" + UPDATED_ESTOQUE_MINIMO);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMinimoIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMinimo in DEFAULT_ESTOQUE_MINIMO or UPDATED_ESTOQUE_MINIMO
        defaultProdutoShouldBeFound("estoqueMinimo.in=" + DEFAULT_ESTOQUE_MINIMO + "," + UPDATED_ESTOQUE_MINIMO);

        // Get all the produtoList where estoqueMinimo equals to UPDATED_ESTOQUE_MINIMO
        defaultProdutoShouldNotBeFound("estoqueMinimo.in=" + UPDATED_ESTOQUE_MINIMO);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMinimoIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMinimo is not null
        defaultProdutoShouldBeFound("estoqueMinimo.specified=true");

        // Get all the produtoList where estoqueMinimo is null
        defaultProdutoShouldNotBeFound("estoqueMinimo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMinimoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMinimo is greater than or equal to DEFAULT_ESTOQUE_MINIMO
        defaultProdutoShouldBeFound("estoqueMinimo.greaterThanOrEqual=" + DEFAULT_ESTOQUE_MINIMO);

        // Get all the produtoList where estoqueMinimo is greater than or equal to UPDATED_ESTOQUE_MINIMO
        defaultProdutoShouldNotBeFound("estoqueMinimo.greaterThanOrEqual=" + UPDATED_ESTOQUE_MINIMO);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMinimoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMinimo is less than or equal to DEFAULT_ESTOQUE_MINIMO
        defaultProdutoShouldBeFound("estoqueMinimo.lessThanOrEqual=" + DEFAULT_ESTOQUE_MINIMO);

        // Get all the produtoList where estoqueMinimo is less than or equal to SMALLER_ESTOQUE_MINIMO
        defaultProdutoShouldNotBeFound("estoqueMinimo.lessThanOrEqual=" + SMALLER_ESTOQUE_MINIMO);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMinimoIsLessThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMinimo is less than DEFAULT_ESTOQUE_MINIMO
        defaultProdutoShouldNotBeFound("estoqueMinimo.lessThan=" + DEFAULT_ESTOQUE_MINIMO);

        // Get all the produtoList where estoqueMinimo is less than UPDATED_ESTOQUE_MINIMO
        defaultProdutoShouldBeFound("estoqueMinimo.lessThan=" + UPDATED_ESTOQUE_MINIMO);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMinimoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMinimo is greater than DEFAULT_ESTOQUE_MINIMO
        defaultProdutoShouldNotBeFound("estoqueMinimo.greaterThan=" + DEFAULT_ESTOQUE_MINIMO);

        // Get all the produtoList where estoqueMinimo is greater than SMALLER_ESTOQUE_MINIMO
        defaultProdutoShouldBeFound("estoqueMinimo.greaterThan=" + SMALLER_ESTOQUE_MINIMO);
    }


    @Test
    @Transactional
    public void getAllProdutosByEstoqueMaximoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMaximo equals to DEFAULT_ESTOQUE_MAXIMO
        defaultProdutoShouldBeFound("estoqueMaximo.equals=" + DEFAULT_ESTOQUE_MAXIMO);

        // Get all the produtoList where estoqueMaximo equals to UPDATED_ESTOQUE_MAXIMO
        defaultProdutoShouldNotBeFound("estoqueMaximo.equals=" + UPDATED_ESTOQUE_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMaximoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMaximo not equals to DEFAULT_ESTOQUE_MAXIMO
        defaultProdutoShouldNotBeFound("estoqueMaximo.notEquals=" + DEFAULT_ESTOQUE_MAXIMO);

        // Get all the produtoList where estoqueMaximo not equals to UPDATED_ESTOQUE_MAXIMO
        defaultProdutoShouldBeFound("estoqueMaximo.notEquals=" + UPDATED_ESTOQUE_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMaximoIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMaximo in DEFAULT_ESTOQUE_MAXIMO or UPDATED_ESTOQUE_MAXIMO
        defaultProdutoShouldBeFound("estoqueMaximo.in=" + DEFAULT_ESTOQUE_MAXIMO + "," + UPDATED_ESTOQUE_MAXIMO);

        // Get all the produtoList where estoqueMaximo equals to UPDATED_ESTOQUE_MAXIMO
        defaultProdutoShouldNotBeFound("estoqueMaximo.in=" + UPDATED_ESTOQUE_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMaximoIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMaximo is not null
        defaultProdutoShouldBeFound("estoqueMaximo.specified=true");

        // Get all the produtoList where estoqueMaximo is null
        defaultProdutoShouldNotBeFound("estoqueMaximo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMaximoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMaximo is greater than or equal to DEFAULT_ESTOQUE_MAXIMO
        defaultProdutoShouldBeFound("estoqueMaximo.greaterThanOrEqual=" + DEFAULT_ESTOQUE_MAXIMO);

        // Get all the produtoList where estoqueMaximo is greater than or equal to UPDATED_ESTOQUE_MAXIMO
        defaultProdutoShouldNotBeFound("estoqueMaximo.greaterThanOrEqual=" + UPDATED_ESTOQUE_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMaximoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMaximo is less than or equal to DEFAULT_ESTOQUE_MAXIMO
        defaultProdutoShouldBeFound("estoqueMaximo.lessThanOrEqual=" + DEFAULT_ESTOQUE_MAXIMO);

        // Get all the produtoList where estoqueMaximo is less than or equal to SMALLER_ESTOQUE_MAXIMO
        defaultProdutoShouldNotBeFound("estoqueMaximo.lessThanOrEqual=" + SMALLER_ESTOQUE_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMaximoIsLessThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMaximo is less than DEFAULT_ESTOQUE_MAXIMO
        defaultProdutoShouldNotBeFound("estoqueMaximo.lessThan=" + DEFAULT_ESTOQUE_MAXIMO);

        // Get all the produtoList where estoqueMaximo is less than UPDATED_ESTOQUE_MAXIMO
        defaultProdutoShouldBeFound("estoqueMaximo.lessThan=" + UPDATED_ESTOQUE_MAXIMO);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueMaximoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueMaximo is greater than DEFAULT_ESTOQUE_MAXIMO
        defaultProdutoShouldNotBeFound("estoqueMaximo.greaterThan=" + DEFAULT_ESTOQUE_MAXIMO);

        // Get all the produtoList where estoqueMaximo is greater than SMALLER_ESTOQUE_MAXIMO
        defaultProdutoShouldBeFound("estoqueMaximo.greaterThan=" + SMALLER_ESTOQUE_MAXIMO);
    }


    @Test
    @Transactional
    public void getAllProdutosByEstoqueAtualIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueAtual equals to DEFAULT_ESTOQUE_ATUAL
        defaultProdutoShouldBeFound("estoqueAtual.equals=" + DEFAULT_ESTOQUE_ATUAL);

        // Get all the produtoList where estoqueAtual equals to UPDATED_ESTOQUE_ATUAL
        defaultProdutoShouldNotBeFound("estoqueAtual.equals=" + UPDATED_ESTOQUE_ATUAL);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueAtualIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueAtual not equals to DEFAULT_ESTOQUE_ATUAL
        defaultProdutoShouldNotBeFound("estoqueAtual.notEquals=" + DEFAULT_ESTOQUE_ATUAL);

        // Get all the produtoList where estoqueAtual not equals to UPDATED_ESTOQUE_ATUAL
        defaultProdutoShouldBeFound("estoqueAtual.notEquals=" + UPDATED_ESTOQUE_ATUAL);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueAtualIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueAtual in DEFAULT_ESTOQUE_ATUAL or UPDATED_ESTOQUE_ATUAL
        defaultProdutoShouldBeFound("estoqueAtual.in=" + DEFAULT_ESTOQUE_ATUAL + "," + UPDATED_ESTOQUE_ATUAL);

        // Get all the produtoList where estoqueAtual equals to UPDATED_ESTOQUE_ATUAL
        defaultProdutoShouldNotBeFound("estoqueAtual.in=" + UPDATED_ESTOQUE_ATUAL);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueAtualIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueAtual is not null
        defaultProdutoShouldBeFound("estoqueAtual.specified=true");

        // Get all the produtoList where estoqueAtual is null
        defaultProdutoShouldNotBeFound("estoqueAtual.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueAtualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueAtual is greater than or equal to DEFAULT_ESTOQUE_ATUAL
        defaultProdutoShouldBeFound("estoqueAtual.greaterThanOrEqual=" + DEFAULT_ESTOQUE_ATUAL);

        // Get all the produtoList where estoqueAtual is greater than or equal to UPDATED_ESTOQUE_ATUAL
        defaultProdutoShouldNotBeFound("estoqueAtual.greaterThanOrEqual=" + UPDATED_ESTOQUE_ATUAL);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueAtualIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueAtual is less than or equal to DEFAULT_ESTOQUE_ATUAL
        defaultProdutoShouldBeFound("estoqueAtual.lessThanOrEqual=" + DEFAULT_ESTOQUE_ATUAL);

        // Get all the produtoList where estoqueAtual is less than or equal to SMALLER_ESTOQUE_ATUAL
        defaultProdutoShouldNotBeFound("estoqueAtual.lessThanOrEqual=" + SMALLER_ESTOQUE_ATUAL);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueAtualIsLessThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueAtual is less than DEFAULT_ESTOQUE_ATUAL
        defaultProdutoShouldNotBeFound("estoqueAtual.lessThan=" + DEFAULT_ESTOQUE_ATUAL);

        // Get all the produtoList where estoqueAtual is less than UPDATED_ESTOQUE_ATUAL
        defaultProdutoShouldBeFound("estoqueAtual.lessThan=" + UPDATED_ESTOQUE_ATUAL);
    }

    @Test
    @Transactional
    public void getAllProdutosByEstoqueAtualIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where estoqueAtual is greater than DEFAULT_ESTOQUE_ATUAL
        defaultProdutoShouldNotBeFound("estoqueAtual.greaterThan=" + DEFAULT_ESTOQUE_ATUAL);

        // Get all the produtoList where estoqueAtual is greater than SMALLER_ESTOQUE_ATUAL
        defaultProdutoShouldBeFound("estoqueAtual.greaterThan=" + SMALLER_ESTOQUE_ATUAL);
    }


    @Test
    @Transactional
    public void getAllProdutosByMostrarPDVIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where mostrarPDV equals to DEFAULT_MOSTRAR_PDV
        defaultProdutoShouldBeFound("mostrarPDV.equals=" + DEFAULT_MOSTRAR_PDV);

        // Get all the produtoList where mostrarPDV equals to UPDATED_MOSTRAR_PDV
        defaultProdutoShouldNotBeFound("mostrarPDV.equals=" + UPDATED_MOSTRAR_PDV);
    }

    @Test
    @Transactional
    public void getAllProdutosByMostrarPDVIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where mostrarPDV not equals to DEFAULT_MOSTRAR_PDV
        defaultProdutoShouldNotBeFound("mostrarPDV.notEquals=" + DEFAULT_MOSTRAR_PDV);

        // Get all the produtoList where mostrarPDV not equals to UPDATED_MOSTRAR_PDV
        defaultProdutoShouldBeFound("mostrarPDV.notEquals=" + UPDATED_MOSTRAR_PDV);
    }

    @Test
    @Transactional
    public void getAllProdutosByMostrarPDVIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where mostrarPDV in DEFAULT_MOSTRAR_PDV or UPDATED_MOSTRAR_PDV
        defaultProdutoShouldBeFound("mostrarPDV.in=" + DEFAULT_MOSTRAR_PDV + "," + UPDATED_MOSTRAR_PDV);

        // Get all the produtoList where mostrarPDV equals to UPDATED_MOSTRAR_PDV
        defaultProdutoShouldNotBeFound("mostrarPDV.in=" + UPDATED_MOSTRAR_PDV);
    }

    @Test
    @Transactional
    public void getAllProdutosByMostrarPDVIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where mostrarPDV is not null
        defaultProdutoShouldBeFound("mostrarPDV.specified=true");

        // Get all the produtoList where mostrarPDV is null
        defaultProdutoShouldNotBeFound("mostrarPDV.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutosByPrazoMedioEntregaIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where prazoMedioEntrega equals to DEFAULT_PRAZO_MEDIO_ENTREGA
        defaultProdutoShouldBeFound("prazoMedioEntrega.equals=" + DEFAULT_PRAZO_MEDIO_ENTREGA);

        // Get all the produtoList where prazoMedioEntrega equals to UPDATED_PRAZO_MEDIO_ENTREGA
        defaultProdutoShouldNotBeFound("prazoMedioEntrega.equals=" + UPDATED_PRAZO_MEDIO_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllProdutosByPrazoMedioEntregaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where prazoMedioEntrega not equals to DEFAULT_PRAZO_MEDIO_ENTREGA
        defaultProdutoShouldNotBeFound("prazoMedioEntrega.notEquals=" + DEFAULT_PRAZO_MEDIO_ENTREGA);

        // Get all the produtoList where prazoMedioEntrega not equals to UPDATED_PRAZO_MEDIO_ENTREGA
        defaultProdutoShouldBeFound("prazoMedioEntrega.notEquals=" + UPDATED_PRAZO_MEDIO_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllProdutosByPrazoMedioEntregaIsInShouldWork() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where prazoMedioEntrega in DEFAULT_PRAZO_MEDIO_ENTREGA or UPDATED_PRAZO_MEDIO_ENTREGA
        defaultProdutoShouldBeFound("prazoMedioEntrega.in=" + DEFAULT_PRAZO_MEDIO_ENTREGA + "," + UPDATED_PRAZO_MEDIO_ENTREGA);

        // Get all the produtoList where prazoMedioEntrega equals to UPDATED_PRAZO_MEDIO_ENTREGA
        defaultProdutoShouldNotBeFound("prazoMedioEntrega.in=" + UPDATED_PRAZO_MEDIO_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllProdutosByPrazoMedioEntregaIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where prazoMedioEntrega is not null
        defaultProdutoShouldBeFound("prazoMedioEntrega.specified=true");

        // Get all the produtoList where prazoMedioEntrega is null
        defaultProdutoShouldNotBeFound("prazoMedioEntrega.specified=false");
    }
                @Test
    @Transactional
    public void getAllProdutosByPrazoMedioEntregaContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where prazoMedioEntrega contains DEFAULT_PRAZO_MEDIO_ENTREGA
        defaultProdutoShouldBeFound("prazoMedioEntrega.contains=" + DEFAULT_PRAZO_MEDIO_ENTREGA);

        // Get all the produtoList where prazoMedioEntrega contains UPDATED_PRAZO_MEDIO_ENTREGA
        defaultProdutoShouldNotBeFound("prazoMedioEntrega.contains=" + UPDATED_PRAZO_MEDIO_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllProdutosByPrazoMedioEntregaNotContainsSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList where prazoMedioEntrega does not contain DEFAULT_PRAZO_MEDIO_ENTREGA
        defaultProdutoShouldNotBeFound("prazoMedioEntrega.doesNotContain=" + DEFAULT_PRAZO_MEDIO_ENTREGA);

        // Get all the produtoList where prazoMedioEntrega does not contain UPDATED_PRAZO_MEDIO_ENTREGA
        defaultProdutoShouldBeFound("prazoMedioEntrega.doesNotContain=" + UPDATED_PRAZO_MEDIO_ENTREGA);
    }


    @Test
    @Transactional
    public void getAllProdutosByCompostoProdutoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        CompostoProduto compostoProduto = CompostoProdutoResourceIT.createEntity(em);
        em.persist(compostoProduto);
        em.flush();
        produto.addCompostoProduto(compostoProduto);
        produtoRepository.saveAndFlush(produto);
        Long compostoProdutoId = compostoProduto.getId();

        // Get all the produtoList where compostoProduto equals to compostoProdutoId
        defaultProdutoShouldBeFound("compostoProdutoId.equals=" + compostoProdutoId);

        // Get all the produtoList where compostoProduto equals to compostoProdutoId + 1
        defaultProdutoShouldNotBeFound("compostoProdutoId.equals=" + (compostoProdutoId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutosByConversaoUnidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        ConversaoUnidade conversaoUnidade = ConversaoUnidadeResourceIT.createEntity(em);
        em.persist(conversaoUnidade);
        em.flush();
        produto.addConversaoUnidade(conversaoUnidade);
        produtoRepository.saveAndFlush(produto);
        Long conversaoUnidadeId = conversaoUnidade.getId();

        // Get all the produtoList where conversaoUnidade equals to conversaoUnidadeId
        defaultProdutoShouldBeFound("conversaoUnidadeId.equals=" + conversaoUnidadeId);

        // Get all the produtoList where conversaoUnidade equals to conversaoUnidadeId + 1
        defaultProdutoShouldNotBeFound("conversaoUnidadeId.equals=" + (conversaoUnidadeId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutosByEstruturaCalculoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        EstruturaCalculo estruturaCalculo = EstruturaCalculoResourceIT.createEntity(em);
        em.persist(estruturaCalculo);
        em.flush();
        produto.addEstruturaCalculo(estruturaCalculo);
        produtoRepository.saveAndFlush(produto);
        Long estruturaCalculoId = estruturaCalculo.getId();

        // Get all the produtoList where estruturaCalculo equals to estruturaCalculoId
        defaultProdutoShouldBeFound("estruturaCalculoId.equals=" + estruturaCalculoId);

        // Get all the produtoList where estruturaCalculo equals to estruturaCalculoId + 1
        defaultProdutoShouldNotBeFound("estruturaCalculoId.equals=" + (estruturaCalculoId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutosByItemCompraIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        ItemCompra itemCompra = ItemCompraResourceIT.createEntity(em);
        em.persist(itemCompra);
        em.flush();
        produto.addItemCompra(itemCompra);
        produtoRepository.saveAndFlush(produto);
        Long itemCompraId = itemCompra.getId();

        // Get all the produtoList where itemCompra equals to itemCompraId
        defaultProdutoShouldBeFound("itemCompraId.equals=" + itemCompraId);

        // Get all the produtoList where itemCompra equals to itemCompraId + 1
        defaultProdutoShouldNotBeFound("itemCompraId.equals=" + (itemCompraId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutosByItemVendaIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        ItemVenda itemVenda = ItemVendaResourceIT.createEntity(em);
        em.persist(itemVenda);
        em.flush();
        produto.addItemVenda(itemVenda);
        produtoRepository.saveAndFlush(produto);
        Long itemVendaId = itemVenda.getId();

        // Get all the produtoList where itemVenda equals to itemVendaId
        defaultProdutoShouldBeFound("itemVendaId.equals=" + itemVendaId);

        // Get all the produtoList where itemVenda equals to itemVendaId + 1
        defaultProdutoShouldNotBeFound("itemVendaId.equals=" + (itemVendaId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutosByUtilizadorIsEqualToSomething() throws Exception {
        // Get already existing entity
        User utilizador = produto.getUtilizador();
        produtoRepository.saveAndFlush(produto);
        Long utilizadorId = utilizador.getId();

        // Get all the produtoList where utilizador equals to utilizadorId
        defaultProdutoShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the produtoList where utilizador equals to utilizadorId + 1
        defaultProdutoShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutosByImpostoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        Imposto imposto = ImpostoResourceIT.createEntity(em);
        em.persist(imposto);
        em.flush();
        produto.addImposto(imposto);
        produtoRepository.saveAndFlush(produto);
        Long impostoId = imposto.getId();

        // Get all the produtoList where imposto equals to impostoId
        defaultProdutoShouldBeFound("impostoId.equals=" + impostoId);

        // Get all the produtoList where imposto equals to impostoId + 1
        defaultProdutoShouldNotBeFound("impostoId.equals=" + (impostoId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutosByFornecedorIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        Fornecedor fornecedor = FornecedorResourceIT.createEntity(em);
        em.persist(fornecedor);
        em.flush();
        produto.addFornecedor(fornecedor);
        produtoRepository.saveAndFlush(produto);
        Long fornecedorId = fornecedor.getId();

        // Get all the produtoList where fornecedor equals to fornecedorId
        defaultProdutoShouldBeFound("fornecedorId.equals=" + fornecedorId);

        // Get all the produtoList where fornecedor equals to fornecedorId + 1
        defaultProdutoShouldNotBeFound("fornecedorId.equals=" + (fornecedorId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutosByLocalArmazenamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        LocalArmazenamento localArmazenamento = LocalArmazenamentoResourceIT.createEntity(em);
        em.persist(localArmazenamento);
        em.flush();
        produto.setLocalArmazenamento(localArmazenamento);
        produtoRepository.saveAndFlush(produto);
        Long localArmazenamentoId = localArmazenamento.getId();

        // Get all the produtoList where localArmazenamento equals to localArmazenamentoId
        defaultProdutoShouldBeFound("localArmazenamentoId.equals=" + localArmazenamentoId);

        // Get all the produtoList where localArmazenamento equals to localArmazenamentoId + 1
        defaultProdutoShouldNotBeFound("localArmazenamentoId.equals=" + (localArmazenamentoId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutosByFamiliaIsEqualToSomething() throws Exception {
        // Get already existing entity
        FamiliaProduto familia = produto.getFamilia();
        produtoRepository.saveAndFlush(produto);
        Long familiaId = familia.getId();

        // Get all the produtoList where familia equals to familiaId
        defaultProdutoShouldBeFound("familiaId.equals=" + familiaId);

        // Get all the produtoList where familia equals to familiaId + 1
        defaultProdutoShouldNotBeFound("familiaId.equals=" + (familiaId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutosByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        produto.setEmpresa(empresa);
        produtoRepository.saveAndFlush(produto);
        Long empresaId = empresa.getId();

        // Get all the produtoList where empresa equals to empresaId
        defaultProdutoShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the produtoList where empresa equals to empresaId + 1
        defaultProdutoShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }


    @Test
    @Transactional
    public void getAllProdutosByEstadoIsEqualToSomething() throws Exception {
        // Get already existing entity
        FluxoDocumento estado = produto.getEstado();
        produtoRepository.saveAndFlush(produto);
        Long estadoId = estado.getId();

        // Get all the produtoList where estado equals to estadoId
        defaultProdutoShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the produtoList where estado equals to estadoId + 1
        defaultProdutoShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProdutoShouldBeFound(String filter) throws Exception {
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].ean").value(hasItem(DEFAULT_EAN)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].composto").value(hasItem(DEFAULT_COMPOSTO.booleanValue())))
            .andExpect(jsonPath("$.[*].estoqueMinimo").value(hasItem(DEFAULT_ESTOQUE_MINIMO.doubleValue())))
            .andExpect(jsonPath("$.[*].estoqueMaximo").value(hasItem(DEFAULT_ESTOQUE_MAXIMO.doubleValue())))
            .andExpect(jsonPath("$.[*].estoqueAtual").value(hasItem(DEFAULT_ESTOQUE_ATUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].mostrarPDV").value(hasItem(DEFAULT_MOSTRAR_PDV.booleanValue())))
            .andExpect(jsonPath("$.[*].prazoMedioEntrega").value(hasItem(DEFAULT_PRAZO_MEDIO_ENTREGA)));

        // Check, that the count call also returns 1
        restProdutoMockMvc.perform(get("/api/produtos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProdutoShouldNotBeFound(String filter) throws Exception {
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProdutoMockMvc.perform(get("/api/produtos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProduto() throws Exception {
        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto
        Produto updatedProduto = produtoRepository.findById(produto.getId()).get();
        // Disconnect from session so that the updates on updatedProduto are not directly saved in db
        em.detach(updatedProduto);
        updatedProduto
            .tipo(UPDATED_TIPO)
            .ean(UPDATED_EAN)
            .numero(UPDATED_NUMERO)
            .nome(UPDATED_NOME)
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .composto(UPDATED_COMPOSTO)
            .estoqueMinimo(UPDATED_ESTOQUE_MINIMO)
            .estoqueMaximo(UPDATED_ESTOQUE_MAXIMO)
            .estoqueAtual(UPDATED_ESTOQUE_ATUAL)
            .descricao(UPDATED_DESCRICAO)
            .mostrarPDV(UPDATED_MOSTRAR_PDV)
            .prazoMedioEntrega(UPDATED_PRAZO_MEDIO_ENTREGA);
        ProdutoDTO produtoDTO = produtoMapper.toDto(updatedProduto);

        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testProduto.getEan()).isEqualTo(UPDATED_EAN);
        assertThat(testProduto.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProduto.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testProduto.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testProduto.isComposto()).isEqualTo(UPDATED_COMPOSTO);
        assertThat(testProduto.getEstoqueMinimo()).isEqualTo(UPDATED_ESTOQUE_MINIMO);
        assertThat(testProduto.getEstoqueMaximo()).isEqualTo(UPDATED_ESTOQUE_MAXIMO);
        assertThat(testProduto.getEstoqueAtual()).isEqualTo(UPDATED_ESTOQUE_ATUAL);
        assertThat(testProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProduto.isMostrarPDV()).isEqualTo(UPDATED_MOSTRAR_PDV);
        assertThat(testProduto.getPrazoMedioEntrega()).isEqualTo(UPDATED_PRAZO_MEDIO_ENTREGA);
    }

    @Test
    @Transactional
    public void updateNonExistingProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Create the Produto
        ProdutoDTO produtoDTO = produtoMapper.toDto(produto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeDelete = produtoRepository.findAll().size();

        // Delete the produto
        restProdutoMockMvc.perform(delete("/api/produtos/{id}", produto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
