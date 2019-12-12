package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ContactoPessoaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoPessoaDTO.class);
        ContactoPessoaDTO contactoPessoaDTO1 = new ContactoPessoaDTO();
        contactoPessoaDTO1.setId(1L);
        ContactoPessoaDTO contactoPessoaDTO2 = new ContactoPessoaDTO();
        assertThat(contactoPessoaDTO1).isNotEqualTo(contactoPessoaDTO2);
        contactoPessoaDTO2.setId(contactoPessoaDTO1.getId());
        assertThat(contactoPessoaDTO1).isEqualTo(contactoPessoaDTO2);
        contactoPessoaDTO2.setId(2L);
        assertThat(contactoPessoaDTO1).isNotEqualTo(contactoPessoaDTO2);
        contactoPessoaDTO1.setId(null);
        assertThat(contactoPessoaDTO1).isNotEqualTo(contactoPessoaDTO2);
    }
}
