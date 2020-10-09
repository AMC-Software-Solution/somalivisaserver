package so.gov.mfa.visa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicantContactInfoMapperTest {

    private ApplicantContactInfoMapper applicantContactInfoMapper;

    @BeforeEach
    public void setUp() {
        applicantContactInfoMapper = new ApplicantContactInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(applicantContactInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(applicantContactInfoMapper.fromId(null)).isNull();
    }
}
