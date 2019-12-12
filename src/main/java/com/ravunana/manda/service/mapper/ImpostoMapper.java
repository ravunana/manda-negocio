package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.ImpostoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Imposto} and its DTO {@link ImpostoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContaMapper.class})
public interface ImpostoMapper extends EntityMapper<ImpostoDTO, Imposto> {

    @Mapping(source = "conta.id", target = "contaId")
    @Mapping(source = "conta.descricao", target = "contaDescricao")
    ImpostoDTO toDto(Imposto imposto);

    @Mapping(target = "grupoTributacaoImpostos", ignore = true)
    @Mapping(target = "removeGrupoTributacaoImposto", ignore = true)
    @Mapping(target = "retencaoFontes", ignore = true)
    @Mapping(target = "removeRetencaoFonte", ignore = true)
    @Mapping(source = "contaId", target = "conta")
    @Mapping(target = "lancamentos", ignore = true)
    @Mapping(target = "removeLancamento", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    @Mapping(target = "removeProduto", ignore = true)
    Imposto toEntity(ImpostoDTO impostoDTO);

    default Imposto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Imposto imposto = new Imposto();
        imposto.setId(id);
        return imposto;
    }
}
