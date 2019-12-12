package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class GrupoTributacaoImpostoMapperTest {

    private GrupoTributacaoImpostoMapper grupoTributacaoImpostoMapper;

    @BeforeEach
    public void setUp() {
        grupoTributacaoImpostoMapper = new GrupoTributacaoImpostoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(grupoTributacaoImpostoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(grupoTributacaoImpostoMapper.fromId(null)).isNull();
    }
}
