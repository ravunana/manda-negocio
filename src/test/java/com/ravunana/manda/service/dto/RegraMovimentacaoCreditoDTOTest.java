package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class RegraMovimentacaoCreditoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegraMovimentacaoCreditoDTO.class);
        RegraMovimentacaoCreditoDTO regraMovimentacaoCreditoDTO1 = new RegraMovimentacaoCreditoDTO();
        regraMovimentacaoCreditoDTO1.setId(1L);
        RegraMovimentacaoCreditoDTO regraMovimentacaoCreditoDTO2 = new RegraMovimentacaoCreditoDTO();
        assertThat(regraMovimentacaoCreditoDTO1).isNotEqualTo(regraMovimentacaoCreditoDTO2);
        regraMovimentacaoCreditoDTO2.setId(regraMovimentacaoCreditoDTO1.getId());
        assertThat(regraMovimentacaoCreditoDTO1).isEqualTo(regraMovimentacaoCreditoDTO2);
        regraMovimentacaoCreditoDTO2.setId(2L);
        assertThat(regraMovimentacaoCreditoDTO1).isNotEqualTo(regraMovimentacaoCreditoDTO2);
        regraMovimentacaoCreditoDTO1.setId(null);
        assertThat(regraMovimentacaoCreditoDTO1).isNotEqualTo(regraMovimentacaoCreditoDTO2);
    }
}
