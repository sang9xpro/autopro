package com.sang9xpro.autopro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sang9xpro.autopro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentsValuesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentsValues.class);
        CommentsValues commentsValues1 = new CommentsValues();
        commentsValues1.setId(1L);
        CommentsValues commentsValues2 = new CommentsValues();
        commentsValues2.setId(commentsValues1.getId());
        assertThat(commentsValues1).isEqualTo(commentsValues2);
        commentsValues2.setId(2L);
        assertThat(commentsValues1).isNotEqualTo(commentsValues2);
        commentsValues1.setId(null);
        assertThat(commentsValues1).isNotEqualTo(commentsValues2);
    }
}
