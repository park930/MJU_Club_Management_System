package com.example.springsecurity.score.repository;

import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.score.entity.ScoreClubEntity;
import com.example.springsecurity.score.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreClubRepository extends JpaRepository<ScoreClubEntity,Long> {
    ScoreClubEntity findByClubEntityAndScoreEntity (ClubEntity clubEntity, ScoreEntity scoreEntity);
}
