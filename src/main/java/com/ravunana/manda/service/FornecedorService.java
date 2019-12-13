package com.ravunana.manda.service;

import com.ravunana.manda.domain.Fornecedor;
import com.ravunana.manda.domain.Pessoa;
import com.ravunana.manda.domain.SerieDocumento;
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
        SerieDocumento serieDocumento = serieDocumentoService.getSerieDocumentoAnoActual();
        int sequencia = serieDocumento.getCodigoFornecedor();
        Pessoa pessoa = pessoaRepository.findById( fornecedorDTO.getPessoaId() ).get();
        fornecedor.setNumero( pessoa.getTipoPessoa().substring(0, 1) + " " + serieDocumento.getSerie() + " " + sequencia ); // <TIPO_PESSOA> <SEQUENCIA_FORNECEDOR>
        fornecedor = fornecedorRepository.save(fornecedor);
        if ( fornecedor.getId() > 0 ) {
        // atualizar serie do documento
        serieDocumento.setCodigoFornecedor( sequencia + 1 );
        serieDocumentoService.atualizarSerieDocumento(serieDocumento);
        }
        return fornecedorMapper.toDto(fornecedor);
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
