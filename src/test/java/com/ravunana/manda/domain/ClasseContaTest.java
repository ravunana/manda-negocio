package com.ravunana.manda.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.manda.web.rest.TestUtil;

public class ClasseContaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasseConta.class);
        ClasseConta classeConta1 = new ClasseConta();
        classeConta1.setId(1L);
        ClasseConta classeConta2 = new ClasseConta();
        classeConta2.setId(classeConta1.getId());
        assertThat(classeConta1).isEqualTo(classeConta2);
        classeConta2.setId(2L);
        assertThat(classeConta1).isNotEqualTo(classeConta2);
        classeConta1.setId(null);
        assertThat(classeConta1).isNotEqualTo(classeConta2);
    }
}
