package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.TournamentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tournament and its DTO TournamentDTO.
 */
@Mapper(componentModel = "spring", uses = {TeamMapper.class, GameMapper.class, })
public interface TournamentMapper extends EntityMapper <TournamentDTO, Tournament> {

    @Mapping(source = "game.id", target = "gameId")
    @Mapping(source = "game.name", target = "gameName")
    TournamentDTO toDto(Tournament tournament); 
    @Mapping(target = "matches", ignore = true)

    @Mapping(source = "gameId", target = "game")
    Tournament toEntity(TournamentDTO tournamentDTO); 
    default Tournament fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tournament tournament = new Tournament();
        tournament.setId(id);
        return tournament;
    }
}
