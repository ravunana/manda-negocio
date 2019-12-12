package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DevolucaoVendaMapperTest {

    private DevolucaoVendaMapper devolucaoVendaMapper;

    @BeforeEach
    public void setUp() {
        devolucaoVendaMapper = new DevolucaoVendaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(devolucaoVendaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(devolucaoVendaMapper.fromId(null)).isNull();
    }
}
