package com.example.springsecurity.score.entity;

import com.example.springsecurity.board.entity.BaseEntity;
import com.example.springsecurity.score.dto.ScoreDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private String title;

}
