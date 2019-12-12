package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class VendaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venda.class);
        Venda venda1 = new Venda();
        venda1.setId(1L);
        Venda venda2 = new Venda();
        venda2.setId(venda1.getId());
        assertThat(venda1).isEqualTo(venda2);
        venda2.setId(2L);
        assertThat(venda1).isNotEqualTo(venda2);
        venda1.setId(null);
        assertThat(venda1).isNotEqualTo(venda2);
    }
}
