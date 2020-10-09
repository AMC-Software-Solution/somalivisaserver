package so.gov.mfa.visa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class AppSettingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppSettingDTO.class);
        AppSettingDTO appSettingDTO1 = new AppSettingDTO();
        appSettingDTO1.setId(1L);
        AppSettingDTO appSettingDTO2 = new AppSettingDTO();
        assertThat(appSettingDTO1).isNotEqualTo(appSettingDTO2);
        appSettingDTO2.setId(appSettingDTO1.getId());
        assertThat(appSettingDTO1).isEqualTo(appSettingDTO2);
        appSettingDTO2.setId(2L);
        assertThat(appSettingDTO1).isNotEqualTo(appSettingDTO2);
        appSettingDTO1.setId(null);
        assertThat(appSettingDTO1).isNotEqualTo(appSettingDTO2);
    }
}
