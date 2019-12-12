package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.AuditoriaVendaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuditoriaVenda} and its DTO {@link AuditoriaVendaDTO}.
 */
@Mapper(componentModel = "spring", uses = {VendaMapper.class, UserMapper.class})
public interface AuditoriaVendaMapper extends EntityMapper<AuditoriaVendaDTO, AuditoriaVenda> {

    @Mapping(source = "venda.id", target = "vendaId")
    @Mapping(source = "venda.numero", target = "vendaNumero")
    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    AuditoriaVendaDTO toDto(AuditoriaVenda auditoriaVenda);

    @Mapping(source = "vendaId", target = "venda")
    @Mapping(source = "utilizadorId", target = "utilizador")
    AuditoriaVenda toEntity(AuditoriaVendaDTO auditoriaVendaDTO);

    default AuditoriaVenda fromId(Long id) {
        if (id == null) {
            return null;
        }
        AuditoriaVenda auditoriaVenda = new AuditoriaVenda();
        auditoriaVenda.setId(id);
        return auditoriaVenda;
    }
}
