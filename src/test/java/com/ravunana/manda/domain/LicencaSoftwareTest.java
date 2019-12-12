package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class LicencaSoftwareTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicencaSoftware.class);
        LicencaSoftware licencaSoftware1 = new LicencaSoftware();
        licencaSoftware1.setId(1L);
        LicencaSoftware licencaSoftware2 = new LicencaSoftware();
        licencaSoftware2.setId(licencaSoftware1.getId());
        assertThat(licencaSoftware1).isEqualTo(licencaSoftware2);
        licencaSoftware2.setId(2L);
        assertThat(licencaSoftware1).isNotEqualTo(licencaSoftware2);
        licencaSoftware1.setId(null);
        assertThat(licencaSoftware1).isNotEqualTo(licencaSoftware2);
    }
}
