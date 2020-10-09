package so.gov.mfa.visa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class ApplicantContactInfoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantContactInfoDTO.class);
        ApplicantContactInfoDTO applicantContactInfoDTO1 = new ApplicantContactInfoDTO();
        applicantContactInfoDTO1.setId(1L);
        ApplicantContactInfoDTO applicantContactInfoDTO2 = new ApplicantContactInfoDTO();
        assertThat(applicantContactInfoDTO1).isNotEqualTo(applicantContactInfoDTO2);
        applicantContactInfoDTO2.setId(applicantContactInfoDTO1.getId());
        assertThat(applicantContactInfoDTO1).isEqualTo(applicantContactInfoDTO2);
        applicantContactInfoDTO2.setId(2L);
        assertThat(applicantContactInfoDTO1).isNotEqualTo(applicantContactInfoDTO2);
        applicantContactInfoDTO1.setId(null);
        assertThat(applicantContactInfoDTO1).isNotEqualTo(applicantContactInfoDTO2);
    }
}
