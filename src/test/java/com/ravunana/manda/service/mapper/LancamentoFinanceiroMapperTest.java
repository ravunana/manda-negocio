package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class LancamentoFinanceiroMapperTest {

    private LancamentoFinanceiroMapper lancamentoFinanceiroMapper;

    @BeforeEach
    public void setUp() {
        lancamentoFinanceiroMapper = new LancamentoFinanceiroMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(lancamentoFinanceiroMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lancamentoFinanceiroMapper.fromId(null)).isNull();
    }
}
