package so.gov.mfa.visa.repository;

import so.gov.mfa.visa.domain.ApplicationComment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ApplicationComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationCommentRepository extends JpaRepository<ApplicationComment, Long> {

    @Query("select applicationComment from ApplicationComment applicationComment where applicationComment.commentedBy.login = ?#{principal.username}")
    List<ApplicationComment> findByCommentedByIsCurrentUser();
}
