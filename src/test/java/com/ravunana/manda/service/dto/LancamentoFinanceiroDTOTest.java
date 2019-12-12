package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class LancamentoFinanceiroDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LancamentoFinanceiroDTO.class);
        LancamentoFinanceiroDTO lancamentoFinanceiroDTO1 = new LancamentoFinanceiroDTO();
        lancamentoFinanceiroDTO1.setId(1L);
        LancamentoFinanceiroDTO lancamentoFinanceiroDTO2 = new LancamentoFinanceiroDTO();
        assertThat(lancamentoFinanceiroDTO1).isNotEqualTo(lancamentoFinanceiroDTO2);
        lancamentoFinanceiroDTO2.setId(lancamentoFinanceiroDTO1.getId());
        assertThat(lancamentoFinanceiroDTO1).isEqualTo(lancamentoFinanceiroDTO2);
        lancamentoFinanceiroDTO2.setId(2L);
        assertThat(lancamentoFinanceiroDTO1).isNotEqualTo(lancamentoFinanceiroDTO2);
        lancamentoFinanceiroDTO1.setId(null);
        assertThat(lancamentoFinanceiroDTO1).isNotEqualTo(lancamentoFinanceiroDTO2);
    }
}
