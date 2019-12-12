package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ContactoPessoaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContactoPessoa} and its DTO {@link ContactoPessoaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class})
public interface ContactoPessoaMapper extends EntityMapper<ContactoPessoaDTO, ContactoPessoa> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    ContactoPessoaDTO toDto(ContactoPessoa contactoPessoa);

    @Mapping(source = "pessoaId", target = "pessoa")
    ContactoPessoa toEntity(ContactoPessoaDTO contactoPessoaDTO);

    default ContactoPessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactoPessoa contactoPessoa = new ContactoPessoa();
        contactoPessoa.setId(id);
        return contactoPessoa;
    }
}
