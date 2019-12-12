package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AuditoriaCompraMapperTest {

    private AuditoriaCompraMapper auditoriaCompraMapper;

    @BeforeEach
    public void setUp() {
        auditoriaCompraMapper = new AuditoriaCompraMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(auditoriaCompraMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(auditoriaCompraMapper.fromId(null)).isNull();
    }
}
