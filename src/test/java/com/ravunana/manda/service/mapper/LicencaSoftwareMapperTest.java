package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class LicencaSoftwareMapperTest {

    private LicencaSoftwareMapper licencaSoftwareMapper;

    @BeforeEach
    public void setUp() {
        licencaSoftwareMapper = new LicencaSoftwareMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(licencaSoftwareMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(licencaSoftwareMapper.fromId(null)).isNull();
    }
}
