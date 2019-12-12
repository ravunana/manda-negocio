package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class CoordenadaBancariaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoordenadaBancaria.class);
        CoordenadaBancaria coordenadaBancaria1 = new CoordenadaBancaria();
        coordenadaBancaria1.setId(1L);
        CoordenadaBancaria coordenadaBancaria2 = new CoordenadaBancaria();
        coordenadaBancaria2.setId(coordenadaBancaria1.getId());
        assertThat(coordenadaBancaria1).isEqualTo(coordenadaBancaria2);
        coordenadaBancaria2.setId(2L);
        assertThat(coordenadaBancaria1).isNotEqualTo(coordenadaBancaria2);
        coordenadaBancaria1.setId(null);
        assertThat(coordenadaBancaria1).isNotEqualTo(coordenadaBancaria2);
    }
}
