package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.LookupItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LookupItem} and its DTO {@link LookupItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {LookupMapper.class})
public interface LookupItemMapper extends EntityMapper<LookupItemDTO, LookupItem> {

    @Mapping(source = "lookup.id", target = "lookupId")
    @Mapping(source = "lookup.nome", target = "lookupNome")
    LookupItemDTO toDto(LookupItem lookupItem);

    @Mapping(source = "lookupId", target = "lookup")
    LookupItem toEntity(LookupItemDTO lookupItemDTO);

    default LookupItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        LookupItem lookupItem = new LookupItem();
        lookupItem.setId(id);
        return lookupItem;
    }
}
