package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AuditoriaVendaMapperTest {

    private AuditoriaVendaMapper auditoriaVendaMapper;

    @BeforeEach
    public void setUp() {
        auditoriaVendaMapper = new AuditoriaVendaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(auditoriaVendaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(auditoriaVendaMapper.fromId(null)).isNull();
    }
}
