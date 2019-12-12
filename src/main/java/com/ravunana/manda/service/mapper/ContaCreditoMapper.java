package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ContaCreditoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContaCredito} and its DTO {@link ContaCreditoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContaMapper.class, EscrituracaoContabilMapper.class})
public interface ContaCreditoMapper extends EntityMapper<ContaCreditoDTO, ContaCredito> {

    @Mapping(source = "contaCreditar.id", target = "contaCreditarId")
    @Mapping(source = "contaCreditar.descricao", target = "contaCreditarDescricao")
    @Mapping(source = "lancamentoCredito.id", target = "lancamentoCreditoId")
    @Mapping(source = "lancamentoCredito.historico", target = "lancamentoCreditoHistorico")
    ContaCreditoDTO toDto(ContaCredito contaCredito);

    @Mapping(source = "contaCreditarId", target = "contaCreditar")
    @Mapping(source = "lancamentoCreditoId", target = "lancamentoCredito")
    ContaCredito toEntity(ContaCreditoDTO contaCreditoDTO);

    default ContaCredito fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContaCredito contaCredito = new ContaCredito();
        contaCredito.setId(id);
        return contaCredito;
    }
}
