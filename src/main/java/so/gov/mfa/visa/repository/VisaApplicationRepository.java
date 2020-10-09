package so.gov.mfa.visa.repository;

import so.gov.mfa.visa.domain.VisaApplication;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VisaApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisaApplicationRepository extends JpaRepository<VisaApplication, Long> {
}
