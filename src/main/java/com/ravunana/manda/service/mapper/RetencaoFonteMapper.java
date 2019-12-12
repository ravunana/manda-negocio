package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.RetencaoFonteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RetencaoFonte} and its DTO {@link RetencaoFonteDTO}.
 */
@Mapper(componentModel = "spring", uses = {DetalheLancamentoMapper.class, ImpostoMapper.class})
public interface RetencaoFonteMapper extends EntityMapper<RetencaoFonteDTO, RetencaoFonte> {

    @Mapping(source = "detalhe.id", target = "detalheId")
    @Mapping(source = "imposto.id", target = "impostoId")
    @Mapping(source = "imposto.codigoImposto", target = "impostoCodigoImposto")
    RetencaoFonteDTO toDto(RetencaoFonte retencaoFonte);

    @Mapping(source = "detalheId", target = "detalhe")
    @Mapping(source = "impostoId", target = "imposto")
    RetencaoFonte toEntity(RetencaoFonteDTO retencaoFonteDTO);

    default RetencaoFonte fromId(Long id) {
        if (id == null) {
            return null;
        }
        RetencaoFonte retencaoFonte = new RetencaoFonte();
        retencaoFonte.setId(id);
        return retencaoFonte;
    }
}
