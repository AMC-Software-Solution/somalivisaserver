package so.gov.mfa.visa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link so.gov.mfa.visa.domain.ApplicationFee} entity.
 */
public class ApplicationFeeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double amount;

    @NotNull
    private String description;

    @NotNull
    private String currency;

    @NotNull
    private String currentIsoCode;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrentIsoCode() {
        return currentIsoCode;
    }

    public void setCurrentIsoCode(String currentIsoCode) {
        this.currentIsoCode = currentIsoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationFeeDTO)) {
            return false;
        }

        return id != null && id.equals(((ApplicationFeeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationFeeDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", currentIsoCode='" + getCurrentIsoCode() + "'" +
            "}";
    }
}
