package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ImpostoMapperTest {

    private ImpostoMapper impostoMapper;

    @BeforeEach
    public void setUp() {
        impostoMapper = new ImpostoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(impostoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(impostoMapper.fromId(null)).isNull();
    }
}
