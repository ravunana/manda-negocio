package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EstruturaCalculoMapperTest {

    private EstruturaCalculoMapper estruturaCalculoMapper;

    @BeforeEach
    public void setUp() {
        estruturaCalculoMapper = new EstruturaCalculoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(estruturaCalculoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(estruturaCalculoMapper.fromId(null)).isNull();
    }
}
