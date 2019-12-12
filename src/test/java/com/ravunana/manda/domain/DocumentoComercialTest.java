package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class DocumentoComercialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentoComercial.class);
        DocumentoComercial documentoComercial1 = new DocumentoComercial();
        documentoComercial1.setId(1L);
        DocumentoComercial documentoComercial2 = new DocumentoComercial();
        documentoComercial2.setId(documentoComercial1.getId());
        assertThat(documentoComercial1).isEqualTo(documentoComercial2);
        documentoComercial2.setId(2L);
        assertThat(documentoComercial1).isNotEqualTo(documentoComercial2);
        documentoComercial1.setId(null);
        assertThat(documentoComercial1).isNotEqualTo(documentoComercial2);
    }
}
