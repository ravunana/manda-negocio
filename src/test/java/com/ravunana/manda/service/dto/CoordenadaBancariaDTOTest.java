package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class CoordenadaBancariaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoordenadaBancariaDTO.class);
        CoordenadaBancariaDTO coordenadaBancariaDTO1 = new CoordenadaBancariaDTO();
        coordenadaBancariaDTO1.setId(1L);
        CoordenadaBancariaDTO coordenadaBancariaDTO2 = new CoordenadaBancariaDTO();
        assertThat(coordenadaBancariaDTO1).isNotEqualTo(coordenadaBancariaDTO2);
        coordenadaBancariaDTO2.setId(coordenadaBancariaDTO1.getId());
        assertThat(coordenadaBancariaDTO1).isEqualTo(coordenadaBancariaDTO2);
        coordenadaBancariaDTO2.setId(2L);
        assertThat(coordenadaBancariaDTO1).isNotEqualTo(coordenadaBancariaDTO2);
        coordenadaBancariaDTO1.setId(null);
        assertThat(coordenadaBancariaDTO1).isNotEqualTo(coordenadaBancariaDTO2);
    }
}
