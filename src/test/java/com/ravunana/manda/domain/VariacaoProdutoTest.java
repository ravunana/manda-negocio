package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class VariacaoProdutoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VariacaoProduto.class);
        VariacaoProduto variacaoProduto1 = new VariacaoProduto();
        variacaoProduto1.setId(1L);
        VariacaoProduto variacaoProduto2 = new VariacaoProduto();
        variacaoProduto2.setId(variacaoProduto1.getId());
        assertThat(variacaoProduto1).isEqualTo(variacaoProduto2);
        variacaoProduto2.setId(2L);
        assertThat(variacaoProduto1).isNotEqualTo(variacaoProduto2);
        variacaoProduto1.setId(null);
        assertThat(variacaoProduto1).isNotEqualTo(variacaoProduto2);
    }
}
