package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ClienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class, ContaMapper.class})
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    @Mapping(source = "conta.id", target = "contaId")
    @Mapping(source = "conta.descricao", target = "contaDescricao")
    ClienteDTO toDto(Cliente cliente);

    @Mapping(source = "pessoaId", target = "pessoa")
    @Mapping(target = "vendas", ignore = true)
    @Mapping(target = "removeVenda", ignore = true)
    @Mapping(source = "contaId", target = "conta")
    Cliente toEntity(ClienteDTO clienteDTO);

    default Cliente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }
}
