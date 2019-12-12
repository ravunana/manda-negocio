package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class LocalizacaoEmpresaMapperTest {

    private LocalizacaoEmpresaMapper localizacaoEmpresaMapper;

    @BeforeEach
    public void setUp() {
        localizacaoEmpresaMapper = new LocalizacaoEmpresaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(localizacaoEmpresaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(localizacaoEmpresaMapper.fromId(null)).isNull();
    }
}
