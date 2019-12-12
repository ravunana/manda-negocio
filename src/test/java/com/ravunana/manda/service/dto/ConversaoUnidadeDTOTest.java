package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ConversaoUnidadeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConversaoUnidadeDTO.class);
        ConversaoUnidadeDTO conversaoUnidadeDTO1 = new ConversaoUnidadeDTO();
        conversaoUnidadeDTO1.setId(1L);
        ConversaoUnidadeDTO conversaoUnidadeDTO2 = new ConversaoUnidadeDTO();
        assertThat(conversaoUnidadeDTO1).isNotEqualTo(conversaoUnidadeDTO2);
        conversaoUnidadeDTO2.setId(conversaoUnidadeDTO1.getId());
        assertThat(conversaoUnidadeDTO1).isEqualTo(conversaoUnidadeDTO2);
        conversaoUnidadeDTO2.setId(2L);
        assertThat(conversaoUnidadeDTO1).isNotEqualTo(conversaoUnidadeDTO2);
        conversaoUnidadeDTO1.setId(null);
        assertThat(conversaoUnidadeDTO1).isNotEqualTo(conversaoUnidadeDTO2);
    }
}
