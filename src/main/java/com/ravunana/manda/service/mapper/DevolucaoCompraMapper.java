package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.DevolucaoCompraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DevolucaoCompra} and its DTO {@link DevolucaoCompraDTO}.
 */
@Mapper(componentModel = "spring", uses = {ItemCompraMapper.class})
public interface DevolucaoCompraMapper extends EntityMapper<DevolucaoCompraDTO, DevolucaoCompra> {

    @Mapping(source = "item.id", target = "itemId")
    DevolucaoCompraDTO toDto(DevolucaoCompra devolucaoCompra);

    @Mapping(source = "itemId", target = "item")
    DevolucaoCompra toEntity(DevolucaoCompraDTO devolucaoCompraDTO);

    default DevolucaoCompra fromId(Long id) {
        if (id == null) {
            return null;
        }
        DevolucaoCompra devolucaoCompra = new DevolucaoCompra();
        devolucaoCompra.setId(id);
        return devolucaoCompra;
    }
}
