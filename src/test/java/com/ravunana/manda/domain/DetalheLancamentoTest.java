package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class DetalheLancamentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalheLancamento.class);
        DetalheLancamento detalheLancamento1 = new DetalheLancamento();
        detalheLancamento1.setId(1L);
        DetalheLancamento detalheLancamento2 = new DetalheLancamento();
        detalheLancamento2.setId(detalheLancamento1.getId());
        assertThat(detalheLancamento1).isEqualTo(detalheLancamento2);
        detalheLancamento2.setId(2L);
        assertThat(detalheLancamento1).isNotEqualTo(detalheLancamento2);
        detalheLancamento1.setId(null);
        assertThat(detalheLancamento1).isNotEqualTo(detalheLancamento2);
    }
}
