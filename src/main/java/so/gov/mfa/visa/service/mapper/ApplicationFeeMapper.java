package so.gov.mfa.visa.service.mapper;


import so.gov.mfa.visa.domain.*;
import so.gov.mfa.visa.service.dto.ApplicationFeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationFee} and its DTO {@link ApplicationFeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationFeeMapper extends EntityMapper<ApplicationFeeDTO, ApplicationFee> {


    @Mapping(target = "visaApplication", ignore = true)
    ApplicationFee toEntity(ApplicationFeeDTO applicationFeeDTO);

    default ApplicationFee fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationFee applicationFee = new ApplicationFee();
        applicationFee.setId(id);
        return applicationFee;
    }
}
