package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class FamiliaProdutoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamiliaProduto.class);
        FamiliaProduto familiaProduto1 = new FamiliaProduto();
        familiaProduto1.setId(1L);
        FamiliaProduto familiaProduto2 = new FamiliaProduto();
        familiaProduto2.setId(familiaProduto1.getId());
        assertThat(familiaProduto1).isEqualTo(familiaProduto2);
        familiaProduto2.setId(2L);
        assertThat(familiaProduto1).isNotEqualTo(familiaProduto2);
        familiaProduto1.setId(null);
        assertThat(familiaProduto1).isNotEqualTo(familiaProduto2);
    }
}
