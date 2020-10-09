package so.gov.mfa.visa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicantTravelDocumentMapperTest {

    private ApplicantTravelDocumentMapper applicantTravelDocumentMapper;

    @BeforeEach
    public void setUp() {
        applicantTravelDocumentMapper = new ApplicantTravelDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(applicantTravelDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(applicantTravelDocumentMapper.fromId(null)).isNull();
    }
}
