package com.example.springsecurity.user.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.user.dto.CustomUserDetails;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
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
        UserDTO userDTO = null;

        if (userEntity.getRole().equals("ROLE_ADMIN")){
            userDTO = UserDTO.toAdminUserDTO(userEntity);
        } else {
            userDTO = UserDTO.toUserDTO(userEntity);
        }

        return new CustomUserDetails(userDTO);
    }

    public UserDTO findByUserName(String writer) {
        UserEntity userEntity = userRepository.findByUsername(writer);
        if (writer.startsWith("admin")){
            return UserDTO.toAdminUserDTO(userEntity);
        } else {
            return UserDTO.toUserDTO(userEntity);
        }
    }

    public void save(UserDTO userDTO) {
        Optional<ClubEntity> optionalClubEntity = clubRepository.findById(userDTO.getClubId());
        if (optionalClubEntity.isPresent()){
            ClubEntity clubEntity = optionalClubEntity.get();
            UserEntity newUserEntity = UserEntity.toNewUserEntity(userDTO,clubEntity);
            newUserEntity.setRole("ROLE_USER");
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

    public UserDTO findChairMan(List<UserDTO> userDTOList) {
        for(UserDTO userDTO : userDTOList){
            String position = userDTO.getDetailPosition();
            if (position == null){
                continue;
            } else {
                if (position.equals("회장")) {
                    return userDTO;
                }
            }
        }
        return null;
    }

    public UserDTO findByUserId(int userId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if (optionalUserEntity.isPresent()){
            UserEntity userEntity = optionalUserEntity.get();
            if (userEntity.getUsername().startsWith("admin")) {
                return UserDTO.toAdminUserDTO(userEntity);
            } else {
                return UserDTO.toUserDTO(userEntity);
            }
        } else {
            return null;
        }
    }

    public List<UserDTO> findAllAdminChairMan() {
        List<UserEntity> userEntityList = userRepository.findAllByUsernameContains("admin");
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity userEntity : userEntityList){
            UserDTO userDTO = UserDTO.toAdminUserDTO(userEntity);
            if (userDTO.getDetailPosition().equals("회장") || userDTO.getDetailPosition().equals("부회장")) {
                userDTOList.add(userDTO);
            }
        }

        System.out.println("userDTOList = " + userDTOList);

        if (userDTOList.isEmpty()){
            return null;
        } else {
            return userDTOList;
        }
    }

    public UserDTO findClubChairMan(Long clubId) {
        Optional<ClubEntity> optionalClubEntity = clubRepository.findById(clubId);
        if (optionalClubEntity.isPresent()){
            UserEntity userEntity = userRepository.findByClubEntityAndDetailPosition(optionalClubEntity.get(), "회장");
            if (userEntity==null){
                return null;
            } else {
                return UserDTO.toUserDTO(userEntity);
            }
        } else {
            return null;
        }
    }

    public void updatePosition(int userId, String detailPosition,String position) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if (optionalUserEntity.isPresent()){
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setDetailPosition(detailPosition);
            userEntity.setPosition(position);
            userRepository.save(userEntity);
        }
    }

    public Long deleteMember(int userId) {
        Long clubId = findByUserId(userId).getClubId();
        userRepository.deleteById(userId);
        return clubId;
    }



    public int getClubRight(List<UserDTO> userDTOList, int userId) {
        for(UserDTO userDTO : userDTOList){
            if (userDTO.getPosition().equals("임원")) {
                if (userDTO.getId() == userId){
                    return 1;
                }
            }
        }
        return 0;
    }

    public List<UserDTO> findAllAdmin() {
        List<UserEntity> userEntityList = userRepository.findAllByUsernameContains("admin");
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity userEntity : userEntityList){
            UserDTO userDTO = UserDTO.toAdminUserDTO(userEntity);
            userDTOList.add(userDTO);
        }

        if (userDTOList.isEmpty()) {
            return null;
        } else {
            return userDTOList;
        }
    }

    public List<ClubDTO> setClubMemberList(List<ClubDTO> clubDTOList) {
        for(ClubDTO clubDTO : clubDTOList){
            List<UserDTO> memberList = findAllByClubDTO(clubDTO);
            clubDTO.setMemberList(memberList);
            clubDTO.setMemberCount(memberList.size());
        }
        return clubDTOList;
    }

    public ClubDTO setMemberList(ClubDTO clubDTO) {
        List<UserDTO> userDTOList = findAllByClubDTO(clubDTO);
        clubDTO.setMemberList(userDTOList);
        clubDTO.setMemberCount(userDTOList.size());
        return clubDTO;
    }
}
