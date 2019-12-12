package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class LocalArmazenamentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalArmazenamento.class);
        LocalArmazenamento localArmazenamento1 = new LocalArmazenamento();
        localArmazenamento1.setId(1L);
        LocalArmazenamento localArmazenamento2 = new LocalArmazenamento();
        localArmazenamento2.setId(localArmazenamento1.getId());
        assertThat(localArmazenamento1).isEqualTo(localArmazenamento2);
        localArmazenamento2.setId(2L);
        assertThat(localArmazenamento1).isNotEqualTo(localArmazenamento2);
        localArmazenamento1.setId(null);
        assertThat(localArmazenamento1).isNotEqualTo(localArmazenamento2);
    }
}
