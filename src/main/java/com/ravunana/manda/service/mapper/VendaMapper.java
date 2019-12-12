package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.VendaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Venda} and its DTO {@link VendaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ClienteMapper.class, DocumentoComercialMapper.class, EmpresaMapper.class})
public interface VendaMapper extends EntityMapper<VendaDTO, Venda> {

    @Mapping(source = "vendedor.id", target = "vendedorId")
    @Mapping(source = "vendedor.login", target = "vendedorLogin")
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.numero", target = "clienteNumero")
    @Mapping(source = "tipoDocumento.id", target = "tipoDocumentoId")
    @Mapping(source = "tipoDocumento.nome", target = "tipoDocumentoNome")
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nome", target = "empresaNome")
    VendaDTO toDto(Venda venda);

    @Mapping(target = "itemVendas", ignore = true)
    @Mapping(target = "removeItemVenda", ignore = true)
    @Mapping(source = "vendedorId", target = "vendedor")
    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "tipoDocumentoId", target = "tipoDocumento")
    @Mapping(source = "empresaId", target = "empresa")
    Venda toEntity(VendaDTO vendaDTO);

    default Venda fromId(Long id) {
        if (id == null) {
            return null;
        }
        Venda venda = new Venda();
        venda.setId(id);
        return venda;
    }
}
