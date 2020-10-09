package so.gov.mfa.visa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class SystemSettingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemSettingDTO.class);
        SystemSettingDTO systemSettingDTO1 = new SystemSettingDTO();
        systemSettingDTO1.setId(1L);
        SystemSettingDTO systemSettingDTO2 = new SystemSettingDTO();
        assertThat(systemSettingDTO1).isNotEqualTo(systemSettingDTO2);
        systemSettingDTO2.setId(systemSettingDTO1.getId());
        assertThat(systemSettingDTO1).isEqualTo(systemSettingDTO2);
        systemSettingDTO2.setId(2L);
        assertThat(systemSettingDTO1).isNotEqualTo(systemSettingDTO2);
        systemSettingDTO1.setId(null);
        assertThat(systemSettingDTO1).isNotEqualTo(systemSettingDTO2);
    }
}
