package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.MapDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Map and its DTO MapDTO.
 */
@Mapper(componentModel = "spring", uses = {GameMapper.class, })
public interface MapMapper extends EntityMapper <MapDTO, Map> {

    @Mapping(source = "game.id", target = "gameId")
    @Mapping(source = "game.name", target = "gameName")
    MapDTO toDto(Map map); 
    @Mapping(target = "matches", ignore = true)

    @Mapping(source = "gameId", target = "game")
    Map toEntity(MapDTO mapDTO); 
    default Map fromId(Long id) {
        if (id == null) {
            return null;
        }
        Map map = new Map();
        map.setId(id);
        return map;
    }
}
