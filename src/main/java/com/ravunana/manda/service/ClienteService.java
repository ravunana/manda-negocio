package com.ravunana.manda.service;

import com.ravunana.manda.domain.Cliente;
import com.ravunana.manda.domain.Conta;
import com.ravunana.manda.domain.Pessoa;
import com.ravunana.manda.domain.SerieDocumento;
import com.ravunana.manda.repository.ClienteRepository;
import com.ravunana.manda.repository.PessoaRepository;
import com.ravunana.manda.service.dto.ClienteDTO;
import com.ravunana.manda.service.mapper.ClienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cliente}.
 */
@Service
@Transactional
public class ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper;

    @Autowired
    private SerieDocumentoService serieDocumentoService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ContaService contaService;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    /**
     * Save a cliente.
     *
     * @param clienteDTO the entity to save.
     * @return the persisted entity.
     */
    public ClienteDTO save(ClienteDTO clienteDTO) {
        log.debug("Request to save Cliente : {}", clienteDTO);
        Cliente cliente = clienteMapper.toEntity(clienteDTO);

        Pessoa pessoa = pessoaRepository.findById( clienteDTO.getPessoaId() ).get();

        if ( cliente.getId() != null ) {
            String numeroSalvo = clienteRepository.findById( clienteDTO.getId() ).get().getNumero();
            cliente.setNumero(numeroSalvo);
            cliente = clienteRepository.save(cliente);
        } else {

            Conta conta = contaService.addSubConta(1182L, pessoa.getNome());
            cliente.setConta(conta);
            cliente.setNumero( getNumeroCliente(pessoa.getTipoPessoa()));
            cliente = clienteRepository.save(cliente);
        }

        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

    private String getNumeroCliente( String tipoPessoa ) {
        SerieDocumento serieDocumento = serieDocumentoService.getSerieDocumentoAnoActual();

        int sequencia = serieDocumento.getCodigoCliente();
        String numero = "P" + tipoPessoa.substring(0, 1) + " " + serieDocumento.getSerie() + "/" + sequencia; // <TIPO_DOCUMENTO> <SEQUENCIA_FORNECEDOR>
        // atualizar serie do documento
        serieDocumento.setCodigoCliente( sequencia + 1 );
        serieDocumentoService.atualizarSerieDocumento(serieDocumento);

        return numero;
    }

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clientes");
        return clienteRepository.findAll(pageable)
            .map(clienteMapper::toDto);
    }


    /**
     * Get one cliente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> findOne(Long id) {
        log.debug("Request to get Cliente : {}", id);
        return clienteRepository.findById(id)
            .map(clienteMapper::toDto);
    }

    /**
     * Delete the cliente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cliente : {}", id);
        clienteRepository.deleteById(id);
    }
}
