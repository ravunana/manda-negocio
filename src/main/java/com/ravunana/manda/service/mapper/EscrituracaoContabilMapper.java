package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.EscrituracaoContabilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EscrituracaoContabil} and its DTO {@link EscrituracaoContabilDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EmpresaMapper.class})
public interface EscrituracaoContabilMapper extends EntityMapper<EscrituracaoContabilDTO, EscrituracaoContabil> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nome", target = "empresaNome")
    EscrituracaoContabilDTO toDto(EscrituracaoContabil escrituracaoContabil);

    @Mapping(target = "contaDebitos", ignore = true)
    @Mapping(target = "removeContaDebito", ignore = true)
    @Mapping(target = "contaCreditos", ignore = true)
    @Mapping(target = "removeContaCredito", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "empresaId", target = "empresa")
    EscrituracaoContabil toEntity(EscrituracaoContabilDTO escrituracaoContabilDTO);

    default EscrituracaoContabil fromId(Long id) {
        if (id == null) {
            return null;
        }
        EscrituracaoContabil escrituracaoContabil = new EscrituracaoContabil();
        escrituracaoContabil.setId(id);
        return escrituracaoContabil;
    }
}
