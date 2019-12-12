package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class RetencaoFonteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RetencaoFonteDTO.class);
        RetencaoFonteDTO retencaoFonteDTO1 = new RetencaoFonteDTO();
        retencaoFonteDTO1.setId(1L);
        RetencaoFonteDTO retencaoFonteDTO2 = new RetencaoFonteDTO();
        assertThat(retencaoFonteDTO1).isNotEqualTo(retencaoFonteDTO2);
        retencaoFonteDTO2.setId(retencaoFonteDTO1.getId());
        assertThat(retencaoFonteDTO1).isEqualTo(retencaoFonteDTO2);
        retencaoFonteDTO2.setId(2L);
        assertThat(retencaoFonteDTO1).isNotEqualTo(retencaoFonteDTO2);
        retencaoFonteDTO1.setId(null);
        assertThat(retencaoFonteDTO1).isNotEqualTo(retencaoFonteDTO2);
    }
}
