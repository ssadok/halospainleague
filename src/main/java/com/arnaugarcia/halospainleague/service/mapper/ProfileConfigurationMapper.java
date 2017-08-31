package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.ProfileConfigurationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProfileConfiguration and its DTO ProfileConfigurationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfileConfigurationMapper extends EntityMapper <ProfileConfigurationDTO, ProfileConfiguration> {
    
    @Mapping(target = "themes", ignore = true)
    @Mapping(target = "player", ignore = true)
    ProfileConfiguration toEntity(ProfileConfigurationDTO profileConfigurationDTO); 
    default ProfileConfiguration fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfileConfiguration profileConfiguration = new ProfileConfiguration();
        profileConfiguration.setId(id);
        return profileConfiguration;
    }
}
