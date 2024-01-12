package com.example.springsecurity.rental.repository;

import com.example.springsecurity.rental.entity.RentalEntity;
import com.example.springsecurity.rental.entity.RentalRenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRenterRepository extends JpaRepository<RentalRenterEntity,Long> {
    List<RentalRenterEntity> findAllByRentalEntityAndIsRent(RentalEntity rentalEntity, int isRent);

    List<RentalRenterEntity> findAllByRentalEntity(RentalEntity rentalEntity);

}