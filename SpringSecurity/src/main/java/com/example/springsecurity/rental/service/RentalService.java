package com.example.springsecurity.rental.service;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.rental.entity.RentalEntity;
import com.example.springsecurity.rental.entity.RenterEntity;
import com.example.springsecurity.rental.repository.RentalRepository;
import com.example.springsecurity.rental.repository.RenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RenterRepository renterRepository;

    public List<RentalDTO> findAll() {
        List<RentalEntity> rentalEntityList = rentalRepository.findAll();
        List<RentalDTO> rentalDTOList = new ArrayList<>();

        for (RentalEntity rentalEntity : rentalEntityList){
            RentalDTO rentalDTO = RentalDTO.toRentalDTO(rentalEntity);
            rentalDTOList.add(rentalDTO);
        }

        return rentalDTOList;
    }

    public void save(RentalDTO rentalDTO) {
        rentalRepository.save(RentalEntity.toRentalEntity(rentalDTO));
    }

    public RentalDTO findById(Long id) {
        Optional<RentalEntity> optionalRentalEntity = rentalRepository.findById(id);
        if (optionalRentalEntity.isPresent()){
            return RentalDTO.toRentalDTO(optionalRentalEntity.get());
        } else {
            return null;
        }
    }

    public List<RentalDTO> findAllRenterCount() {
        List<RentalEntity> rentalEntityList = rentalRepository.findAll();
        List<RentalDTO> rentalDTOList = new ArrayList<>();

        if (!rentalEntityList.isEmpty()){
            for(RentalEntity rentalEntity : rentalEntityList){
                int size = renterRepository.findAllByRentalEntityAndCheckRent(rentalEntity, 1).size();
                RentalDTO rentalDTO = RentalDTO.toRentalDTO(rentalEntity);
                rentalDTO.setRenterCount(size);
                rentalDTOList.add(rentalDTO);
            }
            return rentalDTOList;
        } else {
            return null;
        }
    }

}
