package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class SerieDocumentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SerieDocumento.class);
        SerieDocumento serieDocumento1 = new SerieDocumento();
        serieDocumento1.setId(1L);
        SerieDocumento serieDocumento2 = new SerieDocumento();
        serieDocumento2.setId(serieDocumento1.getId());
        assertThat(serieDocumento1).isEqualTo(serieDocumento2);
        serieDocumento2.setId(2L);
        assertThat(serieDocumento1).isNotEqualTo(serieDocumento2);
        serieDocumento1.setId(null);
        assertThat(serieDocumento1).isNotEqualTo(serieDocumento2);
    }
}
