package com.sang9xpro.autopro.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sang9xpro.autopro.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentsFieldsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentsFields.class);
        CommentsFields commentsFields1 = new CommentsFields();
        commentsFields1.setId(1L);
        CommentsFields commentsFields2 = new CommentsFields();
        commentsFields2.setId(commentsFields1.getId());
        assertThat(commentsFields1).isEqualTo(commentsFields2);
        commentsFields2.setId(2L);
        assertThat(commentsFields1).isNotEqualTo(commentsFields2);
        commentsFields1.setId(null);
        assertThat(commentsFields1).isNotEqualTo(commentsFields2);
    }
}
