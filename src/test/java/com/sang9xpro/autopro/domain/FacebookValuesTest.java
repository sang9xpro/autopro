package com.sang9xpro.autopro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sang9xpro.autopro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FacebookValuesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacebookValues.class);
        FacebookValues facebookValues1 = new FacebookValues();
        facebookValues1.setId(1L);
        FacebookValues facebookValues2 = new FacebookValues();
        facebookValues2.setId(facebookValues1.getId());
        assertThat(facebookValues1).isEqualTo(facebookValues2);
        facebookValues2.setId(2L);
        assertThat(facebookValues1).isNotEqualTo(facebookValues2);
        facebookValues1.setId(null);
        assertThat(facebookValues1).isNotEqualTo(facebookValues2);
    }
}
