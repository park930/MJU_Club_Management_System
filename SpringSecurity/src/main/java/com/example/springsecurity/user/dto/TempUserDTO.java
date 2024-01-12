package com.example.springsecurity.user.dto;

import com.example.springsecurity.user.entity.TempUserEntity;
import com.example.springsecurity.user.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TempUserDTO {

    private int id;
    private String username;
    private Long clubId;
    private String role;
    private String password;
    private String realName;
    private String phoneNumber;
    private String position;
    private String detailPosition;

    public static TempUserDTO toTempUserDTO(TempUserEntity tempUserEntity, Long clubId) {
        TempUserDTO tempUserDTO = new TempUserDTO();
        tempUserDTO.setId(tempUserEntity.getId());
        tempUserDTO.setUsername(tempUserEntity.getUsername());
        tempUserDTO.setClubId(clubId);
        tempUserDTO.setRole(tempUserEntity.getRole());
        tempUserDTO.setPassword(tempUserEntity.getPassword());
        tempUserDTO.setRealName(tempUserEntity.getRealName());
        tempUserDTO.setPhoneNumber(tempUserEntity.getPhoneNumber());
        tempUserDTO.setPosition(tempUserEntity.getPosition());
        tempUserDTO.setDetailPosition(tempUserEntity.getDetailPosition());
        return tempUserDTO;
    }
//
//    public static TempUserDTO toUserDTO(UserEntity userEntity) {
//        TempUserDTO userDTO = new TempUserDTO();
//        userDTO.setId(userEntity.getId());
//        userDTO.setClubId(userEntity.getClubEntity().getId());
//        userDTO.setPassword(userEntity.getPassword());
//        userDTO.setUsername(userEntity.getUsername());
//        userDTO.setRealName(userEntity.getRealName());
//        userDTO.setPosition(userEntity.getPosition());
//        userDTO.setPhoneNumber(userEntity.getPhoneNumber());
//        userDTO.setRole(userEntity.getRole());
//        return userDTO;
//    }
}
