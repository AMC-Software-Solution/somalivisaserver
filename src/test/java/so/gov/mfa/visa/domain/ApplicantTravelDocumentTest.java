package so.gov.mfa.visa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class ApplicantTravelDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantTravelDocument.class);
        ApplicantTravelDocument applicantTravelDocument1 = new ApplicantTravelDocument();
        applicantTravelDocument1.setId(1L);
        ApplicantTravelDocument applicantTravelDocument2 = new ApplicantTravelDocument();
        applicantTravelDocument2.setId(applicantTravelDocument1.getId());
        assertThat(applicantTravelDocument1).isEqualTo(applicantTravelDocument2);
        applicantTravelDocument2.setId(2L);
        assertThat(applicantTravelDocument1).isNotEqualTo(applicantTravelDocument2);
        applicantTravelDocument1.setId(null);
        assertThat(applicantTravelDocument1).isNotEqualTo(applicantTravelDocument2);
    }
}
