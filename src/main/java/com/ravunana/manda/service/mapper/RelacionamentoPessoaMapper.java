package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.RelacionamentoPessoaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RelacionamentoPessoa} and its DTO {@link RelacionamentoPessoaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class})
public interface RelacionamentoPessoaMapper extends EntityMapper<RelacionamentoPessoaDTO, RelacionamentoPessoa> {

    @Mapping(source = "de.id", target = "deId")
    @Mapping(source = "de.nome", target = "deNome")
    @Mapping(source = "para.id", target = "paraId")
    @Mapping(source = "para.nome", target = "paraNome")
    RelacionamentoPessoaDTO toDto(RelacionamentoPessoa relacionamentoPessoa);

    @Mapping(source = "deId", target = "de")
    @Mapping(source = "paraId", target = "para")
    RelacionamentoPessoa toEntity(RelacionamentoPessoaDTO relacionamentoPessoaDTO);

    default RelacionamentoPessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        RelacionamentoPessoa relacionamentoPessoa = new RelacionamentoPessoa();
        relacionamentoPessoa.setId(id);
        return relacionamentoPessoa;
    }
}
