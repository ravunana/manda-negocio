package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ItemCompraDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemCompraDTO.class);
        ItemCompraDTO itemCompraDTO1 = new ItemCompraDTO();
        itemCompraDTO1.setId(1L);
        ItemCompraDTO itemCompraDTO2 = new ItemCompraDTO();
        assertThat(itemCompraDTO1).isNotEqualTo(itemCompraDTO2);
        itemCompraDTO2.setId(itemCompraDTO1.getId());
        assertThat(itemCompraDTO1).isEqualTo(itemCompraDTO2);
        itemCompraDTO2.setId(2L);
        assertThat(itemCompraDTO1).isNotEqualTo(itemCompraDTO2);
        itemCompraDTO1.setId(null);
        assertThat(itemCompraDTO1).isNotEqualTo(itemCompraDTO2);
    }
}
