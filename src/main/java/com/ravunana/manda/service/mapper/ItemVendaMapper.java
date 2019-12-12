package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ItemVendaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ItemVenda} and its DTO {@link ItemVendaDTO}.
 */
@Mapper(componentModel = "spring", uses = {VendaMapper.class, ProdutoMapper.class, FluxoDocumentoMapper.class})
public interface ItemVendaMapper extends EntityMapper<ItemVendaDTO, ItemVenda> {

    @Mapping(source = "venda.id", target = "vendaId")
    @Mapping(source = "venda.numero", target = "vendaNumero")
    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.nome", target = "statusNome")
    ItemVendaDTO toDto(ItemVenda itemVenda);

    @Mapping(source = "vendaId", target = "venda")
    @Mapping(source = "produtoId", target = "produto")
    @Mapping(source = "statusId", target = "status")
    ItemVenda toEntity(ItemVendaDTO itemVendaDTO);

    default ItemVenda fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setId(id);
        return itemVenda;
    }
}
