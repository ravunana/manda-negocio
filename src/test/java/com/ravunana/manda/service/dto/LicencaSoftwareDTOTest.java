package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class LicencaSoftwareDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicencaSoftwareDTO.class);
        LicencaSoftwareDTO licencaSoftwareDTO1 = new LicencaSoftwareDTO();
        licencaSoftwareDTO1.setId(1L);
        LicencaSoftwareDTO licencaSoftwareDTO2 = new LicencaSoftwareDTO();
        assertThat(licencaSoftwareDTO1).isNotEqualTo(licencaSoftwareDTO2);
        licencaSoftwareDTO2.setId(licencaSoftwareDTO1.getId());
        assertThat(licencaSoftwareDTO1).isEqualTo(licencaSoftwareDTO2);
        licencaSoftwareDTO2.setId(2L);
        assertThat(licencaSoftwareDTO1).isNotEqualTo(licencaSoftwareDTO2);
        licencaSoftwareDTO1.setId(null);
        assertThat(licencaSoftwareDTO1).isNotEqualTo(licencaSoftwareDTO2);
    }
}
