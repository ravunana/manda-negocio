package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ItemCompraTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemCompra.class);
        ItemCompra itemCompra1 = new ItemCompra();
        itemCompra1.setId(1L);
        ItemCompra itemCompra2 = new ItemCompra();
        itemCompra2.setId(itemCompra1.getId());
        assertThat(itemCompra1).isEqualTo(itemCompra2);
        itemCompra2.setId(2L);
        assertThat(itemCompra1).isNotEqualTo(itemCompra2);
        itemCompra1.setId(null);
        assertThat(itemCompra1).isNotEqualTo(itemCompra2);
    }
}
