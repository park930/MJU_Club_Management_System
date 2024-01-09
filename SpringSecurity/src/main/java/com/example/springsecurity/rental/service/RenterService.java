package com.example.springsecurity.rental.service;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.rental.entity.RentalEntity;
import com.example.springsecurity.rental.entity.RenterEntity;
import com.example.springsecurity.rental.repository.RentalRepository;
import com.example.springsecurity.rental.repository.RenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RenterService {

    private final RenterRepository renterRepository;
    private final RentalRepository rentalRepository;

    public RentalDTO saveOffer(RenterDTO renterDTO, Long id) {
        RenterEntity renterEntity = RenterEntity.toRenterEntity(renterDTO);
        renterRepository.save(renterEntity);

        Optional<RentalEntity> optionalRentalEntity = rentalRepository.findById(id);
        if (optionalRentalEntity.isPresent()){
            RentalEntity rentalEntity = optionalRentalEntity.get();
            rentalEntity.setRemain(rentalEntity.getRemain()-1);
            rentalRepository.save(rentalEntity);
            return RentalDTO.toRentalDTO(rentalEntity);
        } else {
            return null;
        }

    }

}
