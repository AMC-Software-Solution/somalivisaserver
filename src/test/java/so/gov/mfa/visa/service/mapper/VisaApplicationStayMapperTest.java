package so.gov.mfa.visa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VisaApplicationStayMapperTest {

    private VisaApplicationStayMapper visaApplicationStayMapper;

    @BeforeEach
    public void setUp() {
        visaApplicationStayMapper = new VisaApplicationStayMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(visaApplicationStayMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(visaApplicationStayMapper.fromId(null)).isNull();
    }
}
