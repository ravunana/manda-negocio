package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.MeioLiquidacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MeioLiquidacao} and its DTO {@link MeioLiquidacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MeioLiquidacaoMapper extends EntityMapper<MeioLiquidacaoDTO, MeioLiquidacao> {


    @Mapping(target = "detalheLancamentos", ignore = true)
    @Mapping(target = "removeDetalheLancamento", ignore = true)
    MeioLiquidacao toEntity(MeioLiquidacaoDTO meioLiquidacaoDTO);

    default MeioLiquidacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        MeioLiquidacao meioLiquidacao = new MeioLiquidacao();
        meioLiquidacao.setId(id);
        return meioLiquidacao;
    }
}
