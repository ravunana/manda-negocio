package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CoordenadaBancariaMapperTest {

    private CoordenadaBancariaMapper coordenadaBancariaMapper;

    @BeforeEach
    public void setUp() {
        coordenadaBancariaMapper = new CoordenadaBancariaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(coordenadaBancariaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(coordenadaBancariaMapper.fromId(null)).isNull();
    }
}
