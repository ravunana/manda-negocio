package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ContaDebitoMapperTest {

    private ContaDebitoMapper contaDebitoMapper;

    @BeforeEach
    public void setUp() {
        contaDebitoMapper = new ContaDebitoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(contaDebitoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contaDebitoMapper.fromId(null)).isNull();
    }
}
