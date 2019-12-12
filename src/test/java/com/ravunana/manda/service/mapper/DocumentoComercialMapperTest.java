package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DocumentoComercialMapperTest {

    private DocumentoComercialMapper documentoComercialMapper;

    @BeforeEach
    public void setUp() {
        documentoComercialMapper = new DocumentoComercialMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(documentoComercialMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(documentoComercialMapper.fromId(null)).isNull();
    }
}
