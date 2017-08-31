package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.SocialAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SocialAccount and its DTO SocialAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class, })
public interface SocialAccountMapper extends EntityMapper <SocialAccountDTO, SocialAccount> {

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "player.name", target = "playerName")
    SocialAccountDTO toDto(SocialAccount socialAccount); 

    @Mapping(source = "playerId", target = "player")
    SocialAccount toEntity(SocialAccountDTO socialAccountDTO); 
    default SocialAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        SocialAccount socialAccount = new SocialAccount();
        socialAccount.setId(id);
        return socialAccount;
    }
}
