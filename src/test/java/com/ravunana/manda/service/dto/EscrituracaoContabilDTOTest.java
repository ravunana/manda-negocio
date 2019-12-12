package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class EscrituracaoContabilDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EscrituracaoContabilDTO.class);
        EscrituracaoContabilDTO escrituracaoContabilDTO1 = new EscrituracaoContabilDTO();
        escrituracaoContabilDTO1.setId(1L);
        EscrituracaoContabilDTO escrituracaoContabilDTO2 = new EscrituracaoContabilDTO();
        assertThat(escrituracaoContabilDTO1).isNotEqualTo(escrituracaoContabilDTO2);
        escrituracaoContabilDTO2.setId(escrituracaoContabilDTO1.getId());
        assertThat(escrituracaoContabilDTO1).isEqualTo(escrituracaoContabilDTO2);
        escrituracaoContabilDTO2.setId(2L);
        assertThat(escrituracaoContabilDTO1).isNotEqualTo(escrituracaoContabilDTO2);
        escrituracaoContabilDTO1.setId(null);
        assertThat(escrituracaoContabilDTO1).isNotEqualTo(escrituracaoContabilDTO2);
    }
}
