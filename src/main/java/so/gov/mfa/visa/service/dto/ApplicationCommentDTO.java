package so.gov.mfa.visa.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import so.gov.mfa.visa.domain.enumeration.CommenterType;

/**
 * A DTO for the {@link so.gov.mfa.visa.domain.ApplicationComment} entity.
 */
public class ApplicationCommentDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String comment;

    @NotNull
    private ZonedDateTime commentDate;

    private CommenterType commenterType;


    private Long visaApplicationId;

    private String visaApplicationApplicationName;

    private Long commentedById;

    private String commentedByLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ZonedDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(ZonedDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public CommenterType getCommenterType() {
        return commenterType;
    }

    public void setCommenterType(CommenterType commenterType) {
        this.commenterType = commenterType;
    }

    public Long getVisaApplicationId() {
        return visaApplicationId;
    }

    public void setVisaApplicationId(Long visaApplicationId) {
        this.visaApplicationId = visaApplicationId;
    }

    public String getVisaApplicationApplicationName() {
        return visaApplicationApplicationName;
    }

    public void setVisaApplicationApplicationName(String visaApplicationApplicationName) {
        this.visaApplicationApplicationName = visaApplicationApplicationName;
    }

    public Long getCommentedById() {
        return commentedById;
    }

    public void setCommentedById(Long userId) {
        this.commentedById = userId;
    }

    public String getCommentedByLogin() {
        return commentedByLogin;
    }

    public void setCommentedByLogin(String userLogin) {
        this.commentedByLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationCommentDTO)) {
            return false;
        }

        return id != null && id.equals(((ApplicationCommentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationCommentDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", comment='" + getComment() + "'" +
            ", commentDate='" + getCommentDate() + "'" +
            ", commenterType='" + getCommenterType() + "'" +
            ", visaApplicationId=" + getVisaApplicationId() +
            ", visaApplicationApplicationName='" + getVisaApplicationApplicationName() + "'" +
            ", commentedById=" + getCommentedById() +
            ", commentedByLogin='" + getCommentedByLogin() + "'" +
            "}";
    }
}
