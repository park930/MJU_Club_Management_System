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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubFeeService {
    private final ClubFeeRepository clubFeeRepository;

    public List<ClubFeeDTO> findAllByClubDTO(ClubDTO clubDTO) {
        List<ClubFeeEntity> clubFeeEntityList = clubFeeRepository.findAllByClubEntityOrderByDateDesc(ClubEntity.toUpdateClub(clubDTO));
        List<ClubFeeDTO> clubFeeDTOList = new ArrayList<>();
        for(ClubFeeEntity clubFeeEntity : clubFeeEntityList){
            clubFeeDTOList.add(ClubFeeDTO.toClubFeeDTO(clubFeeEntity));
        }
        return clubFeeDTOList;
    }
}
