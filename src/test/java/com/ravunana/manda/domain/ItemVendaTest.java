package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ItemVendaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemVenda.class);
        ItemVenda itemVenda1 = new ItemVenda();
        itemVenda1.setId(1L);
        ItemVenda itemVenda2 = new ItemVenda();
        itemVenda2.setId(itemVenda1.getId());
        assertThat(itemVenda1).isEqualTo(itemVenda2);
        itemVenda2.setId(2L);
        assertThat(itemVenda1).isNotEqualTo(itemVenda2);
        itemVenda1.setId(null);
        assertThat(itemVenda1).isNotEqualTo(itemVenda2);
    }
}
