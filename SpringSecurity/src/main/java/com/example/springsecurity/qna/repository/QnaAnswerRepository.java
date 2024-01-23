package com.example.springsecurity.qna.repository;

import com.example.springsecurity.qna.entity.QnaAnswerEntity;
import com.example.springsecurity.qna.entity.QnaEntity;
import com.example.springsecurity.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaAnswerRepository extends JpaRepository<QnaAnswerEntity,Long> {
    List<QnaAnswerEntity> findAllByQnaEntityOrderByCreatedTimeDesc(QnaEntity qnaEntity);
}
