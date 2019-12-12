package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.GrupoTributacaoImpostoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GrupoTributacaoImposto} and its DTO {@link GrupoTributacaoImpostoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ImpostoMapper.class, UnidadeMedidaMapper.class})
public interface GrupoTributacaoImpostoMapper extends EntityMapper<GrupoTributacaoImpostoDTO, GrupoTributacaoImposto> {

    @Mapping(source = "imposto.id", target = "impostoId")
    @Mapping(source = "imposto.codigoImposto", target = "impostoCodigoImposto")
    @Mapping(source = "unidadeLimite.id", target = "unidadeLimiteId")
    @Mapping(source = "unidadeLimite.nome", target = "unidadeLimiteNome")
    GrupoTributacaoImpostoDTO toDto(GrupoTributacaoImposto grupoTributacaoImposto);

    @Mapping(source = "impostoId", target = "imposto")
    @Mapping(source = "unidadeLimiteId", target = "unidadeLimite")
    GrupoTributacaoImposto toEntity(GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO);

    default GrupoTributacaoImposto fromId(Long id) {
        if (id == null) {
            return null;
        }
        GrupoTributacaoImposto grupoTributacaoImposto = new GrupoTributacaoImposto();
        grupoTributacaoImposto.setId(id);
        return grupoTributacaoImposto;
    }
}
