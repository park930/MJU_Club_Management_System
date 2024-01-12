package com.example.springsecurity.user.dto;

import com.example.springsecurity.user.entity.UserEntity;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int id;
    private String username;
    private Long clubId;
    private String role;
    private String password;
    private String realName;
    private String phoneNumber;
    private String position;
    private String detailPosition;

    public static UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setClubId(userEntity.getClubEntity().getId());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setRealName(userEntity.getRealName());
        userDTO.setPosition(userEntity.getPosition());
        userDTO.setPhoneNumber(userEntity.getPhoneNumber());
        userDTO.setRole(userEntity.getRole());
        userDTO.setDetailPosition(userEntity.getDetailPosition());
        return userDTO;
    }

    public static UserDTO toUserDTO(TempUserDTO tempUserDTO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setClubId(tempUserDTO.getClubId());
        userDTO.setPassword(tempUserDTO.getPassword());
        userDTO.setUsername(tempUserDTO.getUsername());
        userDTO.setRealName(tempUserDTO.getRealName());
        userDTO.setPosition(tempUserDTO.getPosition());
        userDTO.setPhoneNumber(tempUserDTO.getPhoneNumber());
        userDTO.setRole(tempUserDTO.getRole());
        userDTO.setDetailPosition(tempUserDTO.getDetailPosition());
        return userDTO;
    }
}
