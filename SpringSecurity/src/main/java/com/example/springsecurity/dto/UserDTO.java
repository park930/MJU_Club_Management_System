package com.example.springsecurity.dto;

import com.example.springsecurity.entity.UserEntity;
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

    public static UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setClubId(userEntity.getClubEntity().getId());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setRole(userEntity.getRole());
        return userDTO;
    }
}
