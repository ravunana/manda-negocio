package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SoftwareMapperTest {

    private SoftwareMapper softwareMapper;

    @BeforeEach
    public void setUp() {
        softwareMapper = new SoftwareMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(softwareMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(softwareMapper.fromId(null)).isNull();
    }
}
