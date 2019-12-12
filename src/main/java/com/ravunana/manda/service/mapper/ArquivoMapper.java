package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ArquivoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Arquivo} and its DTO {@link ArquivoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ArquivoMapper extends EntityMapper<ArquivoDTO, Arquivo> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    ArquivoDTO toDto(Arquivo arquivo);

    @Mapping(source = "utilizadorId", target = "utilizador")
    Arquivo toEntity(ArquivoDTO arquivoDTO);

    default Arquivo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Arquivo arquivo = new Arquivo();
        arquivo.setId(id);
        return arquivo;
    }
}
