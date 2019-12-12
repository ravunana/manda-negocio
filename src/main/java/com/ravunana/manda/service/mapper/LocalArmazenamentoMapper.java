package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.LocalArmazenamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocalArmazenamento} and its DTO {@link LocalArmazenamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface LocalArmazenamentoMapper extends EntityMapper<LocalArmazenamentoDTO, LocalArmazenamento> {

    @Mapping(source = "hierarquia.id", target = "hierarquiaId")
    @Mapping(source = "hierarquia.nome", target = "hierarquiaNome")
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nome", target = "empresaNome")
    LocalArmazenamentoDTO toDto(LocalArmazenamento localArmazenamento);

    @Mapping(target = "produtos", ignore = true)
    @Mapping(target = "removeProduto", ignore = true)
    @Mapping(target = "localArmazenamentos", ignore = true)
    @Mapping(target = "removeLocalArmazenamento", ignore = true)
    @Mapping(source = "hierarquiaId", target = "hierarquia")
    @Mapping(source = "empresaId", target = "empresa")
    LocalArmazenamento toEntity(LocalArmazenamentoDTO localArmazenamentoDTO);

    default LocalArmazenamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocalArmazenamento localArmazenamento = new LocalArmazenamento();
        localArmazenamento.setId(id);
        return localArmazenamento;
    }
}
