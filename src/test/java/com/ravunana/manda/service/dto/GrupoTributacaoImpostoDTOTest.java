package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class GrupoTributacaoImpostoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoTributacaoImpostoDTO.class);
        GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO1 = new GrupoTributacaoImpostoDTO();
        grupoTributacaoImpostoDTO1.setId(1L);
        GrupoTributacaoImpostoDTO grupoTributacaoImpostoDTO2 = new GrupoTributacaoImpostoDTO();
        assertThat(grupoTributacaoImpostoDTO1).isNotEqualTo(grupoTributacaoImpostoDTO2);
        grupoTributacaoImpostoDTO2.setId(grupoTributacaoImpostoDTO1.getId());
        assertThat(grupoTributacaoImpostoDTO1).isEqualTo(grupoTributacaoImpostoDTO2);
        grupoTributacaoImpostoDTO2.setId(2L);
        assertThat(grupoTributacaoImpostoDTO1).isNotEqualTo(grupoTributacaoImpostoDTO2);
        grupoTributacaoImpostoDTO1.setId(null);
        assertThat(grupoTributacaoImpostoDTO1).isNotEqualTo(grupoTributacaoImpostoDTO2);
    }
}
