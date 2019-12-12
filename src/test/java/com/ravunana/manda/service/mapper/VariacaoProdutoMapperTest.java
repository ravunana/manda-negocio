package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class VariacaoProdutoMapperTest {

    private VariacaoProdutoMapper variacaoProdutoMapper;

    @BeforeEach
    public void setUp() {
        variacaoProdutoMapper = new VariacaoProdutoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(variacaoProdutoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(variacaoProdutoMapper.fromId(null)).isNull();
    }
}
