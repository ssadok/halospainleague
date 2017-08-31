package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.MatchModeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MatchMode and its DTO MatchModeDTO.
 */
@Mapper(componentModel = "spring", uses = {MatchMapper.class, })
public interface MatchModeMapper extends EntityMapper <MatchModeDTO, MatchMode> {

    @Mapping(source = "match.id", target = "matchId")
    @Mapping(source = "match.reference", target = "matchReference")
    MatchModeDTO toDto(MatchMode matchMode); 

    @Mapping(source = "matchId", target = "match")
    MatchMode toEntity(MatchModeDTO matchModeDTO); 
    default MatchMode fromId(Long id) {
        if (id == null) {
            return null;
        }
        MatchMode matchMode = new MatchMode();
        matchMode.setId(id);
        return matchMode;
    }
}
