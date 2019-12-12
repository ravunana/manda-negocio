package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EscrituracaoContabilMapperTest {

    private EscrituracaoContabilMapper escrituracaoContabilMapper;

    @BeforeEach
    public void setUp() {
        escrituracaoContabilMapper = new EscrituracaoContabilMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(escrituracaoContabilMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(escrituracaoContabilMapper.fromId(null)).isNull();
    }
}
