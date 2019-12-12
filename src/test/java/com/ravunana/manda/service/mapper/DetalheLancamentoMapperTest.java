package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DetalheLancamentoMapperTest {

    private DetalheLancamentoMapper detalheLancamentoMapper;

    @BeforeEach
    public void setUp() {
        detalheLancamentoMapper = new DetalheLancamentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(detalheLancamentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(detalheLancamentoMapper.fromId(null)).isNull();
    }
}
