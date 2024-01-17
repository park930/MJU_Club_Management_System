package com.example.springsecurity.club.dto;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.entity.ClubFeeEntity;
import com.example.springsecurity.score.entity.ScoreClubEntity;
import com.example.springsecurity.todo.entity.TodoClubEntity;
import com.example.springsecurity.todo.entity.TodoCommentEntity;
import com.example.springsecurity.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClubFeeDTO {

    private Long id;
    private String type;
    private String userName;
    private int amount;
    private LocalDateTime date;
    private String purpose;
    private Long clubId;

    public static ClubFeeDTO toClubFeeDTO(ClubFeeEntity clubFeeEntity) {
        ClubFeeDTO clubFeeDTO = new ClubFeeDTO();
        clubFeeDTO.setId(clubFeeEntity.getId());
        clubFeeDTO.setType(clubFeeEntity.getType());
        clubFeeDTO.setUserName(clubFeeEntity.getUserName());
        clubFeeDTO.setAmount(clubFeeEntity.getAmount());
        clubFeeDTO.setDate(clubFeeEntity.getDate());
        clubFeeDTO.setPurpose(clubFeeEntity.getPurpose());
        return clubFeeDTO;
    }
}
