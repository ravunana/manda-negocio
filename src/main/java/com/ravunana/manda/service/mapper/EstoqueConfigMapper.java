package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.EstoqueConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstoqueConfig} and its DTO {@link EstoqueConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProdutoMapper.class, ContaMapper.class})
public interface EstoqueConfigMapper extends EntityMapper<EstoqueConfigDTO, EstoqueConfig> {

    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
    @Mapping(source = "contaVenda.id", target = "contaVendaId")
    @Mapping(source = "contaVenda.descricao", target = "contaVendaDescricao")
    @Mapping(source = "contaCompra.id", target = "contaCompraId")
    @Mapping(source = "contaCompra.descricao", target = "contaCompraDescricao")
    @Mapping(source = "contaImobilizado.id", target = "contaImobilizadoId")
    @Mapping(source = "contaImobilizado.descricao", target = "contaImobilizadoDescricao")
    @Mapping(source = "devolucaoCompra.id", target = "devolucaoCompraId")
    @Mapping(source = "devolucaoCompra.descricao", target = "devolucaoCompraDescricao")
    @Mapping(source = "devolucaoVenda.id", target = "devolucaoVendaId")
    @Mapping(source = "devolucaoVenda.descricao", target = "devolucaoVendaDescricao")
    EstoqueConfigDTO toDto(EstoqueConfig estoqueConfig);

    @Mapping(source = "produtoId", target = "produto")
    @Mapping(source = "contaVendaId", target = "contaVenda")
    @Mapping(source = "contaCompraId", target = "contaCompra")
    @Mapping(source = "contaImobilizadoId", target = "contaImobilizado")
    @Mapping(source = "devolucaoCompraId", target = "devolucaoCompra")
    @Mapping(source = "devolucaoVendaId", target = "devolucaoVenda")
    EstoqueConfig toEntity(EstoqueConfigDTO estoqueConfigDTO);

    default EstoqueConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstoqueConfig estoqueConfig = new EstoqueConfig();
        estoqueConfig.setId(id);
        return estoqueConfig;
    }
}
