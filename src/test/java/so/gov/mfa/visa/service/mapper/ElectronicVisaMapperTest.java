package so.gov.mfa.visa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ElectronicVisaMapperTest {

    private ElectronicVisaMapper electronicVisaMapper;

    @BeforeEach
    public void setUp() {
        electronicVisaMapper = new ElectronicVisaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(electronicVisaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(electronicVisaMapper.fromId(null)).isNull();
    }
}
