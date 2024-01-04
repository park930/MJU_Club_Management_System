package com.example.springsecurity.score.entity;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.todo.entity.TodoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "score_club_table")
@ToString
public class ScoreClubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "score_id")
    private ScoreEntity scoreEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity clubEntity;

    @Column
    private String submitType;

    @Column
    private String plusScoreType;

    public static ScoreClubEntity toNewScoreClubEntity(ScoreDTO savedScoreDTO, ClubDTO clubDTO) {
        ScoreClubEntity scoreClubEntity = new ScoreClubEntity();
        scoreClubEntity.setScoreEntity(ScoreEntity.toUpdateScoreEntity(savedScoreDTO));
        scoreClubEntity.setClubEntity(ClubEntity.toUpdateClub(clubDTO));
        scoreClubEntity.setSubmitType("no");
        scoreClubEntity.setPlusScoreType("no");
        return scoreClubEntity;
    }
}
