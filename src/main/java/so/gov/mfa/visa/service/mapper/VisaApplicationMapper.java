package so.gov.mfa.visa.service.mapper;


import so.gov.mfa.visa.domain.*;
import so.gov.mfa.visa.service.dto.VisaApplicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VisaApplication} and its DTO {@link VisaApplicationDTO}.
 */
@Mapper(componentModel = "spring", uses = {VisaApplicationStayMapper.class, ApplicationFeeMapper.class, ElectronicVisaMapper.class, EmployeeMapper.class})
public interface VisaApplicationMapper extends EntityMapper<VisaApplicationDTO, VisaApplication> {

    @Mapping(source = "visaApplicationStay.id", target = "visaApplicationStayId")
    @Mapping(source = "visaApplicationStay.stayLocationFullAddress", target = "visaApplicationStayStayLocationFullAddress")
    @Mapping(source = "applicationFee.id", target = "applicationFeeId")
    @Mapping(source = "applicationFee.description", target = "applicationFeeDescription")
    @Mapping(source = "electronicVisa.id", target = "electronicVisaId")
    @Mapping(source = "electronicVisa.visaNumber", target = "electronicVisaVisaNumber")
    @Mapping(source = "approvedBy.id", target = "approvedById")
    @Mapping(source = "approvedBy.employeeFullName", target = "approvedByEmployeeFullName")
    VisaApplicationDTO toDto(VisaApplication visaApplication);

    @Mapping(source = "visaApplicationStayId", target = "visaApplicationStay")
    @Mapping(source = "applicationFeeId", target = "applicationFee")
    @Mapping(source = "electronicVisaId", target = "electronicVisa")
    @Mapping(source = "approvedById", target = "approvedBy")
    VisaApplication toEntity(VisaApplicationDTO visaApplicationDTO);

    default VisaApplication fromId(Long id) {
        if (id == null) {
            return null;
        }
        VisaApplication visaApplication = new VisaApplication();
        visaApplication.setId(id);
        return visaApplication;
    }
}
