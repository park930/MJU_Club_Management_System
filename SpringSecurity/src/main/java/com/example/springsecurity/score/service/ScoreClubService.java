package com.example.springsecurity.score.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.entity.ScoreClubEntity;
import com.example.springsecurity.score.entity.ScoreEntity;
import com.example.springsecurity.score.repository.ScoreClubRepository;
import com.example.springsecurity.score.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScoreClubService {

    private final ScoreClubRepository scoreClubRepository;
    private final ScoreRepository scoreRepository;

    public void save(ScoreDTO savedScoreDTO, ClubDTO clubDTO) {
        ScoreClubEntity scoreClubEntity = ScoreClubEntity.toNewScoreClubEntity(savedScoreDTO,clubDTO);
        scoreClubRepository.save(scoreClubEntity);
    }

    public void saveSubmitType(Long todoId, ClubDTO clubDTO, LocalDateTime submitTime) {
        ScoreDTO scoreDTO = ScoreDTO.toScoreDTO(scoreRepository.findByTodoId(todoId));
        ScoreClubEntity scoreEntity = scoreClubRepository.findByClubEntityAndScoreEntity(ClubEntity.toUpdateClub(clubDTO), ScoreEntity.toUpdateScoreEntity(scoreDTO));

        System.out.println("scoreDTO = " + scoreDTO);

        LocalDateTime lateTime = scoreDTO.getLateTime();
        String type = null;
        if (submitTime.isBefore(scoreDTO.getEndTime()) ) {
            type = "normal";
        } else if ( lateTime != null && submitTime.isBefore(lateTime) ) {
            type = "late";
        } else {
            type = "no";
        }

        scoreEntity.setSubmitType(type);
        scoreClubRepository.save(scoreEntity);

    }
}
