package com.example.springsecurity.score.repository;

import com.example.springsecurity.score.entity.ScoreClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreClubRepository extends JpaRepository<ScoreClubEntity,Long> {
}
