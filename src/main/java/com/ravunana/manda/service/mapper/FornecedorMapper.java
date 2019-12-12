package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.FornecedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fornecedor} and its DTO {@link FornecedorDTO}.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class, ContaMapper.class})
public interface FornecedorMapper extends EntityMapper<FornecedorDTO, Fornecedor> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    @Mapping(source = "conta.id", target = "contaId")
    @Mapping(source = "conta.descricao", target = "contaDescricao")
    FornecedorDTO toDto(Fornecedor fornecedor);

    @Mapping(source = "pessoaId", target = "pessoa")
    @Mapping(target = "itemCompras", ignore = true)
    @Mapping(target = "removeItemCompra", ignore = true)
    @Mapping(source = "contaId", target = "conta")
    @Mapping(target = "produtos", ignore = true)
    @Mapping(target = "removeProduto", ignore = true)
    Fornecedor toEntity(FornecedorDTO fornecedorDTO);

    default Fornecedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);
        return fornecedor;
    }
}
