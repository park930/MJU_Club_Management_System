package com.example.springsecurity.board.repository;

import com.example.springsecurity.board.entity.BoardEntity;
import com.example.springsecurity.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepostiory extends JpaRepository<CommentEntity,Long> {

    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
