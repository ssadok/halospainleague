package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.MatchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Match and its DTO MatchDTO.
 */
@Mapper(componentModel = "spring", uses = {ResultMatchMapper.class, TeamListMapper.class, TournamentMapper.class, MapMapper.class, })
public interface MatchMapper extends EntityMapper <MatchDTO, Match> {

    @Mapping(source = "resultMatch.id", target = "resultMatchId")
    @Mapping(source = "resultMatch.reference", target = "resultMatchReference")

    @Mapping(source = "teamList.id", target = "teamListId")
    @Mapping(source = "teamList.reference", target = "teamListReference")

    @Mapping(source = "tournament.id", target = "tournamentId")
    @Mapping(source = "tournament.name", target = "tournamentName")

    @Mapping(source = "map.id", target = "mapId")
    @Mapping(source = "map.name", target = "mapName")
    MatchDTO toDto(Match match); 

    @Mapping(source = "resultMatchId", target = "resultMatch")

    @Mapping(source = "teamListId", target = "teamList")

    @Mapping(source = "tournamentId", target = "tournament")

    @Mapping(source = "mapId", target = "map")
    @Mapping(target = "matchModes", ignore = true)
    Match toEntity(MatchDTO matchDTO); 
    default Match fromId(Long id) {
        if (id == null) {
            return null;
        }
        Match match = new Match();
        match.setId(id);
        return match;
    }
}
