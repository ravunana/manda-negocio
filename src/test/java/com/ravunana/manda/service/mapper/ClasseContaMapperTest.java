package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ClasseContaMapperTest {

    private ClasseContaMapper classeContaMapper;

    @BeforeEach
    public void setUp() {
        classeContaMapper = new ClasseContaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(classeContaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(classeContaMapper.fromId(null)).isNull();
    }
}
