package so.gov.mfa.visa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VisaApplicationMapperTest {

    private VisaApplicationMapper visaApplicationMapper;

    @BeforeEach
    public void setUp() {
        visaApplicationMapper = new VisaApplicationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(visaApplicationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(visaApplicationMapper.fromId(null)).isNull();
    }
}
