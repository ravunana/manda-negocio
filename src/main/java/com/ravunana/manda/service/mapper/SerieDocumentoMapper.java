package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.SerieDocumentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SerieDocumento} and its DTO {@link SerieDocumentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface SerieDocumentoMapper extends EntityMapper<SerieDocumentoDTO, SerieDocumento> {

    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nome", target = "empresaNome")
    SerieDocumentoDTO toDto(SerieDocumento serieDocumento);

    @Mapping(source = "empresaId", target = "empresa")
    SerieDocumento toEntity(SerieDocumentoDTO serieDocumentoDTO);

    default SerieDocumento fromId(Long id) {
        if (id == null) {
            return null;
        }
        SerieDocumento serieDocumento = new SerieDocumento();
        serieDocumento.setId(id);
        return serieDocumento;
    }
}
