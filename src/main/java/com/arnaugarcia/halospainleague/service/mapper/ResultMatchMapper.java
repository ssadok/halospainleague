package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.ResultMatchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ResultMatch and its DTO ResultMatchDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResultMatchMapper extends EntityMapper <ResultMatchDTO, ResultMatch> {
    
    @Mapping(target = "match", ignore = true)
    ResultMatch toEntity(ResultMatchDTO resultMatchDTO); 
    default ResultMatch fromId(Long id) {
        if (id == null) {
            return null;
        }
        ResultMatch resultMatch = new ResultMatch();
        resultMatch.setId(id);
        return resultMatch;
    }
}
