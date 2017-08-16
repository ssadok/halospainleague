package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.Division;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Division entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DivisionRepository extends JpaRepository<Division,Long> {
    
}
