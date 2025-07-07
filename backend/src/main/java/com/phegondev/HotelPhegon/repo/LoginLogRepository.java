package com.phegondev.HotelPhegon.repo;

import com.phegondev.HotelPhegon.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
    // You can add custom query methods here if needed in the future
    // For example: List<LoginLog> findByUsername(String username);
    List<LoginLog> findByUsernameOrderByTimestampDesc(String username);
}