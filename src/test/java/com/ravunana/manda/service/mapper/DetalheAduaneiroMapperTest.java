package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DetalheAduaneiroMapperTest {

    private DetalheAduaneiroMapper detalheAduaneiroMapper;

    @BeforeEach
    public void setUp() {
        detalheAduaneiroMapper = new DetalheAduaneiroMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(detalheAduaneiroMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(detalheAduaneiroMapper.fromId(null)).isNull();
    }
}
