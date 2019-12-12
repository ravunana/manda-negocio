package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class VariacaoProdutoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VariacaoProdutoDTO.class);
        VariacaoProdutoDTO variacaoProdutoDTO1 = new VariacaoProdutoDTO();
        variacaoProdutoDTO1.setId(1L);
        VariacaoProdutoDTO variacaoProdutoDTO2 = new VariacaoProdutoDTO();
        assertThat(variacaoProdutoDTO1).isNotEqualTo(variacaoProdutoDTO2);
        variacaoProdutoDTO2.setId(variacaoProdutoDTO1.getId());
        assertThat(variacaoProdutoDTO1).isEqualTo(variacaoProdutoDTO2);
        variacaoProdutoDTO2.setId(2L);
        assertThat(variacaoProdutoDTO1).isNotEqualTo(variacaoProdutoDTO2);
        variacaoProdutoDTO1.setId(null);
        assertThat(variacaoProdutoDTO1).isNotEqualTo(variacaoProdutoDTO2);
    }
}
