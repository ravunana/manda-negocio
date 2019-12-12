package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EstoqueConfigMapperTest {

    private EstoqueConfigMapper estoqueConfigMapper;

    @BeforeEach
    public void setUp() {
        estoqueConfigMapper = new EstoqueConfigMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(estoqueConfigMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(estoqueConfigMapper.fromId(null)).isNull();
    }
}
