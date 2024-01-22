package com.example.springsecurity.rental.service;

import com.example.springsecurity.rental.dto.RentalDTO;
import com.example.springsecurity.rental.dto.RenterDTO;
import com.example.springsecurity.rental.entity.RentalEntity;
import com.example.springsecurity.rental.entity.RenterEntity;
import com.example.springsecurity.rental.repository.RentalRepository;
import com.example.springsecurity.rental.repository.RenterRepository;
import com.example.springsecurity.user.dto.UserDTO;
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
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public void saveOffer(RenterDTO renterDTO) {
        UserEntity userEntity = userRepository.findByUsername(renterDTO.getUserName());
        Optional<RentalEntity> optionalRentalEntity = rentalRepository.findById(renterDTO.getRentalId());
        if (optionalRentalEntity.isPresent()){
            RenterEntity renterEntity = RenterEntity.toRenterEntity(renterDTO,optionalRentalEntity.get(),userEntity);
            renterEntity.setCheckRent(compareTime(renterDTO.getStartDate(),renterDTO.getEndDate()));
            renterRepository.save(renterEntity);
        }
    }

    public static int compareTime(LocalDateTime startDate, LocalDateTime endDate){
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(startDate) && now.isBefore(endDate)) {
            return 1;
        } else {
            return 0;
        }
    }


    public List<RentalDTO> findAllByUserName(String userName) {
        List<RenterEntity> renterEntityList = renterRepository.findAllByUserEntityAndCheckRentOrderByCreatedTimeDesc(userRepository.findByUsername(userName),1);

        if (renterEntityList.isEmpty()){
            return null;
        }

        List<RentalDTO> rentalDTOList = new ArrayList<>();

        for(RenterEntity renterEntity : renterEntityList){
            RenterDTO renterDTO = RenterDTO.toRenterDTO(renterEntity);
            RentalDTO rentalDTO = RentalDTO.toRentalDTO(renterEntity.getRentalEntity());
            rentalDTO.setEndDate(renterDTO.getEndDate());
            rentalDTOList.add(rentalDTO);
        }
        return rentalDTOList;

    }

    public RenterDTO findById(Long renterId) {
        Optional<RenterEntity> optionalRenterEntity = renterRepository.findById(renterId);
        if (optionalRenterEntity.isPresent()){
            return RenterDTO.toRenterDTO(optionalRenterEntity.get());
        } else {
            return null;
        }
    }

    public void delete(Long renterId) {
        renterRepository.deleteById(renterId);
    }

    public void update(RenterDTO renterDTO,Long rentalId) {
        UserEntity userEntity = userRepository.findByUsername(renterDTO.getUserName());
        Optional<RentalEntity> optionalRentalEntity = rentalRepository.findById(rentalId);
        if (optionalRentalEntity.isPresent()){
            RenterEntity renterEntity = RenterEntity.toUpdateRenterEntity(renterDTO, optionalRentalEntity.get(), userEntity);
            renterEntity.setCheckRent(compareTime(renterDTO.getStartDate(),renterDTO.getEndDate()));
            renterRepository.save(renterEntity);
        }
    }

    public List<RentalDTO> updateRemain(List<RentalDTO> rentalDTOList) {
        for(RentalDTO rentalDTO : rentalDTOList){
            int useSize = renterRepository.findAllByRentalEntityAndCheckRent(RentalEntity.toUpdateRentalEntity(rentalDTO), 1).size();
            int updateRemain = rentalDTO.getTotalCount()-useSize;
            if (updateRemain != rentalDTO.getRemain()){
                rentalDTO.setRemain(updateRemain);
                rentalRepository.save(RentalEntity.toUpdateRentalEntity(rentalDTO));
            }

        }
        return rentalDTOList;
    }

    public List<RenterDTO> findAllByRental(RentalDTO rentalDTO) {
        List<RenterDTO> renterDTOList = new ArrayList<>();
        List<RenterEntity> renterEntityList = renterRepository.findAllByRentalEntityOrderByCreatedTimeDesc(RentalEntity.toUpdateRentalEntity(rentalDTO));
        for(RenterEntity renterEntity : renterEntityList){
            RenterDTO renterDTO = RenterDTO.toRenterDTO(renterEntity);
            if (renterEntity.getCheckRent()==1){
                renterDTO.setIsRent("O");
            } else {
                renterDTO.setIsRent("X");
            }
            renterDTOList.add(renterDTO);
        }
        return renterDTOList;
    }
}
