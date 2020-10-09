package so.gov.mfa.visa.repository;

import so.gov.mfa.visa.domain.ApplicantContactInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplicantContactInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantContactInfoRepository extends JpaRepository<ApplicantContactInfo, Long> {
}
