package com.lfae.api.models.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant creationDate;

    /**
     * UTC start timestamp for global time alignment
     */
    @Column(nullable = false)
    private Instant startDate;
    /**
     * UTC end timestamp
     */
    @Column
    private Instant endDate;
    /**
     * IANA timezone identifier
     */
    @Column(nullable = false)
    private String timeZone;

}