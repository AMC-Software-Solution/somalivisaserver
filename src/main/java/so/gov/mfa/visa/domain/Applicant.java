package so.gov.mfa.visa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import so.gov.mfa.visa.domain.enumeration.Gender;

import so.gov.mfa.visa.domain.enumeration.MaritalStatus;

/**
 * A Applicant.
 */
@Entity
@Table(name = "applicant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "applicant")
public class Applicant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "middle_names", nullable = false)
    private String middleNames;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "place_of_birth", nullable = false)
    private String placeOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Gender sex;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;

    @NotNull
    @Column(name = "occupation", nullable = false)
    private String occupation;

    
    @Lob
    @Column(name = "photo", nullable = false)
    private byte[] photo;

    @Column(name = "photo_content_type", nullable = false)
    private String photoContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private ApplicantContactInfo applicantContactInfo;

    @ManyToOne
    @JsonIgnoreProperties(value = "applicants", allowSetters = true)
    private Country nationality;

    @ManyToOne
    @JsonIgnoreProperties(value = "applicants", allowSetters = true)
    private Country countryOfBirth;

    @ManyToOne
    @JsonIgnoreProperties(value = "applicants", allowSetters = true)
    private Country nationalityAtBirth;

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

    public Applicant title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public Applicant firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleNames() {
        return middleNames;
    }

    public Applicant middleNames(String middleNames) {
        this.middleNames = middleNames;
        return this;
    }

    public void setMiddleNames(String middleNames) {
        this.middleNames = middleNames;
    }

    public String getLastName() {
        return lastName;
    }

    public Applicant lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public Applicant fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Applicant dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public Applicant placeOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Gender getSex() {
        return sex;
    }

    public Applicant sex(Gender sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public Applicant maritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public Applicant occupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Applicant photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Applicant photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public ApplicantContactInfo getApplicantContactInfo() {
        return applicantContactInfo;
    }

    public Applicant applicantContactInfo(ApplicantContactInfo applicantContactInfo) {
        this.applicantContactInfo = applicantContactInfo;
        return this;
    }

    public void setApplicantContactInfo(ApplicantContactInfo applicantContactInfo) {
        this.applicantContactInfo = applicantContactInfo;
    }

    public Country getNationality() {
        return nationality;
    }

    public Applicant nationality(Country country) {
        this.nationality = country;
        return this;
    }

    public void setNationality(Country country) {
        this.nationality = country;
    }

    public Country getCountryOfBirth() {
        return countryOfBirth;
    }

    public Applicant countryOfBirth(Country country) {
        this.countryOfBirth = country;
        return this;
    }

    public void setCountryOfBirth(Country country) {
        this.countryOfBirth = country;
    }

    public Country getNationalityAtBirth() {
        return nationalityAtBirth;
    }

    public Applicant nationalityAtBirth(Country country) {
        this.nationalityAtBirth = country;
        return this;
    }

    public void setNationalityAtBirth(Country country) {
        this.nationalityAtBirth = country;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Applicant)) {
            return false;
        }
        return id != null && id.equals(((Applicant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Applicant{" +
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
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
