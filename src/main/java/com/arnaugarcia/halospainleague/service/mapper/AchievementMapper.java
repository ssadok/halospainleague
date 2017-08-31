package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.AchievementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Achievement and its DTO AchievementDTO.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class, })
public interface AchievementMapper extends EntityMapper <AchievementDTO, Achievement> {
    
    
    default Achievement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Achievement achievement = new Achievement();
        achievement.setId(id);
        return achievement;
    }
}
