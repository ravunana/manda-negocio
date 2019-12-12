package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ProdutoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdutoDTO.class);
        ProdutoDTO produtoDTO1 = new ProdutoDTO();
        produtoDTO1.setId(1L);
        ProdutoDTO produtoDTO2 = new ProdutoDTO();
        assertThat(produtoDTO1).isNotEqualTo(produtoDTO2);
        produtoDTO2.setId(produtoDTO1.getId());
        assertThat(produtoDTO1).isEqualTo(produtoDTO2);
        produtoDTO2.setId(2L);
        assertThat(produtoDTO1).isNotEqualTo(produtoDTO2);
        produtoDTO1.setId(null);
        assertThat(produtoDTO1).isNotEqualTo(produtoDTO2);
    }
}
