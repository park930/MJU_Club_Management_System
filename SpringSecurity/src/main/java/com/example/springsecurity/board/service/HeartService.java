package com.example.springsecurity.board.service;

import com.example.springsecurity.board.dto.HeartDTO;
import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.board.entity.HeartEntity;
import com.example.springsecurity.board.repository.BoardRepository;
import com.example.springsecurity.board.repository.HeartRepository;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    public String saveHeart(HeartDTO heartDTO) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(heartDTO.getBoardId());
        UserEntity userEntity = userRepository.findByUsername(heartDTO.getUserId());
        if (optionalBoardEntity.isPresent() && userEntity!=null){
            BoardEntity boardEntity = optionalBoardEntity.get();

            HeartEntity findHeartEntity = heartRepository.findByBoardEntityAndUserEntity(boardEntity,userEntity);
            if (findHeartEntity == null){
                HeartEntity heartEntity = HeartEntity.toHeartDTO(heartDTO,boardEntity,userEntity);
                heartRepository.save(heartEntity);
                return "좋아요 ❤";
            } else {
                heartRepository.delete(findHeartEntity);
                return "좋아요 💔";
            }
        } else {
            return "좋아요 등록에 실패했습니다..";
        }
    }

    public Long heartCount(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()){
            return heartRepository.countByBoardEntity(optionalBoardEntity.get());
        } else {
            return 0L;
        }
    }
}
