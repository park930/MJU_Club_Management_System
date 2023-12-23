package com.example.springsecurity.board.repository;

import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.board.entity.FavoriteBoardEntity;
import com.example.springsecurity.board.entity.HeartEntity;
import com.example.springsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<HeartEntity,Long> {

    HeartEntity findByBoardEntityAndUserEntity (BoardEntity boardEntity, UserEntity userEntity);

    Long countByBoardEntity(BoardEntity boardEntity);
}
