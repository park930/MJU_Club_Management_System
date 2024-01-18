package com.example.springsecurity.board.repository;

import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.board.entity.HeartEntity;
import com.example.springsecurity.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<HeartEntity,Long> {

    HeartEntity findByBoardEntityAndUserEntity (BoardEntity boardEntity, UserEntity userEntity);

    List<HeartEntity> findAllByUserEntityOrderByCreatedTimeDesc(UserEntity userEntity);
    Long countByBoardEntity(BoardEntity boardEntity);
}
