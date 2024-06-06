package com.infoevent.joparis.event.repositories;


import com.infoevent.joparis.event.entities.Event;
import com.infoevent.joparis.event.entities.EventStatus;
import com.infoevent.joparis.venue.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * JPA repository for {@link Event} entities.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    /**
     * Finds all events by venue ID.
     *
     * @param venue The ID of the venue.
     * @return A list of events associated with the given venue ID.
     */
    List<Event> findByVenue(Venue venue);

    List<Event> findAllByOrderByDateAscStartTimeAsc();

    List<Event> findAllByEventStatusNotIn(List<EventStatus> suspended);

    List<Event> findAllByEventStatusOrderByDateAscStartTimeAsc(EventStatus status);

    List<Event> findEventsByVenueAndEndTimeGreaterThanAndEndTimeLessThanAndDate(Venue venue, LocalTime start, LocalTime end, LocalDate date);

    List<Event> findEventsByVenueAndStartTimeBetweenAndDate(Venue venue, LocalTime start, LocalTime end, LocalDate date);

}
