package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class FluxoDocumentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FluxoDocumentoDTO.class);
        FluxoDocumentoDTO fluxoDocumentoDTO1 = new FluxoDocumentoDTO();
        fluxoDocumentoDTO1.setId(1L);
        FluxoDocumentoDTO fluxoDocumentoDTO2 = new FluxoDocumentoDTO();
        assertThat(fluxoDocumentoDTO1).isNotEqualTo(fluxoDocumentoDTO2);
        fluxoDocumentoDTO2.setId(fluxoDocumentoDTO1.getId());
        assertThat(fluxoDocumentoDTO1).isEqualTo(fluxoDocumentoDTO2);
        fluxoDocumentoDTO2.setId(2L);
        assertThat(fluxoDocumentoDTO1).isNotEqualTo(fluxoDocumentoDTO2);
        fluxoDocumentoDTO1.setId(null);
        assertThat(fluxoDocumentoDTO1).isNotEqualTo(fluxoDocumentoDTO2);
    }
}
