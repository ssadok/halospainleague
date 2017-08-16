package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.SocialAccount;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SocialAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount,Long> {
    
}
