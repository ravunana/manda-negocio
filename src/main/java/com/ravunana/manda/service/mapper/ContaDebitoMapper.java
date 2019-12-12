package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ContaDebitoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContaDebito} and its DTO {@link ContaDebitoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContaMapper.class, EscrituracaoContabilMapper.class})
public interface ContaDebitoMapper extends EntityMapper<ContaDebitoDTO, ContaDebito> {

    @Mapping(source = "contaDebitar.id", target = "contaDebitarId")
    @Mapping(source = "contaDebitar.descricao", target = "contaDebitarDescricao")
    @Mapping(source = "lancamentoDebito.id", target = "lancamentoDebitoId")
    @Mapping(source = "lancamentoDebito.historico", target = "lancamentoDebitoHistorico")
    ContaDebitoDTO toDto(ContaDebito contaDebito);

    @Mapping(source = "contaDebitarId", target = "contaDebitar")
    @Mapping(source = "lancamentoDebitoId", target = "lancamentoDebito")
    ContaDebito toEntity(ContaDebitoDTO contaDebitoDTO);

    default ContaDebito fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContaDebito contaDebito = new ContaDebito();
        contaDebito.setId(id);
        return contaDebito;
    }
}
