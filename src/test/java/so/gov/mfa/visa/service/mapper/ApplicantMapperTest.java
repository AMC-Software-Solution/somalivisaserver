package so.gov.mfa.visa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicantMapperTest {

    private ApplicantMapper applicantMapper;

    @BeforeEach
    public void setUp() {
        applicantMapper = new ApplicantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(applicantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(applicantMapper.fromId(null)).isNull();
    }
}
