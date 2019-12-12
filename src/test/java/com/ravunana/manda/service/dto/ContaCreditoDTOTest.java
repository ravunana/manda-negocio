package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ContaCreditoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContaCreditoDTO.class);
        ContaCreditoDTO contaCreditoDTO1 = new ContaCreditoDTO();
        contaCreditoDTO1.setId(1L);
        ContaCreditoDTO contaCreditoDTO2 = new ContaCreditoDTO();
        assertThat(contaCreditoDTO1).isNotEqualTo(contaCreditoDTO2);
        contaCreditoDTO2.setId(contaCreditoDTO1.getId());
        assertThat(contaCreditoDTO1).isEqualTo(contaCreditoDTO2);
        contaCreditoDTO2.setId(2L);
        assertThat(contaCreditoDTO1).isNotEqualTo(contaCreditoDTO2);
        contaCreditoDTO1.setId(null);
        assertThat(contaCreditoDTO1).isNotEqualTo(contaCreditoDTO2);
    }
}
