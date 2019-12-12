package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class LookupMapperTest {

    private LookupMapper lookupMapper;

    @BeforeEach
    public void setUp() {
        lookupMapper = new LookupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(lookupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lookupMapper.fromId(null)).isNull();
    }
}
