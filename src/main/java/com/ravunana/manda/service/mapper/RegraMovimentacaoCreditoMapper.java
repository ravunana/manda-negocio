package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.RegraMovimentacaoCreditoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RegraMovimentacaoCredito} and its DTO {@link RegraMovimentacaoCreditoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContaMapper.class})
public interface RegraMovimentacaoCreditoMapper extends EntityMapper<RegraMovimentacaoCreditoDTO, RegraMovimentacaoCredito> {

    @Mapping(source = "conta.id", target = "contaId")
    @Mapping(source = "conta.descricao", target = "contaDescricao")
    RegraMovimentacaoCreditoDTO toDto(RegraMovimentacaoCredito regraMovimentacaoCredito);

    @Mapping(source = "contaId", target = "conta")
    RegraMovimentacaoCredito toEntity(RegraMovimentacaoCreditoDTO regraMovimentacaoCreditoDTO);

    default RegraMovimentacaoCredito fromId(Long id) {
        if (id == null) {
            return null;
        }
        RegraMovimentacaoCredito regraMovimentacaoCredito = new RegraMovimentacaoCredito();
        regraMovimentacaoCredito.setId(id);
        return regraMovimentacaoCredito;
    }
}
