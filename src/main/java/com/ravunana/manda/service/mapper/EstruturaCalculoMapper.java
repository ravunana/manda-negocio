package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.EstruturaCalculoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstruturaCalculo} and its DTO {@link EstruturaCalculoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProdutoMapper.class})
public interface EstruturaCalculoMapper extends EntityMapper<EstruturaCalculoDTO, EstruturaCalculo> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "produtoNome")
    EstruturaCalculoDTO toDto(EstruturaCalculo estruturaCalculo);

    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "produtoId", target = "produto")
    EstruturaCalculo toEntity(EstruturaCalculoDTO estruturaCalculoDTO);

    default EstruturaCalculo fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstruturaCalculo estruturaCalculo = new EstruturaCalculo();
        estruturaCalculo.setId(id);
        return estruturaCalculo;
    }
}
