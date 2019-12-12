package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MeioLiquidacaoMapperTest {

    private MeioLiquidacaoMapper meioLiquidacaoMapper;

    @BeforeEach
    public void setUp() {
        meioLiquidacaoMapper = new MeioLiquidacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(meioLiquidacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(meioLiquidacaoMapper.fromId(null)).isNull();
    }
}
