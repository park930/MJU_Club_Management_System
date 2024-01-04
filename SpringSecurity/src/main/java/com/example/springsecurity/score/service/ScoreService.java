package com.example.springsecurity.score.service;

import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.entity.ScoreEntity;
import com.example.springsecurity.score.repository.ScoreClubRepository;
import com.example.springsecurity.score.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public ScoreDTO saveScoreTable(ScoreDTO scoreDTO) {
        ScoreEntity scoreEntity = ScoreEntity.toNewScoreEntity(scoreDTO);
        ScoreEntity savedScoreEntity = scoreRepository.save(scoreEntity);
        return ScoreDTO.toScoreDTO(savedScoreEntity);
    }

    public ScoreDTO findByTodoId(Long id) {
        ScoreEntity scoreEntity = scoreRepository.findByTodoId(id);
        return ScoreDTO.toScoreDTO(scoreEntity);
    }
}
