package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class MoedaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Moeda.class);
        Moeda moeda1 = new Moeda();
        moeda1.setId(1L);
        Moeda moeda2 = new Moeda();
        moeda2.setId(moeda1.getId());
        assertThat(moeda1).isEqualTo(moeda2);
        moeda2.setId(2L);
        assertThat(moeda1).isNotEqualTo(moeda2);
        moeda1.setId(null);
        assertThat(moeda1).isNotEqualTo(moeda2);
    }
}
