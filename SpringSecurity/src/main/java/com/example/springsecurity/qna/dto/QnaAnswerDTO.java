package com.example.springsecurity.qna.dto;

import com.example.springsecurity.qna.entity.QnaAnswerEntity;
import com.example.springsecurity.qna.entity.QnaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QnaAnswerDTO {

    private Long id;
    private String answerWriter;
    private String answerContents;
    private Long qnaId;
    private LocalDateTime createdTime;

    public static QnaAnswerDTO toQnaAnswerDTO(QnaAnswerEntity qnaAnswerEntity) {
        QnaAnswerDTO qnaAnswerDTO = new QnaAnswerDTO();
        qnaAnswerDTO.setAnswerWriter(qnaAnswerEntity.getAnswerWriter());
        qnaAnswerDTO.setAnswerContents(qnaAnswerEntity.getAnswerContents());
        qnaAnswerDTO.setCreatedTime(qnaAnswerEntity.getCreatedTime());
        return qnaAnswerDTO;
    }
}
