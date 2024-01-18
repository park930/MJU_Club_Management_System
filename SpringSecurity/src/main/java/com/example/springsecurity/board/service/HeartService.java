package com.example.springsecurity.board.service;

import com.example.springsecurity.board.dto.BoardDTO;
import com.example.springsecurity.board.dto.HeartDTO;
import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.board.entity.HeartEntity;
import com.example.springsecurity.board.repository.BoardRepository;
import com.example.springsecurity.board.repository.HeartRepository;
import com.example.springsecurity.user.dto.UserDTO;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<BoardDTO> findAllByUserId(int id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()){
            List<HeartEntity> heartEntityList = heartRepository.findAllByUserEntityOrderByCreatedTimeDesc(optionalUserEntity.get());
            List<BoardDTO> heartBoardList = new ArrayList<>();
            for(HeartEntity heartEntity : heartEntityList){
                BoardEntity boardEntity = heartEntity.getBoardEntity();
                heartBoardList.add(BoardDTO.toBoardDTO(boardEntity));
            }
            return heartBoardList;
        } else {
            return null;
        }
    }

    public void deleteHeart(BoardDTO boardDTO, String userName) {
        UserEntity userEntity = userRepository.findByUsername(userName);
        HeartEntity heartEntity = heartRepository.findByBoardEntityAndUserEntity(BoardEntity.toUpdateEntity(boardDTO), userEntity);
        heartRepository.delete(heartEntity);
    }
}
