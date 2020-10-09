package so.gov.mfa.visa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class ApplicantTravelDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicantTravelDocumentDTO.class);
        ApplicantTravelDocumentDTO applicantTravelDocumentDTO1 = new ApplicantTravelDocumentDTO();
        applicantTravelDocumentDTO1.setId(1L);
        ApplicantTravelDocumentDTO applicantTravelDocumentDTO2 = new ApplicantTravelDocumentDTO();
        assertThat(applicantTravelDocumentDTO1).isNotEqualTo(applicantTravelDocumentDTO2);
        applicantTravelDocumentDTO2.setId(applicantTravelDocumentDTO1.getId());
        assertThat(applicantTravelDocumentDTO1).isEqualTo(applicantTravelDocumentDTO2);
        applicantTravelDocumentDTO2.setId(2L);
        assertThat(applicantTravelDocumentDTO1).isNotEqualTo(applicantTravelDocumentDTO2);
        applicantTravelDocumentDTO1.setId(null);
        assertThat(applicantTravelDocumentDTO1).isNotEqualTo(applicantTravelDocumentDTO2);
    }
}
