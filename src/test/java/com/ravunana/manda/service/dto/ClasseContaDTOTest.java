package com.ravunana.manda.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ClasseContaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasseContaDTO.class);
        ClasseContaDTO classeContaDTO1 = new ClasseContaDTO();
        classeContaDTO1.setId(1L);
        ClasseContaDTO classeContaDTO2 = new ClasseContaDTO();
        assertThat(classeContaDTO1).isNotEqualTo(classeContaDTO2);
        classeContaDTO2.setId(classeContaDTO1.getId());
        assertThat(classeContaDTO1).isEqualTo(classeContaDTO2);
        classeContaDTO2.setId(2L);
        assertThat(classeContaDTO1).isNotEqualTo(classeContaDTO2);
        classeContaDTO1.setId(null);
        assertThat(classeContaDTO1).isNotEqualTo(classeContaDTO2);
    }
}
