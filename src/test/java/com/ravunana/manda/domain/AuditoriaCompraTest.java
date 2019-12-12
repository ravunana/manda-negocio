package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class AuditoriaCompraTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditoriaCompra.class);
        AuditoriaCompra auditoriaCompra1 = new AuditoriaCompra();
        auditoriaCompra1.setId(1L);
        AuditoriaCompra auditoriaCompra2 = new AuditoriaCompra();
        auditoriaCompra2.setId(auditoriaCompra1.getId());
        assertThat(auditoriaCompra1).isEqualTo(auditoriaCompra2);
        auditoriaCompra2.setId(2L);
        assertThat(auditoriaCompra1).isNotEqualTo(auditoriaCompra2);
        auditoriaCompra1.setId(null);
        assertThat(auditoriaCompra1).isNotEqualTo(auditoriaCompra2);
    }
}
