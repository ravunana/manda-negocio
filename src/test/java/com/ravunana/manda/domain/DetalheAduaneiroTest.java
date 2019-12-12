package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class DetalheAduaneiroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalheAduaneiro.class);
        DetalheAduaneiro detalheAduaneiro1 = new DetalheAduaneiro();
        detalheAduaneiro1.setId(1L);
        DetalheAduaneiro detalheAduaneiro2 = new DetalheAduaneiro();
        detalheAduaneiro2.setId(detalheAduaneiro1.getId());
        assertThat(detalheAduaneiro1).isEqualTo(detalheAduaneiro2);
        detalheAduaneiro2.setId(2L);
        assertThat(detalheAduaneiro1).isNotEqualTo(detalheAduaneiro2);
        detalheAduaneiro1.setId(null);
        assertThat(detalheAduaneiro1).isNotEqualTo(detalheAduaneiro2);
    }
}
