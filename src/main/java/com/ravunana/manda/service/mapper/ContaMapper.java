package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ContaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Conta} and its DTO {@link ContaDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, ClasseContaMapper.class})
public interface ContaMapper extends EntityMapper<ContaDTO, Conta> {

    @Mapping(source = "contaAgregadora.id", target = "contaAgregadoraId")
    @Mapping(source = "contaAgregadora.descricao", target = "contaAgregadoraDescricao")
    @Mapping(source = "classeConta.id", target = "classeContaId")
    @Mapping(source = "classeConta.descricao", target = "classeContaDescricao")
    ContaDTO toDto(Conta conta);

    @Mapping(target = "contas", ignore = true)
    @Mapping(target = "removeConta", ignore = true)
    @Mapping(target = "contaDebitos", ignore = true)
    @Mapping(target = "removeContaDebito", ignore = true)
    @Mapping(target = "contaCreditos", ignore = true)
    @Mapping(target = "removeContaCredito", ignore = true)
    @Mapping(target = "removeEmpresa", ignore = true)
    @Mapping(source = "contaAgregadoraId", target = "contaAgregadora")
    @Mapping(source = "classeContaId", target = "classeConta")
    Conta toEntity(ContaDTO contaDTO);

    default Conta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Conta conta = new Conta();
        conta.setId(id);
        return conta;
    }
}
