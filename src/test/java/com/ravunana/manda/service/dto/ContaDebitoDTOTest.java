package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ContaDebitoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContaDebitoDTO.class);
        ContaDebitoDTO contaDebitoDTO1 = new ContaDebitoDTO();
        contaDebitoDTO1.setId(1L);
        ContaDebitoDTO contaDebitoDTO2 = new ContaDebitoDTO();
        assertThat(contaDebitoDTO1).isNotEqualTo(contaDebitoDTO2);
        contaDebitoDTO2.setId(contaDebitoDTO1.getId());
        assertThat(contaDebitoDTO1).isEqualTo(contaDebitoDTO2);
        contaDebitoDTO2.setId(2L);
        assertThat(contaDebitoDTO1).isNotEqualTo(contaDebitoDTO2);
        contaDebitoDTO1.setId(null);
        assertThat(contaDebitoDTO1).isNotEqualTo(contaDebitoDTO2);
    }
}
