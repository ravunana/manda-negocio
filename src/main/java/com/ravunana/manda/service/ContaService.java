package com.ravunana.manda.service;

import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.repository.ContaRepository;
import com.ravunana.manda.service.dto.ContaDTO;
import com.ravunana.manda.service.mapper.ContaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Conta}.
 */
@Service
@Transactional
public class ContaService {

    private final Logger log = LoggerFactory.getLogger(ContaService.class);

    private final ContaRepository contaRepository;

    private final ContaMapper contaMapper;

    public ContaService(ContaRepository contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    /**
     * Save a conta.
     *
     * @param contaDTO the entity to save.
     * @return the persisted entity.
     */
    public ContaDTO save(ContaDTO contaDTO) {
        log.debug("Request to save Conta : {}", contaDTO);
        Conta conta = contaMapper.toEntity(contaDTO);
        conta = contaRepository.save(conta);
        return contaMapper.toDto(conta);
    }

    /**
     * Get all the contas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contas");
        return contaRepository.findAll(pageable)
            .map(contaMapper::toDto);
    }

    /**
     * Get all the contas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ContaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return contaRepository.findAllWithEagerRelationships(pageable).map(contaMapper::toDto);
    }


    /**
     * Get one conta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContaDTO> findOne(Long id) {
        log.debug("Request to get Conta : {}", id);
        return contaRepository.findOneWithEagerRelationships(id)
            .map(contaMapper::toDto);
    }

    /**
     * Delete the conta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Conta : {}", id);
        contaRepository.deleteById(id);
    }

    public Conta addSubConta(Long contaAgregadoraId, String descricao) {
        Conta contaAgregadora = null;
        Conta novaConta = null;
        try{
            contaAgregadora = contaRepository.findById( contaAgregadoraId ).get();
            if ( contaAgregadora.equals(null) || contaAgregadora == null ) {
                return new Conta();
            } else {

                String codigoUltimaConta = contaRepository.findByContaAgregadora(contaAgregadora)
                .stream()
                .reduce( (a,b) -> b ).get().getCodigo();

                int sequenciaCodigoConta = Integer.parseInt(codigoUltimaConta.substring( codigoUltimaConta.length() -1 ));

                novaConta = new Conta();
                novaConta.setClasseConta( contaAgregadora.getClasseConta() );

                novaConta.setCodigo( contaAgregadora.getCodigo() + "." + (sequenciaCodigoConta + 1) );
                novaConta.setContaAgregadora( contaAgregadora );
                novaConta.setConteudo("");
                novaConta.setDescricao(descricao);
                novaConta.setGrau(contaAgregadora.getGrau());
                novaConta.setGrupo( contaAgregadora.getGrupo() );
                novaConta.setNatureza( contaAgregadora.getNatureza() );
                novaConta.setTipo( contaAgregadora.getTipo() );

                novaConta = contaRepository.save(novaConta);

                return novaConta;
            }
        } catch (Exception ex) {

            novaConta = new Conta();
            novaConta.setClasseConta( contaAgregadora.getClasseConta() );

            novaConta.setCodigo( contaAgregadora.getCodigo() + "." + 1 );
            novaConta.setContaAgregadora( contaAgregadora );
            novaConta.setConteudo("");
            novaConta.setDescricao(descricao);
            novaConta.setGrau(contaAgregadora.getGrau());
            novaConta.setGrupo( contaAgregadora.getGrupo() );
            novaConta.setNatureza( contaAgregadora.getNatureza() );
            novaConta.setTipo( contaAgregadora.getTipo() );

            novaConta = contaRepository.save(novaConta);

            return novaConta;

        }
}
}
