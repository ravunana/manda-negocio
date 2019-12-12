package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ContaCreditoMapperTest {

    private ContaCreditoMapper contaCreditoMapper;

    @BeforeEach
    public void setUp() {
        contaCreditoMapper = new ContaCreditoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(contaCreditoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contaCreditoMapper.fromId(null)).isNull();
    }
}
