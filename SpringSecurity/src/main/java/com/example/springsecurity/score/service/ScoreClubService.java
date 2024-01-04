package com.example.springsecurity.score.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.entity.ScoreClubEntity;
import com.example.springsecurity.score.repository.ScoreClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreClubService {

    private final ScoreClubRepository scoreClubRepository;

    public void save(ScoreDTO savedScoreDTO, ClubDTO clubDTO) {
        ScoreClubEntity scoreClubEntity = ScoreClubEntity.toNewScoreClubEntity(savedScoreDTO,clubDTO);
        scoreClubRepository.save(scoreClubEntity);
    }
}
