package com.example.springsecurity.score.repository;

import com.example.springsecurity.score.entity.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {
    ScoreEntity findByTodoId(Long todoId);

}
