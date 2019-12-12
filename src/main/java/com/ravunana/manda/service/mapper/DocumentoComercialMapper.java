package com.ravunana.manda.service.mapper;

import com.ravunana.manda.domain.*;
import com.ravunana.manda.service.dto.DocumentoComercialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentoComercial} and its DTO {@link DocumentoComercialDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentoComercialMapper extends EntityMapper<DocumentoComercialDTO, DocumentoComercial> {


    @Mapping(target = "lancamentoFinanceiros", ignore = true)
    @Mapping(target = "removeLancamentoFinanceiro", ignore = true)
    @Mapping(target = "compras", ignore = true)
    @Mapping(target = "removeCompra", ignore = true)
    @Mapping(target = "vendas", ignore = true)
    @Mapping(target = "removeVenda", ignore = true)
    DocumentoComercial toEntity(DocumentoComercialDTO documentoComercialDTO);

    default DocumentoComercial fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentoComercial documentoComercial = new DocumentoComercial();
        documentoComercial.setId(id);
        return documentoComercial;
    }
}
