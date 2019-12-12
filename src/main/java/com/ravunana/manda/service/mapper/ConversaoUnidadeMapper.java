package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ConversaoUnidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConversaoUnidade} and its DTO {@link ConversaoUnidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnidadeMedidaMapper.class, ProdutoMapper.class})
public interface ConversaoUnidadeMapper extends EntityMapper<ConversaoUnidadeDTO, ConversaoUnidade> {

    @Mapping(source = "entrada.id", target = "entradaId")
    @Mapping(source = "entrada.nome", target = "entradaNome")
    @Mapping(source = "saida.id", target = "saidaId")
    @Mapping(source = "saida.nome", target = "saidaNome")
    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
    ConversaoUnidadeDTO toDto(ConversaoUnidade conversaoUnidade);

    @Mapping(source = "entradaId", target = "entrada")
    @Mapping(source = "saidaId", target = "saida")
    @Mapping(source = "produtoId", target = "produto")
    ConversaoUnidade toEntity(ConversaoUnidadeDTO conversaoUnidadeDTO);

    default ConversaoUnidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConversaoUnidade conversaoUnidade = new ConversaoUnidade();
        conversaoUnidade.setId(id);
        return conversaoUnidade;
    }
}
