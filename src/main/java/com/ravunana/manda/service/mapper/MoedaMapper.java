package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.MoedaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Moeda} and its DTO {@link MoedaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MoedaMapper extends EntityMapper<MoedaDTO, Moeda> {


    @Mapping(target = "detalheLancamentos", ignore = true)
    @Mapping(target = "removeDetalheLancamento", ignore = true)
    Moeda toEntity(MoedaDTO moedaDTO);

    default Moeda fromId(Long id) {
        if (id == null) {
            return null;
        }
        Moeda moeda = new Moeda();
        moeda.setId(id);
        return moeda;
    }
}
