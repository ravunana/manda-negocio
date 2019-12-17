package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ItemCompraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ItemCompra} and its DTO {@link ItemCompraDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompraMapper.class, ProdutoMapper.class, FornecedorMapper.class, FluxoDocumentoMapper.class})
public interface ItemCompraMapper extends EntityMapper<ItemCompraDTO, ItemCompra> {

    @Mapping(source = "compra.id", target = "compraId")
    @Mapping(source = "compra.numero", target = "compraNumero")
    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
    @Mapping(source = "fornecedor.id", target = "fornecedorId")
    @Mapping(source = "fornecedor.numero", target = "fornecedorNumero")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.nome", target = "statusNome")
    ItemCompraDTO toDto(ItemCompra itemCompra);

    @Mapping(source = "compraId", target = "compra")
    @Mapping(source = "produtoId", target = "produto")
    @Mapping(source = "fornecedorId", target = "fornecedor")
    @Mapping(source = "statusId", target = "status")
    ItemCompra toEntity(ItemCompraDTO itemCompraDTO);

    default ItemCompra fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemCompra itemCompra = new ItemCompra();
        itemCompra.setId(id);
        return itemCompra;
    }
}
