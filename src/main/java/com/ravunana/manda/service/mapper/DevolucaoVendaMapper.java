package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.DevolucaoVendaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DevolucaoVenda} and its DTO {@link DevolucaoVendaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ItemVendaMapper.class})
public interface DevolucaoVendaMapper extends EntityMapper<DevolucaoVendaDTO, DevolucaoVenda> {

    @Mapping(source = "item.id", target = "itemId")
    DevolucaoVendaDTO toDto(DevolucaoVenda devolucaoVenda);

    @Mapping(source = "itemId", target = "item")
    DevolucaoVenda toEntity(DevolucaoVendaDTO devolucaoVendaDTO);

    default DevolucaoVenda fromId(Long id) {
        if (id == null) {
            return null;
        }
        DevolucaoVenda devolucaoVenda = new DevolucaoVenda();
        devolucaoVenda.setId(id);
        return devolucaoVenda;
    }
}
