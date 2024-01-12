package com.example.springsecurity.user.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.user.dto.CustomUserDetails;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ClubRepository clubRepository;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        UserDTO userDTO = UserDTO.toUserDTO(userEntity);
        if (userDTO != null){
            return new CustomUserDetails(userDTO);
        }

        return null;
    }

    public UserDTO findByUserName(String writer) {
        UserEntity userEntity = userRepository.findByUsername(writer);
        return UserDTO.toUserDTO(userEntity);
    }

    public void save(UserDTO userDTO) {
        Optional<ClubEntity> optionalClubEntity = clubRepository.findById(userDTO.getClubId());
        if (optionalClubEntity.isPresent()){
            ClubEntity clubEntity = optionalClubEntity.get();
            UserEntity newUserEntity = UserEntity.toNewUserEntity(userDTO,clubEntity);
            userRepository.save(newUserEntity);
        }

    }

    public List<UserDTO> findAllByClubDTO(ClubDTO clubDTO) {
        List<UserEntity> userEntityList = userRepository.findAllByClubEntityOrderByUsernameAsc(ClubEntity.toUpdateClub(clubDTO));
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity userEntity : userEntityList){
            userDTOList.add(UserDTO.toUserDTO(userEntity));
        }
        return userDTOList;
    }
}
