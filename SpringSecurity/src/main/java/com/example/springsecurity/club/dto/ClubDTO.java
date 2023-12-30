package com.example.springsecurity.club.dto;

import com.example.springsecurity.club.entity.ClubEntity;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClubDTO {

    private Long id;
    private String category;
    private String clubName;
    private String clubRoom;
    private Long roomPassword;

    public static ClubDTO toClubDTO(ClubEntity clubEntity) {
        ClubDTO clubDTO = new ClubDTO();
        clubDTO.setId(clubEntity.getId());
        clubDTO.setCategory(clubEntity.getCategory());
        clubDTO.setClubName(clubEntity.getClubName());
        clubDTO.setClubRoom(clubEntity.getClubRoom());
        clubDTO.setRoomPassword(clubEntity.getRoomPassword());
        return clubDTO;
    }
}
