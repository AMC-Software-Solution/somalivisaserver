package so.gov.mfa.visa.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link so.gov.mfa.visa.domain.ElectronicVisa} entity.
 */
public class ElectronicVisaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String visaNumber;

    @NotNull
    private String barcode;

    @NotNull
    private String nationality;

    @NotNull
    private String placeOfBirth;

    @NotNull
    private String travelDocument;

    @NotNull
    private LocalDate travelDocumentIssueDate;

    @NotNull
    private LocalDate travelDocumentExpiryDate;

    @NotNull
    private String travelPurpose;

    @NotNull
    private LocalDate visaValidFrom;

    @NotNull
    private LocalDate visaValidUntil;

    @NotNull
    private String visaValidityType;

    @NotNull
    private String visaType;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVisaNumber() {
        return visaNumber;
    }

    public void setVisaNumber(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getTravelDocument() {
        return travelDocument;
    }

    public void setTravelDocument(String travelDocument) {
        this.travelDocument = travelDocument;
    }

    public LocalDate getTravelDocumentIssueDate() {
        return travelDocumentIssueDate;
    }

    public void setTravelDocumentIssueDate(LocalDate travelDocumentIssueDate) {
        this.travelDocumentIssueDate = travelDocumentIssueDate;
    }

    public LocalDate getTravelDocumentExpiryDate() {
        return travelDocumentExpiryDate;
    }

    public void setTravelDocumentExpiryDate(LocalDate travelDocumentExpiryDate) {
        this.travelDocumentExpiryDate = travelDocumentExpiryDate;
    }

    public String getTravelPurpose() {
        return travelPurpose;
    }

    public void setTravelPurpose(String travelPurpose) {
        this.travelPurpose = travelPurpose;
    }

    public LocalDate getVisaValidFrom() {
        return visaValidFrom;
    }

    public void setVisaValidFrom(LocalDate visaValidFrom) {
        this.visaValidFrom = visaValidFrom;
    }

    public LocalDate getVisaValidUntil() {
        return visaValidUntil;
    }

    public void setVisaValidUntil(LocalDate visaValidUntil) {
        this.visaValidUntil = visaValidUntil;
    }

    public String getVisaValidityType() {
        return visaValidityType;
    }

    public void setVisaValidityType(String visaValidityType) {
        this.visaValidityType = visaValidityType;
    }

    public String getVisaType() {
        return visaType;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElectronicVisaDTO)) {
            return false;
        }

        return id != null && id.equals(((ElectronicVisaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElectronicVisaDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", visaNumber='" + getVisaNumber() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", placeOfBirth='" + getPlaceOfBirth() + "'" +
            ", travelDocument='" + getTravelDocument() + "'" +
            ", travelDocumentIssueDate='" + getTravelDocumentIssueDate() + "'" +
            ", travelDocumentExpiryDate='" + getTravelDocumentExpiryDate() + "'" +
            ", travelPurpose='" + getTravelPurpose() + "'" +
            ", visaValidFrom='" + getVisaValidFrom() + "'" +
            ", visaValidUntil='" + getVisaValidUntil() + "'" +
            ", visaValidityType='" + getVisaValidityType() + "'" +
            ", visaType='" + getVisaType() + "'" +
            "}";
    }
}
