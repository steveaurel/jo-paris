package com.infoevent.joparis.venue.repositories;


import com.infoevent.joparis.venue.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Venue entity.
 * Extends JpaRepository to provide CRUD operations for Venue entities.
 */
@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    List<Venue> findAllByOrderByNameAsc();
}
