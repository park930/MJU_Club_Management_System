package com.example.springsecurity.rental.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RenterDTO {

    private Long id;
    private Long studentNumber;
    private String department;
    private String name;
    private String phone;
    private LocalDateTime duration;
    private LocalDateTime offerDate;
}
