package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.PlayerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Player and its DTO PlayerDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProfileConfigurationMapper.class, CountryMapper.class, })
public interface PlayerMapper extends EntityMapper <PlayerDTO, Player> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")

    @Mapping(source = "profileConfiguration.id", target = "profileConfigurationId")

    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    PlayerDTO toDto(Player player); 

    @Mapping(source = "userId", target = "user")

    @Mapping(source = "profileConfigurationId", target = "profileConfiguration")
    @Mapping(target = "socialAccounts", ignore = true)

    @Mapping(source = "countryId", target = "country")
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "achievements", ignore = true)
    @Mapping(target = "teams", ignore = true)
    @Mapping(target = "messageRooms", ignore = true)
    Player toEntity(PlayerDTO playerDTO); 
    default Player fromId(Long id) {
        if (id == null) {
            return null;
        }
        Player player = new Player();
        player.setId(id);
        return player;
    }
}
