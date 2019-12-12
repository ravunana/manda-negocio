package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class MoradaPessoaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoradaPessoaDTO.class);
        MoradaPessoaDTO moradaPessoaDTO1 = new MoradaPessoaDTO();
        moradaPessoaDTO1.setId(1L);
        MoradaPessoaDTO moradaPessoaDTO2 = new MoradaPessoaDTO();
        assertThat(moradaPessoaDTO1).isNotEqualTo(moradaPessoaDTO2);
        moradaPessoaDTO2.setId(moradaPessoaDTO1.getId());
        assertThat(moradaPessoaDTO1).isEqualTo(moradaPessoaDTO2);
        moradaPessoaDTO2.setId(2L);
        assertThat(moradaPessoaDTO1).isNotEqualTo(moradaPessoaDTO2);
        moradaPessoaDTO1.setId(null);
        assertThat(moradaPessoaDTO1).isNotEqualTo(moradaPessoaDTO2);
    }
}
