package com.phegondev.HotelPhegon.controller;


import com.phegondev.HotelPhegon.dto.BookingDetailsDTO;
import com.phegondev.HotelPhegon.dto.Response;
import com.phegondev.HotelPhegon.entity.Booking;
import com.phegondev.HotelPhegon.service.interfac.IBookingService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @PostMapping("/book-room/{roomId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> saveBooking(
            @PathVariable Long roomId,
            @PathVariable Long userId,
            @RequestBody Booking bookingRequest) {
        Response response = bookingService.saveBooking(roomId, userId, bookingRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings() {
        Response response = bookingService.getAllBookings();
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<Response> getBookingsByConfirmationCode(@PathVariable String confirmationCode) {
        Response response = bookingService.findBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/cancel/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> cancelBooking(@PathVariable Long bookingId) {
        Response response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/details/all")
    @PreAuthorize("hasAuthority('ADMIN')") // Apenas administradores podem acessar
    public ResponseEntity<List<BookingDetailsDTO>> getAllBookingDetails() {
        List<BookingDetailsDTO> bookingDetails = bookingService.getAllBookingDetails();
        return ResponseEntity.ok(bookingDetails);
    }

    @GetMapping("/report/pdf")
    @PreAuthorize("hasAuthority('ADMIN')") // Protegendo o endpoint
    public void generatePdfReport(HttpServletResponse response) throws IOException {
        bookingService.generateBookingsReport(response);
    }

}
