package so.gov.mfa.visa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class ApplicationCommentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationComment.class);
        ApplicationComment applicationComment1 = new ApplicationComment();
        applicationComment1.setId(1L);
        ApplicationComment applicationComment2 = new ApplicationComment();
        applicationComment2.setId(applicationComment1.getId());
        assertThat(applicationComment1).isEqualTo(applicationComment2);
        applicationComment2.setId(2L);
        assertThat(applicationComment1).isNotEqualTo(applicationComment2);
        applicationComment1.setId(null);
        assertThat(applicationComment1).isNotEqualTo(applicationComment2);
    }
}
