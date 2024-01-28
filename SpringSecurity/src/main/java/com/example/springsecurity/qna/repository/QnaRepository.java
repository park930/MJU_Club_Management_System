package com.example.springsecurity.qna.repository;

import com.example.springsecurity.qna.entity.QnaEntity;
import com.example.springsecurity.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnaRepository extends JpaRepository<QnaEntity,Long> {
    Page<QnaEntity> findByBoardTitleContaining(String keyWord, Pageable pageable);

    List<QnaEntity> findAllByUserEntityOrderByCreatedTimeDesc(UserEntity userEntity);

    List<QnaEntity> findAllByAnswerOrderByCreatedTimeAsc(int Answer);
}
