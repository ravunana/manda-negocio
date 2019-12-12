package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ArquivoMapperTest {

    private ArquivoMapper arquivoMapper;

    @BeforeEach
    public void setUp() {
        arquivoMapper = new ArquivoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(arquivoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(arquivoMapper.fromId(null)).isNull();
    }
}
