package so.gov.mfa.visa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class ApplicantContactInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantContactInfo.class);
        ApplicantContactInfo applicantContactInfo1 = new ApplicantContactInfo();
        applicantContactInfo1.setId(1L);
        ApplicantContactInfo applicantContactInfo2 = new ApplicantContactInfo();
        applicantContactInfo2.setId(applicantContactInfo1.getId());
        assertThat(applicantContactInfo1).isEqualTo(applicantContactInfo2);
        applicantContactInfo2.setId(2L);
        assertThat(applicantContactInfo1).isNotEqualTo(applicantContactInfo2);
        applicantContactInfo1.setId(null);
        assertThat(applicantContactInfo1).isNotEqualTo(applicantContactInfo2);
    }
}
