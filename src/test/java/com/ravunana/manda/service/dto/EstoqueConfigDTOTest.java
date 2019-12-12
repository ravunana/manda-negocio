package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class EstoqueConfigDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstoqueConfigDTO.class);
        EstoqueConfigDTO estoqueConfigDTO1 = new EstoqueConfigDTO();
        estoqueConfigDTO1.setId(1L);
        EstoqueConfigDTO estoqueConfigDTO2 = new EstoqueConfigDTO();
        assertThat(estoqueConfigDTO1).isNotEqualTo(estoqueConfigDTO2);
        estoqueConfigDTO2.setId(estoqueConfigDTO1.getId());
        assertThat(estoqueConfigDTO1).isEqualTo(estoqueConfigDTO2);
        estoqueConfigDTO2.setId(2L);
        assertThat(estoqueConfigDTO1).isNotEqualTo(estoqueConfigDTO2);
        estoqueConfigDTO1.setId(null);
        assertThat(estoqueConfigDTO1).isNotEqualTo(estoqueConfigDTO2);
    }
}
