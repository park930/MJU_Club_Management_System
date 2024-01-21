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

    public Page<QnaDTO> paging(Pageable pageable) {

        int page = pageable.getPageNumber() - 1;
        int pageLimit = 4;
        // page(현재 페이지), pageLimit, sort, sort의 기준
        Page<QnaEntity> qnaEntityList = qnaRepository.findAll(PageRequest.of(page,pageLimit,Sort.by(Sort.Direction.DESC,"id")));
        Page<QnaDTO> qnaDTOS = qnaEntityList.map(qna -> new QnaDTO(qna.getId(),qna.getBoardWriter(),qna.getBoardTitle(),qna.getBoardContents(),qna.getSecret(),qna.getUserEntity().getId(),qna.getCreatedTime()) ) ;
        return qnaDTOS;
    }

    public Page<QnaDTO> searchPaging(Pageable pageable,String searchKeyWord) {
        int pageLimit = 4;
        int page = pageable.getPageNumber()-1;
        Page<QnaEntity> qnaEntities = qnaRepository.findByBoardTitleContaining(searchKeyWord,PageRequest.of(page,pageLimit,Sort.by(Sort.Direction.DESC,"id")));
        Page<QnaDTO> qnaDTOS = qnaEntities.map(qna -> new QnaDTO( qna.getId(),qna.getBoardWriter(),qna.getBoardTitle(),qna.getBoardContents(),qna.getSecret(),qna.getUserEntity().getId(),qna.getCreatedTime() ));
        return qnaDTOS;
    }

    public List<QnaDTO> findAllByUserName(String userName) {
        List<QnaDTO> qnaDTOList = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUsername(userName);
        List<QnaEntity> qnaEntityList = qnaRepository.findAllByUserEntityOrderByCreatedTimeDesc(userEntity);
        for(QnaEntity qnaEntity : qnaEntityList){
            qnaDTOList.add(QnaDTO.toQnaDTO(qnaEntity));
        }

        if (qnaDTOList.isEmpty()) {
            return null;
        } else {
            return qnaDTOList;
        }
    }
}
