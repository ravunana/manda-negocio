package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class GrupoTributacaoImpostoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoTributacaoImposto.class);
        GrupoTributacaoImposto grupoTributacaoImposto1 = new GrupoTributacaoImposto();
        grupoTributacaoImposto1.setId(1L);
        GrupoTributacaoImposto grupoTributacaoImposto2 = new GrupoTributacaoImposto();
        grupoTributacaoImposto2.setId(grupoTributacaoImposto1.getId());
        assertThat(grupoTributacaoImposto1).isEqualTo(grupoTributacaoImposto2);
        grupoTributacaoImposto2.setId(2L);
        assertThat(grupoTributacaoImposto1).isNotEqualTo(grupoTributacaoImposto2);
        grupoTributacaoImposto1.setId(null);
        assertThat(grupoTributacaoImposto1).isNotEqualTo(grupoTributacaoImposto2);
    }
}
