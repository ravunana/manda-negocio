package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class SerieDocumentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SerieDocumentoDTO.class);
        SerieDocumentoDTO serieDocumentoDTO1 = new SerieDocumentoDTO();
        serieDocumentoDTO1.setId(1L);
        SerieDocumentoDTO serieDocumentoDTO2 = new SerieDocumentoDTO();
        assertThat(serieDocumentoDTO1).isNotEqualTo(serieDocumentoDTO2);
        serieDocumentoDTO2.setId(serieDocumentoDTO1.getId());
        assertThat(serieDocumentoDTO1).isEqualTo(serieDocumentoDTO2);
        serieDocumentoDTO2.setId(2L);
        assertThat(serieDocumentoDTO1).isNotEqualTo(serieDocumentoDTO2);
        serieDocumentoDTO1.setId(null);
        assertThat(serieDocumentoDTO1).isNotEqualTo(serieDocumentoDTO2);
    }
}
