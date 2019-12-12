package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.FormaLiquidacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormaLiquidacao} and its DTO {@link FormaLiquidacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FormaLiquidacaoMapper extends EntityMapper<FormaLiquidacaoDTO, FormaLiquidacao> {


    @Mapping(target = "lancamentoFinanceiros", ignore = true)
    @Mapping(target = "removeLancamentoFinanceiro", ignore = true)
    FormaLiquidacao toEntity(FormaLiquidacaoDTO formaLiquidacaoDTO);

    default FormaLiquidacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        FormaLiquidacao formaLiquidacao = new FormaLiquidacao();
        formaLiquidacao.setId(id);
        return formaLiquidacao;
    }
}
