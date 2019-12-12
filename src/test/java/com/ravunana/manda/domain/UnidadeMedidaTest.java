package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class UnidadeMedidaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeMedida.class);
        UnidadeMedida unidadeMedida1 = new UnidadeMedida();
        unidadeMedida1.setId(1L);
        UnidadeMedida unidadeMedida2 = new UnidadeMedida();
        unidadeMedida2.setId(unidadeMedida1.getId());
        assertThat(unidadeMedida1).isEqualTo(unidadeMedida2);
        unidadeMedida2.setId(2L);
        assertThat(unidadeMedida1).isNotEqualTo(unidadeMedida2);
        unidadeMedida1.setId(null);
        assertThat(unidadeMedida1).isNotEqualTo(unidadeMedida2);
    }
}
