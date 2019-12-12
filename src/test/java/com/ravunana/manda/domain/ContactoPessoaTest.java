package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ContactoPessoaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoPessoa.class);
        ContactoPessoa contactoPessoa1 = new ContactoPessoa();
        contactoPessoa1.setId(1L);
        ContactoPessoa contactoPessoa2 = new ContactoPessoa();
        contactoPessoa2.setId(contactoPessoa1.getId());
        assertThat(contactoPessoa1).isEqualTo(contactoPessoa2);
        contactoPessoa2.setId(2L);
        assertThat(contactoPessoa1).isNotEqualTo(contactoPessoa2);
        contactoPessoa1.setId(null);
        assertThat(contactoPessoa1).isNotEqualTo(contactoPessoa2);
    }
}
