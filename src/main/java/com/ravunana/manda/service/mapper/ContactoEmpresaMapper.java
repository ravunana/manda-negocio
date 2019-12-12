package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ContactoEmpresaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContactoEmpresa} and its DTO {@link ContactoEmpresaDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface ContactoEmpresaMapper extends EntityMapper<ContactoEmpresaDTO, ContactoEmpresa> {

    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nome", target = "empresaNome")
    ContactoEmpresaDTO toDto(ContactoEmpresa contactoEmpresa);

    @Mapping(source = "empresaId", target = "empresa")
    ContactoEmpresa toEntity(ContactoEmpresaDTO contactoEmpresaDTO);

    default ContactoEmpresa fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactoEmpresa contactoEmpresa = new ContactoEmpresa();
        contactoEmpresa.setId(id);
        return contactoEmpresa;
    }
}
