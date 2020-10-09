package so.gov.mfa.visa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class VisaApplicationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisaApplicationDTO.class);
        VisaApplicationDTO visaApplicationDTO1 = new VisaApplicationDTO();
        visaApplicationDTO1.setId(1L);
        VisaApplicationDTO visaApplicationDTO2 = new VisaApplicationDTO();
        assertThat(visaApplicationDTO1).isNotEqualTo(visaApplicationDTO2);
        visaApplicationDTO2.setId(visaApplicationDTO1.getId());
        assertThat(visaApplicationDTO1).isEqualTo(visaApplicationDTO2);
        visaApplicationDTO2.setId(2L);
        assertThat(visaApplicationDTO1).isNotEqualTo(visaApplicationDTO2);
        visaApplicationDTO1.setId(null);
        assertThat(visaApplicationDTO1).isNotEqualTo(visaApplicationDTO2);
    }
}
