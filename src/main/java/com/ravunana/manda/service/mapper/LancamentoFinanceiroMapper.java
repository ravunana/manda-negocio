package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.LancamentoFinanceiroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LancamentoFinanceiro} and its DTO {@link LancamentoFinanceiroDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ContaMapper.class, ImpostoMapper.class, FormaLiquidacaoMapper.class, EmpresaMapper.class, DocumentoComercialMapper.class})
public interface LancamentoFinanceiroMapper extends EntityMapper<LancamentoFinanceiroDTO, LancamentoFinanceiro> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "conta.id", target = "contaId")
    @Mapping(source = "conta.descricao", target = "contaDescricao")
    @Mapping(source = "formaLiquidacao.id", target = "formaLiquidacaoId")
    @Mapping(source = "formaLiquidacao.nome", target = "formaLiquidacaoNome")
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nome", target = "empresaNome")
    @Mapping(source = "tipoRecibo.id", target = "tipoReciboId")
    @Mapping(source = "tipoRecibo.nome", target = "tipoReciboNome")
    LancamentoFinanceiroDTO toDto(LancamentoFinanceiro lancamentoFinanceiro);

    @Mapping(target = "detalheLancamentos", ignore = true)
    @Mapping(target = "removeDetalheLancamento", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "contaId", target = "conta")
    @Mapping(target = "removeImposto", ignore = true)
    @Mapping(source = "formaLiquidacaoId", target = "formaLiquidacao")
    @Mapping(source = "empresaId", target = "empresa")
    @Mapping(source = "tipoReciboId", target = "tipoRecibo")
    LancamentoFinanceiro toEntity(LancamentoFinanceiroDTO lancamentoFinanceiroDTO);

    default LancamentoFinanceiro fromId(Long id) {
        if (id == null) {
            return null;
        }
        LancamentoFinanceiro lancamentoFinanceiro = new LancamentoFinanceiro();
        lancamentoFinanceiro.setId(id);
        return lancamentoFinanceiro;
    }
}
