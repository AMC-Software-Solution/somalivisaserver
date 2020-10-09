package so.gov.mfa.visa.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import so.gov.mfa.visa.domain.enumeration.Gender;
import so.gov.mfa.visa.domain.enumeration.MaritalStatus;

/**
 * A DTO for the {@link so.gov.mfa.visa.domain.Applicant} entity.
 */
public class ApplicantDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String firstName;

    @NotNull
    private String middleNames;

    @NotNull
    private String lastName;

    private String fullName;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private String placeOfBirth;

    @NotNull
    private Gender sex;

    @NotNull
    private MaritalStatus maritalStatus;

    @NotNull
    private String occupation;

    
    @Lob
    private byte[] photo;

    private String photoContentType;

    private Long applicantContactInfoId;

    private String applicantContactInfoEmail;

    private Long nationalityId;

    private String nationalityCountryName;

    private Long countryOfBirthId;

    private String countryOfBirthCountryName;

    private Long nationalityAtBirthId;

    private String nationalityAtBirthCountryName;
    
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(String middleNames) {
        this.middleNames = middleNames;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Long getApplicantContactInfoId() {
        return applicantContactInfoId;
    }

    public void setApplicantContactInfoId(Long applicantContactInfoId) {
        this.applicantContactInfoId = applicantContactInfoId;
    }

    public String getApplicantContactInfoEmail() {
        return applicantContactInfoEmail;
    }

    public void setApplicantContactInfoEmail(String applicantContactInfoEmail) {
        this.applicantContactInfoEmail = applicantContactInfoEmail;
    }

    public Long getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Long countryId) {
        this.nationalityId = countryId;
    }

    public String getNationalityCountryName() {
        return nationalityCountryName;
    }

    public void setNationalityCountryName(String countryCountryName) {
        this.nationalityCountryName = countryCountryName;
    }

    public Long getCountryOfBirthId() {
        return countryOfBirthId;
    }

    public void setCountryOfBirthId(Long countryId) {
        this.countryOfBirthId = countryId;
    }

    public String getCountryOfBirthCountryName() {
        return countryOfBirthCountryName;
    }

    public void setCountryOfBirthCountryName(String countryCountryName) {
        this.countryOfBirthCountryName = countryCountryName;
    }

    public Long getNationalityAtBirthId() {
        return nationalityAtBirthId;
    }

    public void setNationalityAtBirthId(Long countryId) {
        this.nationalityAtBirthId = countryId;
    }

    public String getNationalityAtBirthCountryName() {
        return nationalityAtBirthCountryName;
    }

    public void setNationalityAtBirthCountryName(String countryCountryName) {
        this.nationalityAtBirthCountryName = countryCountryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicantDTO)) {
            return false;
        }

        return id != null && id.equals(((ApplicantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicantDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleNames='" + getMiddleNames() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", placeOfBirth='" + getPlaceOfBirth() + "'" +
            ", sex='" + getSex() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", applicantContactInfoId=" + getApplicantContactInfoId() +
            ", applicantContactInfoEmail='" + getApplicantContactInfoEmail() + "'" +
            ", nationalityId=" + getNationalityId() +
            ", nationalityCountryName='" + getNationalityCountryName() + "'" +
            ", countryOfBirthId=" + getCountryOfBirthId() +
            ", countryOfBirthCountryName='" + getCountryOfBirthCountryName() + "'" +
            ", nationalityAtBirthId=" + getNationalityAtBirthId() +
            ", nationalityAtBirthCountryName='" + getNationalityAtBirthCountryName() + "'" +
            "}";
    }
}
