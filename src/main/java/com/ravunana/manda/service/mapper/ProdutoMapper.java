package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ProdutoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produto} and its DTO {@link ProdutoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ImpostoMapper.class, FornecedorMapper.class, LocalArmazenamentoMapper.class, FamiliaProdutoMapper.class, EmpresaMapper.class, FluxoDocumentoMapper.class})
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "localArmazenamento.id", target = "localArmazenamentoId")
    @Mapping(source = "localArmazenamento.nome", target = "localArmazenamentoNome")
    @Mapping(source = "familia.id", target = "familiaId")
    @Mapping(source = "familia.nome", target = "familiaNome")
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nome", target = "empresaNome")
    @Mapping(source = "estado.id", target = "estadoId")
    @Mapping(source = "estado.nome", target = "estadoNome")
    ProdutoDTO toDto(Produto produto);

    @Mapping(target = "compostoProdutos", ignore = true)
    @Mapping(target = "removeCompostoProduto", ignore = true)
    @Mapping(target = "conversaoUnidades", ignore = true)
    @Mapping(target = "removeConversaoUnidade", ignore = true)
    @Mapping(target = "estruturaCalculos", ignore = true)
    @Mapping(target = "removeEstruturaCalculo", ignore = true)
    @Mapping(target = "itemCompras", ignore = true)
    @Mapping(target = "removeItemCompra", ignore = true)
    @Mapping(target = "itemVendas", ignore = true)
    @Mapping(target = "removeItemVenda", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(target = "removeImposto", ignore = true)
    @Mapping(target = "removeFornecedor", ignore = true)
    @Mapping(source = "localArmazenamentoId", target = "localArmazenamento")
    @Mapping(source = "familiaId", target = "familia")
    @Mapping(source = "empresaId", target = "empresa")
    @Mapping(source = "estadoId", target = "estado")
    Produto toEntity(ProdutoDTO produtoDTO);

    default Produto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Produto produto = new Produto();
        produto.setId(id);
        return produto;
    }
}
