package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.FamiliaProdutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FamiliaProduto} and its DTO {@link FamiliaProdutoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContaMapper.class})
public interface FamiliaProdutoMapper extends EntityMapper<FamiliaProdutoDTO, FamiliaProduto> {

    @Mapping(source = "conta.id", target = "contaId")
    @Mapping(source = "conta.descricao", target = "contaDescricao")
    @Mapping(source = "hierarquia.id", target = "hierarquiaId")
    @Mapping(source = "hierarquia.nome", target = "hierarquiaNome")
    FamiliaProdutoDTO toDto(FamiliaProduto familiaProduto);

    @Mapping(target = "familiaProdutos", ignore = true)
    @Mapping(target = "removeFamiliaProduto", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    @Mapping(target = "removeProduto", ignore = true)
    @Mapping(source = "contaId", target = "conta")
    @Mapping(source = "hierarquiaId", target = "hierarquia")
    FamiliaProduto toEntity(FamiliaProdutoDTO familiaProdutoDTO);

    default FamiliaProduto fromId(Long id) {
        if (id == null) {
            return null;
        }
        FamiliaProduto familiaProduto = new FamiliaProduto();
        familiaProduto.setId(id);
        return familiaProduto;
    }
}
