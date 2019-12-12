package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CompostoProdutoMapperTest {

    private CompostoProdutoMapper compostoProdutoMapper;

    @BeforeEach
    public void setUp() {
        compostoProdutoMapper = new CompostoProdutoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(compostoProdutoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(compostoProdutoMapper.fromId(null)).isNull();
    }
}
