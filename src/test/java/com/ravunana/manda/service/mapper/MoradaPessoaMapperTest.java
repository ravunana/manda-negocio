package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MoradaPessoaMapperTest {

    private MoradaPessoaMapper moradaPessoaMapper;

    @BeforeEach
    public void setUp() {
        moradaPessoaMapper = new MoradaPessoaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(moradaPessoaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(moradaPessoaMapper.fromId(null)).isNull();
    }
}
