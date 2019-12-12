package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.RegraMovimentacaoDebitoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RegraMovimentacaoDebito} and its DTO {@link RegraMovimentacaoDebitoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContaMapper.class})
public interface RegraMovimentacaoDebitoMapper extends EntityMapper<RegraMovimentacaoDebitoDTO, RegraMovimentacaoDebito> {

    @Mapping(source = "conta.id", target = "contaId")
    @Mapping(source = "conta.descricao", target = "contaDescricao")
    RegraMovimentacaoDebitoDTO toDto(RegraMovimentacaoDebito regraMovimentacaoDebito);

    @Mapping(source = "contaId", target = "conta")
    RegraMovimentacaoDebito toEntity(RegraMovimentacaoDebitoDTO regraMovimentacaoDebitoDTO);

    default RegraMovimentacaoDebito fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegraMovimentacaoDebito regraMovimentacaoDebito = new RegraMovimentacaoDebito();
        regraMovimentacaoDebito.setId(id);
        return regraMovimentacaoDebito;
    }
}
