package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class EstoqueConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstoqueConfig.class);
        EstoqueConfig estoqueConfig1 = new EstoqueConfig();
        estoqueConfig1.setId(1L);
        EstoqueConfig estoqueConfig2 = new EstoqueConfig();
        estoqueConfig2.setId(estoqueConfig1.getId());
        assertThat(estoqueConfig1).isEqualTo(estoqueConfig2);
        estoqueConfig2.setId(2L);
        assertThat(estoqueConfig1).isNotEqualTo(estoqueConfig2);
        estoqueConfig1.setId(null);
        assertThat(estoqueConfig1).isNotEqualTo(estoqueConfig2);
    }
}
