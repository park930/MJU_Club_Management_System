package com.example.springsecurity.score.dto;

import com.example.springsecurity.club.entity.ClubEntity;
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
    private String submitType;
    private String plusScoreType;
}
