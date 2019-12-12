package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class RegraMovimentacaoCreditoMapperTest {

    private RegraMovimentacaoCreditoMapper regraMovimentacaoCreditoMapper;

    @BeforeEach
    public void setUp() {
        regraMovimentacaoCreditoMapper = new RegraMovimentacaoCreditoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(regraMovimentacaoCreditoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(regraMovimentacaoCreditoMapper.fromId(null)).isNull();
    }
}
