package com.example.springsecurity.rental.dto;

import com.example.springsecurity.rental.entity.RenterEntity;
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

    public static RenterDTO toRenterDTO(RenterEntity renterEntity) {
        RenterDTO renterDTO = new RenterDTO();
        renterDTO.setId(renterEntity.getId());
        renterDTO.setStudentNumber(renterEntity.getStudentNumber());
        renterDTO.setDepartment(renterEntity.getDepartment());
        renterDTO.setName(renterEntity.getName());
        renterDTO.setPhone(renterEntity.getPhone());
        renterDTO.setDuration(renterEntity.getDuration());
        renterDTO.setOfferDate(renterEntity.getCreatedTime());
        return renterDTO;
    }
}
