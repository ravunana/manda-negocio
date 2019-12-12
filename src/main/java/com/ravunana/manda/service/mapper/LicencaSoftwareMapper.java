package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.LicencaSoftwareDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LicencaSoftware} and its DTO {@link LicencaSoftwareDTO}.
 */
@Mapper(componentModel = "spring", uses = {SoftwareMapper.class, EmpresaMapper.class})
public interface LicencaSoftwareMapper extends EntityMapper<LicencaSoftwareDTO, LicencaSoftware> {

    @Mapping(source = "software.id", target = "softwareId")
    @Mapping(source = "software.nome", target = "softwareNome")
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nome", target = "empresaNome")
    LicencaSoftwareDTO toDto(LicencaSoftware licencaSoftware);

    @Mapping(source = "softwareId", target = "software")
    @Mapping(source = "empresaId", target = "empresa")
    LicencaSoftware toEntity(LicencaSoftwareDTO licencaSoftwareDTO);

    default LicencaSoftware fromId(Long id) {
        if (id == null) {
            return null;
        }
        LicencaSoftware licencaSoftware = new LicencaSoftware();
        licencaSoftware.setId(id);
        return licencaSoftware;
    }
}
