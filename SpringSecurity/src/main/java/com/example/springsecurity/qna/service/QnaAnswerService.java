package com.example.springsecurity.qna.service;

import com.example.springsecurity.qna.dto.QnaAnswerDTO;
import com.example.springsecurity.qna.dto.QnaDTO;
import com.example.springsecurity.qna.entity.QnaAnswerEntity;
import com.example.springsecurity.qna.entity.QnaEntity;
import com.example.springsecurity.qna.repository.QnaAnswerRepository;
import com.example.springsecurity.qna.repository.QnaRepository;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaAnswerService {
    private final QnaAnswerRepository qnaAnswerRepository;
    private final QnaRepository qnaRepository;

    public Long save(QnaAnswerDTO qnaAnswerDTO) {
        Optional<QnaEntity> optionalQnaEntity = qnaRepository.findById(qnaAnswerDTO.getQnaId());
        if (optionalQnaEntity.isPresent()){
            QnaAnswerEntity qnaAnswerEntity = QnaAnswerEntity.toNewQnaAnswer(qnaAnswerDTO,optionalQnaEntity.get());
            return qnaAnswerRepository.save(qnaAnswerEntity).getId();
        } else {
            return null;
        }
    }

    public List<QnaAnswerDTO> findAll(Long qnaId) {
        Optional<QnaEntity> optionalQnaEntity = qnaRepository.findById(qnaId);
        if (optionalQnaEntity.isPresent()){
            List<QnaAnswerDTO> qnaAnswerDTOList = new ArrayList<>();
            List<QnaAnswerEntity> qnaAnswerEntityList = qnaAnswerRepository.findAllByQnaEntityOrderByCreatedTimeDesc(optionalQnaEntity.get());
            for(QnaAnswerEntity qnaAnswerEntity : qnaAnswerEntityList){
                qnaAnswerDTOList.add(QnaAnswerDTO.toQnaAnswerDTO(qnaAnswerEntity));
            }
            return qnaAnswerDTOList;
        } else {
            return null;
        }

    }
}
