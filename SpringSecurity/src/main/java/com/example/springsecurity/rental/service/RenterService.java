package com.example.springsecurity.rental.service;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.rental.entity.RentalEntity;
import com.example.springsecurity.rental.entity.RenterEntity;
import com.example.springsecurity.rental.repository.RentalRepository;
import com.example.springsecurity.rental.repository.RenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RenterService {

    private final RenterRepository renterRepository;
    private final RentalRepository rentalRepository;

    public RenterDTO saveOffer(RenterDTO renterDTO, Long id) {
        RenterEntity renterEntity = RenterEntity.toRenterEntity(renterDTO);
        return RenterDTO.toRenterDTO(renterRepository.save(renterEntity));
    }

}
