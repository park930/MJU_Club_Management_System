package com.example.springsecurity.rental.dto;

import com.example.springsecurity.rental.entity.RentalEntity;
import com.example.springsecurity.rental.entity.RentalRenterEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RentalRenterDTO {
    private Long id;
    private RentalDTO rentalDTO;
    private RenterDTO renterDTO;
    private int isRent;

    public static RentalRenterDTO toRentalRenterDTO(RentalRenterEntity rentalRenterEntity) {
        RentalRenterDTO rentalRenterDTO = new RentalRenterDTO();
        rentalRenterDTO.setId(rentalRenterEntity.getId());
        rentalRenterDTO.setRentalDTO(RentalDTO.toRentalDTO(rentalRenterEntity.getRentalEntity()));
        rentalRenterDTO.setRenterDTO(RenterDTO.toRenterDTO(rentalRenterEntity.getRenterEntity()));
        rentalRenterDTO.setIsRent(rentalRenterEntity.getIsRent());
        return rentalRenterDTO;
    }
}
