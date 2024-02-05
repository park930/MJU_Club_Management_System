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

    private String setAnswerString(int secret) {
        if (secret==1){
            return "답변 완료";
        } else {
            return "답변 미완료";
        }
    }

    public Page<QnaDTO> paging(Pageable pageable) {

        int page = pageable.getPageNumber() - 1;
        int pageLimit = 4;
        // page(현재 페이지), pageLimit, sort, sort의 기준
        Page<QnaEntity> qnaEntityList = qnaRepository.findAll(PageRequest.of(page,pageLimit,Sort.by(Sort.Direction.DESC,"id")));
        Page<QnaDTO> qnaDTOS = qnaEntityList.map(qna -> new QnaDTO(qna.getId(),qna.getBoardWriter(),qna.getBoardTitle(),qna.getBoardContents(),qna.getAnswer(),qna.getSecret(),qna.getUserEntity().getId(),qna.getCreatedTime(),setAnswerString(qna.getSecret())) ) ;
        return qnaDTOS;
    }

    public Page<QnaDTO> searchPaging(Pageable pageable,String searchKeyWord) {
        int pageLimit = 4;
        int page = pageable.getPageNumber()-1;
        Page<QnaEntity> qnaEntities = qnaRepository.findByBoardTitleContaining(searchKeyWord,PageRequest.of(page,pageLimit,Sort.by(Sort.Direction.DESC,"id")));
        Page<QnaDTO> qnaDTOS = qnaEntities.map(qna -> new QnaDTO( qna.getId(),qna.getBoardWriter(),qna.getBoardTitle(),qna.getBoardContents(),qna.getAnswer(),qna.getSecret(),qna.getUserEntity().getId(),qna.getCreatedTime(),setAnswerString(qna.getSecret()) ));
        return qnaDTOS;
    }

    public List<QnaDTO> findAllByUserName(String userName) {
        List<QnaDTO> qnaDTOList = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUsername(userName);
        List<QnaEntity> qnaEntityList = qnaRepository.findAllByUserEntityOrderByCreatedTimeDesc(userEntity);
        for(QnaEntity qnaEntity : qnaEntityList){
            QnaDTO qnaDTO = QnaDTO.toQnaDTO(qnaEntity);
            if (qnaDTO.getSecret()==1){
                qnaDTO.setAnswerString("답변 완료");
            } else {
                qnaDTO.setAnswerString("답변 미완료");
            }
            qnaDTOList.add(qnaDTO);
        }

        if (qnaDTOList.isEmpty()) {
            return null;
        } else {
            return qnaDTOList;
        }
    }

    public void setAnswer(Long qnaId) {
        Optional<QnaEntity> optionalQnaEntity = qnaRepository.findById(qnaId);
        if (optionalQnaEntity.isPresent()){
            QnaEntity qnaEntity = optionalQnaEntity.get();
            qnaEntity.setAnswer(1);
            qnaRepository.save(qnaEntity);
        }
    }


    public List<QnaDTO> findNoAnswerAll() {
        List<QnaEntity> qnaEntityList = qnaRepository.findAllByAnswerOrderByCreatedTimeAsc(0);
        List<QnaDTO> qnaDTOList = new ArrayList<>();
        for(QnaEntity qnaEntity : qnaEntityList){
            qnaDTOList.add(QnaDTO.toQnaDTO(qnaEntity));
        }

        if (qnaDTOList.isEmpty()) {
            return null;
        } else {
            return qnaDTOList;
        }
    }

    public int getRight(String id, String boardWriter) {
        if (id.startsWith("admin") || id.equals(boardWriter)){
            return 1;
        } else {
            return 0;
        }
    }

    public void deleteById(Long qnaId) {
        Optional<QnaEntity> optionalQnaEntity = qnaRepository.findById(qnaId);
        if (optionalQnaEntity.isPresent()){
            qnaRepository.delete(optionalQnaEntity.get());
        }
    }

    public Long update(QnaDTO qnaDTO) {
        Optional<QnaEntity> optionalQnaEntity = qnaRepository.findById(qnaDTO.getId());
        if (optionalQnaEntity.isPresent()){
            QnaEntity qnaEntity = optionalQnaEntity.get();
            qnaEntity.setBoardTitle(qnaDTO.getBoardTitle());
            qnaEntity.setBoardContents(qnaDTO.getBoardContents());
            return qnaRepository.save(qnaEntity).getId();
        }
        return qnaDTO.getId();
    }
}
