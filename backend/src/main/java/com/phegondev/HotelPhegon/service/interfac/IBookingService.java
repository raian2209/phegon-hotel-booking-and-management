package com.phegondev.HotelPhegon.service.interfac;

import com.phegondev.HotelPhegon.dto.BookingDetailsDTO;
import com.phegondev.HotelPhegon.dto.Response;
import com.phegondev.HotelPhegon.entity.Booking;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface IBookingService {

    Response saveBooking(Long rooId, Long userId, Booking bookingRequest);
    Response findBookingByConfirmationCode(String confirmationCode);
    Response getAllBookings();
    Response cancelBooking(Long bookingId);
    List<BookingDetailsDTO> getAllBookingDetails();
    void generateBookingsReport(HttpServletResponse response) throws IOException;
}
