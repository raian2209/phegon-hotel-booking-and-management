package com.phegondev.HotelPhegon.repo;


import com.phegondev.HotelPhegon.entity.BookingSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BookingSummaryRepository extends JpaRepository<BookingSummary, Long>{
}
