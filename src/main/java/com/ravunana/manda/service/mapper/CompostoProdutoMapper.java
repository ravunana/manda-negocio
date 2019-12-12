package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.CompostoProdutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompostoProduto} and its DTO {@link CompostoProdutoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProdutoMapper.class, UnidadeMedidaMapper.class})
public interface CompostoProdutoMapper extends EntityMapper<CompostoProdutoDTO, CompostoProduto> {

    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
    @Mapping(source = "unidade.id", target = "unidadeId")
    @Mapping(source = "unidade.nome", target = "unidadeNome")
    @Mapping(source = "composto.id", target = "compostoId")
    @Mapping(source = "composto.nome", target = "compostoNome")
    CompostoProdutoDTO toDto(CompostoProduto compostoProduto);

    @Mapping(source = "produtoId", target = "produto")
    @Mapping(source = "unidadeId", target = "unidade")
    @Mapping(source = "compostoId", target = "composto")
    CompostoProduto toEntity(CompostoProdutoDTO compostoProdutoDTO);

    default CompostoProduto fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompostoProduto compostoProduto = new CompostoProduto();
        compostoProduto.setId(id);
        return compostoProduto;
    }
}
