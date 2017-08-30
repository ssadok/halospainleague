package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.Tournament;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Tournament entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    @Query("select distinct tournament from Tournament tournament left join fetch tournament.teams")
    List<Tournament> findAllWithEagerRelationships();

    @Query("select tournament from Tournament tournament left join fetch tournament.teams where tournament.id =:id")
    Tournament findOneWithEagerRelationships(@Param("id") Long id);

}
