package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.UnidadeMedidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnidadeMedida} and its DTO {@link UnidadeMedidaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnidadeMedidaMapper extends EntityMapper<UnidadeMedidaDTO, UnidadeMedida> {

    @Mapping(source = "unidadeConversao.id", target = "unidadeConversaoId")
    @Mapping(source = "unidadeConversao.nome", target = "unidadeConversaoNome")
    UnidadeMedidaDTO toDto(UnidadeMedida unidadeMedida);

    @Mapping(target = "unidadeMedidas", ignore = true)
    @Mapping(target = "removeUnidadeMedida", ignore = true)
    @Mapping(target = "grupoTributacaoImpostos", ignore = true)
    @Mapping(target = "removeGrupoTributacaoImposto", ignore = true)
    @Mapping(target = "compostoProdutos", ignore = true)
    @Mapping(target = "removeCompostoProduto", ignore = true)
    @Mapping(source = "unidadeConversaoId", target = "unidadeConversao")
    UnidadeMedida toEntity(UnidadeMedidaDTO unidadeMedidaDTO);

    default UnidadeMedida fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnidadeMedida unidadeMedida = new UnidadeMedida();
        unidadeMedida.setId(id);
        return unidadeMedida;
    }
}
