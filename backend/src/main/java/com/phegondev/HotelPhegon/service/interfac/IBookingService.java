package com.phegondev.HotelPhegon.service.interfac;

import com.phegondev.HotelPhegon.dto.BookingDetailsDTO;
import com.phegondev.HotelPhegon.dto.Response;
import com.phegondev.HotelPhegon.entity.Booking;

import java.util.List;

public interface IBookingService {

    Response saveBooking(Long rooId, Long userId, Booking bookingRequest);
    Response findBookingByConfirmationCode(String confirmationCode);
    Response getAllBookings();
    Response cancelBooking(Long bookingId);
    List<BookingDetailsDTO> getAllBookingDetails();
}
