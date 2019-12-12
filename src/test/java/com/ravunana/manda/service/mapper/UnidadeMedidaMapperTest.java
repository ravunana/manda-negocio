package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class UnidadeMedidaMapperTest {

    private UnidadeMedidaMapper unidadeMedidaMapper;

    @BeforeEach
    public void setUp() {
        unidadeMedidaMapper = new UnidadeMedidaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(unidadeMedidaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(unidadeMedidaMapper.fromId(null)).isNull();
    }
}
