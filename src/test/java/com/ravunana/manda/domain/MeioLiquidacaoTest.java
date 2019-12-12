package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class MeioLiquidacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeioLiquidacao.class);
        MeioLiquidacao meioLiquidacao1 = new MeioLiquidacao();
        meioLiquidacao1.setId(1L);
        MeioLiquidacao meioLiquidacao2 = new MeioLiquidacao();
        meioLiquidacao2.setId(meioLiquidacao1.getId());
        assertThat(meioLiquidacao1).isEqualTo(meioLiquidacao2);
        meioLiquidacao2.setId(2L);
        assertThat(meioLiquidacao1).isNotEqualTo(meioLiquidacao2);
        meioLiquidacao1.setId(null);
        assertThat(meioLiquidacao1).isNotEqualTo(meioLiquidacao2);
    }
}
