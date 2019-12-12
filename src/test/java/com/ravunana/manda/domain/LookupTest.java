package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class LookupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lookup.class);
        Lookup lookup1 = new Lookup();
        lookup1.setId(1L);
        Lookup lookup2 = new Lookup();
        lookup2.setId(lookup1.getId());
        assertThat(lookup1).isEqualTo(lookup2);
        lookup2.setId(2L);
        assertThat(lookup1).isNotEqualTo(lookup2);
        lookup1.setId(null);
        assertThat(lookup1).isNotEqualTo(lookup2);
    }
}
