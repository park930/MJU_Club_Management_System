package com.example.springsecurity.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.dto.JoinDTO;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO){

        //DB에 이미 동일한 username이 있는 계정이 있는지
        if (userRepository.existsByUsername(joinDTO.getUsername())){
            return;
        }

        ClubEntity clubEntity = clubRepository.findByClubName(joinDTO.getClubName());

        UserEntity data = new UserEntity();
        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setClubEntity(clubEntity);
        if (joinDTO.getUsername().startsWith("admin")) {
            data.setRole("ROLE_ADMIN");     //강제로 회원가입자는 ADMIN으로 해놓는다.
        } else {
            data.setRole("ROLE_USER");     //강제로 회원가입자는 ADMIN으로 해놓는다.
        }
        userRepository.save(data);
    }

    public List<ClubDTO> filterClubList(List<ClubDTO> clubDTOList) {
        List<ClubEntity> allClubEntities = userRepository.findAllClubEntities();
        List<Long> allClubId = new ArrayList<>();
        for(ClubEntity clubEntity : allClubEntities){
            allClubId.add(clubEntity.getId());
        }

        List<ClubDTO> clubList = new ArrayList<>();
        for (ClubDTO clubDTO : clubDTOList){
            if (!allClubId.contains(clubDTO.getId())){
                clubList.add(clubDTO);
            }
        }
        return clubList;
    }
}
