package com.example.springsecurity.score.dto;

import com.example.springsecurity.score.entity.ScoreEntity;
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
public class ScoreDTO {

    private Long id;
    private String title;
    private int normalSubmit;
    private int lateSubmit;
    private int noSubmit;
    private int plusRank1;
    private int plusRank2;
    private int plusRank3;
    private LocalDateTime lateTime;
    private LocalDateTime endTime;
    private Long todoId;
    private List<Integer> totalScoreList;
    private List<Integer> totalPlusScoreList;

    public static ScoreDTO toScoreDTO(ScoreEntity savedScoreEntity) {
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setId(savedScoreEntity.getId());
        scoreDTO.setTitle(savedScoreEntity.getTitle());
        scoreDTO.setNormalSubmit(savedScoreEntity.getNormalSubmit());
        scoreDTO.setLateSubmit(savedScoreEntity.getLateSubmit());
        scoreDTO.setNoSubmit(savedScoreEntity.getNoSubmit());
        scoreDTO.setPlusRank1(savedScoreEntity.getPlusRank1());
        scoreDTO.setPlusRank2(savedScoreEntity.getPlusRank2());
        scoreDTO.setPlusRank3(savedScoreEntity.getPlusRank3());
        scoreDTO.setLateTime(savedScoreEntity.getLateTime());
        scoreDTO.setEndTime(savedScoreEntity.getEndTime());
        scoreDTO.setTodoId(savedScoreEntity.getTodoId());
        return scoreDTO;
    }

}
