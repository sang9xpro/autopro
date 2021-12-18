package com.sang9xpro.autopro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sang9xpro.autopro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationsValuesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationsValues.class);
        ApplicationsValues applicationsValues1 = new ApplicationsValues();
        applicationsValues1.setId(1L);
        ApplicationsValues applicationsValues2 = new ApplicationsValues();
        applicationsValues2.setId(applicationsValues1.getId());
        assertThat(applicationsValues1).isEqualTo(applicationsValues2);
        applicationsValues2.setId(2L);
        assertThat(applicationsValues1).isNotEqualTo(applicationsValues2);
        applicationsValues1.setId(null);
        assertThat(applicationsValues1).isNotEqualTo(applicationsValues2);
    }
}
