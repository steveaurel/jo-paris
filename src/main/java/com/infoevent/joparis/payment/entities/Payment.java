package com.infoevent.joparis.payment.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infoevent.joparis.event.entities.Event;
import com.infoevent.joparis.event.entities.OfferType;
import com.infoevent.joparis.user.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
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
    @JoinColumn(name = "venue_id", referencedColumnName = "id", nullable = false)
    private OfferType offerType;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDateTime;

    @Column(nullable = false)
    private BigDecimal amount;
}
