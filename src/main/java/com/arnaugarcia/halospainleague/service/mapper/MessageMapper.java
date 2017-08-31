package com.arnaugarcia.halospainleague.service.mapper;

import com.arnaugarcia.halospainleague.domain.*;
import com.arnaugarcia.halospainleague.service.dto.MessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Message and its DTO MessageDTO.
 */
@Mapper(componentModel = "spring", uses = {MessageRoomMapper.class, })
public interface MessageMapper extends EntityMapper <MessageDTO, Message> {

    @Mapping(source = "messageRoom.id", target = "messageRoomId")
    @Mapping(source = "messageRoom.title", target = "messageRoomTitle")
    MessageDTO toDto(Message message); 

    @Mapping(source = "messageRoomId", target = "messageRoom")
    Message toEntity(MessageDTO messageDTO); 
    default Message fromId(Long id) {
        if (id == null) {
            return null;
        }
        Message message = new Message();
        message.setId(id);
        return message;
    }
}
