package so.gov.mfa.visa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class ApplicantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantDTO.class);
        ApplicantDTO applicantDTO1 = new ApplicantDTO();
        applicantDTO1.setId(1L);
        ApplicantDTO applicantDTO2 = new ApplicantDTO();
        assertThat(applicantDTO1).isNotEqualTo(applicantDTO2);
        applicantDTO2.setId(applicantDTO1.getId());
        assertThat(applicantDTO1).isEqualTo(applicantDTO2);
        applicantDTO2.setId(2L);
        assertThat(applicantDTO1).isNotEqualTo(applicantDTO2);
        applicantDTO1.setId(null);
        assertThat(applicantDTO1).isNotEqualTo(applicantDTO2);
    }
}
