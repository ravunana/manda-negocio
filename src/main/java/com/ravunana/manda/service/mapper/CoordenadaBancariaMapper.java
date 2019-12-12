package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.CoordenadaBancariaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CoordenadaBancaria} and its DTO {@link CoordenadaBancariaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContaMapper.class, EmpresaMapper.class})
public interface CoordenadaBancariaMapper extends EntityMapper<CoordenadaBancariaDTO, CoordenadaBancaria> {

    @Mapping(source = "conta.id", target = "contaId")
    @Mapping(source = "conta.descricao", target = "contaDescricao")
    CoordenadaBancariaDTO toDto(CoordenadaBancaria coordenadaBancaria);

    @Mapping(target = "detalheLancamentos", ignore = true)
    @Mapping(target = "removeDetalheLancamento", ignore = true)
    @Mapping(source = "contaId", target = "conta")
    @Mapping(target = "removeEmpresa", ignore = true)
    CoordenadaBancaria toEntity(CoordenadaBancariaDTO coordenadaBancariaDTO);

    default CoordenadaBancaria fromId(Long id) {
        if (id == null) {
            return null;
        }
        CoordenadaBancaria coordenadaBancaria = new CoordenadaBancaria();
        coordenadaBancaria.setId(id);
        return coordenadaBancaria;
    }
}
