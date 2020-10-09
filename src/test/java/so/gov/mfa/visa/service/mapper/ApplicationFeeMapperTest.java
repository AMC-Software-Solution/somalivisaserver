package so.gov.mfa.visa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationFeeMapperTest {

    private ApplicationFeeMapper applicationFeeMapper;

    @BeforeEach
    public void setUp() {
        applicationFeeMapper = new ApplicationFeeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(applicationFeeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(applicationFeeMapper.fromId(null)).isNull();
    }
}
