package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.SoftwareDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Software} and its DTO {@link SoftwareDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SoftwareMapper extends EntityMapper<SoftwareDTO, Software> {


    @Mapping(target = "licencaSoftwares", ignore = true)
    @Mapping(target = "removeLicencaSoftware", ignore = true)
    Software toEntity(SoftwareDTO softwareDTO);

    default Software fromId(Long id) {
        if (id == null) {
            return null;
        }
        Software software = new Software();
        software.setId(id);
        return software;
    }
}
