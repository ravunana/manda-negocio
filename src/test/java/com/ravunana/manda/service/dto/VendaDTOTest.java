package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class VendaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendaDTO.class);
        VendaDTO vendaDTO1 = new VendaDTO();
        vendaDTO1.setId(1L);
        VendaDTO vendaDTO2 = new VendaDTO();
        assertThat(vendaDTO1).isNotEqualTo(vendaDTO2);
        vendaDTO2.setId(vendaDTO1.getId());
        assertThat(vendaDTO1).isEqualTo(vendaDTO2);
        vendaDTO2.setId(2L);
        assertThat(vendaDTO1).isNotEqualTo(vendaDTO2);
        vendaDTO1.setId(null);
        assertThat(vendaDTO1).isNotEqualTo(vendaDTO2);
    }
}
