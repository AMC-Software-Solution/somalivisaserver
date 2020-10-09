package so.gov.mfa.visa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import so.gov.mfa.visa.domain.enumeration.TypeOfTravelDocument;

/**
 * A ApplicantTravelDocument.
 */
@Entity
@Table(name = "applicant_travel_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "applicanttraveldocument")
public class ApplicantTravelDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @NotNull
    @Column(name = "date_of_issue", nullable = false)
    private LocalDate dateOfIssue;

    @NotNull
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @NotNull
    @Column(name = "issuing_authority", nullable = false)
    private String issuingAuthority;

    
    @Lob
    @Column(name = "document_photo", nullable = false)
    private byte[] documentPhoto;

    @Column(name = "document_photo_content_type", nullable = false)
    private String documentPhotoContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_document", nullable = false)
    private TypeOfTravelDocument typeOfDocument;

    @ManyToOne
    @JsonIgnoreProperties(value = "applicantTravelDocuments", allowSetters = true)
    private Applicant applicant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public ApplicantTravelDocument documentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public ApplicantTravelDocument dateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
        return this;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public ApplicantTravelDocument expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public ApplicantTravelDocument issuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
        return this;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public byte[] getDocumentPhoto() {
        return documentPhoto;
    }

    public ApplicantTravelDocument documentPhoto(byte[] documentPhoto) {
        this.documentPhoto = documentPhoto;
        return this;
    }

    public void setDocumentPhoto(byte[] documentPhoto) {
        this.documentPhoto = documentPhoto;
    }

    public String getDocumentPhotoContentType() {
        return documentPhotoContentType;
    }

    public ApplicantTravelDocument documentPhotoContentType(String documentPhotoContentType) {
        this.documentPhotoContentType = documentPhotoContentType;
        return this;
    }

    public void setDocumentPhotoContentType(String documentPhotoContentType) {
        this.documentPhotoContentType = documentPhotoContentType;
    }

    public TypeOfTravelDocument getTypeOfDocument() {
        return typeOfDocument;
    }

    public ApplicantTravelDocument typeOfDocument(TypeOfTravelDocument typeOfDocument) {
        this.typeOfDocument = typeOfDocument;
        return this;
    }

    public void setTypeOfDocument(TypeOfTravelDocument typeOfDocument) {
        this.typeOfDocument = typeOfDocument;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public ApplicantTravelDocument applicant(Applicant applicant) {
        this.applicant = applicant;
        return this;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantTravelDocument)) {
            return false;
        }
        return id != null && id.equals(((ApplicantTravelDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantTravelDocument{" +
            "id=" + getId() +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", dateOfIssue='" + getDateOfIssue() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", issuingAuthority='" + getIssuingAuthority() + "'" +
            ", documentPhoto='" + getDocumentPhoto() + "'" +
            ", documentPhotoContentType='" + getDocumentPhotoContentType() + "'" +
            ", typeOfDocument='" + getTypeOfDocument() + "'" +
            "}";
    }
}
