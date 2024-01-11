package com.example.springsecurity.rental.service;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RentalRenterDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.rental.entity.RentalEntity;
import com.example.springsecurity.rental.entity.RentalRenterEntity;
import com.example.springsecurity.rental.repository.RentalRenterRepository;
import com.example.springsecurity.rental.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalRenterService {

    private final RentalRenterRepository rentalRenterRepository;
    private final RentalRepository rentalRepository;


    public void save(RentalDTO rentalDTO, RenterDTO renterDTO) {
        RentalRenterEntity rentalRenterEntity = RentalRenterEntity.toNewRentalRenterEntity(rentalDTO,renterDTO);

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(renterDTO.getDuration())){
            rentalRenterEntity.setIsRent(1);
        } else {
            rentalRenterEntity.setIsRent(0);
        }
        rentalRenterRepository.save(rentalRenterEntity);

    }

    public List<RentalDTO> updateRemain(List<RentalDTO> rentalDTOList) {
        for(RentalDTO rentalDTO : rentalDTOList){
            List<RentalRenterEntity> rentalRenterEntityList = rentalRenterRepository.findAllByRentalEntityAndIsRent(RentalEntity.toUpdateRentalEntity(rentalDTO), 1);
            rentalDTO.setRemain(rentalDTO.getTotalCount()-rentalRenterEntityList.size());
            rentalRepository.save(RentalEntity.toUpdateRentalEntity(rentalDTO));
        }
        return rentalDTOList;
    }

    public List<RenterDTO> findAllByRenterList(RentalDTO rentalDTO) {
        List<RentalRenterEntity> rentalRenterEntityList = rentalRenterRepository.findAllByRentalEntity(RentalEntity.toUpdateRentalEntity(rentalDTO));
        List<RenterDTO> filterRenterList = new ArrayList<>();
        for(RentalRenterEntity rentalRenterEntity : rentalRenterEntityList){
            RentalRenterDTO rentalRenterDTO = RentalRenterDTO.toRentalRenterDTO(rentalRenterEntity);
            filterRenterList.add(rentalRenterDTO.getRenterDTO());
        }
        return filterRenterList;
    }
}
