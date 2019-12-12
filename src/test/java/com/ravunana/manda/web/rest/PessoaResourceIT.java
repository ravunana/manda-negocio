package com.ravunana.manda.web.rest;

import com.ravunana.manda.MandaApp;
import com.ravunana.manda.domain.Pessoa;
import com.ravunana.manda.domain.MoradaPessoa;
import com.ravunana.manda.domain.ContactoPessoa;
import com.ravunana.manda.domain.User;
import com.ravunana.manda.repository.PessoaRepository;
import com.ravunana.manda.service.PessoaService;
import com.ravunana.manda.service.dto.PessoaDTO;
import com.ravunana.manda.service.mapper.PessoaMapper;
import com.ravunana.manda.web.rest.errors.ExceptionTranslator;
import com.ravunana.manda.service.dto.PessoaCriteria;
import com.ravunana.manda.service.PessoaQueryService;

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
 * Integration tests for the {@link PessoaResource} REST controller.
 */
@SpringBootTest(classes = MandaApp.class)
public class PessoaResourceIT {

    private static final String DEFAULT_TIPO_PESSOA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PESSOA = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NIF = "AAAAAAAAAA";
    private static final String UPDATED_NIF = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaMapper pessoaMapper;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaQueryService pessoaQueryService;

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

    private MockMvc restPessoaMockMvc;

    private Pessoa pessoa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PessoaResource pessoaResource = new PessoaResource(pessoaService, pessoaQueryService);
        this.restPessoaMockMvc = MockMvcBuilders.standaloneSetup(pessoaResource)
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
    public static Pessoa createEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .tipoPessoa(DEFAULT_TIPO_PESSOA)
            .nome(DEFAULT_NOME)
            .nif(DEFAULT_NIF)
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE);
        return pessoa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoa createUpdatedEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .tipoPessoa(UPDATED_TIPO_PESSOA)
            .nome(UPDATED_NOME)
            .nif(UPDATED_NIF)
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE);
        return pessoa;
    }

    @BeforeEach
    public void initTest() {
        pessoa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPessoa() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);
        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate + 1);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getTipoPessoa()).isEqualTo(DEFAULT_TIPO_PESSOA);
        assertThat(testPessoa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPessoa.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testPessoa.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testPessoa.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPessoaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // Create the Pessoa with an existing ID
        pessoa.setId(1L);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoPessoaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setTipoPessoa(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setNome(null);

        // Create the Pessoa, which fails.
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPessoas() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList
        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoPessoa").value(hasItem(DEFAULT_TIPO_PESSOA)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))));
    }
    
    @Test
    @Transactional
    public void getPessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", pessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pessoa.getId().intValue()))
            .andExpect(jsonPath("$.tipoPessoa").value(DEFAULT_TIPO_PESSOA))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)));
    }


    @Test
    @Transactional
    public void getPessoasByIdFiltering() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        Long id = pessoa.getId();

        defaultPessoaShouldBeFound("id.equals=" + id);
        defaultPessoaShouldNotBeFound("id.notEquals=" + id);

        defaultPessoaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPessoaShouldNotBeFound("id.greaterThan=" + id);

        defaultPessoaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPessoaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPessoasByTipoPessoaIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tipoPessoa equals to DEFAULT_TIPO_PESSOA
        defaultPessoaShouldBeFound("tipoPessoa.equals=" + DEFAULT_TIPO_PESSOA);

        // Get all the pessoaList where tipoPessoa equals to UPDATED_TIPO_PESSOA
        defaultPessoaShouldNotBeFound("tipoPessoa.equals=" + UPDATED_TIPO_PESSOA);
    }

    @Test
    @Transactional
    public void getAllPessoasByTipoPessoaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tipoPessoa not equals to DEFAULT_TIPO_PESSOA
        defaultPessoaShouldNotBeFound("tipoPessoa.notEquals=" + DEFAULT_TIPO_PESSOA);

        // Get all the pessoaList where tipoPessoa not equals to UPDATED_TIPO_PESSOA
        defaultPessoaShouldBeFound("tipoPessoa.notEquals=" + UPDATED_TIPO_PESSOA);
    }

    @Test
    @Transactional
    public void getAllPessoasByTipoPessoaIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tipoPessoa in DEFAULT_TIPO_PESSOA or UPDATED_TIPO_PESSOA
        defaultPessoaShouldBeFound("tipoPessoa.in=" + DEFAULT_TIPO_PESSOA + "," + UPDATED_TIPO_PESSOA);

        // Get all the pessoaList where tipoPessoa equals to UPDATED_TIPO_PESSOA
        defaultPessoaShouldNotBeFound("tipoPessoa.in=" + UPDATED_TIPO_PESSOA);
    }

    @Test
    @Transactional
    public void getAllPessoasByTipoPessoaIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tipoPessoa is not null
        defaultPessoaShouldBeFound("tipoPessoa.specified=true");

        // Get all the pessoaList where tipoPessoa is null
        defaultPessoaShouldNotBeFound("tipoPessoa.specified=false");
    }
                @Test
    @Transactional
    public void getAllPessoasByTipoPessoaContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tipoPessoa contains DEFAULT_TIPO_PESSOA
        defaultPessoaShouldBeFound("tipoPessoa.contains=" + DEFAULT_TIPO_PESSOA);

        // Get all the pessoaList where tipoPessoa contains UPDATED_TIPO_PESSOA
        defaultPessoaShouldNotBeFound("tipoPessoa.contains=" + UPDATED_TIPO_PESSOA);
    }

    @Test
    @Transactional
    public void getAllPessoasByTipoPessoaNotContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tipoPessoa does not contain DEFAULT_TIPO_PESSOA
        defaultPessoaShouldNotBeFound("tipoPessoa.doesNotContain=" + DEFAULT_TIPO_PESSOA);

        // Get all the pessoaList where tipoPessoa does not contain UPDATED_TIPO_PESSOA
        defaultPessoaShouldBeFound("tipoPessoa.doesNotContain=" + UPDATED_TIPO_PESSOA);
    }


    @Test
    @Transactional
    public void getAllPessoasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome equals to DEFAULT_NOME
        defaultPessoaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the pessoaList where nome equals to UPDATED_NOME
        defaultPessoaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPessoasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome not equals to DEFAULT_NOME
        defaultPessoaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the pessoaList where nome not equals to UPDATED_NOME
        defaultPessoaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPessoasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultPessoaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the pessoaList where nome equals to UPDATED_NOME
        defaultPessoaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPessoasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome is not null
        defaultPessoaShouldBeFound("nome.specified=true");

        // Get all the pessoaList where nome is null
        defaultPessoaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllPessoasByNomeContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome contains DEFAULT_NOME
        defaultPessoaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the pessoaList where nome contains UPDATED_NOME
        defaultPessoaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPessoasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome does not contain DEFAULT_NOME
        defaultPessoaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the pessoaList where nome does not contain UPDATED_NOME
        defaultPessoaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllPessoasByNifIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nif equals to DEFAULT_NIF
        defaultPessoaShouldBeFound("nif.equals=" + DEFAULT_NIF);

        // Get all the pessoaList where nif equals to UPDATED_NIF
        defaultPessoaShouldNotBeFound("nif.equals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    public void getAllPessoasByNifIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nif not equals to DEFAULT_NIF
        defaultPessoaShouldNotBeFound("nif.notEquals=" + DEFAULT_NIF);

        // Get all the pessoaList where nif not equals to UPDATED_NIF
        defaultPessoaShouldBeFound("nif.notEquals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    public void getAllPessoasByNifIsInShouldWork() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nif in DEFAULT_NIF or UPDATED_NIF
        defaultPessoaShouldBeFound("nif.in=" + DEFAULT_NIF + "," + UPDATED_NIF);

        // Get all the pessoaList where nif equals to UPDATED_NIF
        defaultPessoaShouldNotBeFound("nif.in=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    public void getAllPessoasByNifIsNullOrNotNull() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nif is not null
        defaultPessoaShouldBeFound("nif.specified=true");

        // Get all the pessoaList where nif is null
        defaultPessoaShouldNotBeFound("nif.specified=false");
    }
                @Test
    @Transactional
    public void getAllPessoasByNifContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nif contains DEFAULT_NIF
        defaultPessoaShouldBeFound("nif.contains=" + DEFAULT_NIF);

        // Get all the pessoaList where nif contains UPDATED_NIF
        defaultPessoaShouldNotBeFound("nif.contains=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    public void getAllPessoasByNifNotContainsSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nif does not contain DEFAULT_NIF
        defaultPessoaShouldNotBeFound("nif.doesNotContain=" + DEFAULT_NIF);

        // Get all the pessoaList where nif does not contain UPDATED_NIF
        defaultPessoaShouldBeFound("nif.doesNotContain=" + UPDATED_NIF);
    }


    @Test
    @Transactional
    public void getAllPessoasByMoradaPessoaIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);
        MoradaPessoa moradaPessoa = MoradaPessoaResourceIT.createEntity(em);
        em.persist(moradaPessoa);
        em.flush();
        pessoa.addMoradaPessoa(moradaPessoa);
        pessoaRepository.saveAndFlush(pessoa);
        Long moradaPessoaId = moradaPessoa.getId();

        // Get all the pessoaList where moradaPessoa equals to moradaPessoaId
        defaultPessoaShouldBeFound("moradaPessoaId.equals=" + moradaPessoaId);

        // Get all the pessoaList where moradaPessoa equals to moradaPessoaId + 1
        defaultPessoaShouldNotBeFound("moradaPessoaId.equals=" + (moradaPessoaId + 1));
    }


    @Test
    @Transactional
    public void getAllPessoasByContactoPessoaIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);
        ContactoPessoa contactoPessoa = ContactoPessoaResourceIT.createEntity(em);
        em.persist(contactoPessoa);
        em.flush();
        pessoa.addContactoPessoa(contactoPessoa);
        pessoaRepository.saveAndFlush(pessoa);
        Long contactoPessoaId = contactoPessoa.getId();

        // Get all the pessoaList where contactoPessoa equals to contactoPessoaId
        defaultPessoaShouldBeFound("contactoPessoaId.equals=" + contactoPessoaId);

        // Get all the pessoaList where contactoPessoa equals to contactoPessoaId + 1
        defaultPessoaShouldNotBeFound("contactoPessoaId.equals=" + (contactoPessoaId + 1));
    }


    @Test
    @Transactional
    public void getAllPessoasByUtilizadorIsEqualToSomething() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);
        User utilizador = UserResourceIT.createEntity(em);
        em.persist(utilizador);
        em.flush();
        pessoa.setUtilizador(utilizador);
        pessoaRepository.saveAndFlush(pessoa);
        Long utilizadorId = utilizador.getId();

        // Get all the pessoaList where utilizador equals to utilizadorId
        defaultPessoaShouldBeFound("utilizadorId.equals=" + utilizadorId);

        // Get all the pessoaList where utilizador equals to utilizadorId + 1
        defaultPessoaShouldNotBeFound("utilizadorId.equals=" + (utilizadorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPessoaShouldBeFound(String filter) throws Exception {
        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoPessoa").value(hasItem(DEFAULT_TIPO_PESSOA)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))));

        // Check, that the count call also returns 1
        restPessoaMockMvc.perform(get("/api/pessoas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPessoaShouldNotBeFound(String filter) throws Exception {
        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPessoaMockMvc.perform(get("/api/pessoas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPessoa() throws Exception {
        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa
        Pessoa updatedPessoa = pessoaRepository.findById(pessoa.getId()).get();
        // Disconnect from session so that the updates on updatedPessoa are not directly saved in db
        em.detach(updatedPessoa);
        updatedPessoa
            .tipoPessoa(UPDATED_TIPO_PESSOA)
            .nome(UPDATED_NOME)
            .nif(UPDATED_NIF)
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE);
        PessoaDTO pessoaDTO = pessoaMapper.toDto(updatedPessoa);

        restPessoaMockMvc.perform(put("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getTipoPessoa()).isEqualTo(UPDATED_TIPO_PESSOA);
        assertThat(testPessoa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoa.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testPessoa.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testPessoa.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Create the Pessoa
        PessoaDTO pessoaDTO = pessoaMapper.toDto(pessoa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaMockMvc.perform(put("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        int databaseSizeBeforeDelete = pessoaRepository.findAll().size();

        // Delete the pessoa
        restPessoaMockMvc.perform(delete("/api/pessoas/{id}", pessoa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
