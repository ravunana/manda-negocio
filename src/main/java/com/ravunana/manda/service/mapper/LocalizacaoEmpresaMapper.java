package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.LocalizacaoEmpresaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocalizacaoEmpresa} and its DTO {@link LocalizacaoEmpresaDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface LocalizacaoEmpresaMapper extends EntityMapper<LocalizacaoEmpresaDTO, LocalizacaoEmpresa> {

    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nome", target = "empresaNome")
    LocalizacaoEmpresaDTO toDto(LocalizacaoEmpresa localizacaoEmpresa);

    @Mapping(source = "empresaId", target = "empresa")
    LocalizacaoEmpresa toEntity(LocalizacaoEmpresaDTO localizacaoEmpresaDTO);

    default LocalizacaoEmpresa fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocalizacaoEmpresa localizacaoEmpresa = new LocalizacaoEmpresa();
        localizacaoEmpresa.setId(id);
        return localizacaoEmpresa;
    }
}
