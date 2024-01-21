package com.example.springsecurity.rental.service;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RentalRenterDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.rental.entity.RentalEntity;
import com.example.springsecurity.rental.entity.RentalRenterEntity;
import com.example.springsecurity.rental.entity.RenterEntity;
import com.example.springsecurity.rental.repository.RentalRenterRepository;
import com.example.springsecurity.rental.repository.RentalRepository;
import com.example.springsecurity.rental.repository.RenterRepository;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RenterService {

    private final RenterRepository renterRepository;
    private final RentalRenterRepository rentalRenterRepository;
    private final UserRepository userRepository;

    public RenterDTO saveOffer(RenterDTO renterDTO) {
        UserEntity userEntity = userRepository.findByUsername(renterDTO.getUserName());
        RenterEntity renterEntity = RenterEntity.toRenterEntity(renterDTO,userEntity);
        return RenterDTO.toRenterDTO(renterRepository.save(renterEntity));
    }

    public List<RentalDTO> findAllByUserName(String userName) {
        List<RentalDTO> rentalDTOList = new ArrayList<>();
        List<RenterEntity> renterEntityList = renterRepository.findAllByUserEntityOrderByCreatedTimeDesc(userRepository.findByUsername(userName));
        for(RenterEntity renterEntity : renterEntityList){
            RentalRenterEntity rentalRenterEntity = rentalRenterRepository.findByRenterEntity(renterEntity);
            RentalRenterDTO rentalRenterDTO = RentalRenterDTO.toRentalRenterDTO(rentalRenterEntity);
            if (rentalRenterDTO.getIsRent()==1){
                rentalDTOList.add(rentalRenterDTO.getRentalDTO());
            }
        }

        if (rentalDTOList.isEmpty()){
            return null;
        } else {
            return rentalDTOList;
        }

    }
}
