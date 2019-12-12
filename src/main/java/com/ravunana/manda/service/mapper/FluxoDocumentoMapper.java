package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.FluxoDocumentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FluxoDocumento} and its DTO {@link FluxoDocumentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FluxoDocumentoMapper extends EntityMapper<FluxoDocumentoDTO, FluxoDocumento> {


    @Mapping(target = "produtos", ignore = true)
    @Mapping(target = "removeProduto", ignore = true)
    @Mapping(target = "itemCompras", ignore = true)
    @Mapping(target = "removeItemCompra", ignore = true)
    @Mapping(target = "itemVendas", ignore = true)
    @Mapping(target = "removeItemVenda", ignore = true)
    FluxoDocumento toEntity(FluxoDocumentoDTO fluxoDocumentoDTO);

    default FluxoDocumento fromId(Long id) {
        if (id == null) {
            return null;
        }
        FluxoDocumento fluxoDocumento = new FluxoDocumento();
        fluxoDocumento.setId(id);
        return fluxoDocumento;
    }
}
