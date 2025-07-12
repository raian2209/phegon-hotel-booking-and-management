package com.phegondev.HotelPhegon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookingDetailsDTO {

    private String confirmationCode;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private String roomType;
    private java.math.BigDecimal roomPrice;

}
