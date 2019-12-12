package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class MeioLiquidacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeioLiquidacaoDTO.class);
        MeioLiquidacaoDTO meioLiquidacaoDTO1 = new MeioLiquidacaoDTO();
        meioLiquidacaoDTO1.setId(1L);
        MeioLiquidacaoDTO meioLiquidacaoDTO2 = new MeioLiquidacaoDTO();
        assertThat(meioLiquidacaoDTO1).isNotEqualTo(meioLiquidacaoDTO2);
        meioLiquidacaoDTO2.setId(meioLiquidacaoDTO1.getId());
        assertThat(meioLiquidacaoDTO1).isEqualTo(meioLiquidacaoDTO2);
        meioLiquidacaoDTO2.setId(2L);
        assertThat(meioLiquidacaoDTO1).isNotEqualTo(meioLiquidacaoDTO2);
        meioLiquidacaoDTO1.setId(null);
        assertThat(meioLiquidacaoDTO1).isNotEqualTo(meioLiquidacaoDTO2);
    }
}
