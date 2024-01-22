package com.example.springsecurity.score.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.score.dto.ScoreDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "score_table")
@ToString
public class ScoreEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int normalSubmit;

    @Column
    private int lateSubmit;

    @Column
    private int noSubmit;

    @Column
    private int plusRank1;

    @Column
    private int plusRank2;

    @Column
    private int plusRank3;

    @Column
    private Long todoId;

    @Column
    private String title;

    @Column
    private LocalDateTime lateTime;

    @Column
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "scoreEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ScoreClubEntity> scoreClubEntityList = new ArrayList<>();

    public static ScoreEntity toNewScoreEntity(ScoreDTO scoreDTO) {
        ScoreEntity scoreEntity = new ScoreEntity();
        scoreEntity.setTitle(scoreDTO.getTitle());
        scoreEntity.setNormalSubmit(scoreDTO.getNormalSubmit());
        scoreEntity.setLateSubmit(scoreDTO.getLateSubmit());
        scoreEntity.setNoSubmit(scoreDTO.getNoSubmit());
        scoreEntity.setPlusRank1(scoreDTO.getPlusRank1());
        scoreEntity.setPlusRank2(scoreDTO.getPlusRank2());
        scoreEntity.setPlusRank3(scoreDTO.getPlusRank3());
        scoreEntity.setLateTime(scoreDTO.getLateTime());
        scoreEntity.setEndTime(scoreDTO.getEndTime());
        scoreEntity.setTodoId(scoreDTO.getTodoId());
        return scoreEntity;
    }

    public static ScoreEntity toUpdateScoreEntity(ScoreDTO scoreDTO) {
        ScoreEntity scoreEntity = new ScoreEntity();
        scoreEntity.setId(scoreDTO.getId());
        scoreEntity.setTitle(scoreDTO.getTitle());
        scoreEntity.setNormalSubmit(scoreDTO.getNormalSubmit());
        scoreEntity.setLateSubmit(scoreDTO.getLateSubmit());
        scoreEntity.setNoSubmit(scoreDTO.getNoSubmit());
        scoreEntity.setPlusRank1(scoreDTO.getPlusRank1());
        scoreEntity.setPlusRank2(scoreDTO.getPlusRank2());
        scoreEntity.setPlusRank3(scoreDTO.getPlusRank3());
        scoreEntity.setLateTime(scoreDTO.getLateTime());
        scoreEntity.setEndTime(scoreDTO.getEndTime());
        scoreEntity.setTodoId(scoreDTO.getTodoId());
        return scoreEntity;
    }
}
