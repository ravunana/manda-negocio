package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class LocalizacaoEmpresaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalizacaoEmpresa.class);
        LocalizacaoEmpresa localizacaoEmpresa1 = new LocalizacaoEmpresa();
        localizacaoEmpresa1.setId(1L);
        LocalizacaoEmpresa localizacaoEmpresa2 = new LocalizacaoEmpresa();
        localizacaoEmpresa2.setId(localizacaoEmpresa1.getId());
        assertThat(localizacaoEmpresa1).isEqualTo(localizacaoEmpresa2);
        localizacaoEmpresa2.setId(2L);
        assertThat(localizacaoEmpresa1).isNotEqualTo(localizacaoEmpresa2);
        localizacaoEmpresa1.setId(null);
        assertThat(localizacaoEmpresa1).isNotEqualTo(localizacaoEmpresa2);
    }
}
