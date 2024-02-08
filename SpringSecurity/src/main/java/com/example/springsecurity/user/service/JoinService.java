package com.example.springsecurity.user.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.user.dto.JoinDTO;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.entity.TempUserEntity;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.TempUserRepository;
import com.example.springsecurity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final TempUserRepository tempUserRepository;
    private final ClubRepository clubRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void joinAdminProcess(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())){
            return;
        }
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        System.out.println("userDTO = " + userDTO);

        UserEntity userEntity = UserEntity.toNewAdminEntity(userDTO);
        userEntity.setRole("ROLE_ADMIN");
        userRepository.save(userEntity);

    }


    public void joinProcess(UserDTO userDTO,String type){

        //DB에 이미 동일한 username이 있는 계정이 있는지
        if (userRepository.existsByUsername(userDTO.getUsername())){
            return;
        }

        Optional<ClubEntity> optionalClubEntity = clubRepository.findById(userDTO.getClubId());
        if (optionalClubEntity.isPresent()){
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

            if (type.equals("normal")) {
                TempUserEntity userEntity = TempUserEntity.toNewTempUserEntity(userDTO,optionalClubEntity.get());
                if (userDTO.getUsername().startsWith("admin")) {
                    userEntity.setRole("ROLE_ADMIN");     //강제로 회원가입자는 ADMIN으로 해놓는다.
                } else {
                    userEntity.setRole("ROLE_USER");     //강제로 회원가입자는 ADMIN으로 해놓는다.
                }
                tempUserRepository.save(userEntity);
            } else {
                UserEntity userEntity = UserEntity.toNewUserEntity(userDTO,optionalClubEntity.get());
                if (userDTO.getUsername().startsWith("admin")) {
                    userEntity.setRole("ROLE_ADMIN");     //강제로 회원가입자는 ADMIN으로 해놓는다.
                } else {
                    userEntity.setRole("ROLE_USER");     //강제로 회원가입자는 ADMIN으로 해놓는다.
                }
                userRepository.save(userEntity);
            }
        }

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


    public int checkId(String userName) {
        if (userRepository.existsByUsername(userName)){
            return 0;
        } else {
            return 1;
        }
    }
}
