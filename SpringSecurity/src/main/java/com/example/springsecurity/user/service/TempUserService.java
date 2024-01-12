package com.example.springsecurity.user.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.user.dto.TempUserDTO;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.entity.TempUserEntity;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.TempUserRepository;
import com.example.springsecurity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TempUserService {

    private final UserRepository userRepository;
    private final TempUserRepository tempUserRepository;
    private final ClubRepository clubRepository;


    public List<TempUserDTO> findAllByClubId(Long clubId) {
        Optional<ClubEntity> optionalClubEntity = clubRepository.findById(clubId);
        List<TempUserDTO> tempUserDTOList = new ArrayList<>();
        if (optionalClubEntity.isPresent()){
            List<TempUserEntity> tempUserEntityList = tempUserRepository.findAllByClubEntity(optionalClubEntity.get());
            for(TempUserEntity tempUserEntity : tempUserEntityList){
                tempUserDTOList.add(TempUserDTO.toTempUserDTO(tempUserEntity,clubId));
            }
        }
        return tempUserDTOList;
    }
}
