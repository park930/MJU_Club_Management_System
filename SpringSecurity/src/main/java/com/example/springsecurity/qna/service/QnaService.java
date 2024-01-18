package com.example.springsecurity.qna.service;

import com.example.springsecurity.qna.dto.QnaDTO;
import com.example.springsecurity.qna.entity.QnaEntity;
import com.example.springsecurity.qna.repository.QnaRepository;
import com.example.springsecurity.user.entity.UserEntity;
import com.example.springsecurity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;

    public List<QnaDTO> findAll() {
        List<QnaEntity> qnaEntityList = qnaRepository.findAll();

        List<QnaDTO> qnaDTOList = new ArrayList<>();
        for(QnaEntity qnaEntity : qnaEntityList){
            qnaDTOList.add(QnaDTO.toQnaDTO(qnaEntity));
        }
        return qnaDTOList;
    }

    public void save(QnaDTO qnaDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(qnaDTO.getUserId());
        if (optionalUserEntity.isPresent()){
            QnaEntity qnaEntity = QnaEntity.toNewQnaEntity(qnaDTO,optionalUserEntity.get());
            qnaRepository.save(qnaEntity);
        }
    }

    public QnaDTO findById(Long qnaId) {
        Optional<QnaEntity> optionalQnaEntity = qnaRepository.findById(qnaId);
        if (optionalQnaEntity.isPresent()){
            return QnaDTO.toQnaDTO(optionalQnaEntity.get());
        } else {
            return null;
        }
    }
}
