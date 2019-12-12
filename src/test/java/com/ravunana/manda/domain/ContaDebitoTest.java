package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ContaDebitoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContaDebito.class);
        ContaDebito contaDebito1 = new ContaDebito();
        contaDebito1.setId(1L);
        ContaDebito contaDebito2 = new ContaDebito();
        contaDebito2.setId(contaDebito1.getId());
        assertThat(contaDebito1).isEqualTo(contaDebito2);
        contaDebito2.setId(2L);
        assertThat(contaDebito1).isNotEqualTo(contaDebito2);
        contaDebito1.setId(null);
        assertThat(contaDebito1).isNotEqualTo(contaDebito2);
    }
}
