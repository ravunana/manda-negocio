package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class RelacionamentoPessoaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelacionamentoPessoa.class);
        RelacionamentoPessoa relacionamentoPessoa1 = new RelacionamentoPessoa();
        relacionamentoPessoa1.setId(1L);
        RelacionamentoPessoa relacionamentoPessoa2 = new RelacionamentoPessoa();
        relacionamentoPessoa2.setId(relacionamentoPessoa1.getId());
        assertThat(relacionamentoPessoa1).isEqualTo(relacionamentoPessoa2);
        relacionamentoPessoa2.setId(2L);
        assertThat(relacionamentoPessoa1).isNotEqualTo(relacionamentoPessoa2);
        relacionamentoPessoa1.setId(null);
        assertThat(relacionamentoPessoa1).isNotEqualTo(relacionamentoPessoa2);
    }
}
