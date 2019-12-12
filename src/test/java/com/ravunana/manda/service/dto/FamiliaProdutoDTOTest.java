package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class FamiliaProdutoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamiliaProdutoDTO.class);
        FamiliaProdutoDTO familiaProdutoDTO1 = new FamiliaProdutoDTO();
        familiaProdutoDTO1.setId(1L);
        FamiliaProdutoDTO familiaProdutoDTO2 = new FamiliaProdutoDTO();
        assertThat(familiaProdutoDTO1).isNotEqualTo(familiaProdutoDTO2);
        familiaProdutoDTO2.setId(familiaProdutoDTO1.getId());
        assertThat(familiaProdutoDTO1).isEqualTo(familiaProdutoDTO2);
        familiaProdutoDTO2.setId(2L);
        assertThat(familiaProdutoDTO1).isNotEqualTo(familiaProdutoDTO2);
        familiaProdutoDTO1.setId(null);
        assertThat(familiaProdutoDTO1).isNotEqualTo(familiaProdutoDTO2);
    }
}
