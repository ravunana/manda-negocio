package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class EscrituracaoContabilTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EscrituracaoContabil.class);
        EscrituracaoContabil escrituracaoContabil1 = new EscrituracaoContabil();
        escrituracaoContabil1.setId(1L);
        EscrituracaoContabil escrituracaoContabil2 = new EscrituracaoContabil();
        escrituracaoContabil2.setId(escrituracaoContabil1.getId());
        assertThat(escrituracaoContabil1).isEqualTo(escrituracaoContabil2);
        escrituracaoContabil2.setId(2L);
        assertThat(escrituracaoContabil1).isNotEqualTo(escrituracaoContabil2);
        escrituracaoContabil1.setId(null);
        assertThat(escrituracaoContabil1).isNotEqualTo(escrituracaoContabil2);
    }
}
