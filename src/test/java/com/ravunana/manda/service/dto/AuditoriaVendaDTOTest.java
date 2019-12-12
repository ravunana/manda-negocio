package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class AuditoriaVendaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditoriaVendaDTO.class);
        AuditoriaVendaDTO auditoriaVendaDTO1 = new AuditoriaVendaDTO();
        auditoriaVendaDTO1.setId(1L);
        AuditoriaVendaDTO auditoriaVendaDTO2 = new AuditoriaVendaDTO();
        assertThat(auditoriaVendaDTO1).isNotEqualTo(auditoriaVendaDTO2);
        auditoriaVendaDTO2.setId(auditoriaVendaDTO1.getId());
        assertThat(auditoriaVendaDTO1).isEqualTo(auditoriaVendaDTO2);
        auditoriaVendaDTO2.setId(2L);
        assertThat(auditoriaVendaDTO1).isNotEqualTo(auditoriaVendaDTO2);
        auditoriaVendaDTO1.setId(null);
        assertThat(auditoriaVendaDTO1).isNotEqualTo(auditoriaVendaDTO2);
    }
}
