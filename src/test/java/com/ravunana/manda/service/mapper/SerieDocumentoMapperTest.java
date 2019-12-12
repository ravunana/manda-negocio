package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SerieDocumentoMapperTest {

    private SerieDocumentoMapper serieDocumentoMapper;

    @BeforeEach
    public void setUp() {
        serieDocumentoMapper = new SerieDocumentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(serieDocumentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serieDocumentoMapper.fromId(null)).isNull();
    }
}
