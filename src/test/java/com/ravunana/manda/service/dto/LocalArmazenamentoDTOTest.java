package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class LocalArmazenamentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalArmazenamentoDTO.class);
        LocalArmazenamentoDTO localArmazenamentoDTO1 = new LocalArmazenamentoDTO();
        localArmazenamentoDTO1.setId(1L);
        LocalArmazenamentoDTO localArmazenamentoDTO2 = new LocalArmazenamentoDTO();
        assertThat(localArmazenamentoDTO1).isNotEqualTo(localArmazenamentoDTO2);
        localArmazenamentoDTO2.setId(localArmazenamentoDTO1.getId());
        assertThat(localArmazenamentoDTO1).isEqualTo(localArmazenamentoDTO2);
        localArmazenamentoDTO2.setId(2L);
        assertThat(localArmazenamentoDTO1).isNotEqualTo(localArmazenamentoDTO2);
        localArmazenamentoDTO1.setId(null);
        assertThat(localArmazenamentoDTO1).isNotEqualTo(localArmazenamentoDTO2);
    }
}
