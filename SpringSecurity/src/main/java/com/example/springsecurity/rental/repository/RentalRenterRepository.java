package com.example.springsecurity.rental.repository;

import com.example.springsecurity.rental.entity.RentalEntity;
import com.example.springsecurity.rental.entity.RentalRenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRenterRepository extends JpaRepository<RentalRenterEntity,Long> {

}
