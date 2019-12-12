package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class EstruturaCalculoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstruturaCalculoDTO.class);
        EstruturaCalculoDTO estruturaCalculoDTO1 = new EstruturaCalculoDTO();
        estruturaCalculoDTO1.setId(1L);
        EstruturaCalculoDTO estruturaCalculoDTO2 = new EstruturaCalculoDTO();
        assertThat(estruturaCalculoDTO1).isNotEqualTo(estruturaCalculoDTO2);
        estruturaCalculoDTO2.setId(estruturaCalculoDTO1.getId());
        assertThat(estruturaCalculoDTO1).isEqualTo(estruturaCalculoDTO2);
        estruturaCalculoDTO2.setId(2L);
        assertThat(estruturaCalculoDTO1).isNotEqualTo(estruturaCalculoDTO2);
        estruturaCalculoDTO1.setId(null);
        assertThat(estruturaCalculoDTO1).isNotEqualTo(estruturaCalculoDTO2);
    }
}
