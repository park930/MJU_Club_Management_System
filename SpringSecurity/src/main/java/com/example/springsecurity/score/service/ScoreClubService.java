package com.example.springsecurity.score.service;

import com.example.springsecurity.club.dto.ClubDTO;
import com.example.springsecurity.club.entity.ClubEntity;
import com.example.springsecurity.club.repository.ClubRepository;
import com.example.springsecurity.score.dto.ScoreClubDTO;
import com.example.springsecurity.score.dto.ScoreDTO;
import com.example.springsecurity.score.entity.ScoreClubEntity;
import com.example.springsecurity.score.entity.ScoreEntity;
import com.example.springsecurity.score.repository.ScoreClubRepository;
import com.example.springsecurity.score.repository.ScoreRepository;
import com.example.springsecurity.todo.entity.TodoCommentEntity;
import com.example.springsecurity.todo.entity.TodoEntity;
import com.example.springsecurity.todo.repository.TodoCommentRepository;
import com.example.springsecurity.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreClubService {

    private final ScoreClubRepository scoreClubRepository;
    private final TodoCommentRepository todoCommentRepository;
    private final ScoreRepository scoreRepository;
    private final TodoRepository todoRepository;
    private final ClubRepository clubRepository;

    public void save(ScoreDTO savedScoreDTO, ClubDTO clubDTO) {
        ScoreClubEntity scoreClubEntity = ScoreClubEntity.toNewScoreClubEntity(savedScoreDTO,clubDTO);
        scoreClubRepository.save(scoreClubEntity);
    }

    public void saveSubmitType(Long todoId, ClubDTO clubDTO, LocalDateTime submitTime) {
        ScoreDTO scoreDTO = ScoreDTO.toScoreDTO(scoreRepository.findByTodoId(todoId));
        ScoreClubEntity scoreEntity = scoreClubRepository.findByClubEntityAndScoreEntity(ClubEntity.toUpdateClub(clubDTO), ScoreEntity.toUpdateScoreEntity(scoreDTO));

        LocalDateTime lateTime = scoreDTO.getLateTime();
        if (submitTime.isBefore(scoreDTO.getEndTime()) ) {
            scoreEntity.setSubmitType(scoreDTO.getNormalSubmit());
        } else if ( lateTime != null && submitTime.isBefore(lateTime) ) {
            scoreEntity.setSubmitType(scoreDTO.getLateSubmit());
        } else {
            scoreEntity.setSubmitType(0);
        }
        scoreClubRepository.save(scoreEntity);

    }

    public List<ScoreClubDTO> findAll() {
        List<ScoreClubEntity> scoreClubEntityList = scoreClubRepository.findAll();
        List<ScoreClubDTO> scoreClubDTOList = new ArrayList<>();
        for(ScoreClubEntity scoreClubEntity : scoreClubEntityList){
            ScoreClubDTO scoreClubDTO = ScoreClubDTO.toScoreClubDTO(scoreClubEntity);
            scoreClubDTOList.add(scoreClubDTO);
        }
        return scoreClubDTOList;
    }


    public List<ScoreClubDTO> findAllByScoreDTO(ScoreDTO scoreDTO) {
        List<ScoreClubEntity> scoreClubEntityList = scoreClubRepository.findAllByScoreEntity(ScoreEntity.toUpdateScoreEntity(scoreDTO));
        List<ScoreClubDTO> scoreClubDTOList = new ArrayList<>();
        for(ScoreClubEntity scoreClubEntity : scoreClubEntityList){
            ScoreClubDTO scoreClubDTO = ScoreClubDTO.toScoreClubDTO(scoreClubEntity);
            scoreClubDTOList.add(scoreClubDTO);
        }
        return scoreClubDTOList;
    }

    public List<ClubDTO> filterClubList(List<ScoreClubDTO> scoreClubDTOList) {
        List<ClubDTO> clubDTOList = new ArrayList<>();
        for(ScoreClubDTO scoreClubDTO : scoreClubDTOList){
            Optional<ClubEntity> optionalClubEntity = clubRepository.findById(scoreClubDTO.getClubId());
            ClubDTO clubDTO = null;
            if (optionalClubEntity.isPresent()){
                ClubEntity clubEntity = optionalClubEntity.get();
                clubDTO = ClubDTO.toClubDTO(clubEntity);
            }
            clubDTOList.add(clubDTO);
        }
        return clubDTOList;
    }


    public void updatePlusScore(ClubDTO clubDTO, ScoreDTO scoreDTO, int plusScore) {
        ScoreClubEntity scoreClubEntity = scoreClubRepository.findByClubEntityAndScoreEntity(ClubEntity.toUpdateClub(clubDTO), ScoreEntity.toUpdateScoreEntity(scoreDTO));
        scoreClubEntity.setPlusScoreType(plusScore);
        scoreClubRepository.save(scoreClubEntity);
    }

    public void refreshScore(ScoreDTO scoreDTO) {
        Optional<ScoreEntity> optionalScoreEntity = scoreRepository.findById(scoreDTO.getId());
        if (optionalScoreEntity.isPresent()){
            ScoreEntity scoreEntity = optionalScoreEntity.get();
            List<ScoreClubEntity> scoreClubEntityList = scoreClubRepository.findAllByScoreEntity(scoreEntity);
            for(ScoreClubEntity scoreClubEntity : scoreClubEntityList){
                ClubEntity clubEntity = scoreClubEntity.getClubEntity();
                Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(scoreDTO.getTodoId());
                if (optionalScoreEntity.isPresent()){
                    TodoCommentEntity commentEntity = todoCommentRepository.findByTodoEntityAndClubEntityAndIsSubmit(optionalTodoEntity.get(), clubEntity, 1);
                    if (commentEntity != null){
                        saveSubmitType(scoreDTO.getTodoId(),ClubDTO.toClubDTO(clubEntity),chooseLaterDateTime(commentEntity.getCreatedTime(),commentEntity.getUpdatedTime()));

                    }

                }
            }
        }

    }

    private LocalDateTime chooseLaterDateTime(LocalDateTime timeA, LocalDateTime timeB) {
        if (timeA == null) {
            return timeB;
        } else if (timeB == null) {
            return timeA;
        } else {
            return timeA.isAfter(timeB) ? timeA : timeB;
        }
    }
}
