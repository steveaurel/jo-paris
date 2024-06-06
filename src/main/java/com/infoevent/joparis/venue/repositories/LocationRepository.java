package com.infoevent.joparis.venue.repositories;


import com.infoevent.joparis.venue.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Location} entity.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
