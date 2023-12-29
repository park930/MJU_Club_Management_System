package com.example.springsecurity.rental.repository;

import com.example.springsecurity.rental.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<RentalEntity,Long> {

}
