package com.sang9xpro.autopro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sang9xpro.autopro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationsFieldsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationsFields.class);
        ApplicationsFields applicationsFields1 = new ApplicationsFields();
        applicationsFields1.setId(1L);
        ApplicationsFields applicationsFields2 = new ApplicationsFields();
        applicationsFields2.setId(applicationsFields1.getId());
        assertThat(applicationsFields1).isEqualTo(applicationsFields2);
        applicationsFields2.setId(2L);
        assertThat(applicationsFields1).isNotEqualTo(applicationsFields2);
        applicationsFields1.setId(null);
        assertThat(applicationsFields1).isNotEqualTo(applicationsFields2);
    }
}
