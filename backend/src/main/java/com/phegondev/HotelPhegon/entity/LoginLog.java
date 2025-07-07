package com.phegondev.HotelPhegon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data // Generates getters, setters, toString(), equals(), and hashCode()
@Builder // Implements the builder pattern for easy object creation
@NoArgsConstructor // Generates a no-argument constructor (required by JPA)
@AllArgsConstructor // Generates a constructor with all arguments
@Entity // Marks this class as a JPA entity
@Table(name = "login_logs") // Specifies the table name in the database
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING) // Stores the enum as a string ("SUCCESS", "FAILURE")
    @Column(nullable = false)
    private LoginStatus status;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private String ipAddress;
}
