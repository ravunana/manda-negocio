package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class FormaLiquidacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormaLiquidacaoDTO.class);
        FormaLiquidacaoDTO formaLiquidacaoDTO1 = new FormaLiquidacaoDTO();
        formaLiquidacaoDTO1.setId(1L);
        FormaLiquidacaoDTO formaLiquidacaoDTO2 = new FormaLiquidacaoDTO();
        assertThat(formaLiquidacaoDTO1).isNotEqualTo(formaLiquidacaoDTO2);
        formaLiquidacaoDTO2.setId(formaLiquidacaoDTO1.getId());
        assertThat(formaLiquidacaoDTO1).isEqualTo(formaLiquidacaoDTO2);
        formaLiquidacaoDTO2.setId(2L);
        assertThat(formaLiquidacaoDTO1).isNotEqualTo(formaLiquidacaoDTO2);
        formaLiquidacaoDTO1.setId(null);
        assertThat(formaLiquidacaoDTO1).isNotEqualTo(formaLiquidacaoDTO2);
    }
}
