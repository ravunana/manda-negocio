package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.CompraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Compra} and its DTO {@link CompraDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, DocumentoComercialMapper.class, EmpresaMapper.class})
public interface CompraMapper extends EntityMapper<CompraDTO, Compra> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "tipoDocumento.id", target = "tipoDocumentoId")
    @Mapping(source = "tipoDocumento.nome", target = "tipoDocumentoNome")
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nome", target = "empresaNome")
    CompraDTO toDto(Compra compra);

    @Mapping(target = "itemCompras", ignore = true)
    @Mapping(target = "removeItemCompra", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "tipoDocumentoId", target = "tipoDocumento")
    @Mapping(source = "empresaId", target = "empresa")
    Compra toEntity(CompraDTO compraDTO);

    default Compra fromId(Long id) {
        if (id == null) {
            return null;
        }
        Compra compra = new Compra();
        compra.setId(id);
        return compra;
    }
}
