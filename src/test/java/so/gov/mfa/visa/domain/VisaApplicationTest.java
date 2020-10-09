package so.gov.mfa.visa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class VisaApplicationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisaApplication.class);
        VisaApplication visaApplication1 = new VisaApplication();
        visaApplication1.setId(1L);
        VisaApplication visaApplication2 = new VisaApplication();
        visaApplication2.setId(visaApplication1.getId());
        assertThat(visaApplication1).isEqualTo(visaApplication2);
        visaApplication2.setId(2L);
        assertThat(visaApplication1).isNotEqualTo(visaApplication2);
        visaApplication1.setId(null);
        assertThat(visaApplication1).isNotEqualTo(visaApplication2);
    }
}
