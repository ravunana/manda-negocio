package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class RegraMovimentacaoDebitoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegraMovimentacaoDebitoDTO.class);
        RegraMovimentacaoDebitoDTO regraMovimentacaoDebitoDTO1 = new RegraMovimentacaoDebitoDTO();
        regraMovimentacaoDebitoDTO1.setId(1L);
        RegraMovimentacaoDebitoDTO regraMovimentacaoDebitoDTO2 = new RegraMovimentacaoDebitoDTO();
        assertThat(regraMovimentacaoDebitoDTO1).isNotEqualTo(regraMovimentacaoDebitoDTO2);
        regraMovimentacaoDebitoDTO2.setId(regraMovimentacaoDebitoDTO1.getId());
        assertThat(regraMovimentacaoDebitoDTO1).isEqualTo(regraMovimentacaoDebitoDTO2);
        regraMovimentacaoDebitoDTO2.setId(2L);
        assertThat(regraMovimentacaoDebitoDTO1).isNotEqualTo(regraMovimentacaoDebitoDTO2);
        regraMovimentacaoDebitoDTO1.setId(null);
        assertThat(regraMovimentacaoDebitoDTO1).isNotEqualTo(regraMovimentacaoDebitoDTO2);
    }
}
