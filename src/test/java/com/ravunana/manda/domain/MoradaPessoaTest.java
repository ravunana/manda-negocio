package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class MoradaPessoaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoradaPessoa.class);
        MoradaPessoa moradaPessoa1 = new MoradaPessoa();
        moradaPessoa1.setId(1L);
        MoradaPessoa moradaPessoa2 = new MoradaPessoa();
        moradaPessoa2.setId(moradaPessoa1.getId());
        assertThat(moradaPessoa1).isEqualTo(moradaPessoa2);
        moradaPessoa2.setId(2L);
        assertThat(moradaPessoa1).isNotEqualTo(moradaPessoa2);
        moradaPessoa1.setId(null);
        assertThat(moradaPessoa1).isNotEqualTo(moradaPessoa2);
    }
}
