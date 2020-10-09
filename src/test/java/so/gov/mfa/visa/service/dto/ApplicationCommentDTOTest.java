package so.gov.mfa.visa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class ApplicationCommentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationCommentDTO.class);
        ApplicationCommentDTO applicationCommentDTO1 = new ApplicationCommentDTO();
        applicationCommentDTO1.setId(1L);
        ApplicationCommentDTO applicationCommentDTO2 = new ApplicationCommentDTO();
        assertThat(applicationCommentDTO1).isNotEqualTo(applicationCommentDTO2);
        applicationCommentDTO2.setId(applicationCommentDTO1.getId());
        assertThat(applicationCommentDTO1).isEqualTo(applicationCommentDTO2);
        applicationCommentDTO2.setId(2L);
        assertThat(applicationCommentDTO1).isNotEqualTo(applicationCommentDTO2);
        applicationCommentDTO1.setId(null);
        assertThat(applicationCommentDTO1).isNotEqualTo(applicationCommentDTO2);
    }
}
