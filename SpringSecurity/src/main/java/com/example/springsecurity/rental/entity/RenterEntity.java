package com.example.springsecurity.rental.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.rental.dto.RenterDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime duration;

    @OneToMany(mappedBy = "renterEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RentalRenterEntity> rentalRenterEntityList = new ArrayList<>();


    public static RenterEntity toRenterEntity(RenterDTO renterDTO) {
        RenterEntity renterEntity = new RenterEntity();
        renterEntity.setStudentNumber(renterDTO.getStudentNumber());
        renterEntity.setDepartment(renterDTO.getDepartment());
        renterEntity.setName(renterDTO.getName());
        renterEntity.setPhone(renterDTO.getPhone());
        renterEntity.setDuration(renterDTO.getDuration());

        return renterEntity;
    }

    public static RenterEntity toUpdateRenterEntity(RenterDTO renterDTO) {
        RenterEntity renterEntity = new RenterEntity();
        renterEntity.setId(renterDTO.getId());
        renterEntity.setStudentNumber(renterDTO.getStudentNumber());
        renterEntity.setDepartment(renterDTO.getDepartment());
        renterEntity.setName(renterDTO.getName());
        renterEntity.setPhone(renterDTO.getPhone());
        renterEntity.setDuration(renterDTO.getDuration());

        return renterEntity;
    }
}
