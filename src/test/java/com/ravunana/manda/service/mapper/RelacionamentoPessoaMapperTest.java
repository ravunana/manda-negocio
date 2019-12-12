package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class RelacionamentoPessoaMapperTest {

    private RelacionamentoPessoaMapper relacionamentoPessoaMapper;

    @BeforeEach
    public void setUp() {
        relacionamentoPessoaMapper = new RelacionamentoPessoaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(relacionamentoPessoaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(relacionamentoPessoaMapper.fromId(null)).isNull();
    }
}
