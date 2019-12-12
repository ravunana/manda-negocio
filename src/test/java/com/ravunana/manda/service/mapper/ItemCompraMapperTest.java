package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ItemCompraMapperTest {

    private ItemCompraMapper itemCompraMapper;

    @BeforeEach
    public void setUp() {
        itemCompraMapper = new ItemCompraMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(itemCompraMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(itemCompraMapper.fromId(null)).isNull();
    }
}
