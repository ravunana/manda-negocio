package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class FluxoDocumentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FluxoDocumento.class);
        FluxoDocumento fluxoDocumento1 = new FluxoDocumento();
        fluxoDocumento1.setId(1L);
        FluxoDocumento fluxoDocumento2 = new FluxoDocumento();
        fluxoDocumento2.setId(fluxoDocumento1.getId());
        assertThat(fluxoDocumento1).isEqualTo(fluxoDocumento2);
        fluxoDocumento2.setId(2L);
        assertThat(fluxoDocumento1).isNotEqualTo(fluxoDocumento2);
        fluxoDocumento1.setId(null);
        assertThat(fluxoDocumento1).isNotEqualTo(fluxoDocumento2);
    }
}
