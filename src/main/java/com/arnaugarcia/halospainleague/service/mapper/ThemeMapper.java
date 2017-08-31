package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.ThemeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Theme and its DTO ThemeDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfileConfigurationMapper.class, MessageRoomMapper.class, })
public interface ThemeMapper extends EntityMapper <ThemeDTO, Theme> {

    @Mapping(source = "profileConfiguration.id", target = "profileConfigurationId")

    @Mapping(source = "messageRoom.id", target = "messageRoomId")
    @Mapping(source = "messageRoom.title", target = "messageRoomTitle")
    ThemeDTO toDto(Theme theme); 

    @Mapping(source = "profileConfigurationId", target = "profileConfiguration")

    @Mapping(source = "messageRoomId", target = "messageRoom")
    Theme toEntity(ThemeDTO themeDTO); 
    default Theme fromId(Long id) {
        if (id == null) {
            return null;
        }
        Theme theme = new Theme();
        theme.setId(id);
        return theme;
    }
}
