package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.DetalheLancamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalheLancamento} and its DTO {@link DetalheLancamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, LancamentoFinanceiroMapper.class, MeioLiquidacaoMapper.class, MoedaMapper.class, CoordenadaBancariaMapper.class})
public interface DetalheLancamentoMapper extends EntityMapper<DetalheLancamentoDTO, DetalheLancamento> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "lancamentoFinanceiro.id", target = "lancamentoFinanceiroId")
    @Mapping(source = "lancamentoFinanceiro.numero", target = "lancamentoFinanceiroNumero")
    @Mapping(source = "metodoLiquidacao.id", target = "metodoLiquidacaoId")
    @Mapping(source = "metodoLiquidacao.nome", target = "metodoLiquidacaoNome")
    @Mapping(source = "moeda.id", target = "moedaId")
    @Mapping(source = "moeda.codigo", target = "moedaCodigo")
    @Mapping(source = "coordenada.id", target = "coordenadaId")
    @Mapping(source = "coordenada.descricao", target = "coordenadaDescricao")
    DetalheLancamentoDTO toDto(DetalheLancamento detalheLancamento);

    @Mapping(target = "retencaoFontes", ignore = true)
    @Mapping(target = "removeRetencaoFonte", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "lancamentoFinanceiroId", target = "lancamentoFinanceiro")
    @Mapping(source = "metodoLiquidacaoId", target = "metodoLiquidacao")
    @Mapping(source = "moedaId", target = "moeda")
    @Mapping(source = "coordenadaId", target = "coordenada")
    DetalheLancamento toEntity(DetalheLancamentoDTO detalheLancamentoDTO);

    default DetalheLancamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetalheLancamento detalheLancamento = new DetalheLancamento();
        detalheLancamento.setId(id);
        return detalheLancamento;
    }
}
