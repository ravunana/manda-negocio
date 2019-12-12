package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.MoradaPessoaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MoradaPessoa} and its DTO {@link MoradaPessoaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class})
public interface MoradaPessoaMapper extends EntityMapper<MoradaPessoaDTO, MoradaPessoa> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    MoradaPessoaDTO toDto(MoradaPessoa moradaPessoa);

    @Mapping(source = "pessoaId", target = "pessoa")
    MoradaPessoa toEntity(MoradaPessoaDTO moradaPessoaDTO);

    default MoradaPessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        MoradaPessoa moradaPessoa = new MoradaPessoa();
        moradaPessoa.setId(id);
        return moradaPessoa;
    }
}
