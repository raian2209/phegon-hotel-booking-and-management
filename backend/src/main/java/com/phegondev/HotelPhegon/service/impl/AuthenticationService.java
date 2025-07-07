package com.phegondev.HotelPhegon.service.impl;

import com.phegondev.HotelPhegon.entity.LoginLog;
import com.phegondev.HotelPhegon.entity.LoginStatus;
import com.phegondev.HotelPhegon.repo.LoginLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthenticationService {

    @Autowired
    private LoginLogRepository loginLogRepository;

    // Example method where you would handle a login attempt
    public void handleLoginAttempt(String username, String ipAddress, boolean isSuccessful) {

        // Use the builder to create a new log entry
        LoginLog logEntry = LoginLog.builder()
                .username(username)
                .status(isSuccessful ? LoginStatus.SUCCESS : LoginStatus.FAILURE)
                .timestamp(LocalDateTime.now())
                .ipAddress(ipAddress)
                .build();

        // Save the log entry to the database
        loginLogRepository.save(logEntry);

        if (!isSuccessful) {
            // Handle failed login logic...
        } else {
            // Handle successful login logic...
        }
    }
}
