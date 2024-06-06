package com.infoevent.joparis.ticket.repositories;

import com.infoevent.joparis.event.entities.Event;
import com.infoevent.joparis.ticket.entities.Ticket;
import com.infoevent.joparis.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


/**
 * JPA repository for {@link Ticket} entities.
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /**
     * Finds all tickets by event ID.
     *
     * @param event The ID of the event.
     * @return A list of tickets associated with the given event ID.
     */
    List<Ticket> findByEvent(Event event);

    /**
     * Finds all tickets by user ID.
     *
     * @param user The ID of the user.
     * @return A list of tickets associated with the given user ID.
     */
    List<Ticket> findByUser(User user);

    Optional<Ticket> findByQrCode(byte [] qrCode);
}
