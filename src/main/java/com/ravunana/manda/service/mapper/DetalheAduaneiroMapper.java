package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.DetalheAduaneiroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalheAduaneiro} and its DTO {@link DetalheAduaneiroDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface DetalheAduaneiroMapper extends EntityMapper<DetalheAduaneiroDTO, DetalheAduaneiro> {

    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
    DetalheAduaneiroDTO toDto(DetalheAduaneiro detalheAduaneiro);

    @Mapping(source = "produtoId", target = "produto")
    DetalheAduaneiro toEntity(DetalheAduaneiroDTO detalheAduaneiroDTO);

    default DetalheAduaneiro fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetalheAduaneiro detalheAduaneiro = new DetalheAduaneiro();
        detalheAduaneiro.setId(id);
        return detalheAduaneiro;
    }
}
