package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class DetalheLancamentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalheLancamentoDTO.class);
        DetalheLancamentoDTO detalheLancamentoDTO1 = new DetalheLancamentoDTO();
        detalheLancamentoDTO1.setId(1L);
        DetalheLancamentoDTO detalheLancamentoDTO2 = new DetalheLancamentoDTO();
        assertThat(detalheLancamentoDTO1).isNotEqualTo(detalheLancamentoDTO2);
        detalheLancamentoDTO2.setId(detalheLancamentoDTO1.getId());
        assertThat(detalheLancamentoDTO1).isEqualTo(detalheLancamentoDTO2);
        detalheLancamentoDTO2.setId(2L);
        assertThat(detalheLancamentoDTO1).isNotEqualTo(detalheLancamentoDTO2);
        detalheLancamentoDTO1.setId(null);
        assertThat(detalheLancamentoDTO1).isNotEqualTo(detalheLancamentoDTO2);
    }
}
