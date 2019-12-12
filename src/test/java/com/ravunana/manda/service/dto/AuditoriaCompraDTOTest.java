package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class AuditoriaCompraDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditoriaCompraDTO.class);
        AuditoriaCompraDTO auditoriaCompraDTO1 = new AuditoriaCompraDTO();
        auditoriaCompraDTO1.setId(1L);
        AuditoriaCompraDTO auditoriaCompraDTO2 = new AuditoriaCompraDTO();
        assertThat(auditoriaCompraDTO1).isNotEqualTo(auditoriaCompraDTO2);
        auditoriaCompraDTO2.setId(auditoriaCompraDTO1.getId());
        assertThat(auditoriaCompraDTO1).isEqualTo(auditoriaCompraDTO2);
        auditoriaCompraDTO2.setId(2L);
        assertThat(auditoriaCompraDTO1).isNotEqualTo(auditoriaCompraDTO2);
        auditoriaCompraDTO1.setId(null);
        assertThat(auditoriaCompraDTO1).isNotEqualTo(auditoriaCompraDTO2);
    }
}
