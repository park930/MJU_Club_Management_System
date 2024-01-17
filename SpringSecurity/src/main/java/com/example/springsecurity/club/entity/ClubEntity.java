package com.example.springsecurity.club.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.score.entity.ScoreClubEntity;
import com.example.springsecurity.todo.entity.TodoClubEntity;
import com.example.springsecurity.todo.entity.TodoCommentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "club_table")
@ToString
public class ClubEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String category;

    @Column
    private String clubName;

    @Column
    private String clubRoom;

    @Column
    private Long roomPassword;

    @OneToMany(mappedBy = "clubEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserEntity> userEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "clubEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ClubFeeEntity> clubFeeEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "clubEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ScoreClubEntity> scoreClubEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "clubEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TodoCommentEntity> todoCommentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "clubEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TodoClubEntity> todoClubEntityList = new ArrayList<>();



    public static ClubEntity toClubEntity(ClubDTO clubDTO) {
        ClubEntity clubEntity = new ClubEntity();
        clubEntity.setCategory(clubDTO.getCategory());
        clubEntity.setClubName(clubDTO.getClubName());
        clubEntity.setClubRoom(clubDTO.getClubRoom());
        clubEntity.setRoomPassword(clubDTO.getRoomPassword());
        return clubEntity;
    }

    public static ClubEntity toUpdateClub(ClubDTO clubDTO){
        ClubEntity clubEntity = new ClubEntity();
        clubEntity.setId(clubDTO.getId());
        clubEntity.setCategory(clubDTO.getCategory());
        clubEntity.setClubName(clubDTO.getClubName());
        clubEntity.setClubRoom(clubDTO.getClubRoom());
        clubEntity.setRoomPassword(clubDTO.getRoomPassword());
        return clubEntity;
    }
}
