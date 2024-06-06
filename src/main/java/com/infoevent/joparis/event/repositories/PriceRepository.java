package com.infoevent.joparis.event.repositories;

import com.infoevent.joparis.event.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * JPA repository for {@link Price} entities.
 */
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

}
