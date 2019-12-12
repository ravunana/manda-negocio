package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class RegraMovimentacaoDebitoMapperTest {

    private RegraMovimentacaoDebitoMapper regraMovimentacaoDebitoMapper;

    @BeforeEach
    public void setUp() {
        regraMovimentacaoDebitoMapper = new RegraMovimentacaoDebitoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(regraMovimentacaoDebitoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(regraMovimentacaoDebitoMapper.fromId(null)).isNull();
    }
}
