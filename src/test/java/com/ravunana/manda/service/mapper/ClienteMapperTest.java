package com.ravunana.manda.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ClienteMapperTest {

    private ClienteMapper clienteMapper;

    @BeforeEach
    public void setUp() {
        clienteMapper = new ClienteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(clienteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(clienteMapper.fromId(null)).isNull();
    }
}
