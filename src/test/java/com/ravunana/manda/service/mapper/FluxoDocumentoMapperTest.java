package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class FluxoDocumentoMapperTest {

    private FluxoDocumentoMapper fluxoDocumentoMapper;

    @BeforeEach
    public void setUp() {
        fluxoDocumentoMapper = new FluxoDocumentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(fluxoDocumentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fluxoDocumentoMapper.fromId(null)).isNull();
    }
}
