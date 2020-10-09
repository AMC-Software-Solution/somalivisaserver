package so.gov.mfa.visa.repository;

import so.gov.mfa.visa.domain.ApplicantTravelDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplicantTravelDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantTravelDocumentRepository extends JpaRepository<ApplicantTravelDocument, Long> {
}
