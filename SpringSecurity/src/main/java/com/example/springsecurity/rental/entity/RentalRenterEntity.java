package com.example.springsecurity.rental.entity;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "rental_renter_table")
@ToString
public class RentalRenterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    private RentalEntity rentalEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id")
    private RenterEntity renterEntity;

    @Column
    private int isRent;


    public static RentalRenterEntity toNewRentalRenterEntity(RentalDTO rentalDTO, RenterDTO renterDTO, UserEntity userEntity) {
        RentalRenterEntity rentalRenterEntity = new RentalRenterEntity();
        rentalRenterEntity.setRenterEntity(RenterEntity.toUpdateRenterEntity(renterDTO,userEntity));
        rentalRenterEntity.setRentalEntity(RentalEntity.toUpdateRentalEntity(rentalDTO));
        return  rentalRenterEntity;
    }
}
