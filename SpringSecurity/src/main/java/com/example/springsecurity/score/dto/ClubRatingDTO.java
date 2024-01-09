package com.example.springsecurity.score.dto;


import com.example.springsecurity.club.dto.ClubDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClubRatingDTO {

    private List<String> headText;
    private List<Integer> totalScoreList;
    private List<ScoreDTO> updateScoreList;
    private List<ClubDTO> clubDTOList;

}
