package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DevolucaoCompraMapperTest {

    private DevolucaoCompraMapper devolucaoCompraMapper;

    @BeforeEach
    public void setUp() {
        devolucaoCompraMapper = new DevolucaoCompraMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(devolucaoCompraMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(devolucaoCompraMapper.fromId(null)).isNull();
    }
}
