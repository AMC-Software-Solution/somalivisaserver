package so.gov.mfa.visa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link so.gov.mfa.visa.domain.Country} entity.
 */
public class CountryDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 100)
    private String countryName;

    @Size(max = 3)
    private String countryIsoCode;

    private String countryFlagUrl;

    private String countryCallingCode;

    private Integer countryTelDigitLength;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    public String getCountryCallingCode() {
        return countryCallingCode;
    }

    public void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }

    public Integer getCountryTelDigitLength() {
        return countryTelDigitLength;
    }

    public void setCountryTelDigitLength(Integer countryTelDigitLength) {
        this.countryTelDigitLength = countryTelDigitLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryDTO)) {
            return false;
        }

        return id != null && id.equals(((CountryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryDTO{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", countryIsoCode='" + getCountryIsoCode() + "'" +
            ", countryFlagUrl='" + getCountryFlagUrl() + "'" +
            ", countryCallingCode='" + getCountryCallingCode() + "'" +
            ", countryTelDigitLength=" + getCountryTelDigitLength() +
            "}";
    }
}
