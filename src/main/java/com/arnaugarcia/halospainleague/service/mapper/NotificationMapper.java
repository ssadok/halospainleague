package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.NotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Notification and its DTO NotificationDTO.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class, })
public interface NotificationMapper extends EntityMapper <NotificationDTO, Notification> {

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "player.name", target = "playerName")
    NotificationDTO toDto(Notification notification); 

    @Mapping(source = "playerId", target = "player")
    Notification toEntity(NotificationDTO notificationDTO); 
    default Notification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notification notification = new Notification();
        notification.setId(id);
        return notification;
    }
}
