package so.gov.mfa.visa.service.mapper;


import so.gov.mfa.visa.domain.*;
import so.gov.mfa.visa.service.dto.VisaApplicationStayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VisaApplicationStay} and its DTO {@link VisaApplicationStayDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VisaApplicationStayMapper extends EntityMapper<VisaApplicationStayDTO, VisaApplicationStay> {


    @Mapping(target = "visaApplication", ignore = true)
    VisaApplicationStay toEntity(VisaApplicationStayDTO visaApplicationStayDTO);

    default VisaApplicationStay fromId(Long id) {
        if (id == null) {
            return null;
        }
        VisaApplicationStay visaApplicationStay = new VisaApplicationStay();
        visaApplicationStay.setId(id);
        return visaApplicationStay;
    }
}
