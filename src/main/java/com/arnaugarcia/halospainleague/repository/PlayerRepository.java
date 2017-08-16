package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.Player;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Player entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {
    
    @Query("select distinct player from Player player left join fetch player.messageRooms")
    List<Player> findAllWithEagerRelationships();

    @Query("select player from Player player left join fetch player.messageRooms where player.id =:id")
    Player findOneWithEagerRelationships(@Param("id") Long id);
    
}
