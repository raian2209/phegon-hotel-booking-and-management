package com.phegondev.HotelPhegon.repo;

import com.phegondev.HotelPhegon.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.phegondev.HotelPhegon.dto.BookingDetailsDTO;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);

//    JOIN (Tabelas "Cruzadas")
//Em vez de retornar apenas o ID do usuário e do quarto, vamos criar um DTO (Data Transfer Object) que já traga as informações completas.
@Query("SELECT new com.phegondev.HotelPhegon.dto.BookingDetailsDTO(" +
        "b.bookingConfirmationCode, b.checkInDate, b.checkOutDate, " +
        "b.user.name, b.user.email, b.room.roomType, b.room.roomPrice) " +
        "FROM Booking b")
List<BookingDetailsDTO> findAllBookingDetails();

}
