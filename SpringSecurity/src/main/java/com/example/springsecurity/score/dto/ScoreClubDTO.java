package com.example.springsecurity.score.dto;

import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.score.entity.ScoreClubEntity;
import com.example.springsecurity.score.entity.ScoreEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScoreClubDTO {

    private Long id;
    private Long scoreId;
    private Long clubId;
    private int submitType;
    private int plusScoreType;

    public static ScoreClubDTO toScoreClubDTO(ScoreClubEntity scoreClubEntity) {
        ScoreClubDTO scoreClubDTO = new ScoreClubDTO();
        scoreClubDTO.setId(scoreClubEntity.getId());
        scoreClubDTO.setScoreId(scoreClubEntity.getScoreEntity().getId());
        scoreClubDTO.setClubId(scoreClubEntity.getClubEntity().getId());
        scoreClubDTO.setSubmitType(scoreClubEntity.getSubmitType());
        scoreClubDTO.setPlusScoreType(scoreClubEntity.getPlusScoreType());
        return  scoreClubDTO;
    }
}
