package so.gov.mfa.visa.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import so.gov.mfa.visa.domain.enumeration.TypeOfTravelDocument;

/**
 * A DTO for the {@link so.gov.mfa.visa.domain.ApplicantTravelDocument} entity.
 */
public class ApplicantTravelDocumentDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String documentNumber;

    @NotNull
    private LocalDate dateOfIssue;

    @NotNull
    private LocalDate expiryDate;

    @NotNull
    private String issuingAuthority;

    
    @Lob
    private byte[] documentPhoto;

    private String documentPhotoContentType;
    @NotNull
    private TypeOfTravelDocument typeOfDocument;


    private Long applicantId;

    private String applicantFullName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public byte[] getDocumentPhoto() {
        return documentPhoto;
    }

    public void setDocumentPhoto(byte[] documentPhoto) {
        this.documentPhoto = documentPhoto;
    }

    public String getDocumentPhotoContentType() {
        return documentPhotoContentType;
    }

    public void setDocumentPhotoContentType(String documentPhotoContentType) {
        this.documentPhotoContentType = documentPhotoContentType;
    }

    public TypeOfTravelDocument getTypeOfDocument() {
        return typeOfDocument;
    }

    public void setTypeOfDocument(TypeOfTravelDocument typeOfDocument) {
        this.typeOfDocument = typeOfDocument;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantFullName() {
        return applicantFullName;
    }

    public void setApplicantFullName(String applicantFullName) {
        this.applicantFullName = applicantFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantTravelDocumentDTO)) {
            return false;
        }

        return id != null && id.equals(((ApplicantTravelDocumentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantTravelDocumentDTO{" +
            "id=" + getId() +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", dateOfIssue='" + getDateOfIssue() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", issuingAuthority='" + getIssuingAuthority() + "'" +
            ", documentPhoto='" + getDocumentPhoto() + "'" +
            ", typeOfDocument='" + getTypeOfDocument() + "'" +
            ", applicantId=" + getApplicantId() +
            ", applicantFullName='" + getApplicantFullName() + "'" +
            "}";
    }
}
