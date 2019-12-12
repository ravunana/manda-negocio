package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ContactoPessoaMapperTest {

    private ContactoPessoaMapper contactoPessoaMapper;

    @BeforeEach
    public void setUp() {
        contactoPessoaMapper = new ContactoPessoaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(contactoPessoaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contactoPessoaMapper.fromId(null)).isNull();
    }
}
