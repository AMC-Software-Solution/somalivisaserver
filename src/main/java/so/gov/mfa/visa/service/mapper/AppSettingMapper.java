package so.gov.mfa.visa.service.mapper;


import so.gov.mfa.visa.domain.*;
import so.gov.mfa.visa.service.dto.AppSettingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppSetting} and its DTO {@link AppSettingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AppSettingMapper extends EntityMapper<AppSettingDTO, AppSetting> {



    default AppSetting fromId(Long id) {
        if (id == null) {
            return null;
        }
        AppSetting appSetting = new AppSetting();
        appSetting.setId(id);
        return appSetting;
    }
}
