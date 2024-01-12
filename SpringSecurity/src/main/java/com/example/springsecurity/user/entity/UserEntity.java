package com.example.springsecurity.user.entity;

import com.example.springsecurity.board.entity.CommentEntity;
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
@Table(name = "user_table")
public class UserEntity {
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
    private String position;

    @Column
    private String detailPosition;

    @Column
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity clubEntity;

    @Column
    private String role;    //권한 종류

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FavoriteBoardEntity> favoriteBoardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HeartEntity> heartEntityList = new ArrayList<>();

    public static UserEntity toNewUserEntity(UserDTO userDTO, ClubEntity clubEntity) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setRealName(userDTO.getRealName());
        userEntity.setPhoneNumber(userDTO.getPhoneNumber());
        userEntity.setPosition(userDTO.getPosition());
        userEntity.setDetailPosition(userDTO.getDetailPosition());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setClubEntity(clubEntity);
        return userEntity;
    }
}
