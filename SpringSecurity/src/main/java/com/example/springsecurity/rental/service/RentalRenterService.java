package com.example.springsecurity.rental.service;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.rental.entity.RentalEntity;
import com.example.springsecurity.rental.entity.RentalRenterEntity;
import com.example.springsecurity.rental.repository.RentalRenterRepository;
import com.example.springsecurity.rental.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalRenterService {

    private final RentalRenterRepository rentalRenterRepository;


    public void save(RentalDTO rentalDTO, RenterDTO renterDTO) {
        RentalRenterEntity rentalRenterEntity = RentalRenterEntity.toNewRentalRenterEntity(rentalDTO,renterDTO);
        rentalRenterRepository.save(rentalRenterEntity);
    }
}
