package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ContaCreditoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContaCredito.class);
        ContaCredito contaCredito1 = new ContaCredito();
        contaCredito1.setId(1L);
        ContaCredito contaCredito2 = new ContaCredito();
        contaCredito2.setId(contaCredito1.getId());
        assertThat(contaCredito1).isEqualTo(contaCredito2);
        contaCredito2.setId(2L);
        assertThat(contaCredito1).isNotEqualTo(contaCredito2);
        contaCredito1.setId(null);
        assertThat(contaCredito1).isNotEqualTo(contaCredito2);
    }
}
