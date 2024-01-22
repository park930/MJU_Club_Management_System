package com.example.springsecurity.rental.repository;

import com.example.springsecurity.rental.entity.RentalEntity;
import com.example.springsecurity.rental.entity.RenterEntity;
import com.example.springsecurity.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RenterRepository extends JpaRepository<RenterEntity, Long> {
    List<RenterEntity> findAllByUserEntityOrderByCreatedTimeDesc(UserEntity userEntity);
    List<RenterEntity> findAllByUserEntityAndCheckRentOrderByCreatedTimeDesc(UserEntity userEntity, int checkRent);

    List<RenterEntity> findAllByRentalEntityAndCheckRent(RentalEntity rentalEntity, int checkRent);

    List<RenterEntity> findAllByRentalEntityOrderByCreatedTimeDesc(RentalEntity updateRentalEntity);
}
