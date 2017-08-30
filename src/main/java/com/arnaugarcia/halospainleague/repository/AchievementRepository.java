package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.Achievement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Achievement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    @Query("select distinct achievement from Achievement achievement left join fetch achievement.players")
    List<Achievement> findAllWithEagerRelationships();

    @Query("select achievement from Achievement achievement left join fetch achievement.players where achievement.id =:id")
    Achievement findOneWithEagerRelationships(@Param("id") Long id);

}
