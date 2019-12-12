package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class LookupItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LookupItem.class);
        LookupItem lookupItem1 = new LookupItem();
        lookupItem1.setId(1L);
        LookupItem lookupItem2 = new LookupItem();
        lookupItem2.setId(lookupItem1.getId());
        assertThat(lookupItem1).isEqualTo(lookupItem2);
        lookupItem2.setId(2L);
        assertThat(lookupItem1).isNotEqualTo(lookupItem2);
        lookupItem1.setId(null);
        assertThat(lookupItem1).isNotEqualTo(lookupItem2);
    }
}
