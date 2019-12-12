package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class DocumentoComercialDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentoComercialDTO.class);
        DocumentoComercialDTO documentoComercialDTO1 = new DocumentoComercialDTO();
        documentoComercialDTO1.setId(1L);
        DocumentoComercialDTO documentoComercialDTO2 = new DocumentoComercialDTO();
        assertThat(documentoComercialDTO1).isNotEqualTo(documentoComercialDTO2);
        documentoComercialDTO2.setId(documentoComercialDTO1.getId());
        assertThat(documentoComercialDTO1).isEqualTo(documentoComercialDTO2);
        documentoComercialDTO2.setId(2L);
        assertThat(documentoComercialDTO1).isNotEqualTo(documentoComercialDTO2);
        documentoComercialDTO1.setId(null);
        assertThat(documentoComercialDTO1).isNotEqualTo(documentoComercialDTO2);
    }
}
