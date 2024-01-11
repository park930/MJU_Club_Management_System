package com.example.springsecurity.board.repository;

import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.board.entity.FavoriteBoardEntity;
import com.example.springsecurity.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<FavoriteBoardEntity,Long> {

    List<FavoriteBoardEntity> findAllByUserEntityOrderByIdDesc (UserEntity userEntity);

    FavoriteBoardEntity findByBoardEntityAndUserEntity(BoardEntity boardEntity, UserEntity userEntity);

}
