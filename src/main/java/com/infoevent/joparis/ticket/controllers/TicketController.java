package com.infoevent.joparis.ticket.controllers;

import com.infoevent.joparis.event.entities.Event;
import com.infoevent.joparis.event.services.EventService;
import com.infoevent.joparis.key.generator.services.KeyGeneratorService;
import com.infoevent.joparis.notification.entities.NotificationRequest;
import com.infoevent.joparis.notification.services.NotificationService;
import com.infoevent.joparis.notification.utils.NotificationUtils;
import com.infoevent.joparis.ticket.entities.Ticket;
import com.infoevent.joparis.ticket.services.TicketService;
import com.infoevent.joparis.ticket.utils.TicketUtils;
import com.infoevent.joparis.ticket.utils.QRCodeUtils;
import com.infoevent.joparis.user.entities.User;
import com.infoevent.joparis.user.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing tickets.
 * Provides endpoints for creating and retrieving tickets,
 * as well as fetching tickets for a specific event and user.
 */
@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController {

    private final TicketService ticketService;
    private final KeyGeneratorService keyGeneratorService;
    private final EventService eventService;
    private final NotificationService notificationService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody Ticket ticket) {
        try {
            log.info("API call to create new ticket for user ID: {} and event ID: {}", ticket.getUser().getId(), ticket.getEvent().getId());

            String ticketKey = keyGeneratorService.generateSecureRandomKey();
            ticket.setKey(ticketKey);

            String qrCodeData = ticket.getUser().getKey().concat(ticket.getKey());
            byte[] qrCodeImage = QRCodeUtils.generateQRCodeImage(qrCodeData, 300, 300);
            ticket.setQrCode(qrCodeImage);

            Ticket createdTicket = ticketService.createTicket(ticket);

            sendTicketNotification(ticket);

            ticket.getEvent().setSeatAvailable(ticket.getEvent().getSeatAvailable() - ticket.getOfferType().getSeatQuantity());

            eventService.updateEvent(ticket.getEvent().getId(), ticket.getEvent());

            return ResponseEntity.ok(createdTicket);
        } catch (Exception e) {
            log.error("Error creating ticket: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Retrieves a ticket by its ID.
     *
     * @param id The ID of the ticket to retrieve.
     * @return ResponseEntity containing the requested ticket, if found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> findTicketById(@PathVariable Long id) {
        log.info("API call to find ticket by ID: {}", id);
        return ticketService.findTicketById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all tickets.
     *
     * @return ResponseEntity containing a list of all tickets.
     */
    @GetMapping
    public ResponseEntity<List<Ticket>> findAllTickets() {
        log.info("API call to list all tickets");
        List<Ticket> tickets = ticketService.findAllTickets();
        return ResponseEntity.ok(tickets);
    }

    /**
     * Retrieves tickets by a specific event ID.
     *
     * @param eventID The ID of the event to find tickets for.
     * @return ResponseEntity containing a list of tickets held at the specified event.
     */
    @GetMapping("/by-event/{eventID}")
    public ResponseEntity<List<Ticket>> findTicketsByEventID(@PathVariable Long eventID) {
        log.info("API call to fetch tickets for event ID: {}", eventID);
        Optional<Event> event = eventService.findEventById(eventID);
        List<Ticket> tickets = ticketService.findTicketsByEvent(event.orElse(null));
        return ResponseEntity.ok(tickets);
    }

    /**
     * Retrieves tickets by a specific user ID.
     *
     * @param userID The ID of the user to find tickets for.
     * @return ResponseEntity containing a list of tickets held at the specified user.
     */
    @GetMapping("/by-user/{userID}")
    public ResponseEntity<List<Ticket>> findTicketsByUserID(@PathVariable Long userID) {
        log.info("API call to fetch tickets for user ID: {}", userID);
        Optional<User> user = userService.getUserById(userID);
        List<Ticket> tickets = ticketService.findTicketsByUser(user.orElse(null));
        return ResponseEntity.ok(tickets);
    }

    /**
     * Decodes a QR code image and verifies if it matches a stored ticket.
     * The method decodes the provided QR code image to a string and
     * attempts to match it with a combination of the user key and ticket key stored in the database.
     * It verifies the integrity and authenticity of the ticket QR code.
     *
     * @param qrCodeImage The QR code image as a byte array.
     * @return ResponseEntity with a Boolean indicating the success of the operation.
     */
    @PostMapping("/decode-qr")
    public ResponseEntity<Boolean> decodeQRCode(@RequestBody byte[] qrCodeImage) {
        log.info("API call to decode QR code and verify ticket authenticity");
        try {
            String decodedData = QRCodeUtils.decodeQRCode(qrCodeImage);
            Ticket ticket = ticketService.findByQrCode(qrCodeImage).orElse(null);

            if (ticket == null) {
                log.info("No ticket found matching the provided QR code");
                return ResponseEntity.notFound().build();
            }
            boolean success = decodedData.equals(ticket.getUser().getKey().concat(ticket.getKey()));

            log.info("QR code decoded successfully for ticket ID: {}, Verification status: {}", ticket.getId(), success);
            return ResponseEntity.ok(success);
        } catch (Exception e) {
            log.error("Error decoding QR code: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }


    private void sendTicketNotification(Ticket ticket) throws MessagingException {
        NotificationRequest notificationRequest = TicketUtils.buildNotificationRequest(ticket);
        String subject = "Confirmation de votre achat de billet";
        String body = NotificationUtils.constructConfirmationEmailBody(notificationRequest);
        byte[] pdfBytes = NotificationUtils.createTicketPdf(notificationRequest);
        notificationService.sendEmailWithAttachment(notificationRequest.getUser().getEmail(), subject, body, pdfBytes);
    }
}
