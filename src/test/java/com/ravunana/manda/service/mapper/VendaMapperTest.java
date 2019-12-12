package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class VendaMapperTest {

    private VendaMapper vendaMapper;

    @BeforeEach
    public void setUp() {
        vendaMapper = new VendaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(vendaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(vendaMapper.fromId(null)).isNull();
    }
}
