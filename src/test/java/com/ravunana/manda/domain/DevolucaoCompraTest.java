package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class DevolucaoCompraTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DevolucaoCompra.class);
        DevolucaoCompra devolucaoCompra1 = new DevolucaoCompra();
        devolucaoCompra1.setId(1L);
        DevolucaoCompra devolucaoCompra2 = new DevolucaoCompra();
        devolucaoCompra2.setId(devolucaoCompra1.getId());
        assertThat(devolucaoCompra1).isEqualTo(devolucaoCompra2);
        devolucaoCompra2.setId(2L);
        assertThat(devolucaoCompra1).isNotEqualTo(devolucaoCompra2);
        devolucaoCompra1.setId(null);
        assertThat(devolucaoCompra1).isNotEqualTo(devolucaoCompra2);
    }
}
