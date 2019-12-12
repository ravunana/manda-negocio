package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ItemVendaMapperTest {

    private ItemVendaMapper itemVendaMapper;

    @BeforeEach
    public void setUp() {
        itemVendaMapper = new ItemVendaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(itemVendaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(itemVendaMapper.fromId(null)).isNull();
    }
}
