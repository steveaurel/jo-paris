package com.infoevent.joparis.ticket.entities;

import com.infoevent.joparis.event.entities.Event;
import com.infoevent.joparis.event.entities.OfferType;
import com.infoevent.joparis.payment.entities.Payment;
import com.infoevent.joparis.user.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id", nullable = false)
    private Payment payment;

    @OneToOne
    @JoinColumn(name = "offer_type_id", referencedColumnName = "id", nullable = false)
    private OfferType offerType;

    @Column(nullable = false)
    private BigDecimal priceAmount;

    @Column(nullable = false)
    @Lob
    private byte[] qrCode;

    @Column(nullable = false, unique = true)
    private String key;
}
