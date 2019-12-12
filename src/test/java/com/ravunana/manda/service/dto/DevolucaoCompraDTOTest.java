package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class DevolucaoCompraDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DevolucaoCompraDTO.class);
        DevolucaoCompraDTO devolucaoCompraDTO1 = new DevolucaoCompraDTO();
        devolucaoCompraDTO1.setId(1L);
        DevolucaoCompraDTO devolucaoCompraDTO2 = new DevolucaoCompraDTO();
        assertThat(devolucaoCompraDTO1).isNotEqualTo(devolucaoCompraDTO2);
        devolucaoCompraDTO2.setId(devolucaoCompraDTO1.getId());
        assertThat(devolucaoCompraDTO1).isEqualTo(devolucaoCompraDTO2);
        devolucaoCompraDTO2.setId(2L);
        assertThat(devolucaoCompraDTO1).isNotEqualTo(devolucaoCompraDTO2);
        devolucaoCompraDTO1.setId(null);
        assertThat(devolucaoCompraDTO1).isNotEqualTo(devolucaoCompraDTO2);
    }
}
