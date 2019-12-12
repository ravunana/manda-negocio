package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProdutoMapperTest {

    private ProdutoMapper produtoMapper;

    @BeforeEach
    public void setUp() {
        produtoMapper = new ProdutoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(produtoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(produtoMapper.fromId(null)).isNull();
    }
}
