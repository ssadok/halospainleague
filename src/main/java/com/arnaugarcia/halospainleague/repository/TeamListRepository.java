package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.TeamList;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TeamList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamListRepository extends JpaRepository<TeamList, Long> {

}
