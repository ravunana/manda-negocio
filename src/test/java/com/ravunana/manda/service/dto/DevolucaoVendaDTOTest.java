package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class DevolucaoVendaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DevolucaoVendaDTO.class);
        DevolucaoVendaDTO devolucaoVendaDTO1 = new DevolucaoVendaDTO();
        devolucaoVendaDTO1.setId(1L);
        DevolucaoVendaDTO devolucaoVendaDTO2 = new DevolucaoVendaDTO();
        assertThat(devolucaoVendaDTO1).isNotEqualTo(devolucaoVendaDTO2);
        devolucaoVendaDTO2.setId(devolucaoVendaDTO1.getId());
        assertThat(devolucaoVendaDTO1).isEqualTo(devolucaoVendaDTO2);
        devolucaoVendaDTO2.setId(2L);
        assertThat(devolucaoVendaDTO1).isNotEqualTo(devolucaoVendaDTO2);
        devolucaoVendaDTO1.setId(null);
        assertThat(devolucaoVendaDTO1).isNotEqualTo(devolucaoVendaDTO2);
    }
}
