package so.gov.mfa.visa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationCommentMapperTest {

    private ApplicationCommentMapper applicationCommentMapper;

    @BeforeEach
    public void setUp() {
        applicationCommentMapper = new ApplicationCommentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(applicationCommentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(applicationCommentMapper.fromId(null)).isNull();
    }
}
