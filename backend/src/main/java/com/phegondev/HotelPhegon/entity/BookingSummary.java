package com.phegondev.HotelPhegon.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.math.BigDecimal;
import java.time.LocalDate;


// VIEW
@Entity
@Data
@Subselect("SELECT * FROM v_booking_summary")
@Immutable
public class BookingSummary {

    @Id
    private Long bookingId; // A view PRECISA ter uma coluna que sirva como ID

    private String bookingConfirmationCode;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestName;
    private String roomType;
    private BigDecimal roomPrice;

    // Getters e Setters
}