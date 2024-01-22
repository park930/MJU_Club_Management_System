package com.example.springsecurity.rental.dto;

import com.example.springsecurity.rental.entity.RenterEntity;
import com.example.springsecurity.user.dto.UserDTO;
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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String userName;
    private Long rentalId;

    //특정 메서드에만 적용됨
    private UserDTO userDTO;
    private String isRent;

    public static RenterDTO toRenterDTO(RenterEntity renterEntity) {
        RenterDTO renterDTO = new RenterDTO();
        renterDTO.setId(renterEntity.getId());
        renterDTO.setStartDate(renterEntity.getStartDate());
        renterDTO.setEndDate(renterEntity.getEndDate());
        renterDTO.setUserDTO(UserDTO.toUserDTO(renterEntity.getUserEntity()));
        return renterDTO;
    }
}
