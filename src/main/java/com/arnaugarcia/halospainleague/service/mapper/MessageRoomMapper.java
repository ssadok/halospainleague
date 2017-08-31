package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.MessageRoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MessageRoom and its DTO MessageRoomDTO.
 */
@Mapper(componentModel = "spring", uses = {PlayerMapper.class, })
public interface MessageRoomMapper extends EntityMapper <MessageRoomDTO, MessageRoom> {
    
    @Mapping(target = "themes", ignore = true)
    @Mapping(target = "message", ignore = true)
    MessageRoom toEntity(MessageRoomDTO messageRoomDTO); 
    default MessageRoom fromId(Long id) {
        if (id == null) {
            return null;
        }
        MessageRoom messageRoom = new MessageRoom();
        messageRoom.setId(id);
        return messageRoom;
    }
}
