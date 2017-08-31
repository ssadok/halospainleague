package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.TeamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Team and its DTO TeamDTO.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class, TeamListMapper.class, DivisionMapper.class, })
public interface TeamMapper extends EntityMapper <TeamDTO, Team> {

    @Mapping(source = "division.id", target = "divisionId")
    @Mapping(source = "division.name", target = "divisionName")
    TeamDTO toDto(Team team); 

    @Mapping(source = "divisionId", target = "division")
    @Mapping(target = "torunaments", ignore = true)
    Team toEntity(TeamDTO teamDTO); 
    default Team fromId(Long id) {
        if (id == null) {
            return null;
        }
        Team team = new Team();
        team.setId(id);
        return team;
    }
}
