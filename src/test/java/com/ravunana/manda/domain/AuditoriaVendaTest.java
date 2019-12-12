package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class AuditoriaVendaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditoriaVenda.class);
        AuditoriaVenda auditoriaVenda1 = new AuditoriaVenda();
        auditoriaVenda1.setId(1L);
        AuditoriaVenda auditoriaVenda2 = new AuditoriaVenda();
        auditoriaVenda2.setId(auditoriaVenda1.getId());
        assertThat(auditoriaVenda1).isEqualTo(auditoriaVenda2);
        auditoriaVenda2.setId(2L);
        assertThat(auditoriaVenda1).isNotEqualTo(auditoriaVenda2);
        auditoriaVenda1.setId(null);
        assertThat(auditoriaVenda1).isNotEqualTo(auditoriaVenda2);
    }
}
