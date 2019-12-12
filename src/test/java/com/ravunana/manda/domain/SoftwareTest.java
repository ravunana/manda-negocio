package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class SoftwareTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Software.class);
        Software software1 = new Software();
        software1.setId(1L);
        Software software2 = new Software();
        software2.setId(software1.getId());
        assertThat(software1).isEqualTo(software2);
        software2.setId(2L);
        assertThat(software1).isNotEqualTo(software2);
        software1.setId(null);
        assertThat(software1).isNotEqualTo(software2);
    }
}
