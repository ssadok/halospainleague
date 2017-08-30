package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.MessageRoom;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the MessageRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
    @Query("select distinct message_room from MessageRoom message_room left join fetch message_room.players")
    List<MessageRoom> findAllWithEagerRelationships();

    @Query("select message_room from MessageRoom message_room left join fetch message_room.players where message_room.id =:id")
    MessageRoom findOneWithEagerRelationships(@Param("id") Long id);

}
