package com.example.springsecurity.score.dto;

import com.example.springsecurity.score.entity.ScoreEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO {

    private Long id;
    private int normalSubmit;
    private int lateSubmit;
    private int noSubmit;
    private int plusRank1;
    private int plusRank2;
    private int plusRank3;
    private String title;

}
