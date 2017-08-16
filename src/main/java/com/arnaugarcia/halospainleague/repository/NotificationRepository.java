package com.arnaugarcia.halospainleague.repository;

import com.arnaugarcia.halospainleague.domain.Notification;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Notification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    
}
