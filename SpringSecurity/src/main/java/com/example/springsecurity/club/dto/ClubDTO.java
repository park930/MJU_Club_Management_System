package com.example.springsecurity.club.dto;

import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.user.dto.UserDTO;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //동아리 멤버 리스트
    private List<UserDTO> memberList;
    private int memberCount;

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
