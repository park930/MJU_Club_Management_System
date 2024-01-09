package com.example.springsecurity.rental.dto;

import com.example.springsecurity.rental.entity.RentalEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RentalRenterDTO {
    private Long id;
    private Long rentalId;
    private Long renterId;
}
