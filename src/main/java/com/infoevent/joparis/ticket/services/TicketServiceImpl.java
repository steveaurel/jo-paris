package com.infoevent.joparis.ticket.services;

import com.infoevent.joparis.event.entities.Event;
import com.infoevent.joparis.ticket.entities.Ticket;
import com.infoevent.joparis.ticket.repositories.TicketRepository;
import com.infoevent.joparis.user.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    @Override
    @Transactional()
    public Ticket createTicket(Ticket ticket) {
        log.info("Creating new ticket for user ID: {} and event ID: {}", ticket.getUser().getId(), ticket.getEvent().getId());
        return ticketRepository.save(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ticket> findTicketById(Long id) {
        log.info("Finding ticket by ID: {}", id);
        return ticketRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findAllTickets() {
        log.info("Retrieving all tickets");
        return ticketRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findTicketsByEvent(Event event) {
        log.info("Fetching tickets for event ID: {}", event.getId());
        return ticketRepository.findByEvent(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findTicketsByUser(User user) {
        log.info("Fetching tickets for user ID: {}", user.getId());
        return ticketRepository.findByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ticket> findByQrCode(byte[] qrCode) {
        log.info("Finding ticket by qrCode: {}", qrCode);
        return ticketRepository.findByQrCode(qrCode);
    }
}
