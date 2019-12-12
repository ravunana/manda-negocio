package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class EstruturaCalculoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstruturaCalculo.class);
        EstruturaCalculo estruturaCalculo1 = new EstruturaCalculo();
        estruturaCalculo1.setId(1L);
        EstruturaCalculo estruturaCalculo2 = new EstruturaCalculo();
        estruturaCalculo2.setId(estruturaCalculo1.getId());
        assertThat(estruturaCalculo1).isEqualTo(estruturaCalculo2);
        estruturaCalculo2.setId(2L);
        assertThat(estruturaCalculo1).isNotEqualTo(estruturaCalculo2);
        estruturaCalculo1.setId(null);
        assertThat(estruturaCalculo1).isNotEqualTo(estruturaCalculo2);
    }
}
