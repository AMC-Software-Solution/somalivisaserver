package so.gov.mfa.visa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import so.gov.mfa.visa.web.rest.TestUtil;

public class AppSettingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppSetting.class);
        AppSetting appSetting1 = new AppSetting();
        appSetting1.setId(1L);
        AppSetting appSetting2 = new AppSetting();
        appSetting2.setId(appSetting1.getId());
        assertThat(appSetting1).isEqualTo(appSetting2);
        appSetting2.setId(2L);
        assertThat(appSetting1).isNotEqualTo(appSetting2);
        appSetting1.setId(null);
        assertThat(appSetting1).isNotEqualTo(appSetting2);
    }
}
