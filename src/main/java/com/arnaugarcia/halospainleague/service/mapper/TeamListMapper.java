package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.TeamListDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TeamList and its DTO TeamListDTO.
 */
@Mapper(componentModel = "spring", uses = {TeamMapper.class, })
public interface TeamListMapper extends EntityMapper <TeamListDTO, TeamList> {
    
    @Mapping(target = "match", ignore = true)
    TeamList toEntity(TeamListDTO teamListDTO); 
    default TeamList fromId(Long id) {
        if (id == null) {
            return null;
        }
        TeamList teamList = new TeamList();
        teamList.setId(id);
        return teamList;
    }
}
