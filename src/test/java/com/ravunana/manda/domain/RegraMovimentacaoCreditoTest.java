package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class RegraMovimentacaoCreditoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegraMovimentacaoCredito.class);
        RegraMovimentacaoCredito regraMovimentacaoCredito1 = new RegraMovimentacaoCredito();
        regraMovimentacaoCredito1.setId(1L);
        RegraMovimentacaoCredito regraMovimentacaoCredito2 = new RegraMovimentacaoCredito();
        regraMovimentacaoCredito2.setId(regraMovimentacaoCredito1.getId());
        assertThat(regraMovimentacaoCredito1).isEqualTo(regraMovimentacaoCredito2);
        regraMovimentacaoCredito2.setId(2L);
        assertThat(regraMovimentacaoCredito1).isNotEqualTo(regraMovimentacaoCredito2);
        regraMovimentacaoCredito1.setId(null);
        assertThat(regraMovimentacaoCredito1).isNotEqualTo(regraMovimentacaoCredito2);
    }
}
