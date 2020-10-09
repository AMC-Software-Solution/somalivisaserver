package so.gov.mfa.visa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SystemSettingMapperTest {

    private SystemSettingMapper systemSettingMapper;

    @BeforeEach
    public void setUp() {
        systemSettingMapper = new SystemSettingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(systemSettingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(systemSettingMapper.fromId(null)).isNull();
    }
}
