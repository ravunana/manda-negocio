package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ContactoEmpresaMapperTest {

    private ContactoEmpresaMapper contactoEmpresaMapper;

    @BeforeEach
    public void setUp() {
        contactoEmpresaMapper = new ContactoEmpresaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(contactoEmpresaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contactoEmpresaMapper.fromId(null)).isNull();
    }
}
