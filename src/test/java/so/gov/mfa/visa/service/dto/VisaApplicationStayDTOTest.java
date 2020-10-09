package so.gov.mfa.visa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class VisaApplicationStayDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisaApplicationStayDTO.class);
        VisaApplicationStayDTO visaApplicationStayDTO1 = new VisaApplicationStayDTO();
        visaApplicationStayDTO1.setId(1L);
        VisaApplicationStayDTO visaApplicationStayDTO2 = new VisaApplicationStayDTO();
        assertThat(visaApplicationStayDTO1).isNotEqualTo(visaApplicationStayDTO2);
        visaApplicationStayDTO2.setId(visaApplicationStayDTO1.getId());
        assertThat(visaApplicationStayDTO1).isEqualTo(visaApplicationStayDTO2);
        visaApplicationStayDTO2.setId(2L);
        assertThat(visaApplicationStayDTO1).isNotEqualTo(visaApplicationStayDTO2);
        visaApplicationStayDTO1.setId(null);
        assertThat(visaApplicationStayDTO1).isNotEqualTo(visaApplicationStayDTO2);
    }
}
