package com.example.springsecurity.club.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.dto.ClubFeeDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.entity.ClubFeeEntity;
import com.example.springsecurity.club.repository.ClubFeeRepository;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubFeeService {
    private final ClubFeeRepository clubFeeRepository;
    private final ClubRepository clubRepository;

    public List<ClubFeeDTO> findAllByClubDTO(ClubDTO clubDTO) {
        List<ClubFeeEntity> clubFeeEntityList = clubFeeRepository.findAllByClubEntityOrderByDateDesc(ClubEntity.toUpdateClub(clubDTO));

        if (clubFeeEntityList==null){
            return null;
        }

        List<ClubFeeDTO> clubFeeDTOList = new ArrayList<>();
        for(ClubFeeEntity clubFeeEntity : clubFeeEntityList){
            clubFeeDTOList.add(ClubFeeDTO.toClubFeeDTO(clubFeeEntity,clubDTO.getId()));
        }

        int balance=0;
        int totalPlus =0;
        int totalMinus=0;
        for(int i=clubFeeDTOList.size()-1;i>=0;i--){
            ClubFeeDTO clubFeeDTO = clubFeeDTOList.get(i);
            int amount = clubFeeDTO.getAmount();
            if (clubFeeDTO.getType().equals("회비") || clubFeeDTO.getType().equals("입금")){
                balance += amount;
                totalPlus += amount;
            } else {
                balance -= clubFeeDTO.getAmount();
                totalMinus += amount;
            }
            clubFeeDTO.setBalance(balance);

            if (i==0){
                clubFeeDTO.setTotalPlusFee(totalPlus);
                clubFeeDTO.setTotalMinusFee(totalMinus);
            }

        }

        return clubFeeDTOList;
    }

    public void save(ClubFeeDTO clubFeeDTO,ClubDTO clubDTO) {
        clubFeeRepository.save(ClubFeeEntity.toNewClubFeeEntity(clubFeeDTO,clubDTO));
    }

    public List<ClubFeeDTO> findFeeUser(List<ClubFeeDTO> clubFeeDTOList) {
        List<ClubFeeDTO> feeList = new ArrayList<>();
        for(ClubFeeDTO clubFeeDTO : clubFeeDTOList){
            if (clubFeeDTO.getType().equals("회비")){
                feeList.add(clubFeeDTO);
            }
        }
        return feeList;
    }

    public void deleteFee(Long clubFeeId) {
        clubFeeRepository.deleteById(clubFeeId);
    }

    public ClubFeeDTO findById(Long clubFeeId) {
        Optional<ClubFeeEntity> optionalClubFeeEntity = clubFeeRepository.findById(clubFeeId);
        if (optionalClubFeeEntity.isPresent()){
            ClubFeeEntity clubFeeEntity = optionalClubFeeEntity.get();
            return ClubFeeDTO.toClubFeeDTO(clubFeeEntity,clubFeeEntity.getClubEntity().getId());
        } else {
            return null;
        }
    }

    public void updateClubFee(ClubFeeDTO clubFeeDTO) {
        Optional<ClubEntity> optionalClubEntity = clubRepository.findById(clubFeeDTO.getClubId());
        if (optionalClubEntity.isPresent()){
            ClubFeeEntity clubFeeEntity = ClubFeeEntity.toUpdateClubFeeEntity(clubFeeDTO,optionalClubEntity.get());
            clubFeeRepository.save(clubFeeEntity);
        }
    }
}
