package com.example.springsecurity.rental.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.rental.dto.RenterDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "renter_table")
@ToString
public class RenterEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long studentNumber;

    @Column
    private String department;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private LocalDate duration;

    public static RenterEntity toRenterEntity(RenterDTO renterDTO) {
        RenterEntity renterEntity = new RenterEntity();
        renterEntity.setStudentNumber(renterDTO.getStudentNumber());
        renterEntity.setDepartment(renterDTO.getDepartment());
        renterEntity.setName(renterDTO.getName());
        renterEntity.setPhone(renterDTO.getPhone());
        renterEntity.setDuration(renterDTO.getDuration());

        return renterEntity;
    }
}
