package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class LookupItemMapperTest {

    private LookupItemMapper lookupItemMapper;

    @BeforeEach
    public void setUp() {
        lookupItemMapper = new LookupItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(lookupItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lookupItemMapper.fromId(null)).isNull();
    }
}
