package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ContactoEmpresaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoEmpresa.class);
        ContactoEmpresa contactoEmpresa1 = new ContactoEmpresa();
        contactoEmpresa1.setId(1L);
        ContactoEmpresa contactoEmpresa2 = new ContactoEmpresa();
        contactoEmpresa2.setId(contactoEmpresa1.getId());
        assertThat(contactoEmpresa1).isEqualTo(contactoEmpresa2);
        contactoEmpresa2.setId(2L);
        assertThat(contactoEmpresa1).isNotEqualTo(contactoEmpresa2);
        contactoEmpresa1.setId(null);
        assertThat(contactoEmpresa1).isNotEqualTo(contactoEmpresa2);
    }
}
