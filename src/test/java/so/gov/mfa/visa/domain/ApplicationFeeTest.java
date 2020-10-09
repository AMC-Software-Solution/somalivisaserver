package so.gov.mfa.visa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class ApplicationFeeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationFee.class);
        ApplicationFee applicationFee1 = new ApplicationFee();
        applicationFee1.setId(1L);
        ApplicationFee applicationFee2 = new ApplicationFee();
        applicationFee2.setId(applicationFee1.getId());
        assertThat(applicationFee1).isEqualTo(applicationFee2);
        applicationFee2.setId(2L);
        assertThat(applicationFee1).isNotEqualTo(applicationFee2);
        applicationFee1.setId(null);
        assertThat(applicationFee1).isNotEqualTo(applicationFee2);
    }
}
