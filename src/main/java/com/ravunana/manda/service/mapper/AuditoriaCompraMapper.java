package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.AuditoriaCompraDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity {@link AuditoriaCompra} and its DTO {@link AuditoriaCompraDTO}.
 */
@Component
@Mapper(componentModel = "spring", uses = {CompraMapper.class, UserMapper.class})
public interface AuditoriaCompraMapper extends EntityMapper<AuditoriaCompraDTO, AuditoriaCompra> {

    @Mapping(source = "compra.id", target = "compraId")
    @Mapping(source = "compra.numero", target = "compraNumero")
    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    AuditoriaCompraDTO toDto(AuditoriaCompra auditoriaCompra);

    @Mapping(source = "compraId", target = "compra")
    @Mapping(source = "utilizadorId", target = "utilizador")
    AuditoriaCompra toEntity(AuditoriaCompraDTO auditoriaCompraDTO);

    default AuditoriaCompra fromId(Long id) {
        if (id == null) {
            return null;
        }
        AuditoriaCompra auditoriaCompra = new AuditoriaCompra();
        auditoriaCompra.setId(id);
        return auditoriaCompra;
    }
}
