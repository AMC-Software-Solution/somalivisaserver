package so.gov.mfa.visa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

import so.gov.mfa.visa.domain.enumeration.CommenterType;

/**
 * A ApplicationComment.
 */
@Entity
@Table(name = "application_comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "applicationcomment")
public class ApplicationComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "comment", nullable = false)
    private String comment;

    @NotNull
    @Column(name = "comment_date", nullable = false)
    private ZonedDateTime commentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "commenter_type")
    private CommenterType commenterType;

    @ManyToOne
    @JsonIgnoreProperties(value = "applicationComments", allowSetters = true)
    private VisaApplication visaApplication;

    @ManyToOne
    @JsonIgnoreProperties(value = "applicationComments", allowSetters = true)
    private User commentedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public ApplicationComment title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public ApplicationComment comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ZonedDateTime getCommentDate() {
        return commentDate;
    }

    public ApplicationComment commentDate(ZonedDateTime commentDate) {
        this.commentDate = commentDate;
        return this;
    }

    public void setCommentDate(ZonedDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public CommenterType getCommenterType() {
        return commenterType;
    }

    public ApplicationComment commenterType(CommenterType commenterType) {
        this.commenterType = commenterType;
        return this;
    }

    public void setCommenterType(CommenterType commenterType) {
        this.commenterType = commenterType;
    }

    public VisaApplication getVisaApplication() {
        return visaApplication;
    }

    public ApplicationComment visaApplication(VisaApplication visaApplication) {
        this.visaApplication = visaApplication;
        return this;
    }

    public void setVisaApplication(VisaApplication visaApplication) {
        this.visaApplication = visaApplication;
    }

    public User getCommentedBy() {
        return commentedBy;
    }

    public ApplicationComment commentedBy(User user) {
        this.commentedBy = user;
        return this;
    }

    public void setCommentedBy(User user) {
        this.commentedBy = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationComment)) {
            return false;
        }
        return id != null && id.equals(((ApplicationComment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationComment{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", comment='" + getComment() + "'" +
            ", commentDate='" + getCommentDate() + "'" +
            ", commenterType='" + getCommenterType() + "'" +
            "}";
    }
}
