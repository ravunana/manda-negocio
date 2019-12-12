package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class CompostoProdutoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompostoProdutoDTO.class);
        CompostoProdutoDTO compostoProdutoDTO1 = new CompostoProdutoDTO();
        compostoProdutoDTO1.setId(1L);
        CompostoProdutoDTO compostoProdutoDTO2 = new CompostoProdutoDTO();
        assertThat(compostoProdutoDTO1).isNotEqualTo(compostoProdutoDTO2);
        compostoProdutoDTO2.setId(compostoProdutoDTO1.getId());
        assertThat(compostoProdutoDTO1).isEqualTo(compostoProdutoDTO2);
        compostoProdutoDTO2.setId(2L);
        assertThat(compostoProdutoDTO1).isNotEqualTo(compostoProdutoDTO2);
        compostoProdutoDTO1.setId(null);
        assertThat(compostoProdutoDTO1).isNotEqualTo(compostoProdutoDTO2);
    }
}
