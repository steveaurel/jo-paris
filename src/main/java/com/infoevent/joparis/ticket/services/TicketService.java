package com.infoevent.joparis.ticket.services;

import com.infoevent.joparis.event.entities.Event;
import com.infoevent.joparis.ticket.entities.Ticket;
import com.infoevent.joparis.user.entities.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface for ticket management service.
 * Defines the operations for managing tickets within the system.
 */

public interface TicketService {
    /**
     * Creates a new ticket.
     *
     * @param ticket The ticket to create.
     * @return The created ticket.
     */
    Ticket createTicket(Ticket ticket);

    /**
     * Finds a ticket by its ID.
     *
     * @param id The ID of the ticket.
     * @return An Optional containing the found ticket, if any.
     */
    Optional<Ticket> findTicketById(Long id);

    /**
     * Retrieves all tickets.
     *
     * @return A list of all tickets.
     */
    List<Ticket> findAllTickets();

    /**
     * Finds all tickets for a specific event.
     *
     * @param event The ID of the event.
     * @return A list of tickets associated with the event.
     */
    List<Ticket> findTicketsByEvent(Event event);

    /**
     * Finds all tickets for a specific user.
     *
     * @param user The ID of the user.
     * @return A list of tickets associated with the user.
     */
    List<Ticket> findTicketsByUser(User user);

    /**
     * Finds a ticket by its ID.
     *
     * @param qrCode The qrCode of the ticket.
     * @return An Optional containing the found ticket, if any.
     */
    Optional<Ticket> findByQrCode(byte[] qrCode);
}
