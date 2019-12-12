package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ConversaoUnidadeMapperTest {

    private ConversaoUnidadeMapper conversaoUnidadeMapper;

    @BeforeEach
    public void setUp() {
        conversaoUnidadeMapper = new ConversaoUnidadeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(conversaoUnidadeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(conversaoUnidadeMapper.fromId(null)).isNull();
    }
}
