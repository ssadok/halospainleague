package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.TeamList;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the TeamList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamListRepository extends JpaRepository<TeamList, Long> {
    @Query("select distinct team_list from TeamList team_list left join fetch team_list.teams")
    List<TeamList> findAllWithEagerRelationships();

    @Query("select team_list from TeamList team_list left join fetch team_list.teams where team_list.id =:id")
    TeamList findOneWithEagerRelationships(@Param("id") Long id);

}
