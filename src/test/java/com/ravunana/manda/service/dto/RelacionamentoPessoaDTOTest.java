package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class RelacionamentoPessoaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelacionamentoPessoaDTO.class);
        RelacionamentoPessoaDTO relacionamentoPessoaDTO1 = new RelacionamentoPessoaDTO();
        relacionamentoPessoaDTO1.setId(1L);
        RelacionamentoPessoaDTO relacionamentoPessoaDTO2 = new RelacionamentoPessoaDTO();
        assertThat(relacionamentoPessoaDTO1).isNotEqualTo(relacionamentoPessoaDTO2);
        relacionamentoPessoaDTO2.setId(relacionamentoPessoaDTO1.getId());
        assertThat(relacionamentoPessoaDTO1).isEqualTo(relacionamentoPessoaDTO2);
        relacionamentoPessoaDTO2.setId(2L);
        assertThat(relacionamentoPessoaDTO1).isNotEqualTo(relacionamentoPessoaDTO2);
        relacionamentoPessoaDTO1.setId(null);
        assertThat(relacionamentoPessoaDTO1).isNotEqualTo(relacionamentoPessoaDTO2);
    }
}
