package com.ravunana.manda.service;

import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.domain.DocumentoComercial;
import com.ravunana.manda.domain.Fornecedor;
import com.ravunana.manda.domain.Pessoa;
import com.ravunana.manda.domain.SerieDocumento;
import com.ravunana.manda.repository.DocumentoComercialRepository;
import com.ravunana.manda.repository.FornecedorRepository;
import com.ravunana.manda.repository.PessoaRepository;
import com.ravunana.manda.service.dto.FornecedorDTO;
import com.ravunana.manda.service.mapper.FornecedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Fornecedor}.
 */
@Service
@Transactional
public class FornecedorService {

    private final Logger log = LoggerFactory.getLogger(FornecedorService.class);

    private final FornecedorRepository fornecedorRepository;

    private final FornecedorMapper fornecedorMapper;

    @Autowired
    private SerieDocumentoService serieDocumentoService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ContaService contaService;

    public FornecedorService(FornecedorRepository fornecedorRepository, FornecedorMapper fornecedorMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    /**
     * Save a fornecedor.
     *
     * @param fornecedorDTO the entity to save.
     * @return the persisted entity.
     */
    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        log.debug("Request to save Fornecedor : {}", fornecedorDTO);

        Fornecedor fornecedor = fornecedorMapper.toEntity(fornecedorDTO);

        Pessoa pessoa = pessoaRepository.findById( fornecedorDTO.getPessoaId() ).get();

        if ( fornecedor.getId() != null ) {
            String numeroSalvo = fornecedorRepository.findById( fornecedorDTO.getId() ).get().getNumero();
            fornecedor.setNumero(numeroSalvo);
            fornecedor = fornecedorRepository.save(fornecedor);
        } else {

            Conta conta = contaService.addSubConta(1187L, pessoa.getNome());
            fornecedor.setConta(conta);
            fornecedor.setNumero( getNumeroFornecedor(pessoa.getTipoPessoa()));
            fornecedor = fornecedorRepository.save(fornecedor);
        }

        return fornecedorMapper.toDto(fornecedor);
    }


    private String getNumeroFornecedor( String tipoPessoa ) {
        SerieDocumento serieDocumento = serieDocumentoService.getSerieDocumentoAnoActual();

        int sequencia = serieDocumento.getCodigoFornecedor();
        String numero = "P" + tipoPessoa.substring(0, 1) + " " + serieDocumento.getSerie() + "/" + sequencia; // <TIPO_DOCUMENTO> <SEQUENCIA_FORNECEDOR>
        // atualizar serie do documento
        serieDocumento.setCodigoFornecedor( sequencia + 1 );
        serieDocumentoService.atualizarSerieDocumento(serieDocumento);

        return numero;
    }

    /**
     * Get all the fornecedors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fornecedors");
        return fornecedorRepository.findAll(pageable)
            .map(fornecedorMapper::toDto);
    }


    /**
     * Get one fornecedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FornecedorDTO> findOne(Long id) {
        log.debug("Request to get Fornecedor : {}", id);
        return fornecedorRepository.findById(id)
            .map(fornecedorMapper::toDto);
    }

    /**
     * Delete the fornecedor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Fornecedor : {}", id);
        fornecedorRepository.deleteById(id);
    }
}
