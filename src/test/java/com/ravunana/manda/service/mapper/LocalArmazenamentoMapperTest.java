package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class LocalArmazenamentoMapperTest {

    private LocalArmazenamentoMapper localArmazenamentoMapper;

    @BeforeEach
    public void setUp() {
        localArmazenamentoMapper = new LocalArmazenamentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(localArmazenamentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(localArmazenamentoMapper.fromId(null)).isNull();
    }
}
