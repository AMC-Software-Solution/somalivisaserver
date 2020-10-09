package so.gov.mfa.visa.service.mapper;


import so.gov.mfa.visa.domain.*;
import so.gov.mfa.visa.service.dto.ApplicantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Applicant} and its DTO {@link ApplicantDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicantContactInfoMapper.class, CountryMapper.class})
public interface ApplicantMapper extends EntityMapper<ApplicantDTO, Applicant> {

    @Mapping(source = "applicantContactInfo.id", target = "applicantContactInfoId")
    @Mapping(source = "applicantContactInfo.email", target = "applicantContactInfoEmail")
    @Mapping(source = "nationality.id", target = "nationalityId")
    @Mapping(source = "nationality.countryName", target = "nationalityCountryName")
    @Mapping(source = "countryOfBirth.id", target = "countryOfBirthId")
    @Mapping(source = "countryOfBirth.countryName", target = "countryOfBirthCountryName")
    @Mapping(source = "nationalityAtBirth.id", target = "nationalityAtBirthId")
    @Mapping(source = "nationalityAtBirth.countryName", target = "nationalityAtBirthCountryName")
    ApplicantDTO toDto(Applicant applicant);

    @Mapping(source = "applicantContactInfoId", target = "applicantContactInfo")
    @Mapping(source = "nationalityId", target = "nationality")
    @Mapping(source = "countryOfBirthId", target = "countryOfBirth")
    @Mapping(source = "nationalityAtBirthId", target = "nationalityAtBirth")
    Applicant toEntity(ApplicantDTO applicantDTO);

    default Applicant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Applicant applicant = new Applicant();
        applicant.setId(id);
        return applicant;
    }
}
