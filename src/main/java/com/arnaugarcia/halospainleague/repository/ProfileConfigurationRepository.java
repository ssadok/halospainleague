package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.ProfileConfiguration;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProfileConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileConfigurationRepository extends JpaRepository<ProfileConfiguration, Long> {

}
