package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class DetalheAduaneiroDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalheAduaneiroDTO.class);
        DetalheAduaneiroDTO detalheAduaneiroDTO1 = new DetalheAduaneiroDTO();
        detalheAduaneiroDTO1.setId(1L);
        DetalheAduaneiroDTO detalheAduaneiroDTO2 = new DetalheAduaneiroDTO();
        assertThat(detalheAduaneiroDTO1).isNotEqualTo(detalheAduaneiroDTO2);
        detalheAduaneiroDTO2.setId(detalheAduaneiroDTO1.getId());
        assertThat(detalheAduaneiroDTO1).isEqualTo(detalheAduaneiroDTO2);
        detalheAduaneiroDTO2.setId(2L);
        assertThat(detalheAduaneiroDTO1).isNotEqualTo(detalheAduaneiroDTO2);
        detalheAduaneiroDTO1.setId(null);
        assertThat(detalheAduaneiroDTO1).isNotEqualTo(detalheAduaneiroDTO2);
    }
}
