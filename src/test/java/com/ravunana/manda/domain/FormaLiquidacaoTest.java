package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class FormaLiquidacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormaLiquidacao.class);
        FormaLiquidacao formaLiquidacao1 = new FormaLiquidacao();
        formaLiquidacao1.setId(1L);
        FormaLiquidacao formaLiquidacao2 = new FormaLiquidacao();
        formaLiquidacao2.setId(formaLiquidacao1.getId());
        assertThat(formaLiquidacao1).isEqualTo(formaLiquidacao2);
        formaLiquidacao2.setId(2L);
        assertThat(formaLiquidacao1).isNotEqualTo(formaLiquidacao2);
        formaLiquidacao1.setId(null);
        assertThat(formaLiquidacao1).isNotEqualTo(formaLiquidacao2);
    }
}
