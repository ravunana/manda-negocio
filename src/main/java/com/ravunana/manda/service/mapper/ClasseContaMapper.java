package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ClasseContaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClasseConta} and its DTO {@link ClasseContaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClasseContaMapper extends EntityMapper<ClasseContaDTO, ClasseConta> {


    @Mapping(target = "contas", ignore = true)
    @Mapping(target = "removeConta", ignore = true)
    ClasseConta toEntity(ClasseContaDTO classeContaDTO);

    default ClasseConta fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClasseConta classeConta = new ClasseConta();
        classeConta.setId(id);
        return classeConta;
    }
}
