package com.sang9xpro.autopro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sang9xpro.autopro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchedulerTaskTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchedulerTask.class);
        SchedulerTask schedulerTask1 = new SchedulerTask();
        schedulerTask1.setId(1L);
        SchedulerTask schedulerTask2 = new SchedulerTask();
        schedulerTask2.setId(schedulerTask1.getId());
        assertThat(schedulerTask1).isEqualTo(schedulerTask2);
        schedulerTask2.setId(2L);
        assertThat(schedulerTask1).isNotEqualTo(schedulerTask2);
        schedulerTask1.setId(null);
        assertThat(schedulerTask1).isNotEqualTo(schedulerTask2);
    }
}
