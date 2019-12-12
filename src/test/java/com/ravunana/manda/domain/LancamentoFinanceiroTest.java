package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class LancamentoFinanceiroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LancamentoFinanceiro.class);
        LancamentoFinanceiro lancamentoFinanceiro1 = new LancamentoFinanceiro();
        lancamentoFinanceiro1.setId(1L);
        LancamentoFinanceiro lancamentoFinanceiro2 = new LancamentoFinanceiro();
        lancamentoFinanceiro2.setId(lancamentoFinanceiro1.getId());
        assertThat(lancamentoFinanceiro1).isEqualTo(lancamentoFinanceiro2);
        lancamentoFinanceiro2.setId(2L);
        assertThat(lancamentoFinanceiro1).isNotEqualTo(lancamentoFinanceiro2);
        lancamentoFinanceiro1.setId(null);
        assertThat(lancamentoFinanceiro1).isNotEqualTo(lancamentoFinanceiro2);
    }
}
