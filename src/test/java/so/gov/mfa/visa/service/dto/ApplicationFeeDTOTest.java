package so.gov.mfa.visa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class ApplicationFeeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationFeeDTO.class);
        ApplicationFeeDTO applicationFeeDTO1 = new ApplicationFeeDTO();
        applicationFeeDTO1.setId(1L);
        ApplicationFeeDTO applicationFeeDTO2 = new ApplicationFeeDTO();
        assertThat(applicationFeeDTO1).isNotEqualTo(applicationFeeDTO2);
        applicationFeeDTO2.setId(applicationFeeDTO1.getId());
        assertThat(applicationFeeDTO1).isEqualTo(applicationFeeDTO2);
        applicationFeeDTO2.setId(2L);
        assertThat(applicationFeeDTO1).isNotEqualTo(applicationFeeDTO2);
        applicationFeeDTO1.setId(null);
        assertThat(applicationFeeDTO1).isNotEqualTo(applicationFeeDTO2);
    }
}
