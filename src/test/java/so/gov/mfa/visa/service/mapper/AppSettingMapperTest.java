package so.gov.mfa.visa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AppSettingMapperTest {

    private AppSettingMapper appSettingMapper;

    @BeforeEach
    public void setUp() {
        appSettingMapper = new AppSettingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(appSettingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(appSettingMapper.fromId(null)).isNull();
    }
}
