package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class UnidadeMedidaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeMedidaDTO.class);
        UnidadeMedidaDTO unidadeMedidaDTO1 = new UnidadeMedidaDTO();
        unidadeMedidaDTO1.setId(1L);
        UnidadeMedidaDTO unidadeMedidaDTO2 = new UnidadeMedidaDTO();
        assertThat(unidadeMedidaDTO1).isNotEqualTo(unidadeMedidaDTO2);
        unidadeMedidaDTO2.setId(unidadeMedidaDTO1.getId());
        assertThat(unidadeMedidaDTO1).isEqualTo(unidadeMedidaDTO2);
        unidadeMedidaDTO2.setId(2L);
        assertThat(unidadeMedidaDTO1).isNotEqualTo(unidadeMedidaDTO2);
        unidadeMedidaDTO1.setId(null);
        assertThat(unidadeMedidaDTO1).isNotEqualTo(unidadeMedidaDTO2);
    }
}
