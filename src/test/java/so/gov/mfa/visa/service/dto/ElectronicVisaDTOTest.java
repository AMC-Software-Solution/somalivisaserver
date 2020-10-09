package so.gov.mfa.visa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class ElectronicVisaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElectronicVisaDTO.class);
        ElectronicVisaDTO electronicVisaDTO1 = new ElectronicVisaDTO();
        electronicVisaDTO1.setId(1L);
        ElectronicVisaDTO electronicVisaDTO2 = new ElectronicVisaDTO();
        assertThat(electronicVisaDTO1).isNotEqualTo(electronicVisaDTO2);
        electronicVisaDTO2.setId(electronicVisaDTO1.getId());
        assertThat(electronicVisaDTO1).isEqualTo(electronicVisaDTO2);
        electronicVisaDTO2.setId(2L);
        assertThat(electronicVisaDTO1).isNotEqualTo(electronicVisaDTO2);
        electronicVisaDTO1.setId(null);
        assertThat(electronicVisaDTO1).isNotEqualTo(electronicVisaDTO2);
    }
}
