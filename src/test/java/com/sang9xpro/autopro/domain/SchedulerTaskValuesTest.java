package com.sang9xpro.autopro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sang9xpro.autopro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchedulerTaskValuesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchedulerTaskValues.class);
        SchedulerTaskValues schedulerTaskValues1 = new SchedulerTaskValues();
        schedulerTaskValues1.setId(1L);
        SchedulerTaskValues schedulerTaskValues2 = new SchedulerTaskValues();
        schedulerTaskValues2.setId(schedulerTaskValues1.getId());
        assertThat(schedulerTaskValues1).isEqualTo(schedulerTaskValues2);
        schedulerTaskValues2.setId(2L);
        assertThat(schedulerTaskValues1).isNotEqualTo(schedulerTaskValues2);
        schedulerTaskValues1.setId(null);
        assertThat(schedulerTaskValues1).isNotEqualTo(schedulerTaskValues2);
    }
}
