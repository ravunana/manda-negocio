package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class FamiliaProdutoMapperTest {

    private FamiliaProdutoMapper familiaProdutoMapper;

    @BeforeEach
    public void setUp() {
        familiaProdutoMapper = new FamiliaProdutoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(familiaProdutoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(familiaProdutoMapper.fromId(null)).isNull();
    }
}
