package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.ResultMatch;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ResultMatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultMatchRepository extends JpaRepository<ResultMatch,Long> {
    
}
