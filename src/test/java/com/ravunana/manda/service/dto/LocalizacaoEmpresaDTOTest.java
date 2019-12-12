package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class LocalizacaoEmpresaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalizacaoEmpresaDTO.class);
        LocalizacaoEmpresaDTO localizacaoEmpresaDTO1 = new LocalizacaoEmpresaDTO();
        localizacaoEmpresaDTO1.setId(1L);
        LocalizacaoEmpresaDTO localizacaoEmpresaDTO2 = new LocalizacaoEmpresaDTO();
        assertThat(localizacaoEmpresaDTO1).isNotEqualTo(localizacaoEmpresaDTO2);
        localizacaoEmpresaDTO2.setId(localizacaoEmpresaDTO1.getId());
        assertThat(localizacaoEmpresaDTO1).isEqualTo(localizacaoEmpresaDTO2);
        localizacaoEmpresaDTO2.setId(2L);
        assertThat(localizacaoEmpresaDTO1).isNotEqualTo(localizacaoEmpresaDTO2);
        localizacaoEmpresaDTO1.setId(null);
        assertThat(localizacaoEmpresaDTO1).isNotEqualTo(localizacaoEmpresaDTO2);
    }
}
