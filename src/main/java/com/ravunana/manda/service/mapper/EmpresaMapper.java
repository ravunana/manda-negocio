package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.EmpresaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empresa} and its DTO {@link EmpresaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ContaMapper.class})
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "conta.id", target = "contaId")
    @Mapping(source = "conta.descricao", target = "contaDescricao")
    @Mapping(source = "hierarquia.id", target = "hierarquiaId")
    @Mapping(source = "hierarquia.nome", target = "hierarquiaNome")
    EmpresaDTO toDto(Empresa empresa);

    @Mapping(target = "escrituracaoContabils", ignore = true)
    @Mapping(target = "removeEscrituracaoContabil", ignore = true)
    @Mapping(target = "empresas", ignore = true)
    @Mapping(target = "removeEmpresa", ignore = true)
    @Mapping(target = "localizacaoEmpresas", ignore = true)
    @Mapping(target = "removeLocalizacaoEmpresa", ignore = true)
    @Mapping(target = "contactoEmpresas", ignore = true)
    @Mapping(target = "removeContactoEmpresa", ignore = true)
    @Mapping(target = "licencaSoftwares", ignore = true)
    @Mapping(target = "removeLicencaSoftware", ignore = true)
    @Mapping(target = "lancamentoFinanceiros", ignore = true)
    @Mapping(target = "removeLancamentoFinanceiro", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    @Mapping(target = "removeProduto", ignore = true)
    @Mapping(target = "localArmazenamentos", ignore = true)
    @Mapping(target = "removeLocalArmazenamento", ignore = true)
    @Mapping(target = "compras", ignore = true)
    @Mapping(target = "removeCompra", ignore = true)
    @Mapping(target = "vendas", ignore = true)
    @Mapping(target = "removeVenda", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "contaId", target = "conta")
    @Mapping(source = "hierarquiaId", target = "hierarquia")
    @Mapping(target = "contas", ignore = true)
    @Mapping(target = "removeConta", ignore = true)
    @Mapping(target = "coordenadaBancarias", ignore = true)
    @Mapping(target = "removeCoordenadaBancaria", ignore = true)
    Empresa toEntity(EmpresaDTO empresaDTO);

    default Empresa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Empresa empresa = new Empresa();
        empresa.setId(id);
        return empresa;
    }
}
