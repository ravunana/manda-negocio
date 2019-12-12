package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class DevolucaoVendaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DevolucaoVenda.class);
        DevolucaoVenda devolucaoVenda1 = new DevolucaoVenda();
        devolucaoVenda1.setId(1L);
        DevolucaoVenda devolucaoVenda2 = new DevolucaoVenda();
        devolucaoVenda2.setId(devolucaoVenda1.getId());
        assertThat(devolucaoVenda1).isEqualTo(devolucaoVenda2);
        devolucaoVenda2.setId(2L);
        assertThat(devolucaoVenda1).isNotEqualTo(devolucaoVenda2);
        devolucaoVenda1.setId(null);
        assertThat(devolucaoVenda1).isNotEqualTo(devolucaoVenda2);
    }
}
