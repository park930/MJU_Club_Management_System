package com.example.springsecurity.rental.service;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RentalRenterDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.rental.entity.RentalEntity;
import com.example.springsecurity.rental.entity.RentalRenterEntity;
import com.example.springsecurity.rental.repository.RentalRenterRepository;
import com.example.springsecurity.rental.repository.RentalRepository;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    public void save(RentalDTO rentalDTO, RenterDTO renterDTO) {
        UserEntity userEntity = userRepository.findByUsername(renterDTO.getUserName());
        RentalRenterEntity rentalRenterEntity = RentalRenterEntity.toNewRentalRenterEntity(rentalDTO,renterDTO,userEntity);

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(renterDTO.getEndDate())){
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
            RenterDTO renterDTO = rentalRenterDTO.getRenterDTO();

            renterDTO.setIsRent( rentalRenterDTO.getIsRent()==1? "O":"X");
            renterDTO.setUserDTO(UserDTO.toUserDTO(userRepository.findByUsername(renterDTO.getUserName())));
            filterRenterList.add(renterDTO);
        }
        return filterRenterList;
    }
}
