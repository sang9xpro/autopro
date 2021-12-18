package com.sang9xpro.autopro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sang9xpro.autopro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchedulerTaskFieldsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchedulerTaskFields.class);
        SchedulerTaskFields schedulerTaskFields1 = new SchedulerTaskFields();
        schedulerTaskFields1.setId(1L);
        SchedulerTaskFields schedulerTaskFields2 = new SchedulerTaskFields();
        schedulerTaskFields2.setId(schedulerTaskFields1.getId());
        assertThat(schedulerTaskFields1).isEqualTo(schedulerTaskFields2);
        schedulerTaskFields2.setId(2L);
        assertThat(schedulerTaskFields1).isNotEqualTo(schedulerTaskFields2);
        schedulerTaskFields1.setId(null);
        assertThat(schedulerTaskFields1).isNotEqualTo(schedulerTaskFields2);
    }
}
