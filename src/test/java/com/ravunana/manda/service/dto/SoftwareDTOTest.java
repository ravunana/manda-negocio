package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class SoftwareDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoftwareDTO.class);
        SoftwareDTO softwareDTO1 = new SoftwareDTO();
        softwareDTO1.setId(1L);
        SoftwareDTO softwareDTO2 = new SoftwareDTO();
        assertThat(softwareDTO1).isNotEqualTo(softwareDTO2);
        softwareDTO2.setId(softwareDTO1.getId());
        assertThat(softwareDTO1).isEqualTo(softwareDTO2);
        softwareDTO2.setId(2L);
        assertThat(softwareDTO1).isNotEqualTo(softwareDTO2);
        softwareDTO1.setId(null);
        assertThat(softwareDTO1).isNotEqualTo(softwareDTO2);
    }
}
