package so.gov.mfa.visa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class ElectronicVisaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElectronicVisa.class);
        ElectronicVisa electronicVisa1 = new ElectronicVisa();
        electronicVisa1.setId(1L);
        ElectronicVisa electronicVisa2 = new ElectronicVisa();
        electronicVisa2.setId(electronicVisa1.getId());
        assertThat(electronicVisa1).isEqualTo(electronicVisa2);
        electronicVisa2.setId(2L);
        assertThat(electronicVisa1).isNotEqualTo(electronicVisa2);
        electronicVisa1.setId(null);
        assertThat(electronicVisa1).isNotEqualTo(electronicVisa2);
    }
}
