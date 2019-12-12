package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ContaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContaDTO.class);
        ContaDTO contaDTO1 = new ContaDTO();
        contaDTO1.setId(1L);
        ContaDTO contaDTO2 = new ContaDTO();
        assertThat(contaDTO1).isNotEqualTo(contaDTO2);
        contaDTO2.setId(contaDTO1.getId());
        assertThat(contaDTO1).isEqualTo(contaDTO2);
        contaDTO2.setId(2L);
        assertThat(contaDTO1).isNotEqualTo(contaDTO2);
        contaDTO1.setId(null);
        assertThat(contaDTO1).isNotEqualTo(contaDTO2);
    }
}
