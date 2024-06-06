package com.infoevent.joparis.notification.entities;

import com.infoevent.joparis.event.entities.Event;
import com.infoevent.joparis.event.entities.OfferType;
import com.infoevent.joparis.user.entities.User;
import com.infoevent.joparis.venue.entities.Venue;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {
    User user;
    OfferType offerType;
    Event event;
    String price;
    Venue venue;
    byte [] qrCode;
}
