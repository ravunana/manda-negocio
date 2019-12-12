package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class RetencaoFonteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RetencaoFonte.class);
        RetencaoFonte retencaoFonte1 = new RetencaoFonte();
        retencaoFonte1.setId(1L);
        RetencaoFonte retencaoFonte2 = new RetencaoFonte();
        retencaoFonte2.setId(retencaoFonte1.getId());
        assertThat(retencaoFonte1).isEqualTo(retencaoFonte2);
        retencaoFonte2.setId(2L);
        assertThat(retencaoFonte1).isNotEqualTo(retencaoFonte2);
        retencaoFonte1.setId(null);
        assertThat(retencaoFonte1).isNotEqualTo(retencaoFonte2);
    }
}
