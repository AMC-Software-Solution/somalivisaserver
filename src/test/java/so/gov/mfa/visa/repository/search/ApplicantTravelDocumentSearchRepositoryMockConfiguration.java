package so.gov.mfa.visa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ApplicantTravelDocumentSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ApplicantTravelDocumentSearchRepositoryMockConfiguration {

    @MockBean
    private ApplicantTravelDocumentSearchRepository mockApplicantTravelDocumentSearchRepository;

}
