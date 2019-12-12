package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ContaMapperTest {

    private ContaMapper contaMapper;

    @BeforeEach
    public void setUp() {
        contaMapper = new ContaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(contaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contaMapper.fromId(null)).isNull();
    }
}
