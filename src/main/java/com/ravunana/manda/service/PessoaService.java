package com.ravunana.manda.service;

import com.ravunana.manda.domain.Pessoa;
import com.ravunana.manda.repository.PessoaRepository;
import com.ravunana.manda.repository.UserRepository;
import com.ravunana.manda.service.dto.ContactoPessoaDTO;
import com.ravunana.manda.service.dto.MoradaPessoaDTO;
import com.ravunana.manda.service.dto.PessoaDTO;
import com.ravunana.manda.service.mapper.PessoaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pessoa}.
 */
@Service
@Transactional
public class PessoaService {

    private final Logger log = LoggerFactory.getLogger(PessoaService.class);

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    @Autowired
    private ContactoPessoaService contactoPessoaService;
    @Autowired
    private MoradaPessoaService moradaPessoaService;
    @Autowired
    private UserRepository UserRepository;

    public PessoaService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
    }

    /**
     * Save a pessoa.
     *
     * @param pessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public PessoaDTO save(PessoaDTO pessoaDTO) {
        log.debug("Request to save Pessoa : {}", pessoaDTO);
        Pessoa pessoa = pessoaMapper.toEntity(pessoaDTO);
        pessoa.setUtilizador( UserRepository.findById(1L).get() );
        pessoa = pessoaRepository.save(pessoa);
        // Percorrer contacto
        for ( ContactoPessoaDTO contacto : contactoPessoaService.listContactos() ) {
            contacto.setPessoaId( pessoa.getId() );
            contactoPessoaService.save( contacto );
        }

        // Percorrer moradas
        for ( MoradaPessoaDTO morada : moradaPessoaService.listMoradas() ) {
            morada.setPessoaId( pessoa.getId() );
            moradaPessoaService.save( morada );
        }

        contactoPessoaService.limparContactos();
        moradaPessoaService.limparMoradas();
        return pessoaMapper.toDto(pessoa);
    }

    /**
     * Get all the pessoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PessoaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pessoas");
        return pessoaRepository.findAll(pageable)
            .map(pessoaMapper::toDto);
    }


    /**
     * Get one pessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findOne(Long id) {
        log.debug("Request to get Pessoa : {}", id);
        return pessoaRepository.findById(id)
            .map(pessoaMapper::toDto);
    }

    /**
     * Delete the pessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pessoa : {}", id);
        pessoaRepository.deleteById(id);
    }
}
