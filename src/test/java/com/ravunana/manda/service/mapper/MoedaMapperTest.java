package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MoedaMapperTest {

    private MoedaMapper moedaMapper;

    @BeforeEach
    public void setUp() {
        moedaMapper = new MoedaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(moedaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(moedaMapper.fromId(null)).isNull();
    }
}
