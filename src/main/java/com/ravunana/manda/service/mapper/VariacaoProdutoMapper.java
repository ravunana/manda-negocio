package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.VariacaoProdutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VariacaoProduto} and its DTO {@link VariacaoProdutoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface VariacaoProdutoMapper extends EntityMapper<VariacaoProdutoDTO, VariacaoProduto> {

    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
    VariacaoProdutoDTO toDto(VariacaoProduto variacaoProduto);

    @Mapping(source = "produtoId", target = "produto")
    VariacaoProduto toEntity(VariacaoProdutoDTO variacaoProdutoDTO);

    default VariacaoProduto fromId(Long id) {
        if (id == null) {
            return null;
        }
        VariacaoProduto variacaoProduto = new VariacaoProduto();
        variacaoProduto.setId(id);
        return variacaoProduto;
    }
}
