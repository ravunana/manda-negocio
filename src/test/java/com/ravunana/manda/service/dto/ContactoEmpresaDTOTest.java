package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ContactoEmpresaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoEmpresaDTO.class);
        ContactoEmpresaDTO contactoEmpresaDTO1 = new ContactoEmpresaDTO();
        contactoEmpresaDTO1.setId(1L);
        ContactoEmpresaDTO contactoEmpresaDTO2 = new ContactoEmpresaDTO();
        assertThat(contactoEmpresaDTO1).isNotEqualTo(contactoEmpresaDTO2);
        contactoEmpresaDTO2.setId(contactoEmpresaDTO1.getId());
        assertThat(contactoEmpresaDTO1).isEqualTo(contactoEmpresaDTO2);
        contactoEmpresaDTO2.setId(2L);
        assertThat(contactoEmpresaDTO1).isNotEqualTo(contactoEmpresaDTO2);
        contactoEmpresaDTO1.setId(null);
        assertThat(contactoEmpresaDTO1).isNotEqualTo(contactoEmpresaDTO2);
    }
}
