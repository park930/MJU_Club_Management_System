package com.example.springsecurity.club.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.club.dto.ClubDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
