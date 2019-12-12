package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class RegraMovimentacaoDebitoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegraMovimentacaoDebito.class);
        RegraMovimentacaoDebito regraMovimentacaoDebito1 = new RegraMovimentacaoDebito();
        regraMovimentacaoDebito1.setId(1L);
        RegraMovimentacaoDebito regraMovimentacaoDebito2 = new RegraMovimentacaoDebito();
        regraMovimentacaoDebito2.setId(regraMovimentacaoDebito1.getId());
        assertThat(regraMovimentacaoDebito1).isEqualTo(regraMovimentacaoDebito2);
        regraMovimentacaoDebito2.setId(2L);
        assertThat(regraMovimentacaoDebito1).isNotEqualTo(regraMovimentacaoDebito2);
        regraMovimentacaoDebito1.setId(null);
        assertThat(regraMovimentacaoDebito1).isNotEqualTo(regraMovimentacaoDebito2);
    }
}
