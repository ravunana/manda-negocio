package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class RetencaoFonteMapperTest {

    private RetencaoFonteMapper retencaoFonteMapper;

    @BeforeEach
    public void setUp() {
        retencaoFonteMapper = new RetencaoFonteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(retencaoFonteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(retencaoFonteMapper.fromId(null)).isNull();
    }
}
