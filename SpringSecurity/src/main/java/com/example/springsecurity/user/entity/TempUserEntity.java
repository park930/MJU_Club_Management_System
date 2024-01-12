package com.example.springsecurity.user.entity;

import com.example.springsecurity.board.entity.FavoriteBoardEntity;
import com.example.springsecurity.board.entity.HeartEntity;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "temp_user_table")
public class TempUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;

    @Column
    private String realName;

    @Column
    private String phoneNumber;

    @Column
    private String detailPosition;

    @Column
    private String position;

    @Column
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity clubEntity;

    @Column
    private String role;    //권한 종류

    public static TempUserEntity toNewTempUserEntity(UserDTO userDTO, ClubEntity clubEntity) {
        TempUserEntity tempUserEntity = new TempUserEntity();
        tempUserEntity.setUsername(userDTO.getUsername());
        tempUserEntity.setRealName(userDTO.getRealName());
        tempUserEntity.setPhoneNumber(userDTO.getPhoneNumber());
        tempUserEntity.setPosition(userDTO.getPosition());
        tempUserEntity.setDetailPosition(userDTO.getDetailPosition());
        tempUserEntity.setPassword(userDTO.getPassword());
        tempUserEntity.setClubEntity(clubEntity);
        return tempUserEntity;
    }
}
