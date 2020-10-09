package so.gov.mfa.visa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ElectronicVisa.
 */
@Entity
@Table(name = "electronic_visa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "electronicvisa")
public class ElectronicVisa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "visa_number", nullable = false)
    private String visaNumber;

    @NotNull
    @Column(name = "barcode", nullable = false)
    private String barcode;

    @NotNull
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @NotNull
    @Column(name = "place_of_birth", nullable = false)
    private String placeOfBirth;

    @NotNull
    @Column(name = "travel_document", nullable = false)
    private String travelDocument;

    @NotNull
    @Column(name = "travel_document_issue_date", nullable = false)
    private LocalDate travelDocumentIssueDate;

    @NotNull
    @Column(name = "travel_document_expiry_date", nullable = false)
    private LocalDate travelDocumentExpiryDate;

    @NotNull
    @Column(name = "travel_purpose", nullable = false)
    private String travelPurpose;

    @NotNull
    @Column(name = "visa_valid_from", nullable = false)
    private LocalDate visaValidFrom;

    @NotNull
    @Column(name = "visa_valid_until", nullable = false)
    private LocalDate visaValidUntil;

    @NotNull
    @Column(name = "visa_validity_type", nullable = false)
    private String visaValidityType;

    @NotNull
    @Column(name = "visa_type", nullable = false)
    private String visaType;

    @OneToOne(mappedBy = "electronicVisa")
    @JsonIgnore
    private VisaApplication visaApplication;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public ElectronicVisa firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ElectronicVisa lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVisaNumber() {
        return visaNumber;
    }

    public ElectronicVisa visaNumber(String visaNumber) {
        this.visaNumber = visaNumber;
        return this;
    }

    public void setVisaNumber(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    public String getBarcode() {
        return barcode;
    }

    public ElectronicVisa barcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNationality() {
        return nationality;
    }

    public ElectronicVisa nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public ElectronicVisa placeOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getTravelDocument() {
        return travelDocument;
    }

    public ElectronicVisa travelDocument(String travelDocument) {
        this.travelDocument = travelDocument;
        return this;
    }

    public void setTravelDocument(String travelDocument) {
        this.travelDocument = travelDocument;
    }

    public LocalDate getTravelDocumentIssueDate() {
        return travelDocumentIssueDate;
    }

    public ElectronicVisa travelDocumentIssueDate(LocalDate travelDocumentIssueDate) {
        this.travelDocumentIssueDate = travelDocumentIssueDate;
        return this;
    }

    public void setTravelDocumentIssueDate(LocalDate travelDocumentIssueDate) {
        this.travelDocumentIssueDate = travelDocumentIssueDate;
    }

    public LocalDate getTravelDocumentExpiryDate() {
        return travelDocumentExpiryDate;
    }

    public ElectronicVisa travelDocumentExpiryDate(LocalDate travelDocumentExpiryDate) {
        this.travelDocumentExpiryDate = travelDocumentExpiryDate;
        return this;
    }

    public void setTravelDocumentExpiryDate(LocalDate travelDocumentExpiryDate) {
        this.travelDocumentExpiryDate = travelDocumentExpiryDate;
    }

    public String getTravelPurpose() {
        return travelPurpose;
    }

    public ElectronicVisa travelPurpose(String travelPurpose) {
        this.travelPurpose = travelPurpose;
        return this;
    }

    public void setTravelPurpose(String travelPurpose) {
        this.travelPurpose = travelPurpose;
    }

    public LocalDate getVisaValidFrom() {
        return visaValidFrom;
    }

    public ElectronicVisa visaValidFrom(LocalDate visaValidFrom) {
        this.visaValidFrom = visaValidFrom;
        return this;
    }

    public void setVisaValidFrom(LocalDate visaValidFrom) {
        this.visaValidFrom = visaValidFrom;
    }

    public LocalDate getVisaValidUntil() {
        return visaValidUntil;
    }

    public ElectronicVisa visaValidUntil(LocalDate visaValidUntil) {
        this.visaValidUntil = visaValidUntil;
        return this;
    }

    public void setVisaValidUntil(LocalDate visaValidUntil) {
        this.visaValidUntil = visaValidUntil;
    }

    public String getVisaValidityType() {
        return visaValidityType;
    }

    public ElectronicVisa visaValidityType(String visaValidityType) {
        this.visaValidityType = visaValidityType;
        return this;
    }

    public void setVisaValidityType(String visaValidityType) {
        this.visaValidityType = visaValidityType;
    }

    public String getVisaType() {
        return visaType;
    }

    public ElectronicVisa visaType(String visaType) {
        this.visaType = visaType;
        return this;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }

    public VisaApplication getVisaApplication() {
        return visaApplication;
    }

    public ElectronicVisa visaApplication(VisaApplication visaApplication) {
        this.visaApplication = visaApplication;
        return this;
    }

    public void setVisaApplication(VisaApplication visaApplication) {
        this.visaApplication = visaApplication;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElectronicVisa)) {
            return false;
        }
        return id != null && id.equals(((ElectronicVisa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElectronicVisa{" +
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
