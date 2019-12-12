package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class FormaLiquidacaoMapperTest {

    private FormaLiquidacaoMapper formaLiquidacaoMapper;

    @BeforeEach
    public void setUp() {
        formaLiquidacaoMapper = new FormaLiquidacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(formaLiquidacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(formaLiquidacaoMapper.fromId(null)).isNull();
    }
}
