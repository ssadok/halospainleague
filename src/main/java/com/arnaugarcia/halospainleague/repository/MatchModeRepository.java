package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.MatchMode;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MatchMode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchModeRepository extends JpaRepository<MatchMode, Long> {

}
