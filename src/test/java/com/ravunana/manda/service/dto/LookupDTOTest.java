package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class LookupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LookupDTO.class);
        LookupDTO lookupDTO1 = new LookupDTO();
        lookupDTO1.setId(1L);
        LookupDTO lookupDTO2 = new LookupDTO();
        assertThat(lookupDTO1).isNotEqualTo(lookupDTO2);
        lookupDTO2.setId(lookupDTO1.getId());
        assertThat(lookupDTO1).isEqualTo(lookupDTO2);
        lookupDTO2.setId(2L);
        assertThat(lookupDTO1).isNotEqualTo(lookupDTO2);
        lookupDTO1.setId(null);
        assertThat(lookupDTO1).isNotEqualTo(lookupDTO2);
    }
}
