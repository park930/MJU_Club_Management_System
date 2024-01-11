package com.example.springsecurity.rental.entity;

import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.rental.dto.RentalDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="rental_table")
@ToString
public class RentalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type;

    @Column
    private String name;

    @Column
    private int totalCount;

    @Column
    private String location;

    @Column
    private int remain;

    @OneToMany(mappedBy = "rentalEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RentalRenterEntity> rentalRenterEntityList = new ArrayList<>();


    public static RentalEntity toRentalEntity(RentalDTO rentalDTO) {
        RentalEntity rentalEntity = new RentalEntity();
        rentalEntity.setType(rentalDTO.getType());
        rentalEntity.setName(rentalDTO.getName());
        rentalEntity.setTotalCount(rentalDTO.getTotalCount());
        rentalEntity.setLocation(rentalDTO.getLocation());
        rentalEntity.setRemain(rentalDTO.getRemain());
        return rentalEntity;
    }

    public static RentalEntity toUpdateRentalEntity(RentalDTO rentalDTO) {
        RentalEntity rentalEntity = new RentalEntity();
        rentalEntity.setId(rentalDTO.getId());
        rentalEntity.setType(rentalDTO.getType());
        rentalEntity.setName(rentalDTO.getName());
        rentalEntity.setTotalCount(rentalDTO.getTotalCount());
        rentalEntity.setLocation(rentalDTO.getLocation());
        rentalEntity.setRemain(rentalDTO.getRemain());
        return rentalEntity;
    }
}
