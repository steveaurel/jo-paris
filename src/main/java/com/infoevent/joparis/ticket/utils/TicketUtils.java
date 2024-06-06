package com.infoevent.joparis.ticket.utils;

import com.infoevent.joparis.notification.entities.NotificationRequest;
import com.infoevent.joparis.ticket.entities.Ticket;

public class TicketUtils {
    public static NotificationRequest buildNotificationRequest(Ticket ticket) {
        return NotificationRequest.builder()
                .user(ticket.getUser())
                .offerType(ticket.getOfferType())
                .price(ticket.getPriceAmount() + " €")
                .qrCode(ticket.getQrCode())
                .event(ticket.getEvent())
                .venue(ticket.getEvent().getVenue())
                .build();
    }
}
