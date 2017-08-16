package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.Theme;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Theme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThemeRepository extends JpaRepository<Theme,Long> {
    
}
