package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.MessageRoom;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MessageRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoom,Long> {
    
}
