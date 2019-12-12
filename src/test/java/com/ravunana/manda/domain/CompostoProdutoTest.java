package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class CompostoProdutoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompostoProduto.class);
        CompostoProduto compostoProduto1 = new CompostoProduto();
        compostoProduto1.setId(1L);
        CompostoProduto compostoProduto2 = new CompostoProduto();
        compostoProduto2.setId(compostoProduto1.getId());
        assertThat(compostoProduto1).isEqualTo(compostoProduto2);
        compostoProduto2.setId(2L);
        assertThat(compostoProduto1).isNotEqualTo(compostoProduto2);
        compostoProduto1.setId(null);
        assertThat(compostoProduto1).isNotEqualTo(compostoProduto2);
    }
}
