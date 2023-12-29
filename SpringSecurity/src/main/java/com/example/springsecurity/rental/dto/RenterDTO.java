package com.example.springsecurity.rental.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RenterDTO {

    private Long studentNumber;
    private String department;
    private String name;
    private String phone;
    private LocalDate duration;
    private LocalDate offerDate;
}
