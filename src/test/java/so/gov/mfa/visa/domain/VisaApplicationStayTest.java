package so.gov.mfa.visa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class VisaApplicationStayTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisaApplicationStay.class);
        VisaApplicationStay visaApplicationStay1 = new VisaApplicationStay();
        visaApplicationStay1.setId(1L);
        VisaApplicationStay visaApplicationStay2 = new VisaApplicationStay();
        visaApplicationStay2.setId(visaApplicationStay1.getId());
        assertThat(visaApplicationStay1).isEqualTo(visaApplicationStay2);
        visaApplicationStay2.setId(2L);
        assertThat(visaApplicationStay1).isNotEqualTo(visaApplicationStay2);
        visaApplicationStay1.setId(null);
        assertThat(visaApplicationStay1).isNotEqualTo(visaApplicationStay2);
    }
}
