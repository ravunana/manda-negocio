package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ConversaoUnidadeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConversaoUnidade.class);
        ConversaoUnidade conversaoUnidade1 = new ConversaoUnidade();
        conversaoUnidade1.setId(1L);
        ConversaoUnidade conversaoUnidade2 = new ConversaoUnidade();
        conversaoUnidade2.setId(conversaoUnidade1.getId());
        assertThat(conversaoUnidade1).isEqualTo(conversaoUnidade2);
        conversaoUnidade2.setId(2L);
        assertThat(conversaoUnidade1).isNotEqualTo(conversaoUnidade2);
        conversaoUnidade1.setId(null);
        assertThat(conversaoUnidade1).isNotEqualTo(conversaoUnidade2);
    }
}
