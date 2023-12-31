package com.example.springsecurity.rental.dto;

import com.example.springsecurity.rental.entity.RentalEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO {
    private Long id;
    private String type;
    private String name;
    private int totalCount;
    private String location;
    private int remain;

    public static RentalDTO toRentalDTO(RentalEntity rentalEntity) {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setId(rentalEntity.getId());
        rentalDTO.setType(rentalEntity.getType());
        rentalDTO.setName(rentalEntity.getName());
        rentalDTO.setTotalCount(rentalEntity.getTotalCount());
        rentalDTO.setLocation(rentalEntity.getLocation());
        rentalDTO.setRemain(rentalEntity.getRemain());
        return rentalDTO;
    }
}